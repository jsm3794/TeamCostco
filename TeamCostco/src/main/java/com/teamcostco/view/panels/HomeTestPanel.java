package main.java.com.teamcostco.view.panels;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class HomeTestPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	public JLabel timeLabel;
	
	private JButton InventorySeachBtn; // 재고조회
	private JButton MaterialDispatchBtn; // 재고이동
	private JButton ProductInspectionBtn; // 제품검수
	private JButton OrderListBtn; // 발주내역
	private JButton WareHouseListBtn; // 창고목록
	private JButton InventorymodificationBtn; // 재고수정
	private JButton StockAccumulationBtn; // 재고적치
	private JButton ReceivingProcessBtn; // 입고처리

	/**
	 * Create the panel.
	 */
	public HomeTestPanel() {
		setSize(new Dimension(480, 640));
		setLayout(null);
		
		JLabel titleLabel = new JLabel("Team Costco");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setBounds(12, 10, 456, 78);
		titleLabel.setFont(new Font("굴림", Font.BOLD, 24));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(titleLabel);
		
		InventorySeachBtn = new JButton("재고조회");
		InventorySeachBtn.setBackground(new Color(0, 122, 255));
		InventorySeachBtn.setForeground(new Color(255, 255, 255));
		InventorySeachBtn.setBounds(60, 200, 150, 60);
		add(InventorySeachBtn);
		
		MaterialDispatchBtn = new JButton("재고이동");
		MaterialDispatchBtn.setBackground(new Color(0, 122, 255));
		MaterialDispatchBtn.setForeground(new Color(255, 255, 255));
		MaterialDispatchBtn.setBounds(60, 310, 150, 60);
		add(MaterialDispatchBtn);
		
		ProductInspectionBtn = new JButton("제품검수");
		ProductInspectionBtn.setBackground(new Color(0, 122, 255));
		ProductInspectionBtn.setForeground(new Color(255, 255, 255));
		ProductInspectionBtn.setBounds(60, 420, 150, 60);
		add(ProductInspectionBtn);
		
		OrderListBtn = new JButton("발주내역");
		OrderListBtn.setBackground(new Color(0, 122, 255));
		OrderListBtn.setForeground(new Color(255, 255, 255));
		OrderListBtn.setBounds(60, 530, 150, 60);
		add(OrderListBtn);
		
		WareHouseListBtn = new JButton("창고목록");
		WareHouseListBtn.setBackground(new Color(0, 122, 255));
		WareHouseListBtn.setForeground(new Color(255, 255, 255));
		WareHouseListBtn.setBounds(270, 200, 150, 60);
		add(WareHouseListBtn);
		
		InventorymodificationBtn = new JButton("재고수정");
		InventorymodificationBtn.setBackground(new Color(0, 122, 255));
		InventorymodificationBtn.setForeground(new Color(255, 255, 255));
		InventorymodificationBtn.setBounds(270, 310, 150, 60);
		add(InventorymodificationBtn);
		
		StockAccumulationBtn = new JButton("재고적치");
		StockAccumulationBtn.setBackground(new Color(0, 122, 255));
		StockAccumulationBtn.setForeground(new Color(255, 255, 255));
		StockAccumulationBtn.setBounds(270, 420, 150, 60);
		add(StockAccumulationBtn);
		
		ReceivingProcessBtn = new JButton("입고처리");
		ReceivingProcessBtn.setBackground(new Color(0, 122, 255));
		ReceivingProcessBtn.setForeground(new Color(255, 255, 255));
		ReceivingProcessBtn.setBounds(270, 530, 150, 60);
		add(ReceivingProcessBtn);
		
		JPanel panel = new JPanel();
		panel.setBounds(60, 98, 360, 60);
		add(panel);
		
		timeLabel = new JLabel("...");
		timeLabel.setFont(new Font("굴림", Font.BOLD, 20));
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(timeLabel, BorderLayout.SOUTH);
	}
	
	public void updateTime(String time) {
		timeLabel.setText(time);
	}
	
	public JLabel getTimeLabel() {
		return timeLabel;
	}

	public JButton getInventorySeachBtn() {
		return InventorySeachBtn;
	}

	public JButton getMaterialDispatchBtn() {
		return MaterialDispatchBtn;
	}

	public JButton getProductInspectionBtn() {
		return ProductInspectionBtn;
	}

	public JButton getOrderListBtn() {
		return OrderListBtn;
	}

	public JButton getWareHouseListBtn() {
		return WareHouseListBtn;
	}

	public JButton getInventorymodificationBtn() {
		return InventorymodificationBtn;
	}

	public JButton getStockAccumulationBtn() {
		return StockAccumulationBtn;
	}

	public JButton getReceivingProcessBtn() {
		return ReceivingProcessBtn;
	}
}










