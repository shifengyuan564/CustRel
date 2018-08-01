package top.meem.cache;

import java.util.Date;

public class Cache {
	private String val;
	private Long date;
	private Long maxInactiveInterval;
	
	public Cache(){
		
	}
	
	public Cache(String val, Long maxInactiveInterval) {
		this.val=val;
		this.date= new Date().getTime();
		this.maxInactiveInterval = maxInactiveInterval;
	}
	public String getVal() {
		return val;
	}
	public void setVal(String val) {
		this.val = val;
	}
	public Long getDate() {
		return date;
	}
	public void setDate(Long date) {
		this.date = date;
	}

	public void setMaxInactiveInterval(Long maxInactiveInterval) {
		this.maxInactiveInterval = maxInactiveInterval;
	}

	public Long getMaxInactiveInterval() {
		return maxInactiveInterval;
	}
	
	
	
}
