package Library.ui;

public class Book {
	private String title;
	private Double ID;
	private String author;
	private String availability;
	
	
	public Book(String Title, Double ID, String Author, String availability){
		this.title = Title;
		this.ID = ID;
		this.author = Author;
		this.availability=availability;
	}
	
	//TODO: refactor this
	public String getValue(String propertyName) {
		switch (propertyName) {
			case "Title":
				return this.title;
			case "ID":
				return this.ID.toString();
			case "Author":
				return this.author;
			case "availability":
				return this.availability;
		}
		return null;
	}
	
	
	public String getTitle() {
		return title;
	}
	public double getID() {
		return this.ID;
	}
	public String getAuthor() {
		return this.author;
	}
			
	public String getavailability() {
		return this.availability;
	}
}