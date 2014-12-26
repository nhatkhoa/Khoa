package cmap.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cmap.entity.Member;
@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {
	// --- Hàm get người dùng bằng username
	public Member findByUsername(String username);
	
	public Member findById(int id);
}
