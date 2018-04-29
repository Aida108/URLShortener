package infobip.interview.task.urlshortener.service;

import infobip.interview.task.urlshortener.model.Url;

public interface RedirectService {
    Url getUrlByCode(String shortenedUrlCode);

    void incrementNumberOfUrlVisits(String shortenedUrlCode);
}
