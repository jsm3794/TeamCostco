
package main.java.com.teamcostco.model;

import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ProductTableModel extends AbstractTableModel {
    private List<WareHouseListModel> data;
    private String[] columnNames = {"상품명", "불량사유", "불량개수", "날짜"};

    public ProductTableModel(List<WareHouseListModel> data) {
        this.data = data;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        WareHouseListModel row = data.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return row.getProduct_name();
            case 1:
                return row.getDisposal_id();
            case 2:
                return row.getDefect_amount();
            case 3 :
            	return row.getCreated_At();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    public void setData(List<WareHouseListModel> data) {
        this.data = data;
        fireTableDataChanged();
    }
}