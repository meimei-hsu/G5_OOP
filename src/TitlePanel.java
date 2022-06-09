import java.awt.BorderLayout;
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

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class TitlePanel extends JPanel {
	private JButton signUpButton, loginButton;
	
	public TitlePanel() {
		createButton();
		setLayout();
	}
	
	public void createButton() {
		signUpButton = new JButton("<html><u>SignUp</u></html>");
		signUpButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
		signUpButton.setForeground(Color.gray);
		signUpButton.setBorder(null);
		signUpButton.setContentAreaFilled(false);
		
		loginButton = new JButton("<html><u>Login</u></html>");
		loginButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
		loginButton.setForeground(Color.gray);
		loginButton.setBorder(null);
		loginButton.setContentAreaFilled(false);
	}
	
	public void addButtonListener(JPanel panel) {
		class ClickListener1 implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				CardLayout card = (CardLayout) (panel.getLayout());
				card.show(panel, "signUpPanel");
			}
		}
		ClickListener1 listener1 = new ClickListener1();
		signUpButton.addActionListener(listener1);
		
		class ClickListener2 implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				CardLayout card = (CardLayout) (panel.getLayout());
				card.show(panel, "loginPanel");
			}
		}
		ClickListener2 listener2 = new ClickListener2();
		loginButton.addActionListener(listener2);
	}
	
	public void setLayout() {
		ImageIcon bgIcon = new ImageIcon(
				new ImageIcon("images/bg1.png").getImage().getScaledInstance(900, 670, Image.SCALE_DEFAULT));
		JLabel imgLabel = new JLabel(bgIcon, SwingConstants.CENTER);
		JPanel imgPanel = new JPanel(new BorderLayout());
		imgPanel.setBackground(Color.white);
		imgPanel.setBorder(null);
		imgPanel.setOpaque(false);
		imgPanel.add(imgLabel, BorderLayout.CENTER);
		
		JPanel btnPanel = new JPanel();
		btnPanel.setBackground(Color.decode("#FAF0AF"));
		btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.X_AXIS));
		btnPanel.add(Box.createRigidArea(new Dimension(70, 0)));
		btnPanel.add(signUpButton);
		btnPanel.add(Box.createRigidArea(new Dimension(270, 0)));
		btnPanel.add(loginButton);
		btnPanel.add(Box.createRigidArea(new Dimension(70, 0)));
		
		
		setSize(900, 700);
		setBackground(Color.white);
		
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 0, 170, 0);
		gbc.anchor = GridBagConstraints.SOUTH;
		add(btnPanel, gbc);
		
		gbc = new GridBagConstraints();
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 0, 50, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.fill = GridBagConstraints.BOTH;
		add(imgPanel, gbc);
		
		
	}
}





