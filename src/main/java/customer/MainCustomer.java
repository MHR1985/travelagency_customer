package customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;
import java.util.Scanner;

@SpringBootApplication
public class MainCustomer {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MainCustomer.class);
        app.run();
        try {
            Receiver.receive(chooseCountry());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String chooseCountry(){
        System.out.println("Which country do you want offers from?");
        Scanner scan = new Scanner(System.in);
        return scan.next().toUpperCase();
    }
}
