package com.jabanto.tims;

import com.jabanto.tims.configuration.TimFXApplication;
import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TimSpringBootLauncher {

	/**
	 * Instead of use the normal SpringBoot application  we use the created TimFXApplication to launch
	 * the JavaFX application
	 * @param args
	 */
	public static void main(String[] args) {
		Application.launch(TimFXApplication.class, args);
	}

}
