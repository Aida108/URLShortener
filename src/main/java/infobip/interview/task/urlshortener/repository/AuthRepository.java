package infobip.interview.task.urlshortener.repository;

import infobip.interview.task.urlshortener.model.Account;

import org.springframework.data.repository.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AuthRepository extends Repository<Account, Long> {
    @Query(value = "SELECT count(*) FROM Account WHERE account_Id = (:accountId) and password = (:password)", nativeQuery = true)
    Integer isUserAuthorized(@Param("accountId") String accountId, @Param("password") String password);
}
