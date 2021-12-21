import math
from heapq import heappop, heapify, heappush
from typing import List

from common.utils import get_input


class ChitonMap:

    min_x = 0
    min_y = 0

    def __init__(self, data: List, repeats: int = 1):
        self.map = [[int(x) for x in y] for y in data]

        self.x_size = len(self.map[0])
        self.y_size = len(self.map)

        self.max_x = self.x_size * repeats
        self.max_y = self.y_size * repeats

    def get_neighbors(self, x: int, y: int) -> List:
        ret = []

        if x - 1 >= self.min_x:
            ret.append((x-1, y))

        if x + 1 < self.max_x:
            ret.append((x + 1, y))

        if y - 1 >= self.min_y:
            ret.append((x, y - 1))

        if y + 1 < self.max_y:
            ret.append((x, y + 1))

        return ret

    def get_value(self, x, y):

        x_act = x % self.x_size
        y_act = y % self.y_size

        modifier = (int(x / self.x_size)) + (int(y / self.y_size))

        return ((self.map[y_act][x_act] + modifier - 1) % 9) + 1

    def find_path(self):

        start = (0, 0)
        target = (self.max_x - 1, self.max_y - 1)

        distance = {start: 0}
        visited = {}

        to_visit = [(0, start)]
        heapify(to_visit)

        while to_visit:
            _, current = heappop(to_visit)

            if current in visited:
                continue

            current_distance = distance[current]
            for n in self.get_neighbors(current[0], current[1]):
                if n in visited:
                    continue
                temp = distance[n] if n in distance else math.inf
                distance[n] = min(temp, current_distance + self.get_value(n[0], n[1]))
                heappush(to_visit, (distance[n], n))

            visited[current] = True
            if current == target:
                return distance[target]


def part_1():
    print(ChitonMap(get_input()).find_path())


def part_2():
    print(ChitonMap(get_input(), 5).find_path())


def main():
    part_1()
    part_2()


if __name__ == "__main__":
    main()
