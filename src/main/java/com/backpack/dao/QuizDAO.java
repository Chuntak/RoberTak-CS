package com.backpack.dao;

import com.backpack.models.ProblemModel;
import com.backpack.models.QuizModel;
import com.backpack.models.SyllabusModel;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Chuntak on 5/5/2017.
 */
public class QuizDAO extends AssignmentDAO {

    public ArrayList<QuizModel> getQuiz(QuizModel qm) {
        String query = "call get_gradable(?,?,?)";
        ArrayList<QuizModel> qml = dbs.getJdbcTemplate().query(query, new Object[] {qm.getCourseId(), "quiz", 0}, new QuizModelExtractor());
        return qml;
    }

    public ArrayList<ProblemModel> getProblems(ProblemModel pm) {
        return getProblems(pm.getQuizId());
    }

    public ArrayList<ProblemModel> getProblems(int quizId) {
        String query = "call get_problem(?)";
        ArrayList<ProblemModel> qml = dbs.getJdbcTemplate().query(query, new Object[] {quizId}, new ProblemModelExtractor());
        return qml;
    }

    public int deleteProblemsFromQuiz(ProblemModel pm) {
        return deleteProblemsFromQuiz(pm.getProblemId(), pm.getQuizId());
    }

    public int deleteProblemsFromQuiz(int problemId, int quizId) {
        String query = "call delete_problem_from_quiz(?,?)";
        return dbs.getJdbcTemplate().update(query, problemId, quizId);
    }

    public QuizModel updateQuiz(QuizModel qm, ArrayList<ProblemModel> pml) {
        String query = "call update_gradable(?,?,?,?,?,?,?,?,?)";
        ArrayList<QuizModel> qml = dbs.getJdbcTemplate().query(query, new Object[] { qm.getId(),
                qm.getCourseId(), qm.getTitle(), "", "quiz", qm.getMaxGrade(), qm.getDueDate(), "", "" }, new QuizModelExtractor());
        QuizModel quizModel;
        if(qml != null && qml.size() > 0)  quizModel = qml.get(0); else quizModel = qm;
        if(pml != null && pml.size() > 0) {
            query = "call update_problem(?,?,?,?,?)";
            for (Iterator<ProblemModel> iterator = pml.iterator(); iterator.hasNext();) {
                ProblemModel pm = iterator.next();
                if(pm.isDeleted() && pm.getProblemId() > 0) {
                    deleteProblemsFromQuiz(pm.getProblemId(), qm.getId());
                    iterator.remove();
                }
                else if(pm.isDeleted()){
                    iterator.remove();
                }
                else {
                    ProblemModel tmp = dbs.getJdbcTemplate().query(query, new Object[]{
                            pm.getProblemId(), "quiz", pm.getQuestion(), pm.getAnswer(), quizModel.getId()}, new ProblemModelExtractor()).get(0);
                    if (tmp != null) pm.setProblemId(tmp.getProblemId());
                }
            }

        }
        quizModel.setQuestionList(pml);
        return quizModel;
    }

    public boolean deleteQuiz(QuizModel qm) {
        String query = "call delete_gradable(?)";
        int rowAffected = dbs.getJdbcTemplate().update(query, qm.getId());
        return rowAffected > 0;
    }

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
                    pml.add(pm);
                }
                return pml;
            }
            return null;
        }
    }

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
}