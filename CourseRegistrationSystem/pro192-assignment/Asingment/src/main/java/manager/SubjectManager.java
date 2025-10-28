/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manager;

import data.Subject;
import interfaces.Displayable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author vuhuy
 */
public class SubjectManager extends Management<Subject> implements Displayable{
    private List<Subject> subjects = new ArrayList<>();

    @Override
    public Subject findById(String id) {
        for (Subject s : subjects) {
            if (s.getSubjectId().equalsIgnoreCase(id)) {
                return s;
            }
        }
        return null;
    }

    @Override
    public boolean update(Subject itemToUpdate) {
        for (int i = 0; i < subjects.size(); i++) {
            if (subjects.get(i).getSubjectId().equals(itemToUpdate.getSubjectId())) {
                subjects.set(i, itemToUpdate);
                return true;
            }
        }
        return false;
    }

   @Override
   public void displayAll() {
       for (Subject s : subjects) {
            System.out.println(s);
       }
    }
  
    }