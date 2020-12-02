/*
 * Copyright 2020 MobileIron, Inc.
 * All rights reserved.
 */
package solutions.day2;

import solutions.AbstractSolution;

public class Solution2 extends AbstractSolution {

    private static final String INPUT_FILENAME = "input2";

    public Solution2() {
        super(2, 2);
    }

    @Override
    public void run() {
        super.run();

        final long validPasswords = this.getInput(INPUT_FILENAME, this.getClass())
                .stream()
                .map(input -> input.split(" "))
                .map(this::evaluatePassword)
                .filter(pass -> pass)
                .count();

        this.printResult(validPasswords);

    }

    private boolean evaluatePassword(String[] values) {
        return new PasswordRule(values).isValid(values[2]);
    }

    private static class PasswordRule {
        private final int min;
        private final int max;
        private final char character;

        public PasswordRule(String[] values) {
            String[] minMax = values[0].split("-");
            this.min = Integer.parseInt(minMax[0]);
            this.max = Integer.parseInt(minMax[1]);

            this.character = values[1].charAt(0);
        }

        public boolean isValid(String password) {
            return password.charAt(this.min - 1) == this.character ^ password.charAt(this.max - 1) == this.character;
        }
    }
}
