import re

parent_pattern = re.compile(r'(?P<parent>[a-z ]+) bags contain')
child_pattern = re.compile(r'(?P<amount>[0-9]) (?P<child>[a-z ]+) bags*')

"""challenge 1"""
input_dict = {}
with open('input', 'r') as input_file:
    for line in input_file.readlines():
        parent_bag = parent_pattern.match(line).group('parent')
        child_bags = [m.group('child') for m in child_pattern.finditer(line)]
        for child in child_bags:
            if child not in input_dict:
                input_dict[child] = []

            input_dict[child].append(parent_bag)

parent_bags = set()


def check1(bag: str):
    if bag not in input_dict:
        return

    for parent in input_dict[bag]:
        check1(parent)
        parent_bags.add(parent)


check1('shiny gold')
print(len(parent_bags))


"""challenge 2"""
input_dict = {}
with open('input', 'r') as input_file:
    for line in input_file.readlines():
        parent_bag = parent_pattern.match(line).group('parent')
        child_bags = [(m.group('child'), int(m.group('amount')))
                      for m in child_pattern.finditer(line)]
        if parent_bag not in input_dict:
            input_dict[parent_bag] = []

        input_dict[parent_bag] = child_bags


def check2(bag: str):
    if bag not in input_dict:
        return 1

    return sum([sum([check2(child[0]) for x in range(0, child[1])]) for child in input_dict[bag]]) + 1


print(check2('shiny gold')-1)
