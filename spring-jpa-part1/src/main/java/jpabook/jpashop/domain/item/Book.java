package jpabook.jpashop.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@DiscriminatorValue("B") // 싱글테이블을 구분하기 위한 값
public class Book extends Item {
    private String author;
    private String isbn;
}
