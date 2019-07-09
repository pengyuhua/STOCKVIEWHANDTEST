package com.pyh.stock.Error;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorHand {
    @RequestMapping("/errorSelf")
    public String errorHand(){

        return "页面访问错误!!!";
    }
}
