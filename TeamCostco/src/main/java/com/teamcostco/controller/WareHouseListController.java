package main.java.com.teamcostco.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import main.java.com.teamcostco.model.AllCategoryJoin;
import main.java.com.teamcostco.model.CategoryData;
import main.java.com.teamcostco.model.WareHouseListModel;
import main.java.com.teamcostco.model.database.DatabaseUtil;
import main.java.com.teamcostco.view.panels.WareHouseListPanel;

// 할일
// ()은 보류
// 1. 콤보박스에 불량사유 추가 --> 생각해보니 굳이 db연결할 필요가 없네용 패널에서 ㄱㄱ
// 2. 불량사유와 상품명으로 검색되게
// (불량재고처분)
// (각 불량별 횟수)

public class WareHouseListController extends PanelController<WareHouseListPanel> {
	private DatabaseUtil connector;

	public WareHouseListController() {
		this.connector = new DatabaseUtil();
		initialize();
	}

	private void initialize() {

	}
	


	@Override
	public String toString() {
		return "불량목록";
	}
}
