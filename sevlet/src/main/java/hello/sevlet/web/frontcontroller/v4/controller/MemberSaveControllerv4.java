package hello.sevlet.web.frontcontroller.v4.controller;

import hello.sevlet.domain.member.Member;
import hello.sevlet.domain.member.MemberRepository;
import hello.sevlet.web.frontcontroller.ModelView;
import hello.sevlet.web.frontcontroller.v4.Controllerv4;

import java.util.List;
import java.util.Map;

public class MemberSaveControllerv4 implements Controllerv4 {
    MemberRepository memberRepository = MemberRepository.getInstance();
    @Override
    public String process(Map<String, String> paramMap, Map<String, Object> model) {
        String username = paramMap.get("username");
        int age = Integer.parseInt(paramMap.get("age"));
        Member member= new Member(username,age);

        memberRepository.save(member);

        model.put("member",member);

        return "save-result";
    }
}
