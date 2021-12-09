from common.utils import get_input

import statistics


def part_1():
    data = [int(x) for x in get_input()[0].split(",")]

    median = statistics.median(data)

    print(sum([abs(x - median) for x in data]))


def part_2():
    data = [int(x) for x in get_input()[0].split(",")]

    mean = int(statistics.mean(data))

    print(sum([((abs(x - mean)) * (abs((x - mean)) + 1))/2 for x in data]))


def main():
    part_1()
    part_2()


if __name__ == "__main__":
    main()