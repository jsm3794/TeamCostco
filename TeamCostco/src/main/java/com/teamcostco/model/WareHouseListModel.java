package main.java.com.teamcostco.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WareHouseListModel {
    private String product_name;
    private String disposal_id;
    private Integer defect_amount;

    public WareHouseListModel(ResultSet rs) throws SQLException {
        product_name = rs.getString("product_name");
        disposal_id = rs.getString("disposal_id");
        defect_amount = rs.getInt("defect_amount");
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getDisposal_id() {
        return disposal_id;
    }

    public void setDisposal_id(String disposal_id) {
        this.disposal_id = disposal_id;
    }

    public Integer getDefect_amount() {
        return defect_amount;
    }

    public void setDefect_amount(Integer defect_amount) {
        this.defect_amount = defect_amount;
    }

    @Override
    public String toString() {
        return "WareHouseListModel [product_name=" + product_name + ", disposal_id=" + disposal_id + ", defect_amount=" + defect_amount + "]";
    }
}