package pkg;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class GmailConfig {
	/*
	 * in order to get i18n to work, method's name must be "messageSource", or
	 * define as @Bean("messageSource")
	 */
	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("MailMessages");
		return messageSource;
	}
}
