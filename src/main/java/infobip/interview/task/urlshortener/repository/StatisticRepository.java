package infobip.interview.task.urlshortener.repository;

import infobip.interview.task.urlshortener.model.Url;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StatisticRepository extends Repository<Url, Long>{
        @Query(value = "SELECT * FROM Url WHERE fk_account_Id = (:accountId)", nativeQuery = true)
        List<Url> getAccountUrls(@Param("accountId") String accountId);
}
