with open('input', 'r') as input_file:
    character_mapping = {
        '.': -1,
        'L': 0,
        '#': 1
    }
    input_list = [[character_mapping[space] for space in row] for row in input_file.read().splitlines()]

rows, cols = len(input_list), len(input_list[0])

star_vertices = []
"""challenge 1"""
# star_vertices.append([(-1, 0)])  # up
# star_vertices.append([(1, 0)])  # down
# star_vertices.append([(0, -1)])  # left
# star_vertices.append([(0, 1)])  # right
# star_vertices.append([(-1, -1)])  # up left
# star_vertices.append([(-1, 1)])  # up right
# star_vertices.append([(1, -1)])  # down left
# star_vertices.append([(1, 1)])  # down right
"""challenge 2"""
star_vertices.append([(-x, 0) for x in range(1, 1+rows)])  # up
star_vertices.append([(x, 0) for x in range(1, 1+rows)])  # down
star_vertices.append([(0, -x) for x in range(1, 1+cols)])  # left
star_vertices.append([(0, x) for x in range(1, 1+cols)])  # right
star_vertices.append([(-x, -x) for x in range(1, 1+cols)])  # up left
star_vertices.append([(-x, x) for x in range(1, 1+cols)])  # up right
star_vertices.append([(x, -x) for x in range(1, 1+cols)])  # down left
star_vertices.append([(x, x) for x in range(1, 1+cols)])  # down right

def update(grid):
    new_grid = [row[:] for row in grid]

    def find_first_seat_in_all_directions(row_index, col_index):
        adjacent_seats = []

        for direction in star_vertices:
            for row_offset, col_offset in direction:
                if 0 <= row_index+row_offset < rows and 0 <= col_index+col_offset < cols and grid[row_index+row_offset][col_index+col_offset] >= 0:
                    adjacent_seats.append((row_index+row_offset, col_index+col_offset))
                    break

        return adjacent_seats

    for row in range(rows):
        for col in range(cols):
            total = sum([grid[next_chair[0]][next_chair[1]] for next_chair in find_first_seat_in_all_directions(row, col)])

            if grid[row][col] == 0 and total == 0:
                new_grid[row][col] = 1
            """challenge 1"""
            # if grid[row][col] == 1 and total >= 4: 
            #     new_grid[row][col] = 0
            """challenge 2"""
            if grid[row][col] == 1 and total >= 5: 
                new_grid[row][col] = 0

    return new_grid


did_update = True
iterations = 0
while did_update:
    new_input_list = update(input_list)
    if new_input_list == input_list:
        did_update = False
    else:
        iterations += 1
        input_list = new_input_list

total = 0
for row in input_list:
    for seat in row:
        if seat == 1:
            total += 1
print(total)