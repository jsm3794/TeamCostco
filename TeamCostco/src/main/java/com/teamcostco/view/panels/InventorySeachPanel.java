package main.java.com.teamcostco.view.panels;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import main.java.com.teamcostco.model.database.DatabaseUtil;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class InventorySeachPanel {

	    public static void main(String[] args) {
	        // 메인 윈도우 생성
	        JFrame frame = new JFrame("재고조회");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
	        frame.setSize(480, 640); 
	        frame.setLayout(new BorderLayout()); 

	        // 카테고리 콤보박스와 검색 텍스트 필드를 위한 패널 생성
	        JPanel topPanel = new JPanel(new GridBagLayout());
	        GridBagConstraints gbc = new GridBagConstraints();
	        gbc.insets = new Insets(5, 5, 5, 5); 

	        // 카테고리 선택을 위한 콤보박스 추가
	        JLabel categoryLabel = new JLabel("카테고리");
	        gbc.gridx = 0; 
	        gbc.gridy = 0; 
	        topPanel.add(categoryLabel, gbc);

	        JComboBox<String> categoryComboBox = new JComboBox<>();
	        loadCategories(categoryComboBox); 
	        gbc.gridx = 1;
	        gbc.gridy = 0;
	        topPanel.add(categoryComboBox, gbc);

	        // 상품명을 검색하기 위한 텍스트 필드 추가
	        JLabel searchLabel = new JLabel("상품명");
	        gbc.gridx = 0;
	        gbc.gridy = 1;
	        topPanel.add(searchLabel, gbc);

	        JTextField searchField = new JTextField(10);
	        gbc.gridx = 1;
	        gbc.gridy = 1;
	        topPanel.add(searchField, gbc);

	        // 검색 버튼 추가
	        JButton searchButton = new JButton("검색");
	        gbc.gridx = 2;
	        gbc.gridy = 1;
	        topPanel.add(searchButton, gbc);

	        // 상단 패널을 프레임에 추가
	        frame.add(topPanel, BorderLayout.NORTH);

	        // 제품 정보를 표시할 테이블 생성
	        String[] columnNames = {"구역", "카테고리", "상품명"};
	        DefaultTableModel model = new DefaultTableModel(columnNames, 0); 
	        JTable table = new JTable(model);
	        JScrollPane scrollPane = new JScrollPane(table); 

	        // 테이블을 포함한 스크롤 팬을 프레임에 추가
	        frame.add(scrollPane, BorderLayout.CENTER);

	        // 검색 버튼 클릭 시 DB에서 데이터를 가져와 테이블에 표시
	        searchButton.addActionListener(e -> {
	            String searchKeyword = searchField.getText();
	            loadData(model, searchKeyword);
	        });

	        
	        frame.setVisible(true);
	    }

	 
	    private static void loadCategories(JComboBox<String> comboBox) {
	        Connection connection = null;
	        PreparedStatement pstmt = null;
	        ResultSet rs = null;

	        try {
	            connection = DatabaseUtil.getConnection();

	            String sql = "SELECT categori_name FROM Categori";
	            pstmt = connection.prepareStatement(sql);

	            rs = pstmt.executeQuery();
	            while (rs.next()) {
	                comboBox.addItem(rs.getString("categori_name"));
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	         
	            DatabaseUtil.close(connection, pstmt, rs);
	        }
	    }

	   
	    private static void loadData(DefaultTableModel model, String searchKeyword) {
	        Connection connection = null;
	        PreparedStatement pstmt = null;
	        ResultSet rs = null;

	        try {
	            connection = DatabaseUtil.getConnection();

	            String sql = "SELECT s.storage_id, c.categori_name, p.product_name " +
	                         "FROM Product p " +
	                         "JOIN Categori c ON p.categori_id = c.categori_id " +
	                         "JOIN Storage s ON p.product_id = s.product_id " +
	                         "WHERE p.product_name LIKE ?";
	            pstmt = connection.prepareStatement(sql);
	            pstmt.setString(1, "%" + searchKeyword + "%");

	            rs = pstmt.executeQuery();

	            model.setRowCount(0);

	            while (rs.next()) {
	                String storageId = rs.getString("storage_id");
	                String categoryName = rs.getString("categori_name");
	                String productName = rs.getString("product_name");

	                model.addRow(new Object[]{storageId, categoryName, productName});
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            DatabaseUtil.close(connection, pstmt, rs);
	        }
	    }
	}

