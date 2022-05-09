package com.example.dddinpractice.logic;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class SnackMachineTest {

    @Test
    public void insertedMoneyGoesToTheMoneyInTransaction() {
        // Arrange
        SnackMachine snackMachine = new SnackMachine();

        // Act
        snackMachine.insertMoney(Money.DOLLAR);

        // Assert
        BigDecimal actualInTransactionAmount = snackMachine.getMoneyInTransaction().getAmount();
        assertThat(actualInTransactionAmount)
                .usingComparator(BigDecimal::compareTo)
                .isEqualTo(BigDecimal.ONE);

        BigDecimal actualInsideAmount = snackMachine.getMoneyInside().getAmount();
        assertThat(actualInsideAmount)
                .usingComparator(BigDecimal::compareTo)
                .isEqualTo(BigDecimal.ZERO);

    }

    @Test
    public void returnMoneyEmptiesMoneyInTransaction() {
        // Arrange
        SnackMachine snackMachine = new SnackMachine();
        snackMachine.insertMoney(Money.DOLLAR);

        // Act
        snackMachine.returnMoney();

        // Assert
        BigDecimal actualInTransactionAmount = snackMachine.getMoneyInTransaction().getAmount();
        assertThat(actualInTransactionAmount)
                .usingComparator(BigDecimal::compareTo)
                .isEqualTo(BigDecimal.ZERO);
    }

    @Test
    public void cannotInsertMoreThanOneCoinOrNoteAtATime() {
        // Arrange
        SnackMachine snackMachine = new SnackMachine();
        Money twoCent = Money.CENT.add(Money.CENT);


        // Act
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> snackMachine.insertMoney(twoCent));

        // Assert
        assertThat(illegalArgumentException.getMessage()).isEqualTo("Cannot insert more than one coin or note at a time");
    }

    @Test
    public void moneyInTransactionGoesToMoneyInsideAfterPurchase() {
        // Arrange
        SnackMachine snackMachine = new SnackMachine();
        snackMachine.insertMoney(Money.DOLLAR);
        snackMachine.insertMoney(Money.DOLLAR);

        // Act
        snackMachine.buySnack();

        // Assert
        assertThat(snackMachine.getMoneyInTransaction().getAmount())
                .usingComparator(BigDecimal::compareTo)
                .isEqualTo(BigDecimal.ZERO);

        assertThat(snackMachine.getMoneyInside().getAmount())
                .usingComparator(BigDecimal::compareTo)
                .isEqualTo(new BigDecimal(2));

    }
}