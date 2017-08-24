// ToDoItemRepository.java
package com.libertymutual.goforcode.todolist.services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import com.libertymutual.goforcode.todolist.models.ToDoItem;

@Service
public class ToDoItemRepository {

    private int nextId = 1;
    List<ToDoItem> toDo = new ArrayList<ToDoItem>();

    /**
     * Get all the items from the file. 
     * @return A list of the items. If no items exist, returns an empty list.
     */
    
    public List<ToDoItem> getAll() {
    	
    	CSVFormat csvFileFormat = CSVFormat.DEFAULT;
    	
		try(FileReader reader = new FileReader("file.csv"); 
			BufferedReader buffer = new BufferedReader(reader);
	    	CSVParser parser = new CSVParser(buffer, csvFileFormat)) {
			
		} catch (FileNotFoundException e) {
			
			System.out.println("could not find file");
			
		} catch (IOException e1) {
			
			System.out.println("something bad happened");

		}

        return toDo;
    }

    /**
     * Assigns a new id to the ToDoItem and saves it to the file.
     * @param item The to-do item to save to the file.
     */
    public void create(ToDoItem item) {

    	CSVFormat csvFileFormat = CSVFormat.DEFAULT;
    	
    	try (FileWriter writer = new FileWriter("file.csv"); 
    		BufferedWriter buffer = new BufferedWriter(writer);
    		CSVPrinter printer = new CSVPrinter(buffer, csvFileFormat)) {

    		item.setId(nextId);
    		toDo.add(item);
    		nextId += 1;
    		printer.printRecord(toDo);
    		
    	}  catch (IOException e) {
    		
    		System.out.println("could not read file");
		}
    }

    /**
     * Gets a specific ToDoItem by its id.
     * @param id The id of the ToDoItem.
     * @return The ToDoItem with the specified id or null if none is found.
     */
    public ToDoItem getById(int id) {
    	
    	CSVFormat csvFileFormat = CSVFormat.DEFAULT;
    	
    	ToDoItem toDoItem = null;
    	
    	try (FileReader reader = new FileReader("file.csv");
    		BufferedReader buffer = new BufferedReader(reader);
    		CSVParser parser = new CSVParser(buffer, csvFileFormat)) { 
    		
    		toDoItem = toDo.get(id - 1);
    		
    	} catch (FileNotFoundException e) {
			
    		System.out.println("file not found");
    		
		} catch (IOException e) {
			
			System.out.println("error reading file");

		}
        
		return toDoItem;
    }

    /**
     * Updates the given to-do item in the file.
     * @param item The item to update.
     */
    public void update(ToDoItem item) {
    	CSVFormat csvFileFormat = CSVFormat.DEFAULT;
    	
    	ToDoItem toDoItem = item;
    	
    	try (FileReader reader = new FileReader("file.csv");
    		BufferedReader buffer = new BufferedReader(reader);
    		CSVParser parser = new CSVParser(buffer, csvFileFormat); 
    		FileWriter writer = new FileWriter("file.csv"); 
    		BufferedWriter bufferW = new BufferedWriter(writer);
    		CSVPrinter printer = new CSVPrinter(bufferW, csvFileFormat)) { 
    		
    		toDoItem.setComplete(true);
    		
    	} catch (FileNotFoundException e) {
			
    		System.out.println("file not found");
    		
		} catch (IOException e) {
			
			System.out.println("error reading file");

		}
    	
    }

}
