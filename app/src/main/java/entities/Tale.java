package entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 
 * 
 * 
 * @author Dadvid Horvath
 */

public class Tale {

	private String title;
	private String link;
	private String description;
	private String author;
	private String rating = "";
	private String cathegory = "";
	private String discusionLink;
	private int discusionCount;

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getCathegory() {
		return cathegory;
	}

	public void setCathegory(String cathegory) {
		this.cathegory = cathegory;
	}

	public String getDiscusionLink() {
		return discusionLink;
	}

	public void setDiscusionLink(String discusionLink) {
		this.discusionLink = discusionLink;
	}

	public int getDiscusionCount() {
		return discusionCount;
	}

	public void setDiscusionCount(int discusionCount) {
		this.discusionCount = discusionCount;
	}

	@Override
	public String toString() {
		// return "Tale{" + "title=" + title + ", link=" + link +
		// ", description=" + description + ", imageURL=" + imageURL +
		// ", author=" + author + ", rating=" + rating + ", date=" + date +
		// ", cathegory=" + cathegory + ", discusionLink=" + discusionLink +
		// ", discusionCount=" + discusionCount + '}';
		return title;
	}

	
	public Tale (){}
}