from collections import defaultdict

import numpy

from common.utils import get_input, create_2d_array


class TransparentPaper:

    def __init__(self, dots):

        dot_map = defaultdict(list)
        x_max = 0
        y_max = 0
        for dot in dots:
            x, y = [int(v) for v in dot.split(",")]

            x_max = max(x, x_max)
            y_max = max(y, y_max)

            dot_map[x].append(y)

        self.dot_map = numpy.array(create_2d_array(x_max + 1, y_max + 1, 0))

        for key, values in dot_map.items():
            for v in values:
                self.dot_map[v][key] = 1

    def fold(self, instruction: str):
        direction, val = instruction.split("=")
        val = int(val)
        match direction:
            case 'x':
                tmp = self.dot_map[:, 0: val]
                folded = numpy.fliplr(self.dot_map[:, val + 1:])
                self.dot_map = numpy.add(tmp, folded)
            case 'y':
                tmp = self.dot_map[0:val]
                folded = numpy.flipud(self.dot_map[val + 1:])

                self.dot_map = numpy.add(tmp, folded)


def split_input():
    data = get_input()
    splitter_index = 0
    for index, val in enumerate(data):
        if val == "":
            splitter_index = index
            break

    return data[:splitter_index], [x.split()[2] for x in data[splitter_index + 1:]]


def part_1():

    data, folds = split_input()
    t = TransparentPaper(data)

    t.fold(folds[0])

    print(numpy.count_nonzero(t.dot_map > 0))


def part_2():
    data, folds = split_input()
    t = TransparentPaper(data)

    [t.fold(x) for x in folds]

    #AHPRPAUZ
    for y in t.dot_map:
        print(".".join(["." if x <= 0 else "#" for x in y]))


def main():
    part_1()
    part_2()


if __name__ == "__main__":
    main()
