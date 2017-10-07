/**
 *
 */
package com.vis.test;

import org.junit.Assert;
import org.junit.Test;

import com.vis.models.InputModel;
import com.vis.services.DWTServiceImpl;
import com.vis.util.CompressionUtil;

/**
 * @author Vis
 *
 */
public class DWTServiceImplTest {

	@Test
	public void testEncodeDecode() {
		DWTServiceImpl dwtService = new DWTServiceImpl();
		float colorBlock[][][] = new float[][][] {
			{ { 10, 20, 30, 40 }, { 10, 20, 30, 40 }, { 10, 20, 30, 40 }, { 10, 20, 30, 40 } },
			{ { 10, 20, 30, 40 }, { 10, 20, 30, 40 }, { 10, 20, 30, 40 }, { 10, 20, 30, 40 } },
			{ { 10, 20, 30, 40 }, { 10, 20, 30, 40 }, { 10, 20, 30, 40 }, { 10, 20, 30, 40 } } };
			float expectedBlock[][][] = new float[3][4][4];
			CompressionUtil.clone3DArray(colorBlock, expectedBlock);
			colorBlock = dwtService.encodeBlock(colorBlock);
			colorBlock =dwtService.decodeBlock(colorBlock);
			Assert.assertArrayEquals(colorBlock, expectedBlock);
	}


	private InputModel prepareInputModel() {
		InputModel inputModel = new InputModel();
		inputModel.setFileName("R:/Study/Masters/Fall 2017/CSCI-576 Multimedia/HW-2/test images/rgb images/Lenna.rgb");
		inputModel.setNoOfCoefficient(16384);
		return inputModel;
	}

	@Test
	public void testEncodeOnCols_shouldReturnValidBlockAfter1stIteration() {
		DWTServiceImpl dwtService = new DWTServiceImpl();
		float colorBlock[][][] = new float[][][] { { { 1, 2, 3, 4 }, { 5, 7, 9, 11 }, { 8, 12, 6, 2 }, { 4, 2, 2, 1 } },
			{ { 1, 2, 3, 4 }, { 5, 7, 9, 11 }, { 8, 12, 6, 2 }, { 4, 2, 2, 1 } },
			{ { 1, 2, 3, 4 }, { 5, 7, 9, 11 }, { 8, 12, 6, 2 }, { 4, 2, 2, 1 } } };
			float[][][] processedBlock = dwtService.encodeOnCols(colorBlock, 4);
			float[][][] expectedBlock = new float[][][] {
				{ { 1.5f, 3.5f, -0.5f, -0.5f }, { 6.0f, 10.0f, -1.0f, -1.0f }, { 10.0f, 4.0f, -2.0f, 2.0f },
					{ 3.0f, 1.5f, 1.0f, .5f } },
				{ { 1.5f, 3.5f, -0.5f, -0.5f }, { 6.0f, 10.0f, -1.0f, -1.0f }, { 10.0f, 4.0f, -2.0f, 2.0f },
						{ 3.0f, 1.5f, 1.0f, 0.5f } },
				{ { 1.5f, 3.5f, -0.5f, -0.5f }, { 6.0f, 10.0f, -1.0f, -1.0f }, { 10.0f, 4.0f, -2.0f, 2.0f },
							{ 3.0f, 1.5f, 1.0f, .5f } } };
							Assert.assertArrayEquals(expectedBlock, processedBlock);
	}

	@Test
	public void testEncodeOnCols_shouldReturnValidBlockAfterAllRowIterations() {
		DWTServiceImpl dwtService = new DWTServiceImpl();
		float colorBlock[][][] = new float[][][] { { { 1, 2, 3, 4 }, { 5, 7, 9, 11 }, { 8, 12, 6, 2 }, { 4, 2, 2, 1 } },
			{ { 1, 2, 3, 4 }, { 5, 7, 9, 11 }, { 8, 12, 6, 2 }, { 4, 2, 2, 1 } },
			{ { 1, 2, 3, 4 }, { 5, 7, 9, 11 }, { 8, 12, 6, 2 }, { 4, 2, 2, 1 } } };
			int size = 4;
			while (size > 1) {
				colorBlock = dwtService.encodeOnCols(colorBlock, size);
				size /= 2;
			}
			float[][][] expectedBlock = new float[][][] {
				{ { 2.5f, -1f, -0.5f, -0.5f }, { 8.0f, -2f, -1.0f, -1.0f }, { 7.0f, 3.0f, -2.0f, 2.0f },
					{ 2.25f, 0.75f, 1.0f, .5f } },
				{ { 2.5f, -1f, -0.5f, -0.5f }, { 8.0f, -2f, -1.0f, -1.0f }, { 7.0f, 3.0f, -2.0f, 2.0f },
						{ 2.25f, 0.75f, 1.0f, 0.5f } },
				{ { 2.5f, -1f, -0.5f, -0.5f }, { 8.0f, -2f, -1.0f, -1.0f }, { 7.0f, 3.0f, -2.0f, 2.0f },
							{ 2.25f, 0.75f, 1.0f, .5f } } };
							Assert.assertArrayEquals(expectedBlock, colorBlock);
	}

	@Test
	public void testEncodeOnRows_shouldReturnValidBlockAfter1stIteration() {
		DWTServiceImpl dwtService = new DWTServiceImpl();
		float colorBlock[][][] = new float[][][] { { { 1, 2, 3, 4 }, { 5, 7, 9, 11 }, { 8, 12, 6, 2 }, { 4, 2, 2, 1 } },
			{ { 1, 2, 3, 4 }, { 5, 7, 9, 11 }, { 8, 12, 6, 2 }, { 4, 2, 2, 1 } },
			{ { 1, 2, 3, 4 }, { 5, 7, 9, 11 }, { 8, 12, 6, 2 }, { 4, 2, 2, 1 } } };
			float[][][] processedBlock = dwtService.encodeOnRows(colorBlock, 4);
			float[][][] expectedBlock = new float[][][] {
				{ { 3f, 4.5f, 6f, 7.5f }, { 6.0f, 7.0f, 4f, 1.5f }, { -2f, -2.5f, -3.0f, -3.5f },
					{ 2.0f, 5f, 2f, .5f } },
				{ { 3f, 4.5f, 6f, 7.5f }, { 6.0f, 7.0f, 4f, 1.5f }, { -2f, -2.5f, -3.0f, -3.5f },
						{ 2.0f, 5f, 2f, .5f } },
				{ { 3f, 4.5f, 6f, 7.5f }, { 6.0f, 7.0f, 4f, 1.5f }, { -2f, -2.5f, -3.0f, -3.5f },
							{ 2.0f, 5f, 2f, .5f } } };
							Assert.assertArrayEquals(expectedBlock, processedBlock);
	}

	@Test
	public void testEncodeOnRows_shouldReturnValidBlockAfterAllRowIterations() {
		DWTServiceImpl dwtService = new DWTServiceImpl();
		float colorBlock[][][] = new float[][][] { { { 1, 2, 3, 4 }, { 5, 7, 9, 11 }, { 8, 12, 6, 2 }, { 4, 2, 2, 1 } },
			{ { 1, 2, 3, 4 }, { 5, 7, 9, 11 }, { 8, 12, 6, 2 }, { 4, 2, 2, 1 } },
			{ { 1, 2, 3, 4 }, { 5, 7, 9, 11 }, { 8, 12, 6, 2 }, { 4, 2, 2, 1 } } };
			int size = 4;
			while (size > 1) {
				colorBlock = dwtService.encodeOnRows(colorBlock, size);
				size /= 2;
			}
			float[][][] expectedBlock = new float[][][] {
				{ { 4.5f, 5.75f, 5f, 4.5f }, { -1.5f, -1.25f, 1.0f, 3f }, { -2f, -2.5f, -3.0f, -3.5f },
					{ 2f, 5f, 2f, .5f } },
				{ { 4.5f, 5.75f, 5f, 4.5f }, { -1.5f, -1.25f, 1.0f, 3f }, { -2f, -2.5f, -3.0f, -3.5f },
						{ 2f, 5f, 2f, .5f } },
				{ { 4.5f, 5.75f, 5f, 4.5f }, { -1.5f, -1.25f, 1.0f, 3f }, { -2f, -2.5f, -3.0f, -3.5f },
							{ 2f, 5f, 2f, .5f } } };

							Assert.assertArrayEquals(expectedBlock, colorBlock);
	}

	@Test
	public void testUpdateCoefficient_shouldReturnValidBlock() {
		float colorBlock[][][] = new float[][][] {
			{ { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 }, { 13, 14, 15, 16 } },
			{ { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 }, { 13, 14, 15, 16 } },
			{ { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 }, { 13, 14, 15, 16 } } };

			DWTServiceImpl dwtService = new DWTServiceImpl();
			colorBlock = dwtService.updateCoefficients(colorBlock, 8);
			float[][][] expectedBlock = { { { 1, 2, 3, 4 }, { 5, 6, 7, 0 }, { 9, 0, 0, 0 }, { 0, 0, 0, 0 } },
					{ { 1, 2, 3, 4 }, { 5, 6, 7, 0 }, { 9, 0, 0, 0 }, { 0, 0, 0, 0 } },
					{ { 1, 2, 3, 4 }, { 5, 6, 7, 0 }, { 9, 0, 0, 0 }, { 0, 0, 0, 0 } } };

			Assert.assertArrayEquals(expectedBlock, colorBlock);
	}

	@Test
	public void testDecodeOnCols_shouldReturnValidBlockAfter1stIteration() {
		DWTServiceImpl dwtService = new DWTServiceImpl();
		float colorBlock[][][] = new float[][][] {
			{ { 1.5f, 3.5f, -0.5f, -0.5f }, { 6.0f, 10.0f, -1.0f, -1.0f }, { 10.0f, 4.0f, -2.0f, 2.0f },
				{ 3.0f, 1.5f, 1.0f, .5f } },
			{ { 1.5f, 3.5f, -0.5f, -0.5f }, { 6.0f, 10.0f, -1.0f, -1.0f }, { 10.0f, 4.0f, -2.0f, 2.0f },
					{ 3.0f, 1.5f, 1.0f, 0.5f } },
			{ { 1.5f, 3.5f, -0.5f, -0.5f }, { 6.0f, 10.0f, -1.0f, -1.0f }, { 10.0f, 4.0f, -2.0f, 2.0f },
						{ 3.0f, 1.5f, 1.0f, .5f } } };

						float[][][] processedBlock = dwtService.decodeOnCols(colorBlock, 4);

						float[][][] expectedBlock = new float[][][] {
							{ { 1, 2, 3, 4 }, { 5, 7, 9, 11 }, { 8, 12, 6, 2 }, { 4, 2, 2, 1 } },
							{ { 1, 2, 3, 4 }, { 5, 7, 9, 11 }, { 8, 12, 6, 2 }, { 4, 2, 2, 1 } },
							{ { 1, 2, 3, 4 }, { 5, 7, 9, 11 }, { 8, 12, 6, 2 }, { 4, 2, 2, 1 } } };

							Assert.assertArrayEquals(expectedBlock, processedBlock);
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

	@Test
	public void testDecodeOnRows_shouldReturnValidBlockAfterAllRowIterations() {
		DWTServiceImpl dwtService = new DWTServiceImpl();
		float colorBlock[][][] = new float[][][] {
			{ { 4.5f, 5.75f, 5f, 4.5f }, { -1.5f, -1.25f, 1.0f, 3f }, { -2f, -2.5f, -3.0f, -3.5f },
				{ 2f, 5f, 2f, .5f } },
			{ { 4.5f, 5.75f, 5f, 4.5f }, { -1.5f, -1.25f, 1.0f, 3f }, { -2f, -2.5f, -3.0f, -3.5f },
					{ 2f, 5f, 2f, .5f } },
			{ { 4.5f, 5.75f, 5f, 4.5f }, { -1.5f, -1.25f, 1.0f, 3f }, { -2f, -2.5f, -3.0f, -3.5f },
						{ 2f, 5f, 2f, .5f } } };


						for (int size = 2; size <= colorBlock[0].length;) {
							colorBlock = dwtService.decodeOnRows(colorBlock, size);
							size *= 2;
						}

						float[][][] expectedBlock = new float[][][] {
							{ { 1, 2, 3, 4 }, { 5, 7, 9, 11 }, { 8, 12, 6, 2 }, { 4, 2, 2, 1 } },
							{ { 1, 2, 3, 4 }, { 5, 7, 9, 11 }, { 8, 12, 6, 2 }, { 4, 2, 2, 1 } },
							{ { 1, 2, 3, 4 }, { 5, 7, 9, 11 }, { 8, 12, 6, 2 }, { 4, 2, 2, 1 } } };

							Assert.assertArrayEquals(expectedBlock, colorBlock);
	}

}
