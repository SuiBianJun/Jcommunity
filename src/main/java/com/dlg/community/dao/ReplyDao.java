package com.dlg.community.dao;

import com.dlg.community.pojo.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplyDao extends JpaRepository<Reply, Integer> {
}
