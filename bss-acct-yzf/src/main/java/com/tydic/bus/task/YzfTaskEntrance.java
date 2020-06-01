package com.tydic.bus.task;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
/**
 * 翼支付定时任务入口
 * @author zhangtw
 *
 */
@Service
public class YzfTaskEntrance {
	
	@Autowired
	YzfMainTask yzfMainTask;
	
	//@Scheduled(cron = "*/10 * * * * ?")//每10秒执行一次
	@Scheduled(cron = "${yzf.scheduled.290}")
	public void yzfTask290() {
		yzfMainTask.executeMainTask("290");
	}
	
	@Scheduled(cron = "${yzf.scheduled.910}")
	public void yzfTask910() {
		yzfMainTask.executeMainTask("910");
	}
	
	@Scheduled(cron = "${yzf.scheduled.911}")
	public void yzfTask911() {
		yzfMainTask.executeMainTask("911");
	}
	
	@Scheduled(cron = "${yzf.scheduled.912}")
	public void yzfTask912() {
		yzfMainTask.executeMainTask("912");
	}
	
	@Scheduled(cron = "${yzf.scheduled.913}")
	public void yzfTask913() {
		yzfMainTask.executeMainTask("913");
	}
	
	@Scheduled(cron = "${yzf.scheduled.914}")
	public void yzfTask914() {
		yzfMainTask.executeMainTask("914");
	}
	
	@Scheduled(cron = "${yzf.scheduled.915}")
	public void yzfTask915() {
		yzfMainTask.executeMainTask("915");
	}
	
	@Scheduled(cron = "${yzf.scheduled.916}")
	public void yzfTask916() {
		yzfMainTask.executeMainTask("916");
	}
	
	@Scheduled(cron = "${yzf.scheduled.917}")
	public void yzfTask917() {
		yzfMainTask.executeMainTask("917");
	}
	
	@Scheduled(cron = "${yzf.scheduled.919}")
	public void yzfTask919() {
		yzfMainTask.executeMainTask("919");
	}
	
	

}
