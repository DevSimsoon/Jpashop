package jpabook.jpashop.domain.item;


import jpabook.jpashop.Exception.NotEnoughStockException;
import jpabook.jpashop.domain.Category;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance (strategy = InheritanceType.SINGLE_TABLE)// 상속
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public abstract class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private  int price;
    private int stockQuantity;

    @ManyToMany (mappedBy = "items")
    private List<Category> categories = new ArrayList<Category>();

    // 비즈니스 로직
    /**
     * stock 증가 => stockQuantity를 setter로 조절
     * 위 처럼 하면 OOP ( 객체지향 )적이지 못 함
     */
    public void addStock(int quantity) { this.stockQuantity += quantity; }

    /**
     * stock 감소
     */
    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if ( restStock < 0 ) {
            throw new NotEnoughStockException("재고가 필요합니다.");
        }
        this.stockQuantity = restStock;
    }




}
