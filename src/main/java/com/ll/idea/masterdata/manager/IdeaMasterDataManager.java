package com.ll.idea.masterdata.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ll.idea.model.IdeaDocumentType;
import com.ll.idea.utils.CSVFileParser;
import com.ll.idea.utils.MySQLDBConnection;

public class IdeaMasterDataManager {

	public IdeaMasterDataManager() {
		//empty constructor to instantiate the object
	}
	
	/**
	 * Reads document type id, document type name from aklero_prod_db.document_types table
	 * @return HashMap, carries document type id, document type name
	 */
	public static synchronized Map<String,String> loadDocumentTypesTable() throws SQLException {
		HashMap<String,String> documentTypeAndNameMap = null;
		Connection connection = null;
		PreparedStatement prepStatement = null;
		ResultSet resultSet = null;
		String sqlQuery = "SELECT DOCUMENT_TYPE_ID, DOCUMENT_NAME FROM DOCUMENT_TYPES ORDER BY DOCUMENT_TYPE_ID ASC";
		try { 
			documentTypeAndNameMap = new HashMap<>();
			connection = new MySQLDBConnection("aklero_prod_db").getMySQLDBConnection();
			prepStatement = connection.prepareStatement(sqlQuery);
			resultSet = prepStatement.executeQuery();
			while(resultSet.next()) {
				 documentTypeAndNameMap.put(resultSet.getString("DOCUMENT_TYPE_ID"),resultSet.getString("DOCUMENT_NAME"));
			}
		} catch(SQLException sqlEx) {
			throw sqlEx;
		} finally {
			if(resultSet != null) {
				resultSet.close();
			}
			if(prepStatement != null) {
				prepStatement.close();
			}
			
			if(connection != null) {
				connection.close();
			}
		}
		return documentTypeAndNameMap;
	}
	
	public static void main(String...args) throws Exception {
		IdeaMasterDataManager dataManager = new IdeaMasterDataManager();
		HashMap<String,String> dataMap = (HashMap<String,String>)dataManager.loadDocumentTypesTable();
		CSVFileParser parser = new CSVFileParser();
		HashMap<String,String> hashmap = new HashMap<>();
		hashmap.put("DocumentTypeCSV", "5.csv");
		List<IdeaDocumentType> documentTypeList = parser.getDocumentTypes(hashmap, dataMap);
		for(int index=0; index<documentTypeList.size();index++) {
			IdeaDocumentType typeObj = (IdeaDocumentType)documentTypeList.get(index);
			System.out.println("" + typeObj.getDocumentTypeId() + ":" + typeObj.getDocumentTypeName() + ": " + typeObj.getImagePageNumber() + ":" + typeObj.getDocumentPageNumber());
		}
	}
}
