package hello.sevlet.web.springmvc.v2;

import hello.sevlet.domain.member.Member;
import hello.sevlet.domain.member.MemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/springmvc/v2/members")
public class SpringMemberControllerv2 {

    MemberRepository memberRepository = MemberRepository.getInstance();
    @RequestMapping("/new-form")
    public ModelAndView newForm(){
        return new ModelAndView("new-form");//논리 이름
    }

    @RequestMapping("/save")
    public ModelAndView save(HttpServletRequest request , HttpServletResponse response) {
        String username = request.getParameter("username");
        int age = Integer.parseInt( request.getParameter("age"));
        Member member= new Member(username,age);

        memberRepository.save(member);

        ModelAndView mv = new ModelAndView("save-result");
        //mv.getModel().put("member",member);
        mv.addObject("member",member);
        return mv;
    }

    @RequestMapping //members
    public ModelAndView members(Map<String, String> paramMap) {
        List<Member> members=memberRepository.findAll();

        ModelAndView mv = new ModelAndView("members");
        //mv.getModel().put("members",members);
        mv.addObject("members",members);
        return mv;
    }
}
