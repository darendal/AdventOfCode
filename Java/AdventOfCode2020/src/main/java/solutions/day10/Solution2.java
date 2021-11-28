/*
 * Copyright 2020 MobileIron, Inc.
 * All rights reserved.
 */
package solutions.day10;

import java.util.HashMap;
import java.util.Map;

import solutions.AbstractSolution;

public class Solution2  extends AbstractSolution {

    public Solution2() {
        super(2, 10);
    }

    @Override
    public void run() {
        super.run();

        final int[] input = this.getInputStream().mapToInt(Integer::parseInt).sorted().toArray();
        final int min = 0;
        final int max = input[input.length -1] + 3;

        final AdapterGraph graph = new AdapterGraph(input.length + 2);

        graph.addNode(min);

        for (int i : input) {
            graph.addNode(i);
        }

        graph.addNode(max);
        this.printResult(graph.countPaths(min, max));
    }

    private static class AdapterGraph {

        private final int[][] graph;
        private final Map<Integer, Integer> exists;
        private int head;

        public AdapterGraph(final int size) {
            this.graph = new int[size][3];
            this.exists = new HashMap<>();
            this.head = 0;
        }

        public void addNode(final int value) {

            // add node to graph and exists map
            this.graph[head] = new int[]{-1, -1, -1 };
            this.exists.put(value, head++);

            // wire up all previous node to point to this one (if applicable)
            for (int i = 1; i < 4; i++) {
                int prevAdapter = value - i;
                if (this.exists.containsKey(prevAdapter)) {
                    this.graph[this.exists.get(prevAdapter)][i-1] = value;
                }
            }
        }

        public long countPaths(final int start, final int end) {
            if (!this.exists.containsKey(start) || !this.exists.containsKey(end)) {
                return -1;
            }
             return new NodeVisitor(graph, exists).countPaths(start, end);
        }

        private static class NodeVisitor {
            private final int[][] graph;
            private final Map<Integer, Integer> exists;
            private final Map<Integer, Long> visited;

            public NodeVisitor(final int[][] graph, final Map<Integer, Integer> exists) {
                this.graph = graph;
                this.exists = exists;
                this.visited = new HashMap<>();
            }

            public long countPaths(int start, int end) {

                if (start == end) {
                    this.visited.put(start, 1L);
                    return 1;
                }

                int[] nodes = this.graph[this.exists.get(start)];

                long count = 0;
                for (int i: nodes) {
                    if (i != -1) {
                        if (visited.containsKey(i)) {
                            // Already visited, fetch from cache
                            count += visited.get(i);
                        } else {
                            // first visit, calculate
                            count += this.countPaths(i, end);
                        }
                    }  // Invalid node, skip

                }
                this.visited.put(start, count);
                return count;
            }
        }

    }
}
