package view.student;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import util.CallBack;
import util.Constant;
import view.ShowMessage;
import dao.BanJiDao;
import dao.StudentDao;
import entity.BanJi;
import entity.Student;

public class StudentView {
	private static StudentView instance;
	List<Student> list = new ArrayList<Student>();

	StudentTableModel model = null;
	JTable table;
	StudentDao stuDao = new StudentDao();
	BanJiDao bjDao = new BanJiDao();

	JTextField nameText;
	JTextField sexText;
	JTextField ageText;
	JComboBox bjBox;
	List<BanJi> bjList;

	JFrame frame;

	private StudentView() {

	}

	public static StudentView getInstance() {

		if (instance == null) {
			instance = new StudentView();
		}
		return instance;
	}

	public void createFrame() {

		if (frame == null) {
			frame = new JFrame();
			init();
		} else {
			bjList.clear();
			bjList = bjDao.searchAll();
			bjBox.removeAllItems();
			bjBox.addItem(Constant.BJ_PROMPT_SELECT);
			for (BanJi bj : bjList) {
				bjBox.addItem(bj.getName());
			}
			bjBox.addItem(Constant.BJ_NO_SET);

			bjBox.setSelectedIndex(0);
			refreshTable();
			frame.setVisible(true);
			

		}

	}

	public void init() {

		list = stuDao.searchAll();

		frame.setSize(Constant.STU_VIEW_WIDTH, Constant.STU_VIEW_HEIGHT);
		frame.setLocationRelativeTo(null);
		// frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setTitle(Constant.STU_TITLE);

		JPanel mainPanel = (JPanel) frame.getContentPane();
		BoxLayout boxLayout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
		mainPanel.setLayout(boxLayout);

		JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 10));
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel(new FlowLayout(FlowLayout.CENTER, 100, 10));
		mainPanel.add(panel1);
		mainPanel.add(panel2);
		mainPanel.add(panel3);
		// 姓名查询
		JLabel nameLabel = new JLabel();
		nameLabel.setText(Constant.STU_NAME);
		panel1.add(nameLabel);

		nameText = new JTextField();
		nameText.setPreferredSize(new Dimension(100, 30));
		panel1.add(nameText);
		// 性别查询
		JLabel sexLabel = new JLabel();
		sexLabel.setText(Constant.STU_SEX);
		panel1.add(sexLabel);

		sexText = new JTextField();
		sexText.setPreferredSize(new Dimension(100, 30));
		panel1.add(sexText);
		// 年龄查询
		JLabel ageLabel = new JLabel();
		ageLabel.setText(Constant.STU_AGE);
		panel1.add(ageLabel);

		ageText = new JTextField();
		ageText.setPreferredSize(new Dimension(100, 30));
		panel1.add(ageText);

		JLabel bjLabel = new JLabel();
		bjLabel.setText(Constant.BJ);
		panel1.add(bjLabel);

		bjBox = new JComboBox();
		bjList = bjDao.searchAll();
		bjBox.addItem(Constant.BJ_PROMPT_SELECT);

		for (BanJi bj : bjList) {

			bjBox.addItem(bj.getName());
		}
		bjBox.addItem(Constant.BJ_NO_SET);

		bjBox.setPreferredSize(new Dimension(100, 30));
		panel1.add(bjBox);

		JButton searchBtn = new JButton();
		searchBtn.setText(Constant.MES_SEARCH);
		searchBtn.setPreferredSize(new Dimension(90, 30));
		searchBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String name = nameText.getText();
				String sex = sexText.getText();
				int age = -1;
				if (!ageText.getText().equals("")) {
					age = Integer.parseInt(ageText.getText());
				}

				Student condition = new Student();
				condition.setName(name);
				condition.setSex(sex);
				condition.setAge(age);

				int index = bjBox.getSelectedIndex();
				BanJi bj = new BanJi();
				if (index > 0) {
					if (index <= bjList.size()) {
						bj = bjList.get(index - 1);
					}else{
						bj.setId(-1);
					}
				}

				condition.setBj(bj);
				list = stuDao.searchByCondition(condition);
				refreshTable(list);
			}
		});
		panel1.add(searchBtn);

		table = new JTable();
		model = new StudentTableModel(list);
		table.setModel(model);
		JScrollPane scroll = new JScrollPane(table);
		scroll.setPreferredSize(new Dimension(600, 400));
		// scroll.setViewportView(table);
		panel2.add(scroll);

		JButton addBtn = new JButton();
		addBtn.setText(Constant.MES_ADD);
		addBtn.setPreferredSize(new Dimension(90, 30));
		panel3.add(addBtn);

		addBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				AddStudentView asv = AddStudentView.getInstance();

				asv.createFrame(new CallBack() {
					@Override
					public void call() {
						refreshTable();
					}
				});

			}
		});

		JButton modifyBtn = new JButton();
		modifyBtn.setText(Constant.MES_MODIFY);
		modifyBtn.setPreferredSize(new Dimension(90, 30));
		panel3.add(modifyBtn);
		modifyBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				int index = table.getSelectedRow();
				if (index == -1) {
					JOptionPane.showMessageDialog(frame,
							Constant.MES_PROMPT_SELECT);

				} else {
					ModifyStudentView msv = ModifyStudentView.getInstance();
					Student stu = list.get(index);

					msv.createFrame(stu.getId(), new CallBack() {
						@Override
						public void call() {
							refreshTable();
						}

					});
					// msv.init();
				}
			}
		});
		JButton deleteBtn = new JButton();
		deleteBtn.setText(Constant.MES_DELETE);
		deleteBtn.setPreferredSize(new Dimension(90, 30));
		panel3.add(deleteBtn);
		deleteBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				int index = table.getSelectedRow();
				if (index == -1) {
					JOptionPane.showMessageDialog(null,
							Constant.MES_PROMPT_SELECT);
				} else {
					int type = JOptionPane.showConfirmDialog(frame,
							Constant.MES_PROMPT_DELETE, Constant.MES_DELETE,
							JOptionPane.YES_NO_OPTION);
					if (type == 0) {
						Student stu = list.get(index);
						boolean flag = stuDao.delete(stu.getId());
						ShowMessage.show(flag, Constant.MES_DELETE);
						refreshTable();
					}

				}

			}

		});
		frame.setVisible(true);

	}

	public void refreshTable() {
		list = stuDao.searchAll();
		model.setData(list);
		model.fireTableDataChanged();
	}

	public void refreshTable(List<Student> list) {
		model.setData(list);
		model.fireTableDataChanged();
	}

}
