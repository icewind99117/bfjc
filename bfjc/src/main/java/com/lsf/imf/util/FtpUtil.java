package com.lsf.imf.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FtpUtil {

	private static Logger log = LoggerFactory.getLogger(FtpUtil.class);

	
	private static FTPClient ftp;

	static {
		ftp=new FTPClient();
	}
	public static void buildConnect(String ip,int port,String user,String pwd) {
		log.info("Ftp connecting...");
		
		try {
			if(ftp.isConnected()) {
				ftp.disconnect();
			}
			ftp.connect(ip, Integer.valueOf(port));

			ftp.login(user, pwd);

			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);

			int reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
			}

			log.info("Ftp connected...");
		} catch (Exception e) {
			log.info("Ftp connect fail!");
		}
		
	}

	public static boolean uploadFile( String path, String filename, InputStream input) {  
		log.info("Ftp Upload "+filename+"("+path+") starting...");
	    if(!ftp.isConnected()) {
	    	
	    	return false;
	    }
	    boolean success=false;
	    try {  

	        boolean suc=  ftp.changeWorkingDirectory(path);  
	        
	        if(!suc) {  
                ftp.makeDirectory(path);  
                ftp.changeWorkingDirectory(path);  
                  
            }  
	        ftp.storeFile(filename, input);           
	          
	        input.close();  
	        ftp.logout();  
			log.info("Ftp Upload "+filename+"("+path+") success");
	        success= true;  
	    } catch (IOException e) {  
	        log.error("Error when upload file:"+filename+"("+path+")",e); 
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
	
	
	 

	public static void main(String[] args) throws Exception {


	}

}
