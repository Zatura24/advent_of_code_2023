package main

import (
	"fmt"
	"math"
	"os"
	"slices"
	"strings"
)

func main () {
    input, err := os.ReadFile("input.txt")
    if err != nil {
        fmt.Println(err)
        os.Exit(1)
    }

    points := 0
    lines := strings.Split(strings.TrimSpace(string(input)), "\n")
    for _, card := range lines {
        splittedCard := strings.FieldsFunc(card, func(r rune) bool {
            return r == ':' || r == '|'
        })

        common := 0
        winning := strings.Fields(splittedCard[1])
        have := strings.Fields(splittedCard[2])
        for _, n := range have {
            if slices.Contains(winning, n) {
                common++
            }
        }

        if common != 0 {
            points += int(math.Pow(2, float64(common - 1)))
        }
    }

    fmt.Println(points)
}
