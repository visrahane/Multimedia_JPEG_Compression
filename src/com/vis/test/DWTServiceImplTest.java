/**
 *
 */
package com.vis.test;

import org.junit.Assert;
import org.junit.Test;

import com.vis.services.DWTServiceImpl;

/**
 * @author Vis
 *
 */
public class DWTServiceImplTest {

	@Test
	public void testApplyDWTOnCols_shouldReturnValidBlockAfter1stIteration() {
		DWTServiceImpl dwtService = new DWTServiceImpl();
		float colorBlock[][][] = new float[][][] { { { 1, 2, 3, 4 }, { 5, 7, 9, 11 }, { 8, 12, 6, 2 }, { 4, 2, 2, 1 } },
			{ { 1, 2, 3, 4 }, { 5, 7, 9, 11 }, { 8, 12, 6, 2 }, { 4, 2, 2, 1 } },
			{ { 1, 2, 3, 4 }, { 5, 7, 9, 11 }, { 8, 12, 6, 2 }, { 4, 2, 2, 1 } } };
			float[][][] processedBlock = dwtService.applyDWTOnCols(colorBlock,4);
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
	public void testApplyDWTOnCols_shouldReturnValidBlockAfterAllRowIterations() {
		DWTServiceImpl dwtService = new DWTServiceImpl();
		float colorBlock[][][] = new float[][][] { { { 1, 2, 3, 4 }, { 5, 7, 9, 11 }, { 8, 12, 6, 2 }, { 4, 2, 2, 1 } },
			{ { 1, 2, 3, 4 }, { 5, 7, 9, 11 }, { 8, 12, 6, 2 }, { 4, 2, 2, 1 } },
			{ { 1, 2, 3, 4 }, { 5, 7, 9, 11 }, { 8, 12, 6, 2 }, { 4, 2, 2, 1 } } };
			int size=4;
			while(size>1){
				colorBlock=dwtService.applyDWTOnCols(colorBlock,size);
				size/=2;
			}
			float[][][] expectedBlock = new float[][][] {
				{ { 2.5f, -1f, -0.5f, -0.5f }, { 8.0f, -2f, -1.0f, -1.0f }, { 7.0f, 3.0f, -2.0f, 2.0f },
					{ 2.25f, 0.75f, 1.0f, .5f } },
				{ { 2.5f, -1f, -0.5f, -0.5f }, { 8.0f, -2f, -1.0f, -1.0f }, { 7.0f, 3.0f, -2.0f, 2.0f },
						{ 2.25f, 0.75f, 1.0f, 0.5f } },
				{{ 2.5f, -1f, -0.5f, -0.5f }, { 8.0f, -2f, -1.0f, -1.0f }, { 7.0f, 3.0f, -2.0f, 2.0f },
							{ 2.25f, 0.75f, 1.0f, .5f } }};
							Assert.assertArrayEquals(expectedBlock, colorBlock);
	}

	@Test
	public void testApplyDWTOnRows_shouldReturnValidBlockAfter1stIteration() {
		DWTServiceImpl dwtService = new DWTServiceImpl();
		float colorBlock[][][] = new float[][][] { { { 1, 2, 3, 4 }, { 5, 7, 9, 11 }, { 8, 12, 6, 2 }, { 4, 2, 2, 1 } },
			{ { 1, 2, 3, 4 }, { 5, 7, 9, 11 }, { 8, 12, 6, 2 }, { 4, 2, 2, 1 } },
			{ { 1, 2, 3, 4 }, { 5, 7, 9, 11 }, { 8, 12, 6, 2 }, { 4, 2, 2, 1 } } };
			float[][][] processedBlock = dwtService.applyDWTOnRows(colorBlock,4);
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
	public void testApplyDWTOnRows_shouldReturnValidBlockAfterAllRowIterations() {
		DWTServiceImpl dwtService = new DWTServiceImpl();
		float colorBlock[][][] = new float[][][] { { { 1, 2, 3, 4 }, { 5, 7, 9, 11 }, { 8, 12, 6, 2 }, { 4, 2, 2, 1 } },
			{ { 1, 2, 3, 4 }, { 5, 7, 9, 11 }, { 8, 12, 6, 2 }, { 4, 2, 2, 1 } },
			{ { 1, 2, 3, 4 }, { 5, 7, 9, 11 }, { 8, 12, 6, 2 }, { 4, 2, 2, 1 } } };
			int size=4;
			while(size>1){
				colorBlock=dwtService.applyDWTOnRows(colorBlock,size);
				size/=2;
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
	public void testUpdateCoefficient_shouldReturnValidBlock(){
		float colorBlock[][][] = new float[][][] {
			{ { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 }, { 13, 14, 15, 16 } },
			{ { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 }, { 13, 14, 15, 16 } },
			{ { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 }, { 13, 14, 15, 16 } } };

			DWTServiceImpl dwtService = new DWTServiceImpl();
			colorBlock = dwtService.updateCoefficients(colorBlock, 8);
			float[][][] expectedBlock={
				{ { 1, 2, 3, 4 }, { 5, 6, 7, 0 }, { 9, 0, 0, 0 }, { 0, 0, 0, 0 } },
				{ { 1, 2, 3, 4 }, { 5, 6, 7, 0 }, { 9, 0, 0, 0 }, { 0, 0, 0, 0 } },
				{ { 1, 2, 3, 4 }, { 5, 6, 7, 0 }, { 9, 0, 0, 0 }, { 0, 0, 0, 0 } } };

			Assert.assertArrayEquals(expectedBlock, colorBlock);
	}

}
