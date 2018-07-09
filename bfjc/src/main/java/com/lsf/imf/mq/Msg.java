package com.lsf.imf.mq;

import java.io.IOException;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lsf.imf.util.Tools;


import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;


public class Msg {
    public enum MsgType {
        ACDMDSStatusRequest, ACDMDSRequest, ACDMDSStatusResponse, ACDMDSResponse;
    }

    private static final String XML_ENCODING = "utf-8";

	private static Logger log = LoggerFactory.getLogger(Msg.class);

    protected String sender;
    protected Date sendTime;
    protected String receiver;
    protected MsgType msgType;
    protected long seqNum;
    protected String status;
    protected String dataType;
    protected String data;
    
    protected Element dataElement;


    public String getSender() {
        return sender;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public String getReceiver() {
        return receiver;
    }

    public MsgType getMsgType() {
        return msgType;
    }

    public long getSeqNum() {
        return seqNum;
    }

    public String getStatus() {
        return status;
    }
    
    public String getDataType() {
        return dataType;
    }



    public String getData() {
        return data;
    }
    
    public Element getDataElement() {
        return dataElement;
    }
    
	public static Msg getResponseMsg(String text) {
        Msg ret = new Msg();
        try {
            Document doc = DocumentHelper.parseText(text);
            doc.setXMLEncoding(XML_ENCODING);
            Element root = doc.getRootElement();
            if (root.getName().equals("Root")) {
                Element header = root.element("Header");
                ret.sender = header.element("Sender").getText();
                ret.sendTime = Tools.timeParse(Tools.TIME_FORMAT,header.element("SendTime").getText());
                ret.receiver = header.element("Receiver").getText();
                ret.msgType = MsgType.valueOf(header.element("MsgType").getText());
                ret.sender = header.element("Sender").getText();
                Element body = root.element("Body");
                ret.seqNum = Long.parseLong(body.element("SeqNum").getText());
                ret.status = body.element("Status").getText();
                if(ret.msgType==MsgType.ACDMDSResponse) {
	                Element eDataType = body.element("DataType");
	                if (eDataType != null) ret.dataType = eDataType.getText();

	                Element eData = body.element("Data");
	                if (eData != null){ 
	                	ret.dataElement=eData;
	
	                }
                }
                
            } else {
                log.error("未识别的消息");
                return null;
            }
        } catch (Exception e) {
            log.error("Error when format responseMsg",e);
            return null;
        }
        return ret;
    }

	private String buildXml() {
        return getDocument().asXML();
    }

    private String buildNonEscapeXml() {
        Document document = getDocument();
        StringWriter stringWriter = new StringWriter();
        XMLWriter xmlWriter = new XMLWriter(stringWriter);
        try {
            xmlWriter.setEscapeText(false);
            xmlWriter.write(document);
        } catch (IOException e) {
            log.error("Error when xmlWriter write",e);
        } finally {
            if (xmlWriter != null) try {
                xmlWriter.close();
            } catch (IOException e) {
                log.error("Error when close xmlWriter",e);
            }
        }
        return stringWriter.toString();
    }

    private Document getDocument() {
        Document doc = DocumentHelper.createDocument();
        doc.setXMLEncoding(XML_ENCODING);
        Element root = doc.addElement("Root");
        Element header = root.addElement("Header");
        header.addElement("Sender").setText(sender);
        header.addElement("SendTime").setText(Tools.timeFormat(Tools.TIME_FORMAT,sendTime));
        header.addElement("Receiver").setText(receiver);
        header.addElement("MsgType").setText(msgType.toString());

        Element body = root.addElement("Body");
        body.addElement("SeqNum").setText(Long.toString(seqNum));

        if (dataType != null) body.addElement("DataType").setText(dataType);

        if ( data != null) {

            body.addElement("Data").setText(data);
        }
        return doc;
    }

    public static String getACDMDSStatusRequesteMsg(String sender) {
        Msg ret = new Msg();
        ret.sender = sender;
        ret.sendTime = new Date();
        ret.receiver = "ACDM";
        ret.msgType = MsgType.ACDMDSStatusRequest;
        ret.seqNum = 0;
        return ret.buildXml();
    }

    
    public static String getACDMDSRequestMsg(String sender,String dataType,String data) {
    	Msg ret = new Msg();
        ret.sender = sender;
        ret.sendTime = new Date();
        ret.receiver = "ACDM";
        ret.msgType = MsgType.ACDMDSRequest;
        ret.seqNum = 0;
        ret.dataType = dataType;
        ret.data = data;
        return ret.buildNonEscapeXml();
    }

   

    public static void main(String[] args) {
    	List<String> list=new ArrayList<String>();
    
    	for(int i=0;i<5;i++) {
    	String sss="<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
    			+ "<Root>"
    			+ "<Header>"
    			+ "<Sender>ACDM</Sender>"
    			+ "<SendTime>2016-09-29 15:15:01</SendTime>"
    			+ "<Receiver>BFJC</Receiver>"
    			+ "<MsgType>ACDMDSResponse</MsgType>"
    			+ "</Header>"
    			+ "<Body>"
    			+ "<SeqNum>0</SeqNum><DataType>IB</DataType><Status>Accept</Status>"
    			+ "<Data>"
    			+ "<OPERATE>UPDATE</OPERATE>"
    			+ "<KEY>"
    			+ "<FLIGHT_DIRECTION>A</FLIGHT_DIRECTION>"
    			+ "<FLIGHT_IDENTITY>9C8849</FLIGHT_IDENTITY>"
    			+ "<SCHEDULED_DATE>2016-09-29</SCHEDULED_DATE>"
    			+ "<FLIGHT_REPEAT_COUNT>0</FLIGHT_REPEAT_COUNT>"
    			+ "</KEY>"
    			+ "<REC>"
    			+ "<AIRCRAFT_REGISTRATION>B1656</AIRCRAFT_REGISTRATION>"
    			+ "<CARRIER_IATA_CODE>9C</CARRIER_IATA_CODE>"
    			+ "<SCHEDULED_DEPARTURE_AIRPORT>PVG</SCHEDULED_DEPARTURE_AIRPORT>"
    			+ "<SCHEDULED_ARRIVAL_AIRPORT>SHE</SCHEDULED_ARRIVAL_AIRPORT>"
    			+ "<SCHEDULED_DEPARTURE_TIME>2016-09-29 15:10:00</SCHEDULED_DEPARTURE_TIME>"
    			+ "<SCHEDULED_ARRIVAL_TIME>2016-09-29 17:55:00</SCHEDULED_ARRIVAL_TIME>"
    			+ "<AIRCRAFT_SUBTYPE_IATA_CODE>A320</AIRCRAFT_SUBTYPE_IATA_CODE>"
    			+ "<AGENT>9C</AGENT><FLIGHT_CLASSIFICATION_CODE>A</FLIGHT_CLASSIFICATION_CODE>"
    			+ "<ROUTE>PVG-DLC-PVG-SHE-PVG-HKT</ROUTE>"
    			+ "<CLASS_DISTRIBUTION>XXXXXXXXXXXXXXXXXXXXXX"+i+"</CLASS_DISTRIBUTION>"
    			+ "<BAGGAGE_MAKEUPS>"
    			+ "<BAGGAGE_MAKEUP>"
    			+ "<BAGGAGE_MAKEUP_CLOSE_DATE_TIME>1</BAGGAGE_MAKEUP_CLOSE_DATE_TIME>"
    			+ "<BAGGAGE_MAKEUP_OPEN_DATE_TIME>A</BAGGAGE_MAKEUP_OPEN_DATE_TIME>"
    			+ "<BAGGAGE_MAKEUP_POSITION_ID>II</BAGGAGE_MAKEUP_POSITION_ID>"
    			+ "<BAGGAGE_MAKEUP_POSITION_ROLE>i</BAGGAGE_MAKEUP_POSITION_ROLE>"
    			+ "</BAGGAGE_MAKEUP>"
    			+ "<BAGGAGE_MAKEUP>"
    			+ "<BAGGAGE_MAKEUP_CLOSE_DATE_TIME>2</BAGGAGE_MAKEUP_CLOSE_DATE_TIME>"
    			+ "<BAGGAGE_MAKEUP_OPEN_DATE_TIME></BAGGAGE_MAKEUP_OPEN_DATE_TIME>"
    			+ "<BAGGAGE_MAKEUP_POSITION_ID>III</BAGGAGE_MAKEUP_POSITION_ID>"
    			+ "<BAGGAGE_MAKEUP_POSITION_ROLE>ii</BAGGAGE_MAKEUP_POSITION_ROLE>"
    			+ "</BAGGAGE_MAKEUP>"
    			+ "</BAGGAGE_MAKEUPS>"
    			+ "<WHEELS_DOWN_DATE_TIME>2016-09-29 15:10:16</WHEELS_DOWN_DATE_TIME>"
    			+ "<CHECKIN_OPEN_DATE_TIME>2016-09-29 12:42:20</CHECKIN_OPEN_DATE_TIME>"
    			+ "<CHECKIN_CLOSE_DATE_TIME>2016-09-29 14:55:17</CHECKIN_CLOSE_DATE_TIME>"
    			+ "<CABIN_DOOR_CLOSE_TIME>2016-09-29 15:02:00</CABIN_DOOR_CLOSE_TIME>"
    			+ "<COBT>2016-09-29 15:05:00</COBT>"
    			+ "<SCHEDULED_LEG_TIME>165</SCHEDULED_LEG_TIME>"
    			+ "<ADULT_PASSENGER>171</ADULT_PASSENGER>"
    			+ "<CHILD_PASSENGER>6</CHILD_PASSENGER>"
    			+ "<INFANT_PASSENGER>1</INFANT_PASSENGER>"
    			+ "<BOARDING_NUMBER>178</BOARDING_NUMBER>"
    			+ "<CHECKIN_NUMBER>178</CHECKIN_NUMBER>"
    			+ "<ARRIVAL_BAGGAGE_COUNT>91</ARRIVAL_BAGGAGE_COUNT>"
    			+ "<ARRIVAL_BAGGAGE_WEIGHT>947</ARRIVAL_BAGGAGE_WEIGHT>"
    			+ "<DEPARTURE_BAGGAGE_COUNT>91</DEPARTURE_BAGGAGE_COUNT>"
    			+ "<DEPARTURE_BAGGAGE_WEIGHT>947</DEPARTURE_BAGGAGE_WEIGHT>"
    			+ "<DEPARTURE_FREIGHT_WEIGHT>1536</DEPARTURE_FREIGHT_WEIGHT>"
    			+ "<DEPARTURE_MAIL_WEIGHT>0</DEPARTURE_MAIL_WEIGHT>"
    			+ "</REC>"
    			+ "</Data>"
    			+ "</Body>"
    			+ "</Root>";
    		list.add(sss);
    	}
    	Long t1=System.currentTimeMillis();
    	
    	for(int i=0;i<list.size();i++) {    	

	    	Msg msg=getResponseMsg(list.get(i));
	    	    
	    	Map<String,Object> mp=Tools.getChildElementDataMap(msg.getDataElement());
	    	Set<String> keySet = mp.keySet();
	        for (Iterator it = keySet.iterator(); it.hasNext();) {
	            String key = (String) it.next();
	            System.out.println(key+":"+mp.get(key));
	    	}
    	}
    	Long t2=System.currentTimeMillis();
    	System.out.println((t2-t1)+"ms");

    }
}
