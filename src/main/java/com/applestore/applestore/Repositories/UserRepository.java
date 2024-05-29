package com.applestore.applestore.Repositories;

import com.applestore.applestore.Entities.UserEntity;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Optional;


@Repository
@EnableJpaRepositories
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Optional<UserEntity> findByUsername(String username);
    UserEntity findUserByGmail(String gmail);
    UserEntity findUserByResetPasswordToken (String resetPasswordToken);

    Optional<UserEntity> findByGmail(String email);
//    UserEntity findByUsername(String tenTaiKhoan);
}