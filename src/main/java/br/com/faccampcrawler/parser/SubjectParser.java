package br.com.faccampcrawler.parser;

import br.com.faccampcrawler.model.Student;
import br.com.faccampcrawler.model.Subject;
import br.com.faccampcrawler.parser.util.ParserUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
class SubjectParser {
    Student retrieveNameAndCourses(Document page) {
        Student student = new Student();

        String studentName = page.select("td.LinhaPar").get(5).ownText();
        String studentCourse = page.select("td.LinhaPar").get(7).ownText().replaceAll("\\s.\\s\\w", "");
        String studentRA = page.select("td.LinhaPar").get(4).ownText();

        student.setName(studentName);
        student.setCourse(studentCourse);
        student.setRa(studentRA);

        return student;
    }

    List<Subject> retrieveSubjects(Document page) {
        List<String> subjectNames = new ArrayList<>();
        List<String> absences = new ArrayList<>();
        List<String> partialAverages = new ArrayList<>();
        List<String> finalAverages = new ArrayList<>();

        Elements colunaParElements = page.select("table.GradeNotas tbody tr td.ColunaPar");
        Elements partialAveragesElements = page.select("td.ColunaMP");
        Elements finalAveragesElements = page.select("td.ColunaMF");

        for (Element element : colunaParElements) {
            String elementText = ParserUtil.removeWhitespace(element.ownText());
            if (!element.hasAttr("width")) {
                if (elementText.length() > 4 && !elementText.equals("Não Informado")) {
                    subjectNames.add(elementText);
                }
            } else {
                if (ParserUtil.removeWhitespace(element.previousElementSibling().ownText()).matches("\\b\\d{2}\\b") && !element.nextElementSibling().hasAttr("width")) {
                    absences.add(elementText);
                }
            }
        }
        for (int i = 0; i < finalAveragesElements.size(); i++) {
            partialAverages.add(ParserUtil.removeWhitespace(partialAveragesElements.get(i).ownText()));
            finalAverages.add(ParserUtil.removeWhitespace(finalAveragesElements.get(i).ownText()));
        }
        return buildSubjectList(subjectNames, absences, partialAverages, finalAverages);
    }

    private List<Subject> buildSubjectList(List<String> subjectNames, List<String> absences, List<String> partialAverages, List<String> finalAverages) {
        List<Subject> subjectList = new ArrayList<>();

        for (int i = 0; i < subjectNames.size(); i++) {
            Subject subject = new Subject();
            subject.setName(subjectNames.get(i));
            subject.setAbsences(absences.get(i));
            subject.setPartialAverage(partialAverages.get(i));
            subject.setFinalAverage(finalAverages.get(i));

            subjectList.add(subject);
        }

        return subjectList;
    }
}
