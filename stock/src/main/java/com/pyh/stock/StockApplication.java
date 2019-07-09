package com.pyh.stock;

import com.pyh.stock.stockUtil.SpringUtil;
import com.pyh.stock.stockUtil.StockCrawThread;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@MapperScan("com.pyh.stock.Dao")
@Import(SpringUtil.class)
public class StockApplication {

    public static void main(String[] args) {
        SpringApplication.run(StockApplication.class, args);
    }

}
