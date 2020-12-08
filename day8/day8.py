with open('input', 'r') as input_file:
    instruction_list = [{'instruction': line[0], 'original': line[0], 'value': int(line[1]), 'visited': False}
                        for line in [instruction.split(' ') for instruction in input_file.read().split('\n')]]

accumulator = 0
program_counter = 0


def increase_pc(x):
    global program_counter
    program_counter += x


def increase_acc(x):
    global accumulator
    accumulator += x
    increase_pc(1)


def perform_instruction(instruction):
    operation_map = {
        'nop': lambda x: increase_pc(1),
        'acc': lambda x: increase_acc(x),
        'jmp': lambda x: increase_pc(x)
    }
    operation_map[instruction['instruction']](instruction['value'])


def has_recursion():
    while program_counter < len(instruction_list):
        if instruction_list[program_counter]['visited']:
            return True
        else:
            instruction_list[program_counter]['visited'] = True
            perform_instruction(instruction_list[program_counter])

    return False


def reset():
    global program_counter, accumulator
    for i in range(len(instruction_list)):
        instruction_list[i]['visited'] = False
        instruction_list[i]['instruction'] = instruction_list[i]['original']
    program_counter, accumulator = 0, 0


def next_nop_or_jmp_index(previous_index):
    for i in range(previous_index+1, len(instruction_list)):
        if instruction_list[i]['instruction'] == 'nop' or instruction_list[i]['instruction'] == 'jmp':
            return i

    return -1


"""challenge 1"""
has_recursion()
print(accumulator)
print(program_counter)

"""challenge 2"""
previous_index = 0
reset()
while has_recursion():
    reset()
    next_index = next_nop_or_jmp_index(previous_index)
    instruction_list[next_index]['instruction'] = 'jmp' if instruction_list[next_index]['instruction'] == 'nop' else 'nop'
    previous_index = next_index

print(accumulator)
print(program_counter)
