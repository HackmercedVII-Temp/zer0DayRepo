package com.example.demo.Dependency;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SiteRequest {

	private String domain;
	private ArrayList<String> imageURLs = new ArrayList<String>();
	private ArrayList<String> siteText = new ArrayList<String>();
	private ArrayList<String> colors = new ArrayList<String>();

	private JSONParser parser = new JSONParser();
	private JSONObject jsonObject;

	private boolean isInitialized = false;

	public SiteRequest(String JSON) {
		try {
			jsonObject = (JSONObject) parser.parse(JSON);
			domain = (String) jsonObject.get("Domain");

			JSONArray imgURLArray = (JSONArray) jsonObject.get("Image_URLS");
			Iterator<String> imgUrlIterator = imgURLArray.iterator();
			while (imgUrlIterator.hasNext()) {
				imageURLs.add(imgUrlIterator.next().toString());
			}

			JSONArray colorsArray = (JSONArray) jsonObject.get("Colors");
			Iterator<String> colorsIterator = colorsArray.iterator();
			while (colorsIterator.hasNext()) {
				colors.add(colorsIterator.next().toString());
			}

			JSONArray textArray = (JSONArray) jsonObject.get("Site_Text");
			Iterator<String> textIterator = textArray.iterator();
			while (textIterator.hasNext()) {
				siteText.add(textIterator.next().toString());
			}

			if (domain == null) {
				isInitialized = false;
				return;
			}
			if (domain.isBlank()) {
				isInitialized = false;
				return;
			}

			isInitialized = true;
		} catch (ParseException e) {
			isInitialized = false;
			return;
		}

	}

	public void print() {
		System.out.println("\nDomain: " + domain + "\n\nImageURLs: \n");
		for (String url : imageURLs) {
			System.out.println(url);
		}
		System.out.println("\nSite Text:\n");
		for (String url : siteText) {
			System.out.println(url);
		}
		System.out.println("\nSite Colors:\n");
		for (String url : colors) {
			System.out.println(url);
		}
	}

}
