package org.zfq.readexif;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

public class GetAllPicInfo {
	
	public static void main(String[] args) {
		GetPictures gp = new GetPictures("C:\\Work\\Download", "jpg");
		List<String> pictures = gp.getPictureNames();
		List<List<String>> all = null;
		if (pictures != null) {
			String filterString = "(^Exposure Time)|(^F-Number)|(^Exposure Program)|(^ISO Speed Ratings)|(^Exposure Bias Value)|(^Max Aperture Value)|(^Metering Mode)|(^White Balance)|(^Flash)|(^Focal Length)|(^Color Space)|(^Exif Image Width)|(^Exif Image Height)|(^Sensing Method)|(^File Source)|(^Scene Type)|(^Make)|(^Model)|(^Orientation)";
			all = new ArrayList<List<String>>();
			for (String name : pictures) {
				// System.out.println(name);
				all.add(GetMetaData.getInfo(name, filterString));
			}
		} else {
			System.out.println("no picture found.");
		}
		System.out.println(all.size());

		// already got all pictures' exif information.
		// draw exif information to picture.
		Font f = new Font("Consolas", Font.ITALIC, 20);
		DrawText.setFont(f);
		for (int i = 0; i < all.size(); i++) {
			List<String> picInfos = all.get(i);
			String name = pictures.get(i);
			DrawText.drawText(name, name+".jpg", picInfos, "jpg", 5, 20);
			
		}
	}
}
