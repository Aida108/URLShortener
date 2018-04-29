package infobip.interview.task.urlshortener.service;

import infobip.interview.task.urlshortener.repository.AuthRepository;

import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService{
    private final AuthRepository authRepository;

    public AuthServiceImpl(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public Boolean isUserAuthorized(String accountId, String password) {
        return authRepository.isUserAuthorized(accountId, password) != 0;
    }
}
