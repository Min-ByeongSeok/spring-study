package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity // DB 테이블에 대응하는 하나의 클래스
        // @Entity가 붙은 클래스는 JPA가 관리하며, JPA를 사용해서 DB테이블과 매핑할 클래스는 @Entity를 붙여야 매핑이 가능하다.
@Getter @Setter
public class Member {
    @Id // JPA 엔티티 객체의 식별자로 사용할 필드에 적용
        // 유니크한 DB의 컬럼과 매핑
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member") // Order 테이블에있는 member필드에 의해 매핑된다.
    // 한명의 회원이 여러개의 상품을 주문할수 있기때문에 1대다 관계설정
    private List<Order> orders = new ArrayList<>();
}
