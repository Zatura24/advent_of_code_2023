import pyparsing as pp
import operator
import functools

"""
    Used resources:
        - https://stackoverflow.com/a/23956778
        - https://github.com/pyparsing/pyparsing/blob/master/examples/fourFn.py
"""

with open('input', 'r') as input_file:
    input_list = input_file.read().splitlines()

operator_mapping = {
    '+': operator.add,
    '*': operator.mul
}

expression_stack = []


def push_first(tokens):
    expression_stack.append(tokens[0])


def create_bnf_part1():
    lpar, rpar = map(pp.Suppress, '()')
    expr = pp.Forward()
    operator = pp.oneOf('+ *')
    operand = pp.Word(pp.nums)
    factor = operand.setParseAction(push_first) | pp.Group(lpar + expr + rpar)
    expr <<= factor + \
        pp.ZeroOrMore((operator + factor).setParseAction(push_first))
    return expr


def create_bnf_part2():
    lpar, rpar = map(pp.Suppress, '()')
    plus, mult = map(pp.Literal, '+*')
    expr = pp.Forward()
    operand = pp.Word(pp.nums)
    factor = operand.setParseAction(push_first) | pp.Group(lpar + expr + rpar)
    term = factor + \
        pp.ZeroOrMore((plus + factor).setParseAction(push_first))
    expr <<= term + \
        pp.ZeroOrMore((mult + term).setParseAction(push_first))
    return expr


def evaluate_stack(s):
    operation = s.pop()

    if operation in '+*':
        right = evaluate_stack(s)
        left = evaluate_stack(s)
        return operator_mapping[operation](left, right)
    else:
        return int(operation)


result_list = []
bnf = create_bnf_part2()  # Change for part 1
for expression in input_list:
    expression_stack[:] = []
    bnf.parseString(expression)
    result_list.append(evaluate_stack(expression_stack[:]))

print(functools.reduce(operator.add, result_list))
