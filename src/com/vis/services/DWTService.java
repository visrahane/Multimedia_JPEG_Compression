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

	float[][][] encode(BufferedImage inputImg, InputModel inputModel);

	float[][][] decode(float[][][] colorMatrix, InputModel inputModel);

	BufferedImage getDecodedImage(float[][][] decodedBlock);
}
