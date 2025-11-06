package util;

import java.util.ArrayList;
import java.util.List;

public class Menu {

    public static int getChoice(String title, List<String> options) {
        System.out.println("\n=============================================");
        System.out.println("|| " + title.toUpperCase());
        System.out.println("=============================================");
        
        for (int i = 0; i < options.size(); i++) {
            System.out.printf("|| %2d. %s\n", (i + 1), options.get(i));
        }
        System.out.println("=============================================");

        int min = 1;
        int max = options.size();
        
        return Validator.getInt(
                "Enter your choice [" + min + "-" + max + "]: ",
                "Invalid choice!",
                min,
                max
        );
    }
    
    public static int showMainMenu() {
        List<String> options = new ArrayList<>();
        options.add("Student Management");
        options.add("Subject Management");
        options.add("Course Section Management");
        options.add("Registration/Grade Management");
        options.add("Reports and Statistics");
        options.add("Save and Exit");
        
        return getChoice("COURSE REGISTRATION SYSTEM", options);
    }
    

    public static int showStudentManagementMenu() {
        List<String> options = new ArrayList<>();
        options.add("Add new student");
        options.add("Search student by ID");
        options.add("Search student by Name");
        options.add("Update student information");
        options.add("Delete student");
        options.add("Display all students (Sorted by name)");
        options.add("Sort by total GPA");
        options.add("Return to Main Menu");

        return getChoice("STUDENT MANAGEMENT", options); 
    }

    public static int showSubjectManagementMenu() {
        List<String> options = new ArrayList<>();
        options.add("Add new subject");
        options.add("Search subject by ID");
        options.add("Update subject information");
        options.add("Delete subject");
        options.add("Display all subjects");
        options.add("Return to Main Menu");

        return getChoice("SUBJECT MANAGEMENT", options);
    }


    public static int showCourseManagementMenu() {
        List<String> options = new ArrayList<>();
        options.add("Add new course section");
        options.add("Search course section by ID");
        options.add("Search course section by Subject ID");
        options.add("Update course section information");
        options.add("Delete course section");
        options.add("Sort subject by GPA");
        options.add("View Students By Subject"); 
        options.add("View Subjects By Student");
        options.add("Display all course sections");
        options.add("Return to Main Menu");

        return getChoice("COURSE SECTION MANAGEMENT", options);
    }
    

    public static int showRegistrationManagementMenu() {
        List<String> options = new ArrayList<>();
        options.add("Register for course section");
        options.add("Cancel course registration");
        options.add("Enter student grades");
        options.add("View student’s registration list");
        options.add("View list of students by course section");
        options.add("Return to Main Menu");
        
        return getChoice("REGISTRATION AND GRADES MANAGEMENT", options);
    }

 
    public static int showReportMenu() {
        List<String> options = new ArrayList<>();
        options.add("Calculate overall GPA for student");
        options.add("Calculate GPA by semester");
        options.add("Sort students by subject GPA");
        options.add("Return to Main Menu");
        
        return getChoice("REPORTS AND STATISTICS", options);
    }
}
