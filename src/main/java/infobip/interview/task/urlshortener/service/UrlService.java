package infobip.interview.task.urlshortener.service;

import infobip.interview.task.urlshortener.model.Url;

public interface UrlService {
        Long getCurrentIdentityColumnId();

        Url getUrlIfAlreadyShortened(Url url);

        void insertUrl(Url url);
}
