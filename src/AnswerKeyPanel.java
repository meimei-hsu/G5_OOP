import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import style.*;

public class AnswerKeyPanel extends JPanel {
	private JPanel questionPanel, answerPanel, notePanel, down_toolPanel;
	private JButton moreButton, markButton, backButton, nextButton, deleteNoteBtn;
	private JTextPane questionText;
	private JScrollPane questionScroll, noteScroll;
	private JLabel[] answerLabel;
	private ArrayList<JButton> answerButtons;
	private JButton aButton, bButton, cButton, dButton, eButton;
	private JTextArea keyArea, noteArea;
	private ImageIcon afterMark, beforeMark, backIcon, nextIcon;
	private int option, number, year, testID;
	private String title, subject;
	private Connection conn;
	private QTool qTool;

	public AnswerKeyPanel() {
		try {
			String server = "jdbc:mysql://140.119.19.73:9306/";
			String database = "MG05";
			String url = server + database
					+ "?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&failOverReadOnly=false";
			String username = "MG05";
			String password = "9mMuzQ";
			conn = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			System.out.printf("<AnswerKeyPanel> constructor: %s\n", e.getMessage());
		}
		initialize();
	}

	public void initialize() {
		qTool = new QTool();
		moreButton = qTool.getMoreButton();

		createMarkButton();
		createQuestionComp();
		createAnswerButton();
		createAnswerPanel();
		createNoteComp();
		createDeleteButton();
		createBackButton();
		createNextButton();
		createToolPanel();
		setLayout();
	}

	public void updateNumber(int num) {
		this.number = num;
	}

	public void updateYear(int year) {
		this.year = year;
	}

	public void updateTestID(int i) {
		this.testID = i;
	}

	public void updateSubject(String subject) {
		this.subject = subject;
	}

	public void updateTitle(String title) {
		this.title = title;
	}

	public QTool getQTool() {
		return this.qTool;
	}

	public void createMarkButton() {
		beforeMark = new ImageIcon(
				new ImageIcon("images/beforeMark.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
		afterMark = new ImageIcon(
				new ImageIcon("images/afterMark.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
		markButton = new JButton(); // "?"
		markButton.setIcon(beforeMark);
		markButton.setBorder(null);
		markButton.setOpaque(false);
		markButton.setContentAreaFilled(false);
	}

	public void updateMarkButton() {
		try {
			Statement stat = conn.createStatement();
			String query = "SELECT Mark FROM UserQuestion WHERE UserID = '" + LoginPanel.getUserID()
					+ "' AND TestID = '" + testID + "' AND Year = '" + year + "' AND Number = '" + number + "'";
			ResultSet result = stat.executeQuery(query);
			result.next();
			if (result.getString(1).equals("TRUE")) {
				markButton.setIcon(afterMark);
			} else {
				markButton.setIcon(beforeMark);
			}
		} catch (Exception e) {
			System.out.printf("<AnswerKeyPanel> update mark button %s\n", e.getMessage());
		}
	}

	public void createQuestionComp() {
		questionText = new JTextPane();
		questionText.setBackground(Color.decode("#F8EFD4"));
		questionText.setEditable(false);

		questionScroll = new JScrollPane(questionText);
		questionScroll.getViewport().getView().setBackground(Color.decode("#F8EFD4"));
		questionScroll.setBorder(new LineBorder(Color.decode("#524632")));
		questionScroll.setPreferredSize(new Dimension(200, 150));
		questionScroll.setAlignmentX(Component.CENTER_ALIGNMENT);
		markButton.setAlignmentY(Component.TOP_ALIGNMENT);

		questionPanel = new JPanel();
		questionPanel.setBackground(Color.decode("#F8EFD4"));
		questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.X_AXIS));
		questionPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		questionPanel.add(questionScroll);
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
					+ "' AND TestID = '" + testID + "' AND Year = '" + year + "' AND Number = " + number;
			stat.execute(Question);
			ResultSet result = stat.getResultSet();
			result.next();

			que = String.format("%d - Q%d. %s", year, number, result.getString(1));
			pic = result.getString(2);
		} catch (Exception e) {
			System.out.printf("<AnswerKey panel> create textPane: %s\n", e.getMessage());
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
			System.err.printf("<AnswerKey panel> create textPane: %s\n", e.getMessage());
		}
	}

	public void createAnswerButton() {
		option = 4;

		aButton = new JButton();
		bButton = new JButton();
		cButton = new JButton();
		dButton = new JButton();
		eButton = new JButton();

		answerButtons = new ArrayList<JButton>();
		answerButtons.add(aButton);
		answerButtons.add(bButton);
		answerButtons.add(cButton);
		answerButtons.add(dButton);
		answerButtons.add(eButton);
		for (JButton b : answerButtons) {
			b.setHorizontalAlignment(SwingConstants.LEFT);
			b.setBorder(new BubbleBorder(Color.decode("#F8EFD4"), 1, 15, 0));
			b.setContentAreaFilled(false);
			b.setPreferredSize(new Dimension(900, 70));
			b.setFont(new Font("微軟正黑體", Font.PLAIN, 16));
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
					+ testID + "' AND Year = '" + year + "' AND Number = " + number;
			ResultSet resultA = stat.executeQuery(queryA);
			resultA.next();
			aButton.setText(resultA.getString(1));
			resultA.close();

			String queryB = "SELECT B FROM UserQuestion WHERE UserID = '" + LoginPanel.getUserID() + "' AND TestID = '"
					+ testID + "' AND Year = '" + year + "' AND Number = " + number;
			ResultSet resultB = stat.executeQuery(queryB);
			resultB.next();
			bButton.setText(resultB.getString(1));
			resultB.close();

			String queryC = "SELECT C FROM UserQuestion WHERE UserID = '" + LoginPanel.getUserID() + "' AND TestID = '"
					+ testID + "' AND Year = '" + year + "' AND Number = " + number;
			ResultSet resultC = stat.executeQuery(queryC);
			resultC.next();
			cButton.setText(resultC.getString(1));
			resultC.close();

			String queryD = "SELECT D FROM UserQuestion WHERE UserID = '" + LoginPanel.getUserID() + "' AND TestID = '"
					+ testID + "' AND Year = '" + year + "' AND Number = " + number;
			ResultSet resultD = stat.executeQuery(queryD);
			resultD.next();
			dButton.setText(resultD.getString(1));
			resultD.close();

			String queryE = "SELECT E FROM UserQuestion WHERE UserID = '" + LoginPanel.getUserID() + "' AND TestID = '"
					+ testID + "' AND Year = '" + year + "' AND Number = " + number;
			ResultSet resultE = stat.executeQuery(queryE);
			resultE.next();
			if (resultE.getString(1) != null) {
				option = 5;
				eButton.setText(resultE.getString(1));
				eButton.setVisible(true);
				answerLabel[4].setVisible(true);
			} else {
				option = 4;
				eButton.setText("");
				eButton.setVisible(false);
				answerLabel[4].setVisible(false);
			}
			resultE.close();

			String queryUserAns = "SELECT UserAnswer FROM UserQuestion WHERE UserID = '" + LoginPanel.getUserID()
					+ "' AND TestID = '" + testID + "' AND Year = '" + year + "' AND Number = " + number;
			ResultSet resultUserAns = stat.executeQuery(queryUserAns);
			resultUserAns.next();
			String userAnswer = resultUserAns.getString(1);
			resultUserAns.close();

			String queryCorrAns = "SELECT Answer FROM UserQuestion WHERE UserID = '" + LoginPanel.getUserID()
					+ "' AND TestID = '" + testID + "' AND Year = '" + year + "' AND Number = " + number;
			ResultSet resultCorrAns = stat.executeQuery(queryCorrAns);
			resultCorrAns.next();
			String correctAnswer = resultCorrAns.getString(1);
			resultCorrAns.close();

			String[] border = { "y" , "y" , "y" , "y" , "y" };
			for (JButton b : answerButtons) {
				b.setBorder(new BubbleBorder(Color.decode("#F8EFD4"), 1, 15, 0));
			}
			for (int i = 0; i < correctAnswer.length(); i++) {
				switch (correctAnswer.charAt(i)) {
				case 'A':
					answerButtons.get(0).setBorder(new BubbleBorder(Color.decode("#5E8C61"), 2, 15, 0));
					border[0] = "g";
					break;
				case 'B':
					answerButtons.get(1).setBorder(new BubbleBorder(Color.decode("#5E8C61"), 2, 15, 0));
					border[1] = "g";
					break;
				case 'C':
					answerButtons.get(2).setBorder(new BubbleBorder(Color.decode("#5E8C61"), 2, 15, 0));
					border[2] = "g";
					break;
				case 'D':
					answerButtons.get(3).setBorder(new BubbleBorder(Color.decode("#5E8C61"), 2, 15, 0));
					border[3] = "g";
					break;
				case 'E':
					answerButtons.get(4).setBorder(new BubbleBorder(Color.decode("#5E8C61"), 2, 15, 0));
					border[4] = "g";
					break;
				}
			}
			if (userAnswer != null) {
				if (!userAnswer.equals(correctAnswer)) {
					if (option == 4) {
						switch (userAnswer) {
						case "A":
							answerButtons.get(0).setBorder(new BubbleBorder(Color.decode("#B6174B"), 2, 15, 0));
							border[0] = "r";
							break;
						case "B":
							answerButtons.get(1).setBorder(new BubbleBorder(Color.decode("#B6174B"), 2, 15, 0));
							border[1] = "r";
							break;
						case "C":
							answerButtons.get(2).setBorder(new BubbleBorder(Color.decode("#B6174B"), 2, 15, 0));
							border[2] = "r";
							break;
						case "D":
							answerButtons.get(3).setBorder(new BubbleBorder(Color.decode("#B6174B"), 2, 15, 0));
							border[3] = "r";
							break;
						}
					} else {
						for (int i = 0; i < userAnswer.length(); i++) {
							if (!correctAnswer.contains(userAnswer.charAt(i) + "")) {
								switch (userAnswer.charAt(i)) {
								case 'A':
									answerButtons.get(0).setBorder(new BubbleBorder(Color.decode("#B6174B"), 2, 15, 0));
									border[0] = "r";
									break;
								case 'B':
									answerButtons.get(1).setBorder(new BubbleBorder(Color.decode("#B6174B"), 2, 15, 0));
									border[1] = "r";
									break;
								case 'C':
									answerButtons.get(2).setBorder(new BubbleBorder(Color.decode("#B6174B"), 2, 15, 0));
									border[2] = "r";
									break;
								case 'D':
									answerButtons.get(3).setBorder(new BubbleBorder(Color.decode("#B6174B"), 2, 15, 0));
									border[3] = "r";
									break;
								case 'E':
									answerButtons.get(4).setBorder(new BubbleBorder(Color.decode("#B6174B"), 2, 15, 0));
									border[4] = "r";
									break;
								}
							} else {
								switch (userAnswer.charAt(i)) {
								case 'A':
									answerButtons.get(0).setBorder(new BubbleBorder(Color.decode("#E88D67"), 2, 15, 0));
									border[0] = "o";
									break;
								case 'B':
									answerButtons.get(1).setBorder(new BubbleBorder(Color.decode("#E88D67"), 2, 15, 0));
									border[1] = "o";
									break;
								case 'C':
									answerButtons.get(2).setBorder(new BubbleBorder(Color.decode("#E88D67"), 2, 15, 0));
									border[2] = "o";
									break;
								case 'D':
									answerButtons.get(3).setBorder(new BubbleBorder(Color.decode("#E88D67"), 2, 15, 0));
									border[3] = "o";
									break;
								case 'E':
									answerButtons.get(4).setBorder(new BubbleBorder(Color.decode("#E88D67"), 2, 15, 0));
									border[4] = "o";
									break;
								}
							}
						}
					}
				}
			}
			for (int i = 0; i < 5; i++) {
				if (border[i].equals("y")) {
					answerButtons.get(i).setToolTipText("");
				} else if (border[i].equals("g")) {
					answerButtons.get(i).setToolTipText("正解");
				} else if (border[i].equals("r")) {
					answerButtons.get(i).setToolTipText("您選的錯誤答案");
				} else if (border[i].equals("o")) {
					answerButtons.get(i).setToolTipText("您選的正確答案");
				}
			}
		} catch (Exception e) {
			System.out.printf("<AnswerKey panel> update answer comp %s\n", e.getMessage());
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
			gbc.insets = new Insets(0, 0, 5, 0);
			answerPanel.add(answerLabel[i], gbc);

			gbc = new GridBagConstraints();
			gbc.gridx = 1;
			gbc.gridy = i;
			gbc.weightx = 1.0;
			gbc.weighty = 1.0;
			gbc.insets = new Insets(0, 0, 5, 0);
			gbc.fill = GridBagConstraints.HORIZONTAL;
			answerPanel.add(answerButtons.get(i), gbc);

			gbc = new GridBagConstraints();
			gbc.gridx = 3;
			gbc.gridy = i;
			gbc.weightx = 1.0;
			gbc.weighty = 1.0;
			answerPanel.add(new JLabel(), gbc);
		}
	}

	public void createNoteComp() {
		keyArea = new JTextArea();
		keyArea.setEditable(false);
		keyArea.setLineWrap(true);
		keyArea.setBackground(Color.decode("#F8EFD4"));
		keyArea.setFont(new Font("微軟正黑體", Font.PLAIN, 16));

		noteArea = new JTextArea();
		noteArea.setEditable(true);
		noteArea.setLineWrap(true);
		noteArea.setBackground(Color.decode("#F8EFD4"));
		noteArea.setFont(new Font("微軟正黑體", Font.PLAIN, 16));
		noteArea.setText("");

		Box box = Box.createVerticalBox();
		box.add(keyArea);
		box.add(noteArea);

		noteScroll = new JScrollPane(box);
		noteScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		noteScroll.getViewport().getView().setBackground(Color.decode("#F8EFD4"));
		noteScroll.setBorder(new LineBorder(Color.decode("#F8EFD4")));
		noteScroll.setAlignmentX(Component.CENTER_ALIGNMENT);
		noteScroll.setPreferredSize(new Dimension(1000, 200));

		notePanel = new JPanel();
		notePanel.setBackground(Color.decode("#F8EFD4"));
		notePanel.setLayout(new BoxLayout(notePanel, BoxLayout.X_AXIS));
		notePanel.add(Box.createRigidArea(new Dimension(10, 0)));
		notePanel.add(noteScroll);
		notePanel.add(Box.createRigidArea(new Dimension(10, 0)));
	}

	public void updateNoteComp() {
		try {
			Statement stat = conn.createStatement();
			String query = "SELECT Answer, Degree, AnswerKey FROM UserQuestion WHERE UserID = '"
					+ LoginPanel.getUserID() + "' AND TestID = '" + testID + "' AND Year = '" + year + "' AND Number = "
					+ number;
			ResultSet result = stat.executeQuery(query);
			if (result.next()) {
				String answer = "";
				for (int i = 0; i < result.getString("Answer").length(); i++) {
					answer += String.format("(%s) ", result.getString("Answer").charAt(i));
				}
				keyArea.setText(String.format("* 正解: %s\n  答對率: %d%s\n\n* 詳解: \n%s\n\n* 你的筆記:", answer,
						result.getInt("Degree"), "%", result.getString("AnswerKey")));
			}
		} catch (Exception e) {
			System.out.println("<AnswerKeyPanel> update key" + e.getMessage());
		}

		try {
			Statement stat = conn.createStatement();
			String query = "SELECT Note FROM UserQuestion WHERE UserID = '" + LoginPanel.getUserID()
					+ "' AND TestID = '" + testID + "' AND Year = '" + year + "' AND Number = " + number;
			ResultSet result = stat.executeQuery(query);
			if (result.next()) {
				noteArea.append(result.getString(1));
			}
			result.close();
		} catch (Exception e) {
			System.out.printf("<AnswerKeyPanel> update note %s\n", e.getMessage());
		}
	}

	public void insertNote() {
		try {
			if (!noteArea.getText().equals("")) {
				Statement stat = conn.createStatement();
				String query = "UPDATE UserQuestion SET Note = '" + noteArea.getText() + "' WHERE UserID = '"
						+ LoginPanel.getUserID() + "' AND TestID = '" + testID + "' AND Year = '" + year
						+ "' AND Number = " + number;
				stat.execute(query);
			}
		} catch (Exception e) {
			System.out.printf("<AnswerKeyPanel> update note: %s\n", e.getMessage());
		}
	}

	public void repaintPanel() {
		int lNum = 0, fNum = 0, fID = 0, lID = 0, fYear = 0, lYear = 0;
		try {
			Statement stat = conn.createStatement();
			String query = "";
			if (title.equals("Answer")) {
				query = "SELECT Year, Number FROM UserQuestion WHERE UserID = '" + LoginPanel.getUserID()
						+ "' AND TestID = '" + RangePanel.getTestID() + "'";
			} else if (title.equals("Note")) {
				query = "SELECT TestID, Year, Number FROM UserQuestion WHERE UserID = '" + LoginPanel.getUserID()
						+ "' AND Subject = '" + subject + "' AND Note IS NOT " + null;
			} else if (title.equals("My Tests")) {
				query = "SELECT Year, Number FROM UserQuestion WHERE UserID = '" + LoginPanel.getUserID()
						+ "' AND TestID = '" + testID + "'";
			}

			ResultSet result = stat.executeQuery(query);
			result.next();
			if (title.equals("Note")) {
				fID = result.getInt("TestID");
			}
			fYear = result.getInt("Year");
			fNum = result.getInt("Number");
			
			result.last();
			if (title.equals("Note")) {
				lID = result.getInt("TestID");
			}
			lYear = result.getInt("Year");
			lNum = result.getInt("Number");
			result.close();
		} catch (Exception e) {
			System.out.printf("<AnswerKeyPanel> repaint panel: %s\n", e.getMessage());
		}

		if (title.equals("Answer") || title.equals("My Tests")) {
			if (fNum == number && fYear == year) {
				backButton.setIcon(null);
				backButton.setEnabled(false);
				nextButton.setIcon(nextIcon);
				nextButton.setEnabled(true);
			} else if (lNum == number && lYear == year) {
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
		} else if (title.equals("Note")) {
			if (fNum == number && fID == testID && fYear == year) {
				backButton.setIcon(null);
				backButton.setEnabled(false);
				nextButton.setIcon(nextIcon);
				nextButton.setEnabled(true);
			} else if (lNum == number && lID == testID && lYear == year) {
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
		}

		questionText.setText("");
		noteArea.setText("");
		removeAll();
		updateMarkButton();
		updateQuestionText();
		updateAnswerComp();
		updateNoteComp();
		setLayout();
		validate();
		repaint();
	}

	public void createDeleteButton() {
		deleteNoteBtn = new JButton("<html><u>Delete Note</u></html>");
		deleteNoteBtn.setFont(new Font("Times New Roman", Font.BOLD, 18));
		deleteNoteBtn.setForeground(Color.gray);
		deleteNoteBtn.setBorder(null);
		deleteNoteBtn.setContentAreaFilled(false);
		deleteNoteBtn.setToolTipText("刪除筆記內容");

		class ClickListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				noteArea.setText("");
				try {
					Statement stat = conn.createStatement();
					String query = "UPDATE UserQuestion SET Note = " + null + " WHERE UserID = '"
							+ LoginPanel.getUserID() + "' AND TestID = '" + testID + "' AND Year = '" + year
							+ "' AND Number = " + number;
					stat.execute(query);
				} catch (Exception ex) {
					System.out.printf("<AnswerKeyPanel> deleteNoteBtn listener: %s\n", ex.getMessage());
				}
			}
		}
		ClickListener listener = new ClickListener();
		deleteNoteBtn.addActionListener(listener);
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
				try {
					insertNote();

					Statement stat = conn.createStatement();
					String query = "";
					if (title.equals("Answer")) {
						query = "SELECT Year, Number FROM UserQuestion WHERE UserID = '" + LoginPanel.getUserID()
								+ "' AND TestID = '" + RangePanel.getTestID() + "'";
					} else if (title.equals("Note")) {
						query = "SELECT TestID, Year, Number FROM UserQuestion WHERE UserID = '"
								+ LoginPanel.getUserID() + "' AND Subject = '" + subject + "' AND Note IS NOT " + null;
					} else if (title.equals("My Tests")) {
						query = "SELECT Year, Number FROM UserQuestion WHERE UserID = '" + LoginPanel.getUserID()
								+ "' AND TestID = '" + testID + "'";
					}

					ResultSet result = stat.executeQuery(query);
					while (result.next()) {
						if (title.equals("Answer") || title.equals("My Tests")) {
							if (result.getInt("Number") == number && result.getInt("Year") == year) {
								result.previous();
								updateNumber(result.getInt("Number"));
								repaintPanel();
							}
						} else if (title.equals("Note")) {
							if (result.getInt("Number") == number && result.getInt("TestID") == testID
									&& result.getInt("Year") == year) {
								result.previous();
								updateNumber(result.getInt("Number"));
								repaintPanel();
							}
						}
					}
					result.close();
				} catch (Exception exception) {
					System.out.printf("<AnswerKeyPanel> back button listener: %s\n", exception.getMessage());
				}
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
				try {
					insertNote();

					Statement stat = conn.createStatement();
					String query = "";
					if (title.equals("Answer")) {
						query = "SELECT Year, Number FROM UserQuestion WHERE UserID = '" + LoginPanel.getUserID()
								+ "' AND TestID = '" + RangePanel.getTestID() + "'";
					} else if (title.equals("Note")) {
						query = "SELECT TestID, Year, Number FROM UserQuestion WHERE UserID = '"
								+ LoginPanel.getUserID() + "' AND Subject = '" + subject + "' AND Note IS NOT " + null;
					} else if (title.equals("My Tests")) {
						query = "SELECT Year, Number FROM UserQuestion WHERE UserID = '" + LoginPanel.getUserID()
								+ "' AND TestID = '" + testID + "'";
					}

					ResultSet result = stat.executeQuery(query);
					while (result.next()) {
						if (title.equals("Answer") || title.equals("My Tests")) {
							if (result.getInt("Number") == number && result.getInt("Year") == year) {
								result.next();
								updateNumber(result.getInt("Number"));
								repaintPanel();
							}
						} else if (title.equals("Note")) {
							if (result.getInt("Number") == number && result.getInt("TestID") == testID
									&& result.getInt("Year") == year) {
								result.next();
								updateNumber(result.getInt("Number"));
								repaintPanel();
							}
						}
					}
					result.close();
				} catch (Exception exception) {
					System.out.printf("<AnswerKeyPanel> next button listener: %s\n", exception.getMessage());
				}
			}
		}
		ClickListener listener = new ClickListener();
		nextButton.addActionListener(listener);
	}

	public void createToolPanel() {
		down_toolPanel = new JPanel();
		down_toolPanel.setBackground(Color.decode("#F8EFD4"));
		down_toolPanel.setLayout(new BoxLayout(down_toolPanel, BoxLayout.X_AXIS));
		down_toolPanel.add(Box.createRigidArea(new Dimension(25, 0)));
		down_toolPanel.add(backButton);
		down_toolPanel.add(Box.createGlue());
		down_toolPanel.add(moreButton);
		down_toolPanel.add(Box.createGlue());
		down_toolPanel.add(nextButton);
		down_toolPanel.add(Box.createRigidArea(new Dimension(30, 0)));
	}

	public void setLayout() {
		setBackground(Color.decode("#F8EFD4"));
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		add(Box.createRigidArea(new Dimension(0, 20)));
		add(questionPanel);
		add(Box.createRigidArea(new Dimension(0, 10)));
		add(answerPanel);
		add(Box.createRigidArea(new Dimension(0, 10)));
		add(deleteNoteBtn);
		add(notePanel);
		add(Box.createRigidArea(new Dimension(0, 10)));
		add(down_toolPanel);
		add(Box.createRigidArea(new Dimension(0, 10)));
	}
}
