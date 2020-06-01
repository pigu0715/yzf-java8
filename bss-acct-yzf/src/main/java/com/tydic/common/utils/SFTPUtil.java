package com.tydic.common.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Vector;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class SFTPUtil {
	private ChannelSftp sftp;
	private String userName; // FTP 登录用户名
	private String password; // FTP
	private String keyFilePath;// 私钥文件的路径
	private String host; // FTP 服务器地址IP地址
	private int port; // FTP 端口
	private Session sshSession;

	/**
	 * 构造基于密码认证的sftp对象
	 * 
	 * @param userName
	 *            用户名
	 * @param password
	 *            登陆密码
	 * @param host
	 *            服务器ip
	 * @param port
	 *            fwq端口
	 */
	public SFTPUtil(String userName, String password, String host, int port) {
		super();
		this.userName = userName;
		this.password = password;
		this.host = host;
		this.port = port;
	}

	/**
	 * 构造基于秘钥认证的sftp对象
	 * 
	 * @param userName
	 *            用户名
	 * @param host
	 *            服务器ip
	 * @param port
	 *            fwq端口
	 * @param keyFilePath
	 *            私钥文件路径
	 */
	public SFTPUtil(String userName, String host, int port, String keyFilePath) {
		super();
		this.userName = userName;
		this.host = host;
		this.port = port;
		this.keyFilePath = keyFilePath;
	}

	/**
	 * 连接sftp服务器
	 * 
	 * @throws Exception
	 */
	public void connect() throws Exception {
		try {
			JSch jsch = new JSch();
			if (keyFilePath != null) {
				jsch.addIdentity(keyFilePath);// 设置私钥
			}

			sshSession = jsch.getSession(userName, host, port);
			if (password != null) {
				sshSession.setPassword(password);
			}
			Properties sshConfig = new Properties();
			sshConfig.put("StrictHostKeyChecking", "no");
			sshSession.setConfig(sshConfig);
			sshSession.connect();
			Channel channel = sshSession.openChannel("sftp");
			channel.connect();

			sftp = (ChannelSftp) channel;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 关闭连接 server
	 */
	public void disconnect() {
		if (sftp != null) {
			if (sftp.isConnected()) {
				sftp.disconnect();
				sshSession.disconnect();
			} else if (sftp.isClosed()) {
				
			}
		}
	}
	
	public void exec(String command) {
        ChannelExec channelExec = null;
        try {
            Channel channel = sshSession.openChannel("exec");
            channelExec = (ChannelExec) channel;
            channelExec.setCommand(command);
            channelExec.connect();
        } catch (Exception e) {
            e.printStackTrace();
            channelExec = null;
        }finally {
            channelExec.disconnect();
        }
    }
	/**
	 * 将输入流的数据上传到sftp作为文件
	 * 
	 * @param directory
	 *            上传到该目录
	 * @param sftpFileName
	 *            sftp端文件名
	 * @param in
	 *            输入流
	 * @throws Exception
	 */
	public void upload(String directory, String sftpFileName, InputStream input) throws Exception {
		try {

			try {// 如果cd报异常，说明目录不存在，就创建目录
				sftp.cd(directory);
			} catch (Exception e) {
				sftp.mkdir(directory);
				sftp.cd(directory);
			}
			sftp.put(input, sftpFileName);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 上传单个文件
	 * 
	 * @param directory
	 *            上传到sftp目录
	 * @param uploadFile
	 *            要上传的文件,包括路径
	 * @throws Exception
	 */
	public void upload(String directory, String uploadFile) throws Exception {
		File file = new File(uploadFile);
		upload(directory, file.getName(), new FileInputStream(file));
	}

	/**
	 * 将byte[]上传到sftp，作为文件。注意:从String生成byte[]是，要指定字符集。
	 * 
	 * @param directory
	 *            上传到sftp目录
	 * @param sftpFileName
	 *            文件在sftp端的命名
	 * @param byteArr
	 *            要上传的字节数组
	 * @throws Exception
	 */
	public void upload(String directory, String sftpFileName, byte[] byteArr) throws Exception {
		upload(directory, sftpFileName, new ByteArrayInputStream(byteArr));
	}

	/**
	 * 将字符串按照指定的字符编码上传到sftp
	 * 
	 * @param directory
	 *            上传到sftp目录
	 * @param sftpFileName
	 *            文件在sftp端的命名
	 * @param dataStr
	 *            待上传的数据
	 * @param charsetName
	 *            sftp上的文件，按该字符编码保存
	 * @throws Exception
	 */
	public void upload(String directory, String sftpFileName, String dataStr, String charsetName) throws Exception {
		upload(directory, sftpFileName, new ByteArrayInputStream(dataStr.getBytes(charsetName)));

	}

	/**
	 * 下载文件
	 * 
	 * @param directory
	 *            下载目录
	 * @param downloadFile
	 *            下载的文件
	 * @param saveFile
	 *            存在本地的路径
	 * @throws Exception
	 */
	public void download(String directory, String fileName, String saveFile) throws Exception {
		try {
			if (directory != null && !"".equals(directory)) {
				sftp.cd(directory);
			}
			File file = new File(saveFile);
			if(!file.isDirectory()){
				file.mkdirs();
			}
			File tempFile = new File(saveFile+fileName);
			if(!tempFile.isFile()){
				tempFile.createNewFile();
			}
			sftp.get(fileName, new FileOutputStream(tempFile));
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * 下载文件
	 * 
	 * @param directory
	 *            下载目录
	 * @param downloadFile
	 *            下载的文件
	 * @param saveFile
	 *            存在本地的路径
	 * @throws Exception
	 */
	public void copy(String directory, String fileName, String toDirectory) throws Exception {
		try {
			try {
	            sftp.mkdir(toDirectory);
	        } catch (SftpException e) {
	        }
			if (directory != null && !"".equals(directory)) {
				sftp.cd(directory);
			}
			String command = " cp "+fileName +" "+ toDirectory;
			this.exec(command);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 下载文件
	 * 
	 * @param directory
	 *            下载目录
	 * @param downloadFile
	 *            下载的文件名
	 * @return 字节数组
	 * @throws Exception
	 */
	public byte[] download(String directory, String downloadFile) throws Exception {
		byte[] fileData = null;
		try {
			if (directory != null && !"".equals(directory)) {
				sftp.cd(directory);
			}
			InputStream is = sftp.get(downloadFile);

			fileData = new byte[is.available()];
			is.read(fileData);

		} catch (SftpException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		}
		return fileData;
	}

	/**
	 * 删除文件
	 * 
	 * @param directory
	 *            要删除文件所在目录
	 * @param deleteFile
	 *            要删除的文件
	 * @throws Exception
	 */
	public void delete(String directory, String deleteFile) throws Exception {
		try {
			sftp.cd(directory);
			sftp.rm(deleteFile);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 列出目录下的文件
	 * 
	 * @param directory
	 *            要列出的目录
	 * @param sftp
	 * @return
	 * @throws SftpException
	 */
	public Vector<?> listFiles(String directory) throws SftpException {
		return sftp.ls(directory);
	}
	
	public static void main(String[] args) {
		int port = 22;
//		String host = "133.64.39.112";
//		String userName = "yzfftp";
//		String password = "Yzf@20!*";
//		String directory = "/crmhome/yzfftp";
//		String backDirectory = "/crmhome/yzfftp/paybak";
//		String downloadFile="/app/bss30_billdev/pay/file/";
//		String fileName="test1111.txt";
		
		String host = "133.64.173.88";
		String userName = "sftp_acct";
		String password = "xZuH4@Xh";
		String directory = "/app/sftp_acct/982095089";
		String backDirectory = "/app/sftp_acct/982095089/paybak";
		String downloadFile="/app/bss30_billdev/pay/file/";
		String fileName="test1111.txt";
		
		
		SFTPUtil sftpUtil = new SFTPUtil(userName, password, host, port);
		try {
			sftpUtil.connect();
			sftpUtil.copy(directory, fileName, backDirectory);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		try {
//			sftpUtil.download(directory,  fileName, downloadFile);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		sftpUtil.disconnect();
		 
	}

}