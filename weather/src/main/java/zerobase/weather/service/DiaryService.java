package zerobase.weather.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import zerobase.weather.WeatherApplication;
import zerobase.weather.domain.DateWeather;
import zerobase.weather.domain.Diary;
import zerobase.weather.error.InvalidDate;
import zerobase.weather.repository.DateWeatherRepository;
import zerobase.weather.repository.DiaryRepository;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
//클래스에 포함된 메소드들이 (읽기전용)트랜잭션으로 동작한다.
public class DiaryService {

    // 직접쓰지않고 애노테이션을 활용하는 이유는
    // 로컬환경, 테스트환경 등 환경별로 db가 다를 수 있기 때문에
    // 환경마다 지정을 해주고 환경분리를 해준다.
    // 코드 상에서는 환경과 무관하게 키값만 가지고 밸류를 가지고 올 수 있다.
    @Value("${openweathermap.key}")
    private String apiKey;

    private final DiaryRepository diaryRepository;
    private final DateWeatherRepository dateWeatherRepository;

    private static final Logger logger = LoggerFactory.getLogger(WeatherApplication.class);

    public DiaryService(DiaryRepository diaryRepository, DateWeatherRepository dateWeatherRepository) {
        this.diaryRepository = diaryRepository;
        this.dateWeatherRepository = dateWeatherRepository;
    }

    // 매일 새벽 1시마다 동작
    @Scheduled(cron = "0 0 1 * * *")
    @Transactional
    public void saveWeatherDate() {
        logger.info("날씨데이터 가져오기");
        dateWeatherRepository.save(getWeatherFromApi());
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void createDiary(LocalDate date, String text) {
        logger.info("started to craete diary");
        // 날씨데이터 가져오기 (API or DB)
        DateWeather dateWeather = getDateWeather(date);

        Diary nowDiary = new Diary();
        nowDiary.setDateWeather(dateWeather);
        nowDiary.setText(text);

        // mvc패턴에서 다이어리 컨트롤러에서 받아온 date와 text를
        // 다이어리 서비스 사용하고 있는데
        // 다이어리 서비스가 db를 사용하기 위해선 다이어레포지토리를 거쳐야한다.
        diaryRepository.save(nowDiary);
        logger.info("end to create diary");
    }

    @Transactional(readOnly = true)
    public List<Diary> readDiary(LocalDate date) {
        logger.debug("read diary");
//        if(date.isAfter(LocalDate.ofYearDay(3050, 1))){
//            throw new InvalidDate();
//        }
        // db에서 데이터를 조회하기 위해서 리포지토리를 통해 날짜를 기준으로 조회
        return diaryRepository.findAllByDate(date);
    }

    public List<Diary> readDiaries(LocalDate startDate, LocalDate endDate) {
        return diaryRepository.findAllByDateBetween(startDate, endDate);
    }

    public void updateDiary(LocalDate date, String text) {
        // 지정된 날짜에 해당하는 첫번째 일기를 가져온다
        Diary nowDiary = diaryRepository.getFirstByDate(date);

        nowDiary.setText(text);

        // 수정된 일기를 저장
        diaryRepository.save(nowDiary);
    }

    public void deleteDiary(LocalDate date) {
        diaryRepository.deleteAllByDate(date);
    }

    private DateWeather getDateWeather(LocalDate date) {
        // 해당날짜의 데이터가 있는지 확인
        List<DateWeather> dateWeatherListFromDB = dateWeatherRepository.findAllByDate(date);

        if (dateWeatherListFromDB.size() == 0) {
            // 해당날짜의 날씨데이터가 없다면 새로 api에서 데이터를 가져와야함
            return getWeatherFromApi();
        } else {
            return dateWeatherListFromDB.get(0);
        }
    }


    private String getWeatherString() {
        String apiUrl = "http://api.openweathermap.org/data/2.5/weather?q=seoul&APPID=" + apiKey;

        try {
            URL url = new URL(apiUrl);
            // apiUrl을 http형식으로 연결
            // httpUrlConnection을 만들어서 apiUrl(요청)을 보낼수있는 http커넥션을 열었다.
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // GET방식의 요청
            connection.setRequestMethod("GET");

            //요청에 대한 상태코드응답을 받음
            int responseCode = connection.getResponseCode();

            BufferedReader br;

            // 응답코드에 따라 실제응답 객체이나 오류메시지를 받아옴
            if (responseCode == 200) {
                br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            }

            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = br.readLine()) != null) {
                // 응답코드에 따라 들어온 실제응답을 하나씩 읽으면서 스트링빌더에 결과값을 쌓는다
                response.append(inputLine);
            }
            br.close();

            return response.toString();

        } catch (Exception e) {
            return "failed to get response";
        }
    }

    private DateWeather getWeatherFromApi() {
        DateWeather dateWeather = new DateWeather();

        // open weather map에서 날씨데이터 가져오기
        String weatherData = getWeatherString();
        // 받아온 날씨 json 파싱하기
        Map<String, Object> parseWeather = parseWeather(weatherData);

        dateWeather.setDate(LocalDate.now());
        dateWeather.setWeather(parseWeather.get("main").toString());
        dateWeather.setIcon(parseWeather.get("icon").toString());
        dateWeather.setTemperature((double) parseWeather.get("temp"));

        return dateWeather;
    }


    private Map<String, Object> parseWeather(String jsonString) {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject;

        try {
            jsonObject = (JSONObject) jsonParser.parse(jsonString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        Map<String, Object> resultMap = new HashMap<>();

        // 명시된 키값의 밸류값들을 가져옴
        JSONObject mainData = (JSONObject) jsonObject.get("main");
        resultMap.put("temp", mainData.get("temp"));

        JSONArray weatherArray = (JSONArray) jsonObject.get("weather");
        JSONObject weatherData = (JSONObject) weatherArray.get(0);
        resultMap.put("main", weatherData.get("main"));
        resultMap.put("icon", weatherData.get("icon"));

        return resultMap;
    }
}
