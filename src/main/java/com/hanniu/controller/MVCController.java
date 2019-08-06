package com.hanniu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MVCController {

	 
	 @RequestMapping("/upload")
	    public String index() {
	        System.out.println("进入到jsp");
	        return "upload";
	    }
}
