package infobip.interview.task.urlshortener.controller;

import infobip.interview.task.urlshortener.exceptionHandler.AccountIdAlreadyTakenException;
import infobip.interview.task.urlshortener.service.AccountService;
import infobip.interview.task.urlshortener.util.AccountUtil;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class AccountController {

    private AccountService accountService;
    private AccountUtil accountUtils;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
        this.accountUtils = new AccountUtil();
    }

    @RequestMapping(value = "/account",
                    method = RequestMethod.POST,
                    produces = MediaType.APPLICATION_JSON_VALUE,
                    consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> registerAccount(@RequestBody Map<String, String> registerAccountData) {
        String accountId = registerAccountData.get("AccountId");

        accountUtils.validateRequestData(accountId);

        return openAccountIfNotExistsAndCreateResponse(accountService.isAccountIdTaken(accountId), accountId);
    }

    private ResponseEntity<?> openAccountIfNotExistsAndCreateResponse(Boolean accountExists, String accountId) {
        if (accountExists)
            throw new AccountIdAlreadyTakenException("Registration failed. Error: AccountId '" + accountId + "' already taken.", !accountExists );
        else
            return new ResponseEntity<>(createAccountOpenedResponse(!accountExists, accountId), HttpStatus.CREATED);
    }

    private String openAccount(String accountId) {
        String password = accountUtils.generateRandomPassword();
        accountService.openAccount(accountId, password);

        return password;
    }

    private JSONObject createAccountOpenedResponse(Boolean success, String accountId){
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("success", success);
        jsonResponse.put("password", openAccount(accountId));
        jsonResponse.put("description", "Your Account is opened.");

        return jsonResponse;
    }
}
