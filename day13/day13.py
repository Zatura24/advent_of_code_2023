import math
import sys

with open('input', 'r') as input_file:
    estimate_earliest_timestamp = int(input_file.readline())
    in_service_busses = [
        int(bus) for bus in input_file.readline().split(',') if bus != 'x']


def get_bus_wait_time(bus_id, current_time):
    return (bus_id * math.floor(current_time / bus_id) + bus_id) - current_time


"""challenge 1"""
next_min_bus_wait = (in_service_busses[0], get_bus_wait_time(
                     in_service_busses[0], estimate_earliest_timestamp))
for bus_index in range(1, len(in_service_busses)):
    next_time = get_bus_wait_time(
        in_service_busses[bus_index], estimate_earliest_timestamp)
    if next_time < next_min_bus_wait[1]:
        next_min_bus_wait = (in_service_busses[bus_index], next_time)

print(next_min_bus_wait[0]*next_min_bus_wait[1])

"""challenge 2"""
with open('input', 'r') as input_file:
    input_file.readline()
    busses = [bus for bus in input_file.readline().split(',')]

busses_with_offset = []
for index, bus in enumerate(busses):
    if bus != 'x':
        # busses_with_offset += '(t + {}) mod {} = 0, '.format(index, int(bus)) # to put in wolfram alpha
        busses_with_offset.append((index, int(bus)))

# something something Chinese remainer problem ¯\_(ツ)_/¯
