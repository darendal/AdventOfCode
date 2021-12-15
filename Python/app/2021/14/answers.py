from collections import defaultdict

from common.utils import get_input


class PolymerChain:
    class Node:
        def __init__(self, value: str):
            self.value = value
            self.neighbors = defaultdict(int)

        def add_neighbor(self, neighbor, count: int = 1):
            self.neighbors[neighbor] += count

        def remove_neighbor(self, neighbor, count: int = 1):
            self.neighbors[neighbor] -= count

            if self.neighbors[neighbor] <= 0:
                self.neighbors.pop(neighbor)

    def __init__(self, data):
        self.first = data[0][0]
        self.nodes = {}
        prev = None
        for v in data[0]:
            if prev is None:
                prev = PolymerChain.Node(v)
                self.nodes[v] = prev
            else:
                if v not in self.nodes:
                    self.nodes[v] = PolymerChain.Node(v)
                prev.add_neighbor(v)
                prev = self.nodes[v]

        self.rules = {}
        for k, val in [x.split(" -> ") for x in data[2:]]:
            self.rules[k] = val

    def transform(self):
        valid_pairs = defaultdict(int)
        for n in self.nodes.values():
            for k, v in n.neighbors.items():
                key = n.value + k
                if key in self.rules:
                    valid_pairs[key] += v

        for pair, number in valid_pairs.items():
            product = self.rules[pair]

            left, right = pair

            if product not in self.nodes:
                self.nodes[product] = PolymerChain.Node(product)

            self.nodes[left].remove_neighbor(right, number)
            self.nodes[left].add_neighbor(product, number)
            self.nodes[product].add_neighbor(right, number)

    def generate_stats(self):

        vals = {}
        counts = [x.neighbors for x in self.nodes.values()]

        for i in self.nodes.keys():
            vals[i] = sum(x[i] if i in x else 0 for x in counts)

        vals[self.first] += 1
        return vals


def simulate_polymer(steps: int):
    p = PolymerChain(get_input())
    [p.transform() for _ in range(steps)]

    sorted_stats = sorted(p.generate_stats().values(), reverse=True)
    print(sorted_stats[0] - sorted_stats[-1])


def part_1():
    simulate_polymer(10)


def part_2():
    simulate_polymer(40)


def main():
    part_1()
    part_2()


if __name__ == "__main__":
    main()
