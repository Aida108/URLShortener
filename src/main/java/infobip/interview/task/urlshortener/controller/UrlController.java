package infobip.interview.task.urlshortener.controller;

import infobip.interview.task.urlshortener.exceptionHandler.GeneralException;
import infobip.interview.task.urlshortener.model.Account;
import infobip.interview.task.urlshortener.model.Url;
import infobip.interview.task.urlshortener.service.AuthService;
import infobip.interview.task.urlshortener.service.UrlService;
import infobip.interview.task.urlshortener.util.AuthUtil;
import infobip.interview.task.urlshortener.util.UrlUtil;
import org.json.simple.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class UrlController {

    private UrlService urlService;
    private AuthService authService;
    private UrlUtil urlUtils;
    private AuthUtil authUtils;

    public UrlController(UrlService urlService, AuthService authService) {
        this.urlService = urlService;
        this.authService = authService;
        this.urlUtils = new UrlUtil();
        this.authUtils = new AuthUtil();
    }

    @RequestMapping(value = "/register",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            headers = HttpHeaders.AUTHORIZATION)
    @ResponseBody
    public ResponseEntity<?> registerURL(@RequestBody Map<String, String> registerAccountData, @RequestHeader("Authorization") String authorizationHeaderValue) {

        Account account = authUtils.DecodeAuthorizationHeader(authorizationHeaderValue);

        String originalUrl = registerAccountData.get("url");
        String redirectType = registerAccountData.get("redirectType");

        urlUtils.validateRequestData(originalUrl, redirectType);

        if (authService.isUserAuthorized(account.getAccountId(), account.getPassword())) {
            if (redirectType == null)
                redirectType = "302";

            Url newUrlRecord = new Url(originalUrl, generateShortenedUrlCode(urlService.getCurrentIdentityColumnId() + 1),
                    Integer.parseInt(redirectType), 0, account);
            return insertUrlIfNotExistsAndCreateResponse(newUrlRecord);
        }

        throw new GeneralException("Invalid account credentials", HttpStatus.UNAUTHORIZED);
    }

    private ResponseEntity<?> insertUrlIfNotExistsAndCreateResponse(Url newUrlRecord) {
        JSONObject jsonResponse = new JSONObject();

        Url alreadyShortenedUrl = urlService.getUrlIfAlreadyShortened(newUrlRecord);

        if (alreadyShortenedUrl == null) {
            urlService.insertUrl(newUrlRecord);
            jsonResponse.put("shortUrl", "http://localhost:8080/" + newUrlRecord.getShortenedUrlCode());
            return new ResponseEntity<>(jsonResponse, HttpStatus.CREATED);
        } else {
            jsonResponse.put("shortUrl", "http://localhost:8080/" + alreadyShortenedUrl.getShortenedUrlCode());
            return new ResponseEntity<>(jsonResponse, HttpStatus.FOUND);
        }
    }

    private String generateShortenedUrlCode(Long urlId) {
        return urlUtils.generateShortenedUrlCode(urlId.intValue() * 10000000);
    }

}


