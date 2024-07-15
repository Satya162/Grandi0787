package com.mycompany.app;
import java.util.Date;

public class RentalService {
    public RentalAgreement checkout(Tool tool, int rentalDays, int discountPercent, Date checkoutDate) throws IllegalArgumentException {
        if (rentalDays < 1) {
            throw new IllegalArgumentException("Rental day count must be 1 or greater.");
        }
        if (discountPercent < 0 || discountPercent > 100) {
            throw new IllegalArgumentException("Discount percent must be in the range 0-100.");
        }

        int chargeDays = calculateChargeDays(tool, rentalDays, checkoutDate);
        double preDiscountCharge = chargeDays * tool.getDailyCharge();
        double discountAmount = preDiscountCharge * discountPercent / 100.0;
        double finalCharge = preDiscountCharge - discountAmount;

        return new RentalAgreement(tool, rentalDays, checkoutDate, chargeDays, preDiscountCharge, discountPercent, discountAmount, finalCharge);
    }

    private int calculateChargeDays(Tool tool, int rentalDays, Date checkoutDate) {
        // Implement charge days calculation logic here
        // For simplicity, this example assumes all days are chargeable
        // In a real-world scenario, you would consider weekends and holidays
        return rentalDays;
    }
}
