package view.score;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.table.AbstractTableModel;

import util.Constant;
import entity.Score;
import entity.Student;

public class ScoreTableModel extends AbstractTableModel {
	List<Score> list = null;
	Set<Score> saveSet=null;
	String[] columnNames = { Constant.STU_NAME,Constant.BJ ,
			"课程名称","分数"};

	public ScoreTableModel(List<Score> list,Set<Score> saveSet) {
		this.list = list;
		this.saveSet=saveSet;
	}

	@Override
	public int getRowCount() {
		return list.size();
	}

	@Override
	public String getColumnName(int columnIndex) {

		return columnNames[columnIndex];

	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}
	
	public void setValueAt(Object obj,int rowIndex, int columnIndex)
	{

		list.get(rowIndex).setScore(Integer.parseInt((String) obj));
		saveSet.add(list.get(rowIndex));
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (columnIndex == 0) {
			
			return list.get(rowIndex).getStudent().getName();
		} else if (columnIndex == 1) {
			
			return list.get(rowIndex).getStudent().getBj().getName();
		} else if (columnIndex == 2) {
		
			return list.get(rowIndex).getSubject().getName();
		} else if (columnIndex == 3) {
			
			if (list.get(rowIndex).getScore() == -1) {
				return "";
			}
			return list.get(rowIndex).getScore();
			
		} else {
			return null;
		}
	}

	public void setData(List<Score> list) {
		this.list = list;
	}
	
    public boolean isCellEditable(int rowIndex, int columnIndex) {
    	if(columnIndex==3)
    	{
    		return true;
    	}
    	else 
    	{
    		return false;
    	}
        
    }
}
