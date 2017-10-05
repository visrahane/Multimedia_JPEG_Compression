package com.vis.services;

import java.awt.image.BufferedImage;

import com.vis.models.InputModel;

/**
 *
 */

/**
 * @author Vis
 *
 */
public interface DWTService {
	float[][][] encodeViaDWT(BufferedImage inputImg, InputModel inputModel);

	float[][][] decodeViaDWT(float[][][] colorMatrix, InputModel inputModel);

	BufferedImage getDecodedImage(float[][][] decodedBlock);
}
