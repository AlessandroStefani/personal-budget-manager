package main.view.investment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import main.control.Controller;
import main.util.AutoCompleteTextField;
import main.view.BaseScene;
import main.view.GUIFactory;
import main.view.GUIFactoryImpl;

public class InvestmentScene {

    private static final String BUY = "Buy";
    private static final String SELL = "Sell";
    private static final String STOCKTITLE = "          Market";
    private static final String SYMBOL = "Symbol    ";
    private static final String SHARE = "Share  ";
    private static final String PRICE = "Price  ";
    private static final String VALUE = "Value";

    private static final int TITLEFONTSIZE = 20;
    private static final int HEADERFONTSIZE = 10;
    private static final int TEXTFONTSIZE = 5;

    private final List<String> desc;

    private final BorderPane root;
    private Queue<List<?>> updateables;
    private final ObservableList<String> accountBox;
    private final AutoCompleteTextField symbolName;
    private final ComboBox<String> accountComboBox;
    private final Controller controller;
    private final GUIFactory guiFactory;

    public InvestmentScene(final BorderPane root, final Controller controller) {
        final GUIFactoryImpl.Builder b = new GUIFactoryImpl.Builder(Screen.getPrimary().getBounds().getWidth(),
                Screen.getPrimary().getBounds().getHeight());
        this.guiFactory = b.build();

        desc = new ArrayList<>();
        desc.add(SYMBOL);
        desc.add(PRICE);
        desc.add(SHARE);
        desc.add(VALUE);

        this.root = root;
        this.controller = controller;
        this.accountBox = FXCollections.observableArrayList();
        this.accountComboBox = new ComboBox<>(accountBox);
        this.symbolName = new AutoCompleteTextField();
        setMarketHoldings();
        createContentDisplay();
        createMenu();
    }

    // interface and functionalities
    @SuppressWarnings("unchecked")
    private void createMenu() {
        final Pane bottomBar = this.guiFactory.createHorizontalPanel();
        final Button buy = this.guiFactory.createButton(BUY), sell = this.guiFactory.createButton(SELL);
        final TextField numberShare = new TextField("1.0");

        symbolName.setPromptText("symbol name");
        numberShare.setPromptText("share number");

        // force the field to be numeric only
        // some Regex expression. This solution was found on stack overflow.
        numberShare.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> observable, final String oldValue,
                    final String newValue) {
                if (!newValue.matches("(-?\\d+\\.?\\d*)")) {
                    numberShare.setText(newValue.replaceAll("[^\\d*]", ""));
                }
            }
        });

        this.symbolName.setTextFormatter(new TextFormatter<>((change) -> {
            change.setText(change.getText().toUpperCase());
            return change;
        }));

        buy.setOnAction(e -> {
            this.controller.buyStocks(this.symbolName.getText(), Double.parseDouble(numberShare.getText()),
                    accountComboBox.getValue());
        });

        sell.setOnAction(e -> {
            this.controller.sellStocks(this.symbolName.getText(), Double.parseDouble(numberShare.getText()),
                    this.accountComboBox.getValue());
        });

        bottomBar.getChildren().addAll(this.accountComboBox, this.symbolName, numberShare, buy, sell);

        this.root.setBottom(bottomBar);
    }

    // content display that are updateble
    @SuppressWarnings("unchecked")
    private void createContentDisplay() {
        final Iterator<List<?>> iter = this.updateables.iterator();
        final List<String> symbols = (List<String>) iter.next();

        // maybe createScheda should have been built differently, but since it's for
        // GUI, not computational model,
        // I think a bit redundancy can't be avoided without losing flexibility;
        final Node n = this.guiFactory.createBlockScheda(this.guiFactory.createText(STOCKTITLE, TITLEFONTSIZE),
                this.guiFactory.transformStringIntoText(this.desc, HEADERFONTSIZE),
                this.guiFactory.transformStringIntoText(symbols, TEXTFONTSIZE),
                this.guiFactory.transformStringIntoText(iter.next(), TEXTFONTSIZE),
                this.guiFactory.transformStringIntoText(iter.next(), TEXTFONTSIZE),
                this.guiFactory.transformStringIntoText(iter.next(), TEXTFONTSIZE));
        this.accountBox.clear();
        this.accountBox.addAll((Collection<? extends String>) iter.next());
        this.accountComboBox.getSelectionModel().selectFirst();
        this.symbolName.getEntries().clear();
        this.symbolName.getEntries().addAll(symbols);

        this.root.setLeft(this.guiFactory.createHorizontalPanel());
        this.root.setRight(this.guiFactory.createHorizontalPanel());
        this.root.setCenter(n);
    }

    private void setMarketHoldings() {
        this.updateables = this.controller.updateMarketInfo();
    }

}
