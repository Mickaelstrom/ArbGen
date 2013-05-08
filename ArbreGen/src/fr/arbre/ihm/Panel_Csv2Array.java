package fr.arbre.ihm;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import fr.arbre.dao.csv.CsvPersonDao;

@SuppressWarnings("serial")
public class Panel_Csv2Array extends JPanel {

	public Panel_Csv2Array() {

		CsvPersonDao dao = CsvPersonDao.getInstance();
		dao.load("resources/CSV/gen-dbz.csv");
		String[][] tab = dao.getTable();
		DefaultTableModel dtm = new DefaultTableModel(tab, dao.getHeader()) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		// JTABLE
		JTable table = new JTable(dtm);
		table.doLayout();
		columnWidth(table, 150, dao.getHeader().length);

		// PANEL
		JScrollPane scrollPane = new JScrollPane(table);
		add(scrollPane);
	}

	public static void columnWidth(JTable table, int width, int maxcolumn) {
		TableColumn column = null;
		for (int i = 0; i < maxcolumn; i++) {
			column = table.getColumnModel().getColumn(i);
			column.setPreferredWidth(width);
		}

	}

}
