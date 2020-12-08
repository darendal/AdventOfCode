/*
 * Copyright 2020 MobileIron, Inc.
 * All rights reserved.
 */
package solutions.day8;

import java.util.List;

public class Solution2 extends Solution1 {

    public Solution2() {
        super(2);
    }

    @Override
    public void run() {
        this.printSolutionHeader();

        final List<String> program = this.getInput();

        for (int i = 0; i < program.size(); i++) {
            final String original = program.get(i);
            String[] instruction = original.split(" ");

            if (instruction[0].equals("jmp")) {
                instruction[0] = "nop";
                program.set(i, instruction[0] + " " + instruction[1]);
            } else if (instruction[0].equals("nop")) {
                instruction[0] = "jmp";
                program.set(i, instruction[0] + " " + instruction[1]);
            }

            final ProgramResult result = this.runProgram(program);

            if (result.success) {
                this.printResult(result.accumulatorValue);
                break;
            } else {
                program.set(i, original);
            }

        }
    }


}
