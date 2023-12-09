with open('input', 'r') as input_file:
    input_list = []
    for line in input_file.readlines():
        input_list.append([char for char in line.rstrip()])

"""challenge 1"""
encountered_trees = 0
horizontal_length = len(input_list[0]) - 1
j = 0

for i in range(len(input_list)):
    if (input_list[i][j] == '#'):
        encountered_trees = encountered_trees + 1

    j = j + 3
    if (j - horizontal_length > 0):
        j = j - horizontal_length - 1

print(encountered_trees)

"""challenge 2"""
multiplied_number = 1
horizontal_length = len(input_list[0]) - 1

slopes = [[1, 1], [3, 1], [5, 1], [7, 1], [1, 2]]
for slope in slopes:
    encountered_trees = 0
    j = 0
    for i in range(0, len(input_list), slope[1]):
        if (input_list[i][j] == '#'):
            encountered_trees = encountered_trees + 1

        j = j + slope[0]
        if (j - horizontal_length > 0):
            j = j - horizontal_length - 1

    multiplied_number = multiplied_number * encountered_trees

print(multiplied_number)
