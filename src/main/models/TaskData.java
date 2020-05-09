package main.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;

public class TaskData {
    private static TaskData instance = new TaskData();
    private static String filename = "Task_Data.txt";

    private ObservableList<task> tasks;
    private DateTimeFormatter formatter;

    public static TaskData getInstance() {
        return instance;
    }

    private TaskData () {
        formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    }

    public ObservableList<task> getTasks() {
        return tasks;
    }

    public void addTask (task task) {
        tasks.add(task);
    }


//    public void setTasks (List<task> tasks) {
//        this.tasks = tasks;
//    }

    public void loadTasks() throws IOException {
        tasks = FXCollections.observableArrayList();
        Path path = Paths.get(filename);
        BufferedReader br = Files.newBufferedReader(path);

        String input;

        try {
            while ((input = br.readLine()) != null) {
                String[]itemPieces = input.split(("\t"));

                String shortDescription = itemPieces[0];
                String details = itemPieces[1];
                String dateString = itemPieces[2];

                LocalDate date = LocalDate.parse(dateString, formatter);
                task task = new task (shortDescription, details, date);
                tasks.add(task);

            }


        } finally {
            if (br != null) {
                br.close();
            }
        }
    }

    public void storeTasks() throws IOException {
        Path path = Paths.get(filename);
        BufferedWriter bw = Files.newBufferedWriter(path);
        try {
            Iterator<task> iter = tasks.iterator();
            while (iter.hasNext()) {
                task item = iter.next();
                bw.write(String.format("%s\t%s\t%s",
                item.getShortDescription(),
                item.getDetails(),
                item.getDeadLine().format(formatter)));

                bw.newLine();
            }

        } finally {
            if (bw != null) {
                bw.close();
            }
        }
    }

}
