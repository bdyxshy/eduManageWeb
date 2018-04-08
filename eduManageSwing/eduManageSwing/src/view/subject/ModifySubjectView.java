package view.subject;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import util.CallBack;
import util.Constant;
import view.ShowMessage;
import dao.SubjectDao;
import entity.Subject;

public class ModifySubjectView {
	JTextField nameText;

	JFrame frame;
	CallBack callBack;
	Subject sub;
	SubjectDao subDao = new SubjectDao();

	private static ModifySubjectView instance;

	public static ModifySubjectView getInstance() {

		if (instance == null) {
			instance = new ModifySubjectView();
		}

		return instance;
	}

	private ModifySubjectView() {

	}

	public void createFrame(int id, CallBack callBack) {

		this.callBack = callBack;
		this.sub = subDao.searchById(id);
		if (frame == null) {
			frame = new JFrame();
			init();
		} else {
			frame.setVisible(true);
		}
		setTexts(sub);
	}

	public void setTexts(Subject sub) {
		this.sub=sub;
		nameText.setText(sub.getName());

	}

	public void init() {

		// frame = new JFrame();
		frame.setSize(Constant.SUB_MODIFY_WIDTH, Constant.SUB_MODIFY_HEIGHT);
		frame.setLocationRelativeTo(null);
		frame.setTitle(Constant.SUB_MODIFY_TITLE);

		JPanel mainPanel = (JPanel) frame.getContentPane();
		BoxLayout boxLayout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
		mainPanel.setLayout(boxLayout);

		JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		//JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		//JPanel panel3 = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		JPanel panel4 = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

		mainPanel.add(panel1);
		//mainPanel.add(panel2);
		//mainPanel.add(panel3);
		mainPanel.add(panel4);
		// ÐÕÃû
		JLabel nameLabel = new JLabel();
		nameLabel.setText(Constant.SUB_NAME);
		panel1.add(nameLabel);

		nameText = new JTextField();
		nameText.setPreferredSize(new Dimension(120, 30));
		nameText.setText(sub.getName());
		panel1.add(nameText);

		JButton saveBtn = new JButton();
		saveBtn.setText(Constant.MES_SAVE);
		saveBtn.setPreferredSize(new Dimension(90, 30));
		panel4.add(saveBtn);
		saveBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String name = nameText.getText();
				sub.setName(name);

				boolean flag = subDao.update(sub);
				ShowMessage.show(flag, Constant.MES_MODIFY);

				frame.dispose();
				callBack.call();

			}
		});
		frame.setVisible(true);

	}

}
