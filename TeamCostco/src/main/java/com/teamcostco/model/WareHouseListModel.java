package main.java.com.teamcostco.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

public class WareHouseListModel {
    private String product_name;
    private String disposal_id;
    private Integer defect_amount;
    private Timestamp created_At;

    public WareHouseListModel(ResultSet rs) throws SQLException {
        product_name = rs.getString("product_name");
        disposal_id = rs.getString("disposal_id");
        defect_amount = rs.getInt("defect_amount");
        created_At = rs.getTimestamp("created_At");
    }

	public String getProduct_name() {
		return product_name;
	}

	public String getDisposal_id() {
		return disposal_id;
	}

	public Integer getDefect_amount() {
		return defect_amount;
	}

	public Timestamp getCreated_At() {
		return created_At;
	}

	@Override
	public String toString() {
		return "WareHouseListModel [product_name=" + product_name + ", disposal_id=" + disposal_id
				+ ", defect_amount=" + defect_amount + ", created_At=" + created_At + "]";
	}

    
}