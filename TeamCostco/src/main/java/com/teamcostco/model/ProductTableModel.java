package main.java.com.teamcostco.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;
/*
	-- 상품코드, 상품이름, 불량사유, 불량개수
SELECT product_code, product_name, disposal_method, defect_amount
FROM defectproduct
INNER JOIN product USING (product_code);

-- 불량개수 세기
SELECT disposal_method, COUNT(*) AS "각 불량별 횟수" FROM defectproduct GROUP BY disposal_method;

 */
public class ProductTableModel extends AbstractTableModel {
    private List<WareHouseListModel> data;
    private String[] columnNames = {"상품코드", "상품이름", "불량사유", "불량개수"};

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
                return row.getProduct_code();
            case 1:
                return row.getProduct_name();
            case 2:
                return row.getDisposal_method();
            case 3:
                return row.getDefect_amount();
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