package vakcinac.io.civil.servant.cron;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import vakcinac.io.civil.servant.service.RedCekanjaService;

@Component
public class AssignTerminsJob {
	
	private RedCekanjaService redCekanjaService;
	
	@Autowired
	public AssignTerminsJob(RedCekanjaService redCekanjaService) {
		this.redCekanjaService = redCekanjaService;
	}
	
	@Scheduled(fixedDelay = 10, initialDelay = 5, timeUnit = TimeUnit.MINUTES)
	public void AssignTermins() throws Exception {
		redCekanjaService.tryToAssignTermins();
	}

}
