package main.java.com.teamcostco.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WareHouseListModel {
    private String product_name;
    private String disposal_method;
    private Integer defect_amount;

    public WareHouseListModel(ResultSet rs) throws SQLException {
        product_name = rs.getString("product_name");
        disposal_method = rs.getString("disposal_method");
        defect_amount = rs.getInt("defect_amount");
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getDisposal_method() {
        return disposal_method;
    }

    public void setDisposal_method(String disposal_method) {
        this.disposal_method = disposal_method;
    }

    public Integer getDefect_amount() {
        return defect_amount;
    }

    public void setDefect_amount(Integer defect_amount) {
        this.defect_amount = defect_amount;
    }

    @Override
    public String toString() {
        return "WareHouseListModel [product_name=" + product_name + ", disposal_method=" + disposal_method + ", defect_amount=" + defect_amount + "]";
    }
}