package com.example.userservice.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


//@AllArgsConstructor //ARG다가지고 이쓴 생성자
//@NoArgsConstructor //ARG없는 디폴트 생성자
@Component //얘는 컨트롤러도 아니고 뭐도아니야
@Data //롬복쓸겨\
public class greeting {

    //@Value("${greeting.message}")
    private String message ;
}
