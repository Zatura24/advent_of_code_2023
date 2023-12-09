from collections import deque

with open('input', 'r') as input_file:
    player1_cards, player2_cards = (deque(map(int, line.splitlines()[1:])) for line in input_file.read().split('\n\n'))

round_count = 1

while len(player1_cards) > 0 and len(player2_cards) > 0:
    # print('-- Round {} --'.format(round_count))
    # print("Player 1's deck: {}".format(player1_cards))
    # print("Player 2's deck: {}".format(player2_cards))
    player1, player2 = player1_cards.popleft(), player2_cards.popleft()
    # print("Player 1 plays: {}".format(player1))
    # print("Player 2 plays: {}".format(player2))

    if player1 > player2:
        # print('Player 1 wins the round!')
        player1_cards.append(player1)
        player1_cards.append(player2)
    else:
        # print('Player 2 wins the round!')
        player2_cards.append(player2)
        player2_cards.append(player1)

    # print('')
    round_count += 1

# print('== Post-game results ==')
# print("Player 1's deck: {}".format(player1_cards)) 
# print("Player 2's deck: {}".format(player2_cards))

print(sum([x * i for i, x in enumerate(reversed((player1_cards if len(player1_cards) > 0 else player2_cards)), start=1)]))