package sdgkteam10.rent_it;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;


/*
 * The following class perform unit test on an item object,
 * testing if values are being set and returned correctly.
 */

public class UnitTester extends TestCase {

    //Item object to perform tests on
    private Item testItem;

    public void setUp() throws Exception {
        super.setUp();

        testItem = new Item();
    }

    public void tearDown() throws Exception {

    }


    //tests get name
    public void testGetItemName() throws Exception {

        String setValue = "Ford Mustang 2016";
        testItem.setItemName(setValue);

        String result = testItem.getItemName();
        assertEquals(setValue, result);
    }


    //tests set name
    public void testSetItemName() throws Exception {

        String setValue = "Lawn Mower";
        testItem.setItemName(setValue);

        String result = testItem.getItemName();
        assertEquals(setValue, result);
    }


    //test get price
    @Test
    public void testGetPrice() throws Exception {

        String setValue = "5.99";
        testItem.setPrice(setValue);

        String result = testItem.getPrice();
        assertEquals(setValue, result);
    }


    //test set price
    @Test
    public void testSetPrice() throws Exception {

        String setValue = "149.99";
        testItem.setPrice(setValue);

        String result = testItem.getPrice();
        assertEquals(setValue, result);
    }


    //test get Price Rate
    @Test
    public void testGetPriceRate() throws Exception {

        String setValue = "Per Hour";
        testItem.setPriceRate(setValue);

        String result = testItem.getPriceRate();
        assertEquals(setValue, result);
    }


    //test set price rate
    @Test
    public void testSetPriceRate() throws Exception {

        String setValue = "Per Day";
        testItem.setPriceRate(setValue);

        String result = testItem.getPriceRate();
        assertEquals(setValue, result);
    }


    //test get description
    @Test
    public void testGetDescription() throws Exception {

        String setValue = "This is a test description";
        testItem.setDescription(setValue);

        String result = testItem.getDescription();
        assertEquals(setValue, result);
    }


    //test set description
    @Test
    public void testSetDescription() throws Exception {
        String setValue = "Like new mint condition vehicle";
        testItem.setDescription(setValue);

        String result = testItem.getDescription();
        assertEquals(setValue, result);
    }


    //test getting category of item
    @Test
    public void testGetCategory() throws Exception {
        String setValue = "Baby";
        testItem.setCategory(setValue);

        String result = testItem.getCategory();
        assertEquals(setValue, result);
    }


    //test setting category
    @Test
    public void testSetCategory() throws Exception {

        String setValue = "Vehicle";
        testItem.setCategory(setValue);

        String result = testItem.getCategory();
        assertEquals(setValue, result);
    }


    //test getting user id
    public void testGetItemUserID() throws Exception {

        String setValue = "68325530-90ae-47fe-bfb5-90798f438346";
        testItem.setItemUserID(setValue);

        String result = testItem.getItemUserID();
        assertEquals(setValue, result);
    }


    //test setting user id
    public void testSetItemUserID() throws Exception {

        String setValue = "b2876235-e6f1-4776-a1e3-992ebd8fe637";
        testItem.setItemUserID(setValue);

        String result = testItem.getItemUserID();
        assertEquals(setValue, result);
    }


    //test getting minimum item rent duration
    public void testGetMinRentDur() throws Exception {

        String setValue = "12";
        testItem.setMinRentDur(setValue);

        String result = testItem.getMinRentDur();
        assertEquals(setValue, result);
    }


    //test setting minimum rent duration
    public void testSetMinRentDur() throws Exception {

        String setValue = "7";
        testItem.setMinRentDur(setValue);

        String result = testItem.getMinRentDur();
        assertEquals(setValue, result);
    }


    //test getting time length
    @Test
    public void testGetMinDurationSpinner() throws Exception {

        String setValue = "Day(s)";
        testItem.setMinDurationSpinner(setValue);

        String result = testItem.getMinDurationSpinner();
        assertEquals(setValue, result);
    }


    //test setting time length
    @Test
    public void testSetMinDurationSpinner() throws Exception {

        String setValue = "Month(s)";
        testItem.setMinDurationSpinner(setValue);

        String result = testItem.getMinDurationSpinner();
        assertEquals(setValue, result);
    }


    //test get deposit of item
    @Test
    public void testGetDepositAmount() throws Exception {

        String setValue = "125.99";
        testItem.setDepositAmount(setValue);

        String result = testItem.getDepositAmount();
        assertEquals(setValue, result);
    }


    //test set deposit of item
    @Test
    public void testSetDepositAmount() throws Exception {

        String setValue = "1.99";
        testItem.setDepositAmount(setValue);

        String result = testItem.getDepositAmount();
        assertEquals(setValue, result);
    }


    //test if deposist is required
    @Test
    public void testGetDepositReqd() throws Exception {

        String setValue = "yes";
        testItem.setDepositReqd(setValue);

        String result = testItem.getDepositReqd();
        assertEquals(setValue, result);
    }


    //test getting deposit required
    @Test
    public void testSetDepositReqd() throws Exception {

        String setValue = "no";
        testItem.setDepositReqd(setValue);

        String result = testItem.getDepositReqd();
        assertEquals(setValue, result);
    }


    //test getting if item price is negotiable
    @Test
    public void testGetPriceNeg() throws Exception {

        String setValue = "yes";
        testItem.setPriceNeg(setValue);

        String result = testItem.getPriceNeg();
        assertEquals(setValue, result);
    }


    //test setting price negotiable
    @Test
    public void testSetPriceNeg() throws Exception {

        String setValue = "no";
        testItem.setPriceNeg(setValue);

        String result = testItem.getPriceNeg();
        assertEquals(setValue, result);
    }


    //test getting if item can be bought
    @Test
    public void testGetItemBuyout() throws Exception {

        String setValue = "no";
        testItem.setItemBuyout(setValue);

        String result = testItem.getItemBuyout();
        assertEquals(setValue, result);
    }


    //test setting if item can be bought
    @Test
    public void testSetItemBuyout() throws Exception {

        String setValue = "yes";
        testItem.setItemBuyout(setValue);

        String result = testItem.getItemBuyout();
        assertEquals(setValue, result);
    }


    //test getting unique item id
    @Test
    public void testGetUniqueID() throws Exception {

        int setValue = 720939831;
        testItem.setUniqueID(setValue);

        int result = testItem.getUniqueID();
        assertEquals(setValue, result);
    }


    //test set unique item id
    @Test
    public void testSetUniqueID()  {

        int setValue = 8195007;
        testItem.setUniqueID(setValue);

        int result = testItem.getUniqueID();
        assertEquals(setValue, result);
    }

    public static void main(String[] args) {

        Result result = JUnitCore.runClasses(UnitTester.class);

        for (Failure failure : result.getFailures())
        {
            System.out.println(failure.toString());
        }
        System.out.println(result.wasSuccessful());
    }
}