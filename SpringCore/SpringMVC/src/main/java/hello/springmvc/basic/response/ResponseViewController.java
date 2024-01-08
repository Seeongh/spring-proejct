package hello.springmvc.basic.response;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ResponseViewController {

    @RequestMapping("/response-view-v1")
    public ModelAndView responseViewv1(){
        ModelAndView mav = new ModelAndView("response/hello")
                .addObject("data","hello");
        return mav;
    }

    @RequestMapping("/response-view-v2")
    public String responseViewv2(Model model){
        model.addAttribute("data", "hello2");
        ModelAndView mav = new ModelAndView("response/hello");

        return "response/hello"; //@Controller면서 String 반환시에는 view의 논리이름이됨.
    }

    @ResponseBody
    @RequestMapping("/response-view-v2_5")
    public String responseViewv2_5(Model model){
        model.addAttribute("data", "hello2");
        ModelAndView mav = new ModelAndView("response/hello");

        return "response/hello"; //뷰로안가고 문자로 보임 그대로 http 응답 코드로 나감,
        // @RestController도 뷰 안찾고 문자열로 가는건 마찮가지
    }

    @RequestMapping("/response/hello") //썜 절대 권장하지 않음, 경로이름이랑 뷰 논리이름이랑 같으면 없어도됨(앞 슬래시뗌)
    public void responseViewv3(Model model){
        model.addAttribute("data", "hello2");
        //관례적으로 생략할 수 있또록 도와주는 정도..
    }
}
