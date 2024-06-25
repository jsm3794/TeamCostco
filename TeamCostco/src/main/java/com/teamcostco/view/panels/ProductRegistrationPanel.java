package main.java.com.teamcostco.view.panels;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import main.java.com.teamcostco.view.textfields.JPlaceholderTextField;

public class ProductRegistrationPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTextField textFieldProductCode;
    private JComboBox<String> comboBoxProductName;
    private JComboBox<String> comboBoxLargeCategory;
    private JComboBox<String> comboBoxMediumCategory;
    private JComboBox<String> comboBoxSmallCategory;
    private JTextField textField;
    private JTextField textField_1;

    /**
     * Create the panel.
     */
    public ProductRegistrationPanel() {
    	
        setBounds(new Rectangle(0, 0, 480, 640));
        setLayout(null);

        JLabel lblNewLabel = new JLabel("상품등록");
        lblNewLabel.setForeground(new Color(255, 255, 255));
        lblNewLabel.setBackground(new Color(0, 122, 255));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("굴림", Font.BOLD, 25));
        lblNewLabel.setBounds(12, 10, 456, 53);
        add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("상품코드");
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1.setBounds(12, 98, 90, 30);
        add(lblNewLabel_1);

        textFieldProductCode = new JPlaceholderTextField("ex) PRID00000001");
        textFieldProductCode.setBounds(114, 98, 354, 30);
        add(textFieldProductCode);
        textFieldProductCode.setColumns(10);

        JLabel lblNewLabel_1_1 = new JLabel("상품명");
        lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1_1.setBounds(12, 138, 90, 30);
        add(lblNewLabel_1_1);

        comboBoxProductName = new JComboBox<>();
        comboBoxProductName.setBounds(114, 138, 354, 30);
        // 예시로 아이템 추가
        comboBoxProductName.addItem("상품명1");
        comboBoxProductName.addItem("상품명2");
        comboBoxProductName.addItem("상품명3");
        add(comboBoxProductName);

        JLabel lblNewLabel_1_2 = new JLabel("대분류");
        lblNewLabel_1_2.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1_2.setBounds(12, 178, 90, 30);
        add(lblNewLabel_1_2);

        comboBoxLargeCategory = new JComboBox<>();
        comboBoxLargeCategory.setBounds(114, 178, 354, 30);
        // 예시로 아이템 추가
        comboBoxLargeCategory.addItem("대분류1");
        comboBoxLargeCategory.addItem("대분류2");
        comboBoxLargeCategory.addItem("대분류3");
        add(comboBoxLargeCategory);

        JLabel lblNewLabel_1_3 = new JLabel("중분류");
        lblNewLabel_1_3.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1_3.setBounds(12, 218, 90, 30);
        add(lblNewLabel_1_3);

        comboBoxMediumCategory = new JComboBox<>();
        comboBoxMediumCategory.setBounds(114, 218, 354, 30);
        // 예시로 아이템 추가
        comboBoxMediumCategory.addItem("중분류1");
        comboBoxMediumCategory.addItem("중분류2");
        comboBoxMediumCategory.addItem("중분류3");
        add(comboBoxMediumCategory);

        JLabel lblNewLabel_1_4 = new JLabel("소분류");
        lblNewLabel_1_4.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1_4.setBounds(12, 258, 90, 30);
        add(lblNewLabel_1_4);

        comboBoxSmallCategory = new JComboBox<>();
        comboBoxSmallCategory.setBounds(114, 258, 354, 30);
        // 예시로 아이템 추가
        comboBoxSmallCategory.addItem("소분류1");
        comboBoxSmallCategory.addItem("소분류2");
        comboBoxSmallCategory.addItem("소분류3");
        add(comboBoxSmallCategory);

        JButton btnRegister = new JButton("상품 등록");
        btnRegister.setBounds(12, 563, 225, 67);
        btnRegister.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 상품 등록 처리 로직 작성
                // 여기에 상품 등록 처리 코드를 추가하세요
                System.out.println("상품이 등록되었습니다.");
            }
        });
        add(btnRegister);

        JButton btnCancel = new JButton("취소");
        btnCancel.setBounds(243, 563, 225, 67);
        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 취소 처리 로직 작성
                // 여기에 취소 처리 코드를 추가하세요
                System.out.println("등록이 취소되었습니다.");
            }
        });
        add(btnCancel);
        
        JLabel lblNewLabel_2 = new JLabel("구입가");
        lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_2.setBounds(240, 319, 174, 35);
        add(lblNewLabel_2);
        
        JLabel lblNewLabel_2_1 = new JLabel("판매가");
        lblNewLabel_2_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_2_1.setBounds(54, 319, 174, 35);
        add(lblNewLabel_2_1);
        
        textField = new JTextField();
        textField.setBounds(243, 364, 174, 41);
        add(textField);
        textField.setColumns(10);
        
        textField_1 = new JTextField();
        textField_1.setColumns(10);
        textField_1.setBounds(54, 364, 174, 41);
        add(textField_1);
        
        JLabel lblNewLabel_1_4_1 = new JLabel("상품");
        lblNewLabel_1_4_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1_4_1.setBounds(12, 425, 90, 30);
        add(lblNewLabel_1_4_1);
        
        JPlaceholderTextField plchldrtxtfldEx = new JPlaceholderTextField("ex) 신라면");
        plchldrtxtfldEx.setColumns(10);
        plchldrtxtfldEx.setBounds(114, 425, 354, 30);
        add(plchldrtxtfldEx);
    }
    
        public static void main(String[] args) {
            EventQueue.invokeLater(new Runnable() {
                public void run() {
                    try {
                        JFrame frame = new JFrame();
                        frame.setSize(500, 700); // 프레임 크기 설정
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        
                        // ProductRegistrationPanel을 프레임에 추가
                        ProductRegistrationPanel panel = new ProductRegistrationPanel();
                        frame.getContentPane().add(panel);
                        
                        frame.setVisible(true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
}
