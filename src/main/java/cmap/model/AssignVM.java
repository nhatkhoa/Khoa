package cmap.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AssignVM implements Serializable{

	private static final long serialVersionUID = -8666016132082830976L;
	private int id;
	private String topic;
	private String deadline;
	private String info;
	private int teacher_id;
	private String teacher_name;
	private int score;
	private String date_submit;
	private int submited;
	private int total;
	private int users;
	
	
	private SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
	public AssignVM(int id, String topic, Date deadline, String info,
			int teacher_id, String teacher_name, int score, Date date_submit,
			int submited, int total, int users) {
		super();
		this.id = id;
		this.topic = topic;
		this.deadline = date.format(deadline);
		this.info = info;
		this.teacher_id = teacher_id;
		this.teacher_name = teacher_name;
		this.score = score;
		this.date_submit = "Chưa nộp";
		this.submited = submited;
		this.total = total;
		this.users = users;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public String getDeadline() {
		return deadline;
	}
	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public int getTeacher_id() {
		return teacher_id;
	}
	public void setTeacher_id(int teacher_id) {
		this.teacher_id = teacher_id;
	}
	public String getTeacher_name() {
		return teacher_name;
	}
	public void setTeacher_name(String teacher_name) {
		this.teacher_name = teacher_name;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public String getDate_submit() {
		return date_submit;
	}
	public void setDate_submit(String date_submit) {
		this.date_submit = date_submit;
	}
	public int getSubmited() {
		return submited;
	}
	public void setSubmited(int submited) {
		this.submited = submited;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}

	public int getUsers() {
		return users;
	}

	public void setUsers(int users) {
		this.users = users;
	}
	
	
	
	
}
