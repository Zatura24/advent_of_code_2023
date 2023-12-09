package main

import (
    _ "embed"
	"fmt"
	"strconv"
	"strings"
)

//go:embed input.txt
var input string

var cubes = map[string]int{
    "red": 12,
    "green": 13,
    "blue": 14,
}

func checkSets(sets string) bool {
    for _, set := range strings.Split(sets, "; ") {
        for _, cube := range strings.Split(set, ", ") {
            x := strings.Split(cube, " ")
            n, _ := strconv.Atoi(x[0])
            if cubes[x[1]] < n {
                return false
            }
        }
    }

    return true
}

func part1() {
    sum := 0;
    for i, game := range strings.Split(strings.TrimSpace(input), "\n") {
        sets := strings.Split(game, ": ")[1]
        if !checkSets(sets) {
            continue
        }

        sum += i + 1
   }

   fmt.Println(sum)
}

func part2() {
    sum := 0;
    for _, game := range strings.Split(strings.TrimSpace(input), "\n") {
        sets := strings.Split(game, ": ")[1]
        cubes := map[string]int{}
        for _, set := range strings.Split(sets, "; ") {
            for _, cube := range strings.Split(set, ", ") {
                x := strings.Split(cube, " ")
                n, _ := strconv.Atoi(x[0])
                cubes[x[1]] = max(cubes[x[1]], n)
            }
        }

        sum += cubes["red"] * cubes["green"] * cubes["blue"]
   }

   fmt.Println(sum)
}

func main() {
    part1()
    part2()
}
