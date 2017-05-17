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
 * Created by Calvin, Chuntak on 5/5/2017.
 */

@Controller
public class QuizController {
    /**
     * This returns the quiz page as per http request
     * @param session the current session
     * @return the quiz page
     */
    @RequestMapping(value="/quiz", method = RequestMethod.GET)
    public String loadQuiz(HttpSession session) {
        return "tabs/quiz";
    }

    /**
     * This returns the quiz taker page as per http request
     * @param session the current session
     * @return the quiz taker page
     */
    @RequestMapping(value="/quizTaker", method = RequestMethod.GET)
    public String loadQuizTaker(HttpSession session) {
        return "tabs/quizTaker";
    }

    /**
     * This is the quiz model
     * @return a model for quiz
     */
    @ModelAttribute("quiz")
    public QuizModel getQuizModel(){
        return new QuizModel();
    }

    /**
     * This is the problem model
     * @return a model for problem
     */
    @ModelAttribute("problem")
    public ProblemModel getProblemModel(){
        return new ProblemModel();
    }

    /**
     * This is an http post method that that will save an entire quiz along with its tags
     * and problems
     * @param quiz the quiz model that is going to be stored in the database
     * @param pml the list of problems that the quiz contains
     * @param tagNames the tags that the quiz is tagged with
     * @param session the current session scope
     * @return a quiz model with its own id.
     */
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

    /**
     * This is an http get method that will get quizes given by the quiz id that's within the quiz model
     * @param quiz the quiz model that will contain the options to get a quiz
     * @param session the current session
     * @return a list of quiz models
     */
    @RequestMapping(value="/getQuiz", method = RequestMethod.GET)
    public @ResponseBody ArrayList<QuizModel> getQuiz(@ModelAttribute("quiz") QuizModel quiz, HttpSession session){
        ArrayList<QuizModel> qml = new QuizDAO().getAllQuiz(quiz);
        return qml;
    }

    /**
     * This is an http method that will get quizes given by the quiz id uses the student id to
     * filter out which quiz the student has already done
     * @param quiz quiz model that contains the quiz id
     * @param session the current session
     * @return a quiz model
     */
    @RequestMapping(value="/getStudQuiz", method = RequestMethod.GET)
    public @ResponseBody ArrayList<QuizModel> getStudQuiz(@ModelAttribute("quiz") QuizModel quiz, HttpSession session){
        ArrayList<QuizModel> qml = new QuizDAO().getStudQuiz(quiz, (int) session.getAttribute("id"));
        return qml;
    }

    /**
     * This is an http method that will get the quiz content, which is the list of problems
     * @param quiz the quiz that needs the list of problems
     * @param session the current session
     * @return the quiz model with its problem list in side the model
     */
    @RequestMapping(value="/getQuizContent", method = RequestMethod.GET)
    public @ResponseBody QuizModel getQuizContent(@ModelAttribute("quiz") QuizModel quiz, HttpSession session){
        ArrayList<ProblemModel> pml = new QuizDAO().getProblems(quiz.getId());
        ArrayList<String> tagList = new TagDAO().getTag(quiz.getId(), "quiz");
        QuizModel qm = new QuizModel();
        qm.setQuizTaggedList(tagList);
        qm.setQuestionList(pml);
        return qm;
    }

    /**
     * This is getting the probloms for student where we use their id to get their
     * autosaved responses to questions
     * @param quiz the quiz with the quiz id to identify the quiz
     * @param session the current session
     * @return a list of problem model
     */
    @RequestMapping(value="/getQuizProblemsForStudent", method = RequestMethod.GET)
    public @ResponseBody ArrayList<ProblemModel> getQuizProblemForStudent(@ModelAttribute("quiz") QuizModel quiz, HttpSession session){
        int studentId = (int) session.getAttribute("id");
        return new QuizDAO().getProblemsForStudent(quiz.getId(), studentId);
    }

    /**
     * saves the student's answer for 1 problem
     * @param problem the problem that the answer will be saved
     * @param session the current session
     * @return returns true if sucess update else fails
     */
    @RequestMapping(value="/updateStudentAnsForProbInQuiz", method = RequestMethod.GET, produces="application/json")
    public @ResponseBody boolean updateStudentAnsForProbInQuiz(@ModelAttribute("problem") ProblemModel problem, HttpSession session){
        int studentId = (int) session.getAttribute("id");
        return new QuizDAO().updateStudentAnsForProbInQuiz(problem, studentId);
    }


    /**
     * Deletes a quiz given the quiz model with quiz id
     * @param quiz the quiz to be deleted
     * @param session the current session
     * @return true if successful delete else fails
     */
    @RequestMapping(value="/deleteQuiz", method = RequestMethod.GET, produces="application/json")
    public @ResponseBody boolean deleteQuiz(@ModelAttribute("quiz") QuizModel quiz, HttpSession session){
        return new QuizDAO().deleteQuiz(quiz);
    }

    /**
     * Submit the quiz for a student, it also provide the student the grade
     * @param problem the last problem to be saved before grading
     * @param session the current scope
     * @return the student's grade for the quiz.
     */
    @RequestMapping(value="/submitQuiz", method = RequestMethod.GET, produces="application/json")
    public @ResponseBody double submitQuiz(@ModelAttribute("problem") ProblemModel problem, HttpSession session){
        return new QuizDAO().submitQuiz(problem, (int) session.getAttribute("id"));
    }



}
