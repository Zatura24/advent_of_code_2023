import re
import numpy as np

rule_pattern = re.compile(
    r'(?P<rule_name>[a-z ]+): (?P<lower_bound_1>[0-9]+)-(?P<upper_bound_1>[0-9]+) or (?P<lower_bound_2>[0-9]+)-(?P<upper_bound_2>[0-9]+)')

rule_map = {}

with open('input', 'r') as input_file:
    input_list = [block.split('\n')
                  for block in input_file.read().split('\n\n')]

    for rule in input_list[0]:
        rule_groupdict = rule_pattern.match(rule).groupdict()
        rule_map[rule_groupdict['rule_name']] = lambda x, \
            lower_bound_1=rule_groupdict['lower_bound_1'], \
            upper_bound_1=rule_groupdict['upper_bound_1'], \
            lower_bound_2=rule_groupdict['lower_bound_2'], \
            upper_bound_2=rule_groupdict['upper_bound_2']: \
            int(lower_bound_1) <= x <= int(upper_bound_1) or int(lower_bound_2) <= x <= int(upper_bound_2)

    my_ticket = input_list[1][1].split(',')

    nearby_tickets = np.array([ticket.split(',') for ticket in input_list[2][1:]])

error_list = []
for ticket in nearby_tickets:
    mask = [all([not f(int(number)) for f in rule_map.values()]) for number in ticket]
    error_list.extend(map(int, ticket[mask]))

print(sum(error_list))

valid_nearby_tickets = nearby_tickets[[all([any([f(int(number)) for f in rule_map.values()]) for number in ticket]) for ticket in nearby_tickets]]