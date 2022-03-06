package com.example.demo.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entities.ValidDomainsEntity;
import com.example.demo.Repositories.ValidDomainsRepository;

@Service
public class ValidDomainsServices {

	@Autowired
	ValidDomainsRepository validDomainsRepository;

	public String displayCompanyColors() {
		List<ValidDomainsEntity> listOfCompanies = validDomainsRepository.findByCompanyNameLike("Amazo");
		String result = "List size: " + listOfCompanies.size() + "\n";
		for (ValidDomainsEntity validDomainsEntity : listOfCompanies) {
			result = result + validDomainsEntity.getCompanyName() + "\n\n" + validDomainsEntity.getCompanyColor()
					+ "\n";
		}
		return result;
	}

}
