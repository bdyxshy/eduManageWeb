package view.banji;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.callback.Callback;
import javax.swing.BoxLayout;
import javax.swing.JButton;
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
import view.student.StudentView;
import dao.BanJiDao;
import dao.BjSubjectDao;
import entity.BanJi;

public class BanJiView {

	List<BanJi> list = new ArrayList<BanJi>();

	BanJiTableModel model = null;
	JTable table;
	BanJiDao bjDao = new BanJiDao();
	JTextField nameText;
	JTextField stuNumsText;
	JFrame frame;
	private static BanJiView instance;
	private BanJiView(){}
	public static BanJiView getInstance() {

		if (instance == null) {
			instance = new BanJiView();
		}
		return instance;
	}
	public void createFrame() {

		if (frame == null) {
			frame = new JFrame();
			init();
		} else {
			refreshTable();
			frame.setVisible(true);
		}

	}
	public void init() {

		list = bjDao.searchAll();

		//frame = new JFrame();
		frame.setSize(700, 600);
		frame.setLocationRelativeTo(null);
		// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle(Constant.BJ_TITLE);

		JPanel mainPanel = (JPanel) frame.getContentPane();
		BoxLayout boxLayout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
		mainPanel.setLayout(boxLayout);

		JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
		mainPanel.add(panel1);
		mainPanel.add(panel2);
		mainPanel.add(panel3);
		// 姓名查询
		JLabel nameLabel = new JLabel();
		nameLabel.setText(Constant.BJ_ID);
		panel1.add(nameLabel);

		nameText = new JTextField();
		nameText.setPreferredSize(new Dimension(120, 30));
		panel1.add(nameText);

		// 班级人数查询
		JLabel ageLabel = new JLabel();
		ageLabel.setText(Constant.BJ_NAME);
		panel1.add(ageLabel);

		stuNumsText = new JTextField();
		stuNumsText.setPreferredSize(new Dimension(120, 30));
		panel1.add(stuNumsText);

		JButton searchBtn = new JButton();
		searchBtn.setText(Constant.MES_SEARCH);
		searchBtn.setPreferredSize(new Dimension(90, 30));
		searchBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String name = nameText.getText();
				int stuNums = -1;
				if(stuNumsText.getText().equals("0"))
				{
					stuNums=0;
				}
				if (!stuNumsText.getText().equals("")) {
					stuNums = Integer.parseInt(stuNumsText.getText());
				}

				BanJi condition = new BanJi();
				condition.setName(name);
				condition.setStuNums(stuNums);

				list = bjDao.searchByCondition(condition);
				refreshTable(list);
			}
		});
		panel1.add(searchBtn);

		table = new JTable();
		model = new BanJiTableModel(list);
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
				AddBanJiView asv = AddBanJiView.getInstance();
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
					ModifyBanJiView msv = ModifyBanJiView.getInstance();
					BanJi bj = list.get(index);

					msv.createFrame(bj.getId(), new CallBack() {
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
						BanJi bj = list.get(index);
						boolean flag = bjDao.delete(bj.getId());
						ShowMessage.show(flag, Constant.MES_DELETE);
						refreshTable();
					}
				}
			}
		});
		
		JButton bjsubjectBtn = new JButton();
		bjsubjectBtn.setText(Constant.SUB_ADD_TITLE);
		bjsubjectBtn.setPreferredSize(new Dimension(110, 30));
		panel3.add(bjsubjectBtn);
		bjsubjectBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				int index = table.getSelectedRow();
				if (index == -1) {
					JOptionPane.showMessageDialog(null,
							Constant.MES_PROMPT_SELECT);
				} else {
					BanJi bj=new BanJi();
					bj=list.get(index);
					System.out.println(bj.getId());
					System.out.println(bj.getName());//===========================================================
					BjSubjectView bsv=BjSubjectView.getInstance();
					bsv.createFrame(bj);
				}
			}
		});
		frame.setVisible(true);
	}

	public void refreshTable() {
		list = bjDao.searchAll();
		model.setData(list);
		model.fireTableDataChanged();
	}

	public void refreshTable(List<BanJi> list) {
		model.setData(list);
		model.fireTableDataChanged();
	}


}
