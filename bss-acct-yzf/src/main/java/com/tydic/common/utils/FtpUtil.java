package com.tydic.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FtpUtil{  
	private static Logger log = LoggerFactory.getLogger(FtpUtil.class);
	/**
	 * ftp链接服务器
	 * @param url
	 * @param port
	 * @param username
	 * @param password
	 * @return
	 */
	public static FTPClient connectFTP(String host, int port, String username, String password) {  
        FTPClient ftp = null;  
        try {  
            ftp=new FTPClient();
            ftp.connect(host, port);   
            ftp.login(username, password);
            log.info("connectFTP success!!");
            ftp.setControlEncoding("UTF-8");
            //默认是此模式PASSIVE_LOCAL_DATA_CONNECTION_MODE,服务器打开客户端连接的数据端口进行数据传输
            //必须加上此2句，解决上传到服务器文件失败,
            ftp.enterLocalPassiveMode();
            ftp.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
        } catch (Exception e) { 
        	log.info("connectFTP--catch--Exception"+e.getMessage());
            e.printStackTrace();  
        }  
        return ftp;  
    } 
	
	/***
	 * 上传文件
	 * @param localFile
	 * @param ftpFile
	 * @param ftp
	 */
	public static boolean upload(String localPath, String ftpPath,String fileName, FTPClient ftp) {  
		boolean flag = false;
        try {  
        	File tempFtpFile = new File(localPath+fileName);  
        	if(tempFtpFile.isFile()){
        		InputStream  is = new FileInputStream(localPath+fileName); 
        		 flag = ftp.storeFile(ftpPath, is); 
        		 is.close();
        		 log.info("upload success!!");
        		 flag = true;
        	} else{
        		log.info("upload fail!!! 上传目标文件不存在");
        	}
        } catch (Exception e) {  
        	log.info("upload--catch--Exception"+e.getMessage());
            e.printStackTrace();  
        } 
        return flag;
    } 
	
	/**
	 * 下载文件
	 * @param localFile
	 * @param ftpFile
	 * @param ftp
	 */
	public static void download(String localPath, String ftpPath,String fileName, FTPClient ftp) {  
        try {  
        	File tempFtpFile = new File(ftpPath+fileName);  
        	
        	if(!tempFtpFile.isFile()){
        		String remoteFileName = ftpPath+fileName; 
        		
        		File file = new File(localPath);
    			if(!file.isDirectory()){
    				file.mkdirs();
    			}
    			File tempFile = new File(localPath+fileName); 
    			if(!tempFile.isFile()){
    				tempFile.createNewFile();
    			}
        		OutputStream is = new FileOutputStream(tempFile); 
        		ftp.retrieveFile(remoteFileName, is); 
        		is.flush();
        		is.close();
        		log.info("download success!!");
        	} else{
        		log.info("download fail!!! 下载目标文件不存在");
        	}
        } catch (Exception e) {  
        	log.info("upload--catch--Exception"+e.getMessage());
            e.printStackTrace();  
        } 
    }  
	
	public static void disconnectFTP(FTPClient ftp) {  
		if (ftp.isConnected()) {
            try {
            	ftp.disconnect();
                log.info("disconnect success!!!");
            } catch (Exception e) {
            	log.error("disconnectFTP--catch--Exception"+e.getMessage());
                e.printStackTrace();  
            }
		}
	}
	
	
	public static void main(String[] args) {
		String host="133.64.39.112";
		int port=22;
		String username="yzfftp";
		String password="Yzf@20!*";
		FTPClient ftp=FtpUtil.connectFTP(host, port, username, password);
		String localPath="/app/bss30_billdev/pay/file/";
		String ftpPath="/crmhome/yzfftp";
		String fileName="test111.txt";
		FtpUtil.download(localPath, ftpPath,fileName, ftp);
//		FtpUtil.upload(localPath, ftpPath+fileName,fileName, ftp);
		FtpUtil.disconnectFTP(ftp);
	}
}