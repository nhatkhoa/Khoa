package cmap.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FeedBackVM implements Serializable {

	private static final long serialVersionUID = 8956338618596270858L;
	
	private int id;
	private String topic;
	private String info;
	private int score;
	private int keyConcept;
	private int keyRelation;
	private int concept;
	private int relation;
	private String date_submit;

	private SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
	
	private ArrayList<FeedConceptVM> nodeDataArray = new ArrayList<FeedConceptVM>(0);
	private ArrayList<FeedRelationVM> linkDataArray = new ArrayList<FeedRelationVM>(0);
	
	
	public FeedBackVM(int id, String topic, String info, int score,
			int keyConcept, int keyRelation, int concept, int relation,
			Date date_submit) {
		super();
		this.id = id;
		this.topic = topic;
		this.info = info;
		this.score = score;
		this.keyConcept = keyConcept;
		this.keyRelation = keyRelation;
		this.concept = concept;
		this.relation = relation;
		this.date_submit = date.format(date_submit);
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
	public int getKeyConcept() {
		return keyConcept;
	}

	public void setKeyConcept(int keyConcept) {
		this.keyConcept = keyConcept;
	}

	public int getKeyRelation() {
		return keyRelation;
	}

	public void setKeyRelation(int keyRelation) {
		this.keyRelation = keyRelation;
	}

	public int getConcept() {
		return concept;
	}

	public void setConcept(int concept) {
		this.concept = concept;
	}

	public int getRelation() {
		return relation;
	}

	public void setRelation(int relation) {
		this.relation = relation;
	}

	public String getDate_submit() {
		return date_submit;
	}

	public void setDate_submit(String date_submit) {
		this.date_submit = date_submit;
	}

	public SimpleDateFormat getDate() {
		return date;
	}

	public void setDate(SimpleDateFormat date) {
		this.date = date;
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
