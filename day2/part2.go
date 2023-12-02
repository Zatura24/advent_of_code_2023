package main

import (
	"fmt"
	"os"
	"strconv"
	"strings"
)

func main() {
    data, err := os.ReadFile("input.txt")
    if err != nil {
        fmt.Println(err)
        os.Exit(1)
    }

    sum := 0;
    for _, game := range strings.Split(strings.TrimSpace(string(data)), "\n") {
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
