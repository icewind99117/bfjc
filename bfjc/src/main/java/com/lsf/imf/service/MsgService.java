package com.lsf.imf.service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lsf.imf.controller.PageController;
import com.lsf.imf.entity.bo.AfdsInfo;
import com.lsf.imf.entity.bo.ApiTag;
import com.lsf.imf.entity.bo.MsgStore;
import com.lsf.imf.mapper.AfdsInfoMapper;
import com.lsf.imf.mapper.ApiTagMapper;
import com.lsf.imf.mapper.MsgStoreMapper;
import com.lsf.imf.mq.Msg;
import com.lsf.imf.util.Tools;

@Service
public class MsgService {
	
	private static Logger log = LoggerFactory.getLogger(MsgService.class);
	
	@Autowired
	MsgStoreMapper msgMapper;
	
	@Autowired
	AfdsInfoMapper afdsInfoMapper;
	
	@Autowired
	ApiTagMapper apiTagMapper;
	
	public int saveMsgStore(MsgStore msgStore) { 
		return msgMapper.saveMsgStore(msgStore);
	}
	
	public int cleanMsgStoreBefore(Timestamp lastTime) { 
		int maxId= msgMapper.getMaxIdOfMsgStoreBefore(lastTime);
		return msgMapper.deleteMsgStoreBefore(maxId);
	}
	
	public int updateMsgStoreStatus(MsgStore msgStore) { 
		return msgMapper.updateMsgStoreStatusAndAfdsId(msgStore);
	}
		
	
	public int countMsgStore(Map<String,String> paramMap) {
		return msgMapper.countMsgStore(paramMap);
	}
	public List<MsgStore> findMsgStoreByPage(Map<String,String> paramMap){
		
		return msgMapper.pageQueryMsgStore(paramMap);
	}
	
	public List<HashMap<String,String>> findAfdsInfoByScheduledDate(String scheduledDate){
		
		return afdsInfoMapper.findAllAfdsInfoByScheduledDate(scheduledDate);
	}
	
	public String saveOrUpdateAfdsInfo(Map<String,Object> map) { 
		AfdsInfo afdsInfo=afdsInfoMapper.getAfdsInfo(map);
		
		if(map.containsKey("operate") && "delete".equalsIgnoreCase((String)map.get("operate")) ) {
			if(afdsInfo==null) {
				return "";
			}else {
				int num=afdsInfoMapper.removeAfdsInfo(afdsInfo.getId());
				if(num==1) {
					return String.valueOf(afdsInfo.getId());	
				}else {
					return null;
				}
			}
		}else {
			int num=0;
			if(afdsInfo==null) {
				num=afdsInfoMapper.insertAfdsInfo(map);

				
			}else {
				map.put("id", afdsInfo.getId());
				num=afdsInfoMapper.updateAfdsInfo(map);
			}
			if(num==1) {
				return String.valueOf(map.get("id"));	
			}else {
				return null;
			}
		}
	}
	
	public Integer updateApiTag(String code,String value) {
		return apiTagMapper.updateApiTag(code,value);
	}
	
	public int updateTimestamp(String msgTime){
		return apiTagMapper.updateTimestamp(msgTime);
	}
	
	public List<ApiTag> findAllApiTag() { 
		return apiTagMapper.findAllApiTag();
	}
	public String getUpdateStart() { 
		
		HashMap<String,String> tagMap=new HashMap<String,String>();
		
		List<ApiTag> list=apiTagMapper.findAllApiTag();
		
		for(ApiTag tag:list) {
			tagMap.put(tag.getCode(), tag.getParams());
		}
		
		if(!tagMap.containsKey("timestamp") || tagMap.get("timestamp")==null || "".equals(tagMap.get("timestamp"))) {
			
			return  getSnapshotTime(tagMap);
			
		}else {
			
			Date latestTimestamp=getLatestTimestamp(tagMap);
			
			long maxInterval =getMaxInterval(tagMap);
						
			if(System.currentTimeMillis()-latestTimestamp.getTime()>=maxInterval) {
				return  getSnapshotTime(tagMap);
			}else {
				return getUpdateStartTime(tagMap,latestTimestamp);
			}
			
			
		}

	}
	
	private String getSnapshotTime(HashMap<String,String> tagMap) {
		String snapshot=tagMap.get("snapshot");
		String dVal=snapshot.replaceAll("D", "").replaceAll("//+", "");
		return  Tools.getDay(Integer.valueOf(dVal))+" 00:00:00";
	}
	
	private Date getLatestTimestamp(HashMap<String,String> tagMap) {
		String timestamp=tagMap.get("timestamp");
		Date latestMsgDate=Tools.timeParse(Tools.TIME_FORMAT, timestamp);
		return latestMsgDate;
	}
	
	private long getMaxInterval(HashMap<String,String> tagMap) {
		String interval=tagMap.get("maxOffline");
		long maxInterval=0;
		String timeInterval = interval.toUpperCase();

		if (timeInterval.endsWith("D")) {
			
			interval = timeInterval.replace("D", "");
			
			maxInterval=Integer.parseInt(interval)*24*60*60*1000;
			
		} else if (timeInterval.endsWith("H")) {
			interval = timeInterval.replace("H", "");
			maxInterval=Integer.parseInt(interval)*60*60*1000;
			
		} else if (timeInterval.endsWith("M")) {
			interval = timeInterval.replace("M", "");
			maxInterval=Integer.parseInt(interval)*60*1000;
			
		} else {
			interval = timeInterval.replace("S", "");
			maxInterval=Integer.parseInt(interval)*1000;
		}
		
		return maxInterval;
	}
	
	
	private String getUpdateStartTime(HashMap<String,String> tagMap,Date latestTimestamp) {
		String interval=tagMap.get("update");
		return Tools.getTime(latestTimestamp, interval);
		
	}
	
	
	public boolean parseMsg(int msgId) {
		MsgStore msgStore= msgMapper.getMsgStoreById(msgId);
        Msg msg = Msg.getResponseMsg(msgStore.getMsgContent());
    	try { 


	        	
        	Map<String,Object> dataMap=Tools.getChildElementDataMap(msg.getDataElement());
        	
        	msgStore.setSeqNum(String.valueOf(msg.getSeqNum()));		        	
        	msgStore.setFlightIdentity((String)dataMap.get("flight_identity"));
        	msgStore.setFlightDirection((String)dataMap.get("flight_direction"));
        	msgStore.setFlightRepeatCount((String)dataMap.get("flight_repeat_count"));
        	msgStore.setScheduledDate((String)dataMap.get("scheduled_date"));
//	        	dataMap.put("uuid",UUID.randomUUID().toString());
        	
        	String afdsId=saveOrUpdateAfdsInfo(dataMap);
        	
        	if(afdsId!=null) {
        		msgStore.setAfdsId(Integer.parseInt(afdsId));
        		msgStore.setStatus(1);
        	}else {	        		
        		msgStore.setStatus(9);
        	}	 
    	
    	}catch(Exception e) {
    		log.error("Error when parse msg to afdsInfo,the seqNum="+msg.getSeqNum(),e);
    		msgStore.setStatus(9);
    		return false;
    	}finally{	 
    	
//    	msgStore.setParseTime(new Timestamp(System.currentTimeMillis()));
    	       		            
    	updateMsgStoreStatus(msgStore);
    	}
	        		        	
	        	
    	return true;

    
	}
}
