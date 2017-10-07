package com.vis.util;
import java.awt.Color;
import java.awt.image.BufferedImage;

import com.vis.constants.CompressionConstants;

/**
 *
 */

/**
 * @author Vis
 *
 */
public class CompressionUtil {

	public static float[][][] prepareColorBlock(BufferedImage inputImg) {
		float block[][][] = new float[3][inputImg.getHeight()][inputImg.getWidth()];
		for (int y = 0; y < inputImg.getHeight(); y++) {
			for (int x = 0; x < inputImg.getWidth(); x++) {
				Color c = new Color(inputImg.getRGB(x, y));
				block[0][y][x] = c.getRed();
				block[1][y][x] = c.getGreen();
				block[2][y][x] = c.getBlue();
			}
		}
		return block;
	}

	public static void clone3DArray(float[][][] inputArray, float[][][] outputArray) {
		for (int i = 0; i < inputArray.length; i++) {
			for (int j = 0; j < inputArray[0].length; j++) {
				outputArray[i][j] = inputArray[i][j].clone();
			}
		}
	}

	public static float[][][][] prepare8x8Blocks(BufferedImage inputImg) {
		int noOfBlocks = inputImg.getHeight() * inputImg.getWidth() / CompressionConstants.JPEG_BLOCK_SIZE;
		float block[][][][] = new float[3][noOfBlocks][CompressionConstants.JPEG_BLOCK_LENGTH][CompressionConstants.JPEG_BLOCK_LENGTH];
		for (int i = 0; i < block[0].length; i++) { // for each block
			for (int j = 0; j < block[0][0].length; j++) {
				for (int k = 0; k < block[0][0][0].length; k++) {
					int x = (i % 64) * 8 + k;
					int y = ((int) (Math.floor(i / 64))) * 8 + j;
					//System.out.println("x-" + x + "y-" + y + " i-" + i + "j-" + j + " k-" + k);
					Color c = new Color(inputImg.getRGB(x, y));
					block[0][i][j][k] = c.getRed();
					block[1][i][j][k] = c.getGreen();
					block[2][i][j][k] = c.getBlue();
				}

			}
		}

		return block;
	}

	public static int getPixel(int red, int green, int blue) {
		return 0xff000000 | ((red & 0xff) << 16) | ((green & 0xff) << 8) | (blue & 0xff);
	}

	public static byte[][][][] getByteArray(float[][][][] colorBlock) {
		byte[][][][] input = new byte[3][1][8][8];
		for (int c = 0; c < 3; c++) {
			for (int i = 0; i < 1; i++) {
				for (int j = 0; j < 8; j++) {
					for (int k = 0; k < 8; k++) {
						input[c][i][j][k] = (byte) colorBlock[c][i][j][k];
					}
				}
			}
		}
		return input;
	}

}
