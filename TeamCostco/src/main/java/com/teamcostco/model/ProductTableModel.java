package main.java.com.teamcostco.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class ProductTableModel extends AbstractTableModel {
    private List<WareHouseListModel> data;
    private String[] columnNames = {"창고번호", "대분류", "중분류", "소분류", "제품명", "창고수량"};

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
                return row.getProduct_id();
            case 1:
                return row.getMain_name();
            case 2:
                return row.getMedium_name();
            case 3:
                return row.getSmall_name();
            case 4:
                return row.getProduct_name();
            case 5:
                return row.getCurrent_inventory();
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