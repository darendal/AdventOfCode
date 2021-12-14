from collections import defaultdict
from typing import List


def get_input(path: str = "input.txt") -> List:
    with open(path, 'r') as input_file:
        input = input_file.read()

    return input.splitlines()


class keydefaultdict(defaultdict):
    def __missing__(self, key):
        if self.default_factory is None:
            raise KeyError(key)
        else:
            ret = self[key] = self.default_factory(key)
            return ret