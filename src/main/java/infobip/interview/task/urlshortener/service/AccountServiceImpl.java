package infobip.interview.task.urlshortener.service;

import infobip.interview.task.urlshortener.repository.AccountRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Boolean isAccountIdTaken(String accountId) {
        return accountRepository.isAccountIdTaken(accountId) != null;
    }

    @Transactional
    public void openAccount(String accountId, String password) {
        accountRepository.insertAccount(accountId, password);
    }
}
