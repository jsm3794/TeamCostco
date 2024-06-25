package main.java.com.teamcostco.view.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class NewHome extends JPanel {

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
	private JButton ProductRegistration; // 상품 등록
	
	/**
	 * Create the panel.
	 */
	
	public NewHome() {
		
		setSize(new Dimension(480, 640));
		setLayout(null);
		
        JPanel image_panel = new JPanel();
        image_panel.setBounds(12, 40, 456, 144);
        ImageIcon image = new ImageIcon("C:\\aiautomationkdw\\repositories\\JavaStudy\\JavaStudy\\src\\project\\123.png");
        JLabel imageLabel = new JLabel(image);
		image_panel.add(imageLabel);
		add(image_panel);
		
		
		InventorySeachBtn = new JButton("재고조회");
		InventorySeachBtn.setFont(new Font("굴림", Font.BOLD, 22));
		InventorySeachBtn.setBackground(new Color(0, 122, 255));
		InventorySeachBtn.setForeground(new Color(255, 255, 255));
		InventorySeachBtn.setBounds(12, 290, 225, 60);
		add(InventorySeachBtn);
		
		MaterialDispatchBtn = new JButton("재고이동");
		MaterialDispatchBtn.setFont(new Font("굴림", Font.BOLD, 22));
		MaterialDispatchBtn.setBackground(new Color(0, 122, 255));
		MaterialDispatchBtn.setForeground(new Color(255, 255, 255));
		MaterialDispatchBtn.setBounds(12, 360, 225, 60);
		add(MaterialDispatchBtn);
		
		ProductInspectionBtn = new JButton("제품검수");
		ProductInspectionBtn.setFont(new Font("굴림", Font.BOLD, 22));
		ProductInspectionBtn.setBackground(new Color(0, 122, 255));
		ProductInspectionBtn.setForeground(new Color(255, 255, 255));
		ProductInspectionBtn.setBounds(12, 430, 225, 60);
		add(ProductInspectionBtn);
		
		OrderListBtn = new JButton("발주내역");
		OrderListBtn.setFont(new Font("굴림", Font.BOLD, 22));
		OrderListBtn.setBackground(new Color(0, 122, 255));
		OrderListBtn.setForeground(new Color(255, 255, 255));
		OrderListBtn.setBounds(12, 500, 225, 60);
		add(OrderListBtn);
		
		WareHouseListBtn = new JButton("창고목록");
		WareHouseListBtn.setFont(new Font("굴림", Font.BOLD, 22));
		WareHouseListBtn.setBackground(new Color(0, 122, 255));
		WareHouseListBtn.setForeground(new Color(255, 255, 255));
		WareHouseListBtn.setBounds(243, 290, 225, 60);
		add(WareHouseListBtn);
		
		InventorymodificationBtn = new JButton("재고수정");
		InventorymodificationBtn.setFont(new Font("굴림", Font.BOLD, 22));
		InventorymodificationBtn.setBackground(new Color(0, 122, 255));
		InventorymodificationBtn.setForeground(new Color(255, 255, 255));
		InventorymodificationBtn.setBounds(243, 360, 225, 60);
		add(InventorymodificationBtn);
		
		StockAccumulationBtn = new JButton("재고적치");
		StockAccumulationBtn.setFont(new Font("굴림", Font.BOLD, 22));
		StockAccumulationBtn.setBackground(new Color(0, 122, 255));
		StockAccumulationBtn.setForeground(new Color(255, 255, 255));
		StockAccumulationBtn.setBounds(243, 430, 225, 60);
		add(StockAccumulationBtn);
		
		ReceivingProcessBtn = new JButton("입고처리");
		ReceivingProcessBtn.setFont(new Font("굴림", Font.BOLD, 22));
		ReceivingProcessBtn.setBackground(new Color(0, 122, 255));
		ReceivingProcessBtn.setForeground(new Color(255, 255, 255));
		ReceivingProcessBtn.setBounds(243, 500, 225, 60);
		add(ReceivingProcessBtn);
		
		ProductRegistration = new JButton("상품 등록");
		ProductRegistration.setForeground(Color.WHITE);
		ProductRegistration.setFont(new Font("굴림", Font.BOLD, 22));
		ProductRegistration.setBackground(new Color(0, 122, 255));
		ProductRegistration.setBounds(12, 570, 225, 60);
		add(ProductRegistration);
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		panel.setBounds(12, 194, 456, 71);
		add(panel);
		
		timeLabel = new JLabel("...");
		timeLabel.setFont(new Font("굴림", Font.BOLD, 30));
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
	
	public JButton getProductRegistration() {
		return ProductRegistration;
	}
}













