package view.banji;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
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
import dao.BjSubjectDao;
import entity.BanJi;
import entity.Subject;

public class BjSubjectView {
	
	List<Subject> list = new ArrayList<Subject>();
	JLabel bjLabel;
	SubjectTableModel model = null;
	JTable table;
	BjSubjectDao BSDao = new BjSubjectDao();
	JFrame frame;
	JComboBox bjBox;
	List <String> subList=new ArrayList<>();
	private static BjSubjectView instance;
	BanJi bj;
	JScrollPane scroll;
	private BjSubjectView(){}
	public static BjSubjectView getInstance()
	{
		if(instance==null)
		{
			instance=new BjSubjectView();
		}
		return instance;
	}
	
	public void createFrame(BanJi bj)
	{
		this.bj=bj;
		System.out.println(bj.getId());
		System.out.println(bj.getName());
		
		if (frame == null) {
			frame = new JFrame();
			init();
			subList.clear();
			subList = BSDao.searchUnSelectSubject(bj);
			bjBox.removeAllItems();
			for (String str : subList) {
				bjBox.addItem(str);
			}			
			//frame=null;
		} else {
			bjLabel.setText(bj.getName());
			bjLabel.setFont(new Font(null, 1,40 ));
			model=new SubjectTableModel();
			list = BSDao.search(bj.getId());
			model.setData(list);
			table.setModel(model);
			subList.clear();
			subList = BSDao.searchUnSelectSubject(bj);
			bjBox.removeAllItems();
			for (String str : subList) {
				bjBox.addItem(str);
			}			
			bjBox.setSelectedIndex(0);
					
			frame.setVisible(true);
		}
	}
	
	public void init() {

		list = BSDao.search(bj.getId());
		
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
		
		
		bjLabel = new JLabel();
		bjLabel.setFont(new Font(null, 1,40 ));
		bjLabel.setText(bj.getName());
		panel1.add(bjLabel);

		
		table = new JTable();
		model = new SubjectTableModel(list);
		table.setModel(model);
		scroll = new JScrollPane(table);
		scroll.setPreferredSize(new Dimension(600, 400));
		panel2.add(scroll);

		bjBox = new JComboBox();
		subList = BSDao.searchUnSelectSubject(bj);

		for (String str : subList) {

			bjBox.addItem(str);
		}

		bjBox.setPreferredSize(new Dimension(120, 30));
		panel3.add(bjBox);
		
		JButton addBtn = new JButton();
		addBtn.setText(Constant.MES_ADD);
		addBtn.setPreferredSize(new Dimension(90, 30));
		panel3.add(addBtn);

		addBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String name = (String)(bjBox.getSelectedItem());
				Subject sub = new Subject();
	
				boolean rs = BSDao.add(name,bj);

				ShowMessage.show(rs, Constant.MES_ADD);
				
				refreshTable();

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
						System.out.println(sub.getId());
						System.out.println(sub.getName());
						boolean flag =BSDao.delete(sub.getId(),bj.getId());
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
		list = BSDao.search(bj.getId());
		subList.clear();
		subList = BSDao.searchUnSelectSubject(bj);
		bjBox.removeAllItems();
		for (String str : subList) {
			bjBox.addItem(str);
		}
		if(subList.size()!=0)
		{bjBox.setSelectedIndex(0);}
		model.setData(list);
		model.fireTableDataChanged();
	}

	public void refreshTable(List<Subject> list) {
		model.setData(list);
		model.fireTableDataChanged();
	}


}
