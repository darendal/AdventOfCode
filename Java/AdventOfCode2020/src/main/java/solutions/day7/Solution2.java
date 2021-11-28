/*
 * Copyright 2020 MobileIron, Inc.
 * All rights reserved.
 */
package solutions.day7;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.javatuples.Pair;

import solutions.AbstractSolution;

public class Solution2 extends AbstractSolution {

    private static final Pattern bagRulePattern = Pattern.compile("(\\d )?\\w+ \\w+ bag");
    private static final String myBag = "shiny gold bag";

    private final Map<String, Rule> ruleMap = new ConcurrentHashMap<>();

    public Solution2() {
        super(2, 7);
    }

    @Override
    public void run() {
        super.run();

        this.getInputStream().parallel().map(this::parseRule).forEach(this::addRule);

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
        final Rule newRule = this.ruleMap.getOrDefault(rules.get(0), new Rule());

        for(int i = 1; i < rules.size(); i++) {
            String ruleKey = rules.get(i);
            String amount = ruleKey.split(" ")[0];

            int amountVal;
            if (amount.equals("no"))
                amountVal = 0;
            else {
                amountVal = Integer.parseInt(amount);
                ruleKey = ruleKey.substring(amount.length()).trim();
            }

            final Rule subRule = this.ruleMap.getOrDefault(ruleKey, new Rule());

            newRule.addContainingRule(subRule, amountVal);

            this.ruleMap.putIfAbsent(ruleKey, subRule);
        }

        this.ruleMap.putIfAbsent(rules.get(0), newRule);
    }

    protected static class Rule {

        // All bags this rule can contain
        private final List<Pair<Rule, Integer>> contains;

        public Rule() {
            this.contains = new ArrayList<>();
        }

        /**
         * Add the given rule to this rule's list of containing rules.
         * Infers that this rule contains the given rule.
         */
        public void addContainingRule(Rule containingRule, int count) {
            this.contains.add(new Pair<>(containingRule, count));
        }

        public int getCount() {
            return 1 + this.contains.stream().mapToInt(elem -> elem.getValue0().getCount() * elem.getValue1()).sum();
        }
    }
}
