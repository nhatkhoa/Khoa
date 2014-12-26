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
	// --- Dành cho chấm điểm : mặc định -1 là chưa chấm
	private int pass;

	// --- Concept đầu
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "from_concept")
	private Concept from;

	// --- Concept sau
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "to_concept")
	private Concept to;

	// --- CMap
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
		this.pass = -1;
	}

	// --- Hàm so compare một cặp concept với nhau
	public int isPass(Relation rela) {
		// --- Lấy tên quan hệ bài làm
		String title = rela.getTitle();

		// --- Nếu tên quan hệ giống nhau thì tiếp tục so sánh 2 concept
		if (this.title.contains(title)) {
			// --- Nếu cả 2 concept đều trùng thì trả về 1 (đúng hoàn toàn)
			if (this.getFrom().equals(rela.getFrom())
					&& this.getTo().equals(rela.getTo()))
				return 3;
			// --- Nếu không thì trả về 0 (sai hoàn toàn)
			return 0;
		}

		// --- Nếu cả 2 concept đều trùng thì trả về 2 (Chỉ đúng 2 concept)
		if (this.getFrom().equals(rela.getFrom())
				&& this.getTo().equals(rela.getTo()))
			return 2;
		// --- Mặc định không trùng điều gì thì trả về 0
		return 0;
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

	public int getPass() {
		return pass;
	}

	public void setPass(int pass) {
		this.pass = pass;
	}

}
