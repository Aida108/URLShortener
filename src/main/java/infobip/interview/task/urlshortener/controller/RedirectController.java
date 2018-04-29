package infobip.interview.task.urlshortener.controller;

import infobip.interview.task.urlshortener.exceptionHandler.GeneralException;
import infobip.interview.task.urlshortener.model.Url;
import infobip.interview.task.urlshortener.service.RedirectService;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
public class RedirectController {

    private RedirectService redirectService;

    public RedirectController(RedirectService redirectService) {
        this.redirectService = redirectService;
    }

    @RequestMapping(value = "/{shortenedUrlCode}",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> redirectURL(@PathVariable("shortenedUrlCode") String shortenedUrlCode) {

        Url storedUrl = redirectService.getUrlByCode(shortenedUrlCode);

        if(storedUrl != null) {
            redirectService.incrementNumberOfUrlVisits(storedUrl.getShortenedUrlCode());

            return createResponseByRedirectType(storedUrl.getRedirectType(), storedUrl.getOriginalUrl());
        }

        throw new GeneralException("Redirect url not found", HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<?> createResponseByRedirectType(Integer responseType, String originalUrl){
        if (responseType == 301)
            return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).header(HttpHeaders.LOCATION, originalUrl).header("Cache-Control", "no-cache").build();
        else
            return ResponseEntity.status(HttpStatus.FOUND).header(HttpHeaders.LOCATION, originalUrl).build();
    }
}
