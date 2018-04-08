package view.score;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import util.CallBack;
import util.Constant;
import view.ShowMessage;
import dao.BanJiDao;
import dao.BjSubjectDao;
import dao.ScoreDao;
import dao.StudentDao;
import dao.SubjectDao;
import entity.BanJi;
import entity.Score;
import entity.Student;
import entity.Subject;

public class ScoreView {
	private static ScoreView instance;
	List<Score> list = new ArrayList<Score>();
	List<BanJi> bjList;
	List<Subject> subList;
	Set<Score> saveSet = new HashSet<Score>();

	ScoreTableModel model = null;
	JTable table;
	
	ScoreDao scDao = new ScoreDao();
	BanJiDao bjDao=new  BanJiDao();
	SubjectDao subDao=new SubjectDao();
	BjSubjectDao bsdDao=new BjSubjectDao();

	JTextField nameText;
//	JTextField subText;
	JComboBox bjBox;
	JComboBox subBox;
	

	JFrame frame;

	
	
	private ScoreView() {

	}

	public static ScoreView getInstance() {

		if (instance == null) {
			instance = new ScoreView();
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

//			bjBox.setSelectedIndex(0);
			refreshTable();
			frame.setVisible(true);	
		}

	}

	public void init() {

		list = scDao.searchAll();

		frame.setSize(Constant.STU_VIEW_WIDTH, Constant.STU_VIEW_HEIGHT);
		frame.setLocationRelativeTo(null);
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
		// 学生查询
		JLabel nameLabel = new JLabel();
		nameLabel.setText(Constant.STU_NAME);
		panel1.add(nameLabel);

		nameText = new JTextField();
		nameText.setPreferredSize(new Dimension(100, 30));
		panel1.add(nameText);
		
		// 班级查询

		JLabel bjLabel = new JLabel();
		bjLabel.setText(Constant.BJ);
		panel1.add(bjLabel);
		
		bjBox = new JComboBox();
		bjList = bjDao.searchAll();
		bjBox.addItem(Constant.BJ_PROMPT_SELECT);

		for (BanJi bj : bjList) {

			bjBox.addItem(bj.getName());
		}


		bjBox.setPreferredSize(new Dimension(100, 30));
		bjBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = -1;
				if (bjBox.getSelectedIndex() > 0) {
					index = bjBox.getSelectedIndex() - 1;
					subList = bsdDao.searchByBjId(bjList.get(index).getId());
				} else {

					subList = subDao.searchAll();

				}
				refreshSubBox(subList);
			}
		});
		panel1.add(bjBox);
	
		// 课程查询
		JLabel sexLabel = new JLabel();
		sexLabel.setText(Constant.SUB);
		panel1.add(sexLabel);

		subBox = new JComboBox();

		subList = subDao.searchAll();
		refreshSubBox(subList);
		subBox.setPreferredSize(new Dimension(100, 30));
		panel1.add(subBox);




		JButton searchBtn = new JButton();
		searchBtn.setText(Constant.MES_SEARCH);
		searchBtn.setPreferredSize(new Dimension(90, 30));
		searchBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Score condition = new Score();

				Student stu = new Student();
				stu.setName(nameText.getText());

				BanJi bj = new BanJi();
				int index = bjBox.getSelectedIndex();
				int bjId = -1;
				if (index > 0) {
					bjId = bjList.get(index - 1).getId();
				}
				bj.setId(bjId);
				stu.setBj(bj);

				Subject sub = new Subject();
				index = subBox.getSelectedIndex();
				int subId = -1;
				if (index > 0) {
					subId = subList.get(index - 1).getId();
				}
				sub.setId(subId);

				condition.setStudent(stu);
				condition.setSubject(sub);
				list = scDao.searchByCondition(condition);
				refreshTable(list);
			}
		});
		panel1.add(searchBtn);

		table = new JTable();
		//table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
		model = new ScoreTableModel(list,saveSet);
		table.setModel(model);
		JScrollPane scroll = new JScrollPane(table);
		scroll.setPreferredSize(new Dimension(600, 400));
		panel2.add(scroll);

		JButton saveBtn = new JButton();
		saveBtn.setText("保存");
		saveBtn.setPreferredSize(new Dimension(90, 30));
		panel3.add(saveBtn);

		saveBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean flag = scDao.save(saveSet);
				// saveList.clear();
				saveSet.clear();
				ShowMessage.show(flag, Constant.MES_SAVE);
				refreshTable();
			}
		});
		frame.setVisible(true);
	}

	
	
	private void refreshSubBox(List<Subject> subList) {
		subBox.removeAllItems();
		subBox.addItem(Constant.SUB_PROMPT_SELECT);
		for (Subject sub : subList) {
			subBox.addItem(sub.getName());
		}
		
	}

	public void refreshTable() {
		list = scDao.searchAll();
		model.setData(list);
		model.fireTableDataChanged();
	}

	public void refreshTable(List<Score> list) {
		model.setData(list);
		model.fireTableDataChanged();
	}

}
