package main.view;

import java.util.Collection;
import java.util.List;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import main.util.Pair;

/**
 * This interface models various GUI components in javafx being used
 * repetitively, so i gathered everything into a factory with Builder. Builder
 * is because there will be a lot of configurations to set up, so i decided to
 * use this pattern to avoid multiple parameters in one constructor. However, I
 * should set up those configurations from an xml file, instead of doing tedious
 * calculations. So let's open it up for pull request :) .
 *
 */
public interface GUIFactory {

    /**
     * create a HBox in javafx.
     * 
     * @return HBox
     */
    Pane createHorizontalPanel();


    /**
     * create a VBox in javafx.
     * 
     * @return VBox
     */
    Pane createVerticalPanel();

    /**
     * create a big button in javafx.
     * 
     * @param name the display on the button
     * @return Button
     */
    Button createButton(String name);

    /**
     * create a scene in javafx.
     * 
     * @param panel the default panel to attach to the scene.
     * @return Scene
     */
    Scene createScene(Pane panel);

    /**
     * create a option panel to show message box.
     * 
     * @param message message to display.
     * @return message box
     */
    Alert createInformationBox(String message);

    /**
     * create a text.
     * 
     * @param s    Number to transform in Text
     * @param size the font size
     * @param <T>  any type of the list
     * @return Text
     */
    <T> Text createText(T s, int size);

    /**
     * transform a list of string into text.
     * 
     * @param <T>  any type of the list
     * @param list the list in String, the order depends on the type of list.
     * @param size the size of text font
     * @return a list of Text
     */
    <T> List<Text> transformStringIntoText(List<T> list, int size);

    /**
     * BlockScheda is a component module that creates a data sheet, to use this
     * class you need to pass a title in String, A collection of descriptions in
     * row, that will be the header for this data sheet, then actual data in
     * columns. Suggestion: provide the size of descriptions as same as the number
     * of columns.
     * 
     * @param title        A title for the block
     * @param descriptions first row as description in any Collection
     * @param fields       several columns in any Collection
     * @return a scrollable pane that contains a plain data sheet.
     */
    ScrollPane createBlockScheda(Node title, Collection<? extends Node> descriptions,
            Collection<? extends Node>... fields);

    /**
     * Able to create a Pane with a title at the top and a list of pairs of objects
     * transformed to text beneath it.
     * An example would be a legend.
     * 
     * @param <X> first item of the pair
     * @param <Y> second item of the pair
     * @param title
     * @param titleFontSize
     * @param list list of pairs to be transformed into text
     * @param listFontSize
     * @return a Pane with a title and a list of objects transformed to text under it
     */
    <X, Y> Pane createVerticalList(String title, int titleFontSize, List<Pair<X, Y>> list, int listFontSize);
}
