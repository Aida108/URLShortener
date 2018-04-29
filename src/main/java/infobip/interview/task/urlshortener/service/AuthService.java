package infobip.interview.task.urlshortener.service;

public interface AuthService {
   Boolean isUserAuthorized(String accountId, String password);
}
