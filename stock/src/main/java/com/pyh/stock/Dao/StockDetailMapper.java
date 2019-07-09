package com.pyh.stock.Dao;

import com.pyh.stock.Entry.StockDetail;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockDetailMapper {
    @Select("select b.stock_name as stock_name, b.stock_search_num as stock_search_num, b.stock_num as stock_num, a.up_time as up_time from stock_detail a, " +
            "stock b WHERE a.stock_num = b.stock_num and b.stock_num = #{stock_num} ORDER BY a.up_time desc LIMIT #{limit}")
    List<StockDetail> stockDetailSelect(@Param("stock_num")String stock_num, @Param("limit") int limit);

    @Insert("INSERT INTO stock_detail (`stock_num`, `open`, `close`, `high`, `low`, `volume`, `up_time`) " +
            "VALUES (#{stock_num},#{open},#{close},#{high},#{low},#{volume},#{up_time})")
    int stockDetailInsert(StockDetail stockDetail);

    //过去时间端记录 信息 当前为30天
    @Select("select b.stock_name as stock_name, b.stock_num as stock_num, a.up_time as up_time, " +
            "a.`open` as `open`,a.`close` as `close`,a.`high` as `high`,a.`low` as `low`,a.`volume` as `volume`" +
            "from stock_detail a, stock b WHERE a.stock_num = b.stock_num and " +
            "DATE_SUB(CURDATE(), INTERVAL 30 DAY) <= a.up_time and a.stock_num = #{stock_num}")
    List<StockDetail> stockDetailTimeIntervalSelect(@Param("stock_num")String stock_num);

    //时间段内增幅大于某点的记录查询
    @Select("select a.stock_name, b.stock_num, count(*) as frequency from stock_detail b, stock a  " +
            "WHERE b.stock_num = a.stock_num and " +
            "(CONVERT(b.`open`, DECIMAL) - CONVERT(b.`close`, DECIMAL))/CONVERT(b.`open`, DECIMAL) > #{intervalData} " +
            "and DATE_SUB(CURDATE(), INTERVAL #{intervalDay} DAY) < b.up_time GROUP BY b.stock_num")
    List<StockDetail> frequencySelect(@Param("intervalData") double intervalData, @Param("intervalDay") int intervalDay);

    @Select("select b.stock_name as stock_name, b.stock_num as stock_num, a.up_time as up_time, " +
            "a.`open` as `open`,a.`close` as `close`,a.`high` as `high`,a.`low` as `low`,a.`volume` as `volume`" +
            "from stock_detail a, stock b WHERE a.stock_num = b.stock_num and a.stock_num = #{stock_num}")
    List<StockDetail> stockDetailAllSelect(@Param("stock_num")String stock_num);

    int insertListData(List<StockDetail> stockDetails);
}
