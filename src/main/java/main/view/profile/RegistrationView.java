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

public class RegistrationView {

    private static final int W_RATIO = 5;
    private static final int H_RATIO = 3;

    private final BorderPane root;

    public RegistrationView(final Controller controller, final Stage stage) {
        final GUIFactoryImpl.Builder b = new GUIFactoryImpl.Builder(Screen.getPrimary().getBounds().getWidth(),
                Screen.getPrimary().getBounds().getHeight());
        final GUIFactory guiFactory = b.build();

        this.root = new BorderPane();
        final Pane textFieldLayout = guiFactory.createVerticalPanel();

        final TextField name = new TextField();
        name.setPromptText("nome");
        final TextField surname = new TextField();
        surname.setPromptText("cognome");
        final TextField fc = new TextField();
        fc.setPromptText("codice fiscale");
        final TextField eMail = new TextField();
        eMail.setPromptText("e-Mail");
        final TextField password = new PasswordField();
        password.setPromptText("password");
        final TextField confPass = new PasswordField();
        confPass.setPromptText("conferma password");

        final Pane buttonLayout = guiFactory.createHorizontalPanel();
        final Button register = guiFactory.createButton("Registrati");
        register.setOnAction(e -> {
            if (checkInputs(eMail.getText(), password.getText(), confPass.getText())) {
                controller.registerProfile(name.getText(), surname.getText(), fc.getText(), eMail.getText(), password.getText());
                controller.showMainScene(stage);
            } else {
                guiFactory.createInformationBox("Email non valida o Password non coincidenti").showAndWait();
            }
        });
        final Button login = guiFactory.createButton("Accedi");
        login.setOnAction(e -> {
            controller.showLoginView(stage);
        });

        buttonLayout.getChildren().addAll(register, login);
        textFieldLayout.getChildren().addAll(name, surname, fc, eMail, password, confPass);
        this.root.setBottom(buttonLayout);
        this.root.setCenter(textFieldLayout);
        stage.setScene(getScene());
        stage.centerOnScreen();
        stage.show();
    }

    private boolean checkInputs(final String eMail, final String password, final String confPass) {
        return eMail.contains("@") && password.equals(confPass);
    }

    private Scene getScene() {
        return new Scene(this.root, Screen.getPrimary().getBounds().getWidth() / W_RATIO, Screen.getPrimary().getBounds().getHeight() / H_RATIO);
    }
}
