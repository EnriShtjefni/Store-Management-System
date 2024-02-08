package model;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.UUID;

public class AccessUsers {

	public static final String filename = "Users.ser";

	private ArrayList<User> users = new ArrayList<User>();
	InputStream file, buffer;
	OutputStream bf, fl;
	ObjectInput input;
	ObjectOutput output;
	File uf = new File(filename);

	public AccessUsers() {
		readF();
	}

	public User checkUser(String user, String pass) {
		for (User x : users)
			if (x.getUser().equals(user) && x.getPass().equals(pass)) {
				return x;
			}
		return null;
	}

	public void addUser(User user) {
		users.add(user);
		writeF();
	}

	public String readS() {
		readF();
		String read = "";
		for (User x : users)
			read += "\n-------------User " + x.getid() + "--------------------\n" + x.toString()
					+ "\n>---------------------------<\n";
		return read;
	}

	public ArrayList<User> getUsers() {
		this.readF();
		return this.users;
	}

	public void remove(UUID id) {
		boolean rm = false;
		readF();
		for (User x : users)
			if (x.getid().equals(id)) {
				rm = true;
				users.remove(users.indexOf(x));
				break;
			}
		if (!rm)
			System.out.println("Not Found");
		writeF();
	}

	public int getPosition(User u) {
		System.out.println("--------------");
		System.out.println(u);
		System.out.println("--------------");
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).toString().equals(u.toString()))
				return i;
		}

		return -1;
	}

	public void editUser(int pos, User u) {
		System.out.println(">>>>" + pos);
		if (pos < 0 || pos >= users.size()) {
			System.out.println("Cannot find User");
			return;
		} else {
			users.set(pos, u);
			this.writeF();
		}
	}

	@SuppressWarnings("unchecked")
	private void readF() {
		try {
			file = new FileInputStream(uf);
			buffer = new BufferedInputStream(file);
			input = new ObjectInputStream(buffer);
			users = (ArrayList<User>) input.readObject();
			for (User user : users) {
				System.out.println("Data: " + user.toString());
			}
		} catch (ClassNotFoundException ex) {
			System.out.println("Not found. Creating new file" + ex.toString());
		} catch (IOException ex) {
			System.out.println("Cannot perform input." + ex.toString());
		}
		closeFile();
	}

	private void writeF() {
		try {
			fl = new FileOutputStream(uf);
			bf = new BufferedOutputStream(fl);
			output = new ObjectOutputStream(bf);
			output.writeObject(users);
		} catch (IOException ex) {
			System.out.println("Cannot perform output." + ex.toString());
		}
		closeFile();
	}

	public void closeFile() {
		try {
			if (input != null) {
				input.close();
				buffer.close();
				file.close();
			}
			if (output != null) {
				output.close();
				bf.close();
				fl.close();
			}
		} catch (IOException e) {
			System.out.println(e.toString());
		}
	}
}