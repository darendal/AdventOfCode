from typing import List

from common.utils import get_input


def get_field_and_points():
    data = get_input()

    points = []
    max_x = 0
    max_y = 0

    for line in data:
        values = line.split(" -> ")
        x1, y1 = [int(x) for x in values[0].split(",")]
        x2, y2 = [int(x) for x in values[1].split(",")]

        max_x = max(max_x, max(x1, x2))
        max_y = max(max_y, max(y1, y2))

        points.append([(x1, y1), (x2, y2)])

    max_y += 1
    max_x += 1
    field = [[]] * max_y
    for i in range(max_x):
        field[i] = ([0] * max_x)

    return field, points


def sum_field(field):
    count = 0

    for i in field:
        count += len([x for x in i if x > 1])

    return count


def get_range(start: int, end: int) -> List:

    if start < end:
        return list(range(start, end + 1))
    else:
        return list(range(start, end - 1, -1))


def part_1():
    field, points = get_field_and_points()

    for p in points:
        x1 = min(p[0][0], p[1][0])
        x2 = max(p[0][0], p[1][0])
        y1 = min(p[0][1], p[1][1])
        y2 = max(p[0][1], p[1][1])

        if x1 == x2:
            for i in range(y1, y2 + 1):
                field[x1][i] += 1
        elif y1 == y2:
            for i in range(x1, x2 + 1):
                field[i][y1] += 1

    print(sum_field(field))


def part_2():
    field, points = get_field_and_points()

    for p in points:
        x1, y1 = p[0]
        x2, y2 = p[1]

        if x1 == x2:
            y1 = min(p[0][1], p[1][1])
            y2 = max(p[0][1], p[1][1])
            for i in range(y1, y2 + 1):
                field[x1][i] += 1
        elif y1 == y2:
            x1 = min(p[0][0], p[1][0])
            x2 = max(p[0][0], p[1][0])
            for i in range(x1, x2 + 1):
                field[i][y1] += 1
        else:
            for x, y in zip(get_range(x1, x2), get_range(y1, y2)):
                field[x][y] += 1

    print(sum_field(field))


def main():
    part_1()
    part_2()


if __name__ == "__main__":
    main()
