package com.pyh.stock.Entry;

public class Stock {
    private Integer save_id;

    private Integer pull_status;

    private String stock_num;;

    private String stock_search_num;

    private String stock_name;

    public Stock() {}

    public Stock(String stock_num, String stock_search_num, String stock_name) {
        this.stock_num = stock_num;
        this.stock_search_num = stock_search_num;
        this.stock_name = stock_name;
    }

    public Integer getPull_status() {
        return pull_status;
    }

    public void setPull_status(Integer pull_status) {
        this.pull_status = pull_status;
    }

    public Integer getSave_id() {
        return save_id;
    }

    public void setSave_id(Integer save_id) {
        this.save_id = save_id;
    }

    public String getStock_num() {
        return stock_num;
    }

    public void setStock_num(String stock_num) {
        this.stock_num = stock_num == null ? null : stock_num.trim();
    }

    public String getStock_search_num() {
        return stock_search_num;
    }

    public void setStock_search_num(String stock_search_num) {
        this.stock_search_num = stock_search_num == null ? null : stock_search_num.trim();
    }

    public String getStock_name() {
        return stock_name;
    }

    public void setStock_name(String stock_name) {
        this.stock_name = stock_name == null ? null : stock_name.trim();
    }

    @Override
    public String toString() {
        return "Stock{" +
                "save_id=" + save_id +
                ", pull_status=" + pull_status +
                ", stock_num='" + stock_num + '\'' +
                ", stock_search_num='" + stock_search_num + '\'' +
                ", stock_name='" + stock_name + '\'' +
                '}';
    }
}