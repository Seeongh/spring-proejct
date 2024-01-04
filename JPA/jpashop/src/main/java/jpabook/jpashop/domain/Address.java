package jpabook.jpashop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable //jpa의 내장타입
@Getter //getter만 값타입은 변경이 불가함, 생성시에 설정되어야함
public class Address {

    private String city;
    private String street;
    private String zipcode;

    protected Address() {//jpa기본 스펙으로 jpa가 생성할떄 reflection이나 기술을 써야하는데 기본생성자가 있어야함.
        //embedded는 public이나 protect를 씀
        // public으로 하면 사람들이 많이 씀-함부로 new안되고 스펙상 생성함.
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
