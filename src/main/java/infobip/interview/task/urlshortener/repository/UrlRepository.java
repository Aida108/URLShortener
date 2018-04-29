package infobip.interview.task.urlshortener.repository;

import infobip.interview.task.urlshortener.model.Url;

import org.springframework.data.repository.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UrlRepository extends Repository<Url, Long> {
    @Query(value = "SELECT COUNT(1) FROM Url", nativeQuery = true)
    Long getCurrentIdentityColumnId();

    @Query(value = "SELECT * from Url WHERE fk_account_Id = (:#{#url.account.accountId}) and original_url = (:#{#url.originalUrl}) and redirect_Type = (:#{#url.redirectType}) ", nativeQuery = true)
    Url getUrlIfAlreadyShortened(@Param("url") Url url);

    @Query(value = "INSERT INTO Url(original_Url, shortened_url_code, redirect_Type, fk_account_Id, number_Of_Url_Visits) " +
            "VALUES (:#{#url.originalUrl}, :#{#url.shortenedUrlCode}, :#{#url.redirectType}, :#{#url.account.accountId}, :#{#url.numberOfUrlVisits})"
            , nativeQuery = true)
    @Modifying
    void insertUrl(@Param("url") Url url);
}
