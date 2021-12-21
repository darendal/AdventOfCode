from math import prod

from bitarray import bitarray
from bitarray.util import hex2ba, ba2int

from common.utils import get_input


class BitsMessage:
    class Packet:
        def __init__(self):
            self.type = None
            self.value = None
            self.version = 0
            self.packets = []

        def parse(self, data: bitarray, start) -> int:
            self.version = ba2int(data[start: start + 3])
            start = start + 3
            self.type = ba2int(data[start: start + 3])
            match self.type:
                case 4:  # Literal
                    return self.literal(data, start + 3)
                case _:  # Operator
                    return self.operator(data, start + 3)

        def literal(self, data, start) -> int:
            val = bitarray()
            while True:
                group = data[start: start + 5]
                start = start + 5
                val.extend(group[1:])
                if group[0] == 0:
                    break

            self.value = ba2int(val)

            return start

        def operator(self, data, start) -> int:
            length_type_id = data[start]
            start = start + 1
            if length_type_id == 0:
                bit_length = ba2int(data[start: start + 15])
                start = start + 15
                packets_start = start

                while start - packets_start < bit_length:
                    p = BitsMessage.Packet()
                    start = p.parse(data, start)
                    self.packets.append(p)

                return start

            if length_type_id == 1:
                packet_count = ba2int(data[start: start + 11])
                start = start + 11

                while len(self.packets) < packet_count:
                    p = BitsMessage.Packet()
                    start = p.parse(data, start)
                    self.packets.append(p)

                return start

        def sum_versions(self):
            return self.version + sum([x.sum_versions() for x in self.packets])

        def execute(self) -> int:

            values = [x.execute() for x in self.packets]
            match self.type:
                case 0:  # Sum
                    return sum(values)
                case 1:  # Product
                    return prod(values)
                case 2:  # Min
                    return min(values)
                case 3:  # Max
                    return max(values)
                case 4:  # Literal
                    return self.value
                case 5:  # Greater Than
                    return 1 if values[0] > values[1] else 0
                case 6:  # Less Than
                    return 1 if values[0] < values[1] else 0
                case 7:  # Equals
                    return 1 if values[0] == values[1] else 0

    def __init__(self, hex_str):
        bits = hex2ba(hex_str)

        self.packet = BitsMessage.Packet()
        self.packet.parse(bits, 0)

    def sum_versions(self):
        return self.packet.sum_versions()

    def execute(self):
        return self.packet.execute()


def part_1():
    b = BitsMessage(get_input()[0])
    print(b.sum_versions())


def part_2():
    b = BitsMessage(get_input()[0])
    print(b.execute())


def main():
    part_1()
    part_2()


if __name__ == "__main__":
    main()
