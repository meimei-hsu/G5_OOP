import java.awt.CardLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class Viewer {
	private static JPanel card;
	private static TitlePanel titlePanel;
	private static SignUpPanel signUpPanel;
	private static LoginPanel loginPanel;
	private static HomePanel homePanel;
	private static RangePanel rangePanel;
	private static InstructionPanel instructionPanel;
	private static ToAnswerPanel toAnsPanel;
	private static QuestionPanel questionPanel;
	private static AnswerKeyPanel answerKeyPanel;
	private static AnswerListPanel ansListPanel;
	private static AnswerKeyPanel noteKeyPanel;
	private static AnswerListPanel noteQPanel;
	private static SubjectPanel subjectPanel;
	private static ListPanel listPanel;
	private static AnswerListPanel listQPanel;
	private static AnswerKeyPanel listKeyPanel;

	public static void main(String[] args) throws SQLException {
		JFrame frame = new JFrame();
		CardLayout cardLayout = new CardLayout();
		card = new JPanel(cardLayout);

		titlePanel = new TitlePanel();
		signUpPanel = new SignUpPanel();
		loginPanel = new LoginPanel();
		homePanel = new HomePanel();
		
		rangePanel = new RangePanel();
		instructionPanel = new InstructionPanel();

		toAnsPanel = new ToAnswerPanel();
		questionPanel = new QuestionPanel();

		answerKeyPanel = new AnswerKeyPanel();
		answerKeyPanel.setName("answerKeyPanel");
		ansListPanel = new AnswerListPanel();
		ansListPanel.setName("ansListPanel");

		noteKeyPanel = new AnswerKeyPanel();
		noteKeyPanel.setName("noteKeyPanel");
		noteQPanel = new AnswerListPanel();
		noteQPanel.setName("noteQPanel");
		subjectPanel = new SubjectPanel();
		
		listPanel = new ListPanel();
		listQPanel = new AnswerListPanel();
		listQPanel.setName("listQPanel");
		listKeyPanel = new AnswerKeyPanel();
		listKeyPanel.setName("listKeyPanel");
		
		titlePanel.addButtonListener(card);
		signUpPanel.addLoginListener(card);
		signUpPanel.addSignUpListener(card);
		loginPanel.addLoginListener(card);
		loginPanel.addSignUpListener(card);
		
		homePanel.addButtonListener(card, listPanel);
		
		rangePanel.createStartBtn(card, instructionPanel, questionPanel);
		rangePanel.addBackListener(card);
		instructionPanel.addButtonListener(card);

		toAnsPanel.addAnswerListener(card, ansListPanel);
		
		subjectPanel.addButtonListener(card, noteQPanel, noteKeyPanel);
		subjectPanel.getQTool().addHomeButtonListener(card, subjectPanel);
		
		listPanel.addListButtonListener(card, listPanel, listQPanel);
		listPanel.getQTool().addHomeButtonListener(card);
		
		card.add(titlePanel, "titlePanel");
		card.add(loginPanel, "loginPanel");
		card.add(signUpPanel, "signUpPanel");
		card.add(homePanel, "homePanel");

		card.add(rangePanel, "rangePanel");
		card.add(instructionPanel, "instructionPanel");
		card.add(questionPanel, "questionPanel");
		card.add(toAnsPanel, "toAnsPanel");

		card.add(ansListPanel, "ansListPanel");
		card.add(answerKeyPanel, "answerKeyPanel");

		card.add(subjectPanel, "subjectPanel");
		card.add(noteQPanel, "noteQPanel");
		card.add(noteKeyPanel, "noteKeyPanel");
		
		card.add(listPanel, "listPanel");
		card.add(listQPanel, "listQPanel");
		card.add(listKeyPanel, "listKeyPanel");

		cardLayout.show(card, "titlePanel");
		frame.add(card);
		frame.setTitle("College Entrance Examination");
		// frame.setSize(1060, 650);
		frame.setSize(900, 700);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
	
	public static void destroy(JPanel panel) {
		Component[] comps = panel.getComponents();
		for (Component c : comps) {
			if (c instanceof JPanel) {
				destroy((JPanel)c);
			} else {
				panel.remove(c);
			}
		}
	}
	
	public static void addQuestionListener() {
		questionPanel.getQTool().addQuestionPanelListener(questionPanel);
		questionPanel.getQTool().addFinishButtonListener(card, questionPanel, toAnsPanel);
	}
	
	public static void addAnswerListener() {
		answerKeyPanel.getQTool().addMoreButtonListener(card, answerKeyPanel);
		ansListPanel.addQuestionButtonListener(card, ansListPanel, answerKeyPanel);
		ansListPanel.getQTool().addHomeButtonListener(card);
	}
	
	public static void addNoteListener() {
		noteQPanel.addQuestionButtonListener(card, noteQPanel, noteKeyPanel);
		noteQPanel.addBackListener(card);
		noteQPanel.getQTool().addHomeButtonListener(card, subjectPanel);
		noteKeyPanel.getQTool().addMoreButtonListener(card, noteKeyPanel);
		
	}
	
	public static void addListListener() {
		listQPanel.addQuestionButtonListener(card, listQPanel, listKeyPanel);
		listQPanel.addBackListener(card);
		listQPanel.getQTool().addHomeButtonListener(card);
		listKeyPanel.getQTool().addMoreButtonListener(card, listKeyPanel);
		
	}

}
