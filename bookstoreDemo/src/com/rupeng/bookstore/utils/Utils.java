package com.rupeng.bookstore.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import javax.imageio.ImageIO;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Utils {

    // 用来生成订单序列号，使用此类的目的是避免在多线程环境下使用synchronized
    private static AtomicInteger ordersNumberCreater = new AtomicInteger();

    // 订单编号格式：时间戳（13位）+序列号（3位）
    // 可满足每毫秒1000个订单的峰值
    // 使用前最好校对好系统时间，如果系统时间被改到过去，会导致部分时间戳被重新使用了一遍，增加了生成重复订单编号的概率
    public static String getNewOrdersId() {
        int number = ordersNumberCreater.incrementAndGet();

        // 如果AtomicInteger增大到Integer.MAX_VALUE之后继续增大，返回的就是负数
        if (number < 0) {
            // 把负数变为整数，+1是因为int最小为-2147483648，最大为2147483647
            number = number + Integer.MAX_VALUE + 1;
        }
        // 把number转化为字符串后，为了可以截取number的后三位，且依次从000，001，002 ...
        // 999，要求number需要大于等于1000
        if (number < 1000) {
            number = number + 1000;
        }
        String numberStr = String.valueOf(number);

        return System.currentTimeMillis() + numberStr.substring(numberStr.length() - 3);
    }

    // 对字符串进行md5加密，注意，如果参数是null会抛出空指针异常
    public static String md5(String plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
            int i = 0;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            return buf.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("服务器错误 :" + e);
        }
    }

    // 从cookie数组中查找指定cookie并获取其值
    public static String getCookieValue(Cookie[] cookies, String cookieName) {
        if (cookies == null) {
            return null;
        }
        for (int i = 0; i < cookies.length; i++) {
            if (cookies[i].getName().equals(cookieName)) {
                return cookies[i].getValue();
            }
        }
        return null;
    }

    // =========================有效性检查--开始==========================//
    // 如果字符串为null或者""空字符串，则返回true
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    // 如果collection集合为null或者元素个数为0，则返回true
    public static boolean isEmpty(Collection coll) {
        return coll == null || coll.size() == 0;
    }

    // 如果map集合为null或者元素个数为0，则返回true
    public static boolean isEmpty(Map map) {
        return map == null || map.size() == 0;
    }

    // 如果为空或者不符合邮箱格式，则返回true
    public static boolean isEmptyOrNotEmail(String str) {
        if (isEmpty(str)) {
            return true;
        }
        return !str.matches("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
    }

    // 如果为空或者不符合手机格式，则返回true
    public static boolean isEmptyOrNotPhone(String str) {
        if (isEmpty(str)) {
            return true;
        }
        return !str.matches("^1[34578]\\d{9}$");
    }

    // 如果字符串的最小长度小于指定长度，则返回true
    public static boolean isEmptyOrNotLengthEnough(String str, int minLength) {
        if (str == null) {
            return true;
        }
        return str.length() < minLength;
    }
    // =========================有效性检查--结束==========================//

    // =========================图片验证码相关--开始==========================//
    private static int codeLength = 4;// 验证码字符个数
    private static int codeFontSize = 20;// 验证码字体大小
    private static String codeFontFamily = "Arial";// 验证码字体
    private static int imageWidth = codeLength * codeFontSize;// 验证码图片宽度
    private static int imageHeight = (int) (codeFontSize * 1.5);// 验证码图片高度

    private static Color backgroundColor = new Color(240, 248, 255);// 背景色

    private static char[] codeCharRepertory = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890"
            .toCharArray();// 普通验证码字符选取仓库

    private static String sessionImageCodeAttrKey = "_sessionImageCode";// 调用session.setAttribute(key,value)时指定的key

    // 生成图片验证码，并发送到浏览器，并把对应的字符串验证码设置到session
    public static String sendImageCode(HttpSession session, HttpServletResponse response) {

        Random random = new Random();

        char[] codeChars = new char[codeLength];

        for (int i = 0; i < codeLength; i++) {
            codeChars[i] = codeCharRepertory[random.nextInt(codeCharRepertory.length)];
        }

        // 绘制验证码图片
        BufferedImage buffImg = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) buffImg.getGraphics();
        // 设置背景色
        g.setColor(backgroundColor);
        g.fillRect(0, 0, imageWidth, imageHeight);

        // 为了在白色背景上显示，尽量生成深色验证码字符
        int red = random.nextInt(256);
        int green = random.nextInt(256);
        int blue = (red + green > 400) ? 0 : (400 - red - green);
        blue = (blue > 255) ? 255 : blue;
        Color codeColor = new Color(red, green, blue);// 验证码颜色
        g.setColor(codeColor);

        // 绘制背景噪点
        for (int i = 0; i < imageWidth; i++) {
            buffImg.setRGB(random.nextInt(imageWidth), random.nextInt(imageHeight), codeColor.getRGB());
        }

        // 设置干扰线
        for (int i = 0; i < 4; i++) {
            // 让第一个点落在上下两条线上
            int x1 = random.nextInt(imageWidth);
            int y1 = random.nextInt(2) == 0 ? 0 : imageHeight;

            // 让第二个点落在左右两个线上
            int x2 = random.nextInt(2) == 0 ? 0 : imageWidth;
            int y2 = random.nextInt(imageHeight);
            g.drawLine(x1, y1, x2, y2);
        }

        // 绘制字符验证码
        for (int i = 0; i < codeLength; i++) {
            Font font = new Font(codeFontFamily, Font.PLAIN, codeFontSize - random.nextInt(3));//
            g.setFont(font);
            g.drawString(codeChars[i] + "", codeFontSize * i + 3, (int) (imageHeight * 0.75));
        }

        // 把验证码图片输出到客户端，并把code设置到session中
        String code = new String(codeChars);
        session.setAttribute(sessionImageCodeAttrKey, code);
        response.setContentType("image/jpeg");
        try {
            ImageIO.write(buffImg, "jpg", response.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        g.dispose();// 释放资源
        return code;

    }

    // 如果request中的验证码不匹配session中的图片验证码(不区分大小写)，则返回true，并立即销毁session中的验证码
    public static boolean isNotMatchImageCode(String requestCode, HttpSession session) {
        if (requestCode == null || requestCode.length() == 0) {
            return false;
        }
        String sessionCode = (String) session.getAttribute(sessionImageCodeAttrKey);
        session.removeAttribute(sessionImageCodeAttrKey);
        return !requestCode.equalsIgnoreCase(sessionCode);
    }
    // =========================图片验证码相关--结束==========================//

    // =========================邮件验证码相关--开始==========================//
    // 生成并发送邮件验证码，并且把对应的字符串验证码设置到session中
    // 需要和checkEmailCode()方法配合使用
    private static Properties mailProps = new Properties();

    static {
        mailProps.setProperty("mail.smtp.host", "smtp.sina.com");// smtp服务器地址
        mailProps.setProperty("mail.smtp.auth", "true");// 指定连接时需要验证用户信息
    }

    private static Session mailSession = Session.getDefaultInstance(mailProps, new Authenticator() {
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            // 指定邮箱用户名和密码，注意用户名不带@xx.xx
            return new PasswordAuthentication("rupengfrom", "rupengfrom");
        }
    });

    private static String sessionMailCodeAttrKey = "_sessionMailCode";// 发送邮件验证码时，使用此key把验证码放入session
    private static String sessionSendTimeAttrKey = "_sessionSendTime";// 发送邮件验证码时，使用此key把发送时间放入session

    // 发送邮件验证码到指定邮箱，并把对应的验证码存入httpsession中一份，并且30分钟内有效
    public static boolean sendMailCode4PasswordRetrieve(HttpSession httpSession, String email) {

        String mailCode = new Random().nextInt(90000) + 10000 + ""; // 得到一个随机的5位数字的字符串
        String info = "您好， 您的验证码是：" + mailCode + "（该验证码在30分钟内有效）";// 邮件内容

        Message message = new MimeMessage(mailSession);
        try {
            message.setSubject("图书商城--找回密码--邮箱验证码");
            message.setFrom(new InternetAddress("rupengfrom@sina.com"));
            message.setRecipient(RecipientType.TO, new InternetAddress(email));

            message.setText(info);

            Transport.send(message);

            httpSession.setAttribute(sessionMailCodeAttrKey, mailCode);
            httpSession.setAttribute(sessionSendTimeAttrKey, System.currentTimeMillis());

            return true;// 发送成功，返回true
        } catch (MessagingException e) {
            e.printStackTrace();// 方便调试打印错误信息，生产环境应该记录日志
            return false;
        }

    }

    // 如果request中的邮箱验证码不匹配session中的邮箱验证码(不区分大小写)，则返回true，并立即销毁session中的验证码
    public static boolean isNotMatchMailCode(String requestCode, HttpSession session) {
        if (requestCode == null || requestCode.length() == 0) {
            return true;
        }
        Long sendTime = (Long) session.getAttribute(sessionSendTimeAttrKey);

        if (sendTime == null) {
            return true;
        }
        // 先检查时间是否超过了30分钟
        if (System.currentTimeMillis() - sendTime > 1000 * 60 * 30) {
            return true;
        }

        String sessionCode = (String) session.getAttribute(sessionMailCodeAttrKey);
        session.removeAttribute(sessionMailCodeAttrKey);

        return !requestCode.equalsIgnoreCase(sessionCode);
    }
    // =========================邮件验证码相关--结束==========================//

    // =========================ajax相关--开始==========================//

    // 生成json格式的数据并发送到浏览器
    private static void _sendAjaxResponse(ServletResponse response, Object ajaxResult) {

        response.setContentType("application/json;charset=UTF-8");

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        String jsonResult = gson.toJson(ajaxResult);

        try {
            response.getWriter().print(jsonResult);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 发送一般格式的json数据
    public static void sendAjaxResponse(ServletResponse response, AjaxResult ajaxResult) {
        _sendAjaxResponse(response, ajaxResult);
    }

    // 发送easyui分页格式的json数据
    public static void sendAjaxResponse(ServletResponse response, PageAjaxResult ajaxResult) {
        _sendAjaxResponse(response, ajaxResult);
    }

    // 发送数组格式的json响应
    public static void sendAjaxResponse(ServletResponse response, Collection conn) {
        _sendAjaxResponse(response, conn);
    }
    // =========================ajax相关--结束==========================//

    // ==========================日期类型转换相关--开始===================//
    // 按照指定格式把字符串转换为日期对象
    public static Date convertDate(String dateStr, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        try {
            return dateFormat.parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    // 把日期对象转换为指定格式的字符串
    public static String convertDate(Date date, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }
    // ==========================日期类型转换相关--开始===================//

    // 处理文件上传，把文件保存到rootPath目录下的当前日期的子目录下，并且文件名是随机的UUID
    public static String fileupload(Part part, String rootPath) {

        // 使用上传时的日期生成中间目录
        String path2 = Utils.convertDate(new Date(), "yyyy-MM-dd");
        // 生成随机文件名，后缀是上传的文件的后缀
        String filename = UUID.randomUUID().toString()
                + part.getSubmittedFileName().substring(part.getSubmittedFileName().lastIndexOf('.'));
        File parentDir = new File(rootPath, path2);
        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }
        OutputStream outputStream = null;
        InputStream inputStream = null;
        try {
            outputStream = new FileOutputStream(new File(parentDir, filename));
            inputStream = part.getInputStream();

            // 数据copy
            byte[] buff = new byte[1024 * 128];
            int len = 0;
            while ((len = inputStream.read(buff)) != -1) {
                outputStream.write(buff, 0, len);
            }
            part.delete();
            // 返回保存的路径，注意，不带根目录
            return path2 + "/" + filename;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                }
            }
            // inputStream是part对象提供的，不是自己创建的，可不用自己手动关闭
        }
    }

}