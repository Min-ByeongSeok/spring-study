package zerobase.fund.service;

import lombok.AllArgsConstructor;
import org.apache.commons.collections4.Trie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import zerobase.fund.exception.impl.AlreadyExistTickerException;
import zerobase.fund.exception.impl.NoCompanyException;
import zerobase.fund.exception.impl.NoExistTickerException;
import zerobase.fund.model.Company;
import zerobase.fund.model.ScrapedResult;
import zerobase.fund.persist.CompanyRepository;
import zerobase.fund.persist.DividendRepository;
import zerobase.fund.persist.entity.CompanyEntity;
import zerobase.fund.persist.entity.DividendEntity;
import zerobase.fund.scraper.Scraper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CompanyService {

    // AppConfig에서 생성된 Trie Bean이 초기화될때
    // CompanyService에 주입이 되면서 trie 인스턴스로 쓰인다.
    private final Trie trie;
    private final Scraper yahooFinanceScraper;

    private final CompanyRepository companyRepository;
    private final DividendRepository dividendRepository;

    public Company save(String ticker) {

        boolean exists = this.companyRepository.existsByTicker(ticker);

        if (exists) {
            throw new AlreadyExistTickerException();
        }

        return this.storeCompanyAndDividend(ticker);
    }

    // 회사를 조회하기 위한 서비스코드
    public Page<CompanyEntity> getAllCompany(Pageable pageable) {
        return this.companyRepository.findAll(pageable);
        // findAll 메서드를 쓰면 조회해야할 회사의 수가 많아진다면
        // 네트워크의 대역폭도 더 많이써야하기때문에 서비스 전체에 악영향을 줄 수 있다.
        // 또한 전체를 조회하더라도 전체를 보여줄 필요가 없을 뿐더러 전체를 보여주는 것도 힘들다
        // 적당한 데이터의 수를 정보를 노출시키는게 여러모로 더 좋다 - > pageable(페이징 기능) 사용
    }

    private Company storeCompanyAndDividend(String ticker) {
        // ticker를 기준으로 회사를 스크래핑한다
        Company company = this.yahooFinanceScraper.scrapCompanyByTicker(ticker);
//        System.out.println(company.toString());
        if (ObjectUtils.isEmpty(company)) {
            throw new NoExistTickerException();
        }

        // 해당 회사가 존재할 경우 회사의 배당금 정보를 스크래핑
        ScrapedResult scrapedResult = this.yahooFinanceScraper.scrap(company);

        // 스프래핑 결과 저장
        CompanyEntity companyEntity = this.companyRepository.save(new CompanyEntity(company));
        List<DividendEntity> dividendEntities =
                scrapedResult.getDividend().stream()
                        .map(e -> new DividendEntity(companyEntity.getId(), e))
                        .collect(Collectors.toList());

        this.dividendRepository.saveAll(dividendEntities);

        return company;
    }

    public List<String> getCompanyNamesKeyword(String keyword) {
        Pageable limit = PageRequest.of(0, 10);

        Page<CompanyEntity> companyEntities
                = this.companyRepository.findByNameStartingWithIgnoreCase(keyword, limit);
        return companyEntities.stream()
                                .map(e -> e.getName())
                                .collect(Collectors.toList());
    }

    public void addAutocompleteKeyword(String keyword) {
        this.trie.put(keyword, null);
    }

    public List<String> autocomplete(String keyword) {
        // 데이터가 많아지면 페이징기능을 사용하거나 개수를 줄여서(stream.limit) 가져오는편이 낫다
        return (List<String>) this.trie.prefixMap(keyword).keySet().stream().collect(Collectors.toList());
    }

    public void deleteAutocompleteKeyword(String keyword) {
        this.trie.remove(keyword);
    }

    public String deleteCompany(String ticker) {
        CompanyEntity company = this.companyRepository.findByTicker(ticker)
                .orElseThrow(() -> new NoCompanyException());

        this.dividendRepository.deleteAllByCompanyId(company.getId());
        this.companyRepository.delete(company);

        this.deleteAutocompleteKeyword(company.getName());

        return company.getName();
    }
}
