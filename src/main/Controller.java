package main;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import main.models.TaskData;
import main.models.task;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
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

    @FXML
    private ContextMenu listContextMenu;

    public void initialize() {

        listContextMenu = new ContextMenu();
        MenuItem deleteMenuTask = new MenuItem("Delete");
        deleteMenuTask.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                task task = toDoListView.getSelectionModel().getSelectedItem();
                deleteTask(task);
            }
        });

        listContextMenu.getItems().addAll(deleteMenuTask);

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

        SortedList<task> sortedList = new SortedList<task>(TaskData.getInstance().getTasks(), new Comparator<task>() {
            @Override
            public int compare(task o1, task o2) {
                return o1.getDeadLine().compareTo(o2.getDeadLine());
            }
        });

        //toDoListView.setItems(TaskData.getInstance().getTasks());
        toDoListView.setItems(sortedList);
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


                cell.emptyProperty().addListener(
                        (obs,wasEmpty,isNowEmpty) -> {
                            if (isNowEmpty) {
                                cell.setContextMenu(null);
                            } else {
                                cell.setContextMenu(listContextMenu);
                            }
                        }
                );
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
            toDoListView.getSelectionModel().select(newTask);
        }
    }

    @FXML
    public void handleKeyPressed (KeyEvent keyEvent) {
        task selectedTask = toDoListView.getSelectionModel().getSelectedItem();
        if (selectedTask != null) {
            if(keyEvent.getCode().equals(KeyCode.DELETE)){
                deleteTask(selectedTask);
            }
        }
    }

    @FXML
    public void handleDetailView(){
        task task = toDoListView.getSelectionModel().getSelectedItem();

    }

    @FXML
    public void handleExit(){
        Platform.exit();
    }

    public void deleteTask(task task) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete task");
        alert.setHeaderText("Delete task: " + task.getShortDescription());
        alert.setContentText("Are you sure? Press OK to confirm.");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && (result.get()) == ButtonType.OK) {
            TaskData.getInstance().deleteTaskData(task);
        }
    }

}
