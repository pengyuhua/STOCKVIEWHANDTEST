package com.pyh.stock.stockUtil;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.xbynet.crawler.Processor;
import com.github.xbynet.crawler.Response;
import com.github.xbynet.crawler.Site;
import com.github.xbynet.crawler.Spider;
import com.github.xbynet.crawler.parser.JsoupParser;
import com.pyh.stock.Dao.StockDetailMapper;
import com.pyh.stock.Dao.StockMapper;
import com.pyh.stock.Entry.StockDetail;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.pyh.stock.Utils.commonMethod.dataParse;

//@Component
public class StockRecordCraw extends Processor {
    private static final Logger logger = LoggerFactory.getLogger(StockRecordCraw.class);

    private String stock_num;
    private boolean craw_status = true;

    public StockRecordCraw() {
    }

    private StockDetailMapper stockDetailMapper = SpringUtil.getBean(StockDetailMapper.class);
    private StockMapper stockMapper = SpringUtil.getBean(StockMapper.class);

    @Override
    @Transactional
    public void process(Response resp) {
        logger.info("股票历史数据抓取解析开始:");
        List<StockDetail> stockDetails = dataParse(resp, stock_num);
        int resultStockDetail = stockDetailMapper.insertListData(stockDetails);
        if (resultStockDetail != -1) {
            int resultStock = stockMapper.updateStatus(1, stock_num);
            if (resultStock != -1) {
                logger.info("状态更改成功!");
                logger.info("数据写入结果: 成功!");
            } else {
                logger.info("状态更改失败!");
            }
        } else {
            logger.info("数据写入结果: 失败!");
            throw new RuntimeException("Save 抛异常了");  //异常抛出开启回滚
        }
        logger.info("股票历史数据抓取解析结束。");
}

    public void start(String stock_num, String url_craw) {
        logger.info("数据抓取地址:" + url_craw);
        this.stock_num = stock_num;
        Site site = new Site();
        Spider spider;
        spider = Spider.builder(this).threadNum(5).site(site)
                .urls(url_craw).build();
        spider.run();
    }

}
