package ElizaProject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JFileChooser;

public class fileHandler implements TextFileIO {

	@Override
	public void createFile(String fileName) {
		PrintWriter outStream = null;
		try {
			outStream = new PrintWriter(fileName);

		} catch (FileNotFoundException e) {
			System.err.println("Could not create file: " + fileName);
			e.printStackTrace();

		} finally {
			if (outStream != null) {
				outStream.close();
			}
		}
	}

	@Override
	public void createFileWithContent(String fileName, String text) {
		PrintWriter outStream = null;
		try {
			outStream = new PrintWriter(fileName);
			outStream.println(text); // write text to file and append \n

		} catch (FileNotFoundException e) {
			System.err.println("Could not create/write file: " + fileName);
			e.printStackTrace();

		} finally {
			if (outStream != null) {
				outStream.close();
			}
		}

	}

	@Override
	public void appendToFile(String fileName, String text) {
		PrintWriter outStream = null;
		try {
			// outStream = new PrintWriter(fileName);
			outStream = new PrintWriter(new FileOutputStream(fileName, true)); // true: write to end of file || append to file
			outStream.println(text); // write text to file and append new line "\n"
			outStream.flush();
		} catch (FileNotFoundException e) {
			System.err.println("Could not create/write file: " + fileName);
			e.printStackTrace();

		} finally {
			if (outStream != null) {
				outStream.close();
			}
		}
	}

	@Override
	public String readFile(String fileName) {
		String fileContent = "";
		Scanner inStream = null;
		try {

			File file = new File(fileName);
			inStream = new Scanner(file);
			while (inStream.hasNextLine()) {
				fileContent += inStream.nextLine() + "\n";
			}

		} catch (FileNotFoundException e) {
			System.err.println("Could not find file.");
			e.printStackTrace();

		} finally {
			if (inStream != null) {
				inStream.close();
			}
		}
		return fileContent;
	}

	// Choose file with FileChooser and read its content
	public String ReadFileFromFileChooser() {
		JFileChooser fc = new JFileChooser(); // new dialog object to choose a file
		int yesNo = fc.showDialog(null, null); // show user dialog to pick thru dirs and fils
		if (yesNo == JFileChooser.APPROVE_OPTION) { // user selected a file
			File f = fc.getSelectedFile();
			if (f.isFile()) { // user's selection is a file
				return readFile(f.getAbsolutePath()); // return the contents of the file
			}
		}

		return "";
	}
	
}
