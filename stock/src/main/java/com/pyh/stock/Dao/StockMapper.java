package com.pyh.stock.Dao;

import com.pyh.stock.Entry.Stock;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockMapper {

    int insert(Stock record);

    @Select("select * from stock order by `stock_num` asc limit #{limit} offset #{offset}")
    List<Stock> stockPull(@Param("limit") int limit, @Param("offset")int offset);

    @Select("select count(*) as stock_total from stock order by stock_num asc")
    int stockCount();

    @Update("update stock set pull_status = #{status} where stock_num = #{stock_num}")
    int updateStatus(@Param("status") int status, @Param("stock_num") String stock_num);

    @Select("select * from stock where stock_num like \'%${stock_num}%\' or stock_search_num like \'%${stock_num}%\'")
    List<Stock> stockSearch(@Param("stock_num")String stock_num);

    int insertSelective(Stock record);

    Stock selectByPrimaryKey(Integer save_id);

    int updateByPrimaryKeySelective(Stock record);

    int updateByPrimaryKey(Stock record);

    int deleteFast(); //快速删除不屑服务器记录，无法恢复数据

    int deleteNormal(); //逐条删除，写记录，可恢复

}