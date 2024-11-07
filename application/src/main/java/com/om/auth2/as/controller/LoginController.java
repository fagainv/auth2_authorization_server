package com.om.auth2.as.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;


@Controller
public class LoginController {

	
	@GetMapping("/login")
	public String getLoginPage(@RequestParam(value = "username", required = false) String username, Model model,
			HttpServletRequest request) {		
		model.addAttribute("username", username);
		return "login";
		
	}
}
