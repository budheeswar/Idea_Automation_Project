package com.ll.idea.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import com.ll.idea.constants.IdeaWFSConstants;
import com.ll.idea.model.IdeaDocumentType;

public class CSVFileParser {

	public CSVFileParser() {
		//Do instantiate an object
	}
	
	
	/**
	 * Read the  list of document types from CSV file and build IdeaDocumentType object. This method
	 * reads the document type id from CSV file and get the corresponding document name from SQL DB document_types table
	 * IdeaDocumentType object holds document type id & corresponding document name.
	 *
	 * @param csvFilePath, predefined csv file specified with list document types of the given loan document carrying Abosolute file path
	 * @return, list of IdeaDocumentTypes
	 */
	public Map<String,String> getSmartkeyDEMockData(String mockDataFileName) throws Exception {
	
		String filePath = System.getProperty("user.dir")+File.separator+"src"+File.separator+"test"+File.separator + 
															"java" + File.separator + "com" + File.separator + "ll" +File.separator + "idea" +File.separator+ "smartkey" +File.separator+ "mock" +File.separator+"data"+ File.separator + mockDataFileName;
	
		BufferedReader bufferedReader = null;
		String csvFileRecord = null;
		HashMap<String,String> hashmap = new HashMap<>();
		boolean isHeaderRecord = true;
		try {
			
			bufferedReader = new BufferedReader(new FileReader(filePath));
			while(((csvFileRecord = bufferedReader.readLine()) != null)) {
				//First record is header, hence ignoring the header and proceeding to detail record.
				csvFileRecord = csvFileRecord.trim();
				if(!isHeaderRecord && !csvFileRecord.isBlank()) {
					String[] tmpStrArr = csvFileRecord.split(",");
					hashmap.put(tmpStrArr[0], tmpStrArr[2]);
				}
				isHeaderRecord = false;
			}
		} catch(Exception ex) {
			throw new Exception ("Exception occured while reading Smartkey mock data due to " + ex.getMessage());
		} finally {
			if(bufferedReader !=null) bufferedReader.close();
		}
		return hashmap;
	}
		
	
	/**
	 * Read the  list of document types from CSV file and build IdeaDocumentType object. This method
	 * reads the document type id from CSV file and get the corresponding document name from SQL DB document_types table
	 * IdeaDocumentType object holds document type id & corresponding document name.
	 * @param hashMap carries input parameters from input csv file. documentTypeMap carries document type information from sample CSV file attached
	 *
	 * @param csvFilePath, predefined csv file specified with list document types of the given loan document carrying Abosolute file path
	 * @return, list of IdeaDocumentTypes
	 */
	public Vector<IdeaDocumentType> getDocumentTypes(Map<String, String> hashMap, Map<String,String> documentTypeMap) throws Exception {
		
		String simulatorCSVFilePath = System.getProperty("user.dir") + File.separator + IdeaWFSConstants.DOCUMENT_TYPE_CSV_FILE_PATH_BASE + File.separator + hashMap.get("DocumentTypeCSV");
		Vector<IdeaDocumentType> documentTypeVector = null;
		BufferedReader bufferedReader = null;
		String csvFileRecord = null;
		boolean isHeaderRecord = true;
		try {
			documentTypeVector = new Vector<>();
			bufferedReader = new BufferedReader(new FileReader(simulatorCSVFilePath));
			while(((csvFileRecord = bufferedReader.readLine()) != null)) {
				//First record is header, hence ignoring the header and proceeding to detail record.
				if(!isHeaderRecord) {
					String[] tmpStrArr = csvFileRecord.split(",");
					IdeaDocumentType ideaDocumentType = null;
					ideaDocumentType = new IdeaDocumentType();
					ideaDocumentType.setDocumentTypeId(tmpStrArr[2].replace("\"",""));
					ideaDocumentType.setDocumentTypeName(documentTypeMap.get(ideaDocumentType.getDocumentTypeId()));
					ideaDocumentType.setImagePageNumber(tmpStrArr[3].replace("\"",""));
					ideaDocumentType.setDocumentPageNumber(tmpStrArr[4].replace("\"",""));
					documentTypeVector.add(ideaDocumentType);
				}
				isHeaderRecord = false;
			}
		} catch(Exception ex) {
			throw new Exception ("Error occured due to " + ex.getMessage());
		} finally {
			if(bufferedReader != null) {
				bufferedReader.close();
			}
		}
		
		return documentTypeVector;
	}
	
	public static void main(String...args) throws Exception {
		CSVFileParser parser = new CSVFileParser();
		String csvFile = "SmartkeyDEMockData.csv";
		parser.getSmartkeyDEMockData(csvFile);
	}
public Vector<IdeaDocumentType> getDocumentType(Map<String, String> hashMap, Map<String,String> documentTypeMap) throws Exception {
		
		String simulatorCSVFilePath = System.getProperty("user.dir") + File.separator + IdeaWFSConstants.DOCUMENT_TYPE_CSV_FILE_PATH_BASE + File.separator + hashMap.get("NewDocumentTypeCSV");
		Vector<IdeaDocumentType> documentTypeVector = null;
		BufferedReader bufferedReader = null;
		String csvFileRecord = null;
		boolean isHeaderRecord = true;
		try {
			documentTypeVector = new Vector<>();
			bufferedReader = new BufferedReader(new FileReader(simulatorCSVFilePath));
			while(((csvFileRecord = bufferedReader.readLine()) != null)) {
				//First record is header, hence ignoring the header and proceeding to detail record.
				if(!isHeaderRecord) {
					String[] tmpStrArr = csvFileRecord.split(",");
					IdeaDocumentType ideaDocumentType = null;
					ideaDocumentType = new IdeaDocumentType();
					ideaDocumentType.setDocumentTypeId(tmpStrArr[2].replace("\"",""));
					ideaDocumentType.setDocumentTypeName(documentTypeMap.get(ideaDocumentType.getDocumentTypeId()));
					ideaDocumentType.setImagePageNumber(tmpStrArr[3].replace("\"",""));
					ideaDocumentType.setDocumentPageNumber(tmpStrArr[4].replace("\"",""));
					documentTypeVector.add(ideaDocumentType);
				}
				isHeaderRecord = false;
			}
		} catch(Exception ex) {
			throw new Exception ("Error occured due to " + ex.getMessage());
		} finally {
			if(bufferedReader != null) {
				bufferedReader.close();
			}
		}
		
		return documentTypeVector;
	}
}
