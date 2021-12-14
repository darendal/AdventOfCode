from typing import List

from common.utils import get_input, keydefaultdict


class CaveMap:
    class Node:

        def __init__(self, label):
            self.label = label
            self.neighbors = set()
            self.is_small = label.islower()

        def add_neighbor(self, n):
            self.neighbors.add(n)

    def __init__(self, data: List):

        self.nodes = keydefaultdict(CaveMap.Node)

        for entry in data:
            s, e = entry.split("-")

            start = self.nodes[s]
            end = self.nodes[e]

            start.add_neighbor(end)
            end.add_neighbor(start)

    def trace_route(self, with_backtrack: bool = False):

        start = self.nodes["start"]
        all_paths = set()
        if with_backtrack:
            [self.__trace_route_backtrack(x, {start.label: 1}, "start", all_paths, None) for x in start.neighbors]
        else:
            [self.__trace_route(x, {start.label: 1}, "start", all_paths) for x in start.neighbors]

        return len(all_paths)

    def __trace_route(self, start, visited, path, all_paths):
        if start.label == "end":
            path += ("-" + start.label)
            if path not in all_paths:
                all_paths.add(path)
            return

        if start.is_small:
            visited[start.label] = 1

        path += ("-" + start.label)

        for n in start.neighbors:
            if n.label not in visited:
                self.__trace_route(n, visited, path, all_paths)

        if start.is_small:
            visited.pop(start.label)

    def __trace_route_backtrack(self, start, visited, path, all_paths, back_track):
        if start.label == "end":
            path += ("-" + start.label)
            if path not in all_paths:
                all_paths.add(path)
            return

        if start.is_small:
            if start.label in visited:
                if start.label == "start" or start.label == "end":
                    return
                elif start.label == back_track:
                    if visited[start.label] > 1:
                        return
                    else:
                        visited[start.label] += 1
                else:
                    return
            else:
                visited[start.label] = 1

        path += ("-" + start.label)

        for n in start.neighbors:
            if path + "-" + start.label in all_paths:
                return
            self.__trace_route_backtrack(n, visited.copy(), path, all_paths, back_track)
            if start.is_small and not any([x == 2 for x in visited.values()]):
                self.__trace_route_backtrack(n, visited.copy(), path, all_paths, start.label)


def part_1():
    print(CaveMap(get_input()).trace_route())


def part_2():
    c = CaveMap(get_input())
    print(c.trace_route(with_backtrack=True))


def main():
    part_1()
    part_2()


if __name__ == "__main__":
    main()
