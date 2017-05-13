package com.backpack.controller;

import com.backpack.dao.QuizDAO;
import com.backpack.dao.TagDAO;
import com.backpack.models.ProblemModel;
import com.backpack.models.QuizModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

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

    /*QUIZ model*/
    @ModelAttribute("quiz")
    public QuizModel getQuizModel(){
        return new QuizModel();
    }

    /*PROBLEMS model*/
    @ModelAttribute("problem")
    public ProblemModel getProblemModel(){
        return new ProblemModel();
    }

    @RequestMapping(value="/updateQuiz", method = RequestMethod.POST)
    public @ResponseBody QuizModel updateQuiz(@ModelAttribute("quiz") QuizModel quiz,
                                              @RequestBody(required = false) ArrayList<ProblemModel> pml,
                                              @RequestParam(value = "tagNames", required = false) ArrayList<String> tagNames,
                                                          HttpSession session) {
        QuizModel qm = new QuizDAO().updateQuiz(quiz, pml);
        qm.setQuizTaggedList(tagNames);
        if(tagNames != null) new TagDAO().updateTaggedGradable(tagNames, qm.getId());
        return qm;
    }

    @RequestMapping(value="/getQuiz", method = RequestMethod.GET)
    public @ResponseBody ArrayList<QuizModel> getQuiz(@ModelAttribute("quiz") QuizModel quiz, HttpSession session){
        ArrayList<QuizModel> qml = new QuizDAO().getQuiz(quiz);
        return qml;
    }

    @RequestMapping(value="/getQuizContent", method = RequestMethod.GET)
    public @ResponseBody QuizModel getQuizContent(@ModelAttribute("quiz") QuizModel quiz, HttpSession session){
        ArrayList<ProblemModel> pml = new QuizDAO().getProblems(quiz.getId());
        ArrayList<String> tagList = new TagDAO().getTag(quiz.getId(), "quiz");
        QuizModel qm = new QuizModel();
        qm.setQuizTaggedList(tagList);
        qm.setQuestionList(pml);
        return qm;
    }

    @RequestMapping(value="/deleteQuiz", method = RequestMethod.GET, produces="application/json")
    public @ResponseBody boolean deleteQuiz(@ModelAttribute("quiz") QuizModel quiz, HttpSession session){
        return new QuizDAO().deleteQuiz(quiz);
    }



}
