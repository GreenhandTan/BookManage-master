package com.gx.controller;

import cn.hutool.crypto.SecureUtil;
import com.gx.domain.Book;
import com.gx.domain.User;
import com.gx.service.BookOrderService;
import com.gx.service.BookService;
import com.gx.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/login")
public class UserController {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookOrderService bookOrderService;

    @Autowired
    private UserService userService;
    //登录校验
    @RequestMapping("/checkLogin.action")
    public String checkLogin(@RequestParam("username")String username, @RequestParam("password")String password, Model model){
        Map map = userService.checkLogin(username,password);//校验，返回不同的返回值和角色存入map集合
        if(map.get("result").equals("login")){
            model.addAttribute("msg","账号或密码错误");
        }else if(map.get("result").equals("admin")) {
            model.addAttribute("user",map.get("user"));//如果是管理员返回管理员管理初始界面，并把管理员存入Attribute
        }else{//user
            List<Book> list = bookService.selectBookByStatus();//如果是普通用户，返回书籍展示页面并把普通用户存入Attribute
            model.addAttribute("list",list);
            model.addAttribute("user",map.get("user"));
        }
        return (String)map.get("result");//返回角色信息或者错误信息，经过前端ajax判断，如果不是错误信息，跳转到user或者admin的action里
    }

   //用户注册功能实现
    @RequestMapping(value = "/addUser.action")
    @ResponseBody()
    public Map<String, Boolean> addUser(User user, HttpServletRequest request, HttpServletResponse res) throws UnsupportedEncodingException {
        Map<String , Boolean> map = new HashMap<>();
        if(!userService.isInserUser(user)){//查询是否存在重名，如果出现重名，则不可插入
            map.put("isUse",false);
            return map;
        }
        //如果不重名
        //对用户的密码进行md5加密
        String pwd = SecureUtil.md5(user.getPassword() + user.getUsername());
        user.setPassword(pwd);//把加密的密码设置给user
        userService.addUser(user);//执行添加操作
        map.put("msg",true);
        return map;
    }
    //用户注册
    //跳转到register.jsp
    @RequestMapping("/register.action")
    public String register(){
        return "register";
    }

    /*//前端跳转到的页面，暂未启用
    @RequestMapping("/admin.action")
    public String admin(int id){//传递的参数仅为跳转那个页面
        /*String result;
        if(id == 1){
            result = "bookManage";
        }else if(id == 2){
            result = "";
        }

        return result;
        return "";
    }*/

    //获取所有的普通User
    //跳转到getCommonUser.jsp
    @RequestMapping("/getCommonUser.action")
    public String getCommonUser(Model model){
        model.addAttribute("list",userService.getCommonUser());//获取所有普通用户信息
        return "commonUserManage";
    }

    //删除某一个用户
    //跳转到deleteUser.jsp
    @RequestMapping("/deleteUser.action")
    @ResponseBody
    public Map<String ,String> deleteUser(@Param("id") int id){
        userService.deleteUser(id);
        Map<String,String> map = new HashMap<>();
        map.put("msg","删除成功！");
        return map;
    }

    //修改一个用户
    //跳转到modifyUser.jsp
    @RequestMapping("/modifyUser.action")
    @ResponseBody
    public int modifyUser(User user){
        System.out.println(user);
        userService.modifyUser(user);//执行用户修改操作
        return user.getId();//返回被修改的用户的id给ajax
    }
    //跳转到login.jsp
    @RequestMapping("/login.action")
    public String login(){
        return "login";
    }

    //我的借还跳转
    //跳转到myBorrow.jsp
    @RequestMapping("/myBorrow.action")
    public String myBorrow(Model model,int id){//一个参数为user 的id
        List<Book> list = bookOrderService.myBorrow(id);
        model.addAttribute("list",list);//传递的是我的借还的这个书籍id
        model.addAttribute("user",userService.getUserById(id).get(0));
        return "myBorrow";//返回到我的借书jsp页面
    }

    //我的借还跳转到User.jsp
    @RequestMapping("/user.action")
    public String jumpUser(Model model,@Param("id") int id){
        //该方法需要传递一个书籍状态为1的书籍列表，key为list
        //还有一个是user的实例
        //user实例的获取：
        model.addAttribute("user",userService.getUserById(id).get(0));
        List<Book> list = bookService.selectBookByStatus();//查出所有可借书籍
        model.addAttribute("list",list);
        return "user";//返回user.jsp
    }
}
