package zerobase.fund;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import zerobase.fund.model.Company;
import zerobase.fund.model.ScrapedResult;
import zerobase.fund.scraper.Scraper;
import zerobase.fund.scraper.YahooFinanceScraper;

@SpringBootApplication
@EnableScheduling
@EnableCaching
public class FundApplication {

    public static void main(String[] args) {
        SpringApplication.run(FundApplication.class, args);
    }
}
