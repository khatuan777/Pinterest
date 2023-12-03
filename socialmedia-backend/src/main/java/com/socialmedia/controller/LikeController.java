package com.socialmedia.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.socialmedia.model.Likes;
import com.socialmedia.model.Notifications;
import com.socialmedia.model.Pins;
import com.socialmedia.model.ResultStatistics;
import com.socialmedia.service.LikeService;
import com.socialmedia.service.NotificationService;
import com.socialmedia.service.PinService;

@RestController
@RequestMapping("/likes")
@CrossOrigin
public class LikeController {

    @Autowired
    private LikeService service;
    @Autowired
    private PinService pinService;

    @Autowired
    private NotificationService notificationService;

    @GetMapping(value = "/getByNotification/{notificationId}")
    public Likes getByNotification(@PathVariable int notificationId) {
        Notifications not = notificationService.getById(notificationId);
        return service.getByNotification(not);
    }

    @PostMapping("/add")
    public boolean saveLike(@RequestBody Likes like) {
        service.saveLike(like);
        return true;
    }

    @GetMapping("/countAll")
    public long countAll() {
        return service.countAll();
    }

    @GetMapping("/getLikeByPinId/{pinId}")
    public List<Likes> getLikeByPinId(@PathVariable("pinId") int id) {
        Optional<Pins> optionalPin = pinService.getPinById(id);
        if (optionalPin.isPresent()) {
            Pins pin = optionalPin.get();
            List<Likes> list = service.findAllByPin(pin);
            return list;
        } else {
            return null;
        }
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

        long countAll = service.countAll();
        long count7DayAgo = service.countByCreatedAtBefore(newDate);

        double ratio = (double) count7DayAgo / countAll;
        double percent = Math.round(ratio * 100.0) / 100.0;

        return percent;
    }

    @PostMapping("/delete")
    public boolean delete(@RequestBody Likes like) {
        service.delete(like);
        return true;
    }

    @GetMapping("/countLikeByCreatedAt")
    public Object countLikeByCreatedAt() {
        Date currentDate = new Date();
        // Thống kê trong ngày====================================
        long countDay = service.countByCreatedAt(currentDate);

        // Thống kê trong tuần====================================
        // Tạo một đối tượng Calendar và đặt ngày hiện tại
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);

        // Điều chỉnh ngày về đầu tuần (Thứ Hai)
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());

        // Lấy ngày đầu tiên trong tuần hiện tại
        Date firstDayOfWeek = calendar.getTime();
        long countWeek = service.countByCreatedAt(firstDayOfWeek, currentDate);

        // Thống kê trong tháng=============================================
        // Đặt ngày về ngày đầu tiên trong tháng
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        // Lấy ngày đầu tiên trong tháng hiện tại
        Date firstDayOfMonth = calendar.getTime();
        long countMonth = service.countByCreatedAt(firstDayOfMonth, currentDate);

        // Thống kê tất cả=============================================
        long countAll = service.countAll();

        // return kết quả
        ResultStatistics r = new ResultStatistics();
        r.countDay = countDay;
        r.countWeek = countWeek;
        r.countMonth = countMonth;
        r.countAll = countAll;
        return r;
    }
}