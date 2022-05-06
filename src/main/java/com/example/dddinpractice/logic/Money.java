package com.example.dddinpractice.logic;

import lombok.Builder;

/**
 * Value Object
 */
@Builder
public record Money(int oneCentCount, int tenCentCount, int quarterCount, int oneDollarCount, int fiveDollarCount, int twentyDollarCount) {
    public Money plus(Money money) {
        return Money.builder()
                .oneCentCount(this.oneCentCount + money.oneCentCount)
                .tenCentCount(this.tenCentCount + money.tenCentCount)
                .quarterCount(this.quarterCount + money.quarterCount)
                .oneDollarCount(this.oneDollarCount + money.oneDollarCount)
                .fiveDollarCount(this.fiveDollarCount + money.fiveDollarCount)
                .twentyDollarCount(this.twentyDollarCount + money.twentyDollarCount)
                .build();
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
}
