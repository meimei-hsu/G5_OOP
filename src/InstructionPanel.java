import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.*;
import javax.swing.border.Border;

public class InstructionPanel extends JPanel {
	private static final int FRAME_WIDTH = 400;
	private static final int FRAME_HEIGHT = 400;

	private JButton startButton;
	private JLabel yearANDtype, subject, notice;
	private JPanel titlePanel, noticePanel;

	public InstructionPanel() {
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		creaLabelComp();
		creaStartButton();
		creaTotalPanel();
	}

	public void creaLabelComp() {
		yearANDtype = new JLabel("大學入學考試中心 - 109學年度指定科目考試試題");
		subject = new JLabel("公民考科");
		notice = new JLabel("\n-作答注意事項-\n考試時間：\n題型題數：\n-第壹部分：\n第貳部分\n作答方式");

		titlePanel = new JPanel();
		titlePanel.add(yearANDtype);
		titlePanel.add(subject);

		Border border = BorderFactory.createLineBorder(Color.BLACK);
		noticePanel = new JPanel();
		noticePanel.add(notice);
		noticePanel.setBorder(border);

	}

	public void creaStartButton() {
		startButton = new JButton("Start");

	}

	public void creaTotalPanel() {
		setLayout(new BorderLayout());
		add(titlePanel, BorderLayout.NORTH);
		add(noticePanel, BorderLayout.CENTER);
		add(startButton, BorderLayout.SOUTH);

	}
}
