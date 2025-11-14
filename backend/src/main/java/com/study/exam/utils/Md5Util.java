package com.study.exam.utils;

import cn.hutool.crypto.digest.DigestUtil;
import org.springframework.stereotype.Component;

@Component
public class Md5Util {

    public static String encrypt(String text) {
        return DigestUtil.md5Hex(text);
    }

    public static String md5(String text) {
        return DigestUtil.md5Hex(text);
    }

    public static String encrypt(String text, String salt) {
        return DigestUtil.md5Hex(text + salt);
    }

    public static boolean verify(String text, String md5Hash) {
        return md5Hash.equals(encrypt(text));
    }
}
