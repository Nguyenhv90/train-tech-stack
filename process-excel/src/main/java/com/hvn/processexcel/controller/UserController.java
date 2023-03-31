package com.hvn.processexcel.controller;

import com.hvn.processexcel.model.User;
import com.hvn.processexcel.service.ExportExcel;
import com.hvn.processexcel.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping("/users/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<User> listUsers = service.listAll();

        ExportExcel excelExporter = new ExportExcel(listUsers);

        excelExporter.export(response);
    }

    @GetMapping("/users/data")
    public void seedData(){
        service.addData();
    }
}
