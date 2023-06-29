package com.ll.idea.utils;

import java.io.File;
import java.io.StringReader;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.testng.Assert;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import com.aventstack.extentreports.Status;
import com.ll.idea.model.IdeaDocumentType;
import com.ll.idea.reporting.ReportGenerator;

public class XMLFileHandler {
	
	public XMLFileHandler() {
		//empty constructor to initialize an object
	}

	/**
	 * validateXMLFile method used to validate the details present in the xml file 
	 * @param hashmap 
	 * @throws Exception
	 */
	public void validateBatchXML(Map<String, String> hashmap, ReportGenerator reportGenerator, Vector<IdeaDocumentType> ideaDocTypeVector) throws Exception {
		try {
			String sLoanNumber = hashmap.get("LoanNumber");
			String sPriority = hashmap.get("Priority");
			String sClientName = hashmap.get("ClientName");
			String sBatchType  = hashmap.get("BatchType");
			String sBatchId = hashmap.get("BatchID");
			String filePath = reportGenerator.getReportPath() + File.separator + "0" + sPriority + "_" + sLoanNumber + "~" + sClientName + "~"
																+ sBatchType + "_" + sBatchId + ".xml";
			reportGenerator.logMessage("Verifying the batch XML file with expected batch details and document types " + new File(filePath).getName() , Status.INFO);
			File fXmlFile = new File(filePath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();

			NodeList nodeList = doc.getElementsByTagName("Batch");
			this.verifyBatchInfo(nodeList, hashmap, reportGenerator);
			
			nodeList = doc.getElementsByTagName("ImageFile");
			this.verifyImageFile(nodeList, hashmap, reportGenerator, ideaDocTypeVector);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void verifyImageFile(NodeList nodeList, Map<String,String> hashmap, ReportGenerator reportGenerator, Vector<IdeaDocumentType> ideaDocTypeVector) throws Exception {
		
		for(int index=0; index<ideaDocTypeVector.size(); index++) {
			IdeaDocumentType documentType =  ideaDocTypeVector.get(index);
			String documentTypeName = documentType.getDocumentTypeName();
			String documentPageNumber = documentType.getDocumentPageNumber();
			if("0".equals(documentPageNumber)) {
				boolean isDocumentFound = false;
				for (int temp = 0; temp < nodeList.getLength(); temp++) {
					Node nNode = nodeList.item(temp);
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element) nNode;
						String documentName = eElement.getElementsByTagName("DocumentName").item(0).getTextContent();
						if(documentName.equals(documentTypeName)) {
							Assert.assertTrue(true);
							isDocumentFound = true;
							reportGenerator.logMessage(documentName + " is indexed and classified correctly", Status.PASS);
							break;
						}
					}
				}
				if(!isDocumentFound) {
					Assert.fail("Document type "+ documentTypeName + "is not found in the batch export XML. Please check & troubleshoot export operation");
				}
			}
		}
		reportGenerator.logMessage("Image file is verified from the XML file", Status.PASS);
	}
	
	public void verifyBatchInfo(NodeList nodeList, Map<String,String> hashmap, ReportGenerator reportGenerator) throws Exception {
		
		for (int temp = 0; temp < nodeList.getLength(); temp++) {
			Node nNode = nodeList.item(temp);
			reportGenerator.logMessage("\nCurrent Element :" + nNode.getNodeName(),Status.INFO);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				Assert.assertEquals(eElement.getElementsByTagName("BatchName").item(0).getTextContent(),
											hashmap.get("LoanNumber") + "~" + hashmap.get("ClientName") + "~" +
											hashmap.get("BatchType") + "_" + hashmap.get("BatchID"));
				reportGenerator.logMessage("The Batch Name is displayed as ["
						+ eElement.getElementsByTagName("BatchName").item(0).getTextContent() + "] in the XML file",
						Status.PASS);
				Assert.assertEquals(eElement.getElementsByTagName("BatchClient").item(0).getTextContent(),
						hashmap.get("ClientName"));
				reportGenerator.logMessage("The BatchClient is displayed as ["
						+ eElement.getElementsByTagName("BatchClient").item(0).getTextContent()
						+ "] in the XML file", Status.PASS);
				Assert.assertEquals(eElement.getElementsByTagName("BatchLoanNumber").item(0).getTextContent(),
						hashmap.get("LoanNumber"));
				reportGenerator.logMessage("The BatchLoanNumber is displayed as ["
						+ eElement.getElementsByTagName("BatchLoanNumber").item(0).getTextContent()
						+ "] in the XML file", Status.PASS);
				Assert.assertEquals(eElement.getElementsByTagName("BatchType").item(0).getTextContent(),
						hashmap.get("BatchType"));
				reportGenerator.logMessage("The BatchType is displayed as ["
						+ eElement.getElementsByTagName("BatchType").item(0).getTextContent() + "] in the XML file",
						Status.PASS);
				Assert.assertEquals(eElement.getElementsByTagName("BatchProduct").item(0).getTextContent(),
						hashmap.get("ProductName"));
				reportGenerator.logMessage("The BatchProduct is displayed as ["
						+ eElement.getElementsByTagName("BatchProduct").item(0).getTextContent()
						+ "] in the XML file", Status.PASS);
				Assert.assertEquals(eElement.getElementsByTagName("BatchProductId").item(0).getTextContent(),
						hashmap.get("ProductID"));
				reportGenerator.logMessage("The BatchProductId is displayed as ["
						+ eElement.getElementsByTagName("BatchProductId").item(0).getTextContent()
						+ "] in the XML file", Status.PASS);
				Assert.assertEquals(eElement.getElementsByTagName("BatchClientId").item(0).getTextContent(),
						hashmap.get("ClientID"));
				reportGenerator.logMessage("The BatchClientId is displayed as ["
						+ eElement.getElementsByTagName("BatchClientId").item(0).getTextContent()
						+ "] in the XML file", Status.PASS);
			}
		}
	}
	
	/**
	 * validateXMLFile method used to validate the details present in the xml file
	 * 
	 * @param hashmap
	 * @throws Exception
	 */
	public Set<String> validatePayLoadXML(Map<String, String> hashmap, ReportGenerator reportGenerator, String sPayLoad)
			throws Exception {
		Set<String> values = new HashSet<String>();
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(new InputSource(new StringReader(sPayLoad)));
			doc.getDocumentElement().normalize();
			NodeList nodeList = doc.getElementsByTagName("Document");
			for (int temp = 0; temp < nodeList.getLength(); temp++) {
				Node nNode = nodeList.item(temp);
				reportGenerator.logMessage("\nCurrent Element :" + nNode.getNodeName(), Status.INFO);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					values.add(eElement.getAttribute("IsFailedSeparationConfidenceTest"));
					values.add(eElement.getAttribute("IsFailedClassificationConfidenceTest"));
					reportGenerator.logMessage(
							"The values displayed are " + eElement.getAttribute("IsFailedSeparationConfidenceTest")
									+ "and" + eElement.getAttribute("IsFailedClassificationConfidenceTest")
									+ "in the XML file for " + nNode.getNodeName(),
							Status.PASS);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return values;
	}

	public Boolean validateDetailsOfXMLPayLoad(HashMap<String, String> hashmap, ReportGenerator reportGenerator,
			String sPayLoad) throws Exception {
		boolean status = false;
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(new InputSource(new StringReader(sPayLoad)));
			doc.getDocumentElement().normalize();
			NodeList nodeList = doc.getElementsByTagName("Document");
			MySQLDBConnection dbConnection = new MySQLDBConnection();
			DecimalFormat df = new DecimalFormat("#.####");
			DecimalFormat dFormat = new DecimalFormat("#.###");
			df.setRoundingMode(RoundingMode.CEILING);
			for (int temp = 0; temp < nodeList.getLength(); temp++) {
				Node nNode = nodeList.item(temp);
				reportGenerator.logMessage("\nCurrent Element :" + nNode.getNodeName(), Status.INFO);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;

					if (eElement.getAttribute("DocTypeId").equals(hashmap.get("DocumentTypeID"))) {
						dbConnection.checkDetailsOfEachDocument(hashmap.get("BatchID"), hashmap,
								eElement.getAttribute("DocTypeId"));

						Assert.assertEquals(eElement.getAttribute("ConfidenceThreshold"),
								hashmap.get("CONFIDENCE_THRESHOLD"));
						reportGenerator.logMessage("The ConfidenceThreshold Value is displayed as "
								+ eElement.getAttribute("ConfidenceThreshold"), Status.PASS);
						Assert.assertEquals(eElement.getAttribute("ConfidenceWeighting"),
								hashmap.get("CONFIDENCE_WEIGHTING"));
						reportGenerator.logMessage("The ConfidenceWeighting Value is displayed as "
								+ eElement.getAttribute("ConfidenceWeighting"), Status.PASS);
						Double relativeConfidence = Double.parseDouble(eElement.getAttribute("RelativeConfidence"));
						Double dRELATIVE_CONFIDENCE = Double.parseDouble(hashmap.get("RELATIVE_CONFIDENCE"));
						Assert.assertEquals(df.format(relativeConfidence), df.format(dRELATIVE_CONFIDENCE));
						reportGenerator.logMessage("The RelativeConfidence Value is displayed as "
								+ eElement.getAttribute("RelativeConfidence"), Status.PASS);
						Assert.assertEquals(eElement.getAttribute("SeparationConfidence"),
								hashmap.get("SEPARATION_CONFIDENCE"));
						reportGenerator.logMessage("The SeparationConfidence Value is displayed as "
								+ eElement.getAttribute("SeparationConfidence"), Status.PASS);
						Double dCalculatedConfidence = Double
								.parseDouble(eElement.getAttribute("CalculatedConfidence"));
						Double dCALCULATED_CONFIDENCE = Double.parseDouble(hashmap.get("CALCULATED_CONFIDENCE"));
						Assert.assertEquals(dFormat.format(dCalculatedConfidence),
								dFormat.format(dCALCULATED_CONFIDENCE));
						reportGenerator.logMessage("The CalculatedConfidence Value is displayed as "
								+ eElement.getAttribute("CalculatedConfidence"), Status.PASS);
						status = true;
						break;
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}
}
