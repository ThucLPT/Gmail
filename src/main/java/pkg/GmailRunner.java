package pkg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;

//@Component
public class GmailRunner implements CommandLineRunner {
	@Autowired
	private GmailService gmailService;

	@Override
	public void run(String... args) throws Exception {
		gmailService.sendHtmlMailWithInlineImage("", "");
	}
}
