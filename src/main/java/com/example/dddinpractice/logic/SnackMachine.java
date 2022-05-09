package com.example.dddinpractice.logic;

import static java.util.Arrays.asList;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * Entity
 * */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class SnackMachine extends Entity {
    private Money moneyInside = Money.NONE;
    private Money moneyInTransaction = Money.NONE;

    public void insertMoney(Money money) {
        List<Money> coinsAndNotes = asList(Money.CENT, Money.TEN_CENT, Money.QUARTER, Money.DOLLAR, Money.FIVE_DOLLAR, Money.TWENTY_DOLLAR);
        if (!coinsAndNotes.contains(money)) {
            throw new IllegalArgumentException("Cannot insert more than one coin or note at a time");
        }
        this.moneyInTransaction = this.moneyInTransaction.add(money);
    }

    public void returnMoney() {
        moneyInTransaction = Money.NONE;
    }

    public void buySnack() {
        this.moneyInside = this.moneyInside.add(this.moneyInTransaction);
        this.moneyInTransaction = Money.NONE;
    }
}
