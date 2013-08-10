package fiu.edu.server;

public class Book {

	String bookName;
	String authors;
	String ISBN;
	int edition;
	Float price;
	int quantities;
	
	
	public Book(String bookName, String authors, String ISBN, 
						int edition, Float price, int quantities) {
		this.bookName = bookName;
		this.authors = authors;
		this.ISBN = ISBN;
		this.edition = edition;
		this.price = price;
		this.quantities = quantities;
	}
	
	
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public String getAuthors() {
		return authors;
	}
	public void setAuthors(String authors) {
		this.authors = authors;
	}
	public String getISBN() {
		return ISBN;
	}
	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}
	public int getEdition() {
		return edition;
	}
	public void setEdition(int edition) {
		this.edition = edition;
	}


	public Float getPrice() {
		return price;
	}


	public void setPrice(Float price) {
		this.price = price;
	}


	public int getQuantities() {
		return quantities;
	}


	public void setQuantities(int quantities) {
		this.quantities = quantities;
	}
	
}
