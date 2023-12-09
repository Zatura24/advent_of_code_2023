def seat_id(seat_pos: tuple): return int(
    seat_pos[0], 2) * 8 + int(seat_pos[1], 2)


with open('input', 'r') as input_file:
    seat_id_list = []
    convert_to_binary = {
        'B': '1',
        'F': '0',
        'R': '1',
        'L': '0'
    }

    for line in input_file.read().splitlines():
        binary_line = ''.join(convert_to_binary[char] for char in line)
        seat_id_list.append(seat_id((binary_line[:7], binary_line[7:])))


"""challenge 1"""
seat_id_list.sort()

highest_seat_ID = seat_id_list[-1]
print(highest_seat_ID)

"""challenge 2"""
for index in range(1, len(seat_id_list)):
    if (seat_id_list[index] - seat_id_list[index-1] > 1):
        print(seat_id_list[index] - 1)
        break
