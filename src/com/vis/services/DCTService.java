/**
 *
 */
package com.vis.services;

import java.awt.image.BufferedImage;

import com.vis.models.InputModel;

/**
 * @author Vis
 *
 */
public interface DCTService {
	float[][][][] encode(BufferedImage inputImg, InputModel inputModel);

	float[][][][] decode(float[][][][] encodedBlock, InputModel inputModel);

	BufferedImage getDecodedImage(float[][][][] decodedBlock);
}
