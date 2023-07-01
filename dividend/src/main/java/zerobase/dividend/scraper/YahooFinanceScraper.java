package zerobase.dividend.scraper;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import zerobase.dividend.model.Company;
import zerobase.dividend.model.Dividend;
import zerobase.dividend.model.ScrapedResult;
import zerobase.dividend.model.constants.Month;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class YahooFinanceScraper implements Scraper{

    private static final String STATISTICS_URL = "https://finance.yahoo.com/quote/%s/history?period1=%d&period2=%d&interval=1mo";
    private static final String SUMMARY_URL = "https://finance.yahoo.com/quote/%s?p=%s";

    private static final long START_TIME = 86400;

    @Override
    public ScrapedResult scrap(Company company) {

        var scrapResult = new ScrapedResult();
        scrapResult.setCompany(company);

        try {
            long now = System.currentTimeMillis() / 1000;
            String url = String.format(STATISTICS_URL, company.getTicker(), START_TIME, now);

            // 웹사이트로부터 스크래핑을 하기위해서는 요청을 보내서 html 문서를 받아오는 것부터 해야한다.
            // Jsoup의 connect메소드는 Connection이라는 인스턴스를 반환한다.
            // Jsoup api는 http://jsoup.org/apidocs 에서 확인할 수 있다.
            // Creates a new Connection (session), with the defined request URL.
            // 정의된 요청 url로 새로운 커넥션을 만든다.
            Connection connection = Jsoup.connect(url);

            // connetion의 get메소드는 Document인스턴스가 반환된다
            // 요청을 GET으로 실행하고 파싱한 결과를 리턴한다.
            // Execute the request as a GET, and parse the result.
            Document document = connection.get();
            // 파싱된결과인 document 인스턴스로부터 원하는 결과를 추출해야한다.

            // 야후파이낸스 사이트에서 키와 밸류값을 찾아서 html속성에 맞게 element들을 가져온다.
            Elements parsingDivs = document.getElementsByAttributeValue("data-test", "historical-prices");
            // Elements 라는 인스턴스를 반환하는데 복수형인 이유는 이 속성을 가진 엘리먼트가 1개가 아닐 수도 있기 때문
            // 여러 엘리멘트를 찾아오기때문에 여러개를 반환한다.

            // 하지만 이 케이스는 historical-prices 값을 가지고 있는 테이블 하나만 가져오기때문에
            // get(0)을 가져오면 된다.
            // 하지만 필요한 배당금 정보외에도 불필요한 다른 정보들을 가져오기때문에 조금 더 다듬어야 한다.
            Element tableEle = parsingDivs.get(0);

            // 테이블의 tbody부분에만 필요한 정보들이 있기때문에 tbody만 가져오도록 한다.
            // thead = get(0), tfoot = get(2)
            Element tbody = tableEle.children().get(1);
            // tbody에도 하위태그들이 존재하는데 스크래핑하고싶은회사마다 배당금 지급횟수가 다를거고
            // 데이터의 갯수가 다르기때문에 인덱스로 가져오기는 힘들다.
            // tbody 안에 있는 데이터들을 순회하면서 데이터를 가져온다
            List<Dividend> dividends = new ArrayList<>();

            for (Element e : tbody.children()) {
                // 배당금 데이터만 가져오기 위해서
                String txt = e.text();
                // Dividend으로 끝나는 경우가 아니면 컨티뉴
                if (!txt.endsWith("Dividend")) {
                    continue;
                }

                // Dividend으로 끝나는 경우라면
                String[] splits = txt.split(" ");
                int month = Month.strToNumber(splits[0]);
                int day = Integer.valueOf(splits[1].replace(",", ""));
                int year = Integer.valueOf(splits[2]);
                String dividend = splits[3];

                if (month < 0) {
                    throw new RuntimeException("Unexpected Month enum value -> " + splits[0]);
                }

                dividends.add(Dividend.builder()
                        .date(LocalDateTime.of(year, month, day, 0, 0))
                        .dividend(dividend)
                        .build());
            }

            scrapResult.setDividends(dividends);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return scrapResult;
    }

    @Override
    public Company scrapCompanyByTicker(String ticker) {
        String url = String.format(SUMMARY_URL, ticker, ticker);

        try {
            Document document = Jsoup.connect(url).get();

            Element titleEle = document.getElementsByTag("h1").get(0);
            String title = titleEle.text().split("-")[1].trim();

            return Company.builder()
                    .ticker(ticker)
                    .name(title)
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
