package br.com.faccampcrawler.controller;

import br.com.faccampcrawler.cases.InvalidLoginException;
import br.com.faccampcrawler.cases.RetrieveStudentData;
import br.com.faccampcrawler.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class StudentController extends AbstractController {

    @Autowired
    private RetrieveStudentData retrieveStudentData;

    @CrossOrigin
    @RequestMapping(value = "/retrieve", method = RequestMethod.POST)
    public Map retrieve(@RequestParam(value = "ra") String ra,
                        @RequestParam(value = "password") String password) {

        try {
            Student student = retrieveStudentData.retrieve(ra, password);
            return buildSuccessResponse(student);
        } catch (InvalidLoginException e) {
            return buildFailResponse("User credentials are not valid. " + e.getMessage());
        } catch (Exception e) {
            return buildErrorResponse("Something wen wrong. " + e.getMessage());
        }
    }
}