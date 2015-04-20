package org.zfq.readexif;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetPictures {

	File root = null;
	String pictureTypes = null;
	ArrayList<String> files = null;

	public File getRoot() {
		return root;
	}

	public String getPictureTypes() {
		return pictureTypes;
	}

	public void setPictureTypes(String pictureTypes) {
		this.pictureTypes = pictureTypes;
	}

	public void setRoot(File root) {
		this.root = root;
	}

	public GetPictures(String path, String types) {
		root = new File(path);
		pictureTypes = types;
		files = new ArrayList<String>();
	}

	private void interate(File file) {
		if (file.canRead()) {
			if (file.isFile()) {
				if (fileTypeFilter(pictureTypes, file.getName())) {
					// System.out.println(file.getAbsolutePath());
					files.add(file.getAbsolutePath());
				}
			} else if (file.isDirectory()) {
				for (File f : file.listFiles()) {
					interate(f);
				}
			}
		}
	}

	/**
	 * *filter file types by its name
	 * 
	 * @param typesString
	 *            types separated by a comma
	 * @param filename
	 *            match source string
	 * @return true if matches, otherwise false
	 */
	private boolean fileTypeFilter(String typesString, String filename) {
		String[] types = typesString.split(",");
		String exp = "";
		for (String s : types) {
			s = "(?:\\." + s + "$)|";
			exp += s;
		}
		exp = exp.substring(0, exp.length() - 1);
		// System.out.println(exp);
		Pattern pattern = Pattern.compile(exp);
		// System.out.println(filename);
		Matcher m = pattern.matcher(filename);
		if (m.find()) {
			return true;
		} else {
			return false;
		}
	}

	public List<String> getPictureNames() {
		interate(root);
		if (files.size() == 0) {
			files = null;
		}
		return files;
	}

}
