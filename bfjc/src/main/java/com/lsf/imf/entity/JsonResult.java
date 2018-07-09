package com.lsf.imf.entity;

import java.lang.reflect.Array;

public class JsonResult {  
    
	public enum ResultCode {  
		
	    LOGIN_SUCCESS("001", "登录成功"),
	    
	    LOGIN_FAIL("002", "登录失败"),
	    /** 数据为空 */  
	    NULL("100", "无相关数据"),  
	    
	    /** 成功 */  
	    SUCCESS("200", "成功"),  
	    
	    /** 无相关流程实例 */  
	    NO_FLOWDATA("300", "无相关流程实例"),  	    
	    	     
	    /** 没有登录 */  
	    NOT_LOGIN("400", "没有登录"),  
	      
	    /** 发生异常 */  
	    EXCEPTION("401", "发生异常"),  
	      
	    /** 系统错误 */  
	    SYS_ERROR("402", "系统错误"),  
	      
	    /** 参数错误 */  
	    PARAMS_ERROR("403", "参数错误 "),  
	      
	    /** 不支持或已经废弃 */  
	    NOT_SUPPORTED("410", "不支持或已经废弃"),  
	      
	    /** AuthCode错误 */  
	    INVALID_AUTHCODE("444", "无效的AuthCode"),  
	  
	    /** 太频繁的调用 */  
	    TOO_FREQUENT("445", "太频繁的调用"),  
	      
	    /** 未知的错误 */  
	    UNKNOWN_ERROR("499", "未知错误");  
	      
	    private ResultCode(String value, String msg){  
	        this.val = value;  
	        this.msg = msg;  
	    }  
	      
	    public String val() {  
	        return val;  
	    }  
	  
	    public String msg() {  
	        return msg;  
	    }  
	      
	    private String val;  
	    private String msg;  
	}  
	
    private String code;  
    private String message; 
    private Integer count;
    private Object data;  
      
    public JsonResult() {  
        this.setCode(ResultCode.SUCCESS);  
        this.setMessage(ResultCode.SUCCESS.msg);  
          
    }  
    
      
    public JsonResult(Object data) {  
    	if(data!=null) {
    		this.setCode(ResultCode.SUCCESS);  
    		this.setMessage(ResultCode.SUCCESS.msg);
    	}else {
    		this.setCode(ResultCode.NULL);  
            this.setMessage(ResultCode.NULL.msg);
        		
    	}
        this.setData(data);            
    }  
    
    public JsonResult(Object data,int count) {  
    	if(data!=null) {
    		this.setCode(ResultCode.SUCCESS);  
    		this.setMessage(ResultCode.SUCCESS.msg);
    	}else {
    		this.setCode(ResultCode.NULL);  
            this.setMessage(ResultCode.NULL.msg);
        		
    	}
        this.setData(data,count);   
    }  
    
    public JsonResult(ResultCode code) {  
        this.setCode(code);  
        this.setMessage(code.msg());  
    }  
      
    public JsonResult(ResultCode code, String message) {  
        this.setCode(code);  
        this.setMessage(message);  
    }  
      
    public JsonResult(ResultCode code, String message, Object data) {  
        this.setCode(code);  
        this.setMessage(message);  
        
        this.setData(data);

    }  
      
    public String getCode() {  
        return code;  
    }  
    public void setCode(ResultCode code) {  
        this.code = code.val();  
    }  
    public String getMessage() {  
        return message;  
    }  
    public void setMessage(String message) {  
        this.message = message;  
    }  
  
    public Integer getCount() {
		return count;
	}


	public void setCount(Integer count) {
		this.count = count;
	}


	public Object getData() {  
        return data;  
    }  
  
    public void setData(Object data) {  
        this.data = data;  
        if(data!=null) {
	        if(data.getClass().isArray()) {
	        	this.setCount(Array.getLength(data));
	        }else {
	        	this.setCount(1);
	        }
        }else {
        	this.setCount(0);
        }
    }  
    
    public void setData(Object data,int count) {  
        this.data = data;  
        this.count=count;
    }  
}  