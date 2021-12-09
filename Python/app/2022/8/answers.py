from functools import reduce

from common.utils import get_input


def interpret_line(line: str):
    digits, output = line.split(" | ")
    digits = [reduce(lambda x, y: x + y, sorted(q)) for q in digits.split()]

    translation = {}

    for easy_digit in [x for x in digits if check_length(x)]:
        if easy_digit not in translation:
            match len(easy_digit):
                case 2:  # digit is 1
                    translation['1'] = easy_digit
                case 3:  # digit is 7
                    translation['7'] = easy_digit
                case 4:  # digit is 4
                    translation['4'] = easy_digit
                case 7:  # digit is 8
                    translation['8'] = easy_digit

            digits.remove(easy_digit)

    union_4_7 = set(translation['7']).union(translation['4'])
    translation['9'] = [x for x in digits if len(x) == 6 and len(set(x).difference(union_4_7)) == 1][0]
    digits.remove(translation['9'])

    translation['6'] = [x for x in digits if len(x) == 6 and set(x).union(translation['1']) == set(translation['8'])][0]
    digits.remove(translation['6'])

    translation['0'] = [x for x in digits if len(x) == 6][0]
    digits.remove(translation['0'])

    translation['2'] = [x for x in digits if len(set(translation['9']).difference(x)) == 2][0]
    digits.remove(translation['2'])

    translation['5'] = [x for x in digits if set(x).union(translation['2']) == set(translation['8'])][0]
    digits.remove(translation['5'])

    translation['3'] = digits[0]

    inv_map = {v: k for k, v in translation.items()}

    result = ''
    for x in [reduce(lambda x, y: x + y, sorted(q)) for q in output.split()]:
        result += inv_map[x]

    return int(result)


def check_length(entry: str) -> bool:
    length = len(entry)

    return length == 2 or length == 4 or length == 3 or length == 7


def part_1():
    data = [x.split(" | ")[1] for x in get_input()]
    counts = [len([y for y in x.split() if check_length(y)]) for x in data]
    print(sum(counts))


def part_2():
    data = get_input()
    print(sum([interpret_line(x) for x in data]))



def main():
    part_1()
    part_2()


if __name__ == "__main__":
    main()
