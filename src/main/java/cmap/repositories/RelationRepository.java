package cmap.repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import cmap.entity.Relation;
@Repository
public interface RelationRepository extends JpaRepository<Relation, Integer> {
	public Relation findById(int id);
	
	@Modifying
    @Transactional
    @Query("delete from Relation u where cmap.id = ?1")
    void deleteByCMap(int id);
}
