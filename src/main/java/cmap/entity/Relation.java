package cmap.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "relation")
public class Relation {

	@Id
	@GeneratedValue
	private int id;
	private String title;

	// --- Mapping
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "from_concept")
	private Concept from;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "to_concept")
	private Concept to;

	@ManyToOne(fetch = FetchType.EAGER)
	private CMap cmap;

	// Constructor mặc định
	public Relation() {

	}
	


	public Relation(Concept from, Concept to, String title, CMap cmap) {
		this.title = title;
		this.cmap = cmap;
		this.from = from;
		this.to = to;
	}


	@Override
	public boolean equals(Object obj) {
		// --- Nếu obj là đối tượng Relation
		if(obj.getClass().equals(this.getClass())){
			// --- Cast đối tượng obj thành Relation
			Relation temp = (Relation) obj;
			// --- Nếu không trùng tiêu đề liên kết trả về false
			if(!this.title.contains(temp.getTitle()))
				return false;
			// --- Kiểm tra tên concept đầu
			if(this.from.getName().contains(temp.getFrom().getName()))
				return false;
			// --- Kiểm tra tên concept sau
			if(this.to.getName().contains(temp.getTo().getName()))
				return false;
		}
		return super.equals(obj);
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Concept getFrom() {
		return from;
	}

	public void setFrom(Concept from) {
		this.from = from;
	}

	public Concept getTo() {
		return to;
	}

	public void setTo(Concept to) {
		this.to = to;
	}

	public CMap getCmap() {
		return cmap;
	}

	public void setCmap(CMap cmap) {
		this.cmap = cmap;
	}

	
}
