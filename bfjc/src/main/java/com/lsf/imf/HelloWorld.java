package com.lsf.imf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class HelloWorld {
	private static Logger log = LoggerFactory.getLogger(HelloWorld.class);
	 @RequestMapping("/hi") 
	String Hello() {
		 log.info("HelloWorld~~");
		return "Hello World!";
	}
	 @RequestMapping("/hi/{name}") 
	String Hello(@PathVariable("name") String name) {
		 return "Hello ".concat(name);
	 }
}
