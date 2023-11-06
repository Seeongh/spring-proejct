package com.example.userservice.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    UserEntity findByUserId(String userId); //userid가 들어오면 enity를 반환시켜준다

    UserEntity findByEmail(String username);
}
