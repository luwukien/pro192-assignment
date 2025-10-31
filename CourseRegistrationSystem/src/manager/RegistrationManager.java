package manager;

import java.util.List;
import java.util.ArrayList;
import interfaces.Displayable;
import data.Student;
import data.Registration;
import data.CourseSection;
import data.Subject;
import enums.RegistrationStatus;

public class RegistrationManager extends Management<Registration> implements Displayable {

    private List<Registration> registrations;
    private StudentManager studentManager;
    private CourseManager courseManager;
    private SubjectManager subjectManager;

    public RegistrationManager(List<Registration> initialList, StudentManager studentMan,
            CourseManager courseMan,
            SubjectManager subjectMan) {
        super(initialList);
        this.registrations = this.list;
        this.studentManager = studentMan;
        this.courseManager = courseMan;
        this.subjectManager = subjectMan;
    }

    public boolean registerCourse(String studentId, String courseSectionId) {
        Student student = studentManager.findById(studentId);
        CourseSection course = courseManager.findById(courseSectionId);

        //Input Student and Course check
        if (student == null || course == null) {
            return false;
        }

        //The numbers of course cCeck
        if (course.isFull()) {
            return false;
        }

        //Duplicate Subjects Check
        for (Registration registration : registrations) {
            if (registration.getStudentId().equals(studentId) && registration.getCourseSectionId().equals(courseSectionId)) {
                System.out.println("Please enter other student ID or courseSection ID. Because this student registrated this course!");
                return false;
            }
        }

        //Credit Limit Check
        String subjectId = course.getSubjectId();
        Subject subject = subjectManager.findById(subjectId);
        int newCredits = subject.getCredit();
        int targetSemester = course.getSemester();
        int currentCredits = calculateCurrentCredits(studentId, targetSemester);
        if (currentCredits + newCredits > 20) {
            System.out.println("Error: Over 20 credits. A student can only register not exceeding 20 credits in a semester!");
            return false;
        }

        //Prerequisite Check
        List<String> prerequisiteSubjects = subject.getPrerequisiteSubjectIds();
        List<Registration> studentRegistrations = this.getRegistrationsByStudent(studentId);

        for (String preSubjectId : prerequisiteSubjects) {
            boolean foundAndPassed = false;

            for (Registration registration : studentRegistrations) {
                CourseSection historicalSection = courseManager.findById(registration.getCourseSectionId());
                if (historicalSection == null) {
                    continue;
                }
                String historicalSubjectId = historicalSection.getSubjectId();
                if (historicalSubjectId.equals(preSubjectId)) {
                    if (registration.getStatus() == RegistrationStatus.PASSED) {
                        foundAndPassed = true;
                        break;
                    }
                }
            }
            if (!foundAndPassed) {
                System.out.println("Error: The id student: " + studentId + " not pass prerequisite subject id: " + preSubjectId + "!");
                return false;
            }
        }

        Registration newRegis = new Registration(studentId, courseSectionId, 0.0, RegistrationStatus.ENROLLED);
        super.add(newRegis);
        course.incrementStudentCount();
        courseManager.update(course);
        System.out.println("Student ID: " + studentId + " registered this course section successfully");
        return true;
    }

    private int calculateCurrentCredits(String studentId, int semester) {
        int totalCredits = 0;

        for (Registration registration : getRegistrationsByStudent(studentId)) {
            if (registration.getStatus() == RegistrationStatus.ENROLLED) {
                CourseSection section = courseManager.findById(registration.getCourseSectionId());
                if (section.getSemester() == semester) {
                    Subject subject = subjectManager.findById(section.getSubjectId());
                    totalCredits += subject.getCredit();
                }
            }
        }
        return totalCredits;
    }

    public boolean withdrawCourse(String studentId, String courseSectionId) {
        for (Registration registration : this.list) {
            if (registration.getCourseSectionId().equals(courseSectionId) && registration.getStudentId().equals(studentId)) {
                Registration regTarget = registration;
                if (regTarget.getStatus() == RegistrationStatus.ENROLLED) {
                    regTarget.setStatus(RegistrationStatus.WITHDRAWN);
                    super.update(regTarget);
                    CourseSection section = courseManager.findById(courseSectionId);
                    if (section != null) {
                        section.decrementStudentCount();
                    }
                    courseManager.update(section);
                    return true;
                } else {
                    System.out.println("This course section studied / withdrawn!");
                    return false;
                }
            }
        }

        System.out.println("This courseSectionID: " + courseSectionId + "is not found");
        return false;
    }

    public List<Registration> getRegistrationsByStudent(String studentId) {
        ArrayList<Registration> resultList = new ArrayList<>();

        for (Registration registration : registrations) {
            if (registration.getStudentId().equals(studentId)) {
                resultList.add(registration);
            }
        }
        return resultList;
    }

    public List<Registration> getRegistrationsByCourse(String sectionId) {
        ArrayList<Registration> resultList = new ArrayList<>();

        for (Registration registration : registrations) {
            if (registration.getCourseSectionId().equals(sectionId)) {
                resultList.add(registration);
            }
        }
        return resultList;

    }

    public List<Student> getStudentsByCourseSection(String courseSectionId) {
        ArrayList<Student> studentList = new ArrayList<>();
        List<Registration> regs = getRegistrationsByCourse(courseSectionId);

        for (Registration reg : regs) {
            String studentId = reg.getStudentId();
            Student student = studentManager.findById(studentId);
            if (student != null) {
                studentList.add(student);
            }
        }

        return studentList;

    }
    
    

    @Override
    public void displayAll() {
        if (this.registrations.isEmpty()) {
            System.out.println("Empty list");
            return;
        }
        for (Registration registration : registrations) {
            System.out.println(registration);
        }
    }
}
