package com.example.backendspringboot.controller;

import com.example.backendspringboot.domain.Employee;
import com.example.backendspringboot.repository.EmployeeRepository;

import com.example.backendspringboot.service.JwtService;
import com.example.backendspringboot.service.JwtServiceImpl;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    JwtService jwtService;

    public AccountController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @PostMapping("/account/login")
    public ResponseEntity login(
            @RequestBody Map<String, String> params,
            HttpServletResponse res
    ){
        Employee employee = employeeRepository.findByUserIdAndPassword(params.get("userId"), params.get("password"));

        if(employee != null){

            Long id = employee.getId();
            String token = jwtService.getToken("id", id);

            Cookie cookie = new Cookie("token", token);
            cookie.setHttpOnly(true);
            cookie.setPath("/");

            res.addCookie(cookie);

            return new ResponseEntity<>(id, HttpStatus.OK);

        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND);

    }

    @GetMapping("/account/check")
    public ResponseEntity check(@CookieValue(value="token", required = false) String token){
        Claims claims = jwtService.getClamis(token);

        if(claims != null){
            int id = Integer.parseInt(claims.get("id").toString());
            return new ResponseEntity<>(id, HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.OK);


    }

}
