* Spring Boot MSA 공부
  - Eureka
  - API GateWay
  - MicroService
  - Spring cloud bus(RabbitMQ)

 * Spring MVC 내부 작동 원리 공부
   1. Servlet
   - fontController
   -  Handlermapping <-> HandlerAdapter(argumentresolver,returnvaluehandler) <-> controller
   -  ViewResolver
     
   2. SpringMVC
   - 요청 매핑 : HTTP Method, PathVariable
   - Http Request 파라미터
     : 쿼리파라미터(get, post: html-form) : @RequestParam @ModelAttribute , HTTP Message Body : @RequestBody
   - Http Response 파라미터
     : 정적리소스, 동적리소스, Http Message : @ResponseBody
   - HTTP Message Converter공부
     
   3. 간단한 Item-service를 통해 WEB 구현

  * Spring JPA 공부
    1. 영속성 공부
    2. EntityMnager를 통한 엔티티 관리 공부
    3. 도메인 설계 공부
