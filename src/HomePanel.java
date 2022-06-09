import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.CardLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class HomePanel extends JPanel {
	private JLabel imgLabel;
	private JButton userButton;
	private JButton testButton;
	private JButton noteButton;
	private JButton listButton;
	private JPanel operatePanel;

	public HomePanel() {
		createComp();
	}

	public void createComp() {
		ImageIcon userIcon = new ImageIcon(
				new ImageIcon("images/user.png").getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT));
		userButton = new JButton();
		userButton.setIcon(userIcon);
		userButton.setBorder(null);
		userButton.setContentAreaFilled(false);
		userButton.setToolTipText("登出");
		
		ImageIcon homeIcon = new ImageIcon(
				new ImageIcon("images/home.png").getImage().getScaledInstance(180, 180, Image.SCALE_DEFAULT));
		imgLabel = new JLabel("Home", homeIcon, SwingConstants.CENTER);
		imgLabel.setVerticalTextPosition(SwingConstants.BOTTOM);
		imgLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		imgLabel.setFont(new Font("Lucida Handwriting", Font.BOLD, 50));
		imgLabel.setForeground(Color.decode("#FDA172"));

		ImageIcon testIcon = new ImageIcon(
				new ImageIcon("images/test.png").getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT));
		testButton = new JButton("Test", testIcon);
		testButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
		testButton.setPreferredSize(new Dimension(120, 120));
		testButton.setBorder(BorderFactory.createMatteBorder(7, 1, 1, 1, Color.decode("#E88D67")));
		testButton.setVerticalTextPosition(SwingConstants.TOP);
		testButton.setHorizontalTextPosition(SwingConstants.CENTER);
		testButton.setContentAreaFilled(false);
		
		ImageIcon noteIcon = new ImageIcon(
				new ImageIcon("images/note.png").getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT));
		noteButton = new JButton("Note", noteIcon);
		noteButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
		noteButton.setPreferredSize(new Dimension(120, 120));
		noteButton.setBorder(BorderFactory.createMatteBorder(7, 1, 1, 1, Color.decode("#E88D67")));
		noteButton.setVerticalTextPosition(SwingConstants.TOP);
		noteButton.setHorizontalTextPosition(SwingConstants.CENTER);
		noteButton.setContentAreaFilled(false);

		ImageIcon listIcon = new ImageIcon(
				new ImageIcon("images/list.png").getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT));
		listButton = new JButton("List", listIcon);
		listButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
		listButton.setPreferredSize(new Dimension(120, 120));
		listButton.setBorder(BorderFactory.createMatteBorder(7, 1, 1, 1, Color.decode("#E88D67")));
		listButton.setVerticalTextPosition(SwingConstants.TOP);
		listButton.setHorizontalTextPosition(SwingConstants.CENTER);
		listButton.setContentAreaFilled(false);

		operatePanel = new JPanel(new GridBagLayout());
		operatePanel.setBackground(Color.decode("#F8EFD4"));
		GridBagConstraints gbc1 = new GridBagConstraints();
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		gbc1.weightx = 1.0;
		gbc1.weighty = 1.0;
		operatePanel.add(testButton, gbc1);

		gbc1 = new GridBagConstraints();
		gbc1.gridx = 2;
		gbc1.gridy = 0;
		gbc1.weightx = 1.0;
		gbc1.weighty = 1.0;
		operatePanel.add(noteButton, gbc1);

		gbc1 = new GridBagConstraints();
		gbc1.gridx = 1;
		gbc1.gridy = 1;
		gbc1.weightx = 1.0;
		gbc1.weighty = 1.0;
		gbc1.insets = new Insets(50, 30, 0, 30);
		operatePanel.add(listButton, gbc1);

		setBackground(Color.decode("#F8EFD4"));
		setLayout(new GridBagLayout());
		GridBagConstraints gbc2 = new GridBagConstraints();
		gbc2.gridx = 0;
		gbc2.gridy = 0;
		gbc2.weightx = 1.0;
		gbc2.weighty = 1.0;
		gbc2.insets = new Insets(15, 15, 0, 0);
		gbc2.anchor = GridBagConstraints.NORTHWEST;
		add(userButton, gbc2);
		
		gbc2 = new GridBagConstraints();
		gbc2.gridx = 0;
		gbc2.gridy = 1;
		gbc2.weightx = 1.0;
		gbc2.weighty = 1.0;
		gbc2.insets = new Insets(0, 10, 130, 0);
		gbc2.anchor = GridBagConstraints.CENTER;
		add(imgLabel, gbc2);

		gbc2 = new GridBagConstraints();
		gbc2.gridx = 1;
		gbc2.gridy = 1;
		gbc2.weightx = 1.0;
		gbc2.weighty = 1.0;
		gbc2.insets = new Insets(0, 0, 130, 10);
		gbc2.anchor = GridBagConstraints.CENTER;
		add(operatePanel, gbc2);
	}
	
	public void addButtonListener(JPanel panel, ListPanel list) {
		class UserListener implements ActionListener {
			CardLayout cardLayout = (CardLayout) (panel.getLayout());
			public void actionPerformed(ActionEvent e) {
				int input = JOptionPane.showConfirmDialog(null, "是否確定登出?", "Confirm Message", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (input == 0) {
					cardLayout.show(panel, "titlePanel");
				}
			}
		}
		UserListener userListener = new UserListener();
		userButton.addActionListener(userListener);
		
		class TestListener implements ActionListener {
			CardLayout cardLayout = (CardLayout) (panel.getLayout());
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(panel, "rangePanel");
			}
		}
		TestListener testListener = new TestListener();
		testButton.addActionListener(testListener);
		
		class NoteListener implements ActionListener {
			CardLayout cardLayout = (CardLayout) (panel.getLayout());
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(panel, "subjectPanel");
			}
		}
		NoteListener noteListener = new NoteListener();
		noteButton.addActionListener(noteListener);
		
		class ListListener implements ActionListener {
			CardLayout cardLayout = (CardLayout) (panel.getLayout());
			public void actionPerformed(ActionEvent e) {
				list.repaintPanel();
				Viewer.addListListener();
				cardLayout.show(panel, "listPanel");
			}
		}
		ListListener listListener = new ListListener();
		listButton.addActionListener(listListener);
	}
}
