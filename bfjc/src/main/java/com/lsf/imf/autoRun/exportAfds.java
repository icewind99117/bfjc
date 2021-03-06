package com.lsf.imf.autoRun;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.lsf.imf.ControlBoard;
import com.lsf.imf.ftp.FtpUploadClient;
import com.lsf.imf.mapper.MsgStoreMapper;

@Component
@Order(value=1)
public class exportAfds implements CommandLineRunner {
	private static Logger log = LoggerFactory.getLogger(exportAfds.class);
	
	@Value("${afds.export.fixTime}")
	private String timerFixTime;
	
	@Value("${afds.export.intervalMin}")
	private int timerIntervalMin;
	
	@Autowired
	FtpUploadClient ftpUploadClient;

	@Override
	public void run(String... args) throws Exception {
        try {
			log.info("启动自动程序");
			log.info("timerFixTime:"+timerFixTime);
			log.info("mq参数加载测试:"+ControlBoard.getDsClient().getInitParams());
			log.info("ftp参数加载测试:"+ftpUploadClient.getInitParams());
			// 间隔毫秒数(参数配置以分钟为单位)
	        long interval = timerIntervalMin * 60 * 1000;
	        // 设置首次运行时间
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd "+timerFixTime);
            Date startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sdf.format(new Date()));
            log.info("init startTime:"+startTime);
            
            // 如果当前时间的已经过了 首次运行时间，则往后顺延间隔时间
            while (System.currentTimeMillis() > startTime.getTime()){
                startTime = new Date(startTime.getTime() + interval);
                log.info("change StartTime:"+startTime);
            }
            
            log.info("final startTime:"+startTime);
            
            Timer t = new Timer();
            //启动定时任务
            t.schedule(new TimerTask() {
                @Override
                public void run() {
                    log.info("autorunner excel upload timer start!");
                    ftpUploadClient.uploadAfdsInfoOf3Daysago();
                }
            }, startTime, interval);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		
	}

}
