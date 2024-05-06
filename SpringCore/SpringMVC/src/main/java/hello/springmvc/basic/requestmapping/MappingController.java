package hello.springmvc.basic.requestmapping;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class MappingController {

    //메서드 지정을 안하면 get, post 모두 허용
    @RequestMapping("/hello-basic") //url이 오면 이 메서드와 매칭한다
    //@RequestMapping("/hello-basic", "/hello-basic2") //배열로 제공해서 배열도가능 url은 다르지만 그냥 인정하고 매핑해줌
    public String helloBasic() {
        log.info("hellobasic");
        return "ok";
    }

    @RequestMapping(value = "/mapping-get-v1", method= RequestMethod.GET)
    public String mappingGet1() {
        log.info("mappingGetv1");
        return "ok";
    }


    /**
     *
     * @GetMapping
     * @PostMapping
     * @PutMapping
     * @DeleteMapping
     * @PatchMapping
     * @return
     */
    @GetMapping(value = "/mapping-get-v2")
    public String mappingGet2() {
        log.info("mappingGetv2");
        return "ok";
    }

    /**
     * PathVariable사용
     * 변수명 같으면 생략가능
     * @return
     */
    @GetMapping("/mapping/{userId}")
    public String mappingPath(@PathVariable("userId")String data) {

        log.info("mappingPath userid = {}", data);
        return "ok";
    }

    //다중 매핑
    @GetMapping("/mapping/users/{userId}/orders/{orderId}")
    public String mappingPath2(@PathVariable("userId")String data, @PathVariable long orderId) {

        log.info("mappingPath userid = {}, orderid = {}", data, orderId);
        return "ok";
    }


    /**
     * parameter로 추가 매핑 - 특정 파라미터가 있어야한다를 암시
     * params="mode",
     * params="!mode",
     * params="mode=debug"
     * params="mode!=debug"
     * params = {"mode=debug","data=good"}
     */
    @GetMapping(value = "/mapping-param", params= "mode=debug")
    public String mappingParam() {
        log.info("mappingParam");
        return "ok";
    }

    /**
     * 헤더 추가 매핑
     * params="mode",
     * params="!mode",
     * params="mode=debug"
     * params="mode!=debug"
     * params = {"mode=debug","data=good"}
     */
    //@GetMapping(value = "/mapping-header", headers= "mode=debug")
    @PostMapping(value = "/mapping-consume", headers="application/json") //type매핑
    public String mappingHeader() {
        log.info("mappingHeader");
        return "ok";
    }

    /**
     * consumes=  Media Type(Contenttype) ex. application/json
     * produces = accept헤더기반 Mediatype
     *
     * produces = "text/html"
     * produces = "!text/html"
     * produces= "text/*"
     * produces= "*\/*""
     * @return
     */
    @PostMapping(value = "/mapping-produce", consumes="text/html") //accept헤더가 필요함 난 text를 생산하는애임, aceept가 json관련되면 못받음
    public String mappingProduces() {
        log.info("mappingProduces");
        return "ok";
    }
}
