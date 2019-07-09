package com.pyh.stock.stockUtil;

import com.github.xbynet.crawler.Processor;
import com.github.xbynet.crawler.Response;
import com.github.xbynet.crawler.Site;
import com.github.xbynet.crawler.Spider;
import com.github.xbynet.crawler.parser.JsoupParser;
import com.pyh.stock.Dao.StockMapper;
import com.pyh.stock.Entry.Stock;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

//<li><a href="?t=ha">沪市A股</a></li>
//<li><a href="?t=sa">深市A股</a></li>
//<li><a href="?t=hb">沪市B股</a></li>
//<li><a href="?t=sb">深市B股</a></li>

//<tr>
//<th><a href="?t=ha&amp;f=symbol&amp;o=desc&amp;p=1">代码↑</a></th>
//<th><a href="?t=ha&amp;f=name&amp;o=desc&amp;p=1">名称</a></th>
//<th><a href="?t=ha&amp;f=last&amp;o=desc&amp;p=1">最新价</a></th>
//<th><a href="?t=ha&amp;f=chg_pct&amp;o=desc&amp;p=1">涨跌幅</a></th>
//<th><a href="?t=ha&amp;f=chg&amp;o=desc&amp;p=1">涨跌额</a></th>
//<th><a href="?t=ha&amp;f=volume&amp;o=desc&amp;p=1">成交量</a></th>
//<th><a href="?t=ha&amp;f=amount&amp;o=desc&amp;p=1">成交额</a></th>
//<th><a href="?t=ha&amp;f=open&amp;o=desc&amp;p=1">今开盘</a></th>
//<th><a href="?t=ha&amp;f=hst_close&amp;o=desc&amp;p=1">昨收盘</a></th>
//<th><a href="?t=ha&amp;f=low&amp;o=desc&amp;p=1">最低价</a></th>
//<th><a href="?t=ha&amp;f=high&amp;o=desc&amp;p=1">最高价</a></th>
//</tr>

//<tr>
//<td><a href="http://finance.ifeng.com/app/hq/stock/sh600000/index.shtml" target="_blank">600000</a></td>
//<td><a href="http://finance.ifeng.com/app/hq/stock/sh600000/index.shtml" target="_blank">浦发银行</a></td>
//<td><span class="Ared">11.590</span></td>
//<td><span class="Ared">0.260%</span></td>
//<td><span class="Ared">0.03</span></td>
//<td>266176手</td>
//<td>30945万</td>
//<td><span class="Ared">11.61</span></td>
//<td>11.56</td>
//<td><span class="Ared">11.57</span></td>
//<td><span class="Ared">11.67</span></td>
//</tr>


//@Component
public class StockCraw extends Processor {
    private static final Logger logger= LoggerFactory.getLogger(StockCraw.class);

    private List<Stock> stocks = new ArrayList<>();
    private List<Stock> stockAll= new ArrayList<>();
    private boolean crawStatus = true;
    private String urlCrawLocal = "";
    private int pageNum = 1;

    private StockMapper stockMapper = SpringUtil.getBean(StockMapper.class);

    public StockCraw() { }

    @Override
    public void process(Response resp) {

        String stock_num = "";
        String stock_search_num = "";
        String stock_name = "";

        String currentUrl = resp.getRequest().getUrl();
        System.out.println("CurrentUrl:" + currentUrl);
        int respCode = resp.getCode();
        System.out.println("ResponseCode:" + respCode);
        System.out.println("type:" + resp.getRespType().name());
        //获取股票名称及代码
        JsoupParser parser = resp.html();
        if (parser != null) {
            crawStatus = true;
            Elements trs = parser.elements("div.block02 tr");
            logger.info("数据结果集大小:"+ trs.size());
            int key_num = -1;
//            有数据时解析数据
            if (trs.size() <= 2) {
                crawStatus = false;
            } else {
                for (Element e : trs) {
                    Elements tds = e.select("td a[href]");
                    if (tds.size() == 1 || tds.size() == 0)
                        continue;
                    for (Element e2 : tds) {
                        key_num++;
                        if (key_num == 0) {
                            if (!e2.text().trim().equals("上一页")) {
                                stock_num = e2.text().trim();
                            }
                        }
                        if (key_num == 1) {
                            key_num = -1;
                            if (!e2.text().trim().equals("下一页")) {
                                String str_num = e2.attr("href").trim();
//                        System.out.println((str_num.split("/")[str_num.split("/").length-2]).trim());
                                stock_search_num = (str_num.split("/")[str_num.split("/").length - 2]).trim();
//                        System.out.println(e2.text());
                                stock_name = e2.text().trim();
                            }
                        }
                    }
                    stocks.add(new Stock(stock_num, stock_search_num, stock_name));
                }
                //剔除重复数据
                Set<Stock> set = new TreeSet<Stock>((s1, s2) -> s1.getStock_num().compareTo(s2.getStock_num()));
                set.addAll(stocks);
                stocks = new ArrayList<>(set);
                stockAll.addAll(stocks);
                //抓取结果预览 存储
                for (Stock stock : stocks) {
                    logger.info(stock.toString());
                    stockMapper.insert(stock);
                }
                //有下一页时继续抓取
                if (crawStatus) {
                    ++pageNum;
                    start(urlCrawLocal + pageNum);
                }else {
                    logger.info("数据采集结束!!!");
                }
            }

        }
    }

    public void start(String url_craw) {
        if (pageNum == 1)
            urlCrawLocal = url_craw;
        stocks.clear();//重置集合空间
        Site site = new Site();
        Spider spider;
//        logger.info(pageNum);
//        logger.info("urlCrawLocal: " + urlCrawLocal);
        String craw_url = urlCrawLocal + pageNum;
        spider = Spider.builder(this).threadNum(5).site(site)
                .urls(craw_url).build();
        spider.run();
    }

    public List<Stock> backStocks(){
        return stockAll;
    }
}
