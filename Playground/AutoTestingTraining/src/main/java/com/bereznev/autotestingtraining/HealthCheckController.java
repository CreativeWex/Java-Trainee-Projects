package com.bereznev.autotestingtraining;
/*
    =====================================
    @author Bereznev Nikita @CreativeWex
    =====================================
 */

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @RequestMapping("/health/check")
    public @ResponseBody String check() {
        return "OK";
    }
}
