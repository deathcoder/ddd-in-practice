package com.example.dddinpractice.logic;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

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
        Money actual = money.add(money);

        // Assert
        assertThat(actual.oneCentCount()).isEqualTo(2);
        assertThat(actual.tenCentCount()).isEqualTo(4);
        assertThat(actual.quarterCount()).isEqualTo(6);
        assertThat(actual.oneDollarCount()).isEqualTo(8);
        assertThat(actual.fiveDollarCount()).isEqualTo(10);
        assertThat(actual.twentyDollarCount()).isEqualTo(12);
    }

    @Test
    void subtractionOfTwoMoneysProducesTheCorrectResult() {
        // Arrange
        Money money1 = Money.builder()
                .oneCentCount(10)
                .tenCentCount(10)
                .quarterCount(10)
                .oneDollarCount(10)
                .fiveDollarCount(10)
                .twentyDollarCount(10)
                .build();

        Money money2 = Money.builder()
                .oneCentCount(1)
                .tenCentCount(2)
                .quarterCount(3)
                .oneDollarCount(4)
                .fiveDollarCount(5)
                .twentyDollarCount(6)
                .build();

        // Act
        Money actual = money1.subtract(money2);

        // Assert
        assertThat(actual.oneCentCount()).isEqualTo(9);
        assertThat(actual.tenCentCount()).isEqualTo(8);
        assertThat(actual.quarterCount()).isEqualTo(7);
        assertThat(actual.oneDollarCount()).isEqualTo(6);
        assertThat(actual.fiveDollarCount()).isEqualTo(5);
        assertThat(actual.twentyDollarCount()).isEqualTo(4);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "2|0|0|0|0|0|Tried to subtract [2] one cent coins from the existing money, but it only contains [1] one cent coins",
            "0|3|0|0|0|0|Tried to subtract [3] ten cent coins from the existing money, but it only contains [2] ten cent coins",
            "0|0|4|0|0|0|Tried to subtract [4] quarter coins from the existing money, but it only contains [3] quarter coins",
            "0|0|0|5|0|0|Tried to subtract [5] one dollar bills from the existing money, but it only contains [4] one dollar bills",
            "0|0|0|0|6|0|Tried to subtract [6] five dollar bills from the existing money, but it only contains [5] five dollar bills",
            "0|0|0|0|0|7|Tried to subtract [7] twenty dollar bills from the existing money, but it only contains [6] twenty dollar bills",
    }, delimiter = '|')
    void cannotSubtractMoreThanExists(int oneCentCount, int tenCentCount, int quarterCount, int oneDollarCount, int fiveDollarCount, int twentyDollarCount, String expectedError) {
        // Arrange
        Money existingMoney = Money.builder()
                .oneCentCount(1)
                .tenCentCount(2)
                .quarterCount(3)
                .oneDollarCount(4)
                .fiveDollarCount(5)
                .twentyDollarCount(6)
                .build();

        Money subtractionMoney = Money.builder()
                .oneCentCount(oneCentCount)
                .tenCentCount(tenCentCount)
                .quarterCount(quarterCount)
                .oneDollarCount(oneDollarCount)
                .fiveDollarCount(fiveDollarCount)
                .twentyDollarCount(twentyDollarCount)
                .build();

        IllegalStateException illegalStateException = assertThrows(IllegalStateException.class, () -> existingMoney.subtract(subtractionMoney));
        assertThat(illegalStateException.getMessage()).isEqualTo(expectedError);
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
            "-1,0,0,0,0,0,oneCentCount cannot be negative",
            "0,-2,0,0,0,0,tenCentCount cannot be negative",
            "0,0,-3,0,0,0,quarterCount cannot be negative",
            "0,0,0,-4,0,0,oneDollarCount cannot be negative",
            "0,0,0,0,-5,0,fiveDollarCount cannot be negative",
            "0,0,0,0,0,-6,twentyDollarCount cannot be negative",
    })
    public void cannotCreateMoneyWithNegativeValue(int oneCentCount, int tenCentCount, int quarterCount, int oneDollarCount, int fiveDollarCount, int twentyDollarCount, String expectedError) {
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> Money.builder()
                .oneCentCount(oneCentCount)
                .tenCentCount(tenCentCount)
                .quarterCount(quarterCount)
                .oneDollarCount(oneDollarCount)
                .fiveDollarCount(fiveDollarCount)
                .twentyDollarCount(twentyDollarCount)
                .build());

        assertThat(illegalArgumentException.getMessage()).isEqualTo(expectedError);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "0,0,0,0,0,0,0.00",
            "1,0,0,0,0,0,0.01",
            "1,2,0,0,0,0,0.21",
            "1,2,3,0,0,0,0.96",
            "1,2,3,4,0,0,4.96",
            "1,2,3,4,5,0,29.96",
            "1,2,3,4,5,6,149.96",
            "11,0,0,0,0,0,0.11",
            "110,0,0,0,100,0,501.10",
    })
    public void amountIsCalculatedCorrectly(int oneCentCount, int tenCentCount, int quarterCount, int oneDollarCount, int fiveDollarCount, int twentyDollarCount, BigDecimal expectedAmount) {
        Money money = Money.builder()
                .oneCentCount(oneCentCount)
                .tenCentCount(tenCentCount)
                .quarterCount(quarterCount)
                .oneDollarCount(oneDollarCount)
                .fiveDollarCount(fiveDollarCount)
                .twentyDollarCount(twentyDollarCount)
                .build();

        assertThat(money.getAmount()).isEqualTo(expectedAmount);
    }
}