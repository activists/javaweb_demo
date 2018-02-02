package com.fengfw.land.controller;


import com.fengfw.land.model.Message;
import com.fengfw.land.model.User;
import com.fengfw.land.service.UserJDBC;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
public class MyController {

    @GetMapping("/")
    public String home(){
        return "home";
    }

    @RequestMapping(value = "/login")
    public String login(@ModelAttribute(value="user") User user){
        return "login";
    }


    @RequestMapping(value="/userInfo")
    public String userLogin( Model model,HttpServletRequest request){
        UserJDBC userJDBC=new UserJDBC();
        String username=request.getRemoteUser();
        String password=userJDBC.getPassword(username);
        if(userJDBC.getVerfy(username,password)){
            int money=-999;
            if(username!=null){
                money=userJDBC.getMoney(username,password);
                model.addAttribute("username",username);
                model.addAttribute("password",password);
                model.addAttribute("money",money);
            }
            if(money!=-1&&money!=-999){
                return "userInfo";
            }
        }
        return "error";
    }

    @RequestMapping(value="/modify")
    public String modify(@ModelAttribute(value="message") Message message, Model model,HttpServletRequest request){
        model.addAttribute("username1",request.getRemoteUser());
        return "modify";
    }

    @RequestMapping(value="/verify")
    public String verify(@ModelAttribute(value="message") Message message,Model model,HttpServletRequest request){
        UserJDBC userJDBC=new UserJDBC();
        String username=request.getRemoteUser();
        String password=userJDBC.getPassword(username);
        int money=userJDBC.getMoney(username,password);
        String newPassword1=message.getNewPassword1();
        String newPassword2=message.getNewPassword2();
        if(newPassword1!=null&&!newPassword1.equals("")&&newPassword2!=null&&!newPassword2.equals("")
                &&newPassword1.equals(newPassword2)){
            if(userJDBC.getModify(username,password,newPassword1)){
                model.addAttribute("username",username);
                model.addAttribute("password",password);
                model.addAttribute("money",money);
                return "userInfo";
            }
        }else if(newPassword1==null||newPassword1.equals("")||newPassword2==null
                ||newPassword2.equals("")||!newPassword1.equals(newPassword2)){
            model.addAttribute("username1",request.getRemoteUser());
            return "modify";
        }
        return "error";
    }

    @RequestMapping(value="/transfer")
    public String verify(@ModelAttribute("user") User user, Model model,HttpServletRequest request) {
            UserJDBC userJDBC = new UserJDBC();
            String username = request.getRemoteUser();
            String password = userJDBC.getPassword(username);
            String whoever=user.getUsername();
            int transMoney=user.getMoney();
            if(whoever!=null&&!whoever.equals("") && transMoney != 0){
                if(userJDBC.transferMoney(username,password,transMoney)){
                    model.addAttribute("result","Have transfer to "+user.getUsername()+" "+user.getMoney()+" yuan!");
                }
            }
            return "transfer";
    }

}
