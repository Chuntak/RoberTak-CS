package com.backpack.dao;

import com.backpack.models.ChoiceModel;
import com.backpack.models.ProblemModel;
import com.backpack.models.QuizModel;
import com.backpack.models.SyllabusModel;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Chuntak on 5/5/2017.
 */
public class QuizDAO extends AssignmentDAO {

    /**
     * This gets all the quizes from the database that
     * are related to the course that is in the quiz
     * model identified by its id
     * @param qm quiz model that contains the courseId
     * @return a list of quiz models for a course
     */
    public ArrayList<QuizModel> getAllQuiz(QuizModel qm) {
        String query = "call get_gradable(?,?,?)";
        ArrayList<QuizModel> qml = dbs.getJdbcTemplate().query(query, new Object[] {qm.getCourseId(), "quiz", 0}, new QuizModelExtractor());
        return qml;
    }

    /**
     * This gets the quizes from the database that
     * are related to the course that the student
     * did not submit
     * @param qm quiz model that contains the courseId
     * @param studentId the student id
     * @return a list of quiz models for a course
     */
    public ArrayList<QuizModel> getStudQuiz(QuizModel qm, int studentId) {
        String query = "call get_quiz(?,?)";
        ArrayList<QuizModel> qml = dbs.getJdbcTemplate().query(query, new Object[] {qm.getCourseId(), studentId}, new QuizModelExtractor());
        return qml;
    }

    /**
     * This gets all the problems that is related to
     * the course that is related to the quiz that is in
     * the problem model idenified by its id
     * @param pm problem model that contains the quizId
     * @return a list of problem models for a quiz
     */
    public ArrayList<ProblemModel> getProblems(ProblemModel pm) {
        return getProblems(pm.getQuizId());
    }

    /**
     * This gets all the problems that is
     * that is related to the quiz that is in
     * the problem model idenified by its id
     * @param quizId the quiz id that identifies the quiz
     * @return a list of problem models for a quiz
     */
    public ArrayList<ProblemModel> getProblems(int quizId) {
        String query = "call get_problem(?)";
        ArrayList<ProblemModel> pml = dbs.getJdbcTemplate().query(query, new Object[] {quizId}, new ProblemModelExtractor());
        return getMultipleChoices(pml, quizId);
    }

    /**
     * This gets all the problems for the quiz with the student's answer
     * @param quizId the quiz id that identifies the quiz
     * @param studentId the student id that identifies the student
     * @return a list of problem models for a quiz with student's answer
     */
    public ArrayList<ProblemModel> getProblemsForStudent(int quizId, int studentId) {
        String query = "call get_studentProbQuiz(?,?,?)";
        ArrayList<ProblemModel> pml = dbs.getJdbcTemplate().query(query, new Object[] {studentId, quizId, new Date()}, new ProblemModelExtractor());
        return getMultipleChoices(pml, quizId);
    }

    /**
     * Saves the student's answer for a problem in a quiz
     * @param pm problem model that contains the quiz id that the
     *           problem is related to, the problem id for itself,
     *           and the student's answer they entered for that question
     * @param studentId the student id which identifies the student
     * @return true if successfully updated the database else false
     */
    public boolean updateStudentAnsForProbInQuiz(ProblemModel pm, int studentId) {
        String query = "call update_studentAnsForProbInQuiz(?,?,?,?)";
        int rowsAffected = dbs.getJdbcTemplate().update(query, studentId, pm.getQuizId(), pm.getProblemId(), pm.getAnswer());
        return rowsAffected > 0;
    }

    /**
     * Saves the student's answer for a problem in a quiz
     * and grade the quiz
     * @param pm problem model that contains the quiz id that the
     *           problem is related to, the problem id for itself,
     *           and the student's answer they entered for that question
     * @param studentId the student id which identifies the student
     * @return
     */
    public double submitQuiz(ProblemModel pm, int studentId) {
        String query = "call grade_student(?,?,?,?)";
        double grade = dbs.getJdbcTemplate().query(query,
                new Object[] {studentId, pm.getQuizId(), pm.getProblemId(), pm.getAnswer()},
                new StudentGradeExtractor());
        return grade;
    }

    /**
     * Deletes a particular problem for a quiz from the database
     * @param pm problem model that contains a problem id and
     *           quiz id that identifies a problem and quiz respectively
     * @return the number of rows affect by the jdbc call
     */
    public int deleteProblemsFromQuiz(ProblemModel pm) {
        return deleteProblemsFromQuiz(pm.getProblemId(), pm.getQuizId());
    }

    /**
     * Deletes a particular problem for a quiz from the database
     * @param problemId an id that identifies a problem
     * @param quizId an id that identifies a quiz
     * @return the number of rows affect by the jdbc call
     */
    public int deleteProblemsFromQuiz(int problemId, int quizId) {
        String query = "call delete_problem_from_quiz(?,?)";
        return dbs.getJdbcTemplate().update(query, problemId, quizId);
    }

    /**
     * Adds or edit a quiz and also add edit the problems of a quiz
     * @param qm a quiz model that contains all the attributes of a quiz
     * @param pml a problem model that contains attributes of a problem
     * @return the quizmodel with its id and the problems with their ids
     */
    public QuizModel updateQuiz(QuizModel qm, ArrayList<ProblemModel> pml) {
        String query = "call update_gradable(?,?,?,?,?,?,?,?,?,?)";
        ArrayList<QuizModel> qml = dbs.getJdbcTemplate().query(query, new Object[] { qm.getId(),
                qm.getCourseId(), qm.getTitle(), "", "quiz", qm.getMaxGrade(), qm.getDueDate(), "", "", 0 }, new QuizModelExtractor());
        QuizModel quizModel;
        /*MAKES SURE THAT WE HAVE THE CORRECT LIST WITH THE ID*/
        if(qml != null && qml.size() > 0)  quizModel = qml.get(0); else quizModel = qm;
        if(pml != null && pml.size() > 0) {
            for (Iterator<ProblemModel> iterator = pml.iterator(); iterator.hasNext();) {
                ProblemModel pm = iterator.next();
                if(pm.isDeleted() && pm.getProblemId() > 0) {
                    /*DELETE QUIZ FROM DB AND LIST*/
                    deleteProblemsFromQuiz(pm.getProblemId(), qm.getId());
                    iterator.remove();
                }
                else if(pm.isDeleted()){
                    /*DELETE QUIZ FROM LIST NOT FROM DB CUZ IT WASNT THERE IN THE FIRST PLACE*/
                    iterator.remove();
                }
                else {
                    /*UPDATE THE PROBLEM*/
                    query = "call update_problem(?,?,?,?,?,?)";
                    ProblemModel tmp = dbs.getJdbcTemplate().query(query, new Object[]{
                            pm.getProblemId(), pm.getType(), pm.getQuestion(), pm.getAnswer(), pm.getPointsWorth(), quizModel.getId()}, new ProblemModelExtractor()).get(0);
                    if (tmp != null) {
                        /*SET ID OF THE PROBLEM*/
                        pm.setProblemId(tmp.getProblemId());
                        /*UPDATE EACH PROBLEM CHOICE IN DB PER PROBLEM*/
                        query = "call update_problemChoice(?,?,?)";
                        if (pm.getType().equals("M/C")){
                            for (ChoiceModel cm : pm.getChoices()) {
                            /*UPDATE THE DB*/
                                ArrayList<ChoiceModel> cml = dbs.getJdbcTemplate().query(query,
                                        new Object[]{cm.getId(), pm.getProblemId(), cm.getAnswerChoice()}, new ChoiceModelExtractor());
                            /*SET THE ID OF THE CHOICE*/
                                if (cml != null && cml.size() > 0) cm.setId(cml.get(0).getId());
                            }
                        }
                    }
                }
            }
        }
        /*SETS THE QUESTION LIST */
        quizModel.setQuestionList(pml);
        return quizModel;
    }

    /**
     * Deletes a quiz from the database
     * @param qm the quiz model that gets deleted
     * @return true if delete success else false
     */
    public boolean deleteQuiz(QuizModel qm) {
        String query = "call delete_gradable(?)";
        int rowAffected = dbs.getJdbcTemplate().update(query, qm.getId());
        return rowAffected > 0;
    }

    /**
     *  Gets the multiple choices for the problems in the problem list
     */
    private ArrayList<ProblemModel> getMultipleChoices(ArrayList<ProblemModel> pml, int quizId) {
        if(pml == null) return null;
   /*PUT ALL IN HASHMAP FOR PUTTING QUIZ CHOICES FASTER*/
        HashMap<Integer, ProblemModel> pmh = new HashMap<Integer, ProblemModel>();
        for(ProblemModel pm : pml){
            pmh.put(pm.getProblemId(), pm);
        }

        /*THIS CALLS THE DATABASE FOR ALL THE CHOICES*/
        String query = "call get_problemChoice(?,?)";
        ArrayList<ChoiceModel> cml = dbs.getJdbcTemplate().query(query, new Object[] {0, quizId}, new ChoiceModelExtractor());

        for(ChoiceModel cm : cml) {
            ProblemModel pm = pmh.get(cm.getQuestionId());
            if(pm.getChoices() == null) pm.setChoicesList(new ArrayList<ChoiceModel>());
            pm.getChoices().add(cm);
        }

        return pml;
    }

    /**
     * This is a private class that extracts problems from the resultset and returns an arraylist of problem models
     */
    private static class ProblemModelExtractor implements ResultSetExtractor<ArrayList<ProblemModel>> {
        @Override
        public ArrayList<ProblemModel> extractData(ResultSet rs) throws SQLException, DataAccessException {
            if(rs != null && rs.getMetaData().getColumnCount() > 0) {
                ArrayList<ProblemModel> pml = new ArrayList<ProblemModel>();
                while (rs.next()) {
                    ProblemModel pm = new ProblemModel();
                    if (columnExists(rs, "id")) pm.setProblemId(rs.getInt("id"));
                    if (columnExists(rs, "type")) pm.setType(rs.getString("type"));
                    if (columnExists(rs, "question")) pm.setQuestion(rs.getString("question"));
                    if (columnExists(rs, "answer")) pm.setAnswer(rs.getString("answer"));
                    if (columnExists(rs, "pointsWorth")) pm.setPointsWorth(rs.getDouble("pointsWorth"));
                    pml.add(pm);
                }
                return pml;
            }
            return null;
        }
    }

    /**
     * This is an extractor for the student's grade
     */
    private static class StudentGradeExtractor implements ResultSetExtractor<Double> {
        @Override
        public Double extractData(ResultSet rs) throws SQLException, DataAccessException {
            double grade = 0;
            rs.next();
            if(rs != null && rs.getMetaData().getColumnCount() > 0) {
                if(columnExists(rs, "grade")) grade = rs.getDouble("grade");
            }
            return grade;
        }
    }
    /**
     * Extractor that extracts quiz models from resultset and returns an array of quiz models
     */
    private static class QuizModelExtractor implements ResultSetExtractor<ArrayList<QuizModel>> {
        @Override
        public ArrayList<QuizModel> extractData(ResultSet rs) throws SQLException, DataAccessException {
            if(rs != null && rs.getMetaData().getColumnCount() > 0) {
                ArrayList<QuizModel> qml = new ArrayList<QuizModel>();
                while (rs.next()) {
                    QuizModel qm = new QuizModel();
                    if (columnExists(rs, "id")) qm.setId(rs.getInt("id"));
                    if (columnExists(rs, "crsId")) qm.setCourseId(rs.getInt("crsId"));
                    if (columnExists(rs, "dueDate")) qm.setDate(rs.getTimestamp("dueDate"));
                    if (columnExists(rs, "title")) qm.setTitle(rs.getString("title"));
                    if (columnExists(rs, "maxGrade")) qm.setMaxGrade(rs.getInt("maxGrade"));
                    qml.add(qm);
                }
                return qml;
            }
            return null;
        }
    }

    /**
     * Extractor that extracts a choice model from result set and returns a list of choice models
     */
    private static class ChoiceModelExtractor implements ResultSetExtractor<ArrayList<ChoiceModel>> {
        @Override
        public ArrayList<ChoiceModel> extractData(ResultSet rs) throws SQLException, DataAccessException {
            if(rs != null && rs.getMetaData().getColumnCount() > 0) {
                ArrayList<ChoiceModel> cml = new ArrayList<ChoiceModel>();
                while (rs.next()) {
                    ChoiceModel cm = new ChoiceModel();
                    if (columnExists(rs, "id")) cm.setId(rs.getInt("id"));
                    if (columnExists(rs, "problemId")) cm.setQuestionId(rs.getInt("problemId"));
                    if (columnExists(rs, "answerChoice")) cm.setAnswerChoice(rs.getString("answerChoice"));
                    cml.add(cm);
                }
                return cml;
            }
            return null;
        }
    }
}