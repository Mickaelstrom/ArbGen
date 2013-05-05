package fr.arbre.dao.csv;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

import fr.arbre.dao.csv.Csv2Array;

public class Csv2ArrayTest {

	public static void main(String[] args) {

		JFrame frame = new JFrame();
		frame.setTitle("Csv2Array Test");
		frame.setSize(1000, 600);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		// Extraction des données
		Csv2Array data = new Csv2Array("resources/CSV/gen-01.csv");
		String[][] tab = data.toArray();
		data.PrintArray(tab);

		// JTABLE
		JTable table = new JTable(tab, tab[0]);
		columnWidth(table, 150, data.getMaxColumn());

		// PANEL
		JPanel pan = new JPanel();
		pan.setBackground(Color.YELLOW);
		pan.add(table);
		frame.setContentPane(pan);
		frame.setVisible(true);
		
		CsvPersonDao dao = CsvPersonDao.getInstance();
		dao.load("resources/CSV/gen-04.csv");
		dao.print();
	}

	public static void columnWidth(JTable table, int width, int maxcolumn) {
		TableColumn column = null;
		for (int i = 0; i < maxcolumn; i++) {
			column = table.getColumnModel().getColumn(i);
			column.setPreferredWidth(width);
		}

	}
}
