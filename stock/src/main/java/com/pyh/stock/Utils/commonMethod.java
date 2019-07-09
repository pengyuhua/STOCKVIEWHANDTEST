package com.pyh.stock.Utils;

import com.github.xbynet.crawler.Response;
import com.github.xbynet.crawler.parser.JsoupParser;
import com.pyh.stock.Entry.StockDetail;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class commonMethod {
    private static final Logger logger= LoggerFactory.getLogger(commonMethod.class);
    private static boolean craw_status = true;
    private static List<StockDetail> stockDetails = new ArrayList<>();

    public static List<StockDetail> dataParse(Response response,String stock_num) {
        JsoupParser jsoupParserparser = response.html();
        String data = jsoupParserparser.elements("body").text().trim()
                .replace("open", "\"open\"")
                .replace("high", "\"high\"")
                .replace("low", "\"low\"")
                .replace("close", "\"close\"")
                .replace("day", "\"day\"")
                .replace("volume", "\"volume\"");
        JSONArray jsonArray = null;
        try {
            jsonArray = JSONArray.fromObject(data.trim()); //抓取数据失败执行回滚事务
            craw_status = true;
        } catch (Exception e) {
            craw_status = false;
        }
        if (craw_status) {
            stockDetails.clear();
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                StockDetail stockDetail = new StockDetail();
                stockDetail.setStock_num(stock_num);
                stockDetail.setOpen(jsonObject.getString("open"));
                stockDetail.setClose(jsonObject.getString("close"));
                stockDetail.setHigh(jsonObject.getString("high"));
                stockDetail.setLow(jsonObject.getString("low"));
                stockDetail.setVolume(jsonObject.getString("volume"));
                stockDetail.setUp_time(jsonObject.getString("day"));
                stockDetails.add(stockDetail);
                System.out.println(stockDetail.toString()); //打印解析结果
            }
        }else {
            logger.info("数据抓取失败，被发现喽0.0");
        }
        return stockDetails;
    }
}
