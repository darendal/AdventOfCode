from collections import defaultdict
from typing import List


def get_input(path: str = "input.txt") -> List:
    with open(path, 'r') as input_file:
        input = input_file.read()

    return input.splitlines()


def create_2d_array(max_x, max_y, val):
    return [[val for _x in range(0, max_x)] for _y in range(0, max_y)]


def flatten(nested_list: list):
    return [item for sublist in nested_list for item in sublist]


class keydefaultdict(defaultdict):
    def __missing__(self, key):
        if self.default_factory is None:
            raise KeyError(key)
        else:
            ret = self[key] = self.default_factory(key)
            return ret
