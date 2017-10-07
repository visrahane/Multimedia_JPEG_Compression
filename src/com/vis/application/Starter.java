/**
 *
 */
package com.vis.application;

import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.vis.models.InputModel;
import com.vis.readers.ImageReader;
import com.vis.services.DCTService;
import com.vis.services.DCTServiceImpl;
import com.vis.services.DWTService;
import com.vis.services.DWTServiceImpl;

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
		DWTService dwtService = new DWTServiceImpl();
		float[][][] encodedBlock=dwtService.encode(inputImg,inputModel);
		float[][][] decodedBlock = dwtService.decode(encodedBlock, inputModel);
		BufferedImage dwtOutput = dwtService.getDecodedImage(decodedBlock);
		DCTService dctService = new DCTServiceImpl();
		float[][][][] encodedDCTBlock = dctService.encode(inputImg, inputModel);
		float[][][][] decodedDCTBlock = dctService.decode(encodedDCTBlock, inputModel);
		BufferedImage dctOutput = dctService.getDecodedImage(decodedDCTBlock);
		displayImage(inputImg);
		displayImage(dctOutput);
		displayImage(dwtOutput);
	}

	private static void displayImage(BufferedImage img) {
		JPanel panel = new JPanel();
		panel.add(new JLabel(new ImageIcon(img)));

		JFrame frame = new JFrame("Display images");

		frame.getContentPane().add(panel);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private static InputModel getInput(String[] args) {
		InputModel inputModel = new InputModel();
		inputModel.setFileName(args[0]);
		inputModel.setNoOfCoefficient(Integer.parseInt(args[1]));
		return inputModel;
	}

}
