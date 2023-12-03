package com.socialmedia.controller;

import com.socialmedia.model.Boards;
import com.socialmedia.model.UserSavePin;
import com.socialmedia.model.Users;
import com.socialmedia.service.BoardService;
import com.socialmedia.service.UserService;
import com.socialmedia.service.UserSavePinService;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/boards")
@CrossOrigin(origins = "http://localhost:3000")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserSavePinService userSavePinService;

    @GetMapping("/username/{username}")
    public List<Boards> findByUserIdOrderByPinIdAsc(@PathVariable("username") String username) {
        Users user = userService.getUserByUsername(username);
        List<Boards> list = boardService.findByUserOrderByIdAsc(user);

        return list;
    }

    @GetMapping("/id/{id}")
    public Boards getBoardById(@PathVariable("id") int id) {
        Optional<Boards> optionalBoard = boardService.findById(id);
        Boards board = new Boards();
        if (optionalBoard.isPresent()) {
            board = optionalBoard.get();
        }
        return board;
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Boards> update(@PathVariable("id") int id, @RequestBody Boards board) {
        Optional<Boards> optional = boardService.findById(id);

        if (optional.isPresent()) {
            Boards currentBoard = optional.get();
            currentBoard.setDescription(board.getDescription());
            currentBoard.setName(board.getName());
            currentBoard.setUser(board.getUser());    
            return new ResponseEntity<>(boardService.save(currentBoard), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Boards> add(@RequestBody Boards board) {
        board.setCreated_at(new Date());
        return new ResponseEntity<>(boardService.save(board), HttpStatus.OK);
    }

    @PostMapping("/delete/{id}")
    public boolean delete(@PathVariable("id") int id) {
        boolean delete = true;
        Optional<Boards> optionalBoard = boardService.findById(id);
        Boards board;
        if (optionalBoard.isPresent()) {
            board = optionalBoard.get();

            //Xóa userSavePin
            List<UserSavePin> listUserSavePin = userSavePinService.findAllByBoard(board);
            for (UserSavePin item : listUserSavePin) {
                boolean deleteUserSavePin = userSavePinService.delete(item);
                if (deleteUserSavePin == false) {
                    delete = false;
                }
            }
        }
        if (delete) {
            return boardService.delete(id);
        } else {
            return false;
        }
    }

}
    
