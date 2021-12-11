from common.utils import get_input


def part_1():
    data = [x.split() for x in get_input()]

    depth = 0
    horizontal = 0

    for i in data:

        direction, amt = i
        amt: int = int(amt)

        match direction:
            case 'forward':
                horizontal += amt
            case 'down':
                depth -= amt
            case 'up':
                depth += amt

    print(abs(depth) * horizontal)


def part_2():
    data = [x.split() for x in get_input()]

    depth = 0
    horizontal = 0
    aim = 0

    for i in data:
        direction, amt = i
        amt: int = int(amt)

        match direction:
            case 'forward':
                horizontal += amt
                depth += (aim * amt)
            case 'down':
                aim -= amt
            case 'up':
                aim += amt

    print(abs(depth) * horizontal)


def main():
    part_1()
    part_2()


if __name__ == "__main__":
    main()
