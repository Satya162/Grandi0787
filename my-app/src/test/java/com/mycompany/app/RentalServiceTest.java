package com.mycompany.app;
import org.junit.jupiter.api.Test;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
//import com.mycompany.app.Tool;

import static org.junit.jupiter.api.Assertions.*;

public class RentalServiceTest {
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");

    private Tool getTool(String toolCode) {
        switch (toolCode) {
            case "CHNS": return new Tool("CHNS", "Chainsaw", "Stihl", 1.49, true, false, true);
            case "LADW": return new Tool("LADW", "Ladder", "Werner", 1.99, true, true, false);
            case "JAKD": return new Tool("JAKD", "Jackhammer", "DeWalt", 2.99, true, false, false);
            case "JAKR": return new Tool("JAKR", "Jackhammer", "Ridgid", 2.99, true, false, false);
            default: throw new IllegalArgumentException("Invalid tool code.");
        }
    }

    private Date parseDate(String date) {
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format.");
        }
    }

    @Test
    public void testInvalidRentalDays() {
        RentalService rentalService = new RentalService();
        Tool tool = getTool("LADW");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            rentalService.checkout(tool, 0, 10, new Date());
        });

        assertEquals("Rental day count must be 1 or greater.", exception.getMessage());
    }

    @Test
    public void testInvalidDiscountPercent() {
        RentalService rentalService = new RentalService();
        Tool tool = getTool("LADW");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            rentalService.checkout(tool, 5, 101, new Date());
        });

        assertEquals("Discount percent must be in the range 0-100.", exception.getMessage());
    }

    @Test
    public void testRentalAgreement() {
        RentalService rentalService = new RentalService();
        Tool tool = getTool("LADW");
        Date checkoutDate = parseDate("07/02/20");
        RentalAgreement agreement = rentalService.checkout(tool, 3, 10, checkoutDate);

        assertEquals("LADW", agreement.getTool().getToolCode());
        assertEquals(3, agreement.getRentalDays());
        assertEquals(checkoutDate, agreement.getCheckoutDate());
        assertEquals(parseDate("07/05/20"), agreement.getDueDate());
        assertEquals(3, agreement.getChargeDays());
        assertEquals(5.97, agreement.getPreDiscountCharge(), 0.01);
        assertEquals(10, agreement.getDiscountPercent());
        assertEquals(0.60, agreement.getDiscountAmount(), 0.01);
        assertEquals(5.37, agreement.getFinalCharge(), 0.01);
    }
}
