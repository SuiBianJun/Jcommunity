package com.dlg.community.controller;

import com.alibaba.fastjson.JSON;
import com.dlg.community.dao.QuestionDao;
import com.dlg.community.dao.UserLoginStatusDao;
import com.dlg.community.dto.GetTokenDto;
import com.dlg.community.dto.PageVO;
import com.dlg.community.pojo.*;
import com.dlg.community.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
public class OAuthController {

    @Autowired
    GetUserInfoParameters getUserInfoParameters;

    @Autowired
    GetTokenParameters getTokenParameters;

    @Autowired
    GetTokenDto getTokenDto;

    @Autowired
    UserLoginStatusDao userLoginStatusDao;

    @Autowired
    QuestionService questionService;

    static Logger logger = LoggerFactory.getLogger(OAuthController.class);

    @RequestMapping(value = {"/", "index"})
    public String home(HttpServletRequest req, @RequestParam(name =  "currentPage", defaultValue = "1") String currentPage,
                       @RequestParam(name = "items", defaultValue = "5") String items){

        HttpSession hs = req.getSession();
        // 查询问题

        hs.setAttribute("questionList", questionService.getQuestion(Integer.valueOf(currentPage), Integer.valueOf(items)));

        PageVO pageVO = questionService.getQuestion(Integer.valueOf(currentPage), Integer.valueOf(items));

        hs.setAttribute("page", pageVO);

        UserLoginStatus userLoginStatus = null;
        String sessionId = hs.getId();
        // 无session,查看cookie

        if(req.getCookies() != null) {
            Cookie[] cookies = req.getCookies();
            for (Cookie cookie : cookies) {
                if ("logging-user".equals(cookie.getName())) {
                    String token = cookie.getValue();
                    // 有cookie
                    userLoginStatus = userLoginStatusDao.selByToken(token);

                    if(userLoginStatus == null)
                        break;

                    logger.debug("userLoginStatus: ", userLoginStatus);
                    // 设置session
                    GithubUserInfo githubUserInfo = new GithubUserInfo();
                    githubUserInfo.setId(userLoginStatus.getUser_id());
                    githubUserInfo.setLogin(userLoginStatus.getUser_name());
                    githubUserInfo.setAvatar_url(userLoginStatus.getUser_avator_url());
                    hs.setAttribute("user", githubUserInfo);
                    // 允许登录
                }
            }
        }
        return "index";
    }

    @RequestMapping("/callback")
    public String callback(String code, HttpServletRequest req, HttpServletResponse resp) throws IOException {

        getTokenParameters.setCode(code);
        String result = getTokenDto.getToken(getTokenParameters);
        logger.debug("result: {}", result);
        String accessToken = result.split("&")[0].split("=")[1];
        getUserInfoParameters.setAccess_token(accessToken);
        logger.debug("userInfoParameters: {}", getUserInfoParameters);
        //json string
        String userInfo = getTokenDto.getUserInfo(getUserInfoParameters);
        logger.info("userInfo: {}", userInfo);
        GithubUserInfo githubUserInfo = JSON.parseObject(userInfo, GithubUserInfo.class);
        if(githubUserInfo != null){
            // 登录成功
            // 更新sessionId操作
            HttpSession hs = req.getSession();
            hs.setAttribute("user", githubUserInfo);

            String sessionId = hs.getId();
            logger.debug("user sessionId: {}", sessionId);

            UserLoginStatus userLoginStatus = new UserLoginStatus();
            userLoginStatus.setUser_token(sessionId);
            userLoginStatus.setUser_name(githubUserInfo.getLogin());
            userLoginStatus.setUser_id(githubUserInfo.getId());
            userLoginStatus.setGmt_create(new Date());
            userLoginStatus.setGmt_modify(new Date());
            userLoginStatus.setUser_avator_url(githubUserInfo.getAvatar_url());

            if(!(userLoginStatusDao.existByUserId(githubUserInfo.getId()) > 0)){
                userLoginStatusDao.save(userLoginStatus);
            }else{
                userLoginStatusDao.updateToken(sessionId, userLoginStatusDao.existByUserId(githubUserInfo.getId()));
            }

            Cookie cookie = new Cookie("logging-user", sessionId);

            cookie.setPath("localhost:8080/");
            cookie.setMaxAge(7 * 24 * 60 * 60);
            resp.addCookie(cookie);

            System.out.println("name: " + githubUserInfo.getLogin());
        }
        return "redirect:index";
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request){

        // 清除当前用户的token
        GithubUserInfo githubUserInfo = (GithubUserInfo) request.getSession().getAttribute("user");
        userLoginStatusDao.deleteByUserId(githubUserInfo.getId());
        request.getSession().removeAttribute("user");
        return "redirect:index";

    }

    /*@RequestMapping(value = {"/", "/index"})
    private String home(){
        return "index";
    }*/

}
