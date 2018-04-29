package infobip.interview.task.urlshortener.util;

import infobip.interview.task.urlshortener.exceptionHandler.GeneralException;
import infobip.interview.task.urlshortener.model.Account;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.codec.binary.StringUtils;
import org.springframework.http.HttpStatus;

public class AuthUtil {

     public Account DecodeAuthorizationHeader(String authorizationHeaderValue){
         if(authorizationHeaderValue.equals("Basic Og=="))
             throw new GeneralException("No credentials found.", HttpStatus.UNAUTHORIZED);

         String credentials = StringUtils.newStringUtf8(Base64.decodeBase64(authorizationHeaderValue.split("\\s+")[1]));
         String[] splitCredentials = credentials.split(":");

         return new Account(splitCredentials[0],splitCredentials[1]);
     }
}
