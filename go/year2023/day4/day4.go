package main

import (
    _ "embed"
	"fmt"
	"math"
	"slices"
	"strings"
)

//go:embed input.txt
var input string

func part1() {
    points := 0
    lines := strings.Split(strings.TrimSpace(input), "\n")
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

func part2() {
    lines := strings.Split(strings.TrimSpace(input), "\n")

    // filling cardsCounts with 1's
    cardsCounts := make([]int, len(lines), len(lines))
    cardsCounts[0] = 1
    for i := 1; i < len(cardsCounts); i *= 2 {
        copy(cardsCounts[i:], cardsCounts[:i])
    }

    for cardNum, card := range lines {
        splittedCard := strings.FieldsFunc(card, func(r rune) bool {
            return r == ':' || r == '|'
        })

        winning := strings.Fields(splittedCard[1])
        have := strings.Fields(splittedCard[2])

        nextCardNum := cardNum + 1
        for _, n := range have {
            if slices.Contains(winning, n) {
                cardsCounts[nextCardNum] += cardsCounts[cardNum]
                nextCardNum++
            }
        }
    }

    // count total cards
    cards := 0
    for _, n := range cardsCounts {
        cards += n
    }
    fmt.Println(cards)
}

func main() {
    part1()
    part2()
}
