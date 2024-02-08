package model;

import java.io.Serializable;
import java.util.UUID;

public class CD implements Serializable {

	private static final long serialVersionUID = 1332650979436225061L;
	private String title;
	private int price;
	private final UUID id;
	private String singer;
	private String genre;
	private int quantity;

	public CD(int price, String title, String singer, String genre, int quantity) {
		super();

		this.id = UUID.randomUUID();
		this.price = price;
		this.title = title;
		this.singer = singer;
		this.genre = genre;
		this.quantity = quantity;
	}

	public UUID getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getSinger() {
		return singer;
	}

	public void setSinger(String singer) {
		this.singer = singer;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "ID: " + id + "\n title: " + title + "\n genre:" + genre + "\n singer: " + singer + "\n price: " + price
				+ "\n quantity: " + quantity;
	}

}
