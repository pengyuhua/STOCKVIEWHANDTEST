package com.pyh.stock.stockUtil;

import com.github.xbynet.crawler.Processor;
import com.github.xbynet.crawler.Response;
import com.github.xbynet.crawler.Site;
import com.github.xbynet.crawler.Spider;
import com.pyh.stock.Dao.StockDetailMapper;
import com.pyh.stock.Dao.StockMapper;
import com.pyh.stock.Entry.StockDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.pyh.stock.Utils.commonMethod.dataParse;

@Component
@Transactional
public class StockDetailUpdate extends Processor {
    private static final Logger logger = LoggerFactory.getLogger(StockDetailUpdate.class);

    private String stock_num = "";
    @Autowired
    private StockDetailMapper stockDetailMapper;

    String dateLatest = "";
    private SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
    private String url_base1 = "http://money.finance.sina.com.cn/quotes_service/api/json_v2.php/CN_MarketData.getKLineData?symbol=";
    private String url_base2 = "&scale=5&ma=1&datalen="; //48 为一天数据
    String url_craw = "";

    @Override
    public void process(Response response) {
        logger.info("股票历史数据抓取解析开始-更新程序:");
        List<StockDetail> stockDetails = dataParse(response, stock_num);
        Collections.sort(stockDetails, new Comparator<StockDetail>() {
            @Override
            public int compare(StockDetail o1, StockDetail o2) {
                try {
                    if (formater.parse(o1.getUp_time().trim()).getTime() < formater.parse(o2.getUp_time().trim()).getTime()) {
                        return 1;
                    } else if (formater.parse(o1.getUp_time().trim()).getTime() > formater.parse(o2.getUp_time().trim()).getTime()) {
                        return -1;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
        try {
            if (formater.parse(stockDetails.get(0).getUp_time().trim()).getTime() <= formater.parse(dateLatest.trim()).getTime()) {
                logger.info("今日股票数据尚未刷新");
            } else {
                int resultStockDetail = stockDetailMapper.insertListData(stockDetails);
                if (resultStockDetail != -1) {
                    logger.info("更新数据写入结果: 成功!");
                } else {
                    logger.info("更新数据写入结果: 失败!");
                    throw new RuntimeException("Save 抛异常了");  //异常抛出开启回滚
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        logger.info("股票历史数据抓取解析结束。");
    }

    public void start(String url_craw, StockDetail stockDetail) {
        logger.info("抓取服务定位:");
        logger.info(stockDetail.toString());
        logger.info(stockDetail.getUp_time());
        long nd = 1000 * 24 * 60 * 60;
        dateLatest = stockDetail.getUp_time().trim();
        stock_num = stockDetail.getStock_num().trim();
        try {
            long diff = new Date().getTime() - formater.parse(stockDetail.getUp_time().trim()).getTime();
            long day = diff / nd;
            url_craw = url_base1 + stockDetail.getStock_search_num() + url_base2 + day * 48;
            logger.info("抓取地址:" + url_craw);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Site site = new Site();
        Spider spider = Spider.builder(this).threadNum(5).site(site)
                .urls(url_craw).build();
        spider.run();
    }
}
