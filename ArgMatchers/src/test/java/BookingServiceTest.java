import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

// initializes Mockito annotations
// so that wer don't have to call initMocks(this)
@RunWith(MockitoJUnitRunner.class)
public class BookingServiceTest {

    // Creates a partial mock where we only mock specific methods of a real object
    // while other methods will retain their original behavior
    @Spy
    BookingRepository bookingRepository;

    // Automatically injects mock objects into the object being tested
    @InjectMocks
    BookingService bookingService;

    @Test
    public void testBuyTicket() {

        // spy() method allows us to wrap a real object and monitor its interactions
        // while still calling the real underlying methods unless explicitly stubbed
        BookingService bookingServiceSpy = Mockito.spy(bookingService);
        bookingServiceSpy.buyTicket("ABC123");

        //verify that any instance of ticket can be passed into bookSeat() method
        Mockito.verify(bookingServiceSpy).bookSeat(any(Ticket.class));
    }

    @Test
    public void testBookSeat() {

       String ticketShowId ="TICKET:ID_1";
       Ticket ticket = new Ticket(ticketShowId);

       List<String> availableTickets = new ArrayList<String>();
       availableTickets.add(ticketShowId);
       when(bookingRepository.getSeats(anyString())).thenReturn(availableTickets);

       assertTrue(bookingService.bookSeat(ticket));
    }
}