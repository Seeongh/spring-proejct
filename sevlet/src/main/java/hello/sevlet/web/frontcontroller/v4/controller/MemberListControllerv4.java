package hello.sevlet.web.frontcontroller.v4.controller;

import hello.sevlet.domain.member.Member;
import hello.sevlet.domain.member.MemberRepository;
import hello.sevlet.web.frontcontroller.ModelView;
import hello.sevlet.web.frontcontroller.v4.Controllerv4;

import java.util.List;
import java.util.Map;

public class MemberListControllerv4 implements Controllerv4 {
    MemberRepository memberRepository = MemberRepository.getInstance();
    @Override
    public String process(Map<String, String> paramMap, Map<String, Object> model) {
        List<Member> members=memberRepository.findAll();

        model.put("members",members);
        return "members";
    }
}
