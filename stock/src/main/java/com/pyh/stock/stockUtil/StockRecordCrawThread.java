package com.pyh.stock.stockUtil;

import com.pyh.stock.Entry.Stock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class StockRecordCrawThread extends Thread{
    private static final Logger logger= LoggerFactory.getLogger(StockRecordCrawThread.class);

//    private StockRecordCraw stockRecordCraw = SpringUtil.getBean(StockRecordCraw.class);
    private List<Stock> stockQuery = new ArrayList<>(); //必须初始化后才能在线程执行中访问
    private String stockRecordCrawUrl1 = "http://money.finance.sina.com.cn/quotes_service/api/json_v2.php/CN_MarketData.getKLineData?symbol=";  //数据读取接口 sz000001
    private String stockRecordCrawUrl2 = "&scale=240&ma=1&datalen=3172"; //3172 10年数据

//    @Autowired
//    private StockRecordCraw stockRecordCraw;

    private String name;

    public StockRecordCrawThread(String name, List<Stock> stocks) {
        this.name = name;
        this.stockQuery.clear();
        this.stockQuery.addAll(stocks);
    }

    @Override
    public void run() {
        logger.info("线程编号:" + Thread.currentThread().toString());
        logger.info("线程："+ this.name);
//        logger.info(stockQuery.toString());
        logger.info("stock接收大小"+ this.stockQuery.size());
        for (Stock s: this.stockQuery){
            logger.info("stock信息:"+s.toString());
            if(s.getPull_status() == 0){
                new StockRecordCraw().start(s.getStock_num().trim() ,stockRecordCrawUrl1+ s.getStock_search_num().trim() + stockRecordCrawUrl2);
            }else{
                logger.info("当前Stock历史记录已获取。");
            }
            //一小时抓取一次数据
            try {
                sleep(1000*60*60*1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
