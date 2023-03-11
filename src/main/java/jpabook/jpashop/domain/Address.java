package jpabook.jpashop.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;

    protected Address() {
    } // JPA 스펙상 그냥 만든거, 없으면 오류남, ppt에 자세한 설명있다.

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    } // 생성할때만 생성자로 생성하게 하는게 좋다
}
