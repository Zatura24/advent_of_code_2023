import re

"""Challenge 1"""
predicate = re.compile(
    r'(?P<min>\d+)-(?P<max>\d+)\s(?P<character>[a-z]):\s(?P<password>[a-z]+)')
valid_password_count = 0

with open('input', 'r') as input_file:
    for line in input_file.readlines():
        match = predicate.search(line)
        character_count = match.group(
            'password').count(match.group('character'))
        if int(match.group('min')) <= character_count <= int(match.group('max')):
            valid_password_count = valid_password_count + 1

print('valid passwords: {}'.format(valid_password_count))


"""Challenge 2"""
predicate = re.compile(
    r'(?P<first>\d+)-(?P<second>\d+)\s(?P<character>[a-z]):\s(?P<password>[a-z]+)')
valid_password_count = 0

with open('input', 'r') as input_file:
    for line in input_file.readlines():
        match = predicate.search(line)
        first = match.group('password')[int(match.group('first'))-1]
        second = match.group('password')[int(match.group('second'))-1]
        # (a and not b) or (not a and b)
        if (first == match.group('character') and not second == match.group('character')) or (not first == match.group('character') and second == match.group('character')):
            valid_password_count = valid_password_count + 1

print('valid passwords: {}'.format(valid_password_count))
