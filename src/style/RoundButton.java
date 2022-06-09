package style;

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.event.*;

public class RoundButton extends JButton {
	private Color background;
	private Color bgClicked;
	private Color border;
	private int thickness;
	private int broken;
	
	public RoundButton(String label) {
		super(label);
		this.background = Color.decode("#F8EFD4");
		this.bgClicked = Color.decode("#F5DEB3");
		this.border = Color.decode("#E88D67");
		this.thickness = 1;
		this.broken = 0;
		//setBackground(Color.lightGray);
		setFocusable(false);

		/*
		 * These statements enlarge the button so that it becomes a circle rather than an oval.
		 */
		Dimension size = getPreferredSize();
		size.width = size.height = Math.max(size.width, size.height);
		setPreferredSize(size);

		/*
		 * This call causes the JButton not to paint the background. 
		 * This allows us to paint a round background.
		 */
		setContentAreaFilled(false);
	}
	
	public RoundButton(String label, Color background, Color border, Color bgClicked) {
		super(label);
		this.background = background;
		this.border = border;
		this.bgClicked = bgClicked;
		setFocusable(false);
		Dimension size = getPreferredSize();
		size.width = size.height = Math.max(size.width, size.height);
		setPreferredSize(size);
		setContentAreaFilled(false);
	}
	
	public RoundButton (String label, int thickness, int broken) {
		super(label);
		this.background = Color.decode("#F8EFD4");
		this.bgClicked = Color.decode("#F5DEB3");
		this.border = Color.decode("#E88D67");
		this.thickness = thickness;
		this.broken = broken;
		setFocusable(false);

		Dimension size = getPreferredSize();
		size.width = size.height = Math.max(size.width, size.height);
		setPreferredSize(size);
		setContentAreaFilled(false);
	}

	protected void paintComponent(Graphics g) {
		if (getModel().isArmed()) {
			g.setColor(bgClicked);
		} else {
			g.setColor(background);
		}
		g.fillOval(0, 0, getSize().width - 1, getSize().height - 1);

		super.paintComponent(g);
	}
	
	public void setBgColor(Color background, Color bgClicked) {
		this.background = background;
		this.bgClicked = bgClicked;
	}

	protected void paintBorder(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(thickness));
        g2.setColor(border);
		g2.drawOval(broken, broken, getSize().width - 1, getSize().height - 1);
	}
	
	public void setBorderColor(Color border, Color bgClicked) {
		this.border = border;
		this.bgClicked = bgClicked;
	}

	// Hit detection.
	Shape shape;

	public boolean contains(int x, int y) {
		// If the button has changed size, make a new shape object.
		if (shape == null || !shape.getBounds().equals(getBounds())) {
			shape = new Ellipse2D.Float(0, 0, getWidth(), getHeight());
		}
		return shape.contains(x, y);
	}
}
/*	
	public static void main(String[] args) {

		JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame frame = new JFrame("Rounded Button Example");
		frame.setLayout(new FlowLayout());

		JButton b1 = new RoundButton("B1");
		JButton b2 = new RoundButton("B2");

		frame.add(b1);
		frame.add(b2);

		frame.setSize(300, 150);
		frame.setVisible(true);
	}
*/
