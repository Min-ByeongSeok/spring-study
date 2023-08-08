package jpabook.jpashop.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@DiscriminatorValue("A")
// 자식클래스에 선언, 엔티티를 저장할 때 부모타입의 구분 컬럼에 저정할값을 지정한다.
// 기본값으로 클래스이름이 설정됨
public class Album extends Item {
    private String artist;
    private String etc;
}
