with open('input', 'r') as input_file:
    input_list = [(instruction[0], int(instruction[1:]))
                  for instruction in input_file.read().splitlines()]

directions = ['N', 'E', 'S', 'W']

north_south = 0
east_west = 0
facing = 'E'

def change_direction(value):
    global facing
    next_index = int((directions.index(facing) + value / 90) % 4)
    facing = directions[next_index]
    return (north_south, east_west)

command_mapping = {
    'N': lambda x: (north_south + x, east_west),
    'E': lambda x: (north_south, east_west + x),
    'S': lambda x: (north_south - x, east_west),
    'W': lambda x: (north_south, east_west - x),
    'F': lambda x: command_mapping[facing](x),
    'R': lambda x: change_direction(x),
    'L': lambda x: change_direction(-x)
}

for command, value in input_list:
    if command in command_mapping:
        north_south, east_west = command_mapping[command](value)

print(east_west, north_south)
print(abs(north_south) + abs(east_west))


north_south = 0
east_west = 0
waypoint_north_south = 1
waypoint_east_west = 10

command_mapping = {
    'N': lambda x: (waypoint_north_south + x, waypoint_east_west),
    'E': lambda x: (waypoint_north_south, waypoint_east_west + x),
    'S': lambda x: (waypoint_north_south - x, waypoint_east_west),
    'W': lambda x: (waypoint_north_south, waypoint_east_west - x),
    'R': lambda x: (-waypoint_east_west, waypoint_north_south) if x == 90 else
                    (-waypoint_north_south, -waypoint_east_west) if x == 180 else
                    command_mapping['L'](90) if x == 270 else None,
    'L': lambda x: (waypoint_east_west, -waypoint_north_south) if x == 90 else
                    (-waypoint_north_south, -waypoint_east_west) if x == 180 else
                    command_mapping['R'](90) if x == 270 else None
}

for command, value in input_list:
    if command in command_mapping:
        waypoint_north_south, waypoint_east_west = command_mapping[command](value)

    if command == 'F':
        north_south, east_west = north_south + value * waypoint_north_south, east_west + value * waypoint_east_west

print(east_west, north_south)
print(abs(north_south) + abs(east_west))
