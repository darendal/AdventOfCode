from typing import List

from common.utils import get_input


class BingoBoard:

    def __init__(self, data: List):

        self.lookup = {}
        self.board = []
        self.has_won = False

        for i, line in enumerate(data):
            row = []
            for j, num in enumerate([x for x in line.split(" ") if x]):
                self.lookup[num] = (i, j)
                row.append(num)
            self.board.append(row)

    def __str__(self):
        return '\n'.join(' '.join(str(x) for x in row) for row in self.board)

    def mark_and_check(self, call) -> bool:

        if call not in self.lookup:
            return False

        row, col = self.lookup[call]

        self.board[row][col] = 'X'

        self.has_won = all(x == 'X' for x in self.board[row]) or all(x[col] == "X" for x in self.board)

        return self.has_won

    def sum_unmarked(self):
        s = 0
        for row in self.board:
            s += sum([int(x) for x in row if x != 'X'])
        return s


def build_boards(data) -> List:
    board_list = []
    board = []
    for entry in data:
        if not entry:
            board_list.append(BingoBoard(board))
            board.clear()
        else:
            board.append(entry)

    board_list.append(BingoBoard(board))

    return board_list


def part_1():
    data = get_input()
    calls = data[0].split(',')
    boards = build_boards(data[2:])

    last_call = 0
    winner: BingoBoard = None
    for call in calls:
        for board in boards:
            if board.mark_and_check(call):
                last_call = int(call)
                winner = board
                break
        if winner:
            break

    print(last_call * winner.sum_unmarked())


def part_2():
    data = get_input()
    calls = data[0].split(',')
    boards = build_boards(data[2:])

    last_call = 0
    winner: BingoBoard = None
    for call in calls:
        rem_boards = [b for b in boards if not b.has_won]
        for board in rem_boards:
            if board.mark_and_check(call) and len(rem_boards) == 1:
                last_call = int(call)
                winner = board
        if winner is not None:
            break

    print(last_call * winner.sum_unmarked())


def main():
    part_1()
    part_2()


if __name__ == "__main__":
    main()
