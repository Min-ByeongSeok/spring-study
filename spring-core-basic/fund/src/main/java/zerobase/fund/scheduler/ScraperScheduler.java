package zerobase.fund.scheduler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import zerobase.fund.model.Company;
import zerobase.fund.model.ScrapedResult;
import zerobase.fund.model.constants.CacheKey;
import zerobase.fund.persist.CompanyRepository;
import zerobase.fund.persist.DividendRepository;
import zerobase.fund.persist.entity.CompanyEntity;
import zerobase.fund.persist.entity.DividendEntity;
import zerobase.fund.scraper.Scraper;

import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
@EnableCaching
public class ScraperScheduler {

    // 회사 목록을 조회하기 위한
    private final CompanyRepository companyRepository;

    // 배당금 정보 저장
    private final DividendRepository dividendRepository;

    private final Scraper yahooFinanceScraper;

    @CacheEvict(value = CacheKey.KEY_FINANCE, allEntries = true)
    // 매일 정각마다 수행
    @Scheduled(cron = "${scheduler.scrap.yahoo}")
    public void yahooFinanceScheduling() {
        log.info("scraping scheduler is started");
        // 저장된 회사 목록 조회
        List<CompanyEntity> companies = this.companyRepository.findAll();

        // 회사에 대한 배당금 정보를 새로 스크래핑
        for (var company : companies) {
            log.info("scraping scheduler is started -> " + company.getName());
            ScrapedResult scrapedResult
                    = this.yahooFinanceScraper.scrap(new Company(company.getTicker(), company.getName()));

            // 스크래핑한 배당금 정보중 데이터베이스에 없는 값은 저장
            scrapedResult.getDividend().stream()
                    // 디비든 모델을 디비든 엔티티로 매핑
                    .map(e -> new DividendEntity(company.getId(), e))
                    // 엘리먼트를 하나씩 디비든 레포지토리에 삽입
                    .forEach(e -> {
                        boolean exists
                                = this.dividendRepository.existsByCompanyIdAndDate(e.getCompanyId(), e.getDate());

                        if (!exists) {
                            this.dividendRepository.save(e);
                            log.info("insert new dividend -> "+ e.toString());
                        }
                    });

            // 연속적으로 스크래핑 대상 사이트 서버에 요청을 날리지 않도록 일시정지
            try {
                Thread.sleep(3000);
            }
            // 인터럽트를 받는 스레드가 blocking 될 수 있는 메소드를 실행할 때 발생
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

    }
}
