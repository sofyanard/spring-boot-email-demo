package com.sofyanard.springbootemaildemo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/mail")
public class EmailSenderController {

    private static final Logger log = LoggerFactory.getLogger(EmailSenderController.class);
    
    @Autowired
    private EmailSenderService emailSenderService;

    @PostMapping
    public void PostMail(@RequestHeader(HttpHeaders.USER_AGENT) String userAgent, @RequestBody EmailSendDto emailSendDto) {
        emailSenderService.sendEmail(emailSendDto.getToEmail(),
                emailSendDto.getSubject(),
                userAgent);
    }

    @GetMapping
    public String GetMail(@RequestHeader(HttpHeaders.USER_AGENT) String userAgent) {
        Locale locale = new Locale("id", "ID");
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy", locale);
        String formattedDate = String.format(locale, "Hari %1$tA, tanggal %1$td %1$tB %1$tY, jam %1$tH:%1$tm", now);
        String formattedDate2 = now.format(formatter);
        
        String emailBody = getEmailBody()
            .replace("USER_AGENT", userAgent)
            .replace("BROWSER_NAME", getBrowserName(userAgent))
            .replace("DEVICE_NAME", getDeviceName(userAgent))
            .replace("FIRST_NAME", "Sofyan")
            .replace("LAST_NAME", "Ardianto")
            .replace("FORMATED_DATE", formattedDate);

        

        try {
            emailSenderService.sendEmail("sofyanard@gmail.com",
                "Informasi Login Aplikasi",
                emailBody);
        } catch (Exception e) {
            log.error("[sendEmail]", e);
        }
            
        return "email sent";
    }

    private String getBrowserName(String userAgent) {
        String result = "";

        String browserName = userAgent.toLowerCase();
        String msieRegx = ".*msie.*";
        String operaRegx = ".*opera.*";
        String firefoxRegx = ".*firefox.*";
        String chromeRegx = ".*chrome.*";
        String webkitRegx = ".*webkit.*";
        String mozillaRegx = ".*mozilla.*";
        String safariRegx = ".*safari.*";

        if (Pattern.matches(msieRegx, browserName)
                && !Pattern.matches(operaRegx, browserName)) {
            result = "IE";
        } else if (Pattern.matches(firefoxRegx, browserName)) {
            result = "Firefox";
        } else if (Pattern.matches(chromeRegx, browserName)
                && Pattern.matches(webkitRegx, browserName)
                && Pattern.matches(mozillaRegx, browserName)) {
            result = "Chrome";
        } else if (Pattern.matches(operaRegx, browserName)) {
            result = "Opera";
        } else if (Pattern.matches(safariRegx, browserName)
                && !Pattern.matches(chromeRegx, browserName)
                && Pattern.matches(webkitRegx, browserName)
                && Pattern.matches(mozillaRegx, browserName)) {
            result = "Safari";
        } else {
            result = "unknown";
        }

        return result;
    }

    private String getDeviceName(String userAgent) {
        String result = "unknown";

        // Pattern pattern = Pattern.compile("(?<=\\()[^\\s]+");
        Pattern pattern = Pattern.compile("(?<=\\().*?(?=\\))");
        Matcher matcher = pattern.matcher(userAgent);
        if (matcher.find()) {
            result = matcher.group();
        }

        return result;
    }

    private String getEmailBody() {
        return "<html>\n" +
                "<head></head>\n" +
                "<body>\n" +
                "<img src=\"https://login.atrbpn.go.id/images/atrbpn-icon.png\" />\n" +
                "<p>Hai Sdr/i. FIRST_NAME LAST_NAME <br/>\n" +
                "di Tempat,</p>\n" +
                "\n" +
                "<p>Akun Aplikasi Kementerian ATR/BPN Anda baru saja login pada FORMATED_DATE \n" +
                "pada browser BROWSER_NAME, di perangkat DEVICE_NAME, dengan IP Address IP_ADDRESS.<br/>\n" +
                "Anda mendapatkan email ini untuk memastikan ini memang Anda. \n" +
                "Jika ini bukan Anda silahkan segera mengganti password untuk keamanan akun anda dengan klik tombol dibawah \n" +
                "atau pada link https://akun.atrbpn.go.id/AkunSaya/GantiPassword </p>\n" +
                "<form action=\"https://akun.atrbpn.go.id/AkunSaya/GantiPassword\" method=\"get\" target=\"_blank\"><button type=\"submit\">Ganti Password</button></form> \n" +
                "<p>~ Don't be a WEAKEST Link in the SECURITY Chain ~</p>\n" +
                "\n" +
                "<p>Salam,</p>\n" +
                "<p>Pengelola Aplikasi<br/>\n" +
                "Kementerian Agraria dan Tata Ruang / Badan Pertanahan Nasional</p>\n" +
                "</body>\n" +
                "</html>";
    }
}
