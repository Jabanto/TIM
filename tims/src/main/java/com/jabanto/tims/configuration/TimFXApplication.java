package com.jabanto.tims.configuration;

import com.jabanto.tims.TimSpringBootLauncher;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * These is the JavaFx Application that will be launch in Spring boot application
 * needs to extends abstract Application abstract class
 *
 */
public class TimFXApplication extends Application {

    // To make use of the spring functionality we create a configurable application context
    // We well call this context in the start() method of the javaFX
    private ConfigurableApplicationContext applicationContext;

    @Override
    /**
     * We need to override this class to initialize the application context
     */
    public void init(){
        // We load the context in our java fx application a give the spring boot application class
        // We get run to get the application context
        applicationContext = new SpringApplicationBuilder(TimSpringBootLauncher.class).run();
    }

    @Override
    public void stop(){
        // We close the application context
        applicationContext.close();
        // and exit our JAVAFx application
        Platform.exit();
    }

    @Override
    public void start(Stage stage){

        /* Lets use Spring boot features calling spring boot context
        *
        *  we use the spring pattern of publishing events via the application context
        * to signal when the stage is ready, we give the StageReadyEvent the Stage, so that anything listening to that
        * event has access to the stage
        * */
        applicationContext.publishEvent(new StageReadyEvent(stage));
    }

    // We create an inner class to define the Stage ready event
    public static class StageReadyEvent extends ApplicationEvent {
        public StageReadyEvent(Stage stage) {
            super(stage);
        }

        // here we return the Stage for that we use the method getSource that returns and object
        // to retrun the stage we cast the method into Stage
        // we know already that getSource ist a Stage cuz we get before in the super constructor
        public Stage getStage() {
            return ((Stage)getSource());
        }
    }
}
