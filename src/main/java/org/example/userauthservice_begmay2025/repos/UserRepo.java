package org.example.userauthservice_begmay2025.repos;

import org.example.userauthservice_begmay2025.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findByEmailEquals(String email);

    Optional<User> findByIdEquals(Long id);

    void deleteByEmail(String email);
}
