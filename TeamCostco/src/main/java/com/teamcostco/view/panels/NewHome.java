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
	private JButton defectiveInventoryBtn; // 불량재고
	private JButton orderRequestBtn; // 재고적치
	private JButton pdaSetting; // 입고처리
	private JButton ProductRegistrationBtn; // 상품등록
	
	/**
	 * Create the panel.
	 */
	
	public NewHome() {
		
		JFrame f = new JFrame();
		
		setSize(new Dimension(480, 640));
		setLayout(null);
		
        JPanel image_panel = new JPanel();
        image_panel.setBounds(12, 48, 456, 144);
        ImageIcon image = new ImageIcon(NewHome.class.getResource("/main/resources/homeLogo.png"));
        JLabel imageLabel = new JLabel(image);
		image_panel.add(imageLabel);
		add(image_panel);
		
		
		InventorySeachBtn = new JButton("재고조회");
		InventorySeachBtn.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		InventorySeachBtn.setBackground(new Color(6, 127, 196));
		InventorySeachBtn.setForeground(new Color(255, 255, 255));
		InventorySeachBtn.setBounds(12, 330, 225, 60);
		add(InventorySeachBtn);
		
		MaterialDispatchBtn = new JButton("재고이동");
		MaterialDispatchBtn.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		MaterialDispatchBtn.setBackground(new Color(6, 127, 196));
		MaterialDispatchBtn.setForeground(new Color(255, 255, 255));
		MaterialDispatchBtn.setBounds(243, 330, 225, 60);
		add(MaterialDispatchBtn);
		
		ProductInspectionBtn = new JButton("제품검수");
		ProductInspectionBtn.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		ProductInspectionBtn.setBackground(new Color(6, 127, 196));
		ProductInspectionBtn.setForeground(new Color(255, 255, 255));
		ProductInspectionBtn.setBounds(12, 470, 225, 60);
		add(ProductInspectionBtn);
		
		OrderListBtn = new JButton("발주내역");
		OrderListBtn.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		OrderListBtn.setBackground(new Color(6, 127, 196));
		OrderListBtn.setForeground(new Color(255, 255, 255));
		OrderListBtn.setBounds(12, 400, 225, 60);
		add(OrderListBtn);
		
		defectiveInventoryBtn = new JButton("불량재고");
		defectiveInventoryBtn.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		defectiveInventoryBtn.setBackground(new Color(6, 127, 196));
		defectiveInventoryBtn.setForeground(new Color(255, 255, 255));
		defectiveInventoryBtn.setBounds(12, 540, 225, 60);
		add(defectiveInventoryBtn);
		
		orderRequestBtn = new JButton("발주요청");
		orderRequestBtn.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		orderRequestBtn.setBackground(new Color(6, 127, 196));
		orderRequestBtn.setForeground(new Color(255, 255, 255));
		orderRequestBtn.setBounds(243, 400, 225, 60);
		add(orderRequestBtn);
		
		pdaSetting = new JButton("환경설정");
		pdaSetting.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		pdaSetting.setBackground(new Color(6, 127, 196));
		pdaSetting.setForeground(new Color(255, 255, 255));
		pdaSetting.setBounds(243, 540, 225, 60);
		pdaSetting.setBackground(new Color(196, 127, 6));
		add(pdaSetting);
		
		ProductRegistrationBtn = new JButton("상품등록");
		ProductRegistrationBtn.setForeground(Color.WHITE);
		ProductRegistrationBtn.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		ProductRegistrationBtn.setBackground(new Color(6, 127, 196));
		ProductRegistrationBtn.setBounds(243, 470, 225, 60);
		add(ProductRegistrationBtn);
		
		JPanel panel = new JPanel();
		panel.setBounds(12, 217, 456, 88);
		add(panel);
		
		timeLabel = new JLabel("...");
		timeLabel.setFont(new Font("맑은 고딕", Font.BOLD, 25));
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

	public JButton getDefectiveInventoryBtn() {
		return defectiveInventoryBtn;
	}

	public JButton getOrderRequestBtn() {
		return orderRequestBtn;
	}

	public JButton getReceivingProcessBtn() {
		return pdaSetting;
	}
	
	public JButton getProductRegistrationBtn() {
		return ProductRegistrationBtn;
	}
}













