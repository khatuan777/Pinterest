package com.socialmedia.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.socialmedia.model.Comments;
import com.socialmedia.model.Notifications;
import com.socialmedia.model.ResultStatistics;
import com.socialmedia.service.CommentService;
import com.socialmedia.service.NotificationService;
import com.socialmedia.service.PinService;

@RestController
@RequestMapping("/comments")
@CrossOrigin
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private PinService pinService;

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/getAll")
    public List<Comments> getAll() {
        return commentService.getAll();
    }

    @GetMapping(value = "/getByNotification/{notificationId}")
    public Comments getByNotification(@PathVariable int notificationId) {
        Notifications not = notificationService.getById(notificationId);
        return commentService.getByNotification(not);
    }

    @GetMapping("/pin_id/{pin_id}")
    public List<Comments> getByPinId(@PathVariable("pin_id") int pin_id) {
        return commentService.findAllByPin(pinService.getPinById(pin_id).get());
    }

    @PostMapping("/add")
    public Comments add(@RequestBody Comments comments) {
        return commentService.saveComment(comments);
    }

    // @GetMapping("/delete/cmt_id/{cmt_id}")
    // public boolean delete(@PathVariable("cmt_id") int cmt_id) {
    // return commentService.delete(cmt_id);
    // }

    @PostMapping("/delete")
    public boolean deleteComment(@RequestBody Comments comment) {
        commentService.delete(comment);
        return true;
    }

    @GetMapping("/countAll")
    public long countAll() {
        return commentService.countAll();
    }

    @GetMapping("/percent7days")
    public double percent7days() {
        Date currentDate = new Date();
        // Tạo một đối tượng Calendar và thiết lập nó với ngày hiện tại
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        // Trừ đi 7 ngày
        calendar.add(Calendar.DAY_OF_MONTH, -7);

        // Lấy ngày mới sau khi đã trừ đi 7 ngày
        Date newDate = calendar.getTime();

        long countAll = commentService.countAll();
        long count7DayAgo = commentService.countByCreatedAtBefore(newDate);

        double ratio = (double) count7DayAgo / countAll;
        double percent = Math.round(ratio * 100.0) / 100.0;

        return percent;
    }

    @GetMapping("/countCommentByCreatedAt")
    public Object countCommentByCreatedAt() {
        Date currentDate = new Date();
        // Thống kê trong ngày====================================
        long countDay = commentService.countByCreatedAt(currentDate);

        // Thống kê trong tuần====================================
        // Tạo một đối tượng Calendar và đặt ngày hiện tại
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);

        // Điều chỉnh ngày về đầu tuần (Thứ Hai)
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());

        // Lấy ngày đầu tiên trong tuần hiện tại
        Date firstDayOfWeek = calendar.getTime();
        long countWeek = commentService.countByCreatedAt(firstDayOfWeek, currentDate);

        // Thống kê trong tháng=============================================
        // Đặt ngày về ngày đầu tiên trong tháng
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        // Lấy ngày đầu tiên trong tháng hiện tại
        Date firstDayOfMonth = calendar.getTime();
        long countMonth = commentService.countByCreatedAt(firstDayOfMonth, currentDate);

        // Thống kê tất cả=============================================
        long countAll = commentService.countAll();

        // return kết quả
        ResultStatistics r = new ResultStatistics();
        r.countDay = countDay;
        r.countWeek = countWeek;
        r.countMonth = countMonth;
        r.countAll = countAll;
        return r;
    }

}