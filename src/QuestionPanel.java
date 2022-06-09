import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import style.*;

public class QuestionPanel extends JPanel {
	private JPanel up_toolPanel, down_toolPanel, questionPanel, answerPanel, pausePanel;
	private JScrollPane scrollPane;
	private JButton moreButton, playButton, pauseButton, finishButton, backButton, nextButton, markButton;
	private JLabel timeLabel;
	private JTextPane questionText;
	private JLabel[] answerLabel;
	private JButton aButton, bButton, cButton, dButton, eButton;
	private JButton[] xButtons;
	private boolean[] click;
	private boolean[][] clickX;
	private ArrayList<JButton> answerButtons;
	private ArrayList<String> ifMarked, chosenAnsNum; // 使用者當題答案
	private ArrayList<Integer> numbers; // 題號們
	private ArrayList<Integer> years;
	private int option, number; // number = 題號們的第幾位數字
	private static String subject;
	private String year;
	private Connection conn;
	private QTool qTool;
	private Answer answer;
	private ImageIcon beforeMark, afterMark, beforeX, afterX, backIcon, nextIcon;

	private RoundButton[] numButtons;
	private JPanel buttonPanel;

	public QuestionPanel() {
		try {
			String server = "jdbc:mysql://140.119.19.73:9306/";
			String database = "MG05";
			String url = server + database
					+ "?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&failOverReadOnly=false";
			String username = "MG05";
			String password = "9mMuzQ";
			conn = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			System.out.println("<QuestionPanel> constructor: " + e.getMessage());
		}
	}

	public void initialize() {
		chosenAnsNum = new ArrayList<String>();
		years = new ArrayList<Integer>();
		qTool = new QTool();
		answer = new Answer();
		ifMarked = answer.getIfMarked();
		numbers = answer.getNumbers();
		number = 0;
		System.out.println(numbers);

		pausePanel = new JPanel(new BorderLayout());
		pausePanel.setBackground(Color.decode("#F8EFD4"));
		ImageIcon pauseIcon = new ImageIcon(
				new ImageIcon("images/rest.png").getImage().getScaledInstance(500, 500, Image.SCALE_DEFAULT));
		JLabel pauseLabel = new JLabel(pauseIcon);
		pausePanel.add(pauseLabel, BorderLayout.CENTER);

		try {
			Statement stat = conn.createStatement();
			String query = "SELECT Subject, Year FROM UserQuestion WHERE UserID = '" + LoginPanel.getUserID()
					+ "' AND TestID = '" + RangePanel.getTestID() + "'";
			ResultSet result = stat.executeQuery(query);
			if (result.next()) {
				subject = result.getString(1);
				year = String.valueOf(result.getInt(2));
			}
			while (result.next()) {
				if (!String.valueOf(result.getInt(2)).equals(year)) {
					year += ", " + String.valueOf(result.getInt(2));
					break;
				}
			}
			result.beforeFirst();

			while (result.next()) {
				years.add(result.getInt(2));
			}
			result.close();
		} catch (Exception e) {
			System.out.println("<Question Panel> getSubject: " + e.getMessage());
		}

		createBackButton();
		createNextButton();
		createMarkButton();
		createQuestionComp();
		createXButton();
		createAnswerButton();
		createAnswerPanel();
		createToolPanel();
		createQNumButtons();

		repaintQuestionPanel();
	}

	public QTool getQTool() {
		return this.qTool;
	}

	public Answer getAnswer() {
		return this.answer;
	}

	public JLabel getTimeLabel() {
		return this.timeLabel;
	}

	public JPanel getAnswerPanel() {
		return this.answerPanel;
	}

	public JButton[] getNumButtons() {
		return this.numButtons;
	}

	public boolean[][] getClickX() {
		return this.clickX;
	}

	public static String getSubject() {
		return subject;
	}

	public String getYear() {
		return this.year;
	}

	public JButton putMoreButton() {
		qTool.setClickMore(0);
		return this.moreButton;
	}

	public JLabel putTimeLabel() {
		qTool.setTimerun(true);
		qTool.setTimeLabelText(null);
		return this.timeLabel;
	}

	public void updateNumber(int num) {
		this.number = num;
	}

	public void updateUserAnswers() {
		String a = "";
		for (int i = 0; i < chosenAnsNum.size(); i++) {
			switch (chosenAnsNum.get(i)) {
			case "0":
				a += "A";
				break;
			case "1":
				a += "B";
				break;
			case "2":
				a += "C";
				break;
			case "3":
				a += "D";
				break;
			case "4":
				a += "E";
				break;
			}
		}

		if (a != "" && a != answer.getUserAnswers().get(number)) {
			answer.setUserAnswers(number, a);
			numButtons[number].setBorderColor(Color.decode("#5E8CD1"), Color.decode("#CFDEF2")); // blue
			numButtons[number].setToolTipText("已作答");
		} else if (a == "") {
			answer.setUserAnswers(number, null);
			numButtons[number].setBorderColor(Color.decode("#E88D67"), Color.decode("#F5DEB3")); // orange
			numButtons[number].setToolTipText("未作答");
		}
		chosenAnsNum.clear();
	}

	public void repaintQuestionPanel() {
		if (number == 0) {
			backButton.setIcon(null);
			backButton.setEnabled(false);
			nextButton.setIcon(nextIcon);
			nextButton.setEnabled(true);
		} else if (number == (numbers.size() - 1)) {
			backButton.setIcon(backIcon);
			backButton.setEnabled(true);
			nextButton.setIcon(null);
			nextButton.setEnabled(false);
		} else {
			backButton.setIcon(backIcon);
			backButton.setEnabled(true);
			nextButton.setIcon(nextIcon);
			nextButton.setEnabled(true);
		}
		questionText.setText("");
		removeAll();
		updateMarkButton();
		updateQuestionText();
		updateXButton();
		updateAnswerComp();
		setQuestionLayout();
		validate();
		repaint();
	}

	public void createBackButton() {
		backIcon = new ImageIcon(
				new ImageIcon("images/back.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
		backButton = new JButton(); // "<-"
		backButton.setEnabled(false);
		backButton.setBorder(null);
		backButton.setContentAreaFilled(false);
		backButton.setPreferredSize(new Dimension(30, 30));
		backButton.setToolTipText("上一題");

		class ClickListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				updateUserAnswers();
				number--;
				repaintQuestionPanel();
			}
		}
		ClickListener listener = new ClickListener();
		backButton.addActionListener(listener);
	}

	public void createNextButton() {
		nextIcon = new ImageIcon(
				new ImageIcon("images/next.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
		nextButton = new JButton(); // "->"
		nextButton.setIcon(nextIcon);
		nextButton.setBorder(null);
		nextButton.setOpaque(false);
		nextButton.setContentAreaFilled(false);
		nextButton.setPreferredSize(new Dimension(30, 30));
		nextButton.setToolTipText("下一題");

		class ClickListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				updateUserAnswers();
				number++;
				repaintQuestionPanel();

			}
		}
		ClickListener listener = new ClickListener();
		nextButton.addActionListener(listener);
	}

	public void createMarkButton() {
		beforeMark = new ImageIcon(
				new ImageIcon("images/beforeMark.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
		afterMark = new ImageIcon(
				new ImageIcon("images/afterMark.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
		ImageIcon qMark = new ImageIcon(
				new ImageIcon("images/afterMark.png").getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT));
		markButton = new JButton(); // "?"
		markButton.setBorder(null);
		markButton.setOpaque(false);
		markButton.setContentAreaFilled(false);
		markButton.setIcon(beforeMark);
		markButton.setToolTipText("標記題目");
		
		class ClickListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if (ifMarked.get(number) == "FALSE") {
					markButton.setIcon(afterMark);
					numButtons[number].setText("");
					numButtons[number].setIcon(qMark);
					ifMarked.set(number, "TRUE");
				} else {
					markButton.setIcon(beforeMark);
					numButtons[number].setText(String.format("%d", numbers.get(number)));
					numButtons[number].setIcon(null);
					ifMarked.set(number, "FALSE");
				}
			}
		}
		ClickListener listener = new ClickListener();
		markButton.addActionListener(listener);
	}

	public void updateMarkButton() {
		if (ifMarked.get(number) == "FALSE") {
			markButton.setIcon(beforeMark);
		} else {
			markButton.setIcon(afterMark);
		}

	}

	public void createQuestionComp() {
		questionText = new JTextPane();
		questionText.setBackground(Color.decode("#F8EFD4"));
		questionText.setEditable(false);

		scrollPane = new JScrollPane(questionText);
		scrollPane.getViewport().getView().setBackground(Color.decode("#F8EFD4"));
		scrollPane.setBorder(new LineBorder(Color.decode("#524632")));
		scrollPane.setPreferredSize(new Dimension(200, 150));
		scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
		markButton.setAlignmentY(Component.TOP_ALIGNMENT);
		questionPanel = new JPanel();
		questionPanel.setBackground(Color.decode("#F8EFD4"));
		questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.X_AXIS));
		questionPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		questionPanel.add(scrollPane);
		questionPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		questionPanel.add(markButton);
		questionPanel.add(Box.createRigidArea(new Dimension(10, 0)));
	}

	public void updateQuestionText() {
		String pic = "";
		String que = "";
		try {
			Statement stat = conn.createStatement();

			String Question = "SELECT Question, Picture FROM UserQuestion WHERE UserID = '" + LoginPanel.getUserID()
					+ "' AND TestID = '" + RangePanel.getTestID() + "' AND Number = " + numbers.get(number)
					+ " AND Year = " + years.get(number);
			stat.execute(Question);
			ResultSet result = stat.getResultSet();
			result.next();

			que = String.format("%d - Q%d. %s", years.get(number), numbers.get(number), result.getString(1));
			pic = result.getString(2);
		} catch (Exception e) {
			System.out.printf("<QuestionPanel> create textPane: %s\n", e.getMessage());
		}

		String[] initString = { que, "\n", " " };
		String[] initStyles = { "regular", "regular", "icon", };

		StyledDocument doc = questionText.getStyledDocument();
		Style def = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
		Style regular = doc.addStyle("regular", def);
		StyleConstants.setFontFamily(def, "微軟正黑體");
		StyleConstants.setFontSize(def, 16);
		Style s = doc.addStyle("icon", regular);
		StyleConstants.setAlignment(s, StyleConstants.ALIGN_RIGHT);
		if (pic != null) {
			ImageIcon icon = new ImageIcon("qpic/" + pic);
			icon = new ImageIcon(icon.getImage().getScaledInstance((icon.getIconWidth() / 5 * 2),
					(icon.getIconHeight() / 5 * 2), Image.SCALE_DEFAULT));
			StyleConstants.setIcon(s, icon);
		}
		try {
			for (int i = 0; i < initString.length; i++) {
				doc.insertString(doc.getLength(), initString[i], doc.getStyle(initStyles[i]));
			}
		} catch (BadLocationException e) {
			System.err.printf("<QuestionPanel> create textPane: %s\n", e.getMessage());
		}
	}

	public void createXButton() {
		beforeX = new ImageIcon(
				new ImageIcon("images/beforeX.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
		afterX = new ImageIcon(
				new ImageIcon("images/afterX.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
		xButtons = new JButton[5];
		clickX = new boolean[numbers.size()][5];
		for (int i = 0; i < numbers.size(); i++) {
			for (int j = 0; j < 5; j++) {
				clickX[i][j] = false;
			}
		}

		class X_ClickListener implements ActionListener {

			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < xButtons.length; i++) {
					if (xButtons[i].getModel().isArmed()) {
						if (clickX[number][i] == false) {
							xButtons[i].setIcon(afterX);
							answerButtons.get(i).setBorder(new BubbleBorder(Color.decode("#F8EFD4"), 1, 15, 0));
							chosenAnsNum.remove(String.valueOf(i));
							clickX[number][i] = true;
						} else {
							xButtons[i].setIcon(beforeX);
							clickX[numbers.size()][i] = false;
						}
					}
				}
				// System.out.println(chosenAnsNum);
			}
		}
		X_ClickListener xListener = new X_ClickListener();
		for (int i = 0; i < 5; i++) {
			xButtons[i] = new JButton(); // "X"
			xButtons[i].setBorder(null);
			xButtons[i].setContentAreaFilled(false);
			xButtons[i].setIcon(beforeX);
			xButtons[i].addActionListener(xListener);
			xButtons[i].setToolTipText("劃除答案選項");
		}
	}

	public void updateXButton() {
		for (int i = 0; i < option; i++) {
			if (clickX[number][i] == false) {
				xButtons[i].setIcon(beforeX);
			} else {
				xButtons[i].setIcon(afterX);
			}
		}
	}

	public void createAnswerButton() {
		option = 4;
		click = new boolean[5];

		aButton = new JButton();
		aButton.setToolTipText("A選項");
		bButton = new JButton();
		bButton.setToolTipText("B選項");
		cButton = new JButton();
		cButton.setToolTipText("C選項");
		dButton = new JButton();
		dButton.setToolTipText("D選項");
		eButton = new JButton();
		eButton.setToolTipText("E選項");

		answerButtons = new ArrayList<JButton>();
		answerButtons.add(aButton);
		answerButtons.add(bButton);
		answerButtons.add(cButton);
		answerButtons.add(dButton);
		answerButtons.add(eButton);

		class Ans_ClickListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < option; i++) {
					if (option == 4) {
						if (answerButtons.get(i).getModel().isArmed()) {
							if (click[i] == false) {
								answerButtons.get(i).setBorder(new BubbleBorder(Color.decode("#5E8CD1"), 1, 15, 0));
								xButtons[i].setIcon(beforeX);
								chosenAnsNum.clear();
								chosenAnsNum.add(String.valueOf(i));
								click[i] = true;
							} else {
								answerButtons.get(i).setBorder(new BubbleBorder(Color.decode("#F8EFD4"), 1, 15, 0));
								chosenAnsNum.remove(String.valueOf(i));
								click[i] = false;
							}

						} else {
							answerButtons.get(i).setBorder(new BubbleBorder(Color.decode("#F8EFD4"), 1, 15, 0));
						}
					} else {
						if (answerButtons.get(i).getModel().isArmed()) {
							if (click[i] == false) {
								answerButtons.get(i).setBorder(new BubbleBorder(Color.decode("#5E8CD1"), 1, 15, 0));
								xButtons[i].setIcon(beforeX);
								chosenAnsNum.add(String.valueOf(i));
								Collections.sort(chosenAnsNum);
								click[i] = true;
							} else {
								answerButtons.get(i).setBorder(new BubbleBorder(Color.decode("#F8EFD4"), 1, 15, 0));
								chosenAnsNum.remove(String.valueOf(i));
								click[i] = false;
							}

						}
					}
				}
				// System.out.println(chosenAnsNum);
			}
		}
		Ans_ClickListener ansListener = new Ans_ClickListener();
		for (JButton b : answerButtons) {
			b.setHorizontalAlignment(SwingConstants.LEFT);
			b.setBorder(new BubbleBorder(Color.decode("#F8EFD4"), 1, 15, 0));
			b.setContentAreaFilled(false);
			b.setPreferredSize(new Dimension(900, 70));

			b.setFont(new Font("微軟正黑體", Font.PLAIN, 16));
			b.addActionListener(ansListener);
		}

		answerLabel = new JLabel[5];
		for (int i = 0; i < 5; i++) {
			answerLabel[i] = new JLabel();
			answerLabel[i].setBorder(null);
			answerLabel[i].setFont(new Font("微軟正黑體", Font.PLAIN, 16));
		}
		answerLabel[0].setText("A.");
		answerLabel[1].setText("B.");
		answerLabel[2].setText("C.");
		answerLabel[3].setText("D.");
		answerLabel[4].setText("E.");
	}

	public void updateAnswerComp() {
		try {
			Statement stat = conn.createStatement();

			String queryA = "SELECT A FROM UserQuestion WHERE UserID = '" + LoginPanel.getUserID() + "' AND TestID = '"
					+ RangePanel.getTestID() + "' AND Number = " + numbers.get(number) + " AND Year = "
					+ years.get(number);
			ResultSet resultA = stat.executeQuery(queryA);
			resultA.next();
			aButton.setText(resultA.getString(1));

			resultA.close();

			String queryB = "SELECT B FROM UserQuestion WHERE UserID = '" + LoginPanel.getUserID() + "' AND TestID = '"
					+ RangePanel.getTestID() + "' AND Number = " + numbers.get(number) + " AND Year = "
					+ years.get(number);
			ResultSet resultB = stat.executeQuery(queryB);
			resultB.next();
			bButton.setText(resultB.getString(1));
			resultB.close();

			String queryC = "SELECT C FROM UserQuestion WHERE UserID = '" + LoginPanel.getUserID() + "' AND TestID = '"
					+ RangePanel.getTestID() + "' AND Number = " + numbers.get(number) + " AND Year = "
					+ years.get(number);
			ResultSet resultC = stat.executeQuery(queryC);
			resultC.next();
			cButton.setText(resultC.getString(1));
			resultC.close();

			String queryD = "SELECT D FROM UserQuestion WHERE UserID = '" + LoginPanel.getUserID() + "' AND TestID = '"
					+ RangePanel.getTestID() + "' AND Number = " + numbers.get(number) + " AND Year = "
					+ years.get(number);
			ResultSet resultD = stat.executeQuery(queryD);
			resultD.next();
			dButton.setText(resultD.getString(1));
			resultD.close();

			String queryE = "SELECT E FROM UserQuestion WHERE UserID = '" + LoginPanel.getUserID() + "' AND TestID = '"
					+ RangePanel.getTestID() + "' AND Number = " + numbers.get(number) + " AND Year = "
					+ years.get(number);
			ResultSet resultE = stat.executeQuery(queryE);
			resultE.next();
			if (resultE.getString(1) != null) {
				option = 5;
				eButton.setText(resultE.getString(1));
				eButton.setVisible(true);
				answerLabel[4].setVisible(true);
				xButtons[4].setVisible(true);
			} else {
				option = 4;
				eButton.setText("");
				eButton.setVisible(false);
				answerLabel[4].setVisible(false);
				xButtons[4].setVisible(false);
			}
			resultE.close();

			for (int i = 0; i < 5; i++) {
				click[i] = false;
				answerButtons.get(i).setBorder(new BubbleBorder(Color.decode("#F8EFD4"), 1, 15, 0));
			}
			String a = answer.getUserAnswers().get(number);
			if (a != null) {
				for (int i = 0; i < a.length(); i++) {
					switch (a.charAt(i)) {
					case 'A':
						aButton.setBorder(new BubbleBorder(Color.decode("#5E8CD1"), 1, 15, 0));
						click[0] = true;
						chosenAnsNum.add("0");
						break;
					case 'B':
						bButton.setBorder(new BubbleBorder(Color.decode("#5E8CD1"), 1, 15, 0));
						click[1] = true;
						chosenAnsNum.add("1");
						break;
					case 'C':
						cButton.setBorder(new BubbleBorder(Color.decode("#5E8CD1"), 1, 15, 0));
						click[2] = true;
						chosenAnsNum.add("2");
						break;
					case 'D':
						dButton.setBorder(new BubbleBorder(Color.decode("#5E8CD1"), 1, 15, 0));
						click[3] = true;
						chosenAnsNum.add("3");
						break;
					case 'E':
						eButton.setBorder(new BubbleBorder(Color.decode("#5E8CD1"), 1, 15, 0));
						click[4] = true;
						chosenAnsNum.add("4");
						break;
					}
				}
			}
		} catch (Exception e) {
			System.out.printf("<Answer> update answer comp %s\n", e.getMessage());
		}
	}

	public void createAnswerPanel() {
		answerPanel = new JPanel(new GridBagLayout());
		answerPanel.setBackground(Color.decode("#F8EFD4"));
		GridBagConstraints gbc;
		for (int i = 0; i < 5; i++) {
			gbc = new GridBagConstraints();
			gbc.gridx = 0;
			gbc.gridy = i;
			gbc.weightx = 1.0;
			gbc.weighty = 1.0;
			answerPanel.add(answerLabel[i], gbc);

			gbc = new GridBagConstraints();
			gbc.gridx = 1;
			gbc.gridy = i;
			gbc.weightx = 1.0;
			gbc.weighty = 1.0;
			gbc.gridx = 2;
			gbc.fill = GridBagConstraints.HORIZONTAL;
			answerPanel.add(answerButtons.get(i), gbc);

			gbc = new GridBagConstraints();
			gbc.gridx = 3;
			gbc.gridy = i;
			gbc.weightx = 1.0;
			gbc.weighty = 1.0;
			answerPanel.add(xButtons[i], gbc);
		}
	}

	public void createToolPanel() {
		moreButton = qTool.getMoreButton();
		playButton = qTool.getPlayButton();
		pauseButton = qTool.getPauseButton();
		timeLabel = qTool.getTimeLabel();
		finishButton = qTool.getFinishButton();

		up_toolPanel = new JPanel();
		up_toolPanel.setBackground(Color.decode("#F8EFD4"));
		down_toolPanel = new JPanel();
		down_toolPanel.setBackground(Color.decode("#F8EFD4"));

		up_toolPanel.setLayout(new BoxLayout(up_toolPanel, BoxLayout.X_AXIS));
		up_toolPanel.add(Box.createRigidArea(new Dimension(20, 0)));
		up_toolPanel.add(moreButton);
		up_toolPanel.add(Box.createGlue());
		up_toolPanel.add(playButton);
		up_toolPanel.add(Box.createRigidArea(new Dimension(15, 0)));
		up_toolPanel.add(pauseButton);
		up_toolPanel.add(Box.createRigidArea(new Dimension(30, 0)));
		up_toolPanel.add(timeLabel);
		up_toolPanel.add(Box.createRigidArea(new Dimension(30, 0)));

		down_toolPanel.setLayout(new BoxLayout(down_toolPanel, BoxLayout.X_AXIS));
		down_toolPanel.add(Box.createRigidArea(new Dimension(30, 0)));
		down_toolPanel.add(backButton);
		down_toolPanel.add(Box.createGlue());
		down_toolPanel.add(finishButton);
		down_toolPanel.add(Box.createGlue());
		down_toolPanel.add(nextButton);
		down_toolPanel.add(Box.createRigidArea(new Dimension(30, 0)));
	}

	public void setQuestionLayout() {
		setBackground(Color.decode("#F8EFD4"));
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		add(Box.createRigidArea(new Dimension(0, 10)));
		add(up_toolPanel);
		add(Box.createRigidArea(new Dimension(0, 20)));
		add(questionPanel);
		add(Box.createRigidArea(new Dimension(0, 10)));
		add(answerPanel);
		add(Box.createRigidArea(new Dimension(0, 20)));
		add(down_toolPanel);
		add(Box.createRigidArea(new Dimension(0, 10)));
	}

	public void setPauseLayout() {
		removeAll();
		setBackground(Color.decode("#F8EFD4"));
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		add(Box.createRigidArea(new Dimension(0, 15)));
		add(up_toolPanel);
		add(pausePanel);
		add(Box.createGlue());
		validate();
		repaint();
	}

	public void createQNumButtons() {
		numButtons = new RoundButton[numbers.size()];
		int row;
		if ((numbers.size() % 10) == 0) {
			row = numbers.size() / 10;
		} else {
			row = numbers.size() / 10 + 1;
		}
		GridLayout gridLayout = new GridLayout(row, 10);
		gridLayout.setHgap(30);
		gridLayout.setVgap(30);
		buttonPanel = new JPanel(gridLayout);
		buttonPanel.setPreferredSize(new Dimension(1000, 500));
		buttonPanel.setBackground(Color.decode("#F8EFD4"));

		for (int i = 0; i < numbers.size(); i++) {
			numButtons[i] = new RoundButton(String.format("%d", numbers.get(i)));
			numButtons[i].setPreferredSize(new Dimension(20, 20));
			numButtons[i].setFont(new Font("微軟正黑體", Font.PLAIN, 14));
			numButtons[i].setToolTipText("未作答");
			buttonPanel.add(numButtons[i]);
		}
	}

	public void repaintNumBtnPanel() {
		removeAll();
		setNumBtnLayout();
		validate();
		repaint();
	}

	public void setNumBtnLayout() {
		setLayout(new GridBagLayout());
		setBackground(Color.decode("#F8EFD4"));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.insets = new Insets(10, 0, 20, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.NORTH;
		add(up_toolPanel, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.insets = new Insets(0, 50, 150, 50);
		gbc.anchor = GridBagConstraints.CENTER;
		add(buttonPanel, gbc);
	}

	public void deleteTest() {
		try {
			Statement stat = conn.createStatement();
			String query = "DELETE FROM UserQuestion WHERE UserID = '" + LoginPanel.getUserID() + "' AND TestID = '"
					+ RangePanel.getTestID() + "'";
			String query2 = "DELETE FROM AllTests WHERE UserID = '" + LoginPanel.getUserID() + "' AND TestID = '"
					+ RangePanel.getTestID() + "'";
			stat.executeUpdate(query);
			stat.executeUpdate(query2);
		} catch (SQLException e) {
			System.out.println("<QuestionPanel> deleteTest: " + e.getMessage());
		}

	}
}
