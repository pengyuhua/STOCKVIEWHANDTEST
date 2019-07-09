package com.pyh.stock.Service.ServiceImpl;

import com.pyh.stock.Dao.StockDetailMapper;
import com.pyh.stock.Dao.StockMapper;
import com.pyh.stock.Entry.Stock;
import com.pyh.stock.Entry.StockDetail;
import com.pyh.stock.Service.StockService;
import com.pyh.stock.stockUtil.StockCrawThread;
import com.pyh.stock.stockUtil.StockDetailUpdate;
import com.pyh.stock.stockUtil.StockRecordCrawThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.List;

import static com.pyh.stock.Utils.DbUtil.getResult;

@Service
public class StockServiceImpl implements StockService {
    private static final Logger logger = LoggerFactory.getLogger(StockServiceImpl.class);
    private int query_num = 1; //控制股票更新节点位置


    @Resource
    private StockMapper stockMapper;

    @Autowired
    private StockDetailMapper stockDetailMapper;
//    @Autowired
//    private StockRecordCraw stockRecordCraw;
    private String stockRecordCrawUrl1 = "http://money.finance.sina.com.cn/quotes_service/api/json_v2.php/CN_MarketData.getKLineData?symbol=";  //数据读取接口 sz000001
    private String stockRecordCrawUrl2 = "&scale=240&ma=1&datalen=3172";


    //抓取股票代码名称信息
    @Override
    public void stockCraw() {
        logger.info("《------------------------------------------------》");
        logger.info(" 股票代码获取程序开始：");
        logger.info("《------------------------------------------------》");
        int threadNum = 1;
//        String[] urls = {"ha", "sa", "hb", "sb"};
        String[] urls = {"http://app.finance.ifeng.com/list/stock.php?t=ha&f=symbol&o=asc&p=",
                "http://app.finance.ifeng.com/list/stock.php?t=sa&f=symbol&o=asc&p=",
                "http://app.finance.ifeng.com/list/stock.php?t=hb&f=symbol&o=asc&p=",
                "http://app.finance.ifeng.com/list/stock.php?t=sb&f=symbol&o=asc&p="};
        new Thread(new StockCrawThread("线程-" + threadNum++ + ":", urls[0])).start();
        new Thread(new StockCrawThread("线程-" + threadNum++ + ":", urls[1])).start();
        new Thread(new StockCrawThread("线程-" + threadNum++ + ":", urls[2])).start();
        new Thread(new StockCrawThread("线程-" + threadNum++ + ":", urls[3])).start();
    }

    @Autowired
    private StockDetailUpdate stockDetailUpdate;

    //定时更新股票最新价格信息
    @Override
    public void stockUpdate(int type) {
        //  type值用于决定初始化前十年数据或者是更新最新数据
        List<Stock> stocks;
        int limit = 100;
        int offset;
        do {
            offset = (query_num - 1) * limit;
            logger.info("offset：" + offset); //打印查询偏移量
            stocks = stockMapper.stockPull(limit, offset);
            query_num = stocks.isEmpty() ? -1 : ++query_num;
            if (!stocks.isEmpty()) {
//                    logger.info(s.toString());
                if (type == 1) {
//                    for (Stock s : stocks) {
////                        new StockRecordCraw().start( s.getStock_num(),stockRecordCrawUrl1 + s.getStock_search_num().trim() + stockRecordCrawUrl2);
//                    }
                    new StockRecordCrawThread("股票历史记录获取线程-" + query_num, stocks).start();//获取线程开启
                } else if (type == 2) {
                    for (Stock s : stocks) {
                        List<StockDetail> stockDetails = stockDetailMapper.stockDetailSelect(s.getStock_num().trim(), 1);
                        if (!stockDetails.isEmpty()) {
                            for (StockDetail s2 : stockDetails) {
//                                logger.info(s2.toString()); //根据指定股票代码获取最新价格记录数据， 提出入库时间以确定更新时间区间
                                stockDetailUpdate.start("",s2);
                            }
                            stockDetails.clear();
                        }
                    }
                }
            }
            stocks.clear();
        } while (query_num != -1);
    }

    @Override
    public List<Stock> stockPull(int limit, int offset) {
        return stockMapper.stockPull(limit, offset);
    }

    @Override
    public int stockCount() {
        return stockMapper.stockCount();
    }

    @Override
    public List<Stock> stockSearch(String stock_num) {
        return stockMapper.stockSearch(stock_num);
    }

    @Override
    public List<StockDetail> stockDetailTimeIntervalSelect(String stock_num) {
        return stockDetailMapper.stockDetailTimeIntervalSelect(stock_num);
    }

    @Override
    public List<StockDetail> frequencySelect(double intervalData, int intervalDay) {
        return stockDetailMapper.frequencySelect(intervalData, intervalDay);
    }


    @Override
    public List<StockDetail> stockDetailAllSelect(String stock_num) {
        return stockDetailMapper.stockDetailAllSelect(stock_num);
    }

    //清除股票代码等信息
    @Override
    public boolean deleteFast() {
        return getResult(stockMapper.deleteFast());
    }

    @Override
    public boolean deleteNormal() {
        return getResult(stockMapper.deleteNormal());
    }


//    @Override
//    public boolean insert(Stock record) {
//        return getResult(stockMapper.insert(record));
//    }
}
