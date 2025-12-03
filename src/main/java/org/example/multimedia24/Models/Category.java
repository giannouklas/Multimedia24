package org.example.multimedia24.Models;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Category implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String name;
    private static List<Category> CategoryList = Collections.synchronizedList(new ArrayList<>());

    //constructor
    public Category(String name) {this.name = name;}

    public static String checkCreateAdd(String name) {
        if (name == null || name.trim().isEmpty())
            return "Category name cannot be null or empty.";
        for (Category c : CategoryList) {
            if (c.getName().equalsIgnoreCase(name))
                return "Category already exists.";
        }
        Category newCategory = new Category(name);
        CategoryList.add(newCategory);
        return null; // Category created and added to the list.
    }

    public String edit(String newName){
        if (newName == null || newName.trim().isEmpty()) {
            return "Category name cannot be empty.";
        }
        if (!name.equals(newName)){
            for (Category c : CategoryList) {
                if (c.getName().equalsIgnoreCase(newName)) {
                    return "Category with name '" + newName + "' already exists.";
                }
            }
            String oldName = getName();
            setName(newName);

            for (Book b: Book.getBookList()){
                if(b.getCategory().equals(oldName)){
                    b.setBookCategory(this);
                }
            }

        }
        return null;
    }

    //getters and setters
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public static List<Category> getCategoryList() {return CategoryList;}
    public static void setCategoryList(List<Category> l){CategoryList = l;}

    public static String removeCategory(String categoryName) {
        for (Category c : CategoryList) {
            if (c.getName().equals(categoryName)) {
                String Message = Book.removeAllBooksForSpecificCategory(categoryName);
                CategoryList.remove(c);
                return "Category " + categoryName + " was successfully removed. "+Message;
            }
        }
        return "No category found with the name " + categoryName + ".";
    }
}
