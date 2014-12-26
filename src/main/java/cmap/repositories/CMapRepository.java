package cmap.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cmap.entity.CMap;
@Repository
public interface CMapRepository extends JpaRepository<CMap, Integer> {
	public CMap findById(int id);
	

}
