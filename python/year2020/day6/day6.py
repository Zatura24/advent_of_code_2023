import functools


def add(a, b):
    return a+b


"""challenge 1"""
with open('input', 'r') as input_file:
    # input as long line of text with all answered question
    input_list = [group.replace('\n', '')
                  for group in input_file.read().split('\n\n')]

unique_answers_list = list(map(lambda answers: len(set(answers)), input_list))
print(functools.reduce(add, unique_answers_list))

"""challenge 2"""
with open('input', 'r') as input_file:
    # input as sub array with answered questions per person in group
    input_list = [group.split('\n')
                  for group in input_file.read().split('\n\n')]


def common_awnser_count_from_group(group):
    group_size = len(group)
    if group_size == 1:
        return len(group[0])

    yes_awnsers = {}
    for person_awnser in group:
        for question in person_awnser:
            yes_awnsers[question] = yes_awnsers.get(question, 0) + 1

    return len(list(filter(lambda x: x == group_size, yes_awnsers.values())))


common_awnsers_list = [common_awnser_count_from_group(
    group) for group in input_list]
print(functools.reduce(add, common_awnsers_list))
