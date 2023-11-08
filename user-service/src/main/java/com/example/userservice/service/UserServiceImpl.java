package com.example.userservice.service;

import com.example.userservice.client.OrderServiceClient;
import com.example.userservice.dto.UserDto;
import com.example.userservice.jpa.UserEntity;
import com.example.userservice.jpa.UserRepository;
import com.example.userservice.vo.ResponseOrder;
import com.example.userservice.vo.ResponseUser;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Slf4j
@Service
public class UserServiceImpl implements UserService{

    UserRepository userRepository;
    BCryptPasswordEncoder passwordEncoder;
    Environment env;
    RestTemplate restTemplate;
    OrderServiceClient orderServiceClient;
    CircuitBreakerFactory circuitBreakerFactory;

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        UserEntity userEntity = userRepository.findByEmail(username);
//
//        if(userEntity == null) {
//            throw new UsernameNotFoundException(username);
//        }
//        userEntity.setEmail("user");
//        return new User(userEntity.getEmail(), userEntity.getEncryptedPwd(), true,true,true,true,new ArrayList<>()); //검색끝, 비교하면 성공하면 그 사용자 값을 반환해줌
//    }

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           BCryptPasswordEncoder passwordEncoder,
                           Environment env,
                           RestTemplate restTemplate,
                           OrderServiceClient orderServiceClient,
                           CircuitBreakerFactory circuitBreakerFactory) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.env = env;
        this.restTemplate =restTemplate;
        this.orderServiceClient= orderServiceClient;
        this.circuitBreakerFactory = circuitBreakerFactory;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.setUserId(UUID.randomUUID().toString());

        //dto- >entity
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity userEntity = mapper.map(userDto,UserEntity.class);
        userEntity.setEncryptedPwd(passwordEncoder.encode(userDto.getPwd()));

        userRepository.save(userEntity);

        return null;
    }

    @Override
    public UserDto getUserByUserId(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);

        UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);

        //List<ResponseOrder> orders = new ArrayList<>(); //주문이 없다고 가정
        /* //Using RestTemplate
        String orderUrl =String.format(env.getProperty("order_service.url"), userId); //연결하고자하는 order-service url(through Eureka)
        ResponseEntity<List<ResponseOrder>> orderListResponse =
                restTemplate.exchange(orderUrl, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<ResponseOrder>>() {
        }); //주소값, 호출하고자하는 type,요청PARAM, 거기서 반환하는 형식,

        List<ResponseOrder> ordersList = orderListResponse.getBody();
        userDto.setOrders(ordersList);
        */

        /* // Using FeignClient
        List<ResponseOrder> orderList = null ;
            try{
                orderList = orderServiceClient.getOrders(userId);
            }
            catch(FeignException ex){ // error handler
                log.error(ex.getMessage());
            }
      */

        /*ErrorDecoder
        List<ResponseOrder> orderList = orderServiceClient.getOrders(userId);
        */
        log.info("before call orderservice");
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker"); //서킷브레이커 생성
        List<ResponseOrder> orderList = circuitBreaker.run(() -> orderServiceClient.getOrders(userId),
                throwable -> new ArrayList<>());
        //데이터가 존재하면 그 데이터를 쓰고 없으면 비어있는 리스트 데이터를 씀
        log.info("after call orderservice");
        userDto.setOrders(orderList);
        return userDto;
    }

    @Override
    public Iterable<UserEntity> getUserByAll() {
        return userRepository.findAll(); //이미 crud에서 기본으로 제공하고있음
    }
}
