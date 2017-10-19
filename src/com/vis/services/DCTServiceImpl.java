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
public class DCTServiceImpl implements DCTService {

	@Override
	public float[][][][] encode(BufferedImage inputImg, InputModel inputModel) {
		float[][][][] colorBlock = CompressionUtil.prepare8x8Blocks(inputImg);
		colorBlock = encodeBlock(colorBlock);
		return colorBlock;
	}

	@Override
	public float[][][][] getCoefficientsInZigZagOrder(float[][][][] colorBlock, int coeff) {
		float[][][][] processedBlock = new float[3][colorBlock[0].length][colorBlock[0][0].length][colorBlock[0][0][0].length];
		coeff = Math.round(coeff / colorBlock[0].length);
		for (int l = 0, count; l < 3; l++) {
			for (int b = 0; b < colorBlock[0].length; b++) {
				count = 1;
				for (int k = 0; k < colorBlock[0][0][0].length; k++) {
					if (k % 2 == 0) {// i goes reverse and j in forward
						for (int j = 0, i = k; j <= k && i > -1; j++, i--) {
							if (count > coeff) {
								break;
							} else {
								count++;
								processedBlock[l][b][i][j] = colorBlock[l][b][i][j];
							}
						}
					} else {
						for (int i = 0, j = k; i <= k && j > -1; i++, j--) {
							if (count > coeff) {
								break;
							} else {
								count++;
								processedBlock[l][b][i][j] = colorBlock[l][b][i][j];
							}
						}
					}
				}

				for (int k = 1; k < colorBlock[0][0][0].length; k++) {
					if (k % 2 == 1) {// i goes reverse and j in forward
						for (int j = k, i = colorBlock[0][0].length - 1; j < colorBlock[0][0].length && i >= k; j++, i--) {
							if (count > coeff) {
								break;
							} else {
								count++;
								processedBlock[l][b][i][j] = colorBlock[l][b][i][j];
							}
						}
					} else {
						for (int i = k, j = colorBlock[0][0].length - 1; i < colorBlock[0][0].length && j >= k; i++, j--) {
							if (count > coeff) {
								break;
							} else {
								count++;
								processedBlock[l][b][i][j] = colorBlock[l][b][i][j];
							}
						}
					}
				}

			}
		}
		return processedBlock;
	}

	public float[][][][] encodeBlock(float[][][][] input) {
		// run forward dct
		float[][][][] dctProcessedBlock = new float[3][input[0].length][input[0][0].length][input[0][0][0].length];

		for (int i = 0; i < input[0].length; i++)// for each block
		{
			for (int u = 0; u < input[0][0].length; u++) { // for each row of block
				for (int v = 0; v < input[0][0][0].length; v++) { // for each col
					float sum[] = new float[3];
					// perform dct
					for (int c = 0; c < 3; c++) {
						for (int x = 0; x < input[0][0].length; x++) {
							for (int y = 0; y < input[0][0][0].length; y++) {
								sum[c] += (float) ((input[c][i][x][y])
										* Math.cos((2 * x + 1) * u * Math.PI * 0.0625)
										* Math.cos((2 * y + 1) * v * Math.PI * 0.0625));
							}
						}
					}
					// for each color
					for (int c = 0; c < 3; c++) {
						if (u == 0 ) {
							sum[c]= (float) ((1 / Math.sqrt(2)) * sum[c]);
						}
						if(v==0) {
							sum[c]= (float) ((1 / Math.sqrt(2)) * sum[c]);

						}
						dctProcessedBlock[c][i][u][v] = ((float) 1 / 4) * sum[c];

					}
				}


			}

		}


		return dctProcessedBlock;
	}

	@Override
	public float[][][][] decode(float[][][][] encodedBlock, InputModel inputModel) {
		// run inverse dct
		encodedBlock = decodeBlock(encodedBlock);
		encodedBlock = clipValuesBeyondRange(encodedBlock);
		return encodedBlock;
	}

	private float[][][][] clipValuesBeyondRange(float[][][][] encodedBlock) {

		for (int i = 0; i < encodedBlock.length; i++) {
			for (int b = 0; b < encodedBlock[0].length; b++) {
				for (int j = 0; j < encodedBlock[0][0].length; j++) {
					for (int k = 0; k < encodedBlock[0][0][0].length; k++) {
						if (encodedBlock[i][b][j][k] > 255) {
							encodedBlock[i][b][j][k] = 255;
						}
						if (encodedBlock[i][b][j][k] < 0) {
							encodedBlock[i][b][j][k] = 0;
						}
					}
				}
			}
		}
		return encodedBlock;

	}

	public float[][][][] decodeBlock(float[][][][] encodedBlock) {

		// run reverse dct
		float[][][][] dctProcessedBlock = new float[3][encodedBlock[0].length][encodedBlock[0][0].length][encodedBlock[0][0][0].length];

		for (int i = 0; i < encodedBlock[0].length; i++)// for each block
		{
			for (int x = 0; x < encodedBlock[0][0].length; x++) { //for each row of block

				for (int y = 0; y < encodedBlock[0][0][0].length; y++) { // for each col
					float sum[] = new float[3];
					// perform reverse dct
					for (int c = 0; c < 3; c++) {
						for (int u = 0; u < encodedBlock[0][0].length; u++) {
							for (int v = 0; v < encodedBlock[0][0][0].length; v++) {
								float temp= (float) (encodedBlock[c][i][u][v]
										* (Math.cos((2 * x + 1) * u * Math.PI * 0.0625)
												* (Math.cos((2 * y + 1) * v * Math.PI * 0.0625))));
								if (u == 0 ) {
									temp /= Math.sqrt(2);
								}
								if(v==0){
									temp /= Math.sqrt(2);
								}
								sum[c]+=temp;
							}
						}
					}
					// for each color
					for (int c = 0; c < 3; c++) {
						dctProcessedBlock[c][i][x][y] = ((float) 1 / 4) * sum[c];
					}


				}


			}
		}


		return dctProcessedBlock;

	}

	@Override
	public BufferedImage getDecodedImage(float[][][][] decodedBlock) {
		BufferedImage outputImage = new BufferedImage(512, 512,
				BufferedImage.TYPE_INT_RGB);

		for (int i = 0; i < decodedBlock[0].length; i++) { // for each block
			for (int j = 0; j < decodedBlock[0][0].length; j++) {
				for (int k = 0; k < decodedBlock[0][0][0].length; k++) {
					int pix = CompressionUtil.getPixel((int) Math.floor(decodedBlock[0][i][j][k]),
							(int) Math.floor(decodedBlock[1][i][j][k]), (int) Math.floor(decodedBlock[2][i][j][k]));
					int x = (i % 64) * 8 + k;
					int y = ((int) (Math.floor(i / 64))) * 8 + j;
					//System.out.println("i-" + i + " j-" + j + " k-" + k + "x-" + x + " y-" + y + " pix-" + pix);
					outputImage.setRGB(x, y, pix);
				}

			}
		}

		return outputImage;

	}


}
