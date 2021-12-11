from collections import deque

from common.utils import get_input

opening = {'(': ')', '[': ']', '{': '}', '<': '>'}

syntax_score = {')': 3, ']': 57, '}': 1197, '>': 25137}
autocomplete_score = {')': 1, ']': 2, '}': 3, '>': 4}


def score_line(line) -> int:

    stack = deque()

    for c in line:
        if c in opening:
            stack.append(c)
        elif opening[stack[-1]] != c:
            return syntax_score[c]
        else:
            stack.pop()

    return 0


def complete_and_score_line(line):
    stack = deque()

    for c in line:
        if c in opening:
            stack.append(c)
        else:
            stack.pop()

    stack.reverse()

    score = 0
    for i in [autocomplete_score[opening[x]] for x in stack]:
        score *= 5
        score += i

    return score


def part_1():
    print(sum([score_line(x) for x in get_input()]))


def part_2():
    data = sorted([complete_and_score_line(x) for x in get_input() if score_line(x) == 0])
    print(data[int(len(data)/2)])


def main():
    part_1()
    part_2()


if __name__ == "__main__":
    main()
