import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
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

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import style.*;

public class QuestionPanel extends JPanel {
	private JPanel up_toolPanel;
	private JPanel down_toolPanel;
	private JPanel questionPanel;
	private JPanel answerPanel;
	private JButton moreButton;
	private JButton playButton;
	private JButton pauseButton;
	private JLabel timeLabel;
	private JButton finishButton;
	private JButton backButton;
	private JButton nextButton;
	private JTextArea questionArea;
	private JButton markButton;
	private JLabel[] answerLabel;
	private ArrayList<JButton> answerButtons;
	private JButton aButton;
	private JButton bButton;
	private JButton cButton;
	private JButton dButton;
	private JButton eButton;
	private JButton[] xButtons;
	private int number = 1;
	private JButton[] numButtons;
	private Connection conn;
	private QTool qTool;

	public QuestionPanel() {
		try {
			String server = "jdbc:mysql://140.119.19.73:9306/";
			String database = "MG05";
			String url = server + database;
			String username = "MG05";
			String password = "9mMuzQ";
			conn = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		qTool = new QTool();
		createBackButton();
		createNextButton();
		createMarkButton();
		createQuestionArea();
		createAnswerComp();
		createQToolPanel();
		setLayout();
	}

	public QTool getQTool() {
		return this.qTool;
	}

	public void addQNumListener(JPanel panel, QToolPanel qToolPanel) {
		numButtons = qToolPanel.getNumButtons();
		
		class ClickListener implements ActionListener {
			CardLayout cardLayout = (CardLayout) (panel.getLayout());

			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < numButtons.length; i++) {
					if (numButtons[i].getModel().isArmed()) {
						number = i + 1;
						if (number == 1) {
							backButton.setVisible(false);
							nextButton.setVisible(true);
						} else if (number == numButtons.length){
							backButton.setVisible(true);
							nextButton.setVisible(false);
						} else {
							backButton.setVisible(true);
							nextButton.setVisible(true);
						}
						removeAll();
						createQuestionArea();
						createAnswerComp();
						setLayout();
						validate();
						repaint();
						cardLayout.show(panel, "1");
					}
				}
			}
		}
		ClickListener listener = new ClickListener();
		for (int i = 0; i < numButtons.length; i++) {
			numButtons[i].addActionListener(listener);
		}
	}
	
	public void createBackButton() {
		ImageIcon backIcon = new ImageIcon(
				new ImageIcon("images/back.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
		backButton = new JButton(); // "<-"
		backButton.setIcon(backIcon);
		backButton.setBorder(null);
		backButton.setOpaque(false);
		backButton.setContentAreaFilled(false);
		backButton.setVisible(false);
		class ClickListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				number--;
				nextButton.setVisible(true);
				if (number == 1) {
					backButton.setVisible(false);
				}
				removeAll();
				createQuestionArea();
				createAnswerComp();
				setLayout();
				validate();
				repaint();
			}
		}
		ClickListener listener = new ClickListener();
		backButton.addActionListener(listener);
	}

	public void createNextButton() {
		ImageIcon nextIcon = new ImageIcon(
				new ImageIcon("images/next.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
		nextButton = new JButton(); // "->"
		nextButton.setIcon(nextIcon);
		nextButton.setBorder(null);
		nextButton.setOpaque(false);
		nextButton.setContentAreaFilled(false);
		class ClickListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				number++;
				backButton.setVisible(true);
				if (number == numButtons.length) {
					nextButton.setVisible(false);
				}
				removeAll();
				createQuestionArea();
				createAnswerComp();
				setLayout();
				validate();
				repaint();

			}
		}
		ClickListener listener = new ClickListener();
		nextButton.addActionListener(listener);
	}

	public void createMarkButton() {
		ImageIcon beforeMark = new ImageIcon(
				new ImageIcon("images/beforeMark.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
		ImageIcon afterMark = new ImageIcon(
				new ImageIcon("images/afterMark.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
		markButton = new JButton(); // "?"
		markButton.setIcon(beforeMark);
		markButton.setBorder(null);
		markButton.setOpaque(false);
		markButton.setContentAreaFilled(false);
		class ClickListener implements ActionListener {
			int click = 0;

			public void actionPerformed(ActionEvent e) {
				click++;
				if (click % 2 == 1) {
					markButton.setIcon(afterMark);
				} else {
					markButton.setIcon(beforeMark);
				}
			}
		}
		ClickListener listener = new ClickListener();
		markButton.addActionListener(listener);
	}

	public void createQuestionArea() {
		questionArea = new JTextArea();
		questionArea.setLineWrap(true);
		questionArea.setEditable(false);

		JScrollPane scrollPane = new JScrollPane(questionArea);
		scrollPane.getViewport().getView().setBackground(Color.decode("#F8EFD4"));
		scrollPane.setBorder(new LineBorder(Color.decode("#EC9706")));
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

		try {
			Statement stat = conn.createStatement();
			String Question = "SELECT Question FROM Society WHERE Number = " + number;
			stat.execute(Question);
			ResultSet result = stat.getResultSet();
			result.next();

			questionArea.setText(String.format("Q%d. %s", number, result.getString(1)));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void createAnswerComp() {
		try {
			Statement statA = conn.createStatement();
			Statement statB = conn.createStatement();
			Statement statC = conn.createStatement();
			Statement statD = conn.createStatement();

			String queryA = "SELECT A FROM Society WHERE Number = " + number;
			statA.execute(queryA);
			ResultSet resultA = statA.getResultSet();
			resultA.next();

			String queryB = "SELECT B FROM Society WHERE Number = " + number;
			statB.execute(queryB);
			ResultSet resultB = statB.getResultSet();
			resultB.next();

			String queryC = "SELECT C FROM Society WHERE Number = " + number;
			statC.execute(queryC);
			ResultSet resultC = statC.getResultSet();
			resultC.next();

			String queryD = "SELECT D FROM Society WHERE Number = " + number;
			statD.execute(queryD);
			ResultSet resultD = statD.getResultSet();
			resultD.next();

			answerButtons = new ArrayList<JButton>();
			aButton = new JButton(resultA.getString(1));
			bButton = new JButton(resultB.getString(1));
			cButton = new JButton(resultC.getString(1));
			dButton = new JButton(resultD.getString(1));
			answerButtons.add(aButton);
			answerButtons.add(bButton);
			answerButtons.add(cButton);
			answerButtons.add(dButton);

			ImageIcon beforeX = new ImageIcon(
					new ImageIcon("images/beforeX.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
			ImageIcon afterX = new ImageIcon(
					new ImageIcon("images/afterX.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
			xButtons = new JButton[4];
			class X_ClickListener implements ActionListener {
				boolean[] click = { false, false, false, false };

				public void actionPerformed(ActionEvent e) {
					for (int i = 0; i < xButtons.length; i++) {
						if (xButtons[i].getModel().isArmed()) {
							if (click[i] == false) {
								xButtons[i].setIcon(afterX);
								answerButtons.get(i).setBorder(null);
								click[i] = true;
							} else {
								xButtons[i].setIcon(beforeX);
								click[i] = false;
							}
						}
					}
				}
			}
			X_ClickListener xListener = new X_ClickListener();
			for (int i = 0; i < 4; i++) {
				xButtons[i] = new JButton(); // "X"
				xButtons[i].setBorder(null);
				xButtons[i].setContentAreaFilled(false);
				xButtons[i].setIcon(beforeX);
				xButtons[i].addActionListener(xListener);
			}

			class Ans_ClickListener implements ActionListener {
				boolean[] click = { false, false, false, false };

				public void actionPerformed(ActionEvent e) {
					for (int i = 0; i < answerButtons.size(); i++) {
						if (answerButtons.get(i).getModel().isArmed()) {
							if (click[i] == false) {
								answerButtons.get(i).setBorder(new BubbleBorder(Color.decode("#5E8CD1"), 1, 15, 0));
								xButtons[i].setIcon(beforeX);
								click[i] = true;
							} else {
								answerButtons.get(i).setBorder(null);
								click[i] = false;
							}
						} else {
							answerButtons.get(i).setBorder(null);
						}
					}
				}
			}
			Ans_ClickListener ansListener = new Ans_ClickListener();
			for (JButton b : answerButtons) {
				b.setHorizontalAlignment(SwingConstants.LEFT);
				b.setContentAreaFilled(false);
				b.setPreferredSize(new Dimension(900, 100));
				b.setBorder(null);
				b.addActionListener(ansListener);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		answerLabel = new JLabel[4];
		for (int i = 0; i < 4; i++) {
			answerLabel[i] = new JLabel();
			answerLabel[i].setBorder(null);
		}
		answerLabel[0].setText("A.");
		answerLabel[1].setText("B.");
		answerLabel[2].setText("C.");
		answerLabel[3].setText("D.");

		answerPanel = new JPanel(new GridBagLayout());
		answerPanel.setBackground(Color.decode("#F8EFD4"));
		GridBagConstraints gbc;
		for (int i = 0; i < 4; i++) {
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

	public void createQToolPanel() {
		moreButton = qTool.createMoreButton();
		playButton = qTool.createPlayButton();
		pauseButton = qTool.createPauseButton();
		timeLabel = qTool.createTimer();
		finishButton = qTool.createFinishButton();

		up_toolPanel = qTool.createUpToolPanel(moreButton, playButton, pauseButton, timeLabel);

		down_toolPanel = new JPanel();
		down_toolPanel.setBackground(Color.decode("#F8EFD4"));
		down_toolPanel.setLayout(new BoxLayout(down_toolPanel, BoxLayout.X_AXIS));
		down_toolPanel.add(Box.createRigidArea(new Dimension(25, 0)));
		down_toolPanel.add(backButton);
		down_toolPanel.add(Box.createGlue());
		down_toolPanel.add(finishButton);
		down_toolPanel.add(Box.createGlue());
		down_toolPanel.add(nextButton);
		down_toolPanel.add(Box.createRigidArea(new Dimension(30, 0)));
	}

	public void setLayout() {
		setBackground(Color.decode("#F8EFD4"));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(Box.createRigidArea(new Dimension(0, 10)));
		add(up_toolPanel);
		add(Box.createRigidArea(new Dimension(0, 30)));
		add(questionPanel);
		add(Box.createRigidArea(new Dimension(0, 5)));
		add(answerPanel);
		add(Box.createGlue());
		add(down_toolPanel);
		add(Box.createRigidArea(new Dimension(0, 10)));
	}
}
