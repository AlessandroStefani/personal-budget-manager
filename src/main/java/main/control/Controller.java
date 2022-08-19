package main.control;

import java.util.List;
import java.util.Queue;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import main.model.profile.ProfileCredentials;
import main.model.profile.ProfileEconomy;

/**
 * This interface does all chores to interact with user.
 * Such as operation IO, calling model for calculations and etc.
 * 
 * All functions are kind of notification sent from View
 *
 */
public interface Controller {
	
	// Part of Song, All possible interactions from users.
	
	void buyStocks(String symbol, double shares, String id);
	
	void sellStocks(String symbol, double shares, String id);
	
	Queue<List<?>> updateMarketInfo();
	
	void terminateApp();

	// Ale's part

	void showProfile(BorderPane root, Stage stage);

    void registerProfile(String name, String surname, String fc, String eMail, String password);

    void accessProfile(String eMail, String password);

    void showLoginView(Stage window);

    void showPasswordChangeView();

    ProfileCredentials getUsrInfo();

    void changePword(String strategy, String newPword, String confPword, String id);

    ProfileEconomy getUsrEconomy();

    void showMainScene(Stage window);

    void showRegistrationView(Stage window);

    void showInvestmentPage(BorderPane root);
}
