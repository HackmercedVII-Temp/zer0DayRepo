package com.example.demo.Dependency;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.UUID;

import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
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
				convertFormat(imageFile.getAbsolutePath(), convertedFile.getAbsolutePath());
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

	public boolean convertFormat(String inputImagePath, String outputImagePath) {
		String extension = inputImagePath.substring(inputImagePath.lastIndexOf(".") + 1);
		extension = extension.toLowerCase();
		switch (extension) {
		case "svg":
			return SVGtoPNG(inputImagePath, outputImagePath);
		default:
			convertedFile = null;
			return false;
		}
	}

	public boolean SVGtoPNG(String inputImagePath, String outputImagePath) {
		TranscoderInput transcoderInput = new TranscoderInput(imageUrl);
		try {
			OutputStream outputStream = new FileOutputStream(outputImagePath);
			TranscoderOutput transcoderOutput = new TranscoderOutput(outputStream);

			PNGTranscoder pngTranscoder = new PNGTranscoder();
			pngTranscoder.transcode(transcoderInput, transcoderOutput);

			outputStream.flush();
			outputStream.close();
			convertedFile = new File(outputImagePath);
			if (!convertedFile.exists()) {
				System.out.println("Svg not converted");
				convertedFile = null;
				return false;
			} else {
				System.out.println("Svg converted to " + outputImagePath);
				return true;
			}
		} catch (Exception e) {
			convertedFile = null;
			return false;
		}
	}

}
