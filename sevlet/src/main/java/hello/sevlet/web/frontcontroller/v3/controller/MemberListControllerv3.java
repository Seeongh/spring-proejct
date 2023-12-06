package hello.sevlet.web.frontcontroller.v3.controller;

import hello.sevlet.domain.member.Member;
import hello.sevlet.domain.member.MemberRepository;
import hello.sevlet.web.frontcontroller.ModelView;
import hello.sevlet.web.frontcontroller.v3.Controllerv3;

import java.util.List;
import java.util.Map;

public class MemberListControllerv3 implements Controllerv3 {
    MemberRepository memberRepository = MemberRepository.getInstance();
    @Override
    public ModelView process(Map<String, String> paramMap) {
        List<Member> members=memberRepository.findAll();

        ModelView mv = new ModelView("members");
        mv.getModel().put("members",members);

        return mv;
    }
}
