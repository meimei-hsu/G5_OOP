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
import style.RoundButton;

public class ListPanel extends JPanel {
	private JLabel titleLabel;
	private ArrayList<JButton> testButtons;
	private ArrayList<RoundButton> deleteBtns;
	private ArrayList<Integer> delete;
	private JPanel menuPanel;
	private JButton homeButton, deleteBtn;
	private JScrollPane scrollPane;
	private Connection conn;
	private QTool qTool;
	private ArrayList<Integer> testID;
	private ListBtnAction listener;

	public ListPanel() {
		try {
			String server = "jdbc:mysql://140.119.19.73:9306/";
			String database = "MG05";
			String url = server + database
					+ "?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&failOverReadOnly=false";
			String username = "MG05";
			String password = "9mMuzQ";
			conn = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			System.out.println("<ListPanel> constructor: " + e.getMessage());
		}
		initialize();
	}

	public void initialize() {
		qTool = new QTool();
		homeButton = qTool.getHomeButton();
		
		testID = new ArrayList<Integer>();
		delete = new ArrayList<Integer>();
		createTitleLabel();
		createMenuPanel();
		updateMenuPanel();
		createDeleteButton();
		setLayout();
	}

	public QTool getQTool() {
		return this.qTool;
	}

	public ArrayList<JButton> getTestButtons() {
		return this.testButtons;
	}

	public void createTitleLabel() {
		ImageIcon listIcon = new ImageIcon(
				new ImageIcon("images/list.png").getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT));
		titleLabel = new JLabel("My Tests");
		titleLabel.setIcon(listIcon);
		titleLabel.setVerticalTextPosition(SwingConstants.CENTER);
		titleLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
		titleLabel.setFont(new Font("Lucida Handwriting", Font.ITALIC, 40));
		titleLabel.setForeground(new Color(139, 69, 19));
	}

	public void createMenuPanel() {
		testButtons = new ArrayList<JButton>();
		deleteBtns = new ArrayList<RoundButton>();
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
		GridBagConstraints gbc;
		int i = 0;
		try {
			Statement stat = conn.createStatement();
			String query = "SELECT TestID, Name, Score, Pass, Time, Date FROM AllTests WHERE UserID = '"
					+ LoginPanel.getUserID() + "'";
			boolean hasResultSet = stat.execute(query);
			if (hasResultSet == true) {
				ResultSet result = stat.getResultSet();
				while (result.next()) {
					String s = String.format("Test %d: %s  ||  %s  ||  %s  ||  %s 完成", result.getInt("TestID"),
							result.getString("Name"), result.getString("Score"), result.getString("Time"),
							result.getString("Date"));
					testButtons.add(new JButton(s));
					testButtons.get(i).setFont(new Font("微軟正黑體", Font.BOLD, 16));
					testButtons.get(i).setHorizontalAlignment(SwingConstants.CENTER);
					testButtons.get(i).setContentAreaFilled(false);
					testButtons.get(i).setPreferredSize(new Dimension(700, 50));
					if (result.getString("Pass").equals("TRUE")) {
						testButtons.get(i).setBorder(new BubbleBorder(Color.decode("#5E8C61"), 2, 15, 0));
						testButtons.get(i).setToolTipText("及格");
					} else {
						testButtons.get(i).setBorder(new BubbleBorder(Color.decode("#B6174B"), 2, 15, 0));
						testButtons.get(i).setToolTipText("未及格");
					}
					deleteBtns.add(
							new RoundButton("D", Color.decode("#F8EFD4"), Color.darkGray, Color.decode("#F8EFD4")));
					deleteBtns.get(i).setForeground(Color.gray);
					deleteBtns.get(i).setPreferredSize(new Dimension(50, 50));
					deleteBtns.get(i).setFont(new Font("Times New Roman", Font.BOLD, 14));
					deleteBtns.get(i).setVisible(false);
					deleteBtns.get(i).setToolTipText("刪除Test " + result.getInt("TestID"));

					testID.add(result.getInt("TestID"));

					gbc = new GridBagConstraints();
					gbc.gridx = 0;
					gbc.gridy = i;
					gbc.weightx = 1.0;
					gbc.weighty = 1.0;
					gbc.insets = new Insets(10, 10, 0, 10);
					menuPanel.add(deleteBtns.get(i), gbc);

					gbc = new GridBagConstraints();
					gbc.gridx = 1;
					gbc.gridy = i;
					gbc.weightx = 1.0;
					gbc.weighty = 1.0;
					gbc.insets = new Insets(10, 20, 0, 20);
					// gbc.insets = new Insets(10, 30, 0, 30);
					gbc.fill = GridBagConstraints.HORIZONTAL;
					menuPanel.add(testButtons.get(i), gbc);

					i++;
				}
				result.close();

				for (JButton b : testButtons) {
					b.addActionListener(listener);
				}
				addRoundDeleteListener();
			}
		} catch (Exception e) {
			System.out.println("<ListPanel> updateMenuPanel: " + e.getMessage());
		}
	}

	public void addListButtonListener(JPanel panel, ListPanel listPanel, AnswerListPanel ansListPanel) {
		listener = new ListBtnAction(panel, listPanel, ansListPanel);
		for (JButton b : testButtons) {
			b.addActionListener(listener);
		}
	}

	public class ListBtnAction implements ActionListener {
		JPanel panel;
		AnswerListPanel ansListPanel;
		ListPanel listPanel;
		CardLayout cardLayout;

		public ListBtnAction(JPanel panel, ListPanel listPanel, AnswerListPanel ansListPanel) {
			this.panel = panel;
			this.ansListPanel = ansListPanel;
			this.listPanel = listPanel;
			cardLayout = (CardLayout) (panel.getLayout());
		}

		public void actionPerformed(ActionEvent e) {
			for (int i = 0; i < listPanel.getTestButtons().size(); i++) {
				if (listPanel.getTestButtons().get(i).getModel().isArmed()) {
					ansListPanel.updateTestID(testID.get(i));
					ansListPanel.updateTitle("My Tests");

					ansListPanel.repaintPanel();

					cardLayout.show(panel, "listQPanel");

				}
			}
		}
	}

	public void createDeleteButton() {
		deleteBtn = new JButton("<html><u>Delete Test</u></html>");
		deleteBtn.setFont(new Font("Times New Roman", Font.BOLD, 18));
		deleteBtn.setForeground(Color.gray);
		deleteBtn.setBorder(null);
		deleteBtn.setContentAreaFilled(false);
		deleteBtn.setToolTipText("顯示刪除按鈕");

		class ClickListener implements ActionListener {
			int click = 0;

			public void actionPerformed(ActionEvent e) {
				if (click % 2 == 0) {
					for (int i = 0; i < testButtons.size(); i++) {
						deleteBtns.get(i).setVisible(true);
						testButtons.get(i).setEnabled(false);
					}
					deleteBtn.setToolTipText("返回目錄頁並重新整理");
					click++;
				} else if (click % 2 == 1) {
					for (int i = 0; i < testButtons.size(); i++) {
						deleteBtns.get(i).setVisible(false);
						testButtons.get(i).setEnabled(true);
					}
					deleteBtn.setToolTipText("顯示刪除按鈕");
					click++;

					for (int j = 1; j < delete.size(); j++) {
						int index = delete.get(j);
						deleteBtns.remove(index);
						testButtons.remove(index);
					}

					repaintPanel();
				}
			}
		}
		ClickListener listener = new ClickListener();
		deleteBtn.addActionListener(listener);
	}

	public void addRoundDeleteListener() {
		class ClickListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				delete = new ArrayList<Integer>();
				try {
					for (int i = 0; i < deleteBtns.size(); i++) {
						if (deleteBtns.get(i).getModel().isArmed()) {
							deleteBtns.get(i).setBgColor(Color.decode("#CED6DF"), Color.decode("#CED6DF"));

							Statement stat = conn.createStatement();
							String query = "DELETE FROM UserQuestion WHERE UserID = '" + LoginPanel.getUserID()
									+ "' AND TestID = '" + testID.get(i) + "'";
							String query2 = "DELETE FROM AllTests WHERE UserID = '" + LoginPanel.getUserID()
									+ "' AND TestID = '" + testID.get(i) + "'";
							stat.executeUpdate(query);
							stat.executeUpdate(query2);

							delete.add(i);
						}
					}
				} catch (Exception ex) {
					System.out.printf("<ListPanel> roundDeleteBtn listener: %s\n", ex.getMessage());
				}
			}
		}
		ClickListener listener = new ClickListener();
		for (int i = 0; i < deleteBtns.size(); i++) {
			deleteBtns.get(i).addActionListener(listener);
		}
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
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.insets = new Insets(60, 0, 0, 50);
		gbc.anchor = GridBagConstraints.EAST;
		add(deleteBtn, gbc);

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
		gbc.gridy = 2;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.insets = new Insets(0, 0, 20, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.SOUTH;
		add(homeButton, gbc);
	}

	public void repaintPanel() {
		testID = new ArrayList<Integer>();
		testButtons.clear();
		deleteBtns.clear();
		menuPanel.removeAll();

		removeAll();
		updateMenuPanel();
		validate();
		setLayout();
	}
}
