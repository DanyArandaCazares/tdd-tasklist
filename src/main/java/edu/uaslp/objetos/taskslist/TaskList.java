package edu.uaslp.objetos.taskslist;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class TaskList {
    LinkedList<Task> taskList;

    public TaskList(){
        taskList = new LinkedList<>();
    }

    public int getSize() {
        return taskList.size();
    }

    public void add(Task task){
        taskList.add(task);
    }

    public void remove(Task task){
        taskList.remove(task);
    }

    public Task find(String title) throws TaskNotFoundException{
        for(Task task : taskList){
            if(task.getTitle().equals(title)){
                return task;
            }
        }

        throw new TaskNotFoundException("Task with title '"+title+"' not found");
    }

    public void markAsDone(String title){
        Task task = find(title);
        task.setDone(true);
    }

    public void markAsNotDone(String title){
        Task task = find((title));
        task.setDone(false);
    }

    public Task getNextTask(){
        Task nextTask;

        Iterator<Task> it = taskList.iterator();
        nextTask = it.next();

        while (it.hasNext()){
            Task actualTask = it.next();
            if(actualTask.getDueDate().isBefore(nextTask.getDueDate()) && !actualTask.isDone()){
                nextTask = actualTask;
            }
        }

        return nextTask;
    }

    public List getNextTasks(){
        LinkedList<Task> taskFound = new LinkedList<>();

        for (Task actualTask : taskList) {
            if (actualTask.getDueDate().isAfter(LocalDateTime.now()) && !actualTask.isDone()) {
                Task task = new Task(actualTask.getTitle(), actualTask.getDescription(), actualTask.getDueDate(), actualTask.isDone());
                taskFound.add(task);
            }
        }
        taskFound.sort((Task a, Task b) -> a.getDueDate().compareTo(b.getDueDate()));

        return taskFound;
    }
}
