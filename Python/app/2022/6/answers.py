from common.utils import get_input
from collections import deque


def lantern_simulate(days: int):
    data = [int(x) for x in get_input()[0].split(",")]

    population = deque(([0] * 9))

    for i in data:
        population[i] += 1

    for i in range(days):
        population.rotate(-1)
        population[-3] += population[8]

    print(sum(population))


def part_1():
    lantern_simulate(80)


def part_2():
    lantern_simulate(256)


def main():
    part_1()
    part_2()


if __name__ == "__main__":
    main()
