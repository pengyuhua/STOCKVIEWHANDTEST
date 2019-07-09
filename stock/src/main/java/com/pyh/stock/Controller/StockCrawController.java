package com.pyh.stock.Controller;

import com.pyh.stock.Service.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/stock")
public class StockCrawController {
    private final Logger logger = LoggerFactory.getLogger(StockCrawController.class);

    @Autowired
    StockService stockService;

    //  抓取十年数据
    @RequestMapping("/crawRecord")
    public void stockRecordCraw(){
        stockService.stockUpdate(1);
    }

    //抓取股票代码等信息
    @RequestMapping("/craw")
    public void stockCraw(){
        stockService.stockCraw();
    }

    //    一键清空数据
    @RequestMapping("/delFast")
    public void deleteFast(){
        logger.info(stockService.deleteFast()? "删除完成!" : "删除失败!");
    }
    @RequestMapping("/delNormal")
    public void deleteNormal(){
        logger.info(stockService.deleteNormal()? "删除完成!" : "删除失败!");
    }
}
