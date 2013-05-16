package fr.arbre.ihm;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import fr.arbre.dao.csv.CsvPersonDao;
import fr.arbre.dao.csv.GenderException;
import fr.arbre.model.Gender;

@SuppressWarnings("serial")
public class Table_Csv2Array extends JTable {

	public Table_Csv2Array(String filename, Gender gender) {
		CsvPersonDao dao = CsvPersonDao.getInstance();
		if (filename != null) {
			dao.load(filename);
		}
		String[][] tab = null;
		if (gender == null) {
			tab = dao.getTable();
		} else {
			try {
				tab = dao.getTableByGender(gender);
			} catch (GenderException e) {
				e.printStackTrace();
			}
		}
		DefaultTableModel dtm = new DefaultTableModel(tab, dao.getHeader()) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		setModel(dtm);

		setAutoResizeMode(AUTO_RESIZE_ALL_COLUMNS);
		getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		getColumnModel().getColumn(0).setPreferredWidth(30); // id
		getColumnModel().getColumn(1).setPreferredWidth(100); // nom
		getColumnModel().getColumn(2).setPreferredWidth(100); // prenom
		getColumnModel().getColumn(3).setPreferredWidth(50); // genre
		getColumnModel().getColumn(4).setPreferredWidth(50); // id père
		getColumnModel().getColumn(5).setPreferredWidth(50); // id mère
		getColumnModel().getColumn(6).setPreferredWidth(100); // date de naissance
		getColumnModel().getColumn(7).setPreferredWidth(120); // nom image
		getColumnModel().getColumn(8).setPreferredWidth(100); // id des enfants

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
		getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
		getColumnModel().getColumn(5).setCellRenderer(centerRenderer);

		// la première est selectionné par défaut
		setRowSelectionInterval(0, 0);
	}

	public int getSelectedId() {
		return Integer.parseInt((String) getValueAt(getSelectedRow(), 0));
	}

}
