package com.pyh.stock.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Configuration
@EnableScheduling
public class ScheduleJob {

    //  Cron表达式是一个字符串，字符串以5或6个空格隔开，分为6或7个域，每一个域代表一个含义，Cron有如下两种语法格式：
    //  1.Seconds Minutes Hours DayofMonth Month DayofWeek Year
    //  2.Seconds Minutes Hours DayofMonth Month DayofWeek
//    每一个域都使用数字，但还可以出现如下特殊字符，它们的含义是：
//            (1) *：表示匹配该域的任意值，假如在Minutes域使用*, 即表示每分钟都会触发事件。
//            (2) ?：只能用在DayofMonth和DayofWeek两个域。它也匹配域的任意值，但实际不会。因为DayofMonth和 DayofWeek会相互影响。例如想在每月的20日触发调度，不管20日到底是星期几，则只能使用如下写法： 13 13 15 20 * ?, 其中最后一位只能用？，而不能使用*，如果使用*表示不管星期几都会触发，实际上并不是这样。
//            (3) -：表示范围，例如在Minutes域使用5-20，表示从5分到20分钟每分钟触发一次
//            (4) /：表示起始时间开始触发，然后每隔固定时间触发一次，例如在Minutes域使用5/20,则意味着5分钟触发一次，而25，45等分别触发一次.
//            (5) ,：表示列出枚举值值。例如：在Minutes域使用5,20，则意味着在5和20分每分钟触发一次。
//            (6) L：表示最后，只能出现在DayofWeek和DayofMonth域，如果在DayofWeek域使用5L,意味着在最后的一个星期四触发。
//            (7) W：表示有效工作日(周一到周五),只能出现在DayofMonth域，系统将在离指定日期的最近的有效工作日触发事件。例如：在 DayofMonth使用5W，如果5日是星期六，则将在最近的工作日：星期五，即4日触发。如果5日是星期天，则在6日(周一)触发；如果5日在星期一 到星期五中的一天，则就在5日触发。另外一点，W的最近寻找不会跨过月份。
//            (8) LW：这两个字符可以连用，表示在某个月最后一个工作日，即最后一个星期五。
//            (9) #：用于确定每个月第几个星期几，只能出现在DayofMonth域。例如在4#2，表示某月的第二个星期三。
//   例子:
//    每隔5秒执行一次："*/5 * * * * ?"
//    每隔1分钟执行一次："0 */1 * * * ?"
//    每天23点执行一次："0 0 23 * * ?"
//    每天凌晨1点执行一次："0 0 1 * * ?"
//    每月1号凌晨1点执行一次："0 0 1 1 * ?"
//    每月最后一天23点执行一次："0 0 23 L * ?"
//    每周星期天凌晨1点实行一次："0 0 1 ? * L"
//    在26分、29分、33分执行一次："0 26,29,33 * * * ?"
//    每天的0点、13点、18点、21点都执行一次："0 0 0,13,18,21 * * ?"
//    表示在每月的1日的凌晨2点调度任务："0 0 2 1 * ? *"
//    表示周一到周五每天上午10：15执行作业："0 15 10 ? * MON-FRI"
//    表示2002-2006年的每个月的最后一个星期五上午10:15执行："0 15 10 ? 6L 2002-2006"

    private static final Logger logger = LoggerFactory.getLogger(ScheduleJob.class);
    @Autowired
    private StockService stockService;

    //每天16点进行数据抓取操作
    @Scheduled(cron = "0 0 0,16 * * ?")
    public void scheduleJob() {
        logger.info("定时打印信息：" + LocalDateTime.now());
        logger.info("股票数据更新任务开始:");
        stockService.stockUpdate(2);
    }

//    @Scheduled(fixedRate = 5000)
//    public void scheduleTest02() {
//        logger.info("定时打印信息2：" + LocalDateTime.now());
//    }

//    可实现 InitializingBean
//    重写该方法实现启动服务执行代码
//    @Override
//    public void afterPropertiesSet() throws Exception {
//    }
}
