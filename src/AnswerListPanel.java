import java.awt.CardLayout;
import java.awt.Color;
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
import javax.swing.SwingConstants;

import style.BubbleBorder;

public class AnswerListPanel extends JPanel {
	private JLabel titleLabel;
	private ArrayList<JButton> questionButtons;
	private ArrayList<JLabel> questionLabels;
	private ArrayList<JLabel> answerLabels;
	private JButton homeButton, backButton;
	private JPanel menuPanel, down_toolPanel;
	private JScrollPane scrollPane;
	private Connection conn;
	private QTool qTool;
	private String title, listSubject = "";
	private int listID;
	private ArrayList<Integer> testID, number, year;
	private AnsListBtnAction listener;
	private ImageIcon backIcon;

	public AnswerListPanel() {
		try {
			String server = "jdbc:mysql://140.119.19.73:9306/";
			String database = "MG05";
			String url = server + database
					+ "?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&failOverReadOnly=false";
			String username = "MG05";
			String password = "9mMuzQ";
			conn = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			System.out.println("<AnswerListPanel> constructor: " + e.getMessage());
		}
		initialize();
	}

	public void initialize() {
		testID = new ArrayList<Integer>();
		year = new ArrayList<Integer>();
		number = new ArrayList<Integer>();
		createTitleLabel();
		createMenuPanel();
		createBackButton();
		createToolPanel();
		setLayout();
	}

	public QTool getQTool() {
		return this.qTool;
	}

	public ArrayList<JButton> getQuestionButtons() {
		return this.questionButtons;
	}

	public ArrayList<JLabel> getQuestionLabels() {
		return this.questionLabels;
	}

	public void updateTestID(int ID) {
		this.listID = ID;
	}

	public void updateTitle(String title) {
		this.title = title;
		ImageIcon icon;

		if (title.equals("Answer")) {
			backButton.setIcon(null);
			backButton.setEnabled(false);
			icon = new ImageIcon(
					new ImageIcon("images/test.png").getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT));
			titleLabel.setIcon(icon);
		} else if (title.equals("My Tests")) {
			backButton.setIcon(backIcon);
			backButton.setEnabled(true);
			icon = new ImageIcon(
					new ImageIcon("images/list.png").getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT));
			titleLabel.setIcon(icon);
		} else {
			backButton.setIcon(backIcon);
			backButton.setEnabled(true);
			icon = new ImageIcon(
					new ImageIcon("images/note.png").getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT));
			titleLabel.setIcon(icon);
		}
	}

	public String getTitle() {
		return this.title;
	}

	public void createTitleLabel() {
		titleLabel = new JLabel();
		titleLabel.setVerticalTextPosition(SwingConstants.CENTER);
		titleLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
		titleLabel.setFont(new Font("Lucida Handwriting", Font.ITALIC, 40));
		titleLabel.setForeground(new Color(139, 69, 19));
	}

	public void updateTitleLabel() {
		titleLabel.setText(title);
	}

	public void createMenuPanel() {
		questionButtons = new ArrayList<JButton>();
		questionLabels = new ArrayList<JLabel>();
		answerLabels = new ArrayList<JLabel>();
		menuPanel = new JPanel(new GridBagLayout());
		menuPanel.setBackground(Color.decode("#F8EFD4"));

		scrollPane = new JScrollPane(menuPanel);
		scrollPane.setHorizontalScrollBar(null);
		scrollPane.setAlignmentY(TOP_ALIGNMENT);
		scrollPane.setBackground(Color.decode("#F8EFD4"));
		scrollPane.setBorder(null);
		scrollPane.setPreferredSize(new Dimension(500, 500));
	}

	public void updateMenuPanel() {
		String query = "";
		GridBagConstraints gbc;
		int i = 0;
		try {
			Statement stat = conn.createStatement();
			if (title.equals("Answer")) {
				query = "SELECT Year, Number, Question, Answer, UserAnswer, Mark FROM UserQuestion WHERE UserID = '"
						+ LoginPanel.getUserID() + "' AND TestID = '" + RangePanel.getTestID() + "'";
			} else if (title.equals("My Tests")) {
				query = "SELECT Year, Subject, Number, Question, Answer, UserAnswer, Mark FROM UserQuestion WHERE UserID = '"
						+ LoginPanel.getUserID() + "' AND TestID = '" + listID + "'";
			} else {
				query = "SELECT TestID, Year, Number, Question, Answer, UserAnswer, Mark FROM UserQuestion WHERE UserID = '"
						+ LoginPanel.getUserID() + "' AND Subject = '" + title + "' AND Note <> '" + null + "'";
			}

			boolean hasResultSet = stat.execute(query);
			if (hasResultSet == true) {
				ResultSet result = stat.getResultSet();
				while (result.next()) {
					questionButtons.add(new JButton(String.format("%s", result.getString("Question"))));
					questionButtons.get(i).setHorizontalAlignment(SwingConstants.LEFT);
					questionButtons.get(i).setContentAreaFilled(false);
					questionButtons.get(i).setPreferredSize(new Dimension(700, 50));
					if (result.getString("Answer").equals(result.getString("UserAnswer"))) {
						questionButtons.get(i).setBorder(new BubbleBorder(Color.decode("#5E8C61"), 2, 15, 0));
					} else {
						questionButtons.get(i).setBorder(new BubbleBorder(Color.decode("#B6174B"), 2, 15, 0));
					}

					if (title.equals("Answer") || title.equals("My Tests")) {
						questionLabels.add(
								new JLabel(String.format("%d - Q%d", result.getInt("Year"), result.getInt("Number"))));
					} else {
						questionLabels.add(new JLabel(String.format("Test%d: %d - Q%d", result.getInt("TestID"),
								result.getInt("Year"), result.getInt("Number"))));
					}
					if (result.getString("Mark").equals("TRUE")) {
						ImageIcon beforeMark = new ImageIcon(new ImageIcon("images/beforeMark.png").getImage()
								.getScaledInstance(25, 25, Image.SCALE_DEFAULT));
						questionLabels.get(i).setIcon(beforeMark);
						questionLabels.get(i).setVerticalTextPosition(SwingConstants.CENTER);
						questionLabels.get(i).setHorizontalTextPosition(SwingConstants.RIGHT);
					}

					if (result.getString("UserAnswer") == null) {
						answerLabels.add(new JLabel(String.format("%s / null", result.getString("Answer"))));
						answerLabels.get(i).setToolTipText("正解 / 您的答案");
					} else {
						answerLabels.add(new JLabel(
								String.format("%s / %s", result.getString("Answer"), result.getString("UserAnswer"))));
						answerLabels.get(i).setToolTipText("正解 / 您的答案");
					}

					if (!title.equals("Answer") && !title.equals("My Tests")) {
						testID.add(result.getInt("TestID"));
					}
					year.add(result.getInt("Year"));
					number.add(result.getInt("Number"));
					if (title.equals("My Tests")) {
						listSubject = result.getString("Subject");
					}

					gbc = new GridBagConstraints();
					gbc.gridx = 0;
					gbc.gridy = i;
					gbc.weightx = 1.0;
					gbc.weighty = 1.0;
					gbc.insets = new Insets(10, 10, 0, 0);
					menuPanel.add(questionLabels.get(i), gbc);

					gbc = new GridBagConstraints();
					gbc.gridx = 1;
					gbc.gridy = i;
					gbc.weightx = 1.0;
					gbc.weighty = 1.0;
					gbc.insets = new Insets(10, 0, 0, 10);
					menuPanel.add(answerLabels.get(i), gbc);

					gbc = new GridBagConstraints();
					gbc.gridx = 2;
					gbc.gridy = i;
					gbc.weightx = 1.0;
					gbc.weighty = 1.0;
					gbc.insets = new Insets(10, 0, 0, 30);
					gbc.fill = GridBagConstraints.HORIZONTAL;
					menuPanel.add(questionButtons.get(i), gbc);

					i++;
				}
				result.close();

				for (JButton b : questionButtons) {
					b.addActionListener(listener);
				}
			}
		} catch (Exception e) {
			System.out.println("<AnswerListPanel> createQuestionComp: " + e.getMessage());
		}
	}

	public void addQuestionButtonListener(JPanel panel, AnswerListPanel ansListPanel, AnswerKeyPanel keyPanel) {
		listener = new AnsListBtnAction(panel, ansListPanel, keyPanel);
		for (JButton b : questionButtons) {
			b.addActionListener(listener);
		}
	}

	public class AnsListBtnAction implements ActionListener {
		JPanel panel;
		AnswerKeyPanel keyPanel;
		AnswerListPanel ansListPanel;
		CardLayout cardLayout;

		public AnsListBtnAction(JPanel panel, AnswerListPanel ansListPanel, AnswerKeyPanel keyPanel) {
			this.panel = panel;
			this.keyPanel = keyPanel;
			this.ansListPanel = ansListPanel;
			cardLayout = (CardLayout) (panel.getLayout());
		}

		public void actionPerformed(ActionEvent e) {
			for (int i = 0; i < ansListPanel.getQuestionButtons().size(); i++) {
				if (ansListPanel.getQuestionButtons().get(i).getModel().isArmed()) {
					keyPanel.updateNumber(number.get(i));
					keyPanel.updateYear(year.get(i));
					if (title.equals("Answer")) {
						keyPanel.updateTestID(RangePanel.getTestID());
						keyPanel.updateSubject(QuestionPanel.getSubject());
						keyPanel.updateTitle("Answer");
					} else if (title.equals("My Tests")) {
						keyPanel.updateTestID(listID);
						keyPanel.updateSubject(listSubject);
						keyPanel.updateTitle("My Tests");
					} else {
						keyPanel.updateTestID(testID.get(i));
						keyPanel.updateSubject(title);
						keyPanel.updateTitle("Note");
					}
					keyPanel.repaintPanel();

					if (ansListPanel.getName().equals("ansListPanel")) {
						cardLayout.show(panel, "answerKeyPanel");
					} else if (ansListPanel.getName().equals("noteQPanel")) {
						cardLayout.show(panel, "noteKeyPanel");
					} else if (ansListPanel.getName().equals("listQPanel")) {
						cardLayout.show(panel, "listKeyPanel");
					}
				}
			}
		}
	}

	public void createBackButton() {
		backIcon = new ImageIcon(
				new ImageIcon("images/back1.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
		backButton = new JButton(); // "<-"
		backButton.setEnabled(false);
		backButton.setBorder(null);
		backButton.setContentAreaFilled(false);
		backButton.setPreferredSize(new Dimension(30, 30));
		backButton.setToolTipText("返回前頁");
	}

	public void addBackListener(JPanel panel) {
		CardLayout cardLayout = (CardLayout) (panel.getLayout());

		class ClickListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if (title.equals("Answer")) {

				} else if (title.equals("My Tests")) {
					cardLayout.show(panel, "listPanel");
				} else {
					cardLayout.show(panel, "subjectPanel");
				}
			}
		}
		ClickListener listener = new ClickListener();
		backButton.addActionListener(listener);
	}

	public void createToolPanel() {
		qTool = new QTool();
		homeButton = qTool.getHomeButton();

		down_toolPanel = new JPanel();
		down_toolPanel.setBackground(Color.decode("#F8EFD4"));
		down_toolPanel.setLayout(new BoxLayout(down_toolPanel, BoxLayout.X_AXIS));
		down_toolPanel.add(Box.createRigidArea(new Dimension(50, 0)));
		down_toolPanel.add(backButton);
		down_toolPanel.add(Box.createHorizontalGlue());
		down_toolPanel.add(homeButton);
		down_toolPanel.add(Box.createHorizontalGlue());
		down_toolPanel.add(Box.createRigidArea(new Dimension(80, 0)));
	}

	public void setLayout() {
		setBackground(Color.decode("#F8EFD4"));
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.insets = new Insets(20, 0, 0, 0);
		gbc.anchor = GridBagConstraints.NORTH;
		add(titleLabel, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		add(scrollPane, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.insets = new Insets(0, 0, 20, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.SOUTH;
		add(down_toolPanel, gbc);
	}

	public void repaintPanel() {
		testID = new ArrayList<Integer>();
		year = new ArrayList<Integer>();
		number = new ArrayList<Integer>();
		questionButtons.clear();
		questionLabels.clear();
		answerLabels.clear();
		menuPanel.removeAll();

		removeAll();
		updateTitleLabel();
		updateMenuPanel();
		validate();
		setLayout();
	}
}
