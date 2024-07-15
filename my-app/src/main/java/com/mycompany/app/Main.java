package com.mycompany.app;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        try {
            RentalService rentalService = new RentalService();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");

            Tool tool = new Tool("LADW", "Ladder", "Werner", 1.99, true, true, false);
            Date checkoutDate = dateFormat.parse("07/02/20");

            RentalAgreement agreement = rentalService.checkout(tool, 3, 10, checkoutDate);
            agreement.printAgreement();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }
}
