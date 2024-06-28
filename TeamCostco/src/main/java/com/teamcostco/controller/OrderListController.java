package main.java.com.teamcostco.controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.java.com.teamcostco.MainForm;
import main.java.com.teamcostco.model.OrderDetailModel;
import main.java.com.teamcostco.model.database.DatabaseUtil;
import main.java.com.teamcostco.view.panels.OrderListPanel;

public class OrderListController extends PanelController<OrderListPanel> {
	

    public OrderListController() {
        initControl();
    }

    private void initControl() {
        view.startDateField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchDatabase();
            }
        });

        view.endDateField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchDatabase();
            }
        });

        view.itemNumberField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchDatabase();
            }
        });

        view.supplierField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchDatabase();
            }
        });

        view.searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchDatabase();
            }
        });

        KeyListener enterListener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    searchDatabase();
                }
            }
        };

        view.startDateField.addKeyListener(enterListener);
        view.endDateField.addKeyListener(enterListener);
        view.itemNumberField.addKeyListener(enterListener);
        view.supplierField.addKeyListener(enterListener);
    }

    private void searchDatabase() {
        view.resultPanel.removeAll();

        LocalDate startDate = parseDate(view.startDateField.getText());
        LocalDate endDate = parseDate(view.endDateField.getText());

        List<OrderDetailModel> filteredData = new ArrayList<>();
        
        /*
         *         this.orderRequestId = rs.getInt("ORDER_REQUEST_ID");
			        this.productCode = rs.getString("PRODUCT_CODE");
			        this.orderEmployeeId = rs.getInt("ORDEREMPLOYEE_ID");
			        this.orderQuantity = rs.getInt("ORDER_QUANTITY");
			        this.requestDate = rs.getDate("REQUEST_DATE");
			        this.requestStatus = rs.getString("REQUEST_STATUS");
			        this.clientName = rs.getString("CLIENT_NAME");
			        this.quantityOfWh = rs.getInt("QUANTITY_OF_WH");
         */
        try (Connection conn = DatabaseUtil.getConnection()) {
        	String sql = "SELECT "
        		    + "order_request_id, "
        		    + "o.product_code, "
        		    + "p.product_name AS product_name, "
        		    + "orderemployee_id, "
        		    + "order_quantity, "
        		    + "request_date, "
        		    + "request_status, "
        		    + "client_name, "
        		    + "quantity_of_wh "
        		    + "FROM ORDERREQUEST o "
        		    + "INNER JOIN product p ON p.product_code = o.product_code "
        		    + "WHERE 1=1 ";
            if (startDate != null) {
                sql += " AND REQUEST_DATE >= ?";
            }
            if (endDate != null) {
                sql += " AND REQUEST_DATE <= ?";
            }
            if (!view.itemNumberField.getText().trim().isEmpty()) {
                sql += " AND PRODUCT_CODE LIKE ?";
            }
            if (!view.supplierField.getText().trim().isEmpty()) {
                sql += " AND CLIENT_NAME LIKE ?";
            }

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                int paramIndex = 1;
                if (startDate != null) {
                    pstmt.setDate(paramIndex++, java.sql.Date.valueOf(startDate));
                }
                if (endDate != null) {
                    pstmt.setDate(paramIndex++, java.sql.Date.valueOf(endDate));
                }
                if (!view.itemNumberField.getText().trim().isEmpty()) {
                    pstmt.setString(paramIndex++, "%" + view.itemNumberField.getText().trim() + "%");
                }
                if (!view.supplierField.getText().trim().isEmpty()) {
                    pstmt.setString(paramIndex++, "%" + view.supplierField.getText().trim() + "%");
                }
                
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        filteredData.add(new OrderDetailModel(rs));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (OrderDetailModel data : filteredData) {
            JPanel entryPanel = createEntryPanel(data);
            if (entryPanel != null) {
                view.resultPanel.add(entryPanel);
            }
        }

        view.resultPanel.revalidate();
        view.resultPanel.repaint();
    }

    private JPanel createEntryPanel(OrderDetailModel data) {
        JPanel entryPanel = new JPanel();
        entryPanel.setLayout(new BoxLayout(entryPanel, BoxLayout.Y_AXIS));
        
        entryPanel.add(new JLabel("거래처: " + data.getClientName()));
        entryPanel.add(new JLabel("발주요청ID: " + data.getOrderRequestId()));
        entryPanel.add(new JLabel("상품명: " + data.getProductName()));
        entryPanel.add(new JLabel("발주수량: " + data.getOrderQuantity()));
        entryPanel.add(new JLabel("요청일자: " + data.getRequestDate()));

        entryPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        entryPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, entryPanel.getPreferredSize().height));
        entryPanel.setPreferredSize(new Dimension(460, entryPanel.getPreferredSize().height));

        entryPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                MainForm.nav.push("orderdetail", true, data);
            }
        });
        return entryPanel;
    }

    private LocalDate parseDate(String dateStr) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(dateStr, formatter);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return "발주목록";
    }
}