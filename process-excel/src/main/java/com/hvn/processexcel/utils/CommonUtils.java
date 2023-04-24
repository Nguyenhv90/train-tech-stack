package com.hvn.processexcel.utils;

import com.hvn.processexcel.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
public class CommonUtils {
    public static boolean isBlankOrEmpty(String str) {
        return str == null || str.trim() == "";
    }
    public static String createInsertSql(List<User> users) {
        log.error("rrrrrr");
        return "";
    }
}
