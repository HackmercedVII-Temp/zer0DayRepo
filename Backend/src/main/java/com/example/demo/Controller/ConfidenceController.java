package com.example.demo.Controller;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Dependency.GoogleVisions;
import com.example.demo.Dependency.SiteRequest;
import com.example.demo.Dependency.StringSimilarityComparitor;
import com.example.demo.Entities.ValidDomainsEntity;
import com.example.demo.Repositories.ValidDomainsRepository;
import com.example.demo.Services.ValidDomainsServices;

@RestController
public class ConfidenceController {

	private static double DOMAIN_SIMILARITY_PERCENTAGE = 0.65;

	@Autowired
	ValidDomainsServices validDomainsServices;

	@Autowired
	ValidDomainsRepository validDomainsRepository;

	private static List<ValidDomainsEntity> allDomains = null;

	private static Map<String, String> cachedImages = new HashMap<String, String>();

//https://seattlecanineclub.net/wp-content/uploads/2018/01/AMAZON-1200x537.png

	@GetMapping("/DecodeImage")
	public String decodeImage(@RequestParam(value = "image64") String image64) {
		byte[] decodedBytes = Base64.getDecoder().decode(image64);
		String decodedString = new String(decodedBytes);
		return GoogleVisions.detectLogos(decodedString);
	}

	@RequestMapping(value = "/CalculateConfidence", method = RequestMethod.POST, consumes = "text/plain")
	public String calculateConfidence(@RequestBody String JSON) {
		ValidDomainsEntity mostLikeyImpersonation = null;
		if (allDomains == null) {
			allDomains = validDomainsRepository.findAll();
		}

		int lowestConfidenceLevel = 100;

		SiteRequest siteRequest = new SiteRequest(JSON);
		if (!siteRequest.isInitialized()) {
			return "error";
		}
		if (isOfficialDomain(siteRequest.getDomain())) {
			return "100";
		}

		for (ValidDomainsEntity validDomainsEntity : allDomains) {
			int confidenceLevel = 100;
			confidenceLevel -= isIntentionallyMisspelledDomain(validDomainsEntity, siteRequest.getDomain());
			confidenceLevel -= percentageMatchingLogo(validDomainsEntity, siteRequest.getImageURLs());

			if (lowestConfidenceLevel > confidenceLevel) {
				lowestConfidenceLevel = confidenceLevel;
				mostLikeyImpersonation = validDomainsEntity;
			}
		}
		if (lowestConfidenceLevel == 100) {
			return "{\"Confidence-Level\":" + Integer.toString(lowestConfidenceLevel) + "}";

		}
		return "{\"Confidence-Level\":" + Integer.toString(lowestConfidenceLevel) + "\n\"Most-Likely-Impersonation\":\""
				+ mostLikeyImpersonation.getCompanyName() + "\"}";

	}

	public boolean isOfficialDomain(String Domain) {
		return validDomainsServices.isOfficialDomain(Domain);
	}

	public int isIntentionallyMisspelledDomain(ValidDomainsEntity validDomainsEntity, String domainName) {
		Double similarity = StringSimilarityComparitor.getSimilarity(domainName, validDomainsEntity.getCompanyDomain());
		if (similarity != 1.0) {
			if (similarity < DOMAIN_SIMILARITY_PERCENTAGE) {
				return 0;
			}
			similarity -= DOMAIN_SIMILARITY_PERCENTAGE;
			int scoreToDeduct = 0;
			while (similarity > 0) {
				scoreToDeduct += 3;
				similarity -= .05;
			}

			return Math.min(scoreToDeduct, 20);
		}
		return 0;
	}

	public int percentageMatchingLogo(ValidDomainsEntity validDomainsEntity, List<String> imageURLs) {
		Map<String, Integer> numberMatching = new HashMap<String, Integer>();
		List<String> corperationLogos = new LinkedList<>();
		for (String imageURL : imageURLs) {
			if (cachedImages.containsKey(imageURL)) {
				corperationLogos.add(cachedImages.get(imageURL));
			} else {
				String googleVisionsOutput = GoogleVisions.detectLogos(imageURL).toLowerCase();
				corperationLogos.add(googleVisionsOutput);
				cachedImages.put(imageURL, googleVisionsOutput);
			}
		}
		for (String corperationName : corperationLogos) {
			if (corperationName.toLowerCase().contains(validDomainsEntity.getCompanyName().toLowerCase())) {
				if (numberMatching.containsKey(validDomainsEntity.getCompanyName())) {
					numberMatching.put(validDomainsEntity.getCompanyName(),
							numberMatching.get(validDomainsEntity.getCompanyName()) + 1);
				} else {
					numberMatching.put(validDomainsEntity.getCompanyName(), 1);
				}
			}
		}

		int largestMatchingCompanyLogos = 0;
		for (Integer value : numberMatching.values()) {
			if (value > largestMatchingCompanyLogos) {
				largestMatchingCompanyLogos = value;
			}
		}
		int totalAmountOfImages = imageURLs.size();
		largestMatchingCompanyLogos -= Math.max(totalAmountOfImages / 10, 1);
		if (largestMatchingCompanyLogos <= 0) {
			return 0;
		} else {
			return Math.min(largestMatchingCompanyLogos * 5, 30);
		}

	}

	public int percentageMatchingText(ValidDomainsEntity validDomainsEntity, List<String> siteText) {

	}

	public int percentageMatchingColor(ValidDomainsEntity validDomainsEntity, List<String> siteColors) {

	}

	@RequestMapping("/LogoDecoder")
	public static String logoDecoder(@RequestParam(value = "testval") String testval) throws IOException {
		byte[] decodedBytes = Base64.getDecoder().decode(testval);
		String decodedString = new String(decodedBytes);
		return GoogleVisions.detectLogos(decodedString);
	}
}
