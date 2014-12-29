package cmap.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cmap.entity.Doc;
 
@Repository
public interface DocRepository extends JpaRepository<Doc, Integer> {
	
	public Doc findById(int id);
	
}
