package com.dlg.community.dao;

import com.dlg.community.pojo.Comment;
import com.dlg.community.pojo.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentDao extends JpaRepository<Comment, Integer> {

    @Query("select c from Comment  c where c.question_id = :questionId and c.user_id = :userId and c.type = :type and c.reply_id = :replyId")
    List<Comment> getComment(@Param("questionId") Integer questionId, @Param("userId") Integer userId, @Param("type") Integer type, @Param("replyId") Integer replyId);
}
