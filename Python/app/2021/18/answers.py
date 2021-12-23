import ast
import copy
import itertools
import math

from common.utils import get_input


class SnailfishNumber:

    def __init__(self, number):

        self.value = None

        self.next = None
        self.prev = None

        self.left = None
        self.right = None

        if isinstance(number, int):
            self.value = number
        else:
            if isinstance(number[0], SnailfishNumber):
                self.left = number[0]
            else:
                self.left = SnailfishNumber(number[0])

            if isinstance(number[1], SnailfishNumber):
                self.right = number[1]
            else:
                self.right = SnailfishNumber(number[1])

            lefts_rightmost = self.left.get_rightmost()
            rights_leftmost = self.right.get_leftmost()

            lefts_rightmost.next = rights_leftmost
            rights_leftmost.prev = lefts_rightmost

    def reduce(self):
        while True:
            while self.explode():
                pass
            if not self.split():
                break

    def explode(self, depth=1):

        if self.value is not None:
            return False

        if depth > 4 and (self.left.value is not None and self.right.value is not None):
            self.value = 0

            if self.left.prev is not None:
                self.left.prev.value += self.left.value
                self.left.prev.next = self
            if self.right.next is not None:
                self.right.next.value += self.right.value
                self.next = self.right.next

            self.prev = self.left.prev
            self.next = self.right.next

            if self.next is not None:
                self.next.prev = self

            if self.prev is not None:
                self.prev.next = self

            self.left = None
            self.right = None

            return True

        else:
            d = depth + 1
            return self.left.explode(d) or self.right.explode(d)

    def split(self) -> bool:
        if self.value is None:
            return self.left.split() or self.right.split()

        if self.value < 10:
            return False

        self.left = SnailfishNumber(self.value // 2)
        self.right = SnailfishNumber(math.ceil(self.value / 2))
        self.left.prev = self.prev
        self.left.next = self.right

        self.right.next = self.next
        self.right.prev = self.left

        if self.prev is not None:
            self.prev.next = self.left

        if self.next is not None:
            self.next.prev = self.right

        self.value = None
        self.prev = None
        self.next = None
        return True

    def magnitude(self) -> int:

        if self.value is not None:
            return self.value

        return (self.left.magnitude() * 3) + (self.right.magnitude() * 2)

    def get_leftmost(self):
        if self.value is not None:
            return self  # Node is leaf node

        return self.left.get_leftmost()

    def get_rightmost(self):
        if self.value is not None:
            return self  # Node is leaf node

        return self.right.get_rightmost()

    def __add__(self, other):
        s = SnailfishNumber([self, other])
        s.reduce()
        return s

    def __str__(self):
        if self.value is not None:
            return str(self.value)
        return f"[{str(self.left)},{str(self.right)}]"


def part_1():
    data = [SnailfishNumber(ast.literal_eval(x)) for x in get_input()]

    start = data[0]

    for i in data[1:]:
        start = start + i

    print(start.magnitude())


def part_2():
    data = [SnailfishNumber(ast.literal_eval(x)) for x in get_input()]
    print(max([(copy.deepcopy(x) + copy.deepcopy(y)).magnitude() for x, y in itertools.permutations(data, 2)]))


def main():
    part_1()
    part_2()


if __name__ == "__main__":
    main()
