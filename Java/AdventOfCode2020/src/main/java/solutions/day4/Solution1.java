/*
 * Copyright 2020 MobileIron, Inc.
 * All rights reserved.
 */
package solutions.day4;

import java.util.List;

import solutions.AbstractSolution;

public class Solution1 extends AbstractSolution {

    public Solution1() {
        this(1);
    }

    protected Solution1(int solutionNum) {
        super(solutionNum, 4);
    }

    @Override
    public void run() {
        super.run();

        final List<String> input = this.getInput();

        Passport passport = new Passport();
        int countValid = 0;

        for(String values : input) {
            if (values.equals("")) {
                if (passport.isValid())
                    countValid++;
                passport = new Passport();
            }
            for (String param: values.split(" ")) {
                passport.setParam(param);
            }
        }

        this.printResult(countValid);

    }


    protected class Passport {
        protected boolean birthYear = false;
        protected boolean issueYear = false;
        protected boolean expireYear = false;
        protected boolean height = false;
        protected boolean eyeColor = false;
        protected boolean passportId = false;
        protected boolean countryId = false;
        protected boolean hairColor = false;

        public void setParam(String param) {
            String key = param.split(":")[0];

            switch (key) {
                case "byr":
                    this.birthYear = true;
                    break;
                case "iyr":
                    this.issueYear = true;
                    break;
                case "eyr":
                    this.expireYear = true;
                    break;
                case "hgt":
                    this.height = true;
                    break;
                case "ecl":
                    this.eyeColor = true;
                    break;
                case "pid":
                    this.passportId = true;
                    break;
                case "cid":
                    this.countryId = true;
                    break;
                case "hcl":
                    this.hairColor = true;
                    break;
            }
        }

        public boolean isValid() {
            return this.birthYear && this.issueYear && this.expireYear && this.height && this.eyeColor && passportId && hairColor;
        }

    }
}
