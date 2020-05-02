package main;



import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import main.models.task;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    private List<task> myTasksList;

    @FXML
    private ListView<task> toDoListView;

    @FXML
    private TextArea description;

    public void initialize() {
        task item1 = new task("1", "Details of 1", LocalDate.of(2020, 1, 1));
        task item2 = new task("2", "Details of 2", LocalDate.of(2020, 2, 2));
        task item3 = new task("3", "Details of 3", LocalDate.of(2020, 3, 3));
        task item4 = new task("4", "Details of 4", LocalDate.of(2020, 4, 4));
        task item5 = new task("5", "Details of 5", LocalDate.of(2020, 5, 5));

        myTasksList = new ArrayList<task>();
        myTasksList.add(item1);
        myTasksList.add(item2);
        myTasksList.add(item3);
        myTasksList.add(item4);
        myTasksList.add(item5);


        toDoListView.getItems().setAll(myTasksList);
        toDoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);


    }

    @FXML
    public void handleDetailView(){
        task item = toDoListView.getSelectionModel().getSelectedItem();
        description.setText(item.getDetails());
    }



}
