package Expression;

import Bank.Bank;

public class Sum implements Expression {

    public Expression augend;
    public Expression addend;

    public Sum(Expression augend, Expression addend) {
        this.augend = augend;
        this.addend = addend;
    }

    public Expression times(int multiplier) {
        return new Sum(augend.times(multiplier), addend.times(multiplier));
    }

    public Expression plus(Expression addend) {
        return new Sum(this, addend);
    }

    public Money reduce(Bank bank, String to) {
        return new Money(augend.reduce(bank, to).amount + addend.reduce(bank, to).amount, to);
    }
}
