from common.utils import get_input

def part_1():
    data = [int(x) for x in get_input()]

    dec = 0
    prev = data[0]
    for i in data:
        if i > prev:
            dec += 1
        prev = i

    print(f"Part 1: {dec}")

def part_2():
    data = [int(x) for x in get_input()]

    prev = 0
    dec = 0

    for i, _ in enumerate(data[:-3]):
        temp = data[i] + data[i+1] + data[i+2]

        if temp > prev:
            dec += 1

        prev = temp

    print(f"Part 2: {dec}")


def main():

    part_1()
    part_2()



if __name__ == "__main__":
    main()

