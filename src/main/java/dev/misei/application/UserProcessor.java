package dev.misei.application;

import dev.misei.domain.mapper.user.UserMapper;
import dev.misei.domain.payload.user.UserPayload;
import dev.misei.repository.AuthRepository;
import dev.misei.repository.BusinessRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserProcessor {
    private final UserMapper userMapper;
    @PersistenceContext
    private EntityManager entityManager;
    private BusinessRepository businessRepository;
    private AuthRepository authRepository;

    @Transactional
    public UserPayload findByEmail(String email) {
        var user = authRepository.findByEmail(email);

        if (user.isPresent()) {
            return userMapper.toPayload(user.get());
        }

        return new UserPayload();
    }

}
