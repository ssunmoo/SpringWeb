package com.Ezenweb.controller;

import com.Ezenweb.domain.dto.RoomDto;
import com.Ezenweb.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private RoomService roomService;

    // 라우터에 있는 path와 매핑이 같으면 오류 발생
    @PostMapping("/setroom")
    public boolean write(RoomDto roomDto ){
        System.out.println(roomDto.toString());
        return roomService.write( roomDto );

    }

    @GetMapping("/getroomlist")
    public List<RoomDto> getroomlist(){
        return roomService.getroomlist();
    }


}
