package com.lsf.imf.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



@Controller
@RequestMapping("/page")
public class PageController {
	
	private static Logger log = LoggerFactory.getLogger(PageController.class);

	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	String login() {
		return "login";
	}

	@RequestMapping(value = "/panel", method = RequestMethod.GET)
	String panel() {
		return "controlPanel";
	}

	@RequestMapping(value = "/msg", method = RequestMethod.GET)
	String msgList() {
		return "msgList";
	}
	
}
