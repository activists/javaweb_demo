package com.ipinyou.demo;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;


@Controller
public class MyController {

    private User tmp_user=new User();


    @GetMapping("/")
    public String home(){
        return "home";
    }

    @RequestMapping(value = "/login")
    public String login(@ModelAttribute(value="user") User user){
        return "login";
    }

    @RequestMapping(value="/userInfo")
    public String userLogin(@ModelAttribute(value="user") User user, Model model,
                            HttpServletRequest request){
        String username=user.getUsername();
        String password=user.getPassword();
        UserJDBC userJDBC=new UserJDBC();
        tmp_user.setUsername(username);
        tmp_user.setPassword(password);
        if(request.isRequestedSessionIdValid()){
            if(userJDBC.getVerfy(username,password)){
//            response.setCharacterEncoding("UTF-8");
//            response.setContentType("text/html;charset=UTF-8");
////            System.out.println("session.isNew:"+session.isNew());
//            if(session==null||session.isNew()){
//                session = request.getSession();
//                session.setAttribute("username", username);
//                session.setAttribute("password",password);
//            }
////            System.out.println("username1:"+session.getAttribute("username"));
//            Cookie cookie = new Cookie("JSESSIONID"+username,session.getId());
//            cookie.setPath("/");
//            cookie.setMaxAge(600);
//            cookie.setSecure(true);
//            response.addCookie(cookie);
                int money=-999;
                if(username!=null){
                    money=userJDBC.getMoney(username,password);
                    model.addAttribute("username",username);
                    model.addAttribute("password",password);
                    model.addAttribute("money",money);
                    tmp_user.setMoney(money);

                }
                if(money!=-1&&money!=-999){
                    return "userInfo";
                }
            }
        }
        return "error";
    }

    @RequestMapping(value="/modify")
    public String modify(@ModelAttribute(value="message") Message message,Model model,HttpServletRequest request){
        if(request.isRequestedSessionIdFromCookie()&&request.isRequestedSessionIdValid()){
            model.addAttribute("username1",tmp_user.getUsername());
            return "modify";
        }
        return "error";
    }

    @RequestMapping(value="/verify")
    public String verify(@ModelAttribute(value="message") Message message,Model model,HttpServletRequest request){
        if(request.isRequestedSessionIdFromCookie()&&request.isRequestedSessionIdValid()){
            UserJDBC userJDBC=new UserJDBC();
            String username= tmp_user.getUsername();
            String password=tmp_user.getPassword();
            String newPassword1=message.getNewPassword1();
            String newPassword2=message.getNewPassword2();
            if(newPassword1!=null&&!newPassword1.equals("")&&newPassword2!=null&&!newPassword2.equals("")
                    &&newPassword1.equals(newPassword2)){
                if(userJDBC.getModify(username,password,newPassword1)){
                    model.addAttribute("username",username);
                    model.addAttribute("password",password);
                    model.addAttribute("money",tmp_user.getMoney());
                    tmp_user.setPassword(newPassword1);
                    return "userInfo";
                }
            }else if(newPassword1==null||newPassword1.equals("")||newPassword2==null
                    ||newPassword2.equals("")||!newPassword1.equals(newPassword2)){
                model.addAttribute("username1",tmp_user.getUsername());
                return "modify";
            }
        }
        return "error";
    }

    @RequestMapping(value="/transfer")
    public String verify(@ModelAttribute("user") User user, Model model,HttpServletRequest request) {
        if(request.isRequestedSessionIdFromCookie()&&request.isRequestedSessionIdValid()){
            UserJDBC userJDBC = new UserJDBC();
            String username = tmp_user.getUsername();
            String password = tmp_user.getPassword();
            String whoever=user.getUsername();
            int transMoney=user.getMoney();
            if(whoever!=null&&!whoever.equals("") && transMoney != 0){
                if(userJDBC.transferMoney(username,password,transMoney)){
                    model.addAttribute("result","Have transfer to "+user.getUsername()+" "+user.getMoney()+" yuan!");
                }
            }
            return "transfer";
        }
        return "error";
    }

}
