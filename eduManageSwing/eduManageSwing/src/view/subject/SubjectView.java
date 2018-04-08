package view.subject;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

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
import dao.SubjectDao;
import entity.Subject;

public class SubjectView {

	List<Subject> list = new ArrayList<Subject>();

	SubjectTableModel model = null;
	JTable table;
	SubjectDao subDao = new SubjectDao();
	JTextField nameText;
	JTextField idText;
	JFrame frame;
	private static SubjectView instance;
	private SubjectView(){}
	public static SubjectView getInstance()
	{
		if(instance==null)
		{
			instance=new SubjectView();
		}
		return instance;
	}
	
	public void createFrame()
	{
		if (frame == null) {
			frame = new JFrame();
			init();
		} else {
			refreshTable();
			frame.setVisible(true);
		}
		
	}
	
	public void init() {

		list = subDao.searchAll();
		
		frame.setSize(700, 600);
		frame.setLocationRelativeTo(null);
		frame.setTitle(Constant.SUB_TITLE);

		JPanel mainPanel = (JPanel) frame.getContentPane();
		BoxLayout boxLayout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
		mainPanel.setLayout(boxLayout);

		JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel(new FlowLayout(FlowLayout.CENTER, 100, 10));
		mainPanel.add(panel1);
		mainPanel.add(panel2);
		mainPanel.add(panel3);
		// id查询
		JLabel idLabel = new JLabel();
		idLabel.setText("id");
		panel1.add(idLabel);

		idText = new JTextField();
		idText.setPreferredSize(new Dimension(120, 30));
		panel1.add(idText);

		// 名称
		JLabel nameLabel = new JLabel();
		nameLabel.setText("课程名称");
		panel1.add(nameLabel);

		nameText = new JTextField();
		nameText.setPreferredSize(new Dimension(120, 30));
		panel1.add(nameText);

		JButton searchBtn = new JButton();
		searchBtn.setText(Constant.MES_SEARCH);
		searchBtn.setPreferredSize(new Dimension(90, 30));
		searchBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String name = nameText.getText();
				int id = -1;
				if (!idText.getText().equals("")) {
					id = Integer.parseInt(idText.getText());
				}

				Subject condition = new Subject();
				condition.setName(name);
				condition.setId(id);
				list = subDao.searchByCondition(condition);
				refreshTable(list);
			}
		});
		panel1.add(searchBtn);

		table = new JTable();
		model = new SubjectTableModel(list);
		table.setModel(model);
		JScrollPane scroll = new JScrollPane(table);
		scroll.setPreferredSize(new Dimension(600, 400));
		panel2.add(scroll);

		JButton addBtn = new JButton();
		addBtn.setText(Constant.MES_ADD);
		addBtn.setPreferredSize(new Dimension(90, 30));
		panel3.add(addBtn);

		addBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				AddSubjectView asv = AddSubjectView.getInstance();

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
					ModifySubjectView msv = ModifySubjectView.getInstance();
					Subject sub = list.get(index);

					msv.createFrame(sub.getId(), new CallBack() {
						@Override
						public void call() {
							refreshTable();
						}
					});
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
						Subject sub = list.get(index);
						boolean flag =subDao.delete(sub.getId());
						// boolean flag = bjDao.save(list);
						ShowMessage.show(flag, Constant.MES_DELETE);
						refreshTable();
					}
				}
			}
		});
		frame.setVisible(true);

	}

	public void refreshTable() {
		list = subDao.searchAll();
		model.setData(list);
		model.fireTableDataChanged();
	}

	public void refreshTable(List<Subject> list) {
		model.setData(list);
		model.fireTableDataChanged();
	}


}
