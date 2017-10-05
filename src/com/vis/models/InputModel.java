/**
 *
 */
package com.vis.models;

/**
 * @author Vis
 *
 */
public class InputModel {

	private String fileName;
	private int noOfCoefficient;
	private int height;
	private int width;

	public InputModel() {
		height = 512;
		width = 512;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InputModel [fileName=").append(fileName).append(", noOfCoefficient=").append(noOfCoefficient)
		.append("]");
		return builder.toString();
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getNoOfCoefficient() {
		return noOfCoefficient;
	}

	public void setNoOfCoefficient(int noOfCoefficient) {
		this.noOfCoefficient = noOfCoefficient;
	}
}
