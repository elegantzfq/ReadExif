package org.zfq.readexif;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

public class DrawText {

	static Graphics g = null;
	private static Font font = null;

	private DrawText() {
	}

	static {
		font = new Font("Consolas", Font.PLAIN, 11);
	}

	public static void drawText(String inFilePath, String outFilePath,
			List<String> textStrings, String fileType, int x, int linegap) {
		if (inFilePath == null || outFilePath == null || textStrings == null
				|| fileType == null || x < 0 || linegap < 0) {
			return;
		} else {
			try {
				BufferedImage bi = ImageIO.read(new File(inFilePath));
				g = bi.getGraphics();
				g.setFont(font);
				int y = bi.getHeight() - (linegap * (textStrings.size() - 1))
						- 5;
				for (int i = 0; i < textStrings.size(); i++) {
					g.drawString(textStrings.get(i), x, y + linegap * i);
				}
				FileOutputStream fos = new FileOutputStream(new File(
						outFilePath));
				ImageIO.write(bi, fileType, fos);
				fos.flush();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static Font getFont() {
		return font;
	}

	public static void setFont(Font font) {
		DrawText.font = font;
	}

	public static void main(String[] args) {
		List<String> tt = GetMetaData
				.getInfo(
						"C:\\Work\\Download\\sample.jpg",
						"(^Exposure Time)|(^F-Number)|(^Exposure Program)|(^ISO Speed Ratings)|(^Exposure Bias Value)|(^Max Aperture Value)|(^Metering Mode)|(^White Balance)|(^Flash)|(^Focal Length)|(^Color Space)|(^Exif Image Width)|(^Exif Image Height)|(^Sensing Method)|(^File Source)|(^Scene Type)|(^Make)|(^Model)|(^Orientation)");
		Font f = new Font("news roman", Font.ITALIC|Font.BOLD, 20);
		DrawText.setFont(f);
		DrawText.drawText("C:\\Work\\Download\\sample.jpg",
				"C:\\Work\\Download\\sample1.jpg", tt, "jpg", 5, 25);
	}
}
