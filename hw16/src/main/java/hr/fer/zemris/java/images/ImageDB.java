package hr.fer.zemris.java.images;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.rest.TagsJason;

/**
 * This class represents database of images for this gallery app. Private
 * variable images is a List of ImageInfo objects which contain information
 * about one image from this database.
 * 
 * @author antonija
 *
 */
public class ImageDB {
	/**
	 * private list of image info
	 */
	private static final List<ImageInfo> images = createImagInfos();

	/**
	 * This method reads data from file opisnik.txt and creates list of ImageInfo
	 * object based on the information in the file. First row contains name of the
	 * picture, second row is description of the picture and the third row contains
	 * tags for that picture. 
	 * 
	 * @return List of ImageInfo for this database
	 */
	private static List<ImageInfo> createImagInfos() {
		List<ImageInfo> images = new ArrayList<ImageInfo>();
		String fileName = TagsJason.path + "//WEB-INF/opisnik.txt";
		Charset charset = Charset.forName("UTF-8");
		List<String> result = null;
		try {
			result = Files.readAllLines(Paths.get(fileName), charset);
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImageInfo image = null;
		for (int i = 0; i < result.size(); i++) {
			if (i % 3 == 0) {
				image = new ImageInfo();
				image.setName(result.get(i));
			} else if (i % 3 == 1) {
				image.setDescription(result.get(i));
			} else {
				image.setTags(getTagsfromLine(result.get(i)));
				images.add(image);
			}
		}
		return images;
	}

	/**
	 * This method separates tags from one string line 
	 * @param string input string line with tags
	 * @return String[] tags
	 */
	private static String[] getTagsfromLine(String string) {
		String[] tags = string.split(",");
		for (int i = 0; i < tags.length; i++) {
			tags[i] = tags[i].trim();
		}
		return tags;
	}

	/**
	 * This method is a getter method for this database list of ImageInfos
	 * @return images
	 */
	public static List<ImageInfo> getImages() {
		return images;
	}

	/**
	 * This method returns List of images that contain tag equal to input tag
	 * @param inputTag input tag
	 * @return List<ImageInfo> of tagged images
	 */
	public static List<ImageInfo> getTagedImages(String inputTag) {
		List<ImageInfo> images = new ArrayList<ImageInfo>();
		for (ImageInfo info : ImageDB.images) {
			for (String tag : info.getTags()) {
				if (tag.equals(inputTag)) {
					images.add(info);
				}
			}
		}
		return images;
	}

	/**
	 * This method returns list of all tags from images in this database. 
	 * @return list of tags
	 */
	public static List<String> getTags() {
		List<String> tags = new ArrayList<String>();
		for (ImageInfo info : images) {
			for (String tag : info.getTags()) {
				if (!tags.contains(tag)) {
					tags.add(tag);
				}
			}
		}
		return tags;
	}

}
