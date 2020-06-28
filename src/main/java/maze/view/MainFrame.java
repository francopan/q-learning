package maze.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Label;

import javax.swing.JFrame;

public class MainFrame {
	
	public MainFrame() {
		JFrame frame = new JFrame("FrameDemo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Label l = new Label();
		//l.setText("Teste");
		//frame.getContentPane().add(l, BorderLayout.CENTER);
		MapFrame mp = new MapFrame(20, 12);
		frame.add(mp);
		frame.setSize(new Dimension(400,300));
		frame.setVisible(true);
	}
}
