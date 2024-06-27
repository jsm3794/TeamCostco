package main.java.com.teamcostco.model;

import java.sql.Date;

public class Product {
    private int productId; // 제품 ID
    private String productcode; // 제품일렬번호
    private String productName; // 제품명
    private String mainid; // 대분류
    private String mediumid; // 중분류
    private String smallid; // 소분류

    
    public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getProductcode() {
		return productcode;
	}
	public void setProductcode(String productcode) {
		this.productcode = productcode;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getMainid() {
		return mainid;
	}
	public void setMainid(String mainid) {
		this.mainid = mainid;
	}
	public String getMediumid() {
		return mediumid;
	}
	public void setMediumid(String mediumid) {
		this.mediumid = mediumid;
	}
	public String getSmallid() {
		return smallid;
	}
	public void setSmallid(String smallid) {
		this.smallid = smallid;
	}
    

}
