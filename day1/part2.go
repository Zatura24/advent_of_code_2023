package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
	"strings"
	"unicode"
)

var mapping map[string]string = map[string]string{
    "one": "o1e",
    "two": "t2o",
    "three": "t3e",
    "four": "f4r",
    "five": "f5e",
    "six": "s6x",
    "seven": "s7n",
    "eight": "e8t",
    "nine": "n9e",
}

func main() {
    file, err := os.Open("input.txt")
    if err != nil { 
        fmt.Errorf("%v", err)
        os.Exit(1)
    }
    defer file.Close()

    scanner := bufio.NewScanner(file)


    sum := 0
    for scanner.Scan() {
        line := scanner.Text()

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
