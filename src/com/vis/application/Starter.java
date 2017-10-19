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
			runScanning(inputModel, inputImg, 4096, 512 * 512);
		} else {
			runScanning(inputModel, inputImg, inputModel.getNoOfCoefficient(), inputModel.getNoOfCoefficient());
		}

	}

	private static void runScanning(InputModel inputModel, BufferedImage inputImg, int initialNoOfCoeff,
			int noOfCoeff) {
		runDWT(inputModel, inputImg, initialNoOfCoeff, noOfCoeff);

		runDCT(inputModel, inputImg, initialNoOfCoeff, noOfCoeff);

	}

	private static void runDCT(InputModel inputModel, BufferedImage inputImg, int initialNoOfCoeff, int noOfCoeff) {
		DCTService dctService = new DCTServiceImpl();
		ImageDisplayService outputDisplayService = new ImageDisplayService("DCT Output Image");

		float[][][][] encodedDCTBlock = dctService.encode(inputImg, inputModel);
		float[][][][] processedDCTBlock = new float[3][encodedDCTBlock.length][encodedDCTBlock[0].length][encodedDCTBlock[0][0].length];
		for (int i = initialNoOfCoeff; i <= noOfCoeff;) {
			processedDCTBlock = dctService.getCoefficientsInZigZagOrder(encodedDCTBlock, i);
			float[][][][] decodedDCTBlock = dctService.decode(processedDCTBlock, inputModel);
			BufferedImage dctOutput = dctService.getDecodedImage(decodedDCTBlock);
			outputDisplayService.displayImage(dctOutput);
			i *= 2;
		}
	}

	private static void runDWT(InputModel inputModel, BufferedImage inputImg, int initialNoOfCoeff,
			int noOfCoeff) {
		DWTService dwtService = new DWTServiceImpl();
		ImageDisplayService outputDisplayService = new ImageDisplayService("DWT Output Image");

		float[][][] encodedBlock = dwtService.encode(inputImg, inputModel);
		float[][][] processedBlock = new float[3][encodedBlock.length][encodedBlock[0].length];
		for (int i = initialNoOfCoeff; i <= noOfCoeff;) {
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
	}

	private static InputModel getInput(String[] args) {
		InputModel inputModel = new InputModel();
		inputModel.setFileName(args[0]);
		inputModel.setNoOfCoefficient(Integer.parseInt(args[1]));
		return inputModel;
	}

}
