package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    //JPA는 룰이있다. select m from Member m where m.name = ?
    // findByName에서 Name을 보고 m.name을 만든다.
    List<Member> findByName(String name);
}
