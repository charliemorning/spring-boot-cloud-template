package org.charlie.template.controller.v1;


import lombok.extern.slf4j.Slf4j;
import org.charlie.template.service.RequestServiceTest;
import org.charlie.template.service.ThreadServiceTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * This controller is used for test.
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class ControllerTest {

    RequestServiceTest requestServiceTest;

    ThreadServiceTest threadServiceTest;

    @Autowired
    public void setRequestServiceTest(RequestServiceTest requestServiceTest) {
        this.requestServiceTest = requestServiceTest;
    }

    @Autowired
    public void setThreadServiceTest(ThreadServiceTest threadServiceTest) {
        this.threadServiceTest = threadServiceTest;
    }

    @RequestMapping("/")
    public String testIndex() {
        log.debug("Hello World!");
        return "Hello World!";
    }

    @GetMapping("/get")
    public String testHttpClientGet(@RequestParam("path") String path) {
        log.debug(path);
        return requestServiceTest.get(path);
    }

    @GetMapping("/thread")
    public String testChildThreadTraceId() {
        threadServiceTest.childThreadLog();
        return "";
    }


}
