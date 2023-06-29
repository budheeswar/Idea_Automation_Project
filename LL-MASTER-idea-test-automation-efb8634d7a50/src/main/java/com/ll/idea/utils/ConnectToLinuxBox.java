package com.ll.idea.utils;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.Reporter;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.ll.idea.reporting.ReportGenerator;

import groovy.transform.Undefined.EXCEPTION;

public class ConnectToLinuxBox {

	private ReportGenerator reportGenerator = null;
	MySQLDBConnection dbConnection = new MySQLDBConnection();

	public ConnectToLinuxBox(WebDriver webDriver, ReportGenerator reportGenerator) {
		this.reportGenerator = reportGenerator;

	}

	

	/**
	 * transferLoanInToEFS is a method to transfer the loan file from local to EFS server
	 * @param host represents host name of EFS server
	 * @param user represents user name of EFS server
	 * @param remoteDir represents the path where the loan needs to be uploaded
	 * @param privatekeypath represents ansible key path
	 * @param sourcepath represents the path of the loan that needs to be uploaded
	 * @throws Exception
	 */
	public void transferLoanInToEFS(String host, String user, String remoteDir, String privatekeypath,
			String sourcepath) throws Exception {
		try {
		Object[] objects = this.getJSchConnection(host, user, privatekeypath);
		ChannelSftp channelSftp=(ChannelSftp)objects[0]; 
		Session session=(Session)objects[1];
		
			try {
				SftpATTRS attrs = channelSftp.lstat(remoteDir);
				Reporter.log("|attrs|" + attrs);
			} catch (Exception e1) {
				Reporter.log("remoteDir conect :" + remoteDir);
				channelSftp.mkdir(remoteDir);
			}

			Reporter.log("sourcepath:" + sourcepath+ ":remoteDir" +remoteDir );
			channelSftp.put(sourcepath, remoteDir);
			channelSftp.exit();
			CommonUtils.sleepForAWhile(10000);
			session.disconnect();
		} catch (EXCEPTION e) {
			e.printStackTrace();
			throw e;
		}

	}

	/**
	 * downloadXmlFileFromEFS is a method used to download xml file from EFS server to local
	 * @param host represents host name of EFS server
	 * @param user represents user name of EFS server
	 * @param privatekeypath represents ansible key path
	 * @param sourceDir represents the path of the of the xml file in EFS server that needs to be downloaded
	 * @param destDir represents the path where the xml file need to be downloaded
	 * @param sPDFDir represents the path of pdf files that needs to be downloaded from EFS server
	 * @throws Exception
	 */
	public void downloadXmlFileFromEFS(String host, String user, String privatekeypath, String sourceDir,String destDir, String sPDFDir)
			throws Exception {
		try {
			Object[] objects = this.getJSchConnection(host, user, privatekeypath);
			ChannelSftp channelSftp=(ChannelSftp)objects[0]; 
			Session session=(Session)objects[1];
			Reporter.log("user :" + user);
			Reporter.log("host :" + host);
			Reporter.log("privatekeypath :" + privatekeypath);
			Reporter.log("xmlDirectoryPath :" + sourceDir);
			//String destPath = System.getProperty("user.dir") + File.separator + "downloadedfiles";
			channelSftp.get(sourceDir, destDir);
			channelSftp.get(sPDFDir, destDir);
			channelSftp.exit();
			session.disconnect();
		} catch (EXCEPTION e) {
			e.printStackTrace();
		}

	}

	/**
	 * copyAndRenameFile is a method used to Rename and copy the file from one path to panother path
	 * @param hashmap
	 * @param sDestinationPath represents the path where we want to paste it
	 * hashmap.put("LoanNumber", sLoanNumber); statement helps in adding the loan number to hashmap data
	 * @throws Exception
	 */
	public String copyAndRenameFile(HashMap<String, String> hashmap,String sDestinationPath) throws Exception {
		File newFile = null;
		String destPath = null;
		try {
			String sLoanNumber = CommonUtils.getUniqueText();			
			hashmap.put("LoanNumber", sLoanNumber);			
			String srcFileName = System.getProperty("user.dir") + File.separator
					+ EnvironmentPropertyLoader.getPropertyByName("sourcepath")+File.separator+hashmap.get("LoanFileName");
			File srcFile = new File(srcFileName);
			newFile = new File(sDestinationPath + File.separator + sLoanNumber + ".pdf"); 
			FileUtils.copyFile(srcFile, newFile);
			destPath = newFile.getAbsolutePath();
			dbConnection.insertLoanRecord(sLoanNumber, hashmap.get("ClientID"), hashmap.get("ProductID"));
			} catch (Exception e) {
			e.printStackTrace();
		}
		return destPath;
	}
	/**
	 * executeCommand is a method used to restart Auto Importer- from EFS server
	 * @param host represents host name of EFS server
	 * @param user represents user name of EFS server
	 * @param command represents the list of commands
	 * @param privatekeypath represents ansible key path
	 * @throws Exception
	 */
	public void executeCommand(HashMap<String, String> hashmap, String host, String user,String command, String privatekeypath) throws Exception {
			try{
		    	JSch jsch = new JSch();
		    	jsch.addIdentity(privatekeypath);	    
		        Session session = jsch.getSession(user, host, 22);
		        session.setConfig("PreferredAuthentications", "publickey,keyboard-interactive,password");
		        java.util.Properties config = new java.util.Properties(); 
		        config.put("StrictHostKeyChecking", "no");
		        session.setConfig(config);
		    	session.connect();
		    	System.out.println("Connected");
		    	Channel channel=session.openChannel("exec");
		        ((ChannelExec)channel).setCommand(command);
		        channel.setInputStream(null);
		        ((ChannelExec)channel).setErrStream(System.err);	        
		        InputStream in=channel.getInputStream();
		        channel.connect();
		        String cmdOutput = null;
		        byte[] tmp=new byte[1024];
		        CommonUtils.sleepForAWhile();
		        while(true){
		          while(in.available()>0){
		            int i=in.read(tmp, 0, 1024);
		            if(i<0)break;
		            cmdOutput = new String(tmp, 0, i);
		            System.out.print(cmdOutput);
		          }	 
//		          System.out.println(str);
		          if(channel.isClosed()){
		            System.out.println("exit-status: "+channel.getExitStatus());
		            break;
		          }
		        }  

				try {
					if (cmdOutput.contains(hashmap.get("CmdMessage"))) {
						Reporter.log("The Idea Auto Import.jar is up and running");
					}
				} catch (NullPointerException e) {
					Assert.fail("Auto Import scheduler is not running");
				}
		        channel.disconnect();
		        session.disconnect();
		        System.out.println("DONE");
		        
	} catch (EXCEPTION e) {
		e.printStackTrace();
		throw e;
	}
	}
	public Object[] getJSchConnection(String host, String user, String privatekeypath) throws Exception {
		ChannelSftp channelSftp = null;
		Session session = null;
		try {
			JSch jsch = new JSch();
			jsch.addIdentity(privatekeypath);
			session = jsch.getSession(user, host, 22);
			session.setConfig("PreferredAuthentications", "publickey,keyboard-interactive,password");
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect();
			channelSftp = (ChannelSftp) session.openChannel("sftp");
			channelSftp.connect();
		} catch (EXCEPTION e) {
			e.printStackTrace();
			throw e;
		}
		return new Object[]{channelSftp, session};
	}
	/**
	 * copyAndRenameFile is a method used to Rename and copy the file from one path to panother path
	 * @param hashmap
	 * @param sDestinationPath represents the path where we want to paste it
	 * hashmap.put("LoanNumber", sLoanNumber); statement helps in adding the loan number to hashmap data
	 * @throws Exception
	 */
	public String[] copyAndRenameMultipleFiles(HashMap<String, String> hashmap,String sDestinationPath) throws Exception {
		File newFile = null;
		String destPath = null;
		String sLoanNumber = null;
		try {
			sLoanNumber = CommonUtils.getUniqueText();
			hashmap.put("LoanNumber", sLoanNumber);
			String srcFileName = System.getProperty("user.dir") + File.separator
					+ EnvironmentPropertyLoader.getPropertyByName("sourcepath")+File.separator+hashmap.get("LoanFileName");
			File srcFile = new File(srcFileName);
			newFile = new File(sDestinationPath + File.separator + sLoanNumber + ".pdf"); 
			FileUtils.copyFile(srcFile, newFile);
			destPath = newFile.getAbsolutePath();
			} catch (Exception e) {
			e.printStackTrace();
		}
		return new String[] {sLoanNumber,destPath} ;
	}
	
	public void executeCommandTokillJavaProcess(HashMap<String, String> hashmap, String host, String user,
			String command, String privatekeypath) throws Exception {
		try {
			JSch jsch = new JSch();
			jsch.addIdentity(privatekeypath);
			Session session = jsch.getSession(user, host, 22);
			session.setConfig("PreferredAuthentications", "publickey,keyboard-interactive,password");
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect();
			System.out.println("Connected");
			Channel channel = session.openChannel("exec");
			((ChannelExec) channel).setCommand(command);
			channel.setInputStream(null);
			((ChannelExec) channel).setErrStream(System.err);
			InputStream in = channel.getInputStream();
			channel.connect();
			channel.disconnect();
			session.disconnect();
			System.out.println("DONE");

		} catch (EXCEPTION e) {
			e.printStackTrace();
			throw e;
		}
	}
}	
