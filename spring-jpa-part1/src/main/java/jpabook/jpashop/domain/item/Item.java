package jpabook.jpashop.domain.item;

import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
// 자식클래스들을 모두 하나의 테이블로 생성
// DB는 상속을 지원하지 않으므로 논리모델을 물리모델로 구현할 방법(전략)
@DiscriminatorColumn(name = "dtype")
// 하위 클래스를 구분하는 용도의 컬럼
public abstract class Item {
    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    // 재고수량 증가
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    // 재고수량 감소
    public void removeStock(int quantity){
        int resultStock = this.stockQuantity - quantity;

        if (resultStock < 0) {
            throw new NotEnoughStockException("need more stock");
        }

        this.stockQuantity = resultStock;
    }
}
