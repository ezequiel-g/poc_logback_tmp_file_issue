package eze.poc.logback.logback_tmpfile;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class App {
	
	private final int multiplicationFactor;
	
	private static final Logger LOG = LoggerFactory.getLogger(App.class);

	/**
	 * @param multiplicationFactor
	 */
	public App(final int multiplicationFactor) {
		this.multiplicationFactor = multiplicationFactor;
	}

	public static void main(final String[] args) {
		final int multiplicationFactor = 6;
		final int repeat = 1000;
		
		final App app = new App(multiplicationFactor);
		
		app.log(repeat);
	}
	
	private String buildRandomMessage() {
		final String randomMessage = UUID.randomUUID().toString();
		final int messageMinChars = randomMessage.length() * multiplicationFactor;
				
		final StringBuilder sb = new StringBuilder(randomMessage);
		
		do {
			sb.append('[')
				.append(UUID.randomUUID())
				.append(']');
			
		} while (sb.length() < messageMinChars);
		
		return sb.toString();
	}
	
	private void log(final int repeat) {
		int iteraction = 0;
		do {
			LOG.trace("iteration: {} message: {}", iteraction, buildRandomMessage());
			LOG.debug("iteration: {} message: {}", iteraction, buildRandomMessage());	
			LOG.info("iteration: {} message: {}", iteraction, buildRandomMessage());
			LOG.warn("iteration: {} message: {}", iteraction, buildRandomMessage());
			LOG.error("iteration: {} message: {}", iteraction, buildRandomMessage());			
		} while (repeat > iteraction++);
	}
}
