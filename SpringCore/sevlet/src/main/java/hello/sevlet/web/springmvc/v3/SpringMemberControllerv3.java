package hello.sevlet.web.springmvc.v3;

import hello.sevlet.domain.member.Member;
import hello.sevlet.domain.member.MemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * viewname 직접반환
 * @RequestParam 사용
 *  -> RequestParam("username") == request.getParameter("username")
 * @RequestMapping -> @GetMapping, @PostMapping
 */
@Controller
@RequestMapping("/springmvc/v3/members")
public class SpringMemberControllerv3 {

    MemberRepository memberRepository = MemberRepository.getInstance();
    //@RequestMapping(value = "/new-form", method= RequestMethod.GET)
    @GetMapping("/new-form")
    public String newForm(){
        return "new-form"; //@기반 컨트롤러는 modelandview,string다 반환가능 그럼 뷰 이름으로 앎
    }

    //@RequestMapping(value ="/save", method = RequestMethod.POST) //http는 여러번 호출할 수있어서 기능에 맞게 변경
    @PostMapping("/save")
    public String save(@RequestParam("username") String username,
                             @RequestParam("age") int age,
                             Model model) {//type cast까지
        Member member= new Member(username,age);

        memberRepository.save(member);
        model.addAttribute("member",member);
        return "save-result";
    }

    //@RequestMapping(method = RequestMethod.GET) //members
    @GetMapping
    public String members(Model model) {
        List<Member> members=memberRepository.findAll();

        model.addAttribute("members",members);
        return "members";
    }
}
