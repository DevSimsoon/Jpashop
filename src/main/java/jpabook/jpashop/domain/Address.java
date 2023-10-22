package jpabook.jpashop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;

    protected Address() { // protected 기능 > 주소를 상속 받아서 수정할 수 있게함
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }

}

/* 엔티티 설계시 주의점

    1. 엔티티에는 가급적 Setter를 사용하지말자
        :  ( 예를 들어 Setter가 있다고 침 )
        : 변경 / 수정 될 수 있는 포인트가 너무 많음
        : 유지보수가 어렵다.
        : 리팩토링(재구성) 으로 Setter 제거

 */