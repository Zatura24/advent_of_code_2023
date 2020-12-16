import time
start_time = time.time()

input_list = [0,12,6,13,20,1,17]

last_occureance_map = {n: i+1 for i, n in enumerate(input_list[:-1])}

previous_number = input_list[-1]

# turn index is 1 less than the real
for turn in range(len(input_list), 30000000):
    next_number = 0

    if previous_number in last_occureance_map:
        next_number = turn - last_occureance_map[previous_number]
    
    last_occureance_map[previous_number] = turn
    previous_number = next_number

print(previous_number)
print("--- %s seconds ---" % (time.time() - start_time))