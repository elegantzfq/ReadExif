package org.zfq.readexif;

import java.util.List;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

public class GetMetaData {
	
	private GetMetaData(){}

	public static List<String> getInfo(String filename,String filterString) {
		File file = new File(filename);
		try {
			Metadata metadata = ImageMetadataReader.readMetadata(file);
			return printImageTags(1, metadata,filterString);
		} catch (ImageProcessingException e) {
			System.err.println("error 1a: " + e);
			return null;
		} catch (IOException e) {
			System.err.println("error 1b: " + e);
			return null;
		}
	}
	
	public static String mergeInfos(List<String> infos){
		StringBuilder sb = new StringBuilder();
		if(infos != null && infos.size() > 0){
			for(String info:infos){
				sb.append(info+"\n");
			}
			return sb.toString();
		}else {
			return "";
		}
	}

	private static boolean info_filter(String regex,String src){
		Pattern pattern = Pattern.compile(regex);
		Matcher math = pattern.matcher(src);
		if(math.matches()){
			return true;
		}else{
			return false;
		}
	}
	
	private static List<String> printImageTags(int approachCount, Metadata metadata,String filterString) {
		String regex = filterString;
		ArrayList<String> metadatas = new ArrayList<String>();
		for (Directory directory : metadata.getDirectories()) {
			for (Tag tag : directory.getTags()){
				boolean ifDisplay = info_filter(regex,tag.getTagName());
				if(ifDisplay){
					metadatas.add(tag.getTagName()+":"+tag.getDescription());
					//System.out.println(tag.getTagName()+":"+tag.getDescription());
				}
			}
//			for (String error : directory.getErrors())
//				System.err.println("ERROR: " + error);
		}
		if(metadatas.size() > 0){
			return metadatas;
		}else{
			return null;
		}
	}

	public static void main(String[] args) {
		List<String> l = GetMetaData.getInfo("C:\\Work\\Download\\sample.jpg", "(^Exposure Time)|(^F-Number)|(^Exposure Program)|(^ISO Speed Ratings)|(^Exposure Bias Value)|(^Max Aperture Value)|(^Metering Mode)|(^White Balance)|(^Flash)|(^Focal Length)|(^Color Space)|(^Exif Image Width)|(^Exif Image Height)|(^Sensing Method)|(^File Source)|(^Scene Type)|(^Make)|(^Model)|(^Orientation)");
		System.out.println(l.size());
		for(String s:l){
			System.out.println(s);
		}
	}
	
}
