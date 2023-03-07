package jpabook.jpashop;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity // jpa가 데이터베이스에 Member 테이블을 만들어준다.
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    private Long id;
    private String username;
}
