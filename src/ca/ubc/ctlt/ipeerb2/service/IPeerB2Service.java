package ca.ubc.ctlt.ipeerb2.service;

import java.util.List;

import ca.ubc.ctlt.ipeerb2.domain.*;

public interface IPeerB2Service {
    Course getCourse(String bbCourseId);

    boolean createCourse(Course course);

    boolean linkCourse(Course course);

    boolean disconnectCourse(String bbCourseId);

    boolean deleteCourse(String bbCourseId);

    boolean syncClass(String bbCourseId);

    List<Group> getGroupsInBbCourse(String bbCourseId);

    List<Group> getGroupsInIPeerCourse(String bbCourseId);

    boolean pushGroups(String bbCourseId);

    boolean pullGroups(String bbCourseId);

    List<Grade> syncGrades(String bbCourseId);

    List<Event> getEventsForUserInCourse(String username, String bbCourseId);

    List<Event> getEventsInCourse(String bbCourseId);

    List<Event> getEventsForUser(String username);

    List<Department> getDepartments();

    boolean assignCourseToDepartment(int courseId, int departmentId);

    int getBbClassSize(String bbCourseId);

    int getBbActiveClassSize(String bbCourseId);

    int getIPeerClassSize(String bbCourseId);
}
