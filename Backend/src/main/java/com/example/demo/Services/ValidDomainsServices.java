package com.example.demo.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entities.ValidDomainsEntity;
import com.example.demo.Repositories.ValidDomainsRepository;
import com.google.common.net.InternetDomainName;

@Service
public class ValidDomainsServices {

	@Autowired
	ValidDomainsRepository validDomainsRepository;

	public String displayCompanyColors() {
		String domainName = InternetDomainName.from("www.amazon.com").topPrivateDomain().topDomainUnderRegistrySuffix()
				.toString();
		System.out.print(domainName);
		List<ValidDomainsEntity> listOfCompanies = validDomainsRepository.findByCompanyDomain(domainName);
		String result = "List size: " + listOfCompanies.size() + "\n";
		for (ValidDomainsEntity validDomainsEntity : listOfCompanies) {
			result = result + validDomainsEntity.getCompanyName() + "\n\n" + validDomainsEntity.getCompanyColor()
					+ "\n";
		}
		return result;
	}

	public boolean isOfficialDomain(String domain) {
		if (domain == null) {
			return false;
		}
		if (validDomainsRepository.findByCompanyDomain(domain).size() > 0) {
			return true;
		}
		return false;
	}
}
