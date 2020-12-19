import re

with open('input', 'r') as input_file:
    rule_list, input_list = [section.splitlines() for section in input_file.read().split('\n\n')]

rule_map = dict([rule.split(': ', 1) for rule in rule_list])

def generate_regex_pattern(rule_number):
    if rule_number == '8':
        return generate_regex_pattern('42') + '+'
    elif rule_number == '11':
        a = generate_regex_pattern('42')
        b = generate_regex_pattern('31')
        return '(?:{})'.format('|'.join(f'{a}{{{n}}}{b}{{{n}}}' for n in range(1, 10)))

    rule = rule_map[rule_number]
    if rule in ('"a"', '"b"'):
        return rule[1]
    else:
        pattern = []
        for rule_part in rule.split(' | '):
            pattern.append(''.join([generate_regex_pattern(number) for number in rule_part.split(' ')]))
        return '(?:{})'.format('|'.join(pattern))


input_pattern = generate_regex_pattern('0')
count = 0
for input in input_list:
    count += 1 if re.fullmatch(input_pattern, input) else 0
print(count)