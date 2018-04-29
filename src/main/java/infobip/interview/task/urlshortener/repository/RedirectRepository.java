package infobip.interview.task.urlshortener.repository;

import infobip.interview.task.urlshortener.model.Url;

import org.springframework.data.repository.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RedirectRepository extends Repository<Url, Long> {
    @Query(value = "SELECT * FROM Url WHERE LOWER(shortened_url_code) = LOWER(:shortenedUrlCode)", nativeQuery = true)
    Url getUrlByCode(@Param("shortenedUrlCode") String shortenedUrlCode);

    @Query(value = "UPDATE Url SET number_Of_Url_Visits = number_Of_Url_Visits + 1 WHERE LOWER(shortened_url_code) = LOWER(:shortenedUrlCode)", nativeQuery = true)
    @Modifying
    void incrementNumberOfUrlVisits(@Param("shortenedUrlCode") String shortenedUrlCode);
}
