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

public class CDManager extends FileOperationsClass {

	public static final String filename = "CDs.ser";

	private ArrayList<CD> cds = new ArrayList<CD>();
	InputStream file, buffer;
	OutputStream bf, fl;
	ObjectInput input;
	ObjectOutput output;
	File uf = new File(filename);

	public CDManager() {
		readF();
	}

	public ArrayList<CD> getCDs() {
		this.readF();
		return this.cds;
	}

	public void addCD(CD cd) {
		cds.add(cd);
		writeF();
	}

	public void remove(UUID id) {
		boolean rm = false;
		readF();
		for (CD x : cds)
			if (x.getId().equals(id)) {
				rm = true;
				cds.remove(cds.indexOf(x));
				break;
			}
		if (!rm)
			System.out.println("Not Found");
		writeF();
	}

	public void editCD(CD u) {
		readF();
		for (int i = 0; i < cds.size(); i++) {

			if (cds.get(i).getId().equals(u.getId())) {
				cds.set(i, u);
				writeF();
			}

		}

	}

	@SuppressWarnings("unchecked")
	public void readF() {
		try {
			file = new FileInputStream(uf);
			buffer = new BufferedInputStream(file);
			input = new ObjectInputStream(buffer);
			cds = (ArrayList<CD>) input.readObject();
			for (CD CD : cds) {
				System.out.println("Data: " + CD.toString());
			}
		} catch (ClassNotFoundException ex) {
			System.out.println("Not found. Creating new file" + ex.toString());
		} catch (IOException ex) {
			System.out.println("Cannot perform input." + ex.toString());
		}
		closeFile();
	}

	public void writeF() {
		try {
			fl = new FileOutputStream(uf);
			bf = new BufferedOutputStream(fl);
			output = new ObjectOutputStream(bf);
			output.writeObject(cds);
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
