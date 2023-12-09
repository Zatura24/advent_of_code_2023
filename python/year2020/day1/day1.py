import itertools
import math

input_list = []
with open('input', 'r') as input_file:
    for line in input_file.readlines():
        input_list.append(int(line))

"""Challenge 1"""
for number_set in itertools.combinations(input_list, 2):
    if (sum(number_set) == 2020):
        print("{} these numbers add to 2020".format(number_set))
        print("They multiply to: {}".format(math.prod(number_set)))


"""Challenge 2"""
for number_set in itertools.combinations(input_list, 3):
    if (sum(number_set) == 2020):
        print("{} these numbers add to 2020".format(number_set))
        print("They multiply to: {}".format(math.prod(number_set)))
