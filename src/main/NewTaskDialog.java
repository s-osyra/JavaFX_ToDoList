package main;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import main.models.TaskData;

import java.awt.*;
import java.time.LocalDate;

public class NewTaskDialog {

    @FXML
    private TextField shortDescriptionField;

    @FXML
    private TextArea detailsArea;

    @FXML
    private DatePicker deadlinePicker;

    public void processResults() {
        String shortDescription = shortDescriptionField.getText().trim();
        String details = detailsArea.getText().trim();
        LocalDate deadlineValue = deadlinePicker.getValue();

//        TaskData.getInstance().addTodoItem(new TodoItem(shortDescription, details, deadlineValue));
    }

}
