package main.view;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.common.base.Optional;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import main.control.Controller;
import main.control.ControllerImpl;
import main.model.account.NotEnoughFundsException;
import main.model.market.Equity;
import main.model.market.OrderImpl;
import main.view.investment.InvestmentScene;
import main.view.profile.LoginView;

public class JavaFxView extends Application implements View {

    //in order to avoid using static here, We need a way to call controller on the main application thread
    //otherwise it will be null for those components who is calling this object from the Javafx thread.
    private volatile GUIFactory guiFactory;
    private static volatile Controller controller;


    private final BorderPane root = new BorderPane();
    private Stage window;

    @Override
    public void start(final Stage primaryStage) throws Exception {
        final GUIFactoryImpl.Builder b = new GUIFactoryImpl.Builder(Screen.getPrimary().getBounds().getWidth(),
                Screen.getPrimary().getBounds().getHeight());
        guiFactory = b.build();

        this.window = primaryStage;

        this.window.setTitle("Bugmate - personal use");
        controller.showLoginView(this.window);

        primaryStage.setOnCloseRequest(event -> {
            System.out.println("Stage is closing");
            controller.terminateApp();
        });
    }

    @Override
    public void setObserver(final Controller observer) {
        controller = observer;
    }

    @Override
    public void show(final String[] args) {
        launch(args);
    }

    @Override
    public void marketUpdates(final Queue<List<?>> queue) {

//        Platform.runLater(() -> {
//            try {
//                investScene.updateEverythingNeeded(queue);
//                this.window.setScene(investScene.getScene());
//            } catch (IllegalArgumentException e) {
//                showMessage("something went wrong, cound't update the market info, please check out your internet.");
//            }
//        });

    }

    @Override
    public void showMessage(final String message) {
        Platform.runLater(() -> guiFactory.createInformationBox(message).showAndWait());

    }

    @Override
    public void setController(final ControllerImpl controllerImpl) {
        this.controller = controllerImpl;
    }

}
