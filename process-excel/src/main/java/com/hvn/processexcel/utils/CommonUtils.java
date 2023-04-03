package com.hvn.processexcel.utils;

import com.hvn.processexcel.model.User;
import org.springframework.util.StringUtils;

import java.util.List;

public class CommonUtils {
    public static boolean isBlankOrEmpty(String str) {
        return str == null || str.trim() == "";
    }
//    public static String createInsertSql(List<User> users) {
//
//    }
}
