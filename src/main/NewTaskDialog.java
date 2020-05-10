package main;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import main.models.task;

import java.time.LocalDate;

public class NewTaskDialog {

    @FXML
    private TextField newShortDescriptionField;

    @FXML
    private TextArea newDetailsField;

    @FXML
    private DatePicker newDeadLinePicker;



    public task processResults() {
        String shortDescription = newShortDescriptionField.getText().trim();
        String details = newDetailsField.getText().trim();
        LocalDate deadlineValue = newDeadLinePicker.getValue();

        task newTask = new task(shortDescription, details, deadlineValue);

        return newTask;
    }

    public void editingResults(task task) {

        newShortDescriptionField.setText(task.getShortDescription());
        newDetailsField.setText(task.getDetails());
        newDeadLinePicker.setValue(task.getDeadLine());

    }


}
