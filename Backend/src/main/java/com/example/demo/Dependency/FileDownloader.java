package com.example.demo.Dependency;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileDownloader {

	private File imageFile, convertedFile;
	private String imageUrl;

	public FileDownloader(String imageURL) {
		this.imageUrl = imageURL;
		String extension = imageURL.substring(imageURL.lastIndexOf("."));
		try {
			imageFile = new File(
					System.getProperty("java.io.tmpdir") + "\\" + UUID.randomUUID().toString() + extension);
			downloadImage(imageUrl);
			if (imageFile.exists()) {
				convertedFile = new File(
						System.getProperty("java.io.tmpdir") + "\\" + UUID.randomUUID().toString() + ".jpeg");
				convertFormat(imageFile.getAbsolutePath(), convertedFile.getAbsolutePath(), "JPEG");
				System.out.println("Flag 4");
				if (convertedFile.exists()) {
					System.out.println("converted file path: " + convertedFile.getAbsolutePath());
					return;
				} else {
					System.out.println("Conversion failed");
					convertedFile = null;
					return;
				}
			} else {
				imageFile = null;
			}
		} catch (Exception e) {
			imageFile = null;
			System.out.print("Failed to get image: \n");
			e.printStackTrace();
		}

	}

	public void delete() {
		if (imageFile != null) {
			if (imageFile.exists()) {
				imageFile.delete();
			}
		}
		if (convertedFile != null) {
			if (convertedFile.exists()) {
				convertedFile.delete();
			}
		}
	}

	public void downloadImage(String sourceUrl) {
		try {
			URL imageUrl = new URL(sourceUrl);
			FileUtils.copyURLToFile(imageUrl, imageFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean convertFormat(String inputImagePath, String outputImagePath, String formatName)
			throws IOException {
		FileInputStream inputStream = new FileInputStream(inputImagePath);
		FileOutputStream outputStream = new FileOutputStream(outputImagePath);

		// reads input image from file
		BufferedImage inputImage = ImageIO.read(inputStream);

		boolean result = ImageIO.write(inputImage, formatName, outputStream);

		// needs to close the streams
		outputStream.close();
		inputStream.close();

		return result;
	}

}
