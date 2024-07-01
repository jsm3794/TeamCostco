package main.java.com.teamcostco.model;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderDetailModel {

    private int orderRequestId;
    private String productCode;
    private String productName;
    private String main_name;
    private int orderEmployeeId;
    private int orderQuantity;
    private Date requestDate;
    private String requestStatus;
    private String clientName;
    private int quantityOfWh;

    public OrderDetailModel(ResultSet rs) throws SQLException {
        this.orderRequestId = rs.getInt("ORDER_REQUEST_ID");
        this.productCode = rs.getString("PRODUCT_CODE");
        this.productName = rs.getString("PRODUCT_NAME");
        this.main_name = rs.getString("main_name");
        this.orderEmployeeId = rs.getInt("ORDEREMPLOYEE_ID");
        this.orderQuantity = rs.getInt("ORDER_QUANTITY");
        this.requestDate = rs.getDate("REQUEST_DATE");
        this.requestStatus = rs.getString("REQUEST_STATUS");
        this.clientName = rs.getString("CLIENT_NAME");
        this.quantityOfWh = rs.getInt("QUANTITY_OF_WH");
    }


    public String getMain_name() {
		return main_name;
	}


	public void setMain_name(String main_name) {
		this.main_name = main_name;
	}


	public String getProductName() {
		return productName;
	}


	public void setProductName(String productName) {
		this.productName = productName;
	}


	public int getOrderRequestId() {
        return orderRequestId;
    }

    public void setOrderRequestId(int orderRequestId) {
        this.orderRequestId = orderRequestId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public int getOrderEmployeeId() {
        return orderEmployeeId;
    }

    public void setOrderEmployeeId(int orderEmployeeId) {
        this.orderEmployeeId = orderEmployeeId;
    }

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(int orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public int getQuantityOfWh() {
        return quantityOfWh;
    }

    public void setQuantityOfWh(int quantityOfWh) {
        this.quantityOfWh = quantityOfWh;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String lineSeparator = "\n";
        sb.append("발주요청ID=").append(orderRequestId).append(lineSeparator);
        sb.append("상품코드=").append(productCode).append(lineSeparator);
        sb.append("상품명=").append(productName).append(lineSeparator);
        sb.append("대분류=").append(main_name).append(lineSeparator);
        sb.append("발주직원ID=").append(orderEmployeeId).append(lineSeparator);
        sb.append("발주수량=").append(orderQuantity).append(lineSeparator);
        sb.append("요청일자=").append(requestDate).append(lineSeparator);
        sb.append("요청상태=").append(requestStatus).append(lineSeparator);
        sb.append("거래처이름=").append(clientName).append(lineSeparator);
        sb.append("입고수량=").append(quantityOfWh);
        return sb.toString();
    }
}