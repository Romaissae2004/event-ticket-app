package com.example.tickets.config;

import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QrCodeConfig {
    @Bean
    public QRCodeWriter qrCodeWriter(){
        return new QRCodeWriter(); //allows this to inject it when ever we need it
    }
}
