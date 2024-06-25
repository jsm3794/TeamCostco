package main.java.com.teamcostco.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import main.java.com.teamcostco.view.panels.ProductRegistrationPanel;

public class ProductRegistrationController extends PanelController<ProductRegistrationPanel> {

	public ProductRegistrationController() {
		initializationComponents();
		productRegistrationComponents();
	}
	
	public void initializationComponents() {
		view.getInitializationBtn().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
						
			}
		});
	}
	
	public void productRegistrationComponents() {
		view.getproductRegistrationBtn().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
	}
	
	
	@Override
	public String toString() {
		
		return "상품등록";
	}

}
