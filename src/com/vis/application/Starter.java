/**
 *
 */
package com.vis.application;

import java.awt.image.BufferedImage;

import com.vis.constants.CompressionConstants;
import com.vis.models.InputModel;
import com.vis.readers.ImageReader;
import com.vis.services.DCTService;
import com.vis.services.DCTServiceImpl;
import com.vis.services.DWTService;
import com.vis.services.DWTServiceImpl;
import com.vis.services.ImageDisplayService;

/**
 * @author Vis
 *
 */
public class Starter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		InputModel inputModel = getInput(args);
		BufferedImage inputImg = ImageReader.readRGBImage(inputModel);
		ImageDisplayService inputDisplayService = new ImageDisplayService("Input Image");
		inputDisplayService.displayImage(inputImg);

		if (CompressionConstants.PROGRESSIVE_SCANNING == inputModel.getNoOfCoefficient()) {
			runProgressiveScanning(inputModel, inputImg);
		} else {
			runNormalScanning(inputModel, inputImg);
		}

	}

	private static void runNormalScanning(InputModel inputModel, BufferedImage inputImg) {
		DWTService dwtService = new DWTServiceImpl();

		float[][][] encodedBlock = dwtService.encode(inputImg, inputModel);
		encodedBlock = dwtService.getCoefficientsInZigzagOrder(encodedBlock, inputModel.getNoOfCoefficient());
		float[][][] decodedBlock = dwtService.decode(encodedBlock, inputModel);

		BufferedImage dwtOutput = dwtService.getDecodedImage(decodedBlock);
		ImageDisplayService outputDisplayService = new ImageDisplayService("Output Image");
		outputDisplayService.displayImage(dwtOutput);

		runDCT(inputModel, inputImg);

	}


	private static void runDCT(InputModel inputModel, BufferedImage inputImg) {
		DCTService dctService = new DCTServiceImpl();
		float[][][][] encodedDCTBlock = dctService.encode(inputImg, inputModel);
		float[][][][] decodedDCTBlock = dctService.decode(encodedDCTBlock, inputModel);
		BufferedImage dctOutput = dctService.getDecodedImage(decodedDCTBlock);
		ImageDisplayService outputDisplayService = new ImageDisplayService("Output Image");
		outputDisplayService.displayImage(dctOutput);
	}

	private static void runProgressiveScanning(InputModel inputModel, BufferedImage inputImg) {
		DWTService dwtService = new DWTServiceImpl();
		ImageDisplayService outputDisplayService = new ImageDisplayService("Output Image");

		float[][][] encodedBlock = dwtService.encode(inputImg, inputModel);
		float[][][] processedBlock = new float[3][encodedBlock.length][encodedBlock[0].length];
		for (int i = 4096, j = 0; i <= inputModel.getHeight() * inputModel.getWidth(); j++) {
			processedBlock = dwtService.getCoefficientsInZigzagOrder(encodedBlock, i);
			float[][][] decodedBlock = dwtService.decode(processedBlock, inputModel);
			BufferedImage dwtOutput = dwtService.getDecodedImage(decodedBlock);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			outputDisplayService.displayImage(dwtOutput);
			i *= 2;
		}

		// runDCT(inputModel, inputImg);

	}

	private static InputModel getInput(String[] args) {
		InputModel inputModel = new InputModel();
		inputModel.setFileName(args[0]);
		inputModel.setNoOfCoefficient(Integer.parseInt(args[1]));
		return inputModel;
	}

}
