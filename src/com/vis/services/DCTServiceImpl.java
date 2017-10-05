/**
 *
 */
package com.vis.services;

import java.awt.image.BufferedImage;

/**
 * @author Vis
 *
 */
public class DCTServiceImpl implements DCTService {

	@Override
	public BufferedImage runDWT(BufferedImage inputImg) {
		// TODO Auto-generated method stub
		int[][] redBlock = prepare8x8Block();
		return inputImg;
	}

	private int[][] prepare8x8Block() {
		// TODO Auto-generated method stub
		return null;
	}

}
