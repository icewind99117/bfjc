package com.lsf.imf.controller;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lsf.imf.ControlBoard;
import com.lsf.imf.entity.JsonResult;
import com.lsf.imf.entity.JsonResult.ResultCode;
import com.lsf.imf.entity.bo.ApiTag;
import com.lsf.imf.entity.bo.MsgStore;
import com.lsf.imf.mq.ACDMDSClient.ApiStatus;
import com.lsf.imf.service.MsgService;


@RestController
@RequestMapping("/msg")
public class MsgController {
	
	private static Logger log = LoggerFactory.getLogger(MsgController.class);

	@Autowired
	private MsgService msgService;
	

	
	@RequestMapping(value = "/panel/dsStart", method = { RequestMethod.GET })
	JsonResult startACDMDS() {
		boolean suc=ControlBoard.getDsClient().start();
		if(suc) {
			return new JsonResult(ResultCode.SUCCESS);
		}else {
			return new JsonResult(ResultCode.NULL);
		}
	}
	
	@RequestMapping(value = "/panel/dsStop", method = { RequestMethod.GET })
	JsonResult stopACDMDS() {
		ControlBoard.getDsClient().stop();
		
		return new JsonResult(ResultCode.SUCCESS);		
	}
	
	@RequestMapping(value = "/panel/dsStatus", method = { RequestMethod.GET })
	JsonResult monitorACDMDS() {
		ApiStatus status =ControlBoard.getDsClient().getApiStatus();
		
		return new JsonResult(status.msg());
		
	}
	
	
	@RequestMapping(value = "/panel/dsParse/{msgId}", method = { RequestMethod.GET })
	JsonResult getFlowById(@PathVariable("msgId") int msgId) {
		Boolean suc=msgService.parseMsg(msgId);
		if(suc) {
			return new JsonResult(ResultCode.SUCCESS);
		}else {
			return new JsonResult(ResultCode.NULL);
		}
	}
	
	@RequestMapping(value = "/allApiTag", method = { RequestMethod.GET })
	JsonResult getFlowById() {
		List<ApiTag> list=msgService.findAllApiTag();
		return new JsonResult(list);
	}

	
	@RequestMapping(value = "/apiTag", method = { RequestMethod.POST })
	JsonResult saveOrUpdateApiTag(@RequestParam Map<String, String> formData) {
		int num=0;
		Set<String> keySet = formData.keySet();
        for (Iterator it = keySet.iterator(); it.hasNext();) {
            String key = (String) it.next();
            
            int n=msgService.updateApiTag(key,formData.get(key));
            num+=n;
        }
			
		if(num==formData.size()) {
			return new JsonResult(ResultCode.SUCCESS);
		}else {
			return new JsonResult(ResultCode.NULL);
		}
	}
	
	@RequestMapping(value = "/msgStoreList", method = { RequestMethod.GET })
	JsonResult queryFlowByPage(@RequestParam Map<String, String> paramMap) {
		
		int pageNo=Integer.parseInt(paramMap.get("page"));
		int limit=Integer.parseInt(paramMap.get("limit"));
		paramMap.put("start", (pageNo-1)*limit+"");
		paramMap.remove("page");		
		
		int count=msgService.countMsgStore(paramMap);
		
		List<MsgStore> list=msgService.findMsgStoreByPage(paramMap);
		
		return new JsonResult(list,count);
	}
	



	

}
