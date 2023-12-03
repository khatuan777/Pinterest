package com.socialmedia.controller;

import com.socialmedia.model.Boards;
import com.socialmedia.model.Comments;
import com.socialmedia.model.DetailNotification;
import com.socialmedia.model.Likes;
import com.socialmedia.model.Pins;
import com.socialmedia.model.ResultStatistics;
import com.socialmedia.model.Users;
import com.socialmedia.model.UserSavePin;
import com.socialmedia.service.PinService;
import com.socialmedia.service.UserService;
import com.socialmedia.service.BoardService;
import com.socialmedia.service.DetailNotificationService;
import com.socialmedia.service.LikeService;
import com.socialmedia.service.UserSavePinService;
import com.socialmedia.service.CommentService;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pins")
@CrossOrigin
public class PinController {

    @Autowired
    private PinService pinService;

    @Autowired
    private BoardService boardService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private DetailNotificationService detailNotificationService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private UserSavePinService userSavePinService;

    @GetMapping("/username/{username}")
    public List<Pins> findByUserIdOrderByPinIdAsc(@PathVariable("username") String username) {
        Users user = userService.getUserByUsername(username);
        List<Pins> list = pinService.findByUserOrderByIdAsc(user);

        return list;
    }


    @GetMapping("/id/{id}")
    public Pins getPinById(@PathVariable("id") int id) {
        Optional<Pins> optionalPin = pinService.getPinById(id);
        Pins pin = new Pins();
        if (optionalPin.isPresent()) {
            pin = optionalPin.get();
        }
        return pin;
    }

    @GetMapping("/getAll")
    public List<Pins> getAllPins() {
        return pinService.getAllPins();
    }

    @GetMapping("/getPinsByUserCreated")
    public List<Pins> getPinsByUserCreated(@RequestParam(name = "userId") int userId) {
        // TODO: process POST request
        Optional<Users> user = userService.getUserById(userId);
        if (user != null) {
            pinService.getPinsByUser(user);
        } else {
            System.out.println(userId);
            return null;
        }
        return null;
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Pins> update(@PathVariable("id") int id, @RequestBody Pins pin) {
        Optional<Pins> optionalPin = pinService.getPinById(id);

        if (optionalPin.isPresent()) {
            Pins currentPin = optionalPin.get();
            currentPin.setTitle(pin.getTitle());
            currentPin.setDescription(pin.getDescription());
            currentPin.setLink(pin.getLink());
            currentPin.setType(pin.getType());
            return new ResponseEntity<>(pinService.save(currentPin), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/delete/{id}")
    public boolean delete(@PathVariable("id") int id) {
        boolean delete = true;
        Optional<Pins> optionalPin = pinService.getPinById(id);
        Pins pin;
        if (optionalPin.isPresent()) {
            pin = optionalPin.get();
//            Xóa comments
            List<Comments> listComment = commentService.findAllByPin(pin);
            for (Comments item : listComment) {
                boolean deleteComment = commentService.delete(item);
                if (deleteComment == false) {
                    delete = false;
                }
            }
            //Xóa detail noti
            List<DetailNotification> listDetailNoti = detailNotificationService.findAllByPin(pin);
            for (DetailNotification item : listDetailNoti) {
                boolean deleteDetailNoti = detailNotificationService.delete(item.getId());
                if (deleteDetailNoti == false) {
                    delete = false;
                }
            }
            //Xóa likes
            List<Likes> listLike = likeService.findAllByPin(pin);
            for (Likes item : listLike) {
                boolean deleteLike = likeService.delete(item.getId());
                if (deleteLike == false) {
                    delete = false;
                }
            }

            //Xóa userSavePin
            List<UserSavePin> listUserSavePin = userSavePinService.findAllByPin(pin);
            for (UserSavePin item : listUserSavePin) {
                boolean deleteUserSavePin = userSavePinService.delete(item);
                if (deleteUserSavePin == false) {
                    delete = false;
                }
            }
        }
        if (delete) {
            return pinService.delete(id);
        } else {
            return false;
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Pins> save(@RequestBody Pins pin) {
        return new ResponseEntity<>(pinService.save(pin), HttpStatus.OK);
    }

    @GetMapping("/countAll")
    public long countAll() {
        return pinService.countAll();
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

        long countAll = pinService.countAll();
        long count7DayAgo = pinService.countByCreatedAtBefore(newDate);

        double ratio = (double) count7DayAgo / countAll;
        double percent = Math.round(ratio * 100.0) / 100.0;

        return percent;
    }

    @GetMapping("/countPinByCreatedAt")
    public Object countPinByCreatedAt() {
        Date currentDate = new Date();
        //Thống kê trong ngày====================================      
        long countDay = pinService.countByCreatedAt(currentDate);

        //Thống kê trong tuần====================================
        // Tạo một đối tượng Calendar và đặt ngày hiện tại
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);

        // Điều chỉnh ngày về đầu tuần (Thứ Hai)
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());

        // Lấy ngày đầu tiên trong tuần hiện tại
        Date firstDayOfWeek = calendar.getTime();
        long countWeek = pinService.countByCreatedAt(firstDayOfWeek, currentDate);

        //Thống kê trong tháng=============================================
        // Đặt ngày về ngày đầu tiên trong tháng
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        // Lấy ngày đầu tiên trong tháng hiện tại
        Date firstDayOfMonth = calendar.getTime();
        long countMonth = pinService.countByCreatedAt(firstDayOfMonth, currentDate);

        //Thống kê tất cả=============================================
        long countAll = pinService.countAll();
        
        //return kết quả
        ResultStatistics r = new ResultStatistics();
        r.countDay = countDay;
        r.countWeek = countWeek;
        r.countMonth = countMonth;
        r.countAll = countAll;
        return r;
    }

}