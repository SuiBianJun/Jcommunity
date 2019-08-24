package com.dlg.community.controller;

import com.dlg.community.pojo.GithubUserInfo;
import com.dlg.community.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    ProfileService profileService;

    @GetMapping("/myquestion")
    public String myQuestion(@RequestParam(name = "currentPage", defaultValue = "1") String currentPage,
                             @RequestParam(name = "items", defaultValue = "5") String items,
                             HttpServletRequest req){

        GithubUserInfo githubUserInfo = (GithubUserInfo) req.getSession().getAttribute("user");

        req.getSession().setAttribute("page", profileService.getQuestion(Integer.valueOf(currentPage), Integer.valueOf(items), githubUserInfo.getId()));

        return "profile/profile-myquestion";
    }

}
