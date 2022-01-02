import java.util.Date;
import java.text.SimpleDateFormat;
import java.lang.Math;
import java.util.Calendar;
import java.time.temporal.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * The checkout class gathers all of the information needed to create a rental
 * agreement
 *
 * @author Cameron Costello
 * @version 11/24/2021
 */
public class Checkout {

    private String toolCode;
    private String rentalDayCount;
    private String discountPercent;
    private Date checkoutDate;

    /**
     * Constructor for objects of class Checkout
     */
    public Checkout(String toolCode, String rentalDayCount, String discountPercent, Date checkoutDate) {
        this.toolCode = toolCode;
        this.rentalDayCount = rentalDayCount;
        this.discountPercent = discountPercent;
        this.checkoutDate = checkoutDate;

    }

    /**
     * This method checks to make sure proper input is given by the user
     * 
     * @throws IllegalArgumentException if rental date is not specified or is less
     *                                  than one or if the discount percent is not
     *                                  between 0 and 100
     */
    public void checkoutMessage() {

        if (this.rentalDayCount.equals("")) {
            throw new IllegalArgumentException(
                    "Please specify the number of days that you would like to rent the tool for.");
        } else if (Integer.parseInt(this.rentalDayCount) < 1) {
            throw new IllegalArgumentException(
                    "Please specify the number of days that you would like to rent the tool for.");
        }

        if (this.discountPercent.equals("")) {
            throw new IllegalArgumentException(
                    "Please make sure the discount percent has a value between zero and 100.");
        } else if (Double.parseDouble(this.discountPercent) < 0 || (Double.parseDouble(this.discountPercent) > 100)) {
            throw new IllegalArgumentException(
                    "Please make sure the discount percent has a value between zero and 100.");
        }

    }

    /**
     * Returns the instance variable that contains the number of rental days.
     * 
     * @return String rental day count instance variable
     */
    public String getRentalDayCount() {
        return this.rentalDayCount;
    }

    /**
     * Returns the instance variable that contains the discount percent specified.
     * 
     * @return String discount percent instance variable
     */
    public String getDiscountPercent() {
        return this.discountPercent;
    }

    /**
     * Calculates the date that a tool will be due.
     * 
     * @param rentalDate         This is the the date that a user wants to start
     *                           renting a tool
     * @param numberOfDaysRented The number of days that a user is renting the tool
     *                           for
     * @return String The rental due date
     */
    public String calculateDueDate(Date rentalDate, String numberOfDaysRented) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(rentalDate);
        calendar.add(Calendar.DATE, Integer.parseInt(numberOfDaysRented));
        String dueDate = sdf.format(calendar.getTime());
        return dueDate;
    }

    /**
     * Creates a new date format
     * 
     * @param date The date to reformat
     * @return String formatted date
     */
    public String editDateFormat(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String formattedDate = format.format(calendar.getTime());
        return formattedDate;
    }

    /**
     * Calculates the daily charge for the tool rented.
     * 
     * @param dailyRentalCharge  Price of the tool per day
     * @param numberOfDaysRented The number of days that a user is renting the tool
     *                           for
     * @return double The daily charge of a rental based on number of days rented
     */
    public double calculateDailyCharge(double dailyRentalCharge, String numberOfDaysRented) {
        return dailyRentalCharge * Integer.parseInt(numberOfDaysRented);
    }

    /**
     * Calculates the chargeable days for a tool
     * 
     * @param checkoutDate  The checkout date of a tool
     * @param dueDate       The due date of a tool
     * @param weekdayCharge Checks for weekday charge of a tool
     * @param weekendCharge Checks for weekend charge of a tool
     * @param holidayCharge Checks for holiday charge of a tool
     * 
     * @return int The number of chargeable days for a tool
     */
    public int calculateChargeableDays(Date checkoutDate, String dueDate, boolean weekdayCharge, boolean weekendCharge,
            boolean holidayCharge) {

        ChargeableDays chargeableDays = new ChargeableDays(checkoutDate, dueDate, weekdayCharge, weekendCharge,
                holidayCharge);
        return chargeableDays.calculateChargeableDays();

    }

    /**
     * Calculates the charge of a rental before a discount is applied.
     * 
     * @param dailyCharge Daily charge for a specific tool
     * @param chargeDays  The number of chargeable days for a tool
     * 
     * @return double The total charge before a discount is applied
     */
    public double calculatePreDiscountCharge(double dailyCharge, int chargeDays) {
        return Math.round((chargeDays * dailyCharge) * 100.00) / 100.00;

    }

    /**
     * Calculates the charge of a rental before a discount is applied.
     * 
     * @param discountPercent The discount percent applied at checkout
     * @param preDiscount     Charge before discount
     * 
     * @return double The total discount amount
     */
    public double calculateDiscountAmount(String discountPercent, double preDiscount) {

        // check to see if the discount percent is 0
        if (Integer.parseInt(discountPercent) == 0) {
            return 0.0;
        }

        // apply discount percent to the prediscount amount
        double discount = Integer.parseInt(discountPercent) / 100.00;
        return Math.round((preDiscount * discount) * 100.00) / 100.00;
    }

    /**
     * Creates a rental agreement
     * 
     * @param toolCode          The code of the tool rented
     * @param toolType          The type of the tool rented
     * @param toolBrand         The brand of the tool rented
     * @param numDays           The number of days the user is renting the tool
     * @param checkoutDate      The date the user starts renting a tool
     * @param dueDate           The date the rental is due back to supplier
     * @param dailyRentalCharge The daily charge for a tool
     * @param chargeDays        The number of chargeable days for a tool
     * @param preDiscountCharge The total charge before a discount is applied
     * @param discountPercent   The discount percentage
     * @param discountAmount    The total discount amount
     * @param finalCharge       Final charge at checkout
     * 
     * 
     * @return String the html format of a rental agreement
     */
    public String generateRentalAgreement(String toolCode, String toolType, String toolBrand, String numDays,
            Date checkoutDate, String dueDate, double dailyRentalCharge, int chargeDays, double preDiscountCharge,
            String discountPercent, double discountAmount, double finalCharge) {

        String formattedCheckoutDate = this.editDateFormat(checkoutDate);

        String agreementDisplay = "<html>Tool code: " + toolCode + "<br>" + "Tool type: " + toolType + "<br>"
                + "Tool brand: " + toolBrand + "<br>" + "Number of days rented: " + numDays + "<br>" + "Checkout Date: "
                + formattedCheckoutDate + "<br>" + "Due Date: " + dueDate + "<br>" + "Daily Rental Charge: "
                + dailyRentalCharge + "<br>" + "Charge Days: " + chargeDays + "<br>" + "Pre-discount charge: "
                + preDiscountCharge + "<br>" + "Discount Percent: " + discountPercent + "<br>" + "Discount Amount: "
                + discountAmount + "<br>" + "Final Charge: " + finalCharge + "</html>";

        System.out.println("Tool code: " + toolCode + "\n" + "Tool type: " + toolType + "\n" + "Tool brand: "
                + toolBrand + "\n" + "Number of days rented: " + numDays + "\n" + "Checkout Date: "
                + formattedCheckoutDate + "\n" + "Due Date: " + dueDate + "\n" + "Daily Rental Charge: "
                + dailyRentalCharge + "\n" + "Charge Days: " + chargeDays + "\n" + "Pre-discount charge: "
                + preDiscountCharge + "\n" + "Discount Percent: " + discountPercent + "\n" + "Discount Amount: "
                + discountAmount + "\n" + "Final Charge: " + finalCharge);

        return agreementDisplay;

    }

}
