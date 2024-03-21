package com.gx.controller;

import com.gx.domain.Book;
import com.gx.service.BookOrderService;
import com.gx.service.BookService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/book")
public class BookController {



    @Autowired
    private BookService bookService;

    @Autowired
    private BookOrderService bookOrderService;

    //删除图书
    @RequestMapping("/deleteById.action")
    @ResponseBody
    public Map<String,String> deleyeById(@RequestParam("bid") int bid){
        //已经借出的图书无法删除
        Book book=bookService.selectBookByID(bid);
        if(book.getStatus()==0)//如果图书已经借出，无法删除
        {
            Map map= new HashMap<String,String>();
            map.put("msg","删除失败");
            return map;
        }
        bookService.deleteBook(bid);
        Map map = new HashMap<String,String>();
        map.put("msg","删除成功");
        return map;
    }


    //添加图书
    @RequestMapping("/addBook.action")
    @ResponseBody
    public int addBook(Book book){
        bookService.addBook(book);//添加图书操作
        int i = bookService.selectLastNum();//把刚刚添加的图书的主键查出来
        return i;//返回给前端的ajax，便于添加后直接显示在表上
    }

    //修改图书
    @RequestMapping("/modify.action")
    @ResponseBody
    public int modifyBook(Book book){
        System.out.println(book);
        bookService.modifyBook(book);//执行修改操作
        return book.getBid();//把修改的图书的id返回ajax，便于显示修改后的该图书的信息
    }

    //借书

    /**
     * 借书所要进行的操作：
     *  将书籍状态改为0
     *  在记录单上添加该用户的信息以及对应的书ID，并设置该订单状态为未完成（未还书，还书即算本次订单完成，未完成状态为1，完成状态为0）
     * @param bid
     */
    @RequestMapping("/updateBookStatusToZero.action")
    @ResponseBody
    public int updateBookStatusToZero(@Param("id") int id ,@Param("bid") int bid){
        bookService.updateBookStatusToZero(bid);//执行借书操作，书的状态有1到0，说明被借出
        //借书订单添加用户以及对应的书籍ID
        bookOrderService.addOrder(id,bid);//把借书记录添加到数据库中
        return bid;
    }

    //还书
    @RequestMapping("/updateBookStatusToOne.action")
    @ResponseBody
    public int updateBookStatusToOne(@Param("bid") int bid){
        //根据书籍的id更改书籍的状态为1
        bookService.updateBookStatusToOne(bid);//执行借书操作，书的状态有1到0，说明书已还
        //将订单的状态更改为0（我的借还中书籍的状态需要是1）
        bookOrderService.updateOrderStatus(bid);//更新借书记录中的该书的状态
        return bid;
    }

}
