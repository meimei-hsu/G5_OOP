import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import style.BubbleBorder;

public class RangePanel extends JPanel {
	private static final int PANEL_WIDTH = 350;
	private static final int PANEL_HEIGHT = 70;

	private JLabel title;
	private JPanel modePanel, testPanel, yearPanel, subjectPanel, typePanel, degreePanel, countPanel, totalPanel;
	private JRadioButton mode_Review, mode_New;
	private JRadioButton test_GSAT, test_AST;
	private JCheckBox year_109, year_108;
	private JRadioButton subject_Chi, subject_Eng, subject_MathA, subject_MathB, subject_Geo, subject_His, subject_Civ,
			subject_Bio, subject_Che, subject_Phy;
	private JCheckBox type_Single, type_Multiple, type_Combination;
	private JCheckBox degree_Easy, degree_Medium, degree_Hard;
	private JComboBox<Integer> count;
	private JButton startBtn, backButton;
	private ButtonGroup modeGroup, testGroup, subjectGroup;

	private String userID;
	private static int testIDNow = 0;
	private ResultSet result = null;
	private Connection conn;

	public RangePanel() {
		try {
			String server = "jdbc:mysql://140.119.19.73:9306/";
			String database = "MG05";
			String url = server + database
					+ "?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&failOverReadOnly=false";
			String username = "MG05";
			String password = "9mMuzQ";
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			System.out.println("<Range Panel> constructor: " + e.getMessage());
		}

		startBtn = new JButton("Done");
		startBtn.setBackground(Color.decode("#F8EFD4"));
		Font font1 = new Font("微軟正黑體", Font.BOLD, 20);
		startBtn.setFont(font1);
		Color color1 = new Color(139, 69, 19);
		startBtn.setForeground(color1);
		startBtn.setBorder(new BubbleBorder(Color.decode("#E88D67"), 2, 30, 0));
		startBtn.setPreferredSize(new Dimension(120, 30));

		backButton = new JButton("Back");
		backButton.setBackground(Color.decode("#F8EFD4"));
		backButton.setFont(font1);
		backButton.setForeground(color1);
		backButton.setBorder(new BubbleBorder(Color.decode("#E88D67"), 2, 30, 0));
		backButton.setPreferredSize(new Dimension(120, 30));

		creaTitle();
		creaModeComp();
		creaTestComp();
		creaYearComp();
		creaSubjectComp();
		creaTypeComp();
		creaDegreeComp();
		creaCountComp();
		creaTotalPanel();
	}

	public void creaTitle() {
		ImageIcon testIcon = new ImageIcon(
				new ImageIcon("images/test.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
		title = new JLabel("考試範圍");
		title.setIcon(testIcon);
		title.setVerticalTextPosition(SwingConstants.CENTER);
		title.setHorizontalTextPosition(SwingConstants.RIGHT);
		title.setFont(new Font("微軟正黑體", Font.BOLD, 40));
		title.setBackground(Color.decode("#F8EFD4"));
		title.setForeground(new Color(139, 69, 19));
		Border empty = BorderFactory.createEmptyBorder();
		title.setBorder(empty);
		// title.setBorder(new LineBorder(Color.decode("#E88D67")));
	}

	public void creaModeComp() {
		mode_Review = new JRadioButton("複習");
		mode_New = new JRadioButton("全新題目");
		mode_Review.setBackground(Color.decode("#F8EFD4"));
		mode_New.setBackground(Color.decode("#F8EFD4"));

		modeGroup = new ButtonGroup();
		modeGroup.add(mode_Review);
		modeGroup.add(mode_New);

		Font font1 = new Font("微軟正黑體", Font.BOLD, 22);
		mode_Review.setFont(font1);
		mode_Review.setBackground(new Color(255, 239, 213));
		Color color1 = new Color(139, 69, 19);
		mode_Review.setForeground(color1);
		Border empty = BorderFactory.createEmptyBorder();
		mode_Review.setBorder(empty);
		mode_Review.setBorder(new LineBorder(Color.decode("#E88D67"), 5));
		mode_Review.setFocusPainted(false);

		mode_New.setFont(font1);
		mode_New.setBackground(new Color(255, 239, 213));
		mode_New.setForeground(color1);
		mode_New.setBorder(empty);
		mode_New.setBorder(new LineBorder(Color.decode("#E88D67"), 5));
		mode_New.setFocusPainted(false);

		Font font = new Font("微軟正黑體", Font.BOLD, 16);
		Color color = new Color(139, 69, 19);
		Border blMode = BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder((Color.decode("#E88D67")), Color.YELLOW), "模式", TitledBorder.CENTER,
				TitledBorder.DEFAULT_POSITION, font, color);

		modePanel = new JPanel();
		modePanel.add(mode_Review);
		modePanel.add(mode_New);
		modePanel.setBorder(blMode);
		modePanel.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
	}

	public void creaTestComp() {
		test_GSAT = new JRadioButton("學測");
		test_AST = new JRadioButton("指考");
		test_GSAT.setBackground(Color.decode("#F8EFD4"));
		test_AST.setBackground(Color.decode("#F8EFD4"));

		testGroup = new ButtonGroup();
		testGroup.add(test_GSAT);
		testGroup.add(test_AST);

		Font font1 = new Font("微軟正黑體", Font.BOLD, 22);
		test_GSAT.setFont(font1);
		test_GSAT.setBackground(new Color(255, 239, 213));
		Color color1 = new Color(139, 69, 19);
		test_GSAT.setForeground(color1);
		Border empty = BorderFactory.createEmptyBorder();
		test_GSAT.setBorder(empty);
		test_GSAT.setBorder(new LineBorder(Color.decode("#E88D67"), 5));
		test_GSAT.setFocusPainted(false);

		test_AST.setFont(font1);
		test_AST.setBackground(new Color(255, 239, 213));
		test_AST.setForeground(color1);
		test_AST.setBorder(empty);
		test_AST.setBorder(new LineBorder(Color.decode("#E88D67"), 5));
		test_AST.setFocusPainted(false);

		Font font = new Font("微軟正黑體", Font.BOLD, 16);
		Color color = new Color(139, 69, 19);
		Border blTest = BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder((Color.decode("#E88D67")), Color.YELLOW), "考試項目", TitledBorder.CENTER,
				TitledBorder.DEFAULT_POSITION, font, color);

		testPanel = new JPanel();
		testPanel.add(test_GSAT);
		testPanel.add(test_AST);
		testPanel.setBorder(blTest);
		testPanel.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
	}

	public void creaYearComp() {
		year_109 = new JCheckBox("109");
		year_108 = new JCheckBox("108");
		year_109.setBackground(Color.decode("#F8EFD4"));
		year_108.setBackground(Color.decode("#F8EFD4"));

		Font font = new Font("微軟正黑體", Font.BOLD, 16);
		Color color = new Color(139, 69, 19);
		Border blYear = BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder((Color.decode("#E88D67")), Color.YELLOW), "年度", TitledBorder.CENTER,
				TitledBorder.DEFAULT_POSITION, font, color);

		Font font1 = new Font("微軟正黑體", Font.BOLD, 22);
		year_109.setFont(font1);
		year_109.setBackground(new Color(255, 239, 213));
		Color color1 = new Color(139, 69, 19);
		year_109.setForeground(color1);
		Border empty = BorderFactory.createEmptyBorder();
		year_109.setBorder(empty);
		year_109.setBorder(new LineBorder(Color.decode("#E88D67"), 5));
		year_109.setFocusPainted(false);

		year_108.setFont(font1);
		year_108.setBackground(new Color(255, 239, 213));
		year_108.setForeground(color1);
		year_108.setBorder(empty);
		year_108.setBorder(new LineBorder(Color.decode("#E88D67"), 5));
		year_108.setFocusPainted(false);

		yearPanel = new JPanel();
		yearPanel.add(year_109);
		yearPanel.add(year_108);
		yearPanel.setBorder(blYear);
		yearPanel.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
	}

	public void creaSubjectComp() {
		subject_Chi = new JRadioButton("國文");
		subject_Eng = new JRadioButton("英文");
		subject_MathA = new JRadioButton("數學甲");
		subject_MathB = new JRadioButton("數學乙");
		subject_Geo = new JRadioButton("地理");
		subject_His = new JRadioButton("歷史");
		subject_Civ = new JRadioButton("公民");
		subject_Bio = new JRadioButton("生物");
		subject_Che = new JRadioButton("化學");
		subject_Phy = new JRadioButton("物理");

		subjectGroup = new ButtonGroup();
		subjectGroup.add(subject_Chi);
		subjectGroup.add(subject_Eng);
		subjectGroup.add(subject_MathA);
		subjectGroup.add(subject_MathB);
		subjectGroup.add(subject_Geo);
		subjectGroup.add(subject_His);
		subjectGroup.add(subject_Civ);
		subjectGroup.add(subject_Bio);
		subjectGroup.add(subject_Che);
		subjectGroup.add(subject_Phy);

		Font font = new Font("微軟正黑體", Font.BOLD, 16);
		Color color = new Color(139, 69, 19);
		Border blSubject = BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder((Color.decode("#E88D67")), Color.YELLOW), "科目", TitledBorder.CENTER,
				TitledBorder.DEFAULT_POSITION, font, color);

		Font font1 = new Font("微軟正黑體", Font.BOLD, 22);
		subject_Chi.setFont(font1);
		subject_Chi.setBackground(new Color(255, 239, 213));
		Color color1 = new Color(139, 69, 19);
		subject_Chi.setForeground(color1);
		Border empty = BorderFactory.createEmptyBorder();
		subject_Chi.setBorder(empty);
		subject_Chi.setBorder(new LineBorder(Color.decode("#E88D67"), 5));
		subject_Chi.setFocusPainted(false);

		subject_Eng.setFont(font1);
		subject_Eng.setBackground(new Color(255, 239, 213));
		subject_Eng.setForeground(color1);
		subject_Eng.setBorder(empty);
		subject_Eng.setBorder(new LineBorder(Color.decode("#E88D67"), 5));
		subject_Eng.setFocusPainted(false);

		subject_MathA.setFont(font1);
		subject_MathA.setBackground(new Color(255, 239, 213));
		subject_MathA.setForeground(color1);
		subject_MathA.setBorder(empty);
		subject_MathA.setBorder(new LineBorder(Color.decode("#E88D67"), 5));
		subject_MathA.setFocusPainted(false);

		subject_MathB.setFont(font1);
		subject_MathB.setBackground(new Color(255, 239, 213));
		subject_MathB.setForeground(color1);
		subject_MathB.setBorder(empty);
		subject_MathB.setBorder(new LineBorder(Color.decode("#E88D67"), 5));
		subject_MathB.setFocusPainted(false);

		subject_Geo.setFont(font1);
		subject_Geo.setBackground(new Color(255, 239, 213));
		subject_Geo.setForeground(color1);
		subject_Geo.setBorder(empty);
		subject_Geo.setBorder(new LineBorder(Color.decode("#E88D67"), 5));
		subject_Geo.setFocusPainted(false);

		subject_His.setFont(font1);
		subject_His.setBackground(new Color(255, 239, 213));
		subject_His.setForeground(color1);
		subject_His.setBorder(empty);
		subject_His.setBorder(new LineBorder(Color.decode("#E88D67"), 5));
		subject_His.setFocusPainted(false);

		subject_Civ.setFont(font1);
		subject_Civ.setBackground(new Color(255, 239, 213));
		subject_Civ.setForeground(color1);
		subject_Civ.setBorder(empty);
		subject_Civ.setBorder(new LineBorder(Color.decode("#E88D67"), 5));
		subject_Civ.setFocusPainted(false);

		subject_Bio.setFont(font1);
		subject_Bio.setBackground(new Color(255, 239, 213));
		subject_Bio.setForeground(color1);
		subject_Bio.setBorder(empty);
		subject_Bio.setBorder(new LineBorder(Color.decode("#E88D67"), 5));
		subject_Bio.setFocusPainted(false);

		subject_Che.setFont(font1);
		subject_Che.setBackground(new Color(255, 239, 213));
		subject_Che.setForeground(color1);
		subject_Che.setBorder(empty);
		subject_Che.setBorder(new LineBorder(Color.decode("#E88D67"), 5));
		subject_Che.setFocusPainted(false);

		subject_Phy.setFont(font1);
		subject_Phy.setBackground(new Color(255, 239, 213));
		subject_Phy.setForeground(color1);
		subject_Phy.setBorder(empty);
		subject_Phy.setBorder(new LineBorder(Color.decode("#E88D67"), 5));
		subject_Phy.setFocusPainted(false);

		subjectPanel = new JPanel();
		subjectPanel.add(subject_Chi);
		subjectPanel.add(subject_Eng);
		subjectPanel.add(subject_MathA);
		subjectPanel.add(subject_MathB);
		subjectPanel.add(subject_Geo);
		subjectPanel.add(subject_His);
		subjectPanel.add(subject_Civ);
		subjectPanel.add(subject_Bio);
		subjectPanel.add(subject_Che);
		subjectPanel.add(subject_Phy);
		subject_Chi.setBackground(Color.decode("#F8EFD4"));
		subject_Eng.setBackground(Color.decode("#F8EFD4"));
		subject_MathA.setBackground(Color.decode("#F8EFD4"));
		subject_MathB.setBackground(Color.decode("#F8EFD4"));
		subject_Geo.setBackground(Color.decode("#F8EFD4"));
		subject_His.setBackground(Color.decode("#F8EFD4"));
		subject_Civ.setBackground(Color.decode("#F8EFD4"));
		subject_Bio.setBackground(Color.decode("#F8EFD4"));
		subject_Che.setBackground(Color.decode("#F8EFD4"));
		subject_Phy.setBackground(Color.decode("#F8EFD4"));
		subjectPanel.setBorder(blSubject);
		subjectPanel.setPreferredSize(new Dimension(PANEL_WIDTH, 100));
	}

	public void creaTypeComp() {
		type_Single = new JCheckBox("單選題");
		type_Multiple = new JCheckBox("複選題");
		type_Combination = new JCheckBox("題組題");
		type_Single.setBackground(Color.decode("#F8EFD4"));
		type_Multiple.setBackground(Color.decode("#F8EFD4"));
		type_Combination.setBackground(Color.decode("#F8EFD4"));

		Font font = new Font("微軟正黑體", Font.BOLD, 16);
		Color color = new Color(139, 69, 19);
		Border blType = BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder((Color.decode("#E88D67")), Color.YELLOW), "題型", TitledBorder.CENTER,
				TitledBorder.DEFAULT_POSITION, font, color);

		Font font1 = new Font("微軟正黑體", Font.BOLD, 22);
		type_Single.setFont(font1);
		type_Single.setBackground(new Color(255, 239, 213));
		Color color1 = new Color(139, 69, 19);
		type_Single.setForeground(color1);
		Border empty = BorderFactory.createEmptyBorder();
		type_Single.setBorder(empty);
		type_Single.setBorder(new LineBorder(Color.decode("#E88D67"), 5));
		type_Single.setFocusPainted(false);

		type_Multiple.setFont(font1);
		type_Multiple.setBackground(new Color(255, 239, 213));
		type_Multiple.setForeground(color1);
		type_Multiple.setBorder(empty);
		type_Multiple.setBorder(new LineBorder(Color.decode("#E88D67"), 5));
		type_Multiple.setFocusPainted(false);

		type_Combination.setFont(font1);
		type_Combination.setBackground(new Color(255, 239, 213));
		type_Combination.setForeground(color1);
		type_Combination.setBorder(empty);
		type_Combination.setBorder(new LineBorder(Color.decode("#E88D67"), 5));
		type_Combination.setFocusPainted(false);

		typePanel = new JPanel();
		typePanel.add(type_Single);
		typePanel.add(type_Multiple);
		typePanel.add(type_Combination);
		typePanel.setBorder(blType);
		typePanel.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
	}

	public void creaDegreeComp() {
		degree_Easy = new JCheckBox("易");
		degree_Medium = new JCheckBox("中");
		degree_Hard = new JCheckBox("難");
		degree_Easy.setBackground(Color.decode("#F8EFD4"));
		degree_Medium.setBackground(Color.decode("#F8EFD4"));
		degree_Hard.setBackground(Color.decode("#F8EFD4"));

		Font font = new Font("微軟正黑體", Font.BOLD, 16);
		Color color = new Color(139, 69, 19);
		Border blDegree = BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder((Color.decode("#E88D67")), Color.YELLOW), "難易度", TitledBorder.CENTER,
				TitledBorder.DEFAULT_POSITION, font, color);

		Font font1 = new Font("微軟正黑體", Font.BOLD, 22);
		degree_Easy.setFont(font1);
		degree_Easy.setBackground(new Color(255, 239, 213));
		Color color1 = new Color(139, 69, 19);
		degree_Easy.setForeground(color1);
		Border empty = BorderFactory.createEmptyBorder();
		degree_Easy.setBorder(empty);
		degree_Easy.setBorder(new LineBorder(Color.decode("#E88D67"), 5));
		degree_Easy.setFocusPainted(false);

		degree_Medium.setFont(font1);
		degree_Medium.setBackground(new Color(255, 239, 213));
		degree_Medium.setForeground(color1);
		degree_Medium.setBorder(empty);
		degree_Medium.setBorder(new LineBorder(Color.decode("#E88D67"), 5));
		degree_Medium.setFocusPainted(false);

		degree_Hard.setFont(font1);
		degree_Hard.setBackground(new Color(255, 239, 213));
		degree_Hard.setForeground(color1);
		degree_Hard.setBorder(empty);
		degree_Hard.setBorder(new LineBorder(Color.decode("#E88D67"), 5));
		degree_Hard.setFocusPainted(false);

		degreePanel = new JPanel();
		degreePanel.add(degree_Easy);
		degreePanel.add(degree_Medium);
		degreePanel.add(degree_Hard);
		degreePanel.setBorder(blDegree);
		degreePanel.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
	}

	public void creaCountComp() {
		count = new JComboBox<Integer>();
		count.addItem(5);
		count.addItem(10);
		count.addItem(15);
		count.addItem(20);
		count.addItem(25);
		count.addItem(30);
		count.addItem(35);
		count.addItem(40);
		count.addItem(45);
		count.addItem(50);
		count.setBackground(Color.decode("#F8EFD4"));

		Font font = new Font("微軟正黑體", Font.BOLD, 16);
		Color color = new Color(139, 69, 19);
		Border blCount = BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder((Color.decode("#E88D67")), Color.YELLOW), "題數", TitledBorder.CENTER,
				TitledBorder.DEFAULT_POSITION, font, color);

		Font font1 = new Font("微軟正黑體", Font.BOLD, 22);
		count.setFont(font1);
		count.setBackground(new Color(255, 239, 213));
		Color color1 = new Color(139, 69, 19);
		count.setForeground(color1);
		Border empty = BorderFactory.createEmptyBorder();
		count.setBorder(empty);

		countPanel = new JPanel();
		countPanel.add(count);
		countPanel.setBorder(blCount);
		countPanel.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
	}

	public void createStartBtn(JPanel panel1, InstructionPanel instruction, QuestionPanel question) {

		try {
			String selectTitle = "SELECT * FROM `Society` WHERE ";
			String qSingle = "MCQ = 'FALSE'";
			String qMultiple = "MCQ = 'TRUE'";
			String qHard = "Degree <= '30'";
			String qMedium = "(Degree > '30' AND Degree <= '70')";
			String qEasy = "Degree > '70'";
			String qCom = "Groupnumber <> '0'";
			String nine = "Year = '109'";
			String eight = "Year = '108'";
			Statement stat = conn.createStatement();

			class ClickListener implements ActionListener {
				public void actionPerformed(ActionEvent e) {
					String query = "";
					String warn = "";
					String[] select = { String.valueOf(year_109.isSelected()), String.valueOf(year_108.isSelected()),
							String.valueOf(type_Single.isSelected()), String.valueOf(type_Multiple.isSelected()),
							String.valueOf(type_Combination.isSelected()), String.valueOf(degree_Hard.isSelected()),
							String.valueOf(degree_Medium.isSelected()), String.valueOf(degree_Easy.isSelected()) };
					if (select[0].equals("true") && select[1].equals("true")) {
						query = String.format("%s(%s OR %s)", selectTitle, nine, eight);
					} else {
						if (select[0].equals("true")) {
							query = String.format("%s%s", selectTitle, nine);
						} else if (select[1].equals("true")) {
							query = String.format("%s%s", selectTitle, eight);
						} else {
							warn += " 年度";
						}
					}

					if (select[2].equals("true") && select[3].equals("true") && select[4].equals("true")) {
						query += String.format(" AND (%s OR %s OR %s)", qSingle, qMultiple, qCom);
					} else if (select[2].equals("true") && select[3].equals("true")) {
						query += String.format(" AND (%s OR %s)", qSingle, qMultiple);
					} else if (select[3].equals("true") && select[4].equals("true")) {
						query += String.format(" AND (%s OR %s)", qMultiple, qCom);
					} else if (select[2].equals("true") && select[4].equals("true")) {
						query += String.format(" AND (%s OR %s)", qSingle, qCom);
					} else {
						if (select[2].equals("true")) {
							query += String.format(" AND %s", qSingle);
						} else if (select[3].equals("true")) {
							query += String.format(" AND %s", qMultiple);
						} else if (select[4].equals("true")) {
							query += String.format(" AND %s", qCom);
						} else {
							warn += " 題型";
						}
					}

					if (select[5].equals("true") && select[6].equals("true") && select[7].equals("true")) {
						query += String.format(" AND (%s OR %s OR %s)", qHard, qMedium, qEasy);
					} else if (select[5].equals("true") && select[6].equals("true")) {
						query += String.format(" AND (%s OR %s)", qHard, qMedium);
					} else if (select[6].equals("true") && select[7].equals("true")) {
						query += String.format(" AND (%s OR %s)", qMedium, qEasy);
					} else if (select[5].equals("true") && select[7].equals("true")) {
						query += String.format(" AND (%s OR %s)", qHard, qEasy);
					} else {
						if (select[5].equals("true")) {
							query += String.format(" AND %s", qHard);
						} else if (select[6].equals("true")) {
							query += String.format(" AND %s", qMedium);
						} else if (select[7].equals("true")) {
							query += String.format(" AND %s", qEasy);
						} else {
							warn += " 難易度";
						}
					}
					
					if (warn == "") {
						if (subject_Civ.isSelected()) {
							try {
								result = stat.executeQuery(query);
								showResultSet(result);
							} catch (SQLException ex) {
								System.out.println(ex.getMessage());
							}
						}
					} else {
						if (subject_Civ.isSelected()) {
							warn = "請選擇" + warn + "!";
							JOptionPane.showMessageDialog(null, warn, "Error", JOptionPane.ERROR_MESSAGE);
						} else {
							warn = "請選擇" + warn + " 科目!";
							JOptionPane.showMessageDialog(null, warn, "Error", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
				
				public int getCount() {
					int questionCount = Integer.parseInt(count.getSelectedItem().toString());
					return questionCount;
				}

				public void showResultSet(ResultSet result) {
					boolean success = false;
					try {
						Statement stat = conn.createStatement();
						int columnCount = result.getMetaData().getColumnCount();
						result.last();
						int rowCount = result.getRow();
						result.beforeFirst();
						
						if (!result.next()) {
							JOptionPane.showMessageDialog(null, "無符合所選範圍之題目，請重新選擇！", "Error",
									JOptionPane.ERROR_MESSAGE);
						} else {
							result.previous();
							ArrayList<ArrayList<Object>> questionList = new ArrayList<ArrayList<Object>>();

							while (result.next()) {
								ArrayList<Object> question = new ArrayList<Object>();
								for (int i = 1; i <= columnCount; i++) {
									Object q = result.getObject(i);
									question.add(q);
								}
								questionList.add(question);
							}

							ArrayList<Integer> numbers = new ArrayList<Integer>();
							for (int i = 0; i < rowCount; i++) {
								numbers.add(i);
							}
							Collections.shuffle(numbers);

							ArrayList<Integer> test = new ArrayList<Integer>();
							if (numbers.size() < getCount()) {
								JOptionPane.showMessageDialog(null, "所選題數大於所選範圍題目之題數，請重新選擇！", "Error",
										JOptionPane.ERROR_MESSAGE);
							} else {
								for (int i = 0; i < getCount(); i++) {
									test.add(numbers.get(i));
								}
								Collections.sort(test); // 讓題號輸入資料庫時是照順序的
								success = true;
							}

							String creaTable = "CREATE TABLE IF NOT EXISTS UserQuestion "
									+ "(UserID varchar(50),TestID int(5),Year int(5),Subject varchar(20),Number int(5),GroupNumber int(5),MCQ varchar(10),Question varchar(1000),Picture varchar(50),A varchar(500),B varchar(500),C varchar(500),D varchar(500),E varchar(500),Answer varchar(500),Degree int(5),AnswerKey varchar(3000),UserAnswer varchar(500),Mark varchar(10),Note varchar(3000));";
							stat.execute(creaTable);

							userID = LoginPanel.getUserID();// 改成loginPanel裡的getUserID(),userID要改成static
							ResultSet userIntheDBorNot = stat.executeQuery(
									String.format("SELECT * FROM UserQuestion WHERE UserID = \"%s\";", userID));

							ArrayList<Integer> testNum = new ArrayList<Integer>();

							if (userIntheDBorNot.next()) {
								while (userIntheDBorNot.next()) {
									testNum.add(userIntheDBorNot.getInt("TestID"));

									for (int item : testNum) {
										if (item > testIDNow) {
											testIDNow = item;
										}
									}
								}
							} else {
								testIDNow = 0;
								System.out.print(getUserID());
								System.out.print(getTestID());
							}

							for (int i = 0; i < test.size(); i++) {

								int year = (int) questionList.get(test.get(i)).get(0);
								String subject = (String) questionList.get(test.get(i)).get(1);
								int number = (int) questionList.get(test.get(i)).get(2);
								int groupNumber = (int) questionList.get(test.get(i)).get(3);
								String MCQ = (String) questionList.get(test.get(i)).get(4);
								String question = (String) questionList.get(test.get(i)).get(5);
								String pic = (String) questionList.get(test.get(i)).get(6);
								String A = (String) questionList.get(test.get(i)).get(7);
								String B = (String) questionList.get(test.get(i)).get(8);
								String C = (String) questionList.get(test.get(i)).get(9);
								String D = (String) questionList.get(test.get(i)).get(10);
								String E = (String) questionList.get(test.get(i)).get(11);
								String answer = (String) questionList.get(test.get(i)).get(12);
								int degree = (int) questionList.get(test.get(i)).get(13);
								String answerKey = (String) questionList.get(test.get(i)).get(14);

								String query = "INSERT INTO " + "UserQuestion "
										+ " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,null,null,null);";

								PreparedStatement createTableStat = conn.prepareStatement(query);

								createTableStat.setString(1, userID);
								createTableStat.setInt(2, getTestID());
								createTableStat.setInt(3, year);
								createTableStat.setString(4, subject);
								createTableStat.setInt(5, number);
								createTableStat.setInt(6, groupNumber);
								createTableStat.setString(7, MCQ);
								createTableStat.setString(8, question);
								createTableStat.setString(9, pic);
								createTableStat.setString(10, A);
								createTableStat.setString(11, B);
								createTableStat.setString(12, C);
								createTableStat.setString(13, D);
								createTableStat.setString(14, E);
								createTableStat.setString(15, answer);
								createTableStat.setInt(16, degree);
								createTableStat.setString(17, answerKey);

								createTableStat.executeUpdate();
							}
						}
						if (success == true) {
							CardLayout card = (CardLayout) (panel1.getLayout());
							card.show(panel1, "instructionPanel");
							instruction.updateLabel(question);
						}

					} catch (SQLException ex) {
						System.out.println(ex.getMessage());
					} finally {
						year_109.setSelected(false);
						year_108.setSelected(false);
						type_Single.setSelected(false);
						type_Multiple.setSelected(false);
						type_Combination.setSelected(false);
						degree_Hard.setSelected(false);
						degree_Medium.setSelected(false);
						degree_Easy.setSelected(false);
						modeGroup.clearSelection();
						testGroup.clearSelection();
						subjectGroup.clearSelection();
					}
				}
			}

			ActionListener btn = new ClickListener();
			startBtn.addActionListener(btn);

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void addBackListener(JPanel panel) {
		class ClickListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				year_109.setSelected(false);
				year_108.setSelected(false);
				type_Single.setSelected(false);
				type_Multiple.setSelected(false);
				type_Combination.setSelected(false);
				degree_Hard.setSelected(false);
				degree_Medium.setSelected(false);
				degree_Easy.setSelected(false);
				modeGroup.clearSelection();
				testGroup.clearSelection();
				subjectGroup.clearSelection();
				
				CardLayout card = (CardLayout) (panel.getLayout());
				card.show(panel, "homePanel");
			}
		}
		ClickListener listener = new ClickListener();
		backButton.addActionListener(listener);
	}

	public static int getTestID() {
		return testIDNow + 1;
	}

	public String getUserID() {
		return userID;
	}

	public void creaTotalPanel() {
		modePanel.setBackground(Color.decode("#F8EFD4"));
		testPanel.setBackground(Color.decode("#F8EFD4"));
		yearPanel.setBackground(Color.decode("#F8EFD4"));
		subjectPanel.setBackground(Color.decode("#F8EFD4"));
		typePanel.setBackground(Color.decode("#F8EFD4"));
		degreePanel.setBackground(Color.decode("#F8EFD4"));
		countPanel.setBackground(Color.decode("#F8EFD4"));

		totalPanel = new JPanel(new GridLayout(7, 1));
		totalPanel.add(modePanel);
		totalPanel.add(testPanel);
		totalPanel.add(yearPanel);
		totalPanel.add(subjectPanel);
		totalPanel.add(typePanel);
		totalPanel.add(degreePanel);
		totalPanel.add(countPanel);
		JScrollPane scrollPane = new JScrollPane(totalPanel);
		scrollPane.setBackground(new Color(255, 239, 213));
		scrollPane.setPreferredSize(new Dimension(500, 500));
		scrollPane.setBorder(new LineBorder(Color.orange));

		setBackground(Color.decode("#F8EFD4"));
		// setLayout(new BorderLayout());
		// add(title, BorderLayout.NORTH);
		// add(scrollPane, BorderLayout.CENTER);
		// add(startBtn, BorderLayout.SOUTH);

		JPanel down_toolPanel = new JPanel();
		down_toolPanel.setBackground(Color.decode("#F8EFD4"));
		down_toolPanel.setLayout(new BoxLayout(down_toolPanel, BoxLayout.X_AXIS));
		down_toolPanel.add(Box.createRigidArea(new Dimension(70, 0)));
		down_toolPanel.add(backButton);
		down_toolPanel.add(Box.createHorizontalGlue());
		down_toolPanel.add(startBtn);
		down_toolPanel.add(Box.createRigidArea(new Dimension(70, 0)));

		setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.insets = new Insets(10, 0, 0, 0);
		gbc.anchor = GridBagConstraints.PAGE_START;
		add(title, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		// gbc.insets = new Insets(0, 0, 120, 0);
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		add(scrollPane, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.insets = new Insets(0, 0, 20, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.PAGE_END;
		add(down_toolPanel, gbc);
	}

}
