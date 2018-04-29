package infobip.interview.task.urlshortener.util;

import infobip.interview.task.urlshortener.exceptionHandler.GeneralException;
import org.apache.commons.text.RandomStringGenerator;
import org.apache.commons.text.CharacterPredicates;
import org.springframework.http.HttpStatus;

public class AccountUtil {

    public String generateRandomPassword() {
        RandomStringGenerator randomPasswordGenerator = new RandomStringGenerator.Builder().withinRange('0', 'z')
                .filteredBy(CharacterPredicates.LETTERS, CharacterPredicates.DIGITS)
                .build();
        return randomPasswordGenerator.generate(8);
    }

    public void validateRequestData(String accountId){
        if (accountId == "" || accountId == null)
            throw new GeneralException("AccountId is required", HttpStatus.BAD_REQUEST);
        else if (!(accountId.matches("[a-zA-Z0-9]*")))
            throw new GeneralException("AccountId value is invalid", HttpStatus.BAD_REQUEST);
    }
}
