package com.dlg.community.service;

import com.dlg.community.dao.QuestionDao;
import com.dlg.community.dao.UserLoginStatusDao;
import com.dlg.community.dto.PageVO;
import com.dlg.community.dto.QuestionVO;
import com.dlg.community.error.MyException;
import com.dlg.community.pojo.Question;
import com.dlg.community.pojo.UserLoginStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    Logger logger = LoggerFactory.getLogger(QuestionService.class);

    @Autowired
    QuestionDao questionDao;

    @Autowired
    UserLoginStatusDao userLoginStatusDao;

//    内存分页
//
//    public List<QuestionVO> getAllQuestion(){
//
//        List<Question> questionList = questionDao.findAll();
//        List<QuestionVO> questionVOList = new ArrayList<>(questionList.size());
//        QuestionVO questionVO = null;
//        UserLoginStatus loginStatus = null;
//
//        for(Question q : questionList){
//            questionVO = new QuestionVO();
//            BeanUtils.copyProperties(q, questionVO);
//            loginStatus = userLoginStatusDao.selUserById(q.getCreator_id());
//            questionVO.setCreator(loginStatus);
//            questionVOList.add(questionVO);
//
//            logger.info(questionVO.toString());
//        }
//
//        if(questionVOList.size() > 0)
//            return questionVOList;
//        return null;
//    }

    public List<QuestionVO> getQuestionByPage(int start, int end){

        List<Question> questionList = questionDao.getQuestionByLimit(start, end);
        List<QuestionVO> questionVOList = new ArrayList<>(questionList.size());
        QuestionVO questionVO = null;
        UserLoginStatus loginStatus = null;

        for(Question q : questionList){
            questionVO = new QuestionVO();
            BeanUtils.copyProperties(q, questionVO);
            loginStatus = userLoginStatusDao.selUserById(q.getCreator_id());
            questionVO.setCreator(loginStatus);
            questionVOList.add(questionVO);

            logger.info(questionVO.toString());
        }

        if(questionVOList.size() > 0)
            return questionVOList;
        return null;
    }

    public PageVO getQuestion(int currentPage, int items){

        PageVO pageVO = new PageVO();
        pageVO.setCurrentPage(currentPage);
        pageVO.setTotalItem((int)questionDao.count());
        pageVO.setItemOfOnePage(items);
        pageVO.setTotalPage(pageVO.getTotalItem() %pageVO.getItemOfOnePage() == 0 ?
                pageVO.getTotalItem() / pageVO.getItemOfOnePage() : pageVO.getTotalItem() / pageVO.getItemOfOnePage() + 1);

        if(currentPage > pageVO.getTotalPage()){
            pageVO.setCurrentPage(pageVO.getTotalPage());
            currentPage = pageVO.getTotalPage();
        }
        if(currentPage < 1){
            pageVO.setCurrentPage(1);
            currentPage = 1;
        }

        pageVO.setPage(currentPage, items);
        pageVO.setCurrentPageItem(getQuestionByPage(items * (pageVO.getCurrentPage() - 1), items));

        System.out.println("page: " + pageVO);

        return pageVO;
    }

    public QuestionVO getQuestionById(Integer id){

        Question question = questionDao.getQuestionById(id);

        if(question == null){
            throw new MyException("访问的页面不存在");
        }

        QuestionVO questionVO = new QuestionVO();
        questionVO.setCreator(userLoginStatusDao.selUserById(question.getCreator_id()));
        BeanUtils.copyProperties(question, questionVO);

        return questionVO;
    }
}
