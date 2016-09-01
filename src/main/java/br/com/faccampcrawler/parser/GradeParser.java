package br.com.faccampcrawler.parser;

import br.com.faccampcrawler.model.Grade;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Component
class GradeParser {
    List<List<Grade>> retrieveGrades(Document document) {
        List<List<Grade>> responseList = new ArrayList<>();
        List<Grade> gradesList = new ArrayList<>();
        int index = 0;

        Elements columns = document.select("td.GradeNotas");
        //Elements dates = document.select("td.GradeNotas table tbody tr td.Descricoes");
        Elements grades = document.select("td.GradeNotas table tbody tr td.GradeNotasDestaque");

        for (Element element : columns) {
            if (removeWhitespace(element.ownText()).matches("\\b\\w{4}\\b") && columns.first() != element) {
                responseList.add(gradesList);
                gradesList = new ArrayList<>();
            } else {
                if (element.children().size() != 0) {
                    Grade grade = new Grade();
                    //TODO: Tratar datas como Date, de fato
                    grade.setDate(Calendar.getInstance().getTime());
                    grade.setValue(removeWhitespace(grades.get(index).ownText()));
                    gradesList.add(grade);
                    index++;
                }
                if (columns.last() == element) {
                    responseList.add(gradesList);
                    gradesList = new ArrayList<>();
                }
            }
        }
        return responseList;
    }

    private String removeWhitespace(String string) {
        return string.replaceAll("\u00a0", "");
    }
}
