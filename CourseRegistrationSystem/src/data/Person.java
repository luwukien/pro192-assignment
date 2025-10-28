package data;

// <<abstract class>>> Person
public abstract class Person {
    protected String fullName; // #fullName [cite: 28]
    protected String email;    // #email [cite: 29]

    public Person(String fullName, String email) {
        this.fullName = fullName;
        this.email = email;
    }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    // Yêu cầu lớp con thực hiện getId()
    public abstract String getId(); 
}