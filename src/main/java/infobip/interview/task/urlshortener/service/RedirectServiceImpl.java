package infobip.interview.task.urlshortener.service;

import infobip.interview.task.urlshortener.repository.RedirectRepository;
import infobip.interview.task.urlshortener.model.Url;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RedirectServiceImpl implements RedirectService{
    private RedirectRepository redirectRepository;

    public RedirectServiceImpl(RedirectRepository redirectRepository) {
        this.redirectRepository = redirectRepository;
    }

    public Url getUrlByCode(String shortenedUrlCode){
        return redirectRepository.getUrlByCode(shortenedUrlCode);
    }

    @Transactional
    public void incrementNumberOfUrlVisits(String shortenedUrlCode){
        redirectRepository.incrementNumberOfUrlVisits(shortenedUrlCode);
    }
}
