package com.example.dddinpractice.logic;

import lombok.Data;
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
    private Money moneyInside;
    private Money moneyInTransaction;

    public void insertMoney(Money money) {
        this.moneyInTransaction = this.moneyInTransaction.plus(money);
    }

    public void returnMoney() {
//        this.oneCentCountInTransaction = 0;
//        this.tenCentCountInTransaction = 0;
//        this.quarterCountInTransaction = 0;
//        this.oneDollarCountInTransaction = 0;
//        this.fiveDollarCountInTransaction = 0;
//        this.twentyDollarCountInTransaction = 0;
    }

    public void buySnack() {
        this.moneyInside = this.moneyInside.plus(this.moneyInTransaction);

//        this.oneCentCountInTransaction = 0;
//        this.tenCentCountInTransaction = 0;
//        this.quarterCountInTransaction = 0;
//        this.oneDollarCountInTransaction = 0;
//        this.fiveDollarCountInTransaction = 0;
//        this.twentyDollarCountInTransaction = 0;
    }
}
