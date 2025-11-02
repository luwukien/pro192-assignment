package manager;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import interfaces.Displayable;
import data.Student;
import data.Registration;
import data.CourseSection;
import data.Subject;
import enums.RegistrationStatus;


public class RegistrationManager extends Management<Registration> implements Displayable {

    private StudentManager studentManager;
    private CourseManager courseManager;
    private SubjectManager subjectManager;

    public RegistrationManager(List<Registration> initialList, StudentManager studentMan,
            CourseManager courseMan,
            SubjectManager subjectMan) {
        super(initialList);
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

        //The numbers of course check
        if (course.isFull()) {
            System.out.println("This course is full. Please register another class!");
            return false;
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

        //Duplicate Subjects Check
        String regId = studentId + "_" + courseSectionId;
        Registration existingReg = this.findById(regId);

        if (existingReg == null) {
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
            if (super.add(newRegis)) {
                course.incrementStudentCount();
                courseManager.update(course);
                System.out.println("Student ID: " + studentId + " registered successfully!");
                return true;
            }
            return false;
        } else {
            //If this student have registrated this course but she/he failed or withdrawn. And she/he want to registrated again!
            RegistrationStatus oldStatus = existingReg.getStatus();

            if (oldStatus == RegistrationStatus.PASSED || oldStatus == RegistrationStatus.ENROLLED) {
                System.out.println("Please enter other student ID or courseSection ID. Because this student registrated this course!");
                return false;
            }

            if (oldStatus == RegistrationStatus.FAILED || oldStatus == RegistrationStatus.WITHDRAWN) {
                existingReg.setStatus(RegistrationStatus.ENROLLED);
                //Reset grade
                existingReg.setGrade(0.0);

                if (super.update(existingReg)) {
                    course.incrementStudentCount();
                    courseManager.update(course);
                    System.out.println("Student ID: " + studentId + " registered this course section successfully");
                    return true;
                }
                return false;
            }
            return false;
        }

    }

    private int calculateCurrentCredits(String studentId, int semester) {
        int totalCredits = 0;

        for (Registration registration : getRegistrationsByStudent(studentId)) {
            if (registration.getStatus() == RegistrationStatus.ENROLLED) {
                CourseSection section = courseManager.findById(registration.getCourseSectionId());
                if (section != null && section.getSemester() == semester) {
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
                        courseManager.update(section);
                        return true;
                    }

                } else {
                    System.out.println("This course section studied / withdrew!");
                    return false;
                }
            }
        }

        System.out.println("This courseSectionID: " + courseSectionId + "is not found");
        return false;
    }

    public List<Registration> getRegistrationsByStudent(String studentId) {
        ArrayList<Registration> resultList = new ArrayList<>();

        for (Registration registration : this.list) {
            if (registration.getStudentId().equals(studentId)) {
                resultList.add(registration);
            }
        }
        return resultList;
    }

    public List<Registration> getRegistrationsByCourse(String sectionId) {
        ArrayList<Registration> resultList = new ArrayList<>();

        for (Registration registration : this.list) {
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

    public List<Student> getStudentsBySubject(String subjectId) {
        Set<String> studentIds = new HashSet<>();
        List<CourseSection> sections = courseManager.getSectionsBySubject(subjectId);
        for (CourseSection section : sections) {
            List<Student> studentsInThisSection = getStudentsByCourseSection(section.getCourseSectionId());
            for (Student student : studentsInThisSection) {
                studentIds.add(student.getStudentId());
            }
        }
        List<Student> finalResult = new ArrayList<>();
        for (String studentId : studentIds) {
            Student s = studentManager.findById(studentId);
            if (s != null) {
                finalResult.add(s);
            }
        }
        return finalResult;
    }

    public List<Subject> getSubjectsByStudent(String studentId) {
        Set<String> subjectIds = new HashSet<>();
        List<Registration> regs = getRegistrationsByStudent(studentId);
        for (Registration reg : regs) {
            CourseSection section = courseManager.findById(reg.getCourseSectionId());
            if (section != null) {
                subjectIds.add(section.getSubjectId());
            }
        }
        List<Subject> finalResult = new ArrayList<>();
        for (String subjectId : subjectIds) {
            Subject s = subjectManager.findById(subjectId);
            if (s != null) {
                finalResult.add(s);
            }

        }
        return finalResult;
    }

    public List<Student> getStudentsSortedByOverallGPA() {
        List<Student> allStudents = studentManager.getAll();
        List<Student> sortedList = new ArrayList<>(allStudents);
        Collections.sort(sortedList, (s1, s2) -> {
            double gpa1 = this.calculateOverallGPA(s1.getStudentId());
            double gpa2 = this.calculateOverallGPA(s2.getStudentId());

            return Double.compare(gpa2, gpa1);
        });
        return sortedList;
    }

    public List<Student> getStudentsSortedBySubjectGPA(String subjectId) {
        List<Student> allStudents = studentManager.getAll();
        List<Student> sortedList = new ArrayList<>(allStudents);

        Collections.sort(sortedList, (s1, s2) -> {
            double gpa1 = findGradeForSubject(s1.getStudentId(), subjectId);
            double gpa2 = findGradeForSubject(s2.getStudentId(), subjectId);

            return Double.compare(gpa2, gpa1);
        });
        return sortedList;
    }

    private double findGradeForSubject(String studentId, String targetSubjectId) {
        List<Registration> regs = this.getRegistrationsByStudent(studentId);
        for (Registration registration : regs) {
            if (registration.getStatus() == RegistrationStatus.PASSED || registration.getStatus() == RegistrationStatus.FAILED) {
                CourseSection course = courseManager.findById(registration.getCourseSectionId());
                if (course != null && course.getSubjectId().equals(targetSubjectId)) {
                    return registration.getGrade();
                }
            }
        }

        return 0.0;
    }

    public double calculateOverallGPA(String studentId) {
        List<Registration> regs = this.getRegistrationsByStudent(studentId);
        double totalGradePoints = 0;
        int totalCredits = 0;

        for (Registration registration : regs) {
            if (registration.getStatus() == RegistrationStatus.PASSED || registration.getStatus() == RegistrationStatus.FAILED) {
                double grade = registration.getGrade();
                CourseSection course = courseManager.findById(registration.getCourseSectionId());
                if (course != null) {
                    Subject subject = subjectManager.findById(course.getSubjectId());
                    if (subject != null) {
                        int credits = subject.getCredit();
                        totalCredits += credits;
                        totalGradePoints += grade * credits;
                    }
                }
            }
        }
        if (totalCredits == 0) {
            return 0.0;
        }

        return totalGradePoints / totalCredits;
    }

    public double calculateSemesterGPA(String studentId, int semester) {
        List<Registration> regs = this.getRegistrationsByStudent(studentId);
        double totalGradePoints = 0;
        int totalCredits = 0;
        for (Registration registration : regs) {
            CourseSection course = courseManager.findById(registration.getCourseSectionId());
            if (course != null && course.getSemester() == semester) {
                if (registration.getStatus() == RegistrationStatus.PASSED) {
                    double grade = registration.getGrade();
                    Subject subject = subjectManager.findById(course.getSubjectId());
                    if (subject != null) {
                        int credits = subject.getCredit();
                        totalCredits += credits;
                        totalGradePoints += grade * credits;
                    }
                }
            }
        }
        if (totalCredits == 0) {
            return 0.0;
        }

        return totalGradePoints / totalCredits;
    }

    @Override
    public void displayAll() {
        if (this.list.isEmpty()) {
            System.out.println("Empty list");
            return;
        }
        for (Registration registration : this.list) {
            System.out.println(registration);
        }
    }

}
