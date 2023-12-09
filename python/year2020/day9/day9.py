from collections import deque
from itertools import combinations

preamble_length = 25

with open('input', 'r') as input_file:
    lines = input_file.read().splitlines()
    lines_as_ints = [int(x) for x in lines]
    preamble = deque(lines_as_ints[:preamble_length])
    input_deque = deque(lines_as_ints[preamble_length:])


def create_posible_sum_from_list(preamble, n=2):
    return [sum(x) for x in combinations(preamble, n)]


while len(input_deque) > 0:
    possible_combinations = create_posible_sum_from_list(preamble)

    number = input_deque.popleft()
    if number in possible_combinations:
        preamble.popleft()
        preamble.append(number)
    else:
        invalid_number = number
        break
print(invalid_number)


def find_first_contagious_set_in_list_that_adds_to_number(list, number_to_find, set_size):
    n = len(list)
    start = 0
    curr_sum = list[0]
    index = 1
    end = 1
    while end <= n:
        if curr_sum == number_to_find and index == set_size:
            return list[start:end]
        while index >= set_size:
            curr_sum -= list[start]
            start += 1
            index = set_size-1
        if end < n:
            curr_sum += list[end]
            index += 1
        end += 1

    return None


for i in range(2, 25):
    contagious_set = find_first_contagious_set_in_list_that_adds_to_number(
        lines_as_ints, invalid_number, i)
    if contagious_set:
        print(min(contagious_set) + max(contagious_set))
        break
