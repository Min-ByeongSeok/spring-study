package zerobase.fund.scraper;

import zerobase.fund.model.Company;
import zerobase.fund.model.ScrapedResult;

public interface Scraper {
    Company scrapCompanyByTicker(String ticker);
    ScrapedResult scrap(Company company);
}
