package zerobase.weather.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import zerobase.weather.domain.Diary;
import zerobase.weather.service.DiaryService;

import java.time.LocalDate;
import java.util.List;

// http 응답을 보낼 때 상태코드들을 컨트롤러에서 지정을 해서
// 내려줄 수 있다.
@RestController
public class DiaryController {

    private final DiaryService diaryService;

    public DiaryController(DiaryService diaryService) {
        this.diaryService = diaryService;
    }

    @ApiOperation(value = "일기 텍스트와 날씨를 이용해서 DB에 일기 저장", notes = "이것은 노트")
    @PostMapping("/create/diary")
    // 주로 데이터를 조회할때 Get
    // 저장할 때는 Post를 많이 사용함
    // 위의 매핑된 path로 접속하면 아래의 메소드가 실행된다.
    void createDiary(
            // 파라미터형식으로 요청할때 필요하다.
            @RequestParam
            // 시간포맷 지정
            @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
            LocalDate date,
            // POST API는 바디에 데이터를 넣어 전송할 수 있는데
            // 아래 코드는 요청할 때 바디에 문자열값을 넣어주겠다 라는 의미를 가진다.
            @RequestBody
            String text
    ){
        // 컨트롤러에 어떠한 경로로 어떻게 요청을 받을 것이며 어떠한 값들을 클라이언트로부터
        // 받을것인지를 명시를 해주었다.

        // 컨트롤러는 받은 값을 서비스에 전달해주어야 한다.
        diaryService.createDiary(date, text);
    }

    //디폴트는 value값으로 인식
    @ApiOperation("선택한 날짜의 모든 일기 데이터를 가져오기")
    @GetMapping("/read/diary")
    List<Diary> readDiary(
            @RequestParam
            @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
            LocalDate date
    ){
        // 날짜를 기준으로 일기 조회하기
        return diaryService.readDiary(date);
    }

    @ApiOperation("선택한 기간중의 모든 일기 데이터를 가져오기")
    @GetMapping("/read/diaries")
    List<Diary> readDiaries(
            @RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) @ApiParam(value = "조회할 기간의 첫번째날", example = "2020-02-02")
            LocalDate startDate,
            @RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) @ApiParam(value = "조회할 기간의 마지막날", example = "2020-02-02")
            LocalDate endDate
    ){
        return diaryService.readDiaries(startDate, endDate);
    }

    @PutMapping("/update/diary")
    void updateDiary(
            @RequestParam
            @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
            LocalDate date,
            @RequestBody
            String text
    ){
        diaryService.updateDiary(date, text);
    }

    @DeleteMapping("/delete/diary")
    void deleteDiary(
            @RequestParam
            @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
            LocalDate date
    ){
        diaryService.deleteDiary(date);
    }
}
