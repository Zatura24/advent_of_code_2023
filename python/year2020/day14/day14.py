import re

with open('input', 'r') as input_file:
    input_list = input_file.read().splitlines()

mask_pattern = re.compile(r'^mask = (?P<mask>[01X]+)$', re.MULTILINE)
mem_pattern = re.compile(r'^mem\[(?P<address>[0-9]+)\] = (?P<value>[0-9]+)$', re.MULTILINE)


def apply_mask(value: int, mask: str):
    value = value | int(mask.replace('X', '0'), 2)
    value = value & int(mask.replace('X', '1'), 2)
    return value


mem = {}
mask = None
for line in input_list:
    if mask_pattern.search(line):
        mask = mask_pattern.match(line).group('mask')
    else:
        address, value = mem_pattern.fullmatch(line).group('address', 'value')
        value = apply_mask(int(value), mask)
        mem[int(address)] = value

print(sum(mem.values()))


def get_all_positions(pos, mask):
    if not mask:
        yield 0
    else:
        for m in get_all_positions(pos >> 1, mask[:-1]):
            if mask[-1] == 'X':
                yield 2 * m + 1
                yield 2 * m + 0
            elif mask[-1] == '1':
                yield 2 * m + 1
            elif mask[-1] == '0':
                yield 2 * m + (pos & 1)


mem = {}
mask = None
for line in input_list:
    if mask_pattern.search(line):
        mask = mask_pattern.match(line).group('mask')
    else:
        address, value = mem_pattern.fullmatch(line).group('address', 'value')
        for m in get_all_positions(int(address), mask):
            mem[m] = int(value)

print(sum(mem.values()))
