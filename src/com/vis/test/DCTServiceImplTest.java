/**
 *
 */
package com.vis.test;

import org.junit.Assert;
import org.junit.Test;

import com.vis.models.InputModel;
import com.vis.services.DCTServiceImpl;
import com.vis.services.DWTServiceImpl;

/**
 * @author Vis
 *
 */
public class DCTServiceImplTest {

	@Test
	public void testEncode() {
		DCTServiceImpl dctService = new DCTServiceImpl();
		float[][][][] expectedBlock = new float[3][1][8][8];
		float[][][][] colorBlock = new float[][][][] {
			{
				{

					{ 139f, 144f, 149f, 153f, 155f, 155f, 155f, 155f },
					{ 144f, 151f, 153f, 156f, 159f, 156f, 156f, 156f },
					{ 150f, 155f, 160f, 163f, 158f, 156f, 156f, 156f },
					{ 159f, 161f, 162f, 160f, 160f, 159f, 159f, 159f },
					{ 159f, 160f, 161f, 162f, 162f, 155f, 155f, 155f },
					{ 161f, 161f, 161f, 161f, 160f, 157f, 157f, 157f },
					{ 162f, 162f, 161f, 163f, 162f, 157f, 157f, 157f },
					{ 162f, 162f, 161f, 161f, 163f, 158f, 158f, 158f }

				}
			},
			{
				{
					{ 139f, 144f, 149f, 153f, 155f, 155f, 155f, 155f },
					{ 144f, 151f, 153f, 156f, 159f, 156f, 156f, 156f },
					{ 150f, 155f, 160f, 163f, 158f, 156f, 156f, 156f },
					{ 159f, 161f, 162f, 160f, 160f, 159f, 159f, 159f },
					{ 159f, 160f, 161f, 162f, 162f, 155f, 155f, 155f },
					{ 161f, 161f, 161f, 161f, 160f, 157f, 157f, 157f },
					{ 162f, 162f, 161f, 163f, 162f, 157f, 157f, 157f },
					{ 162f, 162f, 161f, 161f, 163f, 158f, 158f, 158f }
				}
			},
			{
				{
					{ 139f, 144f, 149f, 153f, 155f, 155f, 155f, 155f },
					{ 144f, 151f, 153f, 156f, 159f, 156f, 156f, 156f },
					{ 150f, 155f, 160f, 163f, 158f, 156f, 156f, 156f },
					{ 159f, 161f, 162f, 160f, 160f, 159f, 159f, 159f },
					{ 159f, 160f, 161f, 162f, 162f, 155f, 155f, 155f },
					{ 161f, 161f, 161f, 161f, 160f, 157f, 157f, 157f },
					{ 162f, 162f, 161f, 163f, 162f, 157f, 157f, 157f },
					{ 162f, 162f, 161f, 161f, 163f, 158f, 158f, 158f }
				}
			}
		};
		byte[][][][] input = getByteArray(colorBlock);
		colorBlock = dctService.encodeBlock(colorBlock);
		Assert.assertArrayEquals(expectedBlock, colorBlock);
	}


	private byte[][][][] getByteArray(float[][][][] colorBlock) {
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

	private InputModel prepareInputModel() {
		InputModel inputModel = new InputModel();
		inputModel.setFileName("R:/Study/Masters/Fall 2017/CSCI-576 Multimedia/HW-2/test images/rgb images/Lenna.rgb");
		inputModel.setNoOfCoefficient(16384);
		return inputModel;
	}


	@Test
	public void testUpdateCoefficient_shouldReturnValidBlock() {
		float[][][][] colorBlock = new float[][][][] { { {

			{ 139f, 144f, 149f, 153f, 155f, 155f, 155f, 155f }, { 144f, 151f, 153f, 156f, 159f, 156f, 156f, 156f },
			{ 150f, 155f, 160f, 163f, 158f, 156f, 156f, 156f }, { 159f, 161f, 162f, 160f, 160f, 159f, 159f, 159f },
			{ 159f, 160f, 161f, 162f, 162f, 155f, 155f, 155f }, { 161f, 161f, 161f, 161f, 160f, 157f, 157f, 157f },
			{ 162f, 162f, 161f, 163f, 162f, 157f, 157f, 157f }, { 162f, 162f, 161f, 161f, 163f, 158f, 158f, 158f }

		} },
			{ { { 139f, 144f, 149f, 153f, 155f, 155f, 155f, 155f },
				{ 144f, 151f, 153f, 156f, 159f, 156f, 156f, 156f },
				{ 150f, 155f, 160f, 163f, 158f, 156f, 156f, 156f },
				{ 159f, 161f, 162f, 160f, 160f, 159f, 159f, 159f },
				{ 159f, 160f, 161f, 162f, 162f, 155f, 155f, 155f },
				{ 161f, 161f, 161f, 161f, 160f, 157f, 157f, 157f },
				{ 162f, 162f, 161f, 163f, 162f, 157f, 157f, 157f },
				{ 162f, 162f, 161f, 161f, 163f, 158f, 158f, 158f } } },
			{ { { 139f, 144f, 149f, 153f, 155f, 155f, 155f, 155f },
				{ 144f, 151f, 153f, 156f, 159f, 156f, 156f, 156f },
				{ 150f, 155f, 160f, 163f, 158f, 156f, 156f, 156f },
				{ 159f, 161f, 162f, 160f, 160f, 159f, 159f, 159f },
				{ 159f, 160f, 161f, 162f, 162f, 155f, 155f, 155f },
				{ 161f, 161f, 161f, 161f, 160f, 157f, 157f, 157f },
				{ 162f, 162f, 161f, 163f, 162f, 157f, 157f, 157f },
				{ 162f, 162f, 161f, 161f, 163f, 158f, 158f, 158f } } } };

				DCTServiceImpl dctService = new DCTServiceImpl();
		colorBlock = dctService.updateCoefficients(colorBlock, 58);
				float[][][][] expectedBlock = { { {

					{ 139f, 144f, 149f, 153f, 155f, 155f, 155f, 155f },
					{ 144f, 151f, 153f, 156f, 159f, 156f, 156f, 156f },
					{ 150f, 155f, 160f, 163f, 158f, 156f, 156f, 156f },
					{ 159f, 161f, 162f, 160f, 160f, 159f, 159f, 159f },
					{ 159f, 160f, 161f, 162f, 162f, 155f, 155f, 155f },
					{ 161f, 161f, 161f, 161f, 160f, 157f, 157f, 0f },
					{ 162f, 162f, 161f, 163f, 162f, 157f, 0f, 0f },
					{ 162f, 162f, 161f, 161f, 163f, 0f, 0f,0f }

				} },
						{ {

							{ 139f, 144f, 149f, 153f, 155f, 155f, 155f, 155f },
							{ 144f, 151f, 153f, 156f, 159f, 156f, 156f, 156f },
							{ 150f, 155f, 160f, 163f, 158f, 156f, 156f, 156f },
							{ 159f, 161f, 162f, 160f, 160f, 159f, 159f, 159f },
							{ 159f, 160f, 161f, 162f, 162f, 155f, 155f, 155f },
							{ 161f, 161f, 161f, 161f, 160f, 157f, 157f, 0f },
							{ 162f, 162f, 161f, 163f, 162f, 157f, 0f, 0f },
							{ 162f, 162f, 161f, 161f, 163f, 0f, 0f,0f }

						} },
						{ {

							{ 139f, 144f, 149f, 153f, 155f, 155f, 155f, 155f },
							{ 144f, 151f, 153f, 156f, 159f, 156f, 156f, 156f },
							{ 150f, 155f, 160f, 163f, 158f, 156f, 156f, 156f },
							{ 159f, 161f, 162f, 160f, 160f, 159f, 159f, 159f },
							{ 159f, 160f, 161f, 162f, 162f, 155f, 155f, 155f },
							{ 161f, 161f, 161f, 161f, 160f, 157f, 157f, 0f },
							{ 162f, 162f, 161f, 163f, 162f, 157f, 0f, 0f },
							{ 162f, 162f, 161f, 161f, 163f, 0f, 0f,0f }

						} } };

				Assert.assertArrayEquals(expectedBlock, colorBlock);
	}


	@Test
	public void testDecodeOnRows_shouldReturnValidBlockAfter1stIteration() {
		DWTServiceImpl dwtService = new DWTServiceImpl();
		float[][][] colorBlock = new float[][][] {
			{ { 3f, 4.5f, 6f, 7.5f }, { 6.0f, 7.0f, 4f, 1.5f }, { -2f, -2.5f, -3.0f, -3.5f },
				{ 2.0f, 5f, 2f, .5f } },
			{ { 3f, 4.5f, 6f, 7.5f }, { 6.0f, 7.0f, 4f, 1.5f }, { -2f, -2.5f, -3.0f, -3.5f },
					{ 2.0f, 5f, 2f, .5f } },
			{ { 3f, 4.5f, 6f, 7.5f }, { 6.0f, 7.0f, 4f, 1.5f }, { -2f, -2.5f, -3.0f, -3.5f },
						{ 2.0f, 5f, 2f, .5f } } };

						float expectedBlock[][][] = new float[][][] {
							{ { 1, 2, 3, 4 }, { 5, 7, 9, 11 }, { 8, 12, 6, 2 }, { 4, 2, 2, 1 } },
							{ { 1, 2, 3, 4 }, { 5, 7, 9, 11 }, { 8, 12, 6, 2 }, { 4, 2, 2, 1 } },
							{ { 1, 2, 3, 4 }, { 5, 7, 9, 11 }, { 8, 12, 6, 2 }, { 4, 2, 2, 1 } } };

							float[][][] processedBlock = dwtService.decodeOnRows(colorBlock, 4);

							Assert.assertArrayEquals(expectedBlock, processedBlock);
	}
}
