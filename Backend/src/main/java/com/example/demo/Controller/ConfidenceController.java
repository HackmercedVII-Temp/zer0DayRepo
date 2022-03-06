package com.example.demo.Controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Dependency.FileDownloader;
import com.example.demo.Dependency.SiteRequest;
import com.example.demo.Entities.ValidDomainsEntity;
import com.example.demo.Services.ValidDomainsServices;
import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.EntityAnnotation;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.cloud.vision.v1.ImageSource;
import com.google.protobuf.ByteString;

@RestController
public class ConfidenceController {

	@Autowired
	ValidDomainsServices validDomainsServices;

	private static ArrayList<String> GC_SUPPORTED_FILE_TYPE_EXTENSIONS = new ArrayList<String>(
			Arrays.asList("PNG8", "PNG24", "GIF", "JPEG", "BMP", "WEBP", "RAW", "ICO", "PDF", "TIFF"));

	@RequestMapping(value = "/CalculateConfidence", method = RequestMethod.POST, consumes = "text/plain")
	public String calculateConfidence(@RequestBody String JSON) {
		int confidenceLevel = 100;

		SiteRequest siteRequest = new SiteRequest(JSON);
		siteRequest.print();
		if (!siteRequest.isInitialized()) {
			return "error";
		}

		if (isOfficialDomain(siteRequest.getDomain())) {
			return "100";
		}

		return "0";
	}

	public boolean isOfficialDomain(String Domain) {
		return validDomainsServices.isOfficialDomain(Domain);
	}

	public double isIntentionallyMisspelledDomain(ValidDomainsEntity validDomainsEntity, String domainName) {

	}

	public double percentageMatchingLogo(ValidDomainsEntity validDomainsEntity, List<String> imageURLs) {

	}

	public double percentageMatchingText(ValidDomainsEntity validDomainsEntity, List<String> siteText) {

	}

	public double percentageMatchingColor(ValidDomainsEntity validDomainsEntity, List<String> siteColors) {

	}

	@RequestMapping("/LogoDecoder")
	public static String logoDecoder(@RequestParam(value = "testval") String testval) throws IOException {
		byte[] decodedBytes = Base64.getDecoder().decode(testval);
		String decodedString = new String(decodedBytes);
		return detectLogos(decodedString);
	}

	public static String detectLogos(String URL) {
		String extension = URL.substring(URL.lastIndexOf(".") + 1);
		if (GC_SUPPORTED_FILE_TYPE_EXTENSIONS.contains(extension.toUpperCase())) {
			try {
				return detectLogosURLs(URL);
			} catch (IOException e) {

			}
		} else {
			FileDownloader fileDownloader = new FileDownloader(URL);
			if (fileDownloader.getConvertedFile() != null) {
				String response;
				try {
					if (fileDownloader.getConvertedFile() != null) {
						if (fileDownloader.getConvertedFile().exists()) {
							response = detectLogosBase64(fileDownloader.getConvertedFile().getAbsolutePath());
							fileDownloader.delete();
							return response;
						} else {
							return "Failed to Convert File";
						}
					} else {
						return "Failed to Convert File";
					}
				} catch (IOException e) {
					return "Unsupported File";
				}
			}
			return "Could Not Get Image File";
		}
		return "Could Not Get Image File";
	}

	public static String detectLogosURLs(String url) throws IOException {
		String responseString = "";

		List<AnnotateImageRequest> requests = new ArrayList<>();

		ImageSource imgSource = ImageSource.newBuilder().setImageUri(url).build();
		Image img = Image.newBuilder().setSource(imgSource).build();
		Feature feat = Feature.newBuilder().setType(Feature.Type.LOGO_DETECTION).build();
		AnnotateImageRequest request = AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
		requests.add(request);
		try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
			BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
			List<AnnotateImageResponse> responses = response.getResponsesList();

			for (AnnotateImageResponse res : responses) {
				if (res.hasError()) {
					String tempMsg = res.getError().getMessage();
					responseString = responseString + "\n" + tempMsg;
					System.out.format("Error: %s%n", tempMsg);
					client.close();
					return responseString;
				}

				// For full list of available annotations, see http://g.co/cloud/vision/docs
				for (EntityAnnotation annotation : res.getLogoAnnotationsList()) {
					String tempMsg = annotation.getDescription();
					responseString = responseString + "\n" + tempMsg;
					System.out.println(tempMsg);
				}
			}
			client.close();
		}
		return responseString;
	}

	// Detects logos in the specified local image.

	public static String detectLogosBase64(String filePath) throws IOException {
		List<AnnotateImageRequest> requests = new ArrayList<>();

		ByteString imgBytes = ByteString.readFrom(new FileInputStream(filePath));

		Image img = Image.newBuilder().setContent(imgBytes).build();
		Feature feat = Feature.newBuilder().setType(Feature.Type.LOGO_DETECTION).build();
		AnnotateImageRequest request = AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
		requests.add(request);
		String finalOutput = "";
		try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
			BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
			List<AnnotateImageResponse> responses = response.getResponsesList();

			for (AnnotateImageResponse res : responses) {
				if (res.hasError()) {
					System.out.format("Error: %s%n", res.getError().getMessage());
					client.close();
					return res.getError().getMessage();
				}

				// For full list of available annotations, see http://g.co/cloud/vision/docs
				for (EntityAnnotation annotation : res.getLogoAnnotationsList()) {
					finalOutput = finalOutput + "\n" + annotation.getDescription();
				}
			}
			client.close();
			return finalOutput;
		}
	}

}
