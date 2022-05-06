package com.example.dddinpractice.logic;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.security.InvalidParameterException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class MoneyTest {

    @Test
    void sumOfTwoMoneysProducesTheCorrectResult() {
        // Arrange
        Money money = Money.builder()
                .oneCentCount(1)
                .tenCentCount(2)
                .quarterCount(3)
                .oneDollarCount(4)
                .fiveDollarCount(5)
                .twentyDollarCount(6)
                .build();

        // Act
        Money actual = money.plus(money);

        // Assert
        assertThat(actual.oneCentCount()).isEqualTo(2);
        assertThat(actual.tenCentCount()).isEqualTo(4);
        assertThat(actual.quarterCount()).isEqualTo(6);
        assertThat(actual.oneDollarCount()).isEqualTo(8);
        assertThat(actual.fiveDollarCount()).isEqualTo(10);
        assertThat(actual.twentyDollarCount()).isEqualTo(12);
    }

    @Test
    public void twoMoneyInstancesEqualIfContainTheSameMoneyAmount() {
        Money money = Money.builder()
                .oneCentCount(1)
                .tenCentCount(2)
                .quarterCount(3)
                .oneDollarCount(4)
                .fiveDollarCount(5)
                .twentyDollarCount(6)
                .build();

        Money sameMoney = Money.builder()
                .oneCentCount(1)
                .tenCentCount(2)
                .quarterCount(3)
                .oneDollarCount(4)
                .fiveDollarCount(5)
                .twentyDollarCount(6)
                .build();

        assertThat(money).isEqualTo(sameMoney);
        assertThat(money).hasSameHashCodeAs(sameMoney);
    }

    @Test
    public void twoMoneyInstancesDoNotEqualIfContainDifferentMoneyAmounts() {
        Money dollar = Money.builder()
                .oneDollarCount(4)
                .build();

        Money penny = Money.builder()
                .oneCentCount(1)
                .build();

        assertThat(dollar).isNotEqualTo(penny);
        assertThat(dollar).doesNotHaveSameHashCodeAs(penny);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "-1,0,0,0,0,0",
            "0,-2,0,0,0,0",
            "0,0,-3,0,0,0",
            "0,0,0,-4,0,0",
            "0,0,0,0,-5,0",
            "0,0,0,0,0,-6",
    })
    public void cannotCreateMoneyWithNegativeValue(int oneCentCount, int tenCentCount, int quarterCount, int oneDollarCount, int fiveDollarCount, int twentyDollarCount) {
        assertThrows(IllegalArgumentException.class, () -> Money.builder()
                .oneCentCount(oneCentCount)
                .tenCentCount(tenCentCount)
                .quarterCount(quarterCount)
                .oneDollarCount(oneDollarCount)
                .fiveDollarCount(fiveDollarCount)
                .twentyDollarCount(twentyDollarCount)
                .build());
    }
}