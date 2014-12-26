package cmap.model;

import java.io.Serializable;

public class AssignPost implements Serializable {

	private static final long serialVersionUID = 7200633966877279409L;
	
	private String topic;
	private String info;
	private String deadline;
	private int cmap_id;
	
	public AssignPost(){
		
	}
	public AssignPost(String topic, String info, String deadline, int cmap_id) {
		super();
		this.topic = topic;
		this.info = info;
		this.deadline = deadline;
		this.cmap_id = cmap_id;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public int getCmap_id() {
		return cmap_id;
	}

	public void setCmap_id(int cmap_id) {
		this.cmap_id = cmap_id;
	}
	
	
}
