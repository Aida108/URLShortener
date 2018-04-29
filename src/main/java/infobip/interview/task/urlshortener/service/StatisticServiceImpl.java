package infobip.interview.task.urlshortener.service;

import infobip.interview.task.urlshortener.model.Url;

import infobip.interview.task.urlshortener.repository.StatisticRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatisticServiceImpl implements StatisticService {
    private StatisticRepository statisticRepository;

    public StatisticServiceImpl(StatisticRepository statisticRepository) {
        this.statisticRepository = statisticRepository;
    }

    public Map<String,Integer> getAccountUrlsStatistic(String accountId){
        List<Url> urls = statisticRepository.getAccountUrls(accountId);
        Map<String,Integer> urlsStatistic = new HashMap<>();
        for (Url url : urls) {
            urlsStatistic.put(url.getOriginalUrl()+":"+url.getRedirectType(), url.getNumberOfUrlVisits());
        }
        return urlsStatistic;
    }
}
