package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import util.Constant;
import view.banji.BanJiView;
import view.score.ScoreView;
import view.student.StudentView;
import view.subject.SubjectView;

public class MainView extends JFrame {

	public void init() {
		this.setSize(400, 300);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		JPanel mainPanel = (JPanel) this.getContentPane();
		mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
		JButton stuButton = new JButton(Constant.STU_TITLE);
		stuButton.setPreferredSize(new Dimension(150, 80));
		stuButton.setFont(new Font(null, Font.BOLD, 25));
		JButton bjButton = new JButton(Constant.BJ_TITLE);
		bjButton.setPreferredSize(new Dimension(150, 80));
		bjButton.setFont(new Font(null, Font.BOLD, 25));

		JButton subButton = new JButton(Constant.SUB_TITLE);
		subButton.setPreferredSize(new Dimension(150, 80));
		subButton.setFont(new Font(null, Font.BOLD, 25));

		JButton scButton = new JButton(Constant.SC_TITLE);
		scButton.setPreferredSize(new Dimension(150, 80));
		scButton.setFont(new Font(null, Font.BOLD, 25));

		mainPanel.add(stuButton);
		mainPanel.add(bjButton);
		mainPanel.add(subButton);
		mainPanel.add(scButton);

		stuButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				StudentView sv = StudentView.getInstance();
				sv.createFrame();
			}
		});
		
		
		bjButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				BanJiView bv = BanJiView.getInstance();
				bv.createFrame();
			}
		});
		
		subButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SubjectView sv=SubjectView.getInstance();
				sv.createFrame();
			}
		});
		 
		scButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ScoreView sv=ScoreView.getInstance();
				sv.createFrame();
			}
		});
		
		this.setVisible(true);

	}

	public static void main(String[] args) {

		new MainView().init();
	}
}
