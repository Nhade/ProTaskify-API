package com.nhade.api.task;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class FileManager {

    public void saveTasks(Map<Integer, Task> tasks, String filename) {
        try (FileOutputStream fileOut = new FileOutputStream(filename);
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
            objectOut.writeObject(tasks);
            objectOut.close();
            fileOut.close();
            System.out.println("Saved tasks to " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked") // TODO: Consider type checking
    public Map<Integer, Task> loadTasks(String filename) {
        Map<Integer, Task> tasks = new HashMap<>();
        try (FileInputStream fileIn = new FileInputStream(filename);
             ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {
            tasks = (Map<Integer, Task>) objectIn.readObject();
            fileIn.close();
            objectIn.close();
            System.out.println("Loaded tasks from " + filename);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return tasks;
    }
}
