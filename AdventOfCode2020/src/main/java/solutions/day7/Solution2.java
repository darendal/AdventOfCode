/*
 * Copyright 2020 MobileIron, Inc.
 * All rights reserved.
 */
package solutions.day7;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.javatuples.Pair;

import solutions.AbstractSolution;

public class Solution2 extends AbstractSolution {

    private static final Pattern bagRulePattern = Pattern.compile("(\\d )?\\w+ \\w+ bag");
    private static final String myBag = "shiny gold bag";

    private final Map<String, Rule> ruleMap = new HashMap<>();

    public Solution2() {
        super(2, 7);
    }

    @Override
    public void run() {
        super.run();

        this.getInputStream().map(this::parseRule).forEach(this::addRule);

        int result = this.ruleMap.get(myBag).getCount();
        this.printResult(result-1);
    }

    private List<String> parseRule(final String rule) {

        final Matcher matcher = bagRulePattern.matcher(rule);
        final List<String> matches = new ArrayList<>();

        while (matcher.find()) {
            matches.add(matcher.group());
        }

        return matches;
    }

    private void addRule(List<String> rules) {
        final Rule newRule = this.ruleMap.getOrDefault(rules.get(0), new Rule(rules.get(0)));

        for(int i = 1; i< rules.size(); i++) {
            String ruleKey = rules.get(i);
            String amount = ruleKey.split(" ")[0];

            int amountVal;
            if (amount.equals("no")) amountVal = 0;
            else {
                amountVal = Integer.parseInt(amount);
                ruleKey = ruleKey.substring(amount.length()).trim();
            }

            final Rule subRule = this.ruleMap.getOrDefault(ruleKey, new Rule(ruleKey));

            newRule.addContainingRule(subRule, amountVal);
            subRule.addContainedByRule(newRule);

            this.ruleMap.putIfAbsent(ruleKey, subRule);
        }

        this.ruleMap.putIfAbsent(rules.get(0), newRule);
    }

    protected static class Rule implements Comparable<Rule> {

        private final String color;

        // All bags this rule can contain
        private final List<Pair<Rule, Integer>> contains;

        // All rules that (directly) allow this bag
        private final List<Rule> containedBy;

        public Rule(final String color) {
            this.color = color;

            this.contains = new ArrayList<>();
            this.containedBy = new ArrayList<>();
        }

        /**
         * Add the given rule to this rule's list of containing rules.
         * Infers that this rule contains the given rule.
         */
        public void addContainingRule(Rule containingRule, int count) {
            this.contains.add(new Pair<>(containingRule, count));
        }

        public void addContainedByRule(Rule containedByRule) {
            this.containedBy.add(containedByRule);
        }

        @Override
        public int compareTo(final Rule o) {
            return this.color.compareTo(o.color);
        }

        public int getCount() {

            int total = 1;
            for (Pair<Rule, Integer> tuple: this.contains) {
                int count = tuple.getValue1();
                Rule rule = tuple.getValue0();

                if (this.color.equals("no other bag")){
                    // the containing bag
                    total += 1;
                } else {
                    total += rule.getCount() * count;
                }
            }

            return total;
        }
    }
}
