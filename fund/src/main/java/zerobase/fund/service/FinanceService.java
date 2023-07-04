package zerobase.fund.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import zerobase.fund.model.Company;
import zerobase.fund.model.Dividend;
import zerobase.fund.model.ScrapedResult;
import zerobase.fund.persist.CompanyRepository;
import zerobase.fund.persist.DividendRepository;
import zerobase.fund.persist.entity.CompanyEntity;
import zerobase.fund.persist.entity.DividendEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FinanceService {

    // 회사명 기준으로 회사정보를 조회하기 위해서 접근
    private final CompanyRepository companyRepository;
    // 배당금을 조회하기 위해서 접근
    private final DividendRepository dividendRepository;

    public ScrapedResult getDividendByCompanyName(String companyName) {
        // 1. 회사명을 기준으로 회사 정보를 조회
        CompanyEntity company = this.companyRepository.findByName(companyName)
                // 값이 없으면 인자로넘겨지는 Exception 발생, 값이 있다면 Optional이 벗겨진 알맹이를 뱉어냄
                .orElseThrow(() -> new RuntimeException("존재하지 않는 회사명입니다."));

        // 2. 조회된 회사의 id로 배당금 조회
        List<DividendEntity> dividendEntities = this.dividendRepository.findAllByCompanyId(company.getId());

        // 3. 결과 반환
        // ScrapedResult에 담아서 결과를 반환해주면 되는데 ScrapedResult에는 company, dividend도 엔티티타입이 아니라서
        // 일반모델 클래스로 매핑해줘야한다.
        // CompanyEntity를 CompanyModel로 바꿔줘야한다.
        // dividendEntities는 리스트타입이라서 가공해주어야한다.
        // 엔티티를 모델타입으로 가공할 수 있는 방법은 크게 2가지가 있다.
        // 1. 반복문 사용
        List<Dividend> dividends = new ArrayList<>(); // 결과값을 담을 리스트
        for (var entity : dividendEntities) {
            dividends.add(Dividend.builder()
                    .date(entity.getDate())
                    .dividend(entity.getDividend())
                    .build());
        }

        // 2. stream 사용
        List<Dividend> dividendList = dividendEntities.stream().map(e -> Dividend.builder()
                        .date(e.getDate())
                        .dividend(e.getDividend())
                        .build())
                        .collect(Collectors.toList());

        return new ScrapedResult(Company.builder()
                .ticker(company.getTicker())
                .name(company.getName())
                .build(),
                dividendList);
    }
}
