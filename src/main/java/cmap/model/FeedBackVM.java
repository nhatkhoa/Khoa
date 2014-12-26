package cmap.model;

import java.io.Serializable;
import java.util.ArrayList;

public class FeedBackVM implements Serializable {

	private static final long serialVersionUID = 8956338618596270858L;
	
	private int id;
	private String topic;
	private String info;
	private int score;

	private ArrayList<FeedConceptVM> nodeDataArray = new ArrayList<FeedConceptVM>(0);
	private ArrayList<FeedRelationVM> linkDataArray = new ArrayList<FeedRelationVM>(0);
	public FeedBackVM(int id, String topic, String info, int score) {
		super();
		this.id = id;
		this.topic = topic;
		this.info = info;
		this.score = score;
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
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public ArrayList<FeedConceptVM> getNodeDataArray() {
		return nodeDataArray;
	}
	public void setNodeDataArray(ArrayList<FeedConceptVM> nodeDataArray) {
		this.nodeDataArray = nodeDataArray;
	}
	public ArrayList<FeedRelationVM> getLinkDataArray() {
		return linkDataArray;
	}
	public void setLinkDataArray(ArrayList<FeedRelationVM> linkDataArray) {
		this.linkDataArray = linkDataArray;
	}

	

}
