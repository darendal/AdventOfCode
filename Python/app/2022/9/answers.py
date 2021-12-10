import math
from collections import deque, Counter
import heapq
from common.utils import get_input


class VentMap:

    def check_up(self, val, x, y) -> bool:
        return y - 1 < self.min_y or val < self.data[y - 1][x]

    def check_down(self, val, x, y) -> bool:
        return y + 1 > self.max_y or val < self.data[y + 1][x]

    def check_left(self, val, x, y):
        return x - 1 < self.min_x or val < self.data[y][x - 1]

    def check_right(self, val, x, y):
        return x + 1 > self.max_x or val < self.data[y][x + 1]

    def __init__(self, data):

        self.data = [list(x) for x in data]

        self.low_points = {}

        self.min_x = 0
        self.min_y = 0
        self.max_x = len(data[0]) - 1
        self.max_y = len(data) - 1

        for i, line in enumerate(data):
            for j, entry in enumerate(line):
                if self.check_up(entry, j, i) \
                        and self.check_down(entry, j, i) \
                        and self.check_left(entry, j, i) \
                        and self.check_right(entry, j, i):
                    self.low_points[(j, i)] = entry

    def sum_risks(self):
        return sum([int(x) + 1 for x in self.low_points.values()])

    def paint_basins(self):

        to_visit = deque([(x, chr(index)) for index, x in enumerate(self.low_points.keys())])
        visited = set()

        while to_visit:
            entry = to_visit.popleft()
            x, y = entry[0]
            if entry[0] in visited \
                    or x < self.min_x \
                    or x > self.max_x \
                    or y < self.min_y \
                    or y > self.max_y \
                    or self.data[y][x] == '9':
                continue

            visited.add(entry[0])
            marker = entry[1]
            self.data[y][x] = marker

            to_visit.append(((x - 1, y), marker))
            to_visit.append(((x + 1, y), marker))
            to_visit.append(((x, y - 1), marker))
            to_visit.append(((x, y + 1), marker))

    def list_basins(self):
        counter = Counter([item for sublist in self.data for item in sublist])

        # Remove non-basin values
        counter.pop('9')

        return counter


def part_1():
    v = VentMap(get_input())
    print(v.sum_risks())


def part_2():
    v = VentMap(get_input())
    v.paint_basins()
    basin_sizes = heapq.nlargest(3, v.list_basins().values())

    print(math.prod(basin_sizes))


def main():
    part_1()
    part_2()


if __name__ == "__main__":
    main()
