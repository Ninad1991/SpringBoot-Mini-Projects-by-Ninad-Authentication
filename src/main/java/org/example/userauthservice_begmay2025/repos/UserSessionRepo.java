package org.example.userauthservice_begmay2025.repos;

import org.example.userauthservice_begmay2025.models.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserSessionRepo extends JpaRepository<UserSession, Long> {

    Optional<UserSession> findByTokenAndUserId(String token, Long userId);

}
