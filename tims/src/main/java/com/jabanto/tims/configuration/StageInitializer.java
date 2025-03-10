package com.jabanto.tims.configuration;

import com.jabanto.tims.configuration.TimFXApplication.StageReadyEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * We need this class to listen the StageReadyEvent and
 * setup our JavaFX Stage when it's ready
 * this class implements ApplicationListener to listen the StageReadyEvent
 * We add component so that spring can inject this class
 */
@Component
public class StageInitializer implements ApplicationListener<StageReadyEvent> {

    // we use the value anotation to say spring here to find the fxml file
    @Value("classpath:/fxml/mainmenu.fxml")
    private Resource mainMenuResource;
    private String applicationTitle;
    private ApplicationContext applicationContext;

    public StageInitializer(@Value("${spring.application.ui.title}") String applicationTitle, ApplicationContext applicationContext) {
        this.applicationTitle = applicationTitle;
        this.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        // We want to get the Stage of the event StageReadyEvent so we can do something with the stage here
        // using the getStage method the stage is ready to be setup in our user interface

        try {

            FXMLLoader fxmlLoader = new FXMLLoader(mainMenuResource.getURL());
            // to make able to use the beans from the application context in the javafx wiring
            // we use getBean Method to provide the controllers that java fx needs
            fxmlLoader.setControllerFactory(aClass -> applicationContext.getBean(aClass));

            Parent parent = fxmlLoader.load();
            Stage stage =  event.getStage();
            stage.setScene(new Scene(parent,800,600));
            stage.setTitle(applicationTitle);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
