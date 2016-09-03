package br.com.faccampcrawler.cases;

import br.com.faccampcrawler.crawler.StudentCrawler;
import br.com.faccampcrawler.model.Student;
import br.com.faccampcrawler.parser.StudentParser;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@Component
public class RetrieveStudentData {
    private StudentCrawler crawler;
    private StudentParser parser;

    @Autowired
    public RetrieveStudentData(StudentCrawler crawler, StudentParser parser) {
        this.crawler = crawler;
        this.parser = parser;
    }

    public Student retrieve(String ra, String password) throws InvalidLoginException {
        Student student = new Student();
        Document index = crawler.executeLogin(ra, password);

        if (parser.validateLogin(index)) {
            try {
                ExecutorService executor = Executors.newSingleThreadExecutor();

                Future<Document> futureSubjectsPage = executor.submit(() -> crawler.retrieveSubjectsPage(crawler.getCookies()));
                Future<Document> futureGradesPage = executor.submit(() -> crawler.retrieveGradesPage(crawler.getCookies()));
                Future<Document> futureEdpGradesPage = executor.submit(() -> crawler.retrieveEDPGradesPage(crawler.getCookies()));

                executor.shutdown();
                executor.awaitTermination(2, TimeUnit.MINUTES);

                Document subjectsPage = futureSubjectsPage.get();
                Document gradesPage = futureGradesPage.get();
                Document edpGradesPage = futureEdpGradesPage.get();

                if (subjectsPage != null && gradesPage != null && edpGradesPage != null)
                    student = parser.parseStudentData(subjectsPage, gradesPage, edpGradesPage);
                else
                    throw new InvalidLoginException("Could not validate login for user with ra: " + ra);

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return student;
    }
}