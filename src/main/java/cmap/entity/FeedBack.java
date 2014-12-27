package cmap.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "feedback")
public class FeedBack {

	@Id
	@GeneratedValue
	private int id;
	private int score;
	private Date date_create;

	// --- Mapping
	@ManyToOne(fetch = FetchType.EAGER)
	private Assign assign;

	@ManyToOne(fetch = FetchType.EAGER)
	private CMap cmap;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "failed", joinColumns = { @JoinColumn(name = "concept_id") }, inverseJoinColumns = { @JoinColumn(name = "feedback_id") })
	private Set<Concept> faileds = new HashSet<Concept>(0);

	public FeedBack() {

	}

	public FeedBack(int score, Date date_create, Assign assign,
			CMap cmap) {
		this.score = score;
		this.date_create = date_create;
		this.assign = assign;
		this.cmap = cmap;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Date getDate_create() {
		return date_create;
	}

	public void setDate_create(Date date_create) {
		this.date_create = date_create;
	}

	public Assign getAssign() {
		return assign;
	}

	public void setAssign(Assign assign) {
		this.assign = assign;
	}

	public CMap getCmap() {
		return cmap;
	}

	public void setCmap(CMap cmap) {
		this.cmap = cmap;
	}

	public Set<Concept> getFaileds() {
		return faileds;
	}

	public void setFaileds(Set<Concept> faileds) {
		this.faileds = faileds;
	}

}
