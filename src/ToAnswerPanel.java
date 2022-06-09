import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import style.BubbleBorder;

import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

public class ToAnswerPanel extends JPanel {
	private JLabel CiLabel, Score, Time, Congratulations, ScoreImage, TimeImage;
	private JButton AnswerButton;
	private JPanel AnswerPanel;
	private String subject;
	
	public ToAnswerPanel() {
		createComp();
		createAnswerPanel();
		setLayout();
	}
	
	public void createComp() {
		CiLabel = new JLabel();
		Font font1 = new Font("微軟正黑體", Font.BOLD, 30);
		CiLabel.setFont(font1);
		CiLabel.setBackground(new Color(255, 239, 213));

		Congratulations = new JLabel("Congratulations !!");
		Dimension preferredSize = new Dimension(300, 100);
		Font font = new Font("微軟正黑體", Font.ITALIC, 20);
		Color color = new Color(139, 69, 19);
		Congratulations.setForeground(color);
		Congratulations.setFont(font);
		Congratulations.setPreferredSize(preferredSize);
		Congratulations.setBackground(new Color(255, 239, 213));
		
		Score = new JLabel();
		Score.setForeground(new Color(139, 69, 19));
		Score.setFont(new Font("微軟正黑體", Font.PLAIN, 40));
		Score.setHorizontalAlignment(SwingConstants.CENTER);
		Score.setPreferredSize(new Dimension(300, 100));
		Score.setBackground(new Color(255, 239, 213));
		
		Time = new JLabel();
		Time.setForeground(new Color(139, 69, 19));
		Time.setFont(new Font("微軟正黑體", Font.PLAIN, 40));
		Time.setHorizontalAlignment(SwingConstants.CENTER);
		Time.setPreferredSize(new Dimension(300, 100));
		Time.setBackground(new Color(255, 239, 213));
		
		AnswerButton = new JButton("Answer");
		AnswerButton.setFont(font1);
		AnswerButton.setPreferredSize(new Dimension(200, 40));
		AnswerButton.setContentAreaFilled(true);
		AnswerButton.setBorder(new BubbleBorder(color, 2, 30, 0));
		AnswerButton.setBackground(Color.decode("#F8EFD4"));
		AnswerButton.setForeground(color);
		
		ImageIcon score = new ImageIcon(
				new ImageIcon("images/score.png").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
		ScoreImage = new JLabel(score);
		ImageIcon time = new ImageIcon(
				new ImageIcon("images/time.png").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
		TimeImage = new JLabel(time);
	}

	public void addAnswerListener(JPanel panel, AnswerListPanel ansListPanel) {
		class ClickListener implements ActionListener {
			CardLayout card = (CardLayout) (panel.getLayout());

			public void actionPerformed(ActionEvent e) {
				ansListPanel.initialize();
				ansListPanel.updateTitle("Answer");
				ansListPanel.repaintPanel();
				Viewer.addAnswerListener();
				
				card.show(panel, "ansListPanel");
			}
		}
		ActionListener listener = new ClickListener();
		AnswerButton.addActionListener(listener);
	}
	
	public void updateScore(String score) {
		Score.setText(String.format("%s", score));
	}
	
	public void updateTime(String time) {
		Time.setText(String.format("%s", time));
	}
	
	public void updateSubject(String subject) {
		CiLabel.setText(subject + " - Advanced Subjects Text");
	}
	
	public void createAnswerPanel() {
		AnswerPanel = new JPanel(new GridBagLayout());
		AnswerPanel.setBackground(Color.decode("#F8EFD4"));
		AnswerPanel.setBorder(new BubbleBorder(Color.decode("#8B4513"), 2, 30, 0));
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 10, 0, 0);
		AnswerPanel.add(Congratulations, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		//gbc1.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.LINE_END;
		AnswerPanel.add(Score, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.insets = new Insets(50, 0, 0, 0);
		gbc.anchor = GridBagConstraints.LINE_END;
		AnswerPanel.add(Time, gbc);
		
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.insets = new Insets(0, 30, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		AnswerPanel.add(ScoreImage, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.insets = new Insets(50, 30, 0, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		AnswerPanel.add(TimeImage, gbc);
	}

	public void setLayout() {
		setBackground(Color.decode("#F8EFD4"));
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.insets = new Insets(50, 0, 0, 0);
		gbc.anchor = GridBagConstraints.PAGE_START;
		add(CiLabel, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.insets = new Insets(0, 0, 50, 0);
		gbc.anchor = GridBagConstraints.CENTER;
		add(AnswerPanel, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.insets = new Insets(0, 0, 50, 0);
		gbc.anchor = GridBagConstraints.PAGE_END;
		add(AnswerButton, gbc);
	}
}
