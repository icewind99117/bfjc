package com.lsf.imf.ftp;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.lsf.imf.entity.bo.AfdsInfo;
import com.lsf.imf.mq.ACDMDSClient;
import com.lsf.imf.service.MsgService;
import com.lsf.imf.util.ExcelUtil;
import com.lsf.imf.util.FtpUtil;
import com.lsf.imf.util.Tools;

@Component
public class FtpUploadClient {
	private static Logger log = LoggerFactory.getLogger(FtpUploadClient.class);
	
	@Autowired
	MsgService msgService;
	
	@Value("${ftp.ip}")
	private String ip;
	
	@Value("${ftp.port}")
	private int port;
	
	@Value("${ftp.user}")
	private String user;
	
	@Value("${ftp.pwd}")
	private String pwd;
	
	public String  getInitParams() {
		return "ftp-ip:"+ip;
	}
	public boolean uploadAfdsInfoOf3Daysago() {
		
		String day=Tools.getDay(-3);
		
		List<HashMap<String,String>> list=msgService.findAfdsInfoByScheduledDate(day);
		
		Workbook excel=ExcelUtil.createWorkBook(list);
		
		InputStream is=ExcelUtil.getExcelInputStream(excel);
		
		FtpUtil.buildConnect(ip, port, user, pwd);

		return FtpUtil.uploadFile(day,"AFDS_"+day+".xls",is);
	}
	
}
	

