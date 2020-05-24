package vn.edu.vinaenter.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.Normalizer;
import java.util.regex.Pattern;

public class StringUtil {

    public static String md5(String str) {
        MessageDigest md;
        String result = "";
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            BigInteger bi = new BigInteger(1, md.digest());

            result = bi.toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String makeSlug(String title) {
        String slug = Normalizer.normalize(title, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        slug = pattern.matcher(slug).replaceAll("");
        slug = slug.toLowerCase();
        // Thay đ thành d
        slug = slug.replaceAll("Ä‘", "d");
        // Xoá các kí tự đặc biệt
        slug = slug.replaceAll("([^0-9a-z-\\s])", "");
        // Thay space thành dấu gạch ngang
        slug = slug.replaceAll("[\\s]", "-");
        // Ä�á»•i nhiá»�u kÃ½ tá»± gáº¡ch ngang liÃªn tiáº¿p thÃ nh 1 kÃ½ tá»± gáº¡ch ngang
        slug = slug.replaceAll("(-+)", "-");
        // XÃ³a cÃ¡c kÃ½ tá»± gáº¡ch ngang á»Ÿ Ä‘áº§u vÃ  cuá»‘i
        slug = slug.replaceAll("^-+", "");
        slug = slug.replaceAll("-+$", "");
        return slug;
    }

}
