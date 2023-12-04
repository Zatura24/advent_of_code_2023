package main

import (
	"fmt"
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


    lines := strings.Split(strings.TrimSpace(string(input)), "\n")

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
