package util;

public class Pagination {
	private int yeNum;
	private int begin;
	private int end;
	private int ye;
	private int maxYe;
	private int yeMa;//ÏÔÊ¾¼¸¸öÒ³Âë1-5£¬1-10
	
	public Pagination(int ye,int max,int yeNum,int yeMa){
		this.ye=ye;
		this.yeNum=yeNum;
		this.yeMa=yeMa;
		cal(max);
	}
	public void cal(int max){
		if(ye<1){
			ye=1;
		}
		this.maxYe=max%yeNum==0?max/yeNum:max/yeNum+1;
		if(ye>maxYe&&maxYe!=0){
			ye=maxYe;
		}
		begin=ye-yeMa/2;
		  end=ye+yeMa/2;
		  if(begin<1){
			  begin=1;
			  end=yeMa;
		  }
		  if(end>maxYe){
			  end=maxYe;
			  begin=end-yeMa+1;
		  }
		  if(maxYe<yeMa){
			  end=maxYe;
			  begin=1;
		  }
	}
	public int getPageNum() {
		return yeNum;
	}
	public void setPageNum(int pageNum) {
		this.yeNum = pageNum;
	}
	public int getBegin() {
		return begin;
	}
	public void setBegin(int begin) {
		this.begin = begin;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	public int getYe() {
		return ye;
	}
	public void setYe(int ye) {
		this.ye = ye;
	}
	public int getMaxYe() {
		return maxYe;
	}
	public void setMaxYe(int maxYe) {
		this.maxYe = maxYe;
	}
	public int getYeMa() {
		return yeMa;
	}
	public void setYeMa(int yeMa) {
		this.yeMa = yeMa;
	}
	
}
