package main.view.profile;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import main.view.GUIFactory;
import main.view.GUIFactoryImpl;
import main.control.Controller;

public class LoginView {

    private static final int W_RATIO = 5;
    private static final int H_RATIO = 3;

    private final BorderPane root;

    public LoginView(final Controller controller, final Stage stage) {
        final GUIFactoryImpl.Builder b = new GUIFactoryImpl.Builder(Screen.getPrimary().getBounds().getWidth(),
                Screen.getPrimary().getBounds().getHeight());
        final GUIFactory guiFactory = b.build();

        this.root = new BorderPane();

        final Pane textFieldLayout = guiFactory.createVerticalPanel();
        final TextField eMail = new TextField();
        eMail.setPromptText("e-Mail");
        final TextField password = new PasswordField();
        password.setPromptText("password");

        final Pane buttonLayout = guiFactory.createHorizontalPanel();
        final Button access = guiFactory.createButton("Accedi");
        access.setOnAction(e -> {
            //roba da fare con json
            controller.showMainScene(stage);
        });

        final Button register = guiFactory.createButton("Registrati");
        register.setOnAction(e -> {
            controller.showRegistrationView(stage);
        });

        buttonLayout.getChildren().addAll(access, register);
        textFieldLayout.getChildren().addAll(eMail, password);
        this.root.setTop(textFieldLayout);
        this.root.setBottom(buttonLayout);
        stage.setScene(getScene());
        stage.centerOnScreen();
        stage.show();
    }

    private  Scene getScene() {
        return new Scene(this.root, Screen.getPrimary().getBounds().getWidth() / W_RATIO, Screen.getPrimary().getBounds().getHeight() / H_RATIO);
    }
}
