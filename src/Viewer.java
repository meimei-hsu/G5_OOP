import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class Viewer {

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		CardLayout cardLayout1 = new CardLayout();
		JPanel login_cardPanel = new JPanel(cardLayout1);
		CardLayout cardLayout2 = new CardLayout();
		JPanel question_cardPanel = new JPanel(cardLayout2);

		SignUpPanel signUpPanel = new SignUpPanel();
		LoginPanel loginPanel = new LoginPanel();
		HomePanel homePanel = new HomePanel();
		signUpPanel.addLoginListener(login_cardPanel);
		signUpPanel.addSignUpListener(login_cardPanel);
		loginPanel.addLoginListener(login_cardPanel);
		loginPanel.addSignUpListener(login_cardPanel);
		
		RangePanel rangePanel = new RangePanel();
		InstructionPanel instructionPanel = new InstructionPanel();
		QuestionPanel questionPanel = new QuestionPanel();
		QToolPanel qToolPanel = new QToolPanel();
		questionPanel.getQTool().addMoreButtonListener(question_cardPanel, questionPanel);
		questionPanel.getQTool().addFinishButtonListener(question_cardPanel);
		qToolPanel.getQTool().addMoreButtonListener(question_cardPanel, qToolPanel);
		qToolPanel.getQTool().addFinishButtonListener(question_cardPanel);
		questionPanel.addQNumListener(question_cardPanel, qToolPanel);
		
		login_cardPanel.add(loginPanel, "1");
		login_cardPanel.add(signUpPanel, "2");
		login_cardPanel.add(homePanel, "3");
		
		question_cardPanel.add(questionPanel, "1");
		question_cardPanel.add(qToolPanel, "2");
		
		cardLayout1.show(login_cardPanel, "1");
		cardLayout2.show(question_cardPanel, "1");

		frame.add(question_cardPanel);
		frame.setTitle("College Entrance Examination");
		frame.setSize(800, 500);
		//frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
 
}
