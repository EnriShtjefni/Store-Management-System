package model;

import java.io.Serializable;
import java.util.UUID;

public class Bill implements Serializable {

	private static final long serialVersionUID = -8475286211410295528L;
	private final UUID id;
	private String title;
	private int quantity;
	private int cashierId;

	public Bill(String title, int quantity, int cashierId) {
		super();
		this.id = UUID.randomUUID();
		this.title = title;
		this.quantity = quantity;
		this.cashierId = cashierId;
	}

	public UUID getId() {
		return id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getCashierId() {
		return cashierId;
	}

	public void setCashierId(int cashierId) {
		this.cashierId = cashierId;
	}

	@Override
	public String toString() {
		return "Bill id: " + id + "\n title: " + title + "\n quantity:" + quantity + "\n cashierId:" + cashierId;
	}

}
