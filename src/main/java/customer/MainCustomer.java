package customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;

@SpringBootApplication
public class MainCustomer {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MainCustomer.class);
        app.run();
        try {
            Receiver.receive("USA");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
