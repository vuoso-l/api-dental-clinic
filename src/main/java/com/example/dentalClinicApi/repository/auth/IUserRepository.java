package com.example.dentalClinicApi.repository.auth;

import com.example.dentalClinicApi.entity.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface IUserRepository extends JpaRepository<User, Integer> {

    public Optional<User> findByEmail(String email);

    public Optional<User> findByUserNameOrEmail(String userName, String email);

    public Optional<User> findByUserName(String userName);

    public Boolean existsByUserName(String userName);

    public Boolean existsByEmail(String email);
}
