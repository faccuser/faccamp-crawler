package br.com.faccampcrawler.crawler;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class StudentCrawler {
    private static final String HOME_URL = "http://www.psxportalacademico.com.br/faccamp/index.php";
    private static final String LOGIN_VALIDATION_URL = "http://www.psxportalacademico.com.br/faccamp/validacao.php";
    private static final String SUBJECTS_URL = "http://www.psxportalacademico.com.br/faccamp/pg_portal.php?frame=frame_alu_notas.php&slc=X&frame_notas=frame_alu_notas_resultados.php";
    private static final String GRADES_URL = "http://www.psxportalacademico.com.br/faccamp/pg_portal.php?frame=frame_alu_notas.php&slc=X&frame_notas=frame_alu_notas_grade.php&etapa=ML";
    private static final String EDP_GRADES_URL = "http://www.psxportalacademico.com.br/faccamp/pg_portal.php?frame=frame_alu_notas.php&slc=X&frame_notas=frame_alu_notas_grade.php&etapa=ED";

    private Map<String, String> cookies;

    public Document executeLogin(String ra, String password) {
        try {
            Connection.Response loginForm = Jsoup.connect(HOME_URL)
                    .method(Connection.Method.GET)
                    .execute();

            cookies = loginForm.cookies();

            return Jsoup.connect(LOGIN_VALIDATION_URL)
                    .data("user", ra)
                    .data("senha", password)
                    .cookies(cookies)
                    .post();
        } catch (IOException e) {
            //TODO: Configurar logs
            System.out.println("[ERROR] Could not execute login for ra " + ra + "\nException: " + e.getMessage());
            return null;
        }
    }

    public Document retrieveSubjectsPage(Map<String, String> cookies) {
        try {
            return Jsoup.connect(SUBJECTS_URL)
                    .cookies(cookies)
                    .execute().parse();
        } catch (IOException e) {
            System.out.println("Failed to connect to page containing absences and averages");
            return null;
        }
    }

    public Document retrieveGradesPage(Map<String, String> cookies) {
        try {
            return Jsoup.connect(GRADES_URL)
                    .cookies(cookies)
                    .execute().parse();
        } catch (IOException e) {
            System.out.println("Failed to connect to page containing grades");
            return null;
        }
    }

    public Document retrieveEDPGradesPage(Map<String, String> cookies) {
        try {
            return Jsoup.connect(EDP_GRADES_URL)
                    .cookies(cookies)
                    .execute().parse();
        } catch (IOException e) {
            System.out.println("Failed to connect to page containing EDP grades");
            return null;
        }
    }

    public synchronized Map<String, String> getCookies() {
        return cookies;
    }
}
