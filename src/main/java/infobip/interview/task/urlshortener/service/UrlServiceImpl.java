package infobip.interview.task.urlshortener.service;

import infobip.interview.task.urlshortener.model.Url;
import infobip.interview.task.urlshortener.repository.UrlRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UrlServiceImpl implements UrlService {
    private UrlRepository urlRepository;

    public UrlServiceImpl(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public Long getCurrentIdentityColumnId(){
        return  urlRepository.getCurrentIdentityColumnId();
    }

    public Url getUrlIfAlreadyShortened(Url url){
       return urlRepository.getUrlIfAlreadyShortened(url);
    }

    @Transactional
    public void insertUrl(Url url){
        urlRepository.insertUrl(url);
    }
}