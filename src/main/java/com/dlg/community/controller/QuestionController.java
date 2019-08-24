package com.dlg.community.controller;

import com.dlg.community.dao.QuestionDao;
import com.dlg.community.pojo.GithubUserInfo;
import com.dlg.community.pojo.Question;
import com.dlg.community.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
public class QuestionController {

    Logger logger = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    QuestionDao questionDao;

    @Autowired
    QuestionService questionService;

    @GetMapping("/publish")
    public String publish(@RequestParam(name = "id", defaultValue = "0") String id, HttpServletRequest req){

        if(!"0".equals(id)){
            req.getSession().setAttribute("modifyQuestion", questionService.getQuestionById(Integer.valueOf(id)));
        }else{
            req.getSession().removeAttribute("modifyQuestion");
        }

        logger.info("publish");
        return "publish";
    }

    @RequestMapping("/pub")
    public String publish2(@RequestParam("title") String title, @RequestParam("description") String description, @RequestParam("tags") String tags, Model m,
                           HttpServletRequest req){
        logger.info("title: " + title);
        /*m.addAttribute("title", title);
        m.addAttribute("description", description);
        m.addAttribute(tags, tags);*/
        logger.debug("发布问题");
        GithubUserInfo user = (GithubUserInfo) req.getSession().getAttribute("user");
        if(user == null){
            m.addAttribute("publish-status", "login-status");
            m.addAttribute("login-status", "请先登录");
            logger.info("请先登录");
            return "publish";
        }

        if(title == null){
            m.addAttribute("publish-status", "title-status");
            m.addAttribute("title-status", "请输入标题");
            return "publish";
        }

        Question question = new Question();
        question.setQuestion_title(title).setQuestion_detail(description).setQuestion_tags(tags)
                .setGmt_create(new Date()).setGmt_update(new Date()).setCreator_id(user.getId());

        // questionDao.exists()
        if(req.getParameter("modify") != null){
            question.setId(Integer.valueOf(req.getParameter("modify")));
            questionDao.updateById(question.getId(), question.getQuestion_title(), question.getQuestion_detail(), question.getQuestion_tags());
        }else{
            questionDao.save(question);
        }

        return "publish";
    }

    @RequestMapping("/question/{id}")
    public String questionDetail(@PathVariable("id") Integer questionId, HttpServletRequest req){

        req.getSession().setAttribute("question", questionService.getQuestionById(questionId));

        return "question";
    }

}
