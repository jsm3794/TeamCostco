package main.java.com.teamcostco.view.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import main.java.com.teamcostco.model.Productinspection;

public class ProductInspectionDetailPanel extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Productinspection> selectedProducts;
    private List<JTextField> missingFields;
    private JButton confirmButton;

    public ProductInspectionDetailPanel() {
        this.missingFields = new ArrayList<>();
        
    }
    

    public List<Productinspection> getSelectedProducts() {
		return selectedProducts;
	}


	public void setSelectedProducts(List<Productinspection> selectedProducts) {
		this.selectedProducts = selectedProducts;
	}


	public void setMissingFields(List<JTextField> missingFields) {
		this.missingFields = missingFields;
	}


	public void setConfirmButton(JButton confirmButton) {
		this.confirmButton = confirmButton;
	}


	public void initializeUI() {
        setLayout(new BorderLayout());
        
        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
        resultPanel.setBackground(Color.WHITE);
        resultPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        for (Productinspection product : selectedProducts) {
            JPanel productPanel = createProductPanel(product);
            resultPanel.add(productPanel);
        }

        JScrollPane scrollPane = new JScrollPane(resultPanel);
        scrollPane.setPreferredSize(new Dimension(400, 300));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        confirmButton = new JButton("검수확인");
        confirmButton.setBackground(new Color(0, 123, 255));
        confirmButton.setForeground(Color.WHITE);
        buttonPanel.add(confirmButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPanel createProductPanel(Productinspection product) {
        JPanel productPanel = new JPanel();
        productPanel.setLayout(new GridLayout(7, 2));
        productPanel.setBackground(Color.WHITE);
        productPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));

        addLabelPair(productPanel, "발주번호", product.getProductcode());
        addLabelPair(productPanel, "카테고리", product.getMain_name());
        addLabelPair(productPanel, "상품명", product.getProduct_name());
        addLabelPair(productPanel, "발주수량", String.valueOf(product.getOrderquantity()));
        addLabelPair(productPanel, "입고수량", String.valueOf(product.getCheckquantity()));
        addLabelPair(productPanel, "입고일자", product.getDateofreceipt().toString());

        JLabel missingLabel = new JLabel("누락개수:");
        missingLabel.setHorizontalAlignment(JLabel.RIGHT);
        missingLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JTextField missingField = new JTextField();
        missingField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        missingFields.add(missingField);

        productPanel.add(missingLabel);
        productPanel.add(missingField);

        return productPanel;
    }

    private void addLabelPair(JPanel panel, String key, String value) {
        JLabel keyLabel = new JLabel(key + ":");
        keyLabel.setHorizontalAlignment(JLabel.RIGHT);
        keyLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JLabel valueLabel = new JLabel(value);
        valueLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        panel.add(keyLabel);
        panel.add(valueLabel);
    }

    public List<JTextField> getMissingFields() {
        return missingFields;
    }

    public JButton getConfirmButton() {
        return confirmButton;
    }
}