package cmap.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cmap.entity.FeedBack;
@Repository
public interface FeedBackRepository extends JpaRepository<FeedBack, Integer> {
	// --- Hàm get người dùng bằng username
	public FeedBack findById(int id);
}
