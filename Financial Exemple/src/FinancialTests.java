import Bank.Bank;
import Expression.Expression;
import Expression.Money;
import Expression.Sum;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FinancialTests {

    @Test
    public void testMultiplication() {
        Money five = Money.dollar(5);
        assertTrue(Money.dollar(10).equals((Money) five.times(2)));
        assertTrue(Money.dollar(15).equals((Money) five.times(3)));
    }

    @Test
    public void testEquality() {
        assertTrue(Money.dollar(5).equals(Money.dollar(5)));
        assertFalse(Money.dollar(5).equals(Money.dollar(6)));
        assertFalse(Money.franc(5).equals(Money.dollar(5)));
    }

    @Test
    public void testFrancMultiplication() {
        Money five = Money.franc(5);
        assertTrue(Money.franc(10).equals((Money) five.times(2)));
        assertTrue(Money.franc(15).equals((Money) five.times(3)));
    }

    @Test
    public void testCurrency() {
        assertEquals("USD", Money.dollar(10).currency());
        assertEquals("CHF", Money.franc(10).currency());
    }

    @Test
    public void testSimpleAddition() {
        Money five = Money.dollar(5);
        Sum sum = (Sum) five.plus(five);
        Bank bank = new Bank();
        Money reduced = bank.reduce(sum, "USD");
        assertTrue(Money.dollar(10).equals(reduced));
    }

    @Test
    public void testPlusReturnsSum() {
        Money five = Money.dollar(5);
        Expression result = five.plus(five);
        Sum sum = (Sum) result;
        assertEquals(five, sum.augend);
        assertEquals(five, sum.addend);
    }

    @Test
    public void testReduceSum() {
        Expression sum = new Sum(Money.dollar(3), Money.dollar(4));
        Bank bank = new Bank();
        Money result = bank.reduce(sum, "USD");
        assertTrue(Money.dollar(7).equals(result));
    }

    @Test
    public void testReduceMoney() {
        Bank bank = new Bank();
        Money result = bank.reduce(Money.dollar(5), "USD");
        assertTrue(Money.dollar(5).equals(result));
    }

    @Test
    public void testReduceMoneyDifferentCurrency() {
        Bank bank = new Bank();
        bank.addRate("CHF", "USD", 2);
        Money result = bank.reduce(Money.franc(2), "USD");
        assertTrue(Money.dollar(1).equals(result));
    }

    @Test
    public void testIdentityRate() {
        assertEquals(1, new Bank().rate("USD", "USD"));
    }

    @Test
    public void testMixedAddition() {
        Expression fiveBucks = Money.dollar(5);
        Expression tenFrancs = Money.franc(10);
        Bank bank = new Bank();
        bank.addRate("CHF", "USD", 2);
        Money result = bank.reduce(fiveBucks.plus(tenFrancs), "USD");
        assertTrue(Money.dollar(10).equals(result));
    }

    @Test
    public void testSumPlusMoney() {
        Expression fiveBucks = Money.dollar(5);
        Expression tenFrancs = Money.franc(10);
        Bank bank = new Bank();
        bank.addRate("CHF", "USD", 2);
        Expression sum = new Sum(fiveBucks, tenFrancs).plus(fiveBucks);
        Money result = bank.reduce(sum, "USD");
        assertTrue(Money.dollar(15).equals(result));
    }

    @Test
    public void testSumTimes() {
        Expression fiveBucks = Money.dollar(5);
        Expression tenFrancs = Money.franc(10);
        Bank bank = new Bank();
        bank.addRate("CHF", "USD", 2);
        Expression sum = new Sum(fiveBucks, tenFrancs).times(2);
        Money result = bank.reduce(sum, "USD");
        assertTrue(Money.dollar(20).equals(result));
    }
}