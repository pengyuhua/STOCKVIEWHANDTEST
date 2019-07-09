package com.pyh.stock.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pyh.stock.Entry.Stock;
import com.pyh.stock.Entry.StockDetail;
import com.pyh.stock.Service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/stockData")
public class StockController {
    @Autowired
    StockService stockService;
    @Autowired
    ObjectMapper objectMapper;

    //股票总条数
    @RequestMapping("/count")
    String stockCount(){
        return "[{\"stock_total\": " + stockService.stockCount() +"}]";
    }

    @RequestMapping("/search")
    String stockSearch(@RequestParam("stock_num")String stock_num){
        List<Stock> stockList = stockService.stockSearch(stock_num);
        try {
            return stockList.isEmpty() ? "[]" : objectMapper.writeValueAsString(stockService.stockSearch(stock_num));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
    }

    @RequestMapping("/select")
    String stockPull(@RequestParam("limit")int limit, @RequestParam("offset")int offset){
        List<Stock> stockList = stockService.stockPull(limit, offset);
        try {
            return stockList.isEmpty() ? "[]" : objectMapper.writeValueAsString(stockList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "[]";
        }
    }

    //查询指定时间段股票交易信息
    @RequestMapping("/latest")
    String stockDetailTimeIntervalSelect(@RequestParam("stock_num")String stock_num){
        try {
            return objectMapper.writeValueAsString(stockService.stockDetailTimeIntervalSelect(stock_num));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "[]";
        }
    }

    //查询指定时间段内 涨幅次数
    @RequestMapping("/frequencySelect")
    public String frequencySelect(@RequestParam("intervalData")double intervalData, @RequestParam("intervalDay")int intervalDay){
        try {
            return objectMapper.writeValueAsString(stockService.frequencySelect(intervalData, intervalDay));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "[]";
        }
    }

    //查询指定股票所有交易信息 （10年内）
    @RequestMapping("/all")
    String stockDetailAllSelect(@RequestParam("stock_num")String stock_num){
        try {
            return objectMapper.writeValueAsString(stockService.stockDetailAllSelect(stock_num));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "[]";
        }
    }

}
