package main;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import main.models.TaskData;
import main.models.task;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class Controller {

    private List<task> myTasksList;

    @FXML
    private ListView<task> toDoListView;

    @FXML
    private TextArea description;

    @FXML
    private Label deadline;

    @FXML
    private BorderPane mainBorderPane;

    public void initialize() {
//        task item1 = new task("1", "Details of 1", LocalDate.of(2020, 1, 1));
//        task item2 = new task("2", "Details of 2", LocalDate.of(2020, 2, 2));
//        task item3 = new task("3", "Details of 3", LocalDate.of(2020, 3, 3));
//        task item4 = new task("4", "Details of 4", LocalDate.of(2020, 4, 4));
//        task item5 = new task("5", "Details of 5", LocalDate.of(2020, 5, 5));
//
//        myTasksList = new ArrayList<task>();
//        myTasksList.add(item1);
//        myTasksList.add(item2);
//        myTasksList.add(item3);
//        myTasksList.add(item4);
//        myTasksList.add(item5);
//
//        TaskData.getInstance().setTasks(myTasksList);

        toDoListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<task>() {
            @Override
            public void changed(ObservableValue<? extends task> observableValue, task oldValue, task newValue) {
                if(newValue != null){
                    task item = toDoListView.getSelectionModel().getSelectedItem();
                    DateTimeFormatter df = DateTimeFormatter.ofPattern("dd.MM.YYYY");
                    description.setText(item.getDetails());
                    deadline.setText(df.format(item.getDeadLine()));
                }
            }
        });

        toDoListView.setItems(TaskData.getInstance().getTasks());
        toDoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        toDoListView.getSelectionModel().selectFirst();

        toDoListView.setCellFactory(new Callback<ListView<task>, ListCell<task>>() {
            @Override
            public ListCell<task> call(ListView<task> param) {
                ListCell<task> cell = new ListCell<task>(){
                    @Override
                    protected void updateItem(task item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText(null);
                        } else {
                            setText(item.getShortDescription());
                            if (item.getDeadLine().isBefore(LocalDate.now().plusDays(1))) {
                                setTextFill(Color.RED);
                            } else if (item.getDeadLine().equals(LocalDate.now().plusDays(1))){
                                setTextFill(Color.ORANGE);
                            }
                        }
                    }
                };

                return cell;
            }
        });


    }

    @FXML
    public void showNewItemDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Add new task");
        dialog.setHeaderText("Adding new task to the list.");
        FXMLLoader fxmlloader = new FXMLLoader();
        fxmlloader.setLocation(getClass().getResource("newTaskDialog.fxml"));
        try{
            dialog.getDialogPane().setContent(fxmlloader.load());

        } catch (IOException e) {
            System.out.println("Couldn't load the dialog");
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            NewTaskDialog controller = fxmlloader.getController();
            task newTask = controller.processResults();
            //toDoListView.getItems().setAll(TaskData.getInstance().getTasks());
            toDoListView.getSelectionModel().select(newTask);
        }
    }

    @FXML
    public void handleDetailView(){
        task item = toDoListView.getSelectionModel().getSelectedItem();
//        description.setText(item.getDetails());
//        deadline.setText(item.getDeadLine().toString());

    }



}
