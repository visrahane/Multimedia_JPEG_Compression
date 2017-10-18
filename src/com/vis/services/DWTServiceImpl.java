/**
 *
 */
package com.vis.services;

import java.awt.image.BufferedImage;

import com.vis.models.InputModel;
import com.vis.util.CompressionUtil;

/**
 * @author Vis
 *
 */
public class DWTServiceImpl implements DWTService {

	@Override
	public float[][][] encode(BufferedImage inputImg, InputModel inputModel) {
		float[][][] colorBlock = CompressionUtil.prepareColorBlock(inputImg);
		colorBlock = encodeBlock(colorBlock);
		return colorBlock;
	}

	@Override
	public float[][][] getCoefficientsInZigzagOrder(float[][][] colorBlock, int coeff) {
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

	public float[][][] encodeBlock(float[][][] colorBlock) {
		int size = colorBlock[0][0].length;
		// process cols
		while (size > 1) {
			colorBlock = encodeOnCols(colorBlock, size);
			size/=2;
		}
		// process rows
		size = colorBlock[0].length;
		while (size > 1) {
			colorBlock = encodeOnRows(colorBlock, size);
			size /= 2;
		}
		return colorBlock;
	}

	public float[][][] encodeOnRows(float[][][] colorBlock, int size) {
		float[][][] dwtProcessedBlock = new float[3][colorBlock[0].length][colorBlock[0][0].length];
		CompressionUtil.clone3DArray(colorBlock, dwtProcessedBlock);

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

	public float[][][] encodeOnCols(float[][][] colorBlock, int size) {
		float[][][] dwtProcessedBlock = new float[3][colorBlock[0].length][colorBlock[0][0].length];
		CompressionUtil.clone3DArray(colorBlock, dwtProcessedBlock);

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

	public float[][][] decodeOnCols(float[][][] colorBlock, int size) {
		float[][][] dwtProcessedBlock = new float[3][colorBlock[0].length][colorBlock[0][0].length];
		CompressionUtil.clone3DArray(colorBlock, dwtProcessedBlock);

		for (int i = 0; i < colorBlock.length; i++) {
			for (int j = 0; j < colorBlock[0].length; j++) {
				// run averages
				for (int k = 0, temp = size / 2; k < size / 2; k++, temp++) {
					dwtProcessedBlock[0][j][k * 2] = (colorBlock[0][j][k] + colorBlock[0][j][temp]);
					dwtProcessedBlock[1][j][k * 2] = (colorBlock[1][j][k] + colorBlock[1][j][temp]);
					dwtProcessedBlock[2][j][k * 2] = (colorBlock[2][j][k] + colorBlock[2][j][temp]);
				}
				// run diff
				for (int k = 0, temp = size / 2; k < size / 2; k++, temp++) {
					dwtProcessedBlock[0][j][k * 2 + 1] = (colorBlock[0][j][k] - colorBlock[0][j][temp]);
					dwtProcessedBlock[1][j][k * 2 + 1] = (colorBlock[1][j][k] - colorBlock[1][j][temp]);
					dwtProcessedBlock[2][j][k * 2 + 1] = (colorBlock[2][j][k] - colorBlock[2][j][temp]);
				}
			}
		}
		return dwtProcessedBlock;
	}

	public float[][][] decodeOnRows(float[][][] colorBlock, int size) {
		float[][][] dwtProcessedBlock = new float[3][colorBlock[0].length][colorBlock[0][0].length];
		CompressionUtil.clone3DArray(colorBlock, dwtProcessedBlock);

		for (int i = 0; i < colorBlock.length; i++) {
			for (int k = 0; k < colorBlock[0][0].length; k++) {
				// run averages
				for (int j = 0, temp = size / 2; j < size / 2; j++, temp++) {
					dwtProcessedBlock[0][j * 2][k] = (colorBlock[0][j][k] + colorBlock[0][temp][k]);
					dwtProcessedBlock[1][j * 2][k] = (colorBlock[1][j][k] + colorBlock[1][temp][k]);
					dwtProcessedBlock[2][j * 2][k] = (colorBlock[2][j][k] + colorBlock[2][temp][k]);
				}
				// run diff
				for (int j = 0, temp = size / 2; j < size / 2; j++, temp++) {
					dwtProcessedBlock[0][j * 2 + 1][k] = (colorBlock[0][j][k] - colorBlock[0][temp][k]);
					dwtProcessedBlock[1][j * 2 + 1][k] = (colorBlock[1][j][k] - colorBlock[1][temp][k]);
					dwtProcessedBlock[2][j * 2 + 1][k] = (colorBlock[2][j][k] - colorBlock[2][temp][k]);
				}
			}
		}
		return dwtProcessedBlock;
	}

	@Override
	public float[][][] decode(float[][][] colorBlock, InputModel inputModel) {
		// TODO Auto-generated method stub
		colorBlock = decodeBlock(colorBlock);
		colorBlock = clipValuesBeyondRange(colorBlock);
		return colorBlock;
	}

	private float[][][] clipValuesBeyondRange(float[][][] colorBlock) {
		for (int i = 0; i < colorBlock.length; i++) {
			for (int j = 0; j < colorBlock[0].length; j++) {
				for (int k = 0; k < colorBlock[0][0].length; k++) {
					if (colorBlock[i][j][k] > 255) {
						colorBlock[i][j][k] = 255;
					}
					if (colorBlock[i][j][k] < 0) {
						colorBlock[i][j][k] = 0;
					}
				}
			}
		}
		return colorBlock;
	}

	public float[][][] decodeBlock(float[][][] colorBlock) {
		// process rows

		for (int size = 1; size <= colorBlock[0].length;) {
			colorBlock = decodeOnRows(colorBlock, size);
			size *= 2;
		}
		// process cols
		for (int size = 1; size <= colorBlock[0][0].length;) {
			colorBlock = decodeOnCols(colorBlock, size);
			size *= 2;
		}
		return colorBlock;

	}

	@Override
	public BufferedImage getDecodedImage(float[][][] decodedBlock) {
		BufferedImage outputImage = new BufferedImage(decodedBlock[0][0].length, decodedBlock[0].length,
				BufferedImage.TYPE_INT_RGB);
		for (int y = 0; y < outputImage.getHeight(); y++) {
			for (int x = 0; x < outputImage.getWidth(); x++) {
				int pix = CompressionUtil.getPixel((int) Math.floor(decodedBlock[0][y][x]),
						(int) Math.floor(decodedBlock[1][y][x]),
						(int) Math.floor(decodedBlock[2][y][x]));
				outputImage.setRGB(x, y, pix);
			}
		}
		return outputImage;
	}

}
