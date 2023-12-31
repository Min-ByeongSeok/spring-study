package hello.springmvc.basic.requestmapping;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class MappingController {

    @RequestMapping({"/hello-basic", "/hello-go"})
    public String helloBasic() {
        log.info("hello basic");

        return "ok";
    }

    @GetMapping(value = "/mapping-get-v1")
    public String mappingGetV2() {
        log.info("mappingGetV2");

        return "OK";
    }

    @GetMapping("/mapping/{userId}")
    public String mappingPath2(@PathVariable String userId) {
        log.info("mappingPath userId = {}", userId);

        return "OK";
    }

    @GetMapping("/mapping/users/{userId}/orders/{orderId}")
    public String mappingPath3(@PathVariable String userId, @PathVariable Long orderId) {
        log.info("mappingPath userid = {}, orderId = {}", userId, orderId);

        return "OK";
    }

    /**
     * 파라미터로 추가 매핑
     * params="mode",
     * params="!mode"
     * params="mode=debug"
     * params="mode!=debug" (! = )
     * params = {"mode=debug","data=good"}
     */
    @GetMapping(value = "/mapping-param", params = "mode=debug")
    public String mappingParam() {
        log.info("mappingParam");

        return "OK";
    }

    /**
     * 특정 헤더로 추가 매핑
     * headers="mode",
     * headers="!mode"
     * headers="mode=debug"
     * headers="mode!=debug" (! = )
     */
    @GetMapping(value = "/mapping-header", headers = "mode=debug")
    public String mappingHeader() {
        log.info("mappingParam");

        return "OK";
    }

    /**
     * Content-Type 헤더 기반 추가 매핑 Media Type
     * consumes="application/json"
     * consumes="!application/json"
     * consumes="application/*"
     * consumes="*\/*"
     * MediaType.APPLICATION_JSON_VALUE
     */
    @PostMapping(value = "/mapping-consume", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String mappingConsume(){
        log.info("mappingConsumes");

        return "OK";
    }

    /**
     * Accept 헤더 기반 Media Type
     * produces = "text/html"
     * produces = "!text/html"
     * produces = "text/*"
     * produces = "*\/*"
     */
    @PostMapping(value = "/mapping-produce", produces = MediaType.TEXT_HTML_VALUE)
    public String mappingProduces(){
        log.info("mappingProduces");

        return "OK";
    }
}
