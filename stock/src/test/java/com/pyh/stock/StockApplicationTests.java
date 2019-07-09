package com.pyh.stock;

import com.pyh.stock.Dao.StockMapper;
import com.pyh.stock.Service.StockService;
import com.pyh.stock.stockUtil.SpringUtil;
import com.pyh.stock.stockUtil.StockCraw;
import com.pyh.stock.stockUtil.StockCrawThread;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StockApplicationTests {
//    @Autowired
//    private StockCraw stockCraw;
    @Autowired
    StockService stockService;

    @Test
    public void contextLoads() {
    }

    @Test
    public void StockCrawTest() {
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
    @Test
    public void BeanGetTest() {
        StockMapper stockMapper = SpringUtil.getBean(StockMapper.class);
        System.out.println(stockMapper);
    }
    @Test
    public void stockPullTest() {
        stockService.stockUpdate(0);
    }


}
