/*
 * Copyright 2020 MobileIron, Inc.
 * All rights reserved.
 */
package solutions.day7;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import solutions.AbstractSolution;

public class Solution1 extends AbstractSolution {

    private static final Pattern bagRulePattern = Pattern.compile("\\w+ \\w+ bag");
    private static final String myBag = "shiny gold bag";


    private final Map<String, Rule> ruleMap = new HashMap<>();

    public Solution1() {
        super(1, 7);
    }

    @Override
    public void run() {
        super.run();

        this.getInputStream().map(this::parseRule).forEach(this::addRule);

        final Map<String, Boolean> visited = new HashMap<>();
        final Queue<Rule> toVisit = new PriorityQueue<>();
        final Set<String> validOuterBags = new HashSet<>();

        toVisit.add(this.ruleMap.get(myBag));

       while (!toVisit.isEmpty()) {

           final Rule current = toVisit.poll();

           if (visited.getOrDefault(current.getColor(), false)) {
               continue;
           }

           validOuterBags.add(current.getColor());
           toVisit.addAll(current.containedBy);

           visited.put(current.getColor(), true);

       }

       validOuterBags.remove(myBag);
       this.printResult(validOuterBags.size());


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
            final String ruleKey = rules.get(i);
            final Rule subRule = this.ruleMap.getOrDefault(ruleKey, new Rule(ruleKey));

            newRule.addContainingRule(subRule);
            subRule.addContainedByRule(newRule);

            this.ruleMap.putIfAbsent(ruleKey, subRule);
        }

        this.ruleMap.putIfAbsent(rules.get(0), newRule);
    }

    protected static class Rule implements Comparable<Rule> {

        private final String color;

        // All bags this rule can contain
        private final List<Rule> contains;

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
         * @param containingRule
         */
        public void addContainingRule(Rule containingRule) {
            this.contains.add(containingRule);
        }

        public void addContainedByRule(Rule containedByRule) {
            this.containedBy.add(containedByRule);
        }

        public String getColor() {
            return this.color;
        }

        @Override
        public int compareTo(final Rule o) {
            return this.color.compareTo(o.color);
        }
    }
}
