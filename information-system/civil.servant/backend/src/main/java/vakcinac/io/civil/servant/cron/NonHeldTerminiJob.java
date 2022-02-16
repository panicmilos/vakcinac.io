package vakcinac.io.civil.servant.cron;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import vakcinac.io.civil.servant.service.TerminService;

@Component
public class NonHeldTerminiJob {
	
	private TerminService terminService;
	
	@Autowired
	public NonHeldTerminiJob(TerminService terminService) {
		this.terminService = terminService;
	}
	
	@Scheduled(cron = "0 0 23 * * *")
	public void UpdateNonHeldTermini() throws Exception {
		terminService.updateNonHeldTerminiStatus();
	}

}
