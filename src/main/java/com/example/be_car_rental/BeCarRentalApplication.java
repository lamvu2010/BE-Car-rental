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
//		final var sessionBuilder = Session.withAuthtokenFromEnv().metadata("my session");
//		// UTF-8 for encoding
//		final Charset utf8 = Charset.forName("UTF-8");
//		// Session.Builder let you customize different aspects of the session, see docs for details.
//		// After customizing the builder, you connect:
//		try (final var session = sessionBuilder.connect()) {
//			// Creates and configures http listener that will be using oauth to secure it
//			final var listenerBuilder = session.httpEndpoint().metadata("my listener");
//
//			// Now start listening with the above configuration
//			try (final var listener = listenerBuilder.listen()) {
//				System.out.println("ngrok url: " + listener.getUrl());
//				final var buf = ByteBuffer.allocateDirect(1024);
//
//				while (true) {
//					// Accept a new connection
//					final var conn = listener.accept();
//
//					// Read from the connection
//					buf.clear();
//					conn.read(buf);
//
//					System.out.println(utf8.decode(buf));
//
//					// Or write to it
//					buf.clear();
//					buf.put("HTTP/1.0 200 OK\n\nHello from ngrok!".getBytes(utf8));
//					buf.flip();
//					conn.write(buf);
//					conn.close();
//				}
//			}
//		}
	}
}
