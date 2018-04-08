package Entity;

import java.util.List;

public class BanJi {
	private int id;
	private String name;
	private int nums;
	private List<Subject> subs;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNums() {
		return nums;
	}
	public void setNums(int nums) {
		this.nums = nums;
	}
	public List<Subject> getSubs() {
		return subs;
	}
	public void setSubs(List<Subject> subs) {
		this.subs = subs;
	}
}
