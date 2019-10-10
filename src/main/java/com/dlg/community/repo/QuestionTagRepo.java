package com.dlg.community.repo;

import com.dlg.community.entity.QuestionTag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionTagRepo extends CrudRepository<QuestionTag, String> {
}
