package fr.arbre.dao.csv;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class Csv2Array {
	
	private int max_line;
	private int max_column;
	private int Current_line;
	private String file;

	Csv2Array(String file) {
		this.file = file;
		this.max_line = 0;
		this.max_column = 0;
		GetMaxValues();
	}

	public int getMaxColumn() {
		return this.max_column;
	}

	public void PrintArray(String[][] str) {
		int i, j;
		for (i = 0; i < max_line; i++) {
			for (j = 0; j < str[i].length; j++)
				System.out.println("str[" + i + "][" + j + "] = " + str[i][j]);
			System.out.println("");
			System.out.println("");
		}

	}

	public void GetMaxValues() {
		@SuppressWarnings("unused")
		String[] str;
		String line = "";
		while (!"EOF".equals(line = readLn(this.file, Current_line))) {
			str = Ln2Array(line);
			Current_line += 1;
		}
		this.Current_line = 0;
	}

	public String[][] toArray() {
		String[][] str = new String[this.max_line][this.max_column];
		String line;
		while (!"EOF".equals(line = readLn(this.file, Current_line))) {

			str[Current_line] = Ln2Array(line);
			Current_line += 1;
		}

		return str;
	}

	public String[] Ln2Array(String line) {
		Pattern p = Pattern.compile("[^A-Z0-9/# ]", 2);
		String[] Array = p.split(line);
		if (max_column < Array.length)
			max_column = Array.length;
		return Array;
	}

	public String readLn(String file, int line) {

		String sCurrentLine = null;
		int compteur = 0;
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));

			while ((sCurrentLine = br.readLine()) != null) {
				sCurrentLine = sCurrentLine.replaceAll("^#", "");

				if (line != compteur) {
					compteur++;
					continue;

				}

				else {
					br.close();
					return sCurrentLine;
				}
			}
			br.close();
		} catch (IOException ex) {
			Logger.getLogger(Csv2Array.class.getName()).log(Level.SEVERE, null,
					ex);
		}
		max_line = compteur;
		return "EOF";
	}
}