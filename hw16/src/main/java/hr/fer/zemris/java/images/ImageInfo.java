package hr.fer.zemris.java.images;

/**
 * This class represents information about one image from ImagDB database.
 * Instance of this class contains name of the image, its description and list
 * of tags for that image.
 *  
 * @author antonija
 *
 */
public class ImageInfo {

	/**
	 * name of the image 
	 */
	private String name;
	/**
	 * description of the image 
	 */
	private String description;
	/**
	 * tags of the image 
	 */
	private String[] tags;

	/**
	 * Default constructor for this image 
	 */
	public ImageInfo() {

	}

	/**
	 * Getter method for name 
	 * @return name 
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter method for name 
	 * @param name input name 
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter method for description 
	 * @return description 
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Setter method for description 
	 * @param description input description 
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Getter method for tags 
	 * @return tags 
	 */
	public String[] getTags() {
		return tags;
	}

	/**
	 * Setter method for tags 
	 * @param tags input tags 
	 */
	public void setTags(String[] tags) {
		this.tags = tags;
	}

}
