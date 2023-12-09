import re
import collections

pattern = re.compile(r'(.+)\(contains (.+)\)')

all_ingredients = set()
times_seen = collections.Counter()
possibility = {}

with open('input', 'r') as input_file:
    input_list = [pattern.fullmatch(line).groups() for line in input_file.read().splitlines()]

"""challenge 1"""
for line in input_list:
    ingredients = set(line[0].rstrip().split(' '))
    allergens = set(line[1].rstrip().split(', '))

    all_ingredients |= ingredients
    times_seen.update(ingredients)

    for allergen in allergens:
        if allergen not in possibility:
            possibility[allergen] = ingredients.copy()
        else:
            possibility[allergen] &= ingredients

bad_ingredients = {ingredient for ingredients in possibility.values() for ingredient in ingredients}
print(sum(times_seen[food] for food in (all_ingredients - bad_ingredients)))

"""challenge 2"""
taken = set()
allergen_and_ingredient = []
while True:
    for allergen, ingredients in possibility.items():
        difference = (ingredients - taken)
        if len(difference) == 1:
            ingredient = difference.pop()
            allergen_and_ingredient.append((allergen,ingredient))
            taken.add(ingredient)
            break
    else:
        break

print(','.join(pair[1] for pair in sorted(allergen_and_ingredient)))