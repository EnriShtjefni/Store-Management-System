package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.UUID;

public class User implements Serializable {

	private static final long serialVersionUID = -5083759422249745403L;
	private String user;
	private String pass;
	private final UUID id;
	private int level;

	public User(String user, String pass, int level) {
		super();
		this.id = UUID.randomUUID();
		this.user = user;
		this.pass = pass;
		this.level = level;
	}

	public UUID getid() {
		return id;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String toString() {
		return "Id: " + this.getid() + "\nUser: " + this.getUser() + "\nPass: " + this.getPass() + "\nLevel: "
				+ this.getLevel();
	}

	private void readObject(ObjectInputStream aInputStream) throws ClassNotFoundException, IOException {
		aInputStream.defaultReadObject();
	}

	private void writeObject(ObjectOutputStream aOutputStream) throws IOException {
		aOutputStream.defaultWriteObject();
	}
}
