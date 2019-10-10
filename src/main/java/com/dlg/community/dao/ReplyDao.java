package com.dlg.community.dao;

import com.dlg.community.pojo.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReplyDao extends JpaRepository<Reply, Integer> {

    @Query("select r from Reply  r where r.question_id = :questionId and r.user_id = :userId and r.type = :type")
    List<Reply> getComment(@Param("questionId") Integer questionId, @Param("userId") Integer userId, @Param("type") Integer type);

}
