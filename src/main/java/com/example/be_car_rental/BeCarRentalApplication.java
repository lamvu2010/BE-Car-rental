package com.example.be_car_rental;

import com.ngrok.Session;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import vn.payos.PayOS;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

@SpringBootApplication
@EnableScheduling
public class BeCarRentalApplication {

	@Value("${PAYOS_CLIENT_ID}")
	private String clientId;

	@Value("${PAYOS_API_KEY}")
	private String apiKey;

	@Value("${PAYOS_CHECKSUM_KEY}")
	private String checksumKey;

	@Bean
	public PayOS payOS() {
		return new PayOS(clientId, apiKey, checksumKey);
	}

	public static void main(String[] args) throws IOException {
		SpringApplication.run(BeCarRentalApplication.class, args);
	}
}
