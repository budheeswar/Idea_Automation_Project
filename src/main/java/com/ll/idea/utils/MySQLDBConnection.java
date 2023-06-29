package com.ll.idea.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import com.ll.idea.model.LoanValueObject;

public class MySQLDBConnection {
	Connection connection = null;

	public MySQLDBConnection() {
		this.createMySQLConnection();
	}

	public MySQLDBConnection(String dbName) {
		this.createMySQLConnection(dbName);
	}

	public Connection getMySQLDBConnection() {
		return this.connection;
	}

	private void createMySQLConnection() {
		String hostName = null;
		String dbName = null;
		String userName = null;
		String password = null;
		try {
			hostName = EnvironmentPropertyLoader.getPropertyByName("mysql_host");
			dbName = EnvironmentPropertyLoader.getPropertyByName("mysql_db");
			userName = EnvironmentPropertyLoader.getPropertyByName("mysql_user");
			password = EnvironmentPropertyLoader.getPropertyByName("mysql_pwd");
			String connectionURL = "jdbc:mysql://" + hostName + "/" + dbName;
			this.connection = DriverManager.getConnection(connectionURL, userName, password);
		} catch (SQLException sqlEx) {
			sqlEx.printStackTrace();
		}
	}

	private void createMySQLConnection(String dbName) {
		String hostName = null;
		String userName = null;
		String password = null;
		try {
			hostName = EnvironmentPropertyLoader.getPropertyByName("mysql_host");
			if (dbName != null && dbName.contains("aklero_prod_db")) {
				dbName = EnvironmentPropertyLoader.getPropertyByName("mysql_aklero_db");
			} else {
				dbName = EnvironmentPropertyLoader.getPropertyByName("mysql_db");
			}
			userName = EnvironmentPropertyLoader.getPropertyByName("mysql_user");
			password = EnvironmentPropertyLoader.getPropertyByName("mysql_pwd");
			String connectionURL = "jdbc:mysql://" + hostName + "/" + dbName;
			this.connection = DriverManager.getConnection(connectionURL, userName, password);
		} catch (SQLException sqlEx) {
			sqlEx.printStackTrace();
		}
	}

	public boolean pollAndCheckWorkFlowState(String loanNumber, String loanState, String loanStatus) throws Exception {
		boolean isMet = false;
		int howmanyTimes = Integer.parseInt(ConfigPropertyLoader.getConfigValue("sleepTimeOneThousandMs"));
		long sleepTime = Long.parseLong(ConfigPropertyLoader.getConfigValue("howmanytime"));
		ArrayList<LoanValueObject> loanValueObjectList = null;
		LoanValueObject loanValueObject = null;
		try {
			while (true) {
				loanValueObjectList = this.getLoanDetails(loanNumber);
				if (loanValueObjectList != null && !loanValueObjectList.isEmpty()) {
					loanValueObject = loanValueObjectList.get(0);
					if (loanState.equals(loanValueObject.getLoanState())
							&& loanStatus.equals(loanValueObject.getLoanStatus())) {
						isMet = true;
					}

					if (!isMet && howmanyTimes > 0) {
						Thread.sleep(sleepTime);
						howmanyTimes--;
					}
					if (isMet || howmanyTimes == 0) {
						break;
					}
				} else if (!isMet && howmanyTimes > 0) {
					Thread.sleep(sleepTime);
					howmanyTimes--;
				}
			}
		} catch (Exception ex) {
			throw ex;
		}
		return isMet;
	}

	public ArrayList<LoanValueObject> getLoanDetails(String loanNumber) throws Exception {
		ArrayList<LoanValueObject> loanValueObjList = new ArrayList<>();
		LoanValueObject loanValueObj = null;
		Connection dbConnection = null;
		PreparedStatement pStmt = null;
		ResultSet resultSet = null;
		String sqlQuery = "SELECT lb.LOAN_BATCH_ID AS LoanBatch," + "lb.PAGE_COUNT AS PageCount,"
				+ "lb.LOAN_ID AS LoanID," + "l.LOAN_NUMBER AS LoanNumber," + "lb.PRIORITY AS Priority,"
				+ "wn.NODE_NAME AS State," + "wi.DATE_CREATED AS WORK_ITEM_DATE," + "wis.STATUS_NAME AS Status,"
				+ "co.COMPANY_NAME AS Company," + "c.CLIENT_NAME AS Client," + "v.VENDOR_NAME AS Vendor,"
				+ "p.PRODUCT_NAME AS Product," + "lb.DATE_CREATED AS DateSubmitted,"
				+ "lb.FLOW_COMPLETED_DATE AS ExportNode," + "lb.DATE_EXPORTED_FROM_IDEA AS DateExportedFROMIDEA,"
				+ "bl.NAME AS Batch," + "wl.NAME AS Workflow," + "CONCAT(u.LAST_NAME,  u.FIRST_NAME) AS Operator,"
				+ "lol.sla_export_due_by AS ExportSLA," + "lol.sla_load_due_by AS HDLoadSLA," + "lb.DIY AS DIY  FROM "
				+ "((((((((((((((idea_db_211.loan_batch lb USE INDEX (INDEX_LOAN_BATCH_DATE_CREATED) "
				+ "JOIN idea_db_211.loan l ON ((l.LOAN_ID = lb.LOAN_ID))) "
				+ "JOIN aklero_prod_db.client c ON ((c.CLIENT_ID = l.CLIENT_ID))) "
				+ "JOIN aklero_prod_db.company co ON ((co.COMPANY_ID = c.COMPANY_ID))) "
				+ "JOIN idea_db_211.vendor_loan_batch vlb ON ((vlb.LOAN_BATCH_ID = lb.LOAN_BATCH_ID))) "
				+ "JOIN idea_db_211.vendor v ON ((v.VENDOR_ID = vlb.VENDOR_ID))) "
				+ "JOIN aklero_prod_db.products p ON ((p.PRODUCT_ID = lb.PRODUCT_ID))) "
				+ "LEFT JOIN idea_db_211.work_item wi  ON ((wi.WORK_ITEM_ID = lb.CURRENT_WORK_ITEM_ID))) "
				+ "LEFT JOIN idea_db_211.workflow_node_mapping wnm ON ((wnm.WORKFLOW_NODE_MAPPING_ID = wi.WORKFLOW_NODE_MAPPING_ID))) "
				+ "LEFT JOIN idea_db_211.workflow_node wn ON ((wn.WORKFLOW_NODE_ID = wnm.WORKFLOW_NODE_ID))) "
				+ "LEFT JOIN idea_db_211.user u ON ((u.USER_ID = wi.ASSIGNED_USER_ID))) "
				+ "LEFT JOIN idea_db_211.work_item_status wis ON ((wis.WORK_ITEM_STATUS_ID = wi.CURRENT_WORK_ITEM_STATUS_ID))) "
				+ "LEFT JOIN idea_db_211.workflow_lookup wl ON ((wl.WORKFLOW_LOOKUP_ID = wnm.WORKFLOW_LOOKUP_ID))) "
				+ "JOIN idea_db_211.batch_lookup bl ON ((bl.BATCH_LOOKUP_ID = lb.BATCH_LOOKUP_ID))) "
				+ "LEFT JOIN life_of_loan_db.loansla lol ON (((lb.LOAN_ID = lol.idea_loan_id) "
				+ "AND (p.PRODUCT_ID = lol.dms_product_id)))) WHERE l.loan_number ='" + loanNumber + "'";
		try {
			dbConnection = this.getMySQLDBConnection();
			pStmt = dbConnection.prepareStatement(sqlQuery);
			System.out.println(sqlQuery);
			resultSet = pStmt.executeQuery();
			if (resultSet != null & resultSet.next()) {
				loanValueObj = new LoanValueObject();
				loanValueObj.setLoanBatchId(resultSet.getString("LoanBatch"));
				loanValueObj.setLoanId(resultSet.getString("LoanID"));
				loanValueObj.setLoanState(resultSet.getString("State"));
				loanValueObj.setLoanStatus(resultSet.getString("Status"));
				loanValueObjList.add(loanValueObj);
			}

		} catch (SQLException ex) {
			throw new Exception("Exception occured due to " + ex.getMessage());
		}
		return loanValueObjList;
	}

	public ResultSet executeSQLQuery(String sqlQuery, String[] parameters) throws SQLException {
		ResultSet resultSet = null;
		PreparedStatement pStmt = null;

		try {
			pStmt = this.connection.prepareStatement(sqlQuery);
			int index = 1;
			if (parameters != null && parameters.length > 0) {
				for (String parameter : parameters) {
					pStmt.setString(index, parameter);
					index++;
				}
			}
			resultSet = pStmt.executeQuery();
		} catch (SQLException sqlEx) {
			sqlEx.printStackTrace();
		} finally {
			if (pStmt != null) {
				// pStmt.close();
			}
		}
		return resultSet;
	}

	public static void main(String[] args) throws SQLException {
		System.out.println("DB Connect & SQL operation");
		MySQLDBConnection dbConnection = new MySQLDBConnection();
		String queryWithParam = "select loan_id, client_id from loan where loan_number = ?";
		String[] params = { "0093251650" };

		ResultSet resultSet = dbConnection.executeSQLQuery(queryWithParam, params);

		while (resultSet.next()) {
			System.out.println(
					"[Loan ID]" + resultSet.getString("loan_id") + "[client_id]" + resultSet.getString("client_id"));

		}
		resultSet.close();
	}

	public String checkIfAnySpecifiedLoanExists(String sStatus, String sState, String sVendor) throws Exception {
		String sLoanNumber = null;
		Connection dbConnection = null;
		PreparedStatement pStmt = null;
		ResultSet resultSet = null;
		String sqlQuery = "SELECT lb.LOAN_BATCH_ID AS LoanBatch," + "lb.PAGE_COUNT AS PageCount,"
				+ "lb.LOAN_ID AS LoanID," + "l.LOAN_NUMBER AS LoanNumber," + "lb.PRIORITY AS Priority,"
				+ "wn.NODE_NAME AS State," + "wi.DATE_CREATED AS WORK_ITEM_DATE," + "wis.STATUS_NAME AS Status,"
				+ "co.COMPANY_NAME AS Company," + "c.CLIENT_NAME AS Client," + "v.VENDOR_NAME AS Vendor,"
				+ "p.PRODUCT_NAME AS Product," + "lb.DATE_CREATED AS DateSubmitted,"
				+ "lb.FLOW_COMPLETED_DATE AS ExportNode," + "lb.DATE_EXPORTED_FROM_IDEA AS DateExportedFROMIDEA,"
				+ "bl.NAME AS Batch," + "wl.NAME AS Workflow," + "CONCAT(u.LAST_NAME,  u.FIRST_NAME) AS Operator,"
				+ "lol.sla_export_due_by AS ExportSLA," + "lol.sla_load_due_by AS HDLoadSLA," + "lb.DIY AS DIY  FROM "
				+ "((((((((((((((idea_db_211.loan_batch lb USE INDEX (INDEX_LOAN_BATCH_DATE_CREATED) "
				+ "JOIN idea_db_211.loan l ON ((l.LOAN_ID = lb.LOAN_ID))) "
				+ "JOIN aklero_prod_db.client c ON ((c.CLIENT_ID = l.CLIENT_ID))) "
				+ "JOIN aklero_prod_db.company co ON ((co.COMPANY_ID = c.COMPANY_ID))) "
				+ "JOIN idea_db_211.vendor_loan_batch vlb ON ((vlb.LOAN_BATCH_ID = lb.LOAN_BATCH_ID))) "
				+ "JOIN idea_db_211.vendor v ON ((v.VENDOR_ID = vlb.VENDOR_ID))) "
				+ "JOIN aklero_prod_db.products p ON ((p.PRODUCT_ID = lb.PRODUCT_ID))) "
				+ "LEFT JOIN idea_db_211.work_item wi  ON ((wi.WORK_ITEM_ID = lb.CURRENT_WORK_ITEM_ID))) "
				+ "LEFT JOIN idea_db_211.workflow_node_mapping wnm ON ((wnm.WORKFLOW_NODE_MAPPING_ID = wi.WORKFLOW_NODE_MAPPING_ID))) "
				+ "LEFT JOIN idea_db_211.workflow_node wn ON ((wn.WORKFLOW_NODE_ID = wnm.WORKFLOW_NODE_ID))) "
				+ "LEFT JOIN idea_db_211.user u ON ((u.USER_ID = wi.ASSIGNED_USER_ID))) "
				+ "LEFT JOIN idea_db_211.work_item_status wis ON ((wis.WORK_ITEM_STATUS_ID = wi.CURRENT_WORK_ITEM_STATUS_ID))) "
				+ "LEFT JOIN idea_db_211.workflow_lookup wl ON ((wl.WORKFLOW_LOOKUP_ID = wnm.WORKFLOW_LOOKUP_ID))) "
				+ "JOIN idea_db_211.batch_lookup bl ON ((bl.BATCH_LOOKUP_ID = lb.BATCH_LOOKUP_ID))) "
				+ "LEFT JOIN life_of_loan_db.loansla lol ON (((lb.LOAN_ID = lol.idea_loan_id) "
				+ "AND (p.PRODUCT_ID = lol.dms_product_id)))) WHERE wis.STATUS_NAME = '" + sStatus
				+ "' AND wn.NODE_NAME = '" + sState + "' AND v.VENDOR_NAME ='" + sVendor + "'";
		try {
			dbConnection = this.getMySQLDBConnection();
			pStmt = dbConnection.prepareStatement(sqlQuery);
			System.out.println(sqlQuery);
			resultSet = pStmt.executeQuery();
			if (resultSet != null & resultSet.next()) {
				sLoanNumber = resultSet.getString("LoanNumber");
			}

		} catch (SQLException ex) {
			throw new Exception("Exception occured due to " + ex.getMessage());
		}
		return sLoanNumber;
	}

	public String checkSplitByCompany(String sClientID, String sProjectID) throws Exception {
		String sSplitByCompany = null;
		Connection dbConnection = null;
		PreparedStatement pStmt = null;
		ResultSet resultSet = null;
		String sqlQuery = "SELECT Product_ID, Client_ID,SPLIT_BY_COMPANY FROM `auto_import_profile` WHERE Client_ID ="
				+ sClientID + " AND Product_ID = " + sProjectID;
		try {
			dbConnection = this.getMySQLDBConnection();
			pStmt = dbConnection.prepareStatement(sqlQuery);
			System.out.println(sqlQuery);
			resultSet = pStmt.executeQuery();
			if (resultSet != null & resultSet.next()) {
				sSplitByCompany = resultSet.getString("SPLIT_BY_COMPANY");
			}

		} catch (SQLException ex) {
			throw new Exception("Exception occured due to " + ex.getMessage());
		}
		return sSplitByCompany;
	}

	public String checkIfAnySpecifiedLoanProduct(String sProductID) throws Exception {
		String smaxPageCount = null;
		Connection dbConnection = null;
		PreparedStatement pStmt = null;
		ResultSet resultSet = null;
		String sqlQuery = "SELECT * FROM loan_import_page_allocation WHERE Product_ID =" + sProductID;
		try {
			dbConnection = this.getMySQLDBConnection();
			pStmt = dbConnection.prepareStatement(sqlQuery);
			System.out.println(sqlQuery);
			resultSet = pStmt.executeQuery();
			if (resultSet != null & resultSet.next()) {
				smaxPageCount = resultSet.getString("MAX_PAGE_COUNT");
			}

		} catch (SQLException ex) {
			throw new Exception("Exception occured due to " + ex.getMessage());
		}
		return smaxPageCount;
	}

	public String checkSplitByCompanyDate(String sClientID, String sProjectID) throws Exception {
		String sDateUpdated = null;
		Connection dbConnection = null;
		PreparedStatement pStmt = null;
		ResultSet resultSet = null;
		String sqlQuery = "SELECT Product_ID, Client_ID,SPLIT_BY_COMPANY,DATE_UPDATED FROM `auto_import_profile` WHERE Client_ID ="
				+ sClientID + " AND Product_ID = " + sProjectID;
		try {
			dbConnection = this.getMySQLDBConnection();
			pStmt = dbConnection.prepareStatement(sqlQuery);
			System.out.println(sqlQuery);
			resultSet = pStmt.executeQuery();
			if (resultSet != null & resultSet.next()) {
				sDateUpdated = resultSet.getString("DATE_UPDATED");
			}

		} catch (SQLException ex) {
			throw new Exception("Exception occured due to " + ex.getMessage());
		}
		return sDateUpdated;
	}

	public ArrayList<LoanValueObject> checkConfidenceValues(String sLoanbatchID) throws Exception {
		ArrayList<LoanValueObject> loanValueObjList = new ArrayList<>();
		LoanValueObject loanValueObj = null;
		Connection dbConnection = null;
		PreparedStatement pStmt = null;
		ResultSet resultSet = null;
		String sqlQuery = "SELECT RELATIVE_CONFIDENCE,CALCULATED_CONFIDENCE,SEPARATION_CONFIDENCE,ML_DOCUMENT_TYPE_ID FROM  espy_document WHERE loan_batch_id ="
				+ sLoanbatchID;
		try {
			dbConnection = this.getMySQLDBConnection();
			pStmt = dbConnection.prepareStatement(sqlQuery);
			System.out.println(sqlQuery);
			resultSet = pStmt.executeQuery();
			while (resultSet.next()) {
				loanValueObj = new LoanValueObject();
				loanValueObj.setRelativeConfidenceValue(resultSet.getString("RELATIVE_CONFIDENCE"));
				loanValueObj.setCalculatedConfidenceValue(resultSet.getString("CALCULATED_CONFIDENCE"));
				loanValueObj.setSeperationalConfidenceValue(resultSet.getString("SEPARATION_CONFIDENCE"));
				loanValueObj.setMlDocumentTypeValue(resultSet.getString("ML_DOCUMENT_TYPE_ID"));
				loanValueObjList.add(loanValueObj);
			}
		} catch (SQLException ex) {
			throw new Exception("Exception occured due to " + ex.getMessage());
		}
		return loanValueObjList;
	}

	public boolean checkThreasholdSeprationAndClassificationValues(String sProductID, String sDocumentID,
			HashMap<String, String> hashmap) throws Exception {
		String thresholdSeprationValue = null;
		String thresholdClassificationValue = null;
		Connection dbConnection = null;
		PreparedStatement pStmt = null;
		ResultSet resultSet = null;
		String sqlQuery = "SELECT THRESHOLD_SEPARATION,THRESHOLD_CLASSIFICATION FROM auto_index_document_threshold WHERE product_ID="
				+ sProductID + " AND DOCUMENT_TYPE_ID=" + sDocumentID;
		try {
			dbConnection = this.getMySQLDBConnection();
			pStmt = dbConnection.prepareStatement(sqlQuery);
			System.out.println(sqlQuery);
			resultSet = pStmt.executeQuery();
			if (resultSet != null & resultSet.next()) {
				thresholdSeprationValue = resultSet.getString("THRESHOLD_SEPARATION");
				thresholdClassificationValue = resultSet.getString("THRESHOLD_CLASSIFICATION");

			}
			hashmap.put("thresholdSeprationValue", thresholdSeprationValue);
			hashmap.put("thresholdClassificationValue", thresholdClassificationValue);

		} catch (SQLException ex) {
			throw new Exception("Exception occured due to " + ex.getMessage());
		}
		return true;
	}

	public String retrieveAutomationPayload(String sLoanbatchID) throws Exception {
		String sAUTOMATION_PAYLOAD = null;
		Connection dbConnection = null;
		PreparedStatement pStmt = null;
		ResultSet resultSet = null;
		String sqlQuery = "SELECT * FROM auto_index_loan_review WHERE loan_batch_ID=" + sLoanbatchID;
		try {
			dbConnection = this.getMySQLDBConnection();
			pStmt = dbConnection.prepareStatement(sqlQuery);
			System.out.println(sqlQuery);
			resultSet = pStmt.executeQuery();
			if (resultSet != null & resultSet.next()) {
				sAUTOMATION_PAYLOAD = resultSet.getString("AUTOMATION_PAYLOAD");
			}

		} catch (SQLException ex) {
			throw new Exception("Exception occured due to " + ex.getMessage());
		}
		return sAUTOMATION_PAYLOAD;
	}

	public void checkDetailsOfEachDocument(String sLoanbatchID, HashMap<String, String> hashmap, String sDocumentID)
			throws Exception {
		Connection dbConnection = null;
		PreparedStatement pStmt = null;
		ResultSet resultSet = null;
		String sqlQuery = "SELECT * FROM  espy_document WHERE loan_batch_id =" + sLoanbatchID
				+ " AND ML_DOCUMENT_TYPE_ID = " + sDocumentID;
		try {
			dbConnection = this.getMySQLDBConnection();
			pStmt = dbConnection.prepareStatement(sqlQuery);
			System.out.println(sqlQuery);
			resultSet = pStmt.executeQuery();
			while (resultSet.next()) {
				hashmap.put("CONFIDENCE_THRESHOLD", resultSet.getString("CONFIDENCE_THRESHOLD"));
				hashmap.put("CONFIDENCE_WEIGHTING", resultSet.getString("CONFIDENCE_WEIGHTING"));
				hashmap.put("RELATIVE_CONFIDENCE", resultSet.getString("RELATIVE_CONFIDENCE"));
				hashmap.put("SEPARATION_CONFIDENCE", resultSet.getString("SEPARATION_CONFIDENCE"));
				hashmap.put("CALCULATED_CONFIDENCE", resultSet.getString("CALCULATED_CONFIDENCE"));
			}

		} catch (SQLException ex) {
			throw new Exception("Exception occured due to " + ex.getMessage());
		}

	}

	public boolean checkReplacedDocumentInESPYDocument(String sLoanbatchID, String sReplaceDocumentBy,
			String sDocumentToBeReplaced) throws Exception {

		boolean status = false;
		String sEIR_DOCUMENT_TYPE_ID = null;
		Connection dbConnection = null;
		PreparedStatement pStmt = null;
		ResultSet resultSet = null;
		String sqlQuery = "SELECT * FROM espy_document WHERE loan_batch_id = " + sLoanbatchID
				+ " AND ML_DOCUMENT_TYPE_ID = " + sDocumentToBeReplaced; // 2025
		try {
			dbConnection = this.getMySQLDBConnection();
			pStmt = dbConnection.prepareStatement(sqlQuery);
			System.out.println(sqlQuery);
			resultSet = pStmt.executeQuery();
			while (resultSet.next()) {
				resultSet.getString("RELATIVE_CONFIDENCE");
				resultSet.getString("CALCULATED_CONFIDENCE");
				sEIR_DOCUMENT_TYPE_ID = resultSet.getString("EIR_DOCUMENT_TYPE_ID");
				resultSet.getString("ML_DOCUMENT_TYPE_ID");
				if (sReplaceDocumentBy.equalsIgnoreCase(sEIR_DOCUMENT_TYPE_ID)) {
					status = true;
				}
			}
		} catch (SQLException ex) {
			throw new Exception("Exception occured due to " + ex.getMessage());
		}
		return status;
	}

	public Integer checkNoOfRecordsInESPYDocument(String sLoanbatchID) throws Exception {
		int count = 0;
		Connection dbConnection = null;
		PreparedStatement pStmt = null;
		ResultSet resultSet = null;
		String sqlQuery = "SELECT * FROM espy_document WHERE loan_batch_id = " + sLoanbatchID; // 2025
		try {
			dbConnection = this.getMySQLDBConnection();
			pStmt = dbConnection.prepareStatement(sqlQuery);
			System.out.println(sqlQuery);
			resultSet = pStmt.executeQuery();
			while (resultSet.next()) {
				count++;
			}
		} catch (SQLException ex) {
			throw new Exception("Exception occured due to " + ex.getMessage());
		}
		return count;
	}

	public Integer checkReplacedDocumentupdatedInESPYDocument(String sLoanbatchID, String sDocumentID)
			throws Exception {
		int count = 0;
		Connection dbConnection = null;
		PreparedStatement pStmt = null;
		ResultSet resultSet = null;
		String sqlQuery = "SELECT * FROM espy_document WHERE loan_batch_id = " + sLoanbatchID
				+ " AND EIR_DOCUMENT_TYPE_ID = " + sDocumentID;
		try {
			dbConnection = this.getMySQLDBConnection();
			pStmt = dbConnection.prepareStatement(sqlQuery);
			System.out.println(sqlQuery);
			resultSet = pStmt.executeQuery();
			while (resultSet.next()) {
				count++;
			}
		} catch (SQLException ex) {
			throw new Exception("Exception occured due to " + ex.getMessage());
		}
		return count;
	}

	public boolean deleteRecordFromAutoindexThreasholdtable(String sProductID, String sDocumentID) throws Exception {
		boolean status = false;
		Connection dbConnection = null;
		PreparedStatement pStmt = null;
		String sqlQuery = "DELETE FROM auto_index_document_threshold WHERE product_ID =" + sProductID
				+ " AND DOCUMENT_TYPE_ID = " + sDocumentID;
		try {
			dbConnection = this.getMySQLDBConnection();
			pStmt = dbConnection.prepareStatement(sqlQuery);
			System.out.println(sqlQuery);
			pStmt.execute();
			status = true;
		} catch (SQLException ex) {
			throw new Exception("Exception occured due to " + ex.getMessage());
		}
		return status;
	}

	public HashSet<String> checkUSOFTField(String sLoanbatchID) throws Exception {
		HashSet<String> values = new HashSet<String>();
		String sUsoftValue = null;
		Connection dbConnection = null;
		PreparedStatement pStmt = null;
		ResultSet resultSet = null;
		String sqlQuery = "SELECT document_de_new.* FROM document_de_new INNER JOIN document ON document.DOCUMENT_ID = document_de_new.DOCUMENT_ID INNER JOIN document_loan_batch ON document.DOCUMENT_ID = document_loan_batch.DOCUMENT_ID WHERE document_loan_batch.LOAN_BATCH_ID = "
				+ sLoanbatchID;
		try {
			dbConnection = this.getMySQLDBConnection();
			pStmt = dbConnection.prepareStatement(sqlQuery);
			System.out.println(sqlQuery);
			resultSet = pStmt.executeQuery();
			while (resultSet.next()) {
				sUsoftValue = resultSet.getString("USOFT");
				CommonUtils.sleepForAWhile(200);
				values.add(sUsoftValue);
			}
		} catch (SQLException ex) {
			throw new Exception("Exception occured due to " + ex.getMessage());
		}
		return values;
	}

	public void insertLoanRecord(String sLoanNumber, String clientid, String productid) throws Exception {
		Connection dbConnection = null;
		PreparedStatement pStmt = null;
		ResultSet resultSet = null;
		String sqlQuery = "INSERT INTO aklero_prod_db.`loans` ( `LOAN_NUMBER`, `CLIENT_PRODUCT_ID`, `ORIG_CLIENT_PRODUCT_ID`, "
				+ "`LOS_DATA_ID`, `IS_DEFICIENT`, `IS_CLOSING_RECEIVED`, `IS_LENDER_RECEIVED`, `IS_CRITICAL_STOP`, "
				+ "`SUPPRESSED_DATE`, `DATE_CREATED`, `DATE_UPDATED`, `CREATE_USER_ID`, `UPDATE_USER_ID`, `WORKSPACE_ID`, "
				+ "`VERSION`, `IS_POA_PRESENT`, `MISS_DOCS_CNT`, `CONTENT_DEF_DOCS_CNT`, `DATA_EXISTENCE_DEF_DOCS_CNT`, "
				+ "`INST_ID`, `LOAN_REVIEW_STATUS_ID`, `IS_FINAL_RECEIVED`, `EXPORT`, `IS_RELEASED`, `IS_INACTIVE`, "
				+ "`UPGRADED`, `IS_REPORT_REQUESTED`, `REPOSITORY_LOOKUP_ID`, `HAS_SIGNATURE_CLIPPING`, `HAS_DATA_CLIPPING`, "
				+ "`QC_PROJECT_ID`, `FS_REPOSITORY_LOOKUP_ID`) VALUES " + "('" + sLoanNumber
				+ "', (SELECT client_Product_id FROM aklero_prod_db.`client_products` WHERE client_id = " + clientid
				+ " AND product_id = " + productid + " ), NULL, 6714788, 'Y', 'Y', 'Y', 'N', '1970-01-01', "
				+ "'2020-10-13 15:56:04', '2020-10-13 15:56:04', 'AkleroSystem', NULL, "
				+ "NULL, '5.5.0', NULL, NULL, NULL, NULL, NULL, NULL, 'N', 0, 0, 0, 1, 0, NULL, 0, 0, NULL, 4)";

		try {
			dbConnection = this.getMySQLDBConnection();
			pStmt = dbConnection.prepareStatement(sqlQuery);
			System.out.println(sqlQuery);
			pStmt.execute(sqlQuery);

		} catch (SQLException ex) {
			throw new Exception("Exception occured due to " + ex.getMessage());
		}

	}

	public Integer checkDemAutomationDocumentDeExists(String sDocumentTypeID, String sClientProductID,
			String AutomationTypeID, String Active) throws Exception {
		int count = 0;
		Connection dbConnection = null;
		PreparedStatement pStmt = null;
		ResultSet resultSet = null;
		String sqlQuery = "SELECT count(*) as count FROM dem_automation_document_de WHERE DOCUMENT_TYPE_ID = "
				+ sDocumentTypeID + " AND CLIENT_PRODUCT_ID = " + sClientProductID + " AND AUTOMATION_TYPE_ID = "
				+ AutomationTypeID + " AND ACTIVE = " + Active;
		try {
			dbConnection = this.getMySQLDBConnection();
			pStmt = dbConnection.prepareStatement(sqlQuery);
			System.out.println(sqlQuery);
			resultSet = pStmt.executeQuery();
			while (resultSet.next()) {
				count = resultSet.getInt("count");
			}
		} catch (SQLException ex) {
			throw new Exception("Exception occured due to " + ex.getMessage());
		}
		return count;
	}

	public Integer checkPagecountThresholdExists(String sClientID, String sProductID, String sMinPagecount,
			String sMaxPagecount) throws Exception {
		int count = 0;
		Connection dbConnection = null;
		PreparedStatement pStmt = null;
		ResultSet resultSet = null;
		String sqlQuery = "SELECT count(*) as count FROM pagecount_threshold WHERE CLIENT_ID = " + sClientID
				+ " AND PRODUCT_ID = " + sProductID + " AND MIN_PAGE_COUNT = " + sMinPagecount
				+ " AND MAX_PAGE_COUNT = " + sMaxPagecount;
		try {
			dbConnection = this.getMySQLDBConnection();
			pStmt = dbConnection.prepareStatement(sqlQuery);
			System.out.println(sqlQuery);
			resultSet = pStmt.executeQuery();
			while (resultSet.next()) {
				count = resultSet.getInt("count");
			}
		} catch (SQLException ex) {
			throw new Exception("Exception occured due to " + ex.getMessage());
		}
		System.out.println("After Running pageCoutn Query the Count is " + count);
		return count;
	}

	public boolean createPagecountThreholdRecordsInDB(int recordsSize) throws Exception {
		boolean r = false;
		int count=0;
		Connection dbConnection = null;
		PreparedStatement pStmt = null;
		ResultSet resultSet = null;
		String deleteQuery = "DELETE FROM pagecount_threshold";
		String sqlQuery = "INSERT INTO pagecount_threshold (CLIENT_ID, PRODUCT_ID, MIN_PAGE_COUNT, MAX_PAGE_COUNT) VALUES (0, 0, 50, 99)";
				try {
					dbConnection = this.getMySQLDBConnection();
					pStmt = dbConnection.prepareStatement(deleteQuery);
					pStmt.execute(deleteQuery);
					pStmt = dbConnection.prepareStatement(sqlQuery);
					System.out.println(sqlQuery);
					for(int i = 1;i<=recordsSize ;i++) {
						pStmt.execute(sqlQuery);
						count++;
					}
					if(count >0) {
						r = true;
					}
							
					
				} catch(SQLException ex) {
					throw new Exception("Exception occured due to " + ex.getMessage());
				}
		return r;
	}
	public void deletePagecountThreholdRecordsInDB() throws Exception {
		Connection dbConnection = null;
		PreparedStatement pStmt = null;
		String deleteQuery = "DELETE FROM pagecount_threshold";
				try {
					dbConnection = this.getMySQLDBConnection();
					pStmt = dbConnection.prepareStatement(deleteQuery);
					pStmt.execute(deleteQuery);
					System.out.println(deleteQuery);					
					
				} catch(SQLException ex) {
					throw new Exception("Exception occured due to " + ex.getMessage());
				}
	}

	public Integer updatePagecountThresholdByClientIDAndProductID(String sClientID, String sProductID, String sMinPagecount,String sMaxPagecount)
			throws Exception {
		int count = 0;
		Connection dbConnection = null;
		PreparedStatement pStmt = null;
		
		
		String sqlQuery = "UPDATE `pagecount_threshold` SET MIN_PAGE_COUNT = "+ sMinPagecount +" , MAX_PAGE_COUNT = " + sMaxPagecount
				+ "  WHERE PRODUCT_ID = "+sProductID+" AND CLIENT_ID = " + sClientID  +";";
		try {
			dbConnection = this.getMySQLDBConnection();
			pStmt = dbConnection.prepareStatement(sqlQuery);
			System.out.println(sqlQuery);
			count = pStmt.executeUpdate();
			if(count==0) {
				//perform insert query then do update
				//INSERT QUERY
				String insQury = "INSERT INTO `pagecount_threshold` ( CLIENT_ID, PRODUCT_ID ) VALUES ("+sClientID+" , "+sProductID+");";
			    pStmt = dbConnection.prepareStatement(insQury);
			    int c = pStmt.executeUpdate();
			    pStmt = dbConnection.prepareStatement(sqlQuery);
				count = pStmt.executeUpdate();
			    }
		} catch (SQLException ex) {
			throw new Exception("Exception occured due to " + ex.getMessage());
		}
		return count;
	}
	

}
