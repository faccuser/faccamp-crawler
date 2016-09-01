package br.com.faccampcrawler.parser;

import br.com.faccampcrawler.model.Grade;
import br.com.faccampcrawler.model.Student;
import br.com.faccampcrawler.model.Subject;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StudentParser {
    private SubjectParser subjectParser;
    private GradeParser gradeParser;

    @Autowired
    public StudentParser(SubjectParser subjectParser, GradeParser gradeParser) {
        this.subjectParser = subjectParser;
        this.gradeParser = gradeParser;
    }

    public Student parseStudentData(Document subjectsPage, Document gradesPage, Document edpGradesPage) {
        Student student = subjectParser.retrieveNameAndCourses(subjectsPage);
        List<Subject> subjects = subjectParser.retrieveSubjects(subjectsPage);
        List<List<Grade>> gradeList = gradeParser.retrieveGrades(gradesPage);
        List<List<Grade>> edpGradeList = gradeParser.retrieveGrades(edpGradesPage);

        for (int i = 0; i < subjects.size(); i++) {
            subjects.get(i).setGrades(gradeList.get(i));
            subjects.get(i).setEdpGrades(edpGradeList.get(i));
        }

        student.setSubjects(subjects);
        return student;
    }

    public boolean validateLogin(Document page) {
        Elements elements = page.select("em");
        return !elements.first().ownText().equals("user");
    }
}
