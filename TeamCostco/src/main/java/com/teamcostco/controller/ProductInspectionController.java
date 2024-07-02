package main.java.com.teamcostco.controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import main.java.com.teamcostco.model.ProductInspection;
import main.java.com.teamcostco.model.database.DatabaseUtil;
import main.java.com.teamcostco.view.panels.ProductInspectionPanel;

/**
 * 제품 검수 컨트롤러 클래스
 */
public class ProductInspectionController extends PanelController<ProductInspectionPanel> {

	private List<ProductInspection> productInspections;
	private List<String> categories;
	private ProductInspection selectedProduct;
	private List<ProductInspection> selectedProducts = new ArrayList<>();

	/**
	 * 생성자
	 */
	public ProductInspectionController() {
		productInspections = new ArrayList<>();
		loadTableData();

		categories = retrieveCategoriesFromDatabase();
		for (String item : categories) {
			view.categoryComboBox.addItem(item);
		}

		view.productNumberField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				updateTableData();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				updateTableData();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				updateTableData();
			}
		});

		view.categoryComboBox.addActionListener(e -> updateTableData());

		view.checkButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				displayInspectionResultPanels(true);
			}
		});

	}

	/**
	 * 테이블 업데이트 메서드
	 * 
	 * @param list 제품 검수 목록
	 */
	private void updateTable(List<ProductInspection> list) {
		view.resultPanel.removeAll();
		for (ProductInspection data : list) {
			JPanel entryPanel = createEntryPanel(data);
			if (entryPanel != null) {
				view.resultPanel.add(entryPanel);
			}
		}
		view.resultPanel.revalidate();
		view.resultPanel.repaint();
	}

	/**
	 * 제품 검수 항목 패널 생성 메서드
	 * 
	 * @param data 제품 검수 데이터
	 * @return 제품 검수 항목 패널
	 */
	private JPanel createEntryPanel(ProductInspection data) {
		JPanel entryPanel = new JPanel();
		entryPanel.setLayout(new BoxLayout(entryPanel, BoxLayout.X_AXIS));

		JPanel checkBoxPanel = new JPanel();
		checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.Y_AXIS));
		JCheckBox checkBox = new JCheckBox();
		checkBox.setAlignmentX(Component.CENTER_ALIGNMENT);
		checkBoxPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		checkBoxPanel.add(checkBox);
		entryPanel.add(checkBoxPanel);

		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
		infoPanel.add(new JLabel("상품명: " + data.getProduct_name()));
		infoPanel.add(new JLabel("상품코드: " + data.getProductcode()));
		infoPanel.add(new JLabel("대분류: " + data.getMain_name()));
		infoPanel.add(new JLabel("발주수량: " + data.getOrderquantity()));
		infoPanel.add(new JLabel("입고수량: " + data.getCheckquantity()));

		entryPanel.add(infoPanel);

		entryPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		entryPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, entryPanel.getPreferredSize().height));
		entryPanel.setPreferredSize(new Dimension(460, entryPanel.getPreferredSize().height));

		entryPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				selectedProduct = data;
				updateSelectedProductHighlight();
			}
		});

		checkBox.addActionListener(e -> {
			if (checkBox.isSelected()) {
				selectedProducts.add(data);
			} else {
				selectedProducts.remove(data);
			}
		});

		return entryPanel;
	}

	/**
	 * 선택된 제품 강조 표시 업데이트 메서드
	 */
	private void updateSelectedProductHighlight() {
		for (Component component : view.resultPanel.getComponents()) {
			if (component instanceof JPanel) {
				JPanel panel = (JPanel) component;
				ProductInspection product = getProductFromPanel(panel);
				if (product != null && product.equals(selectedProduct)) {
					panel.setBackground(Color.LIGHT_GRAY);
				} else {
					panel.setBackground(null);
				}
			}
		}
		view.resultPanel.repaint();
	}

	/**
	 * 데이터베이스에서 카테고리 목록을 가져오는 메서드
	 * 
	 * @return 카테고리 목록
	 */
	private List<String> retrieveCategoriesFromDatabase() {
		List<String> categories = new ArrayList<>();
		categories.add("전체");

		try (Connection conn = DatabaseUtil.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT main_name FROM maincategory")) {

			while (rs.next()) {
				categories.add(rs.getString("main_name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(view, "카테고리를 가져오는 중 오류가 발생했습니다.", "데이터베이스 오류", JOptionPane.ERROR_MESSAGE);
		}

		return categories;
	}

	/**
	 * 테이블 데이터 로드 메서드
	 */
	private void loadTableData() {
		productInspections.clear();

		new SwingWorker<List<ProductInspection>, Void>() {
			@Override
			protected List<ProductInspection> doInBackground() throws Exception {
				return fetchDataFromDatabase(null, "전체");
			}

			@Override
			protected void done() {
				try {
					List<ProductInspection> allProducts = get();
					updateTable(allProducts);
				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(view, "데이터를 가져오는 중 오류가 발생했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
				}
			}
		}.execute();
	}

	/**
	 * 데이터베이스에서 제품 검수 데이터를 가져오는 메서드
	 * 
	 * @param productName 제품명
	 * @param mainname    카테고리 이름
	 * @return 제품 검수 목록
	 */
	private List<ProductInspection> fetchDataFromDatabase(String productName, String mainname) {
		List<ProductInspection> inspections = new ArrayList<>();

		StringBuilder query = new StringBuilder("SELECT product_code, main_name, product_name, order_quantity,"
				+ " check_quantity, DATE_OF_RECEIPT FROM productinspection INNER JOIN product using(product_code) "
				+ "INNER JOIN maincategory USING (main_id) WHERE 1=1");

		List<Object> params = new ArrayList<>();

		if (productName != null && !productName.isEmpty()) {
			query.append(" AND product_name LIKE ?");
			params.add("%" + productName + "%");
		}

		if (mainname != null && !"전체".equals(mainname)) {
			query.append(" AND main_name = ?");
			params.add(mainname);
		}

		try (Connection conn = DatabaseUtil.getConnection();
				PreparedStatement ps = conn.prepareStatement(query.toString())) {

			for (int i = 0; i < params.size(); i++) {
				ps.setObject(i + 1, params.get(i));
			}

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					ProductInspection inspection = new ProductInspection(rs);
					inspections.add(inspection);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(view, "데이터베이스에서 데이터를 가져오는 중 오류가 발생했습니다.", "데이터베이스 오류",
					JOptionPane.ERROR_MESSAGE);
		}

		return inspections;
	}

	/**
	 * 제품번호 및 카테고리에 따라 테이블 데이터를 업데이트하는 메서드
	 */
	private void updateTableData() {
		String productCode = view.productNumberField.getText().trim();
		String selectedCategory = (String) view.categoryComboBox.getSelectedItem();

		new SwingWorker<List<ProductInspection>, Void>() {
			@Override
			protected List<ProductInspection> doInBackground() throws Exception {
				return fetchDataFromDatabase(productCode, selectedCategory);
			}

			@Override
			protected void done() {
				try {
					List<ProductInspection> filteredProducts = get();
					updateTable(filteredProducts);
				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(view, "데이터를 가져오는 중 오류가 발생했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
				}
			}
		}.execute();
	}

	/**
	 * 패널에서 제품 검수 데이터 객체를 추출하는 메서드
	 * 
	 * @param panel 제품 검수 데이터가 표시된 패널
	 * @return 제품 검수 데이터 객체(Productinspection), 해당 데이터가 없는 경우 null 반환
	 */
	private ProductInspection getProductFromPanel(JPanel panel) {
		JPanel infoPanel = (JPanel) panel.getComponent(1);
		JLabel productNameLabel = (JLabel) infoPanel.getComponent(0);
		String productName = productNameLabel.getText().substring(5);

		JLabel productCodeLabel = (JLabel) infoPanel.getComponent(1);
		String productCode = productCodeLabel.getText().substring(6);

		for (ProductInspection product : productInspections) {
			if (product.getProduct_name().equals(productName) && product.getProductcode().equals(productCode)) {
				return product;
			}
		}

		return null;
	}

	/**
	 * 검수 결과 패널을 표시하는 메서드
	 * 
	 * @param isInspectionPassed true면 검수 통과, false면 불량 확인
	 */
	private void displayInspectionResultPanels(boolean isInspectionPassed) {
		// 첫 번째 패널에서만 선택된 제품 목록을 확인하고 메시지를 표시함
		if (isFirstPanel()) {
			// 선택된 제품 목록이 비어있는 경우 메시지 표시
			if (selectedProducts.isEmpty()) {
				JOptionPane.showMessageDialog(view, "제품을 선택하세요.", isInspectionPassed ? "검수 확인" : "불량 확인",
						JOptionPane.WARNING_MESSAGE);
				return;
			}
		}

		JPanel resultPanel = new JPanel();
		resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
		resultPanel.setBackground(Color.WHITE);
		resultPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		List<JTextField> missingFields = new ArrayList<>();

		for (ProductInspection product : selectedProducts) {
			JPanel productPanel = new JPanel();
			productPanel.setLayout(new GridLayout(7, 2)); // 행 수를 7로 변경 (체크박스 제외)
			productPanel.setBackground(Color.WHITE);
			productPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));

			addLabelPair(productPanel, "발주번호", product.getProductcode());
			addLabelPair(productPanel, "카테고리", product.getMain_name());
			addLabelPair(productPanel, "상품명", product.getProduct_name());
			addLabelPair(productPanel, "발주수량", String.valueOf(product.getOrderquantity()));
			addLabelPair(productPanel, "입고수량", String.valueOf(product.getCheckquantity()));
			addLabelPair(productPanel, "입고일자", product.getDateofreceipt().toString());

			// 누락개수 입력 필드 추가
			JLabel missingLabel = new JLabel("누락개수:");
			missingLabel.setHorizontalAlignment(JLabel.RIGHT);
			missingLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			JTextField missingField = new JTextField();
			missingField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			missingFields.add(missingField);

			productPanel.add(missingLabel);
			productPanel.add(missingField);

			resultPanel.add(productPanel);
		}

		JScrollPane scrollPane = new JScrollPane(resultPanel);
		scrollPane.setPreferredSize(new Dimension(400, 300));

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton confirmButton = new JButton("검수확인");
		confirmButton.setBackground(new Color(0, 123, 255));
		confirmButton.setForeground(Color.WHITE);
		confirmButton.addActionListener(e -> {
			// 첫 번째 패널에서만 불량 제품 저장
			updateInventory(selectedProducts);
			if (isFirstPanel()) {
				saveDefectProducts(selectedProducts, missingFields);
			}
			JOptionPane.showMessageDialog(view, "검수 결과가 저장되었습니다.");
		});
		buttonPanel.add(confirmButton);

		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(scrollPane, BorderLayout.CENTER);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);

		JOptionPane.showOptionDialog(view, mainPanel, isInspectionPassed ? "검수 확인 결과" : "불량 확인 결과",
				JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[] {}, null);
	}

	/**
	 * 첫 번째 패널인지 확인하는 메서드
	 * 
	 * @return 첫 번째 패널이면 true, 아니면 false
	 */
	private boolean isFirstPanel() {
		// 첫 번째 패널을 구분하는 조건을 여기에 추가
		// 예를 들어 view.checkButton을 기준으로 첫 번째 패널 여부를 판단할 수 있음
		return true; // 첫 번째 패널인 경우 true 반환
	}

	private void updateInventory(List<ProductInspection> products) {
		// SQL 쿼리문
		String sql = "UPDATE product SET CURRENT_INVENTORY = CURRENT_INVENTORY + ? WHERE PRODUCT_CODE = ?";
		try (Connection conn = DatabaseUtil.getConnection()) {

			try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
				// 각 제품에 대해 입고 수량 업데이트
				for (ProductInspection product : products) {
					int checkQuantity = product.getCheckquantity();
					String productCode = product.getProductcode();

					// 제품 코드와 입고 수량 검증
					if (productCode == null || productCode.isEmpty() || checkQuantity <= 0) {
						System.out.println("Invalid product code or check quantity for product: " + product);
						continue; // 잘못된 데이터는 건너뜀
					}

					// PreparedStatement에 값 설정
					pstmt.setInt(1, checkQuantity);
					pstmt.setString(2, productCode);

					// SQL 실행
					pstmt.executeUpdate();

					System.out.println(checkQuantity);
					System.out.println(productCode);
				}

			} catch (SQLException ex) {
				// 예외 발생 시 롤백
				ex.printStackTrace();
				conn.rollback();
				throw ex; // 예외를 다시 throw하여 상위 호출자에게 전파
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			// 데이터베이스 오류를 사용자에게 알림
			JOptionPane.showMessageDialog(view, "입고 수량을 업데이트하는 중 오류가 발생했습니다.", "데이터베이스 오류", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * 불량 제품을 DB의 defectproduct 테이블에 저장하는 메서드
	 * 
	 * @param products      검수 결과가 불량으로 판정된 제품 목록
	 * @param missingFields 누락 개수 입력 필드 목록
	 */
	private void saveDefectProducts(List<ProductInspection> products, List<JTextField> missingFields) {
		// SQL 쿼리문
		String sql = "INSERT INTO defectproduct (PRODUCT_CODE, DEFECT_AMOUNT, CREATED_AT) VALUES (?, ?, CURRENT_TIMESTAMP)";

		try (Connection conn = DatabaseUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			// 각 제품에 대해 불량 제품 정보를 저장
			for (int i = 0; i < products.size(); i++) {
				ProductInspection product = products.get(i);
				int defectAmount = 0;
				try {
					// 누락 개수 입력 필드에서 숫자를 가져옴
					defectAmount = Integer.parseInt(missingFields.get(i).getText());
				} catch (NumberFormatException ex) {
					// 누락 개수가 숫자가 아닌 경우 0으로 처리
				}

				// 누락 개수가 0보다 큰 경우에만 저장
				if (defectAmount > 0) {
					// PreparedStatement에 값 설정
					pstmt.setString(1, product.getProductcode());
					pstmt.setInt(2, defectAmount);

					// SQL 실행
					pstmt.executeUpdate();
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(view, "불량 제품을 저장하는 중 오류가 발생했습니다.", "데이터베이스 오류", JOptionPane.ERROR_MESSAGE);
		}
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

	@Override
	public String toString() {
		return "Product Inspection Controller";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		ProductInspection that = (ProductInspection) obj;
		return Objects.equals(getProductcode(), that.getProductcode());
	}

	private Object getProductcode() {
		return null;
	}

	@Override
	public int hashCode() {
		return Objects.hash(getProductcode());
	}
}