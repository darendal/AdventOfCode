from collections import deque
from typing import List

from common.utils import get_input


class OctoMap:

    def __init__(self, data: List):
        self.data = data

        for index, line in enumerate(data):
            data[index] = [int(x) for x in line]

        self.min_x = 0
        self.max_x = len(data[0]) - 1

        self.min_y = 0
        self.max_y = len(data) - 1

    def get_valid_neighbors(self, x: int, y: int) -> List:
        x_start = max(self.min_x, x - 1)
        x_end = min(self.max_x, x + 1) + 1

        y_start = max(self.min_y, y - 1)
        y_end = min(self.max_y, y + 1) + 1

        return [(x, y) for x in range(x_start, x_end) for y in range(y_start, y_end)]

    def simulate(self):
        flashing = set()
        to_visit = deque()

        for y in range(len(self.data)):
            for x in range(len(self.data[y])):
                self.data[y][x] += 1

                if self.data[y][x] > 9:
                    flashing.add((x, y))
                    to_visit.extend(self.get_valid_neighbors(x, y))

        while to_visit:
            node = to_visit.pop()

            if node in flashing:
                continue

            x, y = node
            self.data[y][x] += 1

            if self.data[y][x] > 9:
                flashing.add((x, y))
                to_visit.extend(self.get_valid_neighbors(x, y))

        for x, y in flashing:
            self.data[y][x] = 0

        return len(flashing)


def part_1():
    octo = OctoMap(get_input())
    print(sum([octo.simulate() for _ in range(100)]))


def part_2():
    octo = OctoMap(get_input())

    num_octo = (octo.max_x + 1) * (octo.max_y + 1)

    counter = 0
    while True:
        counter += 1
        if octo.simulate() == num_octo:
            break

    print(counter)


def main():
    part_1()
    part_2()


if __name__ == "__main__":
    main()
