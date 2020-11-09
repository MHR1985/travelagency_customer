package customer.dto;

public class BookingDTO {
    public final String name;
    public final String email;
    public final Long offerId;

    public BookingDTO(String name, String email, Long offerId) {
        this.name = name;
        this.email = email;
        this.offerId = offerId;
    }


}
