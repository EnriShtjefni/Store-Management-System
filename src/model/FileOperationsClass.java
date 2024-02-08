package model;

public abstract class FileOperationsClass implements FileOperationsInterface {

	public abstract void readF();

	public abstract void writeF();

	public abstract void closeFile();

	public void classInfomation() {
		System.out.println("This class handles all the general functions ");
	}
}
