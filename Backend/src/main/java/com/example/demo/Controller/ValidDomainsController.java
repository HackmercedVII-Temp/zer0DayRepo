package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Services.ValidDomainsServices;

@RestController
public class ValidDomainsController {
	@Autowired
	ValidDomainsServices validDomainsServices;

	@RequestMapping("/TestCompanyColors")
	public String displayCompanyColors() {
		return validDomainsServices.displayCompanyColors();
	}

}
