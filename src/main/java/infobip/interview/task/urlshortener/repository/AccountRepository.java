package infobip.interview.task.urlshortener.repository;

import infobip.interview.task.urlshortener.model.Account;

import org.springframework.data.repository.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountRepository extends Repository<Account, Long> {
    @Query(value = "INSERT INTO Account (account_Id, password) VALUES (:accountId, :password)", nativeQuery = true)
    @Modifying
    void insertAccount(@Param("accountId") String accountId, @Param("password") String password);

    @Query(value = "SELECT * FROM Account WHERE account_Id = (:accountId)", nativeQuery = true)
    Account isAccountIdTaken(@Param("accountId") String accountId);
}
