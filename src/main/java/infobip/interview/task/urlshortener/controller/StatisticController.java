package infobip.interview.task.urlshortener.controller;

import infobip.interview.task.urlshortener.exceptionHandler.GeneralException;
import infobip.interview.task.urlshortener.model.Account;
import infobip.interview.task.urlshortener.service.AuthService;
import infobip.interview.task.urlshortener.service.StatisticService;
import infobip.interview.task.urlshortener.util.AuthUtil;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.json.simple.JSONObject;
import java.util.Map;

@RestController
@RequestMapping("/statistic")
public class StatisticController {

    private StatisticService statisticService;
    private AuthService authService;
    private AuthUtil authUtils;

    public StatisticController(StatisticService statisticService, AuthService authService) {
        this.statisticService = statisticService;
        this.authService = authService;
        this.authUtils = new AuthUtil();
    }

    @RequestMapping(value = "/{AccountId}",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE,
                    consumes = MediaType.APPLICATION_JSON_VALUE,
                    headers = HttpHeaders.AUTHORIZATION)
    @ResponseBody
    public ResponseEntity<?> getURLStatistic(@PathVariable("AccountId") String AccountId,
                                             @RequestHeader(value = "Authorization", required = false) String authorizationHeaderValue) {

        Account account = authUtils.DecodeAuthorizationHeader(authorizationHeaderValue);

        if (authService.isUserAuthorized(account.getAccountId(), account.getPassword())) {

            if (!account.getAccountId().equals(AccountId))
                throw new GeneralException("Users are not allowed to get other user's url statistic.", HttpStatus.FORBIDDEN);

            Map<String, Integer> urlsStatistic = statisticService.getAccountUrlsStatistic(AccountId);

            return createResponseByUrlsStatistic(urlsStatistic);
        }
        throw new GeneralException("Invalid account credentials", HttpStatus.UNAUTHORIZED);
    }

    private ResponseEntity<?> createResponseByUrlsStatistic( Map<String, Integer> urlsStatistic){
        if (urlsStatistic.size() != 0)
            return new ResponseEntity<>(new JSONObject(urlsStatistic), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
