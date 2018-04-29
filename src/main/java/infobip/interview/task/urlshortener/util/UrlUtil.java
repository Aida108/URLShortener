package infobip.interview.task.urlshortener.util;

import infobip.interview.task.urlshortener.exceptionHandler.GeneralException;
import org.springframework.http.HttpStatus;

import java.net.URL;

public class UrlUtil {

    static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    static final int alphabetLength = ALPHABET.length();

    public String generateShortenedUrlCode(long nextIdentityNumber) {
        final StringBuilder stringBuilder = new StringBuilder(1);
        do {
            stringBuilder.insert(0, ALPHABET.charAt((int) (nextIdentityNumber % alphabetLength)));
            nextIdentityNumber /= alphabetLength;
        } while (nextIdentityNumber > 0);
        return stringBuilder.toString();
    }

    public void validateRequestData(String originalUrl, String redirectType) {
        if (originalUrl == null || (redirectType != null && !redirectType.equals("301") && !redirectType.equals("302")) || !isUrlValid(originalUrl))
            throw new GeneralException("Request data not valid", HttpStatus.BAD_REQUEST);
    }

    private Boolean isUrlValid(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
