/*
 * Copyright 2020 MobileIron, Inc.
 * All rights reserved.
 */
package solutions.day4;

import java.util.List;
import java.util.regex.Pattern;

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

        private final Pattern birthYearPattern = Pattern.compile("(19[2-9]\\d)|(200[0-2])");
        private final Pattern issueYearPattern = Pattern.compile("(201\\d)|(2020)");
        private final Pattern expireYearPattern = Pattern.compile("(202\\d)|(2030)");
        private final Pattern heightPattern = Pattern.compile("(6\\din)|(59in)|(7[0-6]in)|(1[5-8]\\dcm)|(19[0-3]cm)");
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
                    this.birthYear = birthYearPattern.matcher(values[1]).matches();
                    break;
                case "iyr":
                    this.issueYear = issueYearPattern.matcher(values[1]).matches();
                    break;
                case "eyr":
                    this.expireYear = expireYearPattern.matcher(values[1]).matches();
                    break;
                case "hgt":
                    this.height = heightPattern.matcher(values[1]).matches();
                    break;
                case "ecl":
                    this.eyeColor = this.eyeColorPattern.matcher(values[1]).matches();
                    break;
                case "pid":
                    this.passportId = this.pidPattern.matcher(values[1]).matches();
                    break;
                case "hcl":
                    this.hairColor = hairColorPattern.matcher(values[1]).matches();
                    break;
            }
        }

        public boolean isValid() {
            return this.birthYear && this.issueYear && this.expireYear && this.height && this.eyeColor && passportId && hairColor;
        }

    }
}
