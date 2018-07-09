package com.lsf.imf.mq;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ibm.mq.MQC;
import com.ibm.mq.MQEnvironment;
import com.ibm.mq.MQException;
import com.ibm.mq.MQGetMessageOptions;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQPutMessageOptions;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.constants.CMQC;
import com.lsf.imf.ControlBoard;
import com.lsf.imf.entity.bo.ApiTag;
import com.lsf.imf.entity.bo.MsgStore;
import com.lsf.imf.mapper.ApiTagMapper;
import com.lsf.imf.mapper.MsgStoreMapper;
import com.lsf.imf.mq.Msg.MsgType;
import com.lsf.imf.service.MsgService;
import com.lsf.imf.util.Tools;


@Component
public class ACDMDSClient {

	private static Logger log = LoggerFactory.getLogger(ACDMDSClient.class);
	
	public enum ApiStatus {  
		
	    STOPED("999", "已停止"),
	    
	    STARTING("100", "启动中"),
	    
	    START_FAIL("101", "启动失败"),
	    
	    STARTED("199", "已启动"),  
	     
	    CONNECTING("200", "尝试连接中"),
	    
	    CONNECTING_QM("210", "连接队列管理器中"),
	    
	    CONNECT_QM_FAIL("211", "连接队列管理器失败"),
	    
	    CONNECTED_QM("219", "连接队列管理器成功"),
	    
	    CONNECTING_Q("220", "连接队列中"),
	    
	    CONNECT_Q_FAIL("220", "连接队列失败"),
	   
	    CONNECTED_Q("229", "连接队列成功"),
	    
	    CONNECT_FAIL("201", "连接失败"),  
	    
	    CONNECTED("299", "已连接"),  
	    
	    TIMER_STARTING("300", "定时器启动中"),
	    
	    TIMER_STARTED("399", "定时器已启动"),
	    
	    ONLINE("499", "在线"),
	    
	    OFFLINE("599", "离线"),
	   
	    STOPING("900", "停止中");  
	      
	    private ApiStatus(String code, String msg){  
	        this.code = code;  
	        this.msg = msg;  
	    }  
	      
	    public String code() {  
	        return code;  
	    }  
	  
	    public String msg() {  
	        return msg;  
	    }  
	      
	    private String code;  
	    private String msg;  
	}  
    
	@Autowired
	MsgService msgService;
	
	@Value("${mq.hostName}")
	private String mqHostName;
	
	@Value("${mq.channel}")
	private String mqChannel;
	
	@Value("${mq.port}")
	private int mqPort;
	
	@Value("${mq.ccsid}")
	private int mqCCSID;
	
	@Value("${mq.queueManager}")
	private String queueManager;
			
	@Value("${mq.serverName}")
	private String mqServerName;
	
	@Value("${mq.subscribeDataType}")
	private String subscribeDataType;	
	
	
    private MQQueueManager qMgr;

    private MQQueue inQueue;
    private MQQueue outQueue;
    
    private AtomicBoolean online = new AtomicBoolean(false);
    
    private AtomicLong heartbeatMillis = new AtomicLong(0L);
    
    private ApiStatus apiStatus=ApiStatus.STOPED;

    private List<Timer> dsTimers=new ArrayList<Timer>();
    
    
	public String  getInitParams() {
		return "queueManager:"+queueManager;
	}
	
    private String test;
    
    public void setApiStatus(ApiStatus apiStatus) {
    	this.apiStatus=apiStatus;
    }
    
    public ApiStatus getApiStatus() {
    	return this.apiStatus;
    }
    
    public boolean isOnline() {
        return online.get();
    }

    public void setOnline(boolean online) {
        this.online.set(online);
    }
    

    public void setHeartbeatMillis(){
    	heartbeatMillis.set(System.currentTimeMillis());
    }

    public long getHeartbeatMillis(){
    	return heartbeatMillis.get();
    }
    
    private boolean initQueueManager(String hostName){
    	
    	MQEnvironment.hostname = hostName;
        MQEnvironment.port = mqPort;
        MQEnvironment.channel = mqChannel;
        MQEnvironment.CCSID = mqCCSID;
        try{
        	setApiStatus(ApiStatus.CONNECTING_QM);
        	qMgr = new MQQueueManager(queueManager);
        	setApiStatus(ApiStatus.CONNECTED_QM);
        	return true;	
		} catch (MQException e) {	
			setApiStatus(ApiStatus.CONNECT_QM_FAIL);
			log.error("Error when connect to "+queueManager+" in "+hostName,e.getMessage());
			return false;
        }
        
    }
    

    public boolean buildConnect(){
    	setApiStatus(ApiStatus.CONNECTING);
    	String[] hostNameArr=mqHostName.split(",");
    	String connectedHostName="";
    	 try {
    		 boolean suc=false;
    		 for(int i=0;i<3;i++){
	        	for(String hostName:hostNameArr){

	        		suc=initQueueManager(hostName);
	        		
	        		if(suc){
	        			
	        			connectedHostName=hostName;
	        			break;
	        		}
	        	}
	        	if(suc){break;}
	        	
	        	try {
					Thread.sleep(1000*5);
				} catch (InterruptedException e) {
					log.error("Error when sleep 1 min after buildconnect "+(i+1)+" times ",e.getMessage());
					e.printStackTrace();
				}
	        	
    		 }


    		 if(suc) {
    			 setApiStatus(ApiStatus.CONNECTING_Q);
    			 
    			 int qOptioin = CMQC.MQOO_INPUT_AS_Q_DEF | CMQC.MQOO_INQUIRE | CMQC.MQOO_OUTPUT;
		     
    			 inQueue = qMgr.accessQueue("ACDMDS." + mqServerName + ".OUT.Q", qOptioin);
    			 outQueue = qMgr.accessQueue("ACDMDS." + mqServerName + ".IN.Q", qOptioin);
    			 
    			 setApiStatus(ApiStatus.CONNECTED);
    			 log.info("Connect to queue of "+queueManager+" in "+connectedHostName+" success!");
    		 }
		     return suc;

         } catch (MQException e) {
        	 setApiStatus(ApiStatus.CONNECT_Q_FAIL);
        	 log.error("Error when connect to queue of "+queueManager+" in "+connectedHostName,e.getMessage());
             return false;
         }
    }
    


    public void receiveMsgs() {
    	
        try {
        	
            while ( inQueue.getCurrentDepth()>0) {
                MQMessage message = new MQMessage();
                inQueue.get(message);
                byte[] iii = new byte[message.getMessageLength()];
                message.readFully(iii); // 取出队列的消息
                String text;
                switch (message.characterSet) {
                case 1381:
                    text = new String(iii, "gbk");
                    break;
                default:
                    text = new String(iii, "utf-8");
                    break;
                }
//                System.out.println("Receive:"+text);
//                long t1=System.currentTimeMillis();
                processMsg(text);
                
//                long t2=System.currentTimeMillis();
                
//                log.info("耗时:"+(t2-t1));

            }
        } catch (RuntimeException e) {
        	log.error("Error when getMsg from queue",e.getMessage());            
		} catch (MQException e) {
			if (e.completionCode == 2 && e.reasonCode == 2033) {
				log.info("队列中暂时为空");
			} else {
				log.error("Error when getMsg from queue,so try to rebuildConnect",e.getMessage());     
				buildConnect();
			}

		} catch (java.io.IOException e) {
			log.error("Error when getMsg from queue",e.getMessage());         
		}
       
    }
    
    private void processMsg(String text) {

        Msg msg = Msg.getResponseMsg(text);
        if (msg == null) return;
        MsgStore msgStore=new MsgStore(msg,text);
        
        switch (msg.getMsgType()) {
	        case ACDMDSStatusResponse:
	        	log.debug("receive heatbeat");    	
	        	msgService.saveMsgStore(msgStore);
	            
	            if("Down".equalsIgnoreCase(msg.getStatus())){
	            	String updateStart=msgService.getUpdateStart();
	            	sendMsg(Msg.getACDMDSRequestMsg(mqServerName, subscribeDataType, "<UpdateStart>"+updateStart+"</UpdateStart>"));
	            }else if("Up".equalsIgnoreCase(msg.getStatus())) {
	            	 setOnline(true);
	            	 setHeartbeatMillis();  
	            }
	            break;
	            
	        case ACDMDSResponse:
	        	log.debug("receive msg:seqNum="+msg.getSeqNum());
	        	setOnline(true);
	        	
            	setHeartbeatMillis();  
            	
            	try { 	        	
		        	Map<String,Object> dataMap=Tools.getChildElementDataMap(msg.getDataElement());
		        	
		        	msgStore.setSeqNum(String.valueOf(msg.getSeqNum()));		        	
		        	msgStore.setFlightIdentity((String)dataMap.get("flight_identity"));
		        	msgStore.setFlightDirection((String)dataMap.get("flight_direction"));
		        	msgStore.setFlightRepeatCount((String)dataMap.get("flight_repeat_count"));
		        	msgStore.setScheduledDate((String)dataMap.get("scheduled_date"));
	//	        	dataMap.put("uuid",UUID.randomUUID().toString());
		        	
		        	String afdsId=msgService.saveOrUpdateAfdsInfo(dataMap);
		        	
		        	if(afdsId!=null) {
		        		msgStore.setAfdsId(Integer.parseInt(afdsId));
		        		msgStore.setStatus(1);
		        	}else {	        		
		        		msgStore.setStatus(9);
		        	}	 
	        	
            	}catch(Exception e) {
            		log.error("Error when parse msg to afdsInfo,the seqNum="+msg.getSeqNum(),e);
            		msgStore.setStatus(9);
	        	}	 
            	
	        	msgStore.setParseTime(new Timestamp(System.currentTimeMillis()));
	        	       		            
	        	msgService.saveMsgStore(msgStore);
	        	
	        	msgService.updateTimestamp(msgStore.getMsgTime());	        	
	        	
	            break;
	
	        default:
	            log.error("Unknow msgType:" + text);
	            break;
        }

    }


    public void sendMsg(String msg) {
        try {
            MQMessage qMsg = new MQMessage();
            qMsg.characterSet = MQEnvironment.CCSID;
            qMsg.writeString(msg);
            MQPutMessageOptions pmo = new MQPutMessageOptions();

            outQueue.put(qMsg, pmo);

            log.debug("Send:"+msg);
        } catch (MQException e) {
        	log.error("Error when sendMsg to queue,so try to rebuildConnect",e.getMessage());     
			buildConnect();
		} catch (java.io.IOException e) {
			log.error("Error when sendMsg to queue",e.getMessage());         
		}

    }

    public boolean start() {
    	if(this.apiStatus==ApiStatus.STARTING || this.apiStatus==ApiStatus.STARTED||this.apiStatus==ApiStatus.ONLINE) {
    		return false;
    	}
    	setApiStatus(ApiStatus.STARTING);
    	boolean online=buildConnect();
    	if(online) {
    		
        	for(Timer t:dsTimers) {
        		t.cancel();
        	}
        	dsTimers.clear();
        	
    		setApiStatus(ApiStatus.TIMER_STARTING);
    		
    		Timer sendTimer=new Timer();
    		
    		sendTimer.schedule(new TimerTask() {// 发消息
	            @Override
	            public void run() {
	            	sendMsg(Msg.getACDMDSStatusRequesteMsg(mqServerName));
	            }
	        }, 0, 1000*60);
    		
    		dsTimers.add(sendTimer);
    		
    		Timer receiveTimer=new Timer();
    		receiveTimer.schedule(new TimerTask() {// 收消息
	            @Override
	            public void run() {
	            	receiveMsgs();
	            }
	        }, 0, 1000);
    		dsTimers.add(receiveTimer);
    		
    		 Timer onlineTimer=new Timer();
    		 onlineTimer.schedule(new TimerTask() {// 检测心跳是否失效
    	            @Override
    	            public void run() {
    	                if (isOnline()) {
    	                	
	    	                if(System.currentTimeMillis() - getHeartbeatMillis() > 10 * 60 * 1000L) {
	    	                	setApiStatus(ApiStatus.OFFLINE);
	    	                }else {
	    	                	setApiStatus(ApiStatus.ONLINE);
	    	                }
    	                }
    	            }
    	        }, 0, 60 * 1000);  
   
    		 dsTimers.add(onlineTimer);
    		 
    		setApiStatus(ApiStatus.TIMER_STARTED);
    		
    		setApiStatus(ApiStatus.STARTED);
    		
    		return true;
    	}else {
    		
    		setApiStatus(ApiStatus.START_FAIL);
    		return false;
    	}
    	
    }
    
    public void stop(){
    	setApiStatus(ApiStatus.STOPING);
    	for(Timer t:dsTimers) {
    		t.cancel();
    	}
    	dsTimers.clear();

    	disConnect();
    	setApiStatus(ApiStatus.STOPED);
    }
    
    
    
    public void setTest(String testVal) {
    	this.test=testVal;
    }
    
    public String getTest() {
    	return this.test;
    }
    public String getHostName() {
    	return this.mqHostName;
    }
    
    private void disConnect() {
        try {
        	if(inQueue!=null){
        		inQueue.close();
        	}
        	if(outQueue!=null){
        		outQueue.close();
        	}
        	if(qMgr!=null){
        		qMgr.disconnect();
        	}
        } catch (MQException e) {
        	 log.error("Error when disconnect to queue of "+queueManager,e.getMessage());            
        }
    }
    /**
     * @param args
     */
    public static void main(String[] args) {
    	ACDMDSClient client=ControlBoard.getDsClient();
    	System.out.println(client);
    	client.setTest("aaa");
    	System.out.println(client.getTest());
    	System.out.println(client.getHostName());
    	
    	ACDMDSClient client2=ControlBoard.getDsClient();
    	System.out.println(client2);
    	System.out.println(client2.getTest());
    }
}