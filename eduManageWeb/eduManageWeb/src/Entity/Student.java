package Entity;

import java.io.Serializable;

public class Student{
	private int id;
	private String name;
	private String sex;
	private int age;
	private BanJi banji;
	private String photo;
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
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public BanJi getBanji() {
		return banji;
	}
	public void setBanji(BanJi banji) {
		this.banji = banji;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
}
