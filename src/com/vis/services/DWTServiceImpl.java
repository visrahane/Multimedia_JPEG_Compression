/**
 *
 */
package com.vis.services;

import java.awt.Color;
import java.awt.image.BufferedImage;

import com.vis.models.InputModel;

/**
 * @author Vis
 *
 */
public class DWTServiceImpl implements DWTService {

	@Override
	public float[][][] encodeViaDWT(BufferedImage inputImg, InputModel inputModel) {
		// TODO Auto-generated method stub
		float[][][] colorBlock = prepareColorBlock(inputImg);
		encodeBlock(inputImg, colorBlock);
		colorBlock = updateCoefficients(colorBlock, inputModel.getNoOfCoefficient());
		return colorBlock;
	}

	public float[][][] updateCoefficients(float[][][] colorBlock, int coeff) {
		float[][][] processedBlock = new float[3][colorBlock[0].length][colorBlock[0][0].length];
		for (int l = 0, count; l < 3; l++) {
			count = 1;
			for (int k = 0; k < colorBlock[0][0].length; k++) {
				if (k % 2 == 0) {// i goes reverse and j in forward
					for (int j = 0, i = k; j <= k && i > -1; j++, i--) {
						if (count > coeff) {
							break;
						} else {
							count++;
							processedBlock[l][i][j] = colorBlock[l][i][j];
						}
					}
				} else {
					for (int i = 0, j = k; i <= k && j > -1; i++, j--) {
						if (count > coeff) {
							break;
						} else {
							count++;
							processedBlock[l][i][j] = colorBlock[l][i][j];
						}
					}
				}
			}

			for (int k = 1; k < colorBlock[0][0].length; k++) {
				if (k % 2 == 1) {// i goes reverse and j in forward
					for (int j = k, i = colorBlock[0][0].length - 1; j < colorBlock[0][0].length && i >= k; j++, i--) {
						if (count > coeff) {
							break;
						} else {
							count++;
							processedBlock[l][i][j] = colorBlock[l][i][j];
						}
					}
				} else {
					for (int i = k, j = colorBlock[0][0].length - 1; i < colorBlock[0][0].length && j >= k; i++, j--) {
						if (count > coeff) {
							break;
						} else {
							count++;
							processedBlock[l][i][j] = colorBlock[l][i][j];
						}
					}
				}
			}

		}
		return processedBlock;
	}

	private void encodeBlock(BufferedImage inputImg, float[][][] colorBlock) {
		int size = inputImg.getHeight();
		// process cols
		while (size > 1) {
			colorBlock = applyDWTOnCols(colorBlock, size);
			size/=2;
		}
		// process rows
		size = inputImg.getHeight();
		while (size > 1) {
			colorBlock = applyDWTOnRows(colorBlock, size);
			size /= 2;
		}
		// return colorBlock;
	}

	public float[][][] applyDWTOnRows(float[][][] colorBlock, int size) {
		float[][][] dwtProcessedBlock = new float[3][colorBlock[0].length][colorBlock[0][0].length];
		clone3DArray(colorBlock, dwtProcessedBlock);

		for (int i = 0; i < colorBlock.length; i++) {
			for (int k = 0; k < colorBlock[0][0].length; k++) {
				// run averages
				for (int j = 0; j < size / 2; j++) {
					dwtProcessedBlock[0][j][k] = (colorBlock[0][j * 2][k] + colorBlock[0][j * 2 + 1][k]) / 2;
					dwtProcessedBlock[1][j][k] = (colorBlock[1][j * 2][k] + colorBlock[1][j * 2 + 1][k]) / 2;
					dwtProcessedBlock[2][j][k] = (colorBlock[2][j * 2][k] + colorBlock[2][j * 2 + 1][k]) / 2;
				}
				// run diff
				for (int j = size / 2, temp = 0; j < size; j++) {
					dwtProcessedBlock[0][j][k] = (colorBlock[0][temp][k] - colorBlock[0][temp + 1][k]) / 2;
					dwtProcessedBlock[1][j][k] = (colorBlock[1][temp][k] - colorBlock[1][temp + 1][k]) / 2;
					dwtProcessedBlock[2][j][k] = (colorBlock[2][temp][k] - colorBlock[2][temp + 1][k]) / 2;
					temp += 2;
				}
			}
		}
		return dwtProcessedBlock;
	}

	private void clone3DArray(float[][][] colorBlock, float[][][] dwtProcessedBlock) {
		for (int i = 0; i < colorBlock.length; i++) {
			for (int j = 0; j < colorBlock[0].length; j++) {
				dwtProcessedBlock[i][j] = colorBlock[i][j].clone();
			}
		}
	}

	public float[][][] applyDWTOnCols(float[][][] colorBlock, int size) {
		float[][][] dwtProcessedBlock = new float[3][colorBlock[0].length][colorBlock[0][0].length];
		clone3DArray(colorBlock, dwtProcessedBlock);

		for(int i=0;i<colorBlock.length;i++){
			for (int j = 0; j < colorBlock[0].length; j++) {
				// run averages
				for (int k = 0; k < size / 2; k++) {
					dwtProcessedBlock[0][j][k] = (colorBlock[0][j][k * 2] + colorBlock[0][j][k * 2 + 1]) / 2;
					dwtProcessedBlock[1][j][k] = (colorBlock[1][j][k * 2] + colorBlock[1][j][k * 2 + 1]) / 2;
					dwtProcessedBlock[2][j][k] = (colorBlock[2][j][k * 2] + colorBlock[2][j][k * 2 + 1]) / 2;
				}
				// run diff
				for (int k = size / 2, temp = 0; k < size; k++) {
					dwtProcessedBlock[0][j][k] = (colorBlock[0][j][temp] - colorBlock[0][j][temp + 1]) / 2;
					dwtProcessedBlock[1][j][k] = (colorBlock[1][j][temp] - colorBlock[1][j][temp + 1]) / 2;
					dwtProcessedBlock[2][j][k] = (colorBlock[2][j][temp] - colorBlock[2][j][temp + 1]) / 2;
					temp += 2;
				}
			}
		}
		return dwtProcessedBlock;
	}

	private float[][][] prepareColorBlock(BufferedImage inputImg) {
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

	@Override
	public float[][][] decodeViaDWT(float[][][] colorBlock, InputModel inputModel) {
		float[][][] processedBlock = new float[3][colorBlock[0].length][colorBlock[0][0].length];
		return processedBlock;
	}

}
