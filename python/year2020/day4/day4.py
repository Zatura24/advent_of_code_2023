import re

with open('input', 'r') as input_file:
    passports = input_file.read().split('\n\n')


required_field = {
    'byr': lambda x: re.match(r'\d{4}', x) and 1920 <= int(x) <= 2002,
    'iyr': lambda x: re.match(r'\d{4}', x) and 2010 <= int(x) <= 2020,
    'eyr': lambda x: re.match(r'\d{4}', x) and 2020 <= int(x) <= 2030,
    'hgt': lambda x: (re.match(r'\d+cm', x) and 150 <= int(x[:-2]) <= 193) or (re.match(r'\d+in', x) and 59 <= int(x[:-2]) <= 76),
    'hcl': lambda x: re.match(r'#[0-9a-f]{6}', x),
    'ecl': lambda x: x in ['amb', 'blu', 'brn', 'gry', 'grn', 'hzl', 'oth'],
    'pid': lambda x: re.match(r'^[0-9]{9}$', x)
}


def create_passport(input_list: list):
    passport = {}
    for key, value in list(map(lambda x: x.split(':'), input_list)):
        passport[key] = value
    return passport


"""challenge 1"""
def has_required_keys(passport):
    return all(field in passport for field in required_field.keys())


valid_passport_count = 0
for passport in passports:
    if has_required_keys(create_passport(passport.rsplit())):
        valid_passport_count += 1
print(valid_passport_count)


"""challenge 2"""
def is_valid(passport):
    return has_required_keys(passport) and all(
        [required_field[field](passport[field])
         for field in required_field.keys()]
    )


valid_passport_count = 0
for passport in passports:
    if is_valid(create_passport(passport.rsplit())):
        valid_passport_count += 1
print(valid_passport_count)
