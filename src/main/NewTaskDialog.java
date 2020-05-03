package main;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import main.models.TaskData;
import main.models.task;

import java.time.LocalDate;

public class NewTaskDialog {

    @FXML
    private TextField shortDescriptionField;

    @FXML
    private TextArea detailsField;

    @FXML
    private DatePicker deadLinePicker;

    public task processResults() {
        String shortDescription = shortDescriptionField.getText().trim();
        String details = detailsField.getText().trim();
        LocalDate deadlineValue = deadLinePicker.getValue();

        task newTask = new task(shortDescription, details, deadlineValue);
        TaskData.getInstance().addTask(newTask);

        return newTask;
    }

}
