package com.zycus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexPageController {

	@RequestMapping(value = "howToPlay", method = RequestMethod.GET)
	public String getHowToPlay()
	{
		System.out.println("LOL");
		return "/howToPlay";
	}
	
	@RequestMapping(value = "credits", method = RequestMethod.GET)
	public String about()
	{
		return "/credits";
	}
}
