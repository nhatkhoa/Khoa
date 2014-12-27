package cmap.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import cmap.entity.FeedBack;
@Repository
public interface FeedBackRepository extends JpaRepository<FeedBack, Integer> {
	// --- Lấy feedback theo id
	public FeedBack findById(int id);
	
	// --- Lấy feedback theo assign và member
	@Query("Select f from FeedBack f Where f.assign.id =?1 AND f.cmap.author.username =?2 Order By f.id DESC")
	public Set<FeedBack> getFeedBack(int assign_id, String username);
}
