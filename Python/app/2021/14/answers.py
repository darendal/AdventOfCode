from collections import Counter

from common.utils import get_input


class PolymerChain:
    class Node:
        def __init__(self, value: str, next=None, prev=None):
            self.value = value
            self.next = next
            self.prev = prev

    def __init__(self, data):
        self.head = None
        prev = None
        for v in data[0]:
            n = PolymerChain.Node(v)

            if not self.head:
                self.head = n
            else:
                prev.next = n
                n.prev = prev

            prev = n

        self.count = len(data[0])

        self.rules = {}
        for k, val in [x.split(" -> ") for x in data[2:]]:
            self.rules[k] = val

    def transform(self):

        current = self.head
        while current.next:
            chain = current.value + current.next.value

            if chain in self.rules:
                new_node = PolymerChain.Node(self.rules[chain], next=current.next, prev=current)
                tmp = current.next

                current.next = new_node
                tmp.prev = new_node
                current = tmp

                self.count += 1
            else:
                current = current.next

    def generate_stats(self):

        return Counter(str(self))

    def __str__(self):
        ret = []

        current = self.head
        while current:
            ret.append(current.value)
            current = current.next
        return "".join(ret)


def simulate_polymer(steps: int):
    p = PolymerChain(get_input())

    for _ in range(steps):
        p.transform()

    stats = p.generate_stats()
    sorted_stats = sorted(stats.values(), reverse=True)
    print(sorted_stats[0] - sorted_stats[-1])


def part_1():
    simulate_polymer(10)


def part_2():
    simulate_polymer(40)


def main():
    part_1()
    #part_2()


if __name__ == "__main__":
    main()
