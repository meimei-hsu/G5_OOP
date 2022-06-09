import javax.swing.*;
import javax.swing.border.LineBorder;

import style.*;

import java.awt.event.*;
import java.awt.*;

public class SubjectPanel extends JPanel {
	private JPanel subjectPanel, down_toolPanel;
	private JLabel titleLabel;
	private JButton homeButton, backButton;
	private JButton subjectButtonChi, subjectButtonEn, subjectButtonMa, subjectButtonSo, subjectButtonSc,
			subjectButtonBi, subjectButtonChe, subjectButtonPh, subjectButtonGe, subjectButtonHi, subjectButtonCi;
	private QTool qTool;
	private ImageIcon backIcon;

	public SubjectPanel() {
		createTitleLabel();
		createSubjectBtn();
		createBackButton();
		createToolPanel();
		setBtnPanel();
		setLayout();
	}

	public QTool getQTool() {
		return this.qTool;
	}

	public void createTitleLabel() {
		ImageIcon noteIcon = new ImageIcon(
				new ImageIcon("images/note.png").getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT));
		titleLabel = new JLabel("Note", noteIcon, SwingConstants.LEFT);
		titleLabel.setFont(new Font("Lucida Handwriting", Font.BOLD, 40));
		titleLabel.setVerticalTextPosition(SwingConstants.CENTER);
		titleLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
		titleLabel.setForeground(new Color(139, 69, 19));
	}

	public void createSubjectBtn() {
		subjectPanel = new JPanel(new GridBagLayout());
		subjectPanel.setBackground(Color.decode("#F8EFD4"));

		subjectButtonChi = new RoundButton("國文", 3, 3);
		subjectButtonEn = new RoundButton("英文", 3, 3);
		subjectButtonMa = new RoundButton("數學", 3, 3);
		subjectButtonSo = new RoundButton("社會", 3, 3);
		subjectButtonSc = new RoundButton("自然", 3, 3);
		subjectButtonHi = new RoundButton("歷史", 3, 3);
		subjectButtonGe = new RoundButton("地理", 3, 3);
		subjectButtonCi = new RoundButton("公民", 3, 3);
		subjectButtonPh = new RoundButton("物理", 3, 3);
		subjectButtonChe = new RoundButton("化學", 3, 3);
		subjectButtonBi = new RoundButton("生物", 3, 3);
		JButton[] subjectBtns = { subjectButtonChi, subjectButtonEn, subjectButtonMa, subjectButtonSo, subjectButtonSc,
				subjectButtonHi, subjectButtonGe, subjectButtonCi, subjectButtonPh, subjectButtonChe, subjectButtonBi };

		for (int i = 0; i < 11; i++) {
			subjectBtns[i].setFont(new Font("微軟正黑體", Font.PLAIN, 40));
			subjectBtns[i].setPreferredSize(new Dimension(120, 120));
		}

		class SocietyBtnListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				backButton.setIcon(backIcon);
				backButton.setEnabled(true);
				setSocietyPanel();
				repaintPanel();
			}
		}
		SocietyBtnListener listenerSo = new SocietyBtnListener();
		subjectButtonSo.addActionListener(listenerSo);

		class ScienceBtnListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				backButton.setIcon(backIcon);
				backButton.setEnabled(true);
				setSciencePanel();
				repaintPanel();
			}
		}
		ScienceBtnListener listenerSc = new ScienceBtnListener();
		subjectButtonSc.addActionListener(listenerSc);
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
		
		class ClickListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				backButton.setIcon(null);
				backButton.setEnabled(false);
				setBtnPanel();
				repaintPanel();
			}
		}
		ClickListener listener = new ClickListener();
		backButton.addActionListener(listener);
	}

	public void repaintPanel() {
		removeAll();
		setLayout();
		validate();
		repaint();
	}

	public void setBtnPanel() {
		subjectPanel.removeAll();
		backButton.setIcon(null);
		backButton.setEnabled(false);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		subjectPanel.add(subjectButtonChi, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		subjectPanel.add(subjectButtonMa, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 4;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		subjectPanel.add(subjectButtonEn, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.insets = new Insets(20, 0, 0, 0);
		subjectPanel.add(subjectButtonSo, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 3;
		gbc.gridy = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.insets = new Insets(20, 0, 0, 0);
		subjectPanel.add(subjectButtonSc, gbc);
	}

	public void setSocietyPanel() {
		subjectPanel.removeAll();

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.insets = new Insets(0, 20, 20, 20);
		gbc.anchor = GridBagConstraints.CENTER;
		subjectPanel.add(subjectButtonGe, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.anchor = GridBagConstraints.CENTER;
		subjectPanel.add(subjectButtonHi, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 2;
		gbc.gridy = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.anchor = GridBagConstraints.CENTER;
		subjectPanel.add(subjectButtonCi, gbc);
	}

	public void setSciencePanel() {
		subjectPanel.removeAll();

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.anchor = GridBagConstraints.CENTER;
		subjectPanel.add(subjectButtonPh, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.anchor = GridBagConstraints.CENTER;
		subjectPanel.add(subjectButtonChe, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.insets = new Insets(20, 20, 0, 20);
		gbc.anchor = GridBagConstraints.CENTER;
		subjectPanel.add(subjectButtonBi, gbc);
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
		// gbc.insets = new Insets(15, 0, 0, 0);
		gbc.insets = new Insets(20, 0, 0, 0);
		gbc.anchor = GridBagConstraints.PAGE_START;
		add(titleLabel, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.insets = new Insets(0, 30, 0, 30);
		gbc.anchor = GridBagConstraints.CENTER;
		add(subjectPanel, gbc);

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

	public void addButtonListener(JPanel panel, AnswerListPanel ansListPanel, AnswerKeyPanel ansKeyPanel) {
		CardLayout card = (CardLayout) (panel.getLayout());

		class ChineseListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				Viewer.addNoteListener();
				ansListPanel.updateTitle("Chinese");
				ansListPanel.repaintPanel();
				card.show(panel, "noteQPanel");
			}
		}
		ChineseListener listenerChi = new ChineseListener();
		subjectButtonChi.addActionListener(listenerChi);

		class EnglishListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				Viewer.addNoteListener();
				ansListPanel.updateTitle("English");
				ansListPanel.repaintPanel();
				card.show(panel, "noteQPanel");
			}
		}
		ActionListener listenerEn = new EnglishListener();
		subjectButtonEn.addActionListener(listenerEn);

		class MathListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				Viewer.addNoteListener();
				ansListPanel.updateTitle("Math");
				ansListPanel.repaintPanel();
				card.show(panel, "noteQPanel");
			}
		}
		ActionListener listenerMa = new MathListener();
		subjectButtonMa.addActionListener(listenerMa);

		class HistoryListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				Viewer.addNoteListener();
				ansListPanel.updateTitle("History");
				ansListPanel.repaintPanel();
				card.show(panel, "noteQPanel");
			}
		}
		ActionListener listenerHi = new HistoryListener();
		subjectButtonHi.addActionListener(listenerHi);

		class GeographyListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				Viewer.addNoteListener();
				ansListPanel.updateTitle("Geography");
				ansListPanel.repaintPanel();
				card.show(panel, "noteQPanel");
			}
		}
		ActionListener listenerGe = new GeographyListener();
		subjectButtonGe.addActionListener(listenerGe);

		class CitizenListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				Viewer.addNoteListener();
				ansListPanel.updateTitle("Civics");
				ansListPanel.repaintPanel();
				card.show(panel, "noteQPanel");
			}
		}
		ActionListener listenerCi = new CitizenListener();
		subjectButtonCi.addActionListener(listenerCi);

		class PhysicListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				Viewer.addNoteListener();
				ansListPanel.updateTitle("Physics");
				ansListPanel.repaintPanel();
				card.show(panel, "noteQPanel");
			}
		}
		ActionListener listenerPh = new PhysicListener();
		subjectButtonPh.addActionListener(listenerPh);

		class ChemistryListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				Viewer.addNoteListener();
				ansListPanel.updateTitle("Chemistry");
				ansListPanel.repaintPanel();
				card.show(panel, "noteQPanel");
			}
		}
		ActionListener listenerChe = new ChemistryListener();
		subjectButtonChe.addActionListener(listenerChe);

		class BiologyListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				Viewer.addNoteListener();
				ansListPanel.updateTitle("Biology");
				ansListPanel.repaintPanel();
				card.show(panel, "noteQPanel");
			}
		}
		ActionListener listenerBi = new BiologyListener();
		subjectButtonBi.addActionListener(listenerBi);
	}
}
