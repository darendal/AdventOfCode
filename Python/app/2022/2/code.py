from common.utils import get_input


def part_1():
    data = [x.split(" ") for x in get_input()]

    depth = 0
    horizontal = 0

    for i in data:

        direction: str = i[0]
        amt: int = int(i[1])

        if direction.startswith('f'):
            horizontal += amt
        elif direction.startswith('d'):
            depth -= amt
        elif direction.startswith('u'):
            depth += amt

    print(abs(depth) * horizontal)


def part_2():
    data = [x.split(" ") for x in get_input()]

    depth = 0
    horizontal = 0
    aim = 0

    for i in data:
        direction: str = i[0]
        amt: int = int(i[1])

        if direction.startswith('f'):
            horizontal += amt
            depth += (aim * amt)
        elif direction.startswith('d'):
            aim -= amt
        elif direction.startswith('u'):
            aim += amt

    print(abs(depth) * horizontal)

def main():
    part_1()
    part_2()


if __name__ == "__main__":
    main()
