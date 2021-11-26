import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import java.util.Date;
import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Sale Application JUnit tests
 *
 * @author Cameron Costello
 * @version 11/24/2021
 */
public class SaleApplicationTest {
    
    @Test
    public void testOne() {

        // exception tester
        Date date = new GregorianCalendar(2015, Calendar.SEPTEMBER, 3).getTime();
        Checkout checkout = new Checkout("JAKR", "5", "101", date);
        
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            checkout.checkoutMessage();
        });
    
        String expectedMessage = "Please make sure the discount percent has a value between zero and 100.";
        String actualMessage = exception.getMessage();
    
        assertTrue(actualMessage.contains(expectedMessage));

    }


    @Test
    public void testTwo() {

        Date date = new GregorianCalendar(2020, Calendar.JULY, 2).getTime();
        DateFormat dateFormat =new SimpleDateFormat("MM/dd/yy"); 
        String checkoutDate = dateFormat.format(date);
        Checkout checkout = new Checkout("LADW", "3", "10", date);
        Tool tool = new Tool("LADW", new ToolType("Ladder", 1.99, true, true, false), "Werner");

        String toolCode = tool.getCode();
        String toolType = tool.getType().getTool();
        String toolBrand = tool.getBrand();
        String numDays = "3";
        String discountPercent = "10";
        
        checkout.checkoutMessage();

        // checkout methods
        String dueDate = checkout.calculateDueDate(date, numDays);
        double dailyRentalCharge = tool.getType().getDailyCharge();
        int chargeDays = checkout.calculateChargeableDays(date, dueDate, tool.getType().getWeekdayCharge(), tool.getType().getWeekendCharge(), tool.getType().getHolidayCharge());
        double preDiscountCharge = checkout.calculatePreDiscountCharge(tool.getType().getDailyCharge(), chargeDays);
        double discountApplied = checkout.calculateDiscountAmount(discountPercent, preDiscountCharge);
        double finalCharge = Math.round((preDiscountCharge - discountApplied) * 100.00) / 100.00;
        String rentalAgreement = checkout.generateRentalAgreement(toolCode, toolType, toolBrand, numDays, date, dueDate, dailyRentalCharge, chargeDays, preDiscountCharge, discountPercent, discountApplied, finalCharge);


        assertEquals("LADW", toolCode);
        assertEquals("Ladder", toolType);
        assertEquals("Werner", toolBrand);
        assertEquals("07/02/20", checkoutDate);
        assertEquals("07/05/20", dueDate);
        assertEquals(1.99, dailyRentalCharge, 0.001);
        assertEquals(3, chargeDays);
        assertEquals(5.97, preDiscountCharge, 0.001);
        assertEquals("10", discountPercent);
        assertEquals(.6, discountApplied, 0.001);
        assertEquals(5.37, finalCharge, 0.001);

    }

    @Test
    public void testThree() {

        Date date = new GregorianCalendar(2015, Calendar.JULY, 2).getTime();
        DateFormat dateFormat =new SimpleDateFormat("MM/dd/yy"); 
        String checkoutDate = dateFormat.format(date);
        Checkout checkout = new Checkout("CHNS", "5", "25", date);

        // public Checkout(String toolCode, String rentalDayCount, String discountPercent, Date checkoutDate) {
        Tool tool = new Tool("CHNS", new ToolType("Chainsaw", 1.49, true, false, true), "Stihl");

        String toolCode = tool.getCode();
        String toolType = tool.getType().getTool();
        String toolBrand = tool.getBrand();
        String numDays = "5";
        String discountPercent = "25";
        
        checkout.checkoutMessage();

        // checkout methods
        String dueDate = checkout.calculateDueDate(date, numDays);
        double dailyRentalCharge = tool.getType().getDailyCharge();
        int chargeDays = checkout.calculateChargeableDays(date, dueDate, tool.getType().getWeekdayCharge(), tool.getType().getWeekendCharge(), tool.getType().getHolidayCharge());
        double preDiscountCharge = checkout.calculatePreDiscountCharge(tool.getType().getDailyCharge(), chargeDays);
        double discountApplied = checkout.calculateDiscountAmount(discountPercent, preDiscountCharge);
        double finalCharge = Math.round((preDiscountCharge - discountApplied) * 100.00) / 100.00;
        String rentalAgreement = checkout.generateRentalAgreement(toolCode, toolType, toolBrand, numDays, date, dueDate, dailyRentalCharge, chargeDays, preDiscountCharge, discountPercent, discountApplied, finalCharge);


        assertEquals("CHNS", toolCode);
        assertEquals("Chainsaw", toolType);
        assertEquals("Stihl", toolBrand);
        assertEquals("07/02/15", checkoutDate);
        assertEquals("07/07/15", dueDate);
        assertEquals(1.49, dailyRentalCharge, 0.001);
        assertEquals(3, chargeDays);
        assertEquals(4.47, preDiscountCharge, 0.001);
        assertEquals("25", discountPercent);
        assertEquals(1.12, discountApplied, 0.001);
        assertEquals(3.35, finalCharge, 0.001);

    }



    @Test
    public void testFour() {

        Date date = new GregorianCalendar(2015, Calendar.SEPTEMBER, 3).getTime();
        DateFormat dateFormat =new SimpleDateFormat("MM/dd/yy"); 
        String checkoutDate = dateFormat.format(date);
        Checkout checkout = new Checkout("JAKD", "6", "0", date);

        // public Checkout(String toolCode, String rentalDayCount, String discountPercent, Date checkoutDate) {
        Tool tool = new Tool("JAKD", new ToolType("Jackhammer", 2.99, true, false, false), "DeWalt");

        String toolCode = tool.getCode();
        String toolType = tool.getType().getTool();
        String toolBrand = tool.getBrand();
        String numDays = "6";
        String discountPercent = "0";
        
        checkout.checkoutMessage();

        // checkout methods
        String dueDate = checkout.calculateDueDate(date, numDays);
        double dailyRentalCharge = tool.getType().getDailyCharge();
        int chargeDays = checkout.calculateChargeableDays(date, dueDate, tool.getType().getWeekdayCharge(), tool.getType().getWeekendCharge(), tool.getType().getHolidayCharge());
        double preDiscountCharge = checkout.calculatePreDiscountCharge(tool.getType().getDailyCharge(), chargeDays);
        double discountApplied = checkout.calculateDiscountAmount(discountPercent, preDiscountCharge);
        double finalCharge = Math.round((preDiscountCharge - discountApplied) * 100.00) / 100.00;
        String rentalAgreement = checkout.generateRentalAgreement(toolCode, toolType, toolBrand, numDays, date, dueDate, dailyRentalCharge, chargeDays, preDiscountCharge, discountPercent, discountApplied, finalCharge);


        assertEquals("JAKD", toolCode);
        assertEquals("Jackhammer", toolType);
        assertEquals("DeWalt", toolBrand);
        assertEquals("09/03/15", checkoutDate);
        assertEquals("09/09/15", dueDate);
        assertEquals(2.99, dailyRentalCharge, 0.001);
        assertEquals(3, chargeDays);
        assertEquals(8.97, preDiscountCharge, 0.001);
        assertEquals("0", discountPercent);
        assertEquals(0.0, discountApplied, 0.001);
        assertEquals(8.97, finalCharge, 0.001);

    }

    @Test
    public void testFive() {

        Date date = new GregorianCalendar(2015, Calendar.JULY, 2).getTime();
        DateFormat dateFormat =new SimpleDateFormat("MM/dd/yy"); 
        String checkoutDate = dateFormat.format(date);
        Checkout checkout = new Checkout("JAKR", "9", "0", date);

        // public Checkout(String toolCode, String rentalDayCount, String discountPercent, Date checkoutDate) {
        Tool tool = new Tool("JAKR", new ToolType("Jackhammer", 2.99, true, false, false), "Ridgid");

        String toolCode = tool.getCode();
        String toolType = tool.getType().getTool();
        String toolBrand = tool.getBrand();
        String numDays = "9";
        String discountPercent = "0";
        
        checkout.checkoutMessage();

        // checkout methods
        String dueDate = checkout.calculateDueDate(date, numDays);
        double dailyRentalCharge = tool.getType().getDailyCharge();
        int chargeDays = checkout.calculateChargeableDays(date, dueDate, tool.getType().getWeekdayCharge(), tool.getType().getWeekendCharge(), tool.getType().getHolidayCharge());
        double preDiscountCharge = checkout.calculatePreDiscountCharge(tool.getType().getDailyCharge(), chargeDays);
        double discountApplied = checkout.calculateDiscountAmount(discountPercent, preDiscountCharge);
        double finalCharge = Math.round((preDiscountCharge - discountApplied) * 100.00) / 100.00;
        String rentalAgreement = checkout.generateRentalAgreement(toolCode, toolType, toolBrand, numDays, date, dueDate, dailyRentalCharge, chargeDays, preDiscountCharge, discountPercent, discountApplied, finalCharge);


        assertEquals("JAKR", toolCode);
        assertEquals("Jackhammer", toolType);
        assertEquals("Ridgid", toolBrand);
        assertEquals("07/02/15", checkoutDate);
        assertEquals("07/11/15", dueDate);
        assertEquals(2.99, dailyRentalCharge, 0.001);
        assertEquals(5, chargeDays);
        assertEquals(14.95, preDiscountCharge, 0.001);
        assertEquals("0", discountPercent);
        assertEquals(0.0, discountApplied, 0.001);
        assertEquals(14.95, finalCharge, 0.001);

    }

    @Test
    public void testSix() {

        Date date = new GregorianCalendar(2020, Calendar.JULY, 2).getTime();
        DateFormat dateFormat =new SimpleDateFormat("MM/dd/yy"); 
        String checkoutDate = dateFormat.format(date);
        Checkout checkout = new Checkout("JAKR", "9", "0", date);

        // public Checkout(String toolCode, String rentalDayCount, String discountPercent, Date checkoutDate) {
        Tool tool = new Tool("JAKR", new ToolType("Jackhammer", 2.99, true, false, false), "Ridgid");

        String toolCode = tool.getCode();
        String toolType = tool.getType().getTool();
        String toolBrand = tool.getBrand();
        String numDays = "4";
        String discountPercent = "50";
        
        checkout.checkoutMessage();

        // checkout methods
        String dueDate = checkout.calculateDueDate(date, numDays);
        double dailyRentalCharge = tool.getType().getDailyCharge();
        int chargeDays = checkout.calculateChargeableDays(date, dueDate, tool.getType().getWeekdayCharge(), tool.getType().getWeekendCharge(), tool.getType().getHolidayCharge());
        double preDiscountCharge = checkout.calculatePreDiscountCharge(tool.getType().getDailyCharge(), chargeDays);
        double discountApplied = checkout.calculateDiscountAmount(discountPercent, preDiscountCharge);
        double finalCharge = Math.round((preDiscountCharge - discountApplied) * 100.00) / 100.00;
        String rentalAgreement = checkout.generateRentalAgreement(toolCode, toolType, toolBrand, numDays, date, dueDate, dailyRentalCharge, chargeDays, preDiscountCharge, discountPercent, discountApplied, finalCharge);


        assertEquals("JAKR", toolCode);
        assertEquals("Jackhammer", toolType);
        assertEquals("Ridgid", toolBrand);
        assertEquals("07/02/20", checkoutDate);
        assertEquals("07/06/20", dueDate);
        assertEquals(2.99, dailyRentalCharge, 0.001);
        assertEquals(1, chargeDays);
        assertEquals(2.99, preDiscountCharge, 0.001);
        assertEquals("50", discountPercent);
        assertEquals(1.5, discountApplied, 0.001);
        assertEquals(1.49, finalCharge, 0.001);

    }
}
