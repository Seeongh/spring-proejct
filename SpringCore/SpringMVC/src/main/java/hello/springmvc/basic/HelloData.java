package hello.springmvc.basic;

import lombok.Data;

@Data //getter,setter, toString 등 다 자동으로 생성
public class HelloData {
    private String username;
    private int age;
}
