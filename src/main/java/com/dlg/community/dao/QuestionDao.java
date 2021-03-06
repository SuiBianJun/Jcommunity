package com.dlg.community.dao;

import com.dlg.community.pojo.Question;
import com.dlg.community.pojo.QuestionRelative;
import com.dlg.community.pojo.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository

public interface QuestionDao extends JpaRepository<Question, Integer> {

    @Query(nativeQuery = true, value = "select * from t_question_info limit :start,:count")
    List<Question> getQuestionByLimit(@Param("start") int start, @Param("count") int count);

    @Query(nativeQuery = true, value = "select * from t_question_info where creator_id=:creatorId limit :start,:count")
    List<Question> getQuestionByLimitAndCreatorId(@Param("creatorId") int creatorId, @Param("start") int start, @Param("count") int count);

    @Query(nativeQuery = true, value = "select count(1) from t_question_info where creator_id=:creatorId")
    int countQuestionByCreatorId(@Param("creatorId") int creatorId);

    @Query("select q from Question q where q.id=:id")
    Question getQuestionById(@Param("id") Integer id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true, value = "update t_question_info set question_title=:title, question_detail=:detail, question_tags=:tags where id=:id")
    void updateById(@Param("id") Integer id, @Param("title") String title, @Param("detail") String detail, @Param("tags") String tags);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true, value="update t_question_info set view_count = view_count + 1 where id=:id")
    void updateQuestionViewCount(@Param("id") Integer id);

    @Query("select r from Reply r where r.question_id = :id and r.type = :type")
    List<Reply> getAllQuestionReplyById(@Param("id") Integer id, @Param("type") Integer type);

    List<QuestionRelative> getQuestionRelative(@Param("id") Integer id);
}
