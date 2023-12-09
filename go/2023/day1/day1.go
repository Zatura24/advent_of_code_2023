package main

import (
    _ "embed"
	"fmt"
	"strconv"
	"strings"
	"unicode"
)

//go:embed input.txt
var input string

func part1() {
    sum := 0
    for _, line := range strings.Split(strings.TrimSpace(input), "\n") {

        digits := make([]byte, 2)
        for i := 0; i < len(line); i++ {
            if unicode.IsDigit(rune(line[i])) {
                digits[0] = line[i]
                break
            }
        }
        for j := len(line) - 1; j >= 0; j-- {
            if unicode.IsDigit(rune(line[j])) {
                digits[1] = line[j]
                break
            }
        }
        i, _ := strconv.Atoi(string(digits))
        sum += i
    }

    fmt.Println(sum)
}

var mapping map[string]string = map[string]string{
    "one":      "o1e",
    "two":      "t2o",
    "three":    "t3e",
    "four":     "f4r",
    "five":     "f5e",
    "six":      "s6x",
    "seven":    "s7n",
    "eight":    "e8t",
    "nine":     "n9e",
}

func part2() {
    sum := 0
    for _, line := range strings.Split(strings.TrimSpace(input), "\n") {
        for x, y := range mapping {
            line = strings.ReplaceAll(line, x, y)
        }

        digits := make([]byte, 2)
        for i := 0; i < len(line); i++ {
            if unicode.IsDigit(rune(line[i])) {
                digits[0] = line[i]
                break
            }
        }
        for j := len(line) - 1; j >= 0; j-- {
            if unicode.IsDigit(rune(line[j])) {
                digits[1] = line[j]
                break
            }
        }

        i, _ := strconv.Atoi(string(digits))
        sum += i
    }

    fmt.Println(sum)
}

func main () {
   part1()
   part2()
}
