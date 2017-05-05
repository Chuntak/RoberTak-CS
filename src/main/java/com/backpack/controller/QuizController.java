package com.backpack.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by Calvin on 5/5/2017.
 */

@Controller
public class QuizController {
    /*returns the announcements page*/
    @RequestMapping(value="/quiz", method = RequestMethod.GET)
    public String loadQuiz(HttpSession session) {
        return "tabs/quiz";
    }

}
