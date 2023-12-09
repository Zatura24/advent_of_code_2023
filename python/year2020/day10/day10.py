with open('input', 'r') as input_file:
    input_list = [int(adapter) for adapter in input_file.read().splitlines()]
    input_list.sort()

"""challenge 1"""
adapter_list = [0]  # supply starting joltage
adapter_list.extend(input_list)
adapter_list.append(input_list[-1]+3)  # supplying my internal adapter

one_jolt_difference = 0
three_jolt_difference = 0
for i in range(1, len(adapter_list)):
    joltage_difference = adapter_list[i] - adapter_list[i-1]
    if joltage_difference == 1:
        one_jolt_difference += 1
    elif joltage_difference == 3:
        three_jolt_difference += 1

print(one_jolt_difference * three_jolt_difference)

"""challenge 2"""
allowed_steps = [1, 2, 3]
last_item = input_list[-1]

arrangement_history = {}


def find_arrangements(current_value: int, list: []):
    if str(current_value) in arrangement_history:
        return arrangement_history[str(current_value)]

    if current_value == last_item:
        return 1

    split_index = 1
    value = 0
    for step in (x for x in allowed_steps if current_value+x in list):
        value += find_arrangements(current_value+step, list[split_index:])
        split_index += 1
        arrangement_history[str(current_value)] = value

    return value


print(find_arrangements(0, input_list))
