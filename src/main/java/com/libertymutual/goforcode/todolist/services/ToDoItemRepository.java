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
    List<ToDoItem> toDo;
    String [] items = new String[3];

    /**
     * Get all the items from the file. 
     * @return A list of the items. If no items exist, returns an empty list.
     */
    
    public List<ToDoItem> getAll() {
    	
    	CSVFormat csvFileFormat = CSVFormat.DEFAULT;
    	toDo = new ArrayList<ToDoItem>();
    	
		try(FileReader reader = new FileReader("file.csv"); 
			BufferedReader buffer = new BufferedReader(reader);
	    	CSVParser parser = new CSVParser(buffer, csvFileFormat)) {
			
			Iterable<CSVRecord> records = csvFileFormat.parse(buffer);
			
			nextId = 1;
			
			ToDoItem toDoItem;
			
			for (CSVRecord record : records) {
				
				toDoItem = new ToDoItem();
				
			    String columnOne = record.get(0);
			    int id = Integer.parseInt(columnOne);
			    toDoItem.setId(id);
			    String columnTwo = record.get(1);
			    toDoItem.setText(columnTwo);
			    String columnThree = record.get(2);
			    Boolean boo = Boolean.parseBoolean(columnThree);
			    toDoItem.setComplete(boo);
			    toDo.add(toDoItem);
			    nextId += 1;
			   
			}
			
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
    	
    	try (FileWriter writer = new FileWriter("file.csv", true); 
    		BufferedWriter buffer = new BufferedWriter(writer);
    		CSVPrinter printer = new CSVPrinter(buffer, csvFileFormat)) {

    		item.setId(nextId);
    		String id = Integer.toString(item.getId());
    		String boo = Boolean.toString(item.isComplete());
    		items[0] = id;
    		items[1] = item.getText();
    		items[2] = boo;
    		printer.printRecord(items);
    		nextId += 1;
    		
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
    	
    	try (FileReader reader = new FileReader("file.csv");
    		BufferedReader buffer = new BufferedReader(reader);
    		CSVParser parser = new CSVParser(buffer, csvFileFormat)) { 
    		
    		for(ToDoItem item : toDo) {
    			if(item.getId() == id) {
    				return item;
    			}
    		}
    		
    	} catch (FileNotFoundException e) {
			
    		System.out.println("file not found");
    		
		} catch (IOException e) {
			
			System.out.println("error reading file");

		}
        
		return null;
    }

    /**
     * Updates the given to-do item in the file.
     * @param item The item to update.
     */
    public void update(ToDoItem item) {
    	CSVFormat csvFileFormat = CSVFormat.DEFAULT;
	
    	try (FileReader reader = new FileReader("file.csv");
    		BufferedReader buffer = new BufferedReader(reader);
    		CSVParser parser = new CSVParser(buffer, csvFileFormat); 
    		FileWriter writer = new FileWriter("file.csv"); 
    		BufferedWriter bufferW = new BufferedWriter(writer);
    		CSVPrinter printer = new CSVPrinter(bufferW, csvFileFormat)) { 
    		
    		List<String[]> str = new ArrayList<String[]>(); 
    		
    		item.setComplete(true);
    		
    		for (ToDoItem items : toDo) {
 
    			String[] string = new String[3];
        		String id = Integer.toString(items.getId());
        		String boo = Boolean.toString(items.isComplete());
        		string[0] = id;
        		string[1] = items.getText();
        		string[2] = boo;
        		str.add(string);
        		
    		}
    	
    		printer.printRecords(str);
    		
    	} catch (FileNotFoundException e) {
			
    		System.out.println("file not found");
    		
		} catch (IOException e) {
			
			System.out.println("error reading file");

		}
    	
    }

}
