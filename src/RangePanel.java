import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.Border;

public class RangePanel extends JPanel {
	private static final int FRAME_WIDTH = 400;
	private static final int FRAME_HEIGHT = 400;
	private static final int PANEL_WIDTH = 350;
	private static final int PANEL_HEIGHT = 70;

	private JLabel title;
	private JPanel modePanel, testPanel, yearPanel, subjectPanel, typePanel, degreePanel, countPanel, totalPanel;
	private JRadioButton mode_Review, mode_New;
	private JRadioButton test_GSAT, test_AST;
	private JCheckBox year_109, year_108;
	private JRadioButton subject_Chi, subject_Eng, subject_MathA, subject_MathB, subject_Geo, subject_His, subject_Civ,
			subject_Bio, subject_Che, subject_Phy;
	private JCheckBox type_Single, type_Multiple, type_Cloze;
	private JRadioButton degree_Easy, degree_Medium, degree_Hard;
	private JComboBox count;
	private JButton startBtn;

	/*
	 * CardLayout card = new CardLayout(); JPanel panel = new JPanel(card);
	 */

	public RangePanel() {
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		// setTitle("Range");
		startBtn = new JButton("出題");

		/*
		 * InstructionPage i = new InstructionPage(); panel.add(i, "Instruction");
		 */

		creaTitle();
		creaModeComp();
		creaTestComp();
		creaYearComp();
		creaSubjectComp();
		creaTypeComp();
		creaDegreeComp();
		creaCountComp();
		// creaStartBtn();
		creaTotalPanel();
	}

	public void creaTitle() {
		title = new JLabel("考試範圍");
	}

	public void creaModeComp() {
		mode_Review = new JRadioButton("複習");
		mode_New = new JRadioButton("全新題目");

		ButtonGroup modeGroup = new ButtonGroup();
		modeGroup.add(mode_Review);
		modeGroup.add(mode_New);

		Border blMode = BorderFactory.createTitledBorder("模式");

		modePanel = new JPanel();
		modePanel.add(mode_Review);
		modePanel.add(mode_New);
		modePanel.setBorder(blMode);
		modePanel.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
	}

	public void creaTestComp() {
		test_GSAT = new JRadioButton("學測");
		test_AST = new JRadioButton("指考");

		ButtonGroup testGroup = new ButtonGroup();
		testGroup.add(test_GSAT);
		testGroup.add(test_AST);

		Border blTest = BorderFactory.createTitledBorder("考試項目");

		testPanel = new JPanel();
		testPanel.add(test_GSAT);
		testPanel.add(test_AST);
		testPanel.setBorder(blTest);
		testPanel.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
	}

	public void creaYearComp() {
		year_109 = new JCheckBox("109");
		year_108 = new JCheckBox("108");

		Border blYear = BorderFactory.createTitledBorder("年度");

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

		ButtonGroup subjectGroup = new ButtonGroup();
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

		Border blSubject = BorderFactory.createTitledBorder("科目");

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
		subjectPanel.setBorder(blSubject);
		subjectPanel.setPreferredSize(new Dimension(PANEL_WIDTH, 100));
	}

	public void creaTypeComp() {
		type_Single = new JCheckBox("單選題");
		type_Multiple = new JCheckBox("複選題");
		type_Cloze = new JCheckBox("選填題");

		Border blType = BorderFactory.createTitledBorder("題型");

		typePanel = new JPanel();
		typePanel.add(type_Single);
		typePanel.add(type_Multiple);
		typePanel.add(type_Cloze);
		typePanel.setBorder(blType);
		typePanel.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
	}

	public void creaDegreeComp() {
		degree_Easy = new JRadioButton("易");
		degree_Medium = new JRadioButton("中");
		degree_Hard = new JRadioButton("難");

		ButtonGroup degreeGroup = new ButtonGroup();
		degreeGroup.add(degree_Easy);
		degreeGroup.add(degree_Medium);
		degreeGroup.add(degree_Hard);

		Border blDegree = BorderFactory.createTitledBorder("難易度");

		degreePanel = new JPanel();
		degreePanel.add(degree_Easy);
		degreePanel.add(degree_Medium);
		degreePanel.add(degree_Hard);
		degreePanel.setBorder(blDegree);
		degreePanel.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
	}

	public void creaCountComp() {
		count = new JComboBox();
		count.addItem("---請選擇---");
		count.addItem(10);
		count.addItem(20);
		count.addItem(30);
		count.addItem(40);
		count.addItem(50);

		Border blCount = BorderFactory.createTitledBorder("題數");

		countPanel = new JPanel();
		countPanel.add(count);
		countPanel.setBorder(blCount);
		countPanel.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
	}

	public void creaStartBtn(JPanel panel1) {

		class ClickListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				CardLayout card = (CardLayout) (panel1.getLayout());

				card.show(panel1, "Instruction");

			}
		}
		ActionListener btn = new ClickListener();
		startBtn.addActionListener(btn);
	}

	public void creaTotalPanel() {
		setLayout(new BorderLayout());
		totalPanel = new JPanel(new GridLayout(7, 1));

		add(title, BorderLayout.NORTH);
		totalPanel.add(modePanel);
		totalPanel.add(testPanel);
		totalPanel.add(yearPanel);
		totalPanel.add(subjectPanel);
		totalPanel.add(typePanel);
		totalPanel.add(degreePanel);
		totalPanel.add(countPanel);

		JScrollPane scrollPane = new JScrollPane(totalPanel);

		add(scrollPane, BorderLayout.CENTER);
		add(startBtn, BorderLayout.SOUTH);

	}

}
