/*
 * Copyright 2020 MobileIron, Inc.
 * All rights reserved.
 */
package solutions.day4;

import java.util.List;
import java.util.regex.Pattern;

import utils.IntUtils;

public class Solution2 extends Solution1 {

    public Solution2() {
        super(2);
    }

    @Override
    public void run() {
        this.printSolutionHeader();

        final List<String> input = this.getInput();

        Solution2.Passport passport = new Solution2.Passport();
        int countValid = 0;

        for(String values : input) {
            if (values.equals("")) {
                if (passport.isValid())
                    countValid++;
                passport = new Solution2.Passport();
            }
            for (String param: values.split(" ")) {
                passport.setParam(param);
            }
        }

        this.printResult(countValid);

    }


    protected class Passport {

        private final Pattern hairColorPattern = Pattern.compile("#[a-f,0-9]{6}");
        private final Pattern eyeColorPattern = Pattern.compile("(amb)|(blu)|(brn)|(gry)|(grn)|(hzl)|(oth)");
        private final Pattern pidPattern = Pattern.compile("[0-9]{9}");

        protected boolean birthYear = false;
        protected boolean issueYear = false;
        protected boolean expireYear = false;
        protected boolean height = false;
        protected boolean eyeColor = false;
        protected boolean passportId = false;
        protected boolean hairColor = false;

        public void setParam(String param) {
            String[] values = param.split(":");
            String key = values[0];

            switch (key) {
                case "byr":
                    setBirthYear(values[1]);
                    break;
                case "iyr":
                    setIssueYear(values[1]);
                    break;
                case "eyr":
                    setExpireYear(values[1]);
                    break;
                case "hgt":
                    setHeight(values[1]);
                    break;
                case "ecl":
                    this.setEyeColor(values[1]);
                    break;
                case "pid":
                    this.setPassportId(values[1]);
                    break;
                case "hcl":
                   this.setHairColor(values[1]);
                    break;
            }
        }

        private void setBirthYear(String param) {
            int year = IntUtils.tryParse(param, 0);
            this.birthYear = year >= 1920 && year <= 2002;
        }

        private void setIssueYear(String param) {
            int year = IntUtils.tryParse(param, 0);
            this.issueYear = year >= 2010 && year <= 2020;
        }

        private void setExpireYear(String param) {
            int year = IntUtils.tryParse(param, 0);
            this.expireYear = year >= 2020 && year <= 20130;
        }

        private void setHeight(String param) {
            if (param.endsWith("cm")) {
                int height = IntUtils.tryParse(param.substring(0, param.length() - 2), 0);
                this.height = height >= 150 && height <= 193;
            } else if (param.endsWith("in")) {
                int height = IntUtils.tryParse(param.substring(0, param.length() - 2), 0);
                this.height = height >= 59 && height <= 76;
            } else {
                this.height = false;
            }
        }

        private void setEyeColor(String param) {
            this.eyeColor = this.eyeColorPattern.matcher(param).matches();
        }

        private void setPassportId(String param) {
            this.passportId = this.pidPattern.matcher(param).matches();
        }

        private void setHairColor(String param) {
            this.hairColor = hairColorPattern.matcher(param).matches();
        }

        public boolean isValid() {
            return this.birthYear && this.issueYear && this.expireYear && this.height && this.eyeColor && passportId && hairColor;
        }

    }
}
