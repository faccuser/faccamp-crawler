package br.com.faccampcrawler.controller;

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
            if (student != null) {
                return buildSuccessResponse(student);
            }  else {
                return buildFailResponse("Could not retrieve student data.");
            }
        } catch (Exception e) {
            return buildFailResponse("Something went wrong.");
        }
    }
}