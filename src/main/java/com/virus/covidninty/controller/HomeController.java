package com.virus.covidninty.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.virus.covidninty.services.Coronaservices;

@Controller
public class HomeController {

	@Autowired
	Coronaservices coronaservices;
	
	@GetMapping("/")
	public String homePage(Model model) {
		model.addAttribute("DeveloperName","Mister Ujjaval");
		model.addAttribute("locationState",coronaservices.getAllStates());
		
		return "home";
	}
}
