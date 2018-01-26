package com.kanq.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.stereotype.Component;

@Component
public class FtpUtils {
	/**
	 * @author xh 测试成功 可以下载中文文件
	 * @param url
	 * @param port
	 * @param username
	 * @param password
	 * @param remotePath
	 * @param fileName
	 * @param localPath
	 * @return
	 */
	public static boolean downFtpFile(String url, int port, String username,  
           String password, String remotePath, String fileName,  
           String localPath) {  
       boolean success = false;  
       FTPClient ftp = new FTPClient();  
       try {  
           int reply;  
           ftp.connect(url, port);  
           // 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器  
           ftp.login(username, password);// 登录  
           reply = ftp.getReplyCode();  
           if (!FTPReply.isPositiveCompletion(reply)) {  
               ftp.disconnect();  
               return success;  
           }  
           ftp.changeWorkingDirectory(remotePath);// 转移到FTP服务器目录  
           FTPFile[] fs = ftp.listFiles();  
           for (FTPFile ff : fs) {  
               String fname = new String(ff.getName().getBytes("iso-8859-1"),  
                       "gbk");  
               if (fname.equals(fileName)) {  
                   File localFile = new File(localPath+fname);  
                   OutputStream is = new FileOutputStream(localFile);  
                   success=ftp.retrieveFile(ff.getName(), is);  
                   is.close();  
                   break;  
               }  
           }  
           ftp.logout();  
        
       } catch (IOException e) {  
           e.printStackTrace();  
       } finally {  
           if (ftp.isConnected()) {  
               try {  
                   ftp.disconnect();  
               } catch (IOException ioe) {  
               }  
           }  
       }  
       return success;  
   }  

	 /**
	  * @author xh 测试成功 可以上传中文文件
	  * @param url
	  * @param port
	  * @param username
	  * @param password
	  * @param path
	  * @param filename
	  * @param input
	  * @return
	  */
	public static boolean uploadFile(String url,int port,String username, String password, String path, String filename, InputStream input) {
		boolean success = false;
		FTPClient ftp = new FTPClient();
		   try {
			    int reply;
			    ftp.connect(url, port);//连接FTP服务器
				//如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
				ftp.login(username, password);//登录
				reply = ftp.getReplyCode();
				
				if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return success;
		               }
				ftp.changeWorkingDirectory(path);
				ftp.setControlEncoding("ISO-8859-1");
				ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
				   ftp.storeFile(new String(filename.getBytes("GBK"),"ISO-8859-1"), input); 
					input.close();
					ftp.logout();
					success = true;
			
				
		     } catch (IOException e) {
				e.printStackTrace();
				} finally {
						if (ftp.isConnected()) {
						try {
					ftp.disconnect();
					} catch (IOException ioe) {}
		}
	
				}
		   return success;
	}
	
	 /** 
    * 删除文件的名称filename
    * @return 
    */  
   public  static boolean ftpDelete(String url,int port,String username, String password, String path, String filename) {
   	boolean success = false;  
       FTPClient ftp = new FTPClient();  
       try {  
           int reply;  
           ftp.connect(url, port);  
           // 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器  
           ftp.login(username, password);// 登录  
           reply = ftp.getReplyCode();  
           if (!FTPReply.isPositiveCompletion(reply)) {  
               ftp.disconnect();  
               return success;  
           }  
           ftp.changeWorkingDirectory(path);// 转移到FTP服务器目录 
       	ftp.setControlEncoding("ISO-8859-1");
          // FTPFile[] fs = ftp.listFiles();  
               	 success = ftp.deleteFile(new String(filename.getBytes("GBK"),"ISO-8859-1"));  
       }  catch (IOException e) {
		       e.printStackTrace();
		} finally {
				if (ftp.isConnected()) {
				try {
			ftp.disconnect();
			} catch (IOException ioe) {}
                  }
		}
       return success;
   }
}
