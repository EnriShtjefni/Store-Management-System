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

public class BillManager {

	public static final String filename = "Bills.ser";

	private ArrayList<Bill> bls = new ArrayList<Bill>();
	InputStream file, buffer;
	OutputStream bf, fl;
	ObjectInput input;
	ObjectOutput output;
	File uf = new File(filename);

	public BillManager() {
		readF();
	}

	public void addBill(Bill Bill) {
		bls.add(Bill);
		writeF();
	}

	public String readS() {
		readF();
		String read = "";
		for (Bill x : bls)
			read += "\n-------------Bill " + x.getId() + "--------------------\n" + x.toString()
					+ "\n>---------------------------<\n";
		return read;
	}

	public ArrayList<Bill> getbls() {
		this.readF();
		return this.bls;
	}

	public void remove(UUID id) {
		boolean rm = false;
		readF();
		for (Bill x : bls)
			if (x.getId().equals(id)) {
				rm = true;
				bls.remove(bls.indexOf(x));
			}
		if (!rm)
			System.out.println("Not Found");
		writeF();
	}

	public int getPosition(Bill u) {
		System.out.println("--------------");
		System.out.println(u);
		System.out.println("--------------");
		for (int i = 0; i < bls.size(); i++) {
			if (bls.get(i).toString().equals(u.toString()))
				return i;
		}

		return -1;
	}

	@SuppressWarnings("unchecked")
	private void readF() {
		try {
			file = new FileInputStream(uf);
			buffer = new BufferedInputStream(file);
			input = new ObjectInputStream(buffer);
			bls = (ArrayList<Bill>) input.readObject();
			for (Bill Bill : bls) {
				System.out.println("Data: " + Bill.toString());
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
			output.writeObject(bls);
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
