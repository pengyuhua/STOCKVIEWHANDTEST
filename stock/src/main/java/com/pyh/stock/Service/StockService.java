package com.pyh.stock.Service;

import com.pyh.stock.Entry.Stock;
import com.pyh.stock.Entry.StockDetail;

import java.util.List;

public interface StockService {

    void stockCraw();

    void stockUpdate(int type);

    List<Stock> stockPull(int limit, int offset);

    //获取数据总条数
    int stockCount();

    List<Stock> stockSearch(String stock_num);

    List<StockDetail> stockDetailTimeIntervalSelect(String stock_num); //获取时间区间内指定股票数据

    //获取涨幅次数 时间段内

    /**
     *
     * @param intervalData 涨幅大小 double类型 如 0.05 即5%
     * @param intervalDay  时间区间 int类型 如30 为过去30天内
     * @return  查询结果集
     */
    List<StockDetail> frequencySelect(double intervalData, int intervalDay);

    List<StockDetail> stockDetailAllSelect(String stock_num); //获取所有指定股票数据

//    void stockRecordPull();

//    boolean insert(Stock record);

    boolean deleteFast(); //快速删除不屑服务器记录，无法恢复数据

    boolean deleteNormal();  //逐条删除，写记录，可恢复

}
