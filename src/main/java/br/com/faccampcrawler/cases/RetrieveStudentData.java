package br.com.faccampcrawler.cases;

import br.com.faccampcrawler.crawler.StudentCrawler;
import br.com.faccampcrawler.model.Student;
import br.com.faccampcrawler.parser.StudentParser;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RetrieveStudentData {
    private StudentCrawler crawler;
    private StudentParser parser;

    @Autowired
    public RetrieveStudentData(StudentCrawler crawler, StudentParser parser) {
        this.crawler = crawler;
        this.parser = parser;
    }

    public Student retrieve(String ra, String password) {
        Student student = new Student();
        Document index = crawler.executeLogin(ra, password);

        if (parser.validateLogin(index)) {
            Document subjectsPage = crawler.retrieveSubjectsPage(crawler.getCookies());
            Document gradesPage = crawler.retrieveGradesPage(crawler.getCookies());
            Document edpGradesPage = crawler.retrieveEDPGradesPage(crawler.getCookies());

            if (subjectsPage != null && gradesPage != null && edpGradesPage != null) {
                student = parser.parseStudentData(subjectsPage, gradesPage, edpGradesPage);
            }
        }
        return student;
    }
}
