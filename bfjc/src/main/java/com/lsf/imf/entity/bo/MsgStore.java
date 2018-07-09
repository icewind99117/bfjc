package com.lsf.imf.entity.bo;

import java.io.IOException;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lsf.imf.mq.Msg;
import com.lsf.imf.mq.Msg.MsgType;
import com.lsf.imf.util.Tools;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;


public class MsgStore {
	private int id;
	private String msgType;
	private String dataType;
	private String msgContent;
	private String sender;
	private String msgTime;
	private Timestamp receiveTime;
	private Timestamp parseTime;
	private int status;
	private String seqNum;	
	private String flightIdentity;
	private String flightDirection;
	private String flightRepeatCount;
	private String scheduledDate;
	private int afdsId;	
	private int removed;
	
	
	
	

	

	public MsgStore(int id, String msgType, String dataType, String msgContent, String sender, String msgTime,
			Timestamp receiveTime, Timestamp parseTime, int status, String seqNum, String flightIdentity,
			String flightDirection, String flightRepeatCount, String scheduledDate, int afdsId, int removed) {
		super();
		this.id = id;
		this.msgType = msgType;
		this.dataType = dataType;
		this.msgContent = msgContent;
		this.sender = sender;
		this.msgTime = msgTime;
		this.receiveTime = receiveTime;
		this.parseTime = parseTime;
		this.status = status;
		this.seqNum = seqNum;
		this.flightIdentity = flightIdentity;
		this.flightDirection = flightDirection;
		this.flightRepeatCount = flightRepeatCount;
		this.scheduledDate = scheduledDate;
		this.afdsId = afdsId;
		this.removed = removed;
	}

	public MsgStore(Msg msg,String text) {
		super();
		this.msgType = msg.getMsgType().toString();
		this.dataType = msg.getDataType();
		this.msgContent = text;
		this.sender = msg.getSender();
		this.msgTime = Tools.timeFormat(Tools.TIME_FORMAT,msg.getSendTime());
		this.receiveTime=new Timestamp(System.currentTimeMillis());
		if(msg.getMsgType()==MsgType.ACDMDSStatusResponse) {
			this.status=2; 	
		}else {
			this.status=0;
		}
		
		this.removed = 0;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getMsgContent() {
		return msgContent;
	}
	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getMsgTime() {
		return msgTime;
	}
	public void setMsgTime(String msgTime) {
		this.msgTime = msgTime;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	public Timestamp getReceiveTime() {
		return receiveTime;
	}
	


	public void setReceiveTime(Timestamp receiveTime) {
		this.receiveTime = receiveTime;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	public Timestamp getParseTime() {
		return parseTime;
	}

	public void setParseTime(Timestamp parseTime) {
		this.parseTime = parseTime;
	}

	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
	public int getAfdsId() {
		return afdsId;
	}

	public void setAfdsId(int afdsId) {
		this.afdsId = afdsId;
	}

	public int getRemoved() {
		return removed;
	}
	public void setRemoved(int removed) {
		this.removed = removed;
	}

	public String getFlightIdentity() {
		return flightIdentity;
	}

	public void setFlightIdentity(String flightIdentity) {
		this.flightIdentity = flightIdentity;
	}

	public String getFlightDirection() {
		return flightDirection;
	}

	public void setFlightDirection(String flightDirection) {
		this.flightDirection = flightDirection;
	}

	public String getFlightRepeatCount() {
		return flightRepeatCount;
	}

	public void setFlightRepeatCount(String flightRepeatCount) {
		this.flightRepeatCount = flightRepeatCount;
	}

	public String getScheduledDate() {
		return scheduledDate;
	}

	public void setScheduledDate(String scheduledDate) {
		this.scheduledDate = scheduledDate;
	}

	public String getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(String seqNum) {
		this.seqNum = seqNum;
	}


    

	
}
