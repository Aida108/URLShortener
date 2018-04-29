package infobip.interview.task.urlshortener.service;

import java.util.Map;

public interface StatisticService {
   Map<String,Integer> getAccountUrlsStatistic(String accountId);
}
