package Expression;

import Bank.Bank;

public class Money implements Expression {

    public int amount;
    private final String currency;

    Money(int amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public String currency() {
        return currency;
    }

    public Expression times(int multiplier) {
        return new Money(amount * multiplier, currency);
    }

    public Expression plus(Expression money) {
        return new Sum(this, money);
    }

    public Money reduce(Bank bank, String to) {
        return new Money(amount / bank.rate(currency, to), to);
    }

    public boolean equals(Money money) {
        return amount == money.amount && money.currency.equals(currency);
    }

    public static Money dollar(int amount) {
        return new Money(amount, "USD");
    }

    public static Money franc(int amount) {
        return new Money(amount, "CHF");
    }
}
