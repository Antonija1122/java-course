package hr.fer.zemris.java.rest;

import hr.fer.zemris.java.images.ImageDB;
import hr.fer.zemris.java.images.ImageInfo;

import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * 
 * The class uses the org.json library to work with JSON format.
 * 
 * @author antonija
 */
@Path("/tags")
public class TagsJason {

	/**
	 * This is string representation of path to this app
	 */
	public static String path;

	/**
	 * This method gets all tags from ImageInfo database and sends back the list of
	 * all tags
	 * 
	 * @return status ok with all tags from database
	 */
	@GET
	@Produces("application/json")
	public Response getQuotesList() {
		List<String> tags = ImageDB.getTags();

		JSONObject result = new JSONObject();
		result.put("tags", tags);

		return Response.status(Status.OK).entity(result.toString()).build();
	}

	/**
	 * This method gets all images tagged with input tagName and sends back all the
	 * names of those images.
	 * 
	 * @param tagName input tag
	 * @return status ok with names of tagged images
	 */
	@Path("/images/{tagName}")
	@GET
	@Produces("application/json")
	public Response getQuote(@PathParam("tagName") String tagName) {
		List<ImageInfo> images = ImageDB.getTagedImages(tagName);
		List<String> names = images.stream().map(l -> l.getName().trim()).collect(Collectors.toList());

		JSONArray namesArray = new JSONArray();
		for (String n : names) {
			namesArray.put(n);
		}
		JSONObject result = new JSONObject();
		result.put("names", namesArray);

		return Response.status(Status.OK).entity(result.toString()).build();
	}

	/**
	 * This method sends description and tags list of input image name and also
	 * returns previous and next image name from the current tag
	 * 
	 * @param name input name of image
	 * @param tag  current tag in the app
	 * @return status ok with description and tag list of input image (given name)
	 *         and name of previous and next image from the input tag
	 */
	@Path("info/{id}/{currentTag}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getQuote2(@PathParam("id") String name, @PathParam("currentTag") String tag) {
		List<ImageInfo> images = ImageDB.getTagedImages(tag);
		String previous = null;
		String next = null;
		String desc = null;
		String[] tags = null;
		for (int i = 0; i < images.size(); i++) {
			if (images.get(i).getName().equals(name)) {
				desc = images.get(i).getDescription();
				tags = images.get(i).getTags();
				if (images.size() == 1) {
					previous = images.get(0).getName();
					next = images.get(0).getName();
				} else if (i == 0) {
					previous = images.get(images.size() - 1).getName();
					next = images.get(i + 1).getName();
				} else if (i == images.size() - 1) {
					previous = images.get(i - 1).getName();
					next = images.get(0).getName();
				} else {
					previous = images.get(i - 1).getName();
					next = images.get(i + 1).getName();
				}
			}
		}

		JSONObject result = new JSONObject();

		result.put("desc", desc);
		result.put("prev", previous);
		result.put("next", next);
		result.put("tags", tags);
		return Response.status(Status.OK).entity(result.toString()).build();
	}

}
