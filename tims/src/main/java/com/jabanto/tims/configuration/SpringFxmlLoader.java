package com.jabanto.tims.configuration;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class SpringFxmlLoader {

        @Autowired
        private ApplicationContext applicationContext ;

        @Value("${spring.application.ui.title}")
        private String applicationTitle;

        /**
         * Generic Window Loader for Project Overrides Controller Factory for Spring
         * and JavaFX Integration
         *
         * @param resource - FXML loaded resource.
         * @throws IOException
         */

        public void changeWindow(Resource resource) throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(resource.getURL());
            fxmlLoader.setControllerFactory(applicationContext::getBean);

            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage =  new Stage();
            stage.setTitle(applicationTitle);
            stage.setScene(scene);
            stage.show();
        }
}
