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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "cmap")
public class CMap {

	@Id
	@GeneratedValue
	private int id;
	private String title;
	private String info;
	private Date date_create;

	// ---- Người tạo concept map này
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "member_id")
	private Member author;

	// --- Fecth : Kiểu load - EAGER : Load đồng thời với CMAP
	// --- cascade.ALL - orphanRemoval : Kiểu lan truyền, khi xóa cmap thì tự động xóa tất cả concept, relation,....
	// --- Danh sách các concept chứa trong nó
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "cmap")
	private Set<Concept> concepts = new HashSet<Concept>(0);

	// --- Danh sách các quan hệ của các concept trên
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "cmap")
	private Set<Relation> relations = new HashSet<Relation>(0);

	// --- Danh sách các member được share 
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "shares")
	private Set<Member> members = new HashSet<Member>(0);
	
	// --- Danh sách feedback đối với cmap này
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true, mappedBy = "cmap")
	private Set<FeedBack> feedbacks = new HashSet<FeedBack>(0);

	public CMap(){
		
	}
	public CMap(String title, String info, Member author) {
		super();
		this.title = title;
		this.info = info;
		this.author = author;
		this.date_create = new Date();
	}

	// --- Kiểm tra quyền sở hữu của một member đối với cmap này
	// --- Trả về 0 : Không có quyền gì trên cmap này
	// --- Trả về 1 : Chủ sở hữu
	// --- Trả về 2 : Người được share
	public int ownership(String username) {
		if(this.author.getUsername().contains(username)){
			return 1;
		}
		for (Member m : members){
			if(m.getUsername().contains(username)){
				return 2;
			}
		}
		return 0;
	}

	public int ownership(int mem_id) {
		if(this.author.getId() == mem_id){
			return 1;
		}
		for (Member m : members){
			if(m.getId() == mem_id){
				return 2;
			}
		}
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

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Date getDate_create() {
		return date_create;
	}

	public void setDate_create(Date date_create) {
		this.date_create = date_create;
	}

	public Member getAuthor() {
		return author;
	}

	public void setAuthor(Member author) {
		this.author = author;
	}

	public Set<Concept> getConcepts() {
		return concepts;
	}

	public void setConcepts(Set<Concept> concepts) {
		this.concepts = concepts;
	}

	public Set<Relation> getRelations() {
		return relations;
	}

	public void setRelations(Set<Relation> relations) {
		this.relations = relations;
	}

	public Set<Member> getMembers() {
		return members;
	}

	public void setMembers(Set<Member> members) {
		this.members = members;
	}

	public Set<FeedBack> getFeedbacks() {
		return feedbacks;
	}

	public void setFeedbacks(Set<FeedBack> feedbacks) {
		this.feedbacks = feedbacks;
	}

}
