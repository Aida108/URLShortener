package infobip.interview.task.urlshortener.service;

public interface AccountService {
        Boolean isAccountIdTaken(String accountId);

        void openAccount(String accountId, String password);
}
