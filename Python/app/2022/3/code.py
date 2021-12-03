from typing import List

from bitstring import BitArray

from common.utils import get_input


def get_bitcount_array(data: List) -> List:
    counts = [0] * len(data[0])

    for i in data:
        for index, j in enumerate(i):

            if j == "1":
                counts[index] += 1

    return counts


def part_1():
    data = get_input()

    counts = get_bitcount_array(data)

    half = len(data) / 2
    for index, i in enumerate(counts):
        if i < half:
            counts[index] = '0'
        else:
            counts[index] = '1'

    bits = BitArray(bin=''.join(counts))

    gamma = bits.uint

    bits.invert()

    epsilon = bits.uint
    print(f"{gamma * epsilon}")


def part_2():
    data = get_input()

    oxy_list = data
    i = 0
    while len(oxy_list) > 1:
        zeros, ones = [], []
        for x in oxy_list:
            zeros.append(x) if x[i] == '0' else ones.append(x)

        if len(zeros) > (len(oxy_list)/2):
            oxy_list = zeros
        else:
            oxy_list = ones
        i += 1

    co2_list = data
    i = 0
    while len(co2_list) > 1:
        zeros, ones = [], []
        for x in co2_list:
            zeros.append(x) if x[i] == '0' else ones.append(x)

        if len(zeros) > (len(co2_list) / 2):
            co2_list = ones
        else:
            co2_list = zeros
        i += 1

    print(BitArray(bin=oxy_list[0]).uint * BitArray(bin=co2_list[0]).uint)


def main():
    part_1()
    part_2()


if __name__ == "__main__":
    main()
