package fr.arbre.ihm;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

import fr.arbre.dao.csv.Csv2Array;

@SuppressWarnings("serial")
public class Panel_Csv2Array extends JPanel {

	public Panel_Csv2Array() {

		Csv2Array data = new Csv2Array("resources/CSV/gen-01.csv");
		//JPanel panIcon = new JPanel();
		String[][] tab = data.toArray();
		data.PrintArray(tab);

		// JTABLE
		JTable table = new JTable(tab, tab[0]);
		columnWidth(table, 150, data.getMaxColumn());

		// PANEL
		JPanel pan = new JPanel();

		this.add(table);
		this.add(pan);

	}

	public static void columnWidth(JTable table, int width, int maxcolumn) {
		TableColumn column = null;
		for (int i = 0; i < maxcolumn; i++) {
			column = table.getColumnModel().getColumn(i);
			column.setPreferredWidth(width);
		}

	}

}
