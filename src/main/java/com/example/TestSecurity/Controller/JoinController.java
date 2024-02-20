package com.example.TestSecurity.Controller;

import com.example.TestSecurity.dto.JoinDTO;
import com.example.TestSecurity.service.JoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class JoinController {
    //생성자 주입
    //private final JoinService joinService;

    @Autowired
    private JoinService joinService;


    @GetMapping("/join")
    public String joinP(){
        return "join";
    }

    @PostMapping("/joinProc")
    public String joinProcess(JoinDTO joinDTO){
        System.out.println("joinDTO.getUsername = " + joinDTO.getUsername());

        joinService.joinProcess(joinDTO);
        return "redirect:/login";
    }
}
