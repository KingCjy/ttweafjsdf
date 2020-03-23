package me.kingcjy.demo.repository;

import me.kingcjy.demo.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
