package main

import (
	"fmt"
	"os"
	"strconv"
	"strings"
)

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

func main() {
    data, err := os.ReadFile("input.txt")
    if err != nil {
        fmt.Println(err)
        os.Exit(1)
    }

    sum := 0;
    for i, game := range strings.Split(strings.TrimSpace(string(data)), "\n") {
        sets := strings.Split(game, ": ")[1]
        if !checkSets(sets) {
            continue
        }

        sum += i + 1
   }

   fmt.Println(sum)
}
