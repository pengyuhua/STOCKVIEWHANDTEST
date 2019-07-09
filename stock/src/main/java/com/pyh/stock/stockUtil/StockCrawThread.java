package com.pyh.stock.stockUtil;

import com.pyh.stock.Entry.Stock;

import java.util.List;

public class StockCrawThread implements Runnable{


    private List<Stock> stocks;

//    @Autowired
//    private StockCraw stockCraw;

    private String url_craw;
    private String name;

    public StockCrawThread(String name, String url) {
        this.url_craw = url;
        this.name = name;
    }

    public List<Stock> BackStock(){
        return stocks;
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        System.out.println("线程："+ this.name);
        System.out.println(this.url_craw);
        StockCraw stockCraw = new StockCraw();
        stockCraw.start(this.url_craw);
        System.out.println(stockCraw.backStocks().size());
    }
}
