import numpy

from common.utils import get_input


def separate_vals(vals: str):
    s = [int(x) for x in vals[2:].split("..")]
    return min(s), max(s)


def _fire(position, velocity):

    return tuple(map(sum, zip(position, velocity))), (velocity[0] - numpy.sign(velocity[0]), velocity[1] - 1)


class ProbeCannon:

    def __init__(self, target_string: str):
        target_string = target_string[13:].split(", ")

        self.x_min_target, self.x_max_target = separate_vals(target_string[0])
        self.y_min_target, self.y_max_target = separate_vals(target_string[1])

        self.all_valids = set()

    def fire(self, velocity):

        position = (0, 0)
        max_y = 0
        position_val = 0
        while position_val == 0:
            position, velocity = _fire(position, velocity)
            max_y = max(max_y, position[1])
            position_val = self.check_position(position, velocity)

        if position_val == 1:
            return position_val, max_y
        else:
            return position_val, None

    def check_position(self, position, velocity) -> int:
        x, y = position

        if self.x_min_target <= x <= self.x_max_target and self.y_max_target >= y >= self.y_min_target:
            return 1  # In target

        if x > self.x_max_target:
            return 2  # Out of bounds - impossible

        if y < self.y_min_target and velocity[1] <= 0:
            return 3

        return 0

    def get_all_valids(self):
        if self.all_valids:
            return self.all_valids
        for x in range(1, self.x_max_target + 5):
            for y in range(self.y_min_target - 5, abs(self.y_min_target) + 5):
                pos_val, _ = self.fire((x, y))
                if pos_val == 1:
                    self.all_valids.add((x, y))
        return self.all_valids


def main():
    p = ProbeCannon(get_input()[0])
    v = p.get_all_valids()

    print(max([p.fire(x) for x in v]))
    print(len(v))


if __name__ == "__main__":
    main()
