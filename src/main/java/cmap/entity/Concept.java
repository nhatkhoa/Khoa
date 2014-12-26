package cmap.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="concept")
public class Concept{
	
	// ---- Set primary key, auto generate ID
	@Id
	@GeneratedValue
	private int id;
	private String name;
	private String loc;
	private int	pass;
	
	// ---- Mapping
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private CMap cmap;
	
	// ---- Constructor
	public Concept(){
		
	}
	

	public Concept(String name, String loc, CMap cmap) {
		this.name = name;
		this.loc = loc;
		this.cmap = cmap;
		this.pass = -1;
	}

	@Override 
	public boolean equals(Object obj){
		Concept con = (Concept) obj;
		// --- So sánh tên con concept
		return this.getName().contains(con.getName());

	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLoc() {
		return loc;
	}

	public void setLoc(String loc) {
		this.loc = loc;
	}

	public int getPass() {
		return pass;
	}

	public void setPass(int pass) {
		this.pass = pass;
	}

	public CMap getCmap() {
		return cmap;
	}

	public void setCmap(CMap cmap) {
		this.cmap = cmap;
	}

	
	
}