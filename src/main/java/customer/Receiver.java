package customer;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import customer.dto.BookingDTO;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class Receiver {

    private Receiver(){

    }
    public static void receive(String queue) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Channel channel;
        try (Connection connection = factory.newConnection()) {
            channel = connection.createChannel();
        }

        channel.queueDeclare(queue, true, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {

            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            try {
                handleMessage(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        channel.basicConsume(queue, true, deliverCallback, consumerTag -> { });
    }

    private static void handleMessage(String message) throws Exception {
        JsonObject jobj = new Gson().fromJson(message, JsonObject.class);
        Long id = jobj.get("id").getAsLong();
        Double price = jobj.get("price").getAsDouble();
        String destination = jobj.get("destination").getAsString();
        String date = jobj.get("date").getAsString();
        respondToOffer(destination,date,price,id);

    }

    private static void respondToOffer(String destination, String date, Double price, Long id) throws Exception {
        System.out.println("Offer recieved: Destination " + destination + ", price: " + price + ", date: " + date);
        System.out.println("Do you accept? YES or NO");
        Scanner scanner = new Scanner(System.in);
        String answer = scanner.next();
        Gson gson = new Gson();
        if(answer.equalsIgnoreCase("yes")){
            System.out.println("Enter name");
            String name = scanner.next();
            System.out.println("Enter email");
            String email = scanner.next();
            BookingDTO data = new BookingDTO(name,email,id);
            Sender.sendBookingConfirmation(gson.toJson(data));
        }
    }
}
