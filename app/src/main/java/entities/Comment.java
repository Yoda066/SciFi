package entities;

/**

 *

 * @author David Horvath

 */

public class Comment {



    private String author;

    private String text;

    private String date;

    private String imagePath;
    
    


    public String getImagePath() {
		return imagePath;
	}



	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}



	public String getAuthor() {

        return author;

    }



    public void setAuthor(String author) {

        this.author = author;

    }



    public String getText() {

        return text;

    }



    public void setText(String text) {

        this.text = text;

    }



    public String getDate() {

        return date;

    }



    public void setDate(String date) {

        this.date = date;

    }



    @Override

    public String toString() {

        return author + " " + date + '\n' + text;

    }



	public Comment(String author, String text, String imagePath) {
		super();
		this.author = author;
		this.text = text;
		this.imagePath = imagePath;
	}
    
}