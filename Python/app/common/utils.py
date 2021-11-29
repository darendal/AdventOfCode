from typing import List


def get_input(path: str = "input.txt") -> List:
    with open(path, 'r') as input_file:
        input = input_file.read()

    return input.splitlines()

