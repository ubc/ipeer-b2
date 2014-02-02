package ca.ubc.ctlt.ipeerb2.service;

import java.util.List;

import ca.ubc.ctlt.ipeerb2.domain.*;

public interface IPeerB2Service {
    public Course getCourse(String bbCourseId);

    public boolean createCourse(Course course);

    public boolean linkCourse(Course course);

    public boolean disconnectCourse(String bbCourseId);

    public boolean deleteCourse(String bbCourseId);

    public boolean syncClass(String bbCourseId);

    public List<Group> getGroupsInBbCourse(String bbCourseId);

    public List<Group> getGroupsInIPeerCourse(String bbCourseId);

    public boolean pushGroups(String bbCourseId);

    public boolean pullGroups(String bbCourseId);

    public List<Grade> syncGrades(String bbCourseId);

    public List<Event> getEventsForUserInCourse(String username, String bbCourseId);

    public List<Event> getEventsInCourse(String bbCourseId);

    public List<Event> getEventsForUser(String username);

    public List<Department> getDepartments();

    public boolean assignCourseToDepartment(int courseId, int departmentId);

    public int getBbClassSize(String bbCourseId);

    public int getBbActiveClassSize(String bbCourseId);

    public int getIPeerClassSize(String bbCourseId);
}
