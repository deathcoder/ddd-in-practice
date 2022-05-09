package com.example.dddinpractice.logic;

import java.math.BigDecimal;

import lombok.Builder;

/**
 * Value Object
 */
@Builder
public record Money(int oneCentCount, int tenCentCount, int quarterCount, int oneDollarCount, int fiveDollarCount, int twentyDollarCount) {
    public Money add(Money money) {
        return Money.builder()
                .oneCentCount(this.oneCentCount + money.oneCentCount)
                .tenCentCount(this.tenCentCount + money.tenCentCount)
                .quarterCount(this.quarterCount + money.quarterCount)
                .oneDollarCount(this.oneDollarCount + money.oneDollarCount)
                .fiveDollarCount(this.fiveDollarCount + money.fiveDollarCount)
                .twentyDollarCount(this.twentyDollarCount + money.twentyDollarCount)
                .build();
    }

    public Money subtract(Money money) {
        return Money.builder()
                .oneCentCount(subtract(this.oneCentCount, money.oneCentCount, "one cent coins"))
                .tenCentCount(subtract(this.tenCentCount, money.tenCentCount, "ten cent coins"))
                .quarterCount(subtract(this.quarterCount, money.quarterCount, "quarter coins"))
                .oneDollarCount(subtract(this.oneDollarCount, money.oneDollarCount, "one dollar bills"))
                .fiveDollarCount(subtract(this.fiveDollarCount, money.fiveDollarCount, "five dollar bills"))
                .twentyDollarCount(subtract(this.twentyDollarCount, money.twentyDollarCount, "twenty dollar bills"))
                .build();
    }

    public BigDecimal getAmount() {
        return BigDecimal
                .valueOf(oneCentCount).multiply(new BigDecimal("0.01"))
                .add(BigDecimal.valueOf(tenCentCount).multiply(new BigDecimal("0.1")))
                .add(BigDecimal.valueOf(quarterCount).multiply(new BigDecimal("0.25")))
                .add(BigDecimal.valueOf(oneDollarCount))
                .add(BigDecimal.valueOf(fiveDollarCount).multiply(new BigDecimal("5")))
                .add(BigDecimal.valueOf(twentyDollarCount).multiply(new BigDecimal("20")));
    }

    // ##########################################
    // http://www.devnips.com/2021/05/adding-custom-validation-in-lombok.html
    // Below we add the custom code to validate money counts
    // ##########################################

    /**
     * Override the builder() method to return our custom builder instead of the Lombok generated builder class.
     *
     * @return
     */
    public static MoneyBuilder builder() {
        return new CustomBuilder();
    }

    /**
     * Customized builder class, extends the Lombok generated builder class and overrides method implementations.
     */
    private static class CustomBuilder extends MoneyBuilder {

        /**
         * Adding validations as part of build() method.
         *
         * @return
         */
        public Money build() {
            if (super.oneCentCount < 0) {
                throw new IllegalArgumentException("oneCentCount cannot be negative");
            }

            if (super.tenCentCount < 0) {
                throw new IllegalArgumentException("tenCentCount cannot be negative");
            }

            if (super.quarterCount < 0) {
                throw new IllegalArgumentException("quarterCount cannot be negative");
            }

            if (super.oneDollarCount < 0) {
                throw new IllegalArgumentException("oneDollarCount cannot be negative");
            }

            if (super.fiveDollarCount < 0) {
                throw new IllegalArgumentException("fiveDollarCount cannot be negative");
            }
            if (super.twentyDollarCount < 0) {
                throw new IllegalArgumentException("twentyDollarCount cannot be negative");
            }


            return super.build();
        }
    }

    private int subtract(int amount1, int amount2, String moneyDescription) {
        int result = amount1 - amount2;

        if (result < 0) {
            throw new IllegalStateException(String.format("Tried to subtract [%d] %s from the existing money, but it only contains [%d] %s", amount2, moneyDescription, amount1, moneyDescription));
        }
        return result;
    }
}
