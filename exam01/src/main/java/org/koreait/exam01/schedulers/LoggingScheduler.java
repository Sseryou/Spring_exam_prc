package org.koreait.exam01.schedulers;

import lombok.extern.java.Log;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Log //로그를 띄우도록 설정
public class LoggingScheduler {

    //@Scheduled(cron="*/5 * * * * *")
    public void logging(){
        //시스템 로그에 5초마다 실행...이 뜰것이다...
        log.info("5초마다 실행...");
    }

}
