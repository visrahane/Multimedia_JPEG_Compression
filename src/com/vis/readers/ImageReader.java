/**
 *
 */
package com.vis.readers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.vis.models.InputModel;


public class ImageReader {


	public static BufferedImage readRGBImage(InputModel inputModel) {
		BufferedImage img = new BufferedImage(inputModel.getWidth(), inputModel.getHeight(),
				BufferedImage.TYPE_INT_RGB);

		try {
			File file = new File(inputModel.getFileName());
			InputStream is = new FileInputStream(file);

			long len = file.length();
			byte[] bytes = new byte[(int)len];

			int offset = 0;
			int numRead = 0;
			while (offset < bytes.length && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
				offset += numRead;
			}


			int ind = 0;
			for (int y = 0; y < inputModel.getHeight(); y++) {

				for (int x = 0; x < inputModel.getWidth(); x++) {

					byte a = 0;
					byte r = bytes[ind];
					byte g = bytes[ind + inputModel.getHeight() * inputModel.getWidth()];
					byte b = bytes[ind + inputModel.getHeight() * inputModel.getWidth() * 2];

					int pix = 0xff000000 | ((r & 0xff) << 16) | ((g & 0xff) << 8) | (b & 0xff);
					//int pix = ((a << 24) + (r << 16) + (g << 8) + b);
					// System.out.println("pixel:"+pix);
					img.setRGB(x,y,pix);
					ind++;
				}
			}


		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}

	public static BufferedImage readBMPImage(InputModel inputModel) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(inputModel.getFileName()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return image;

	}
}