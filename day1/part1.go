package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
	"unicode"
)

func main () {
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
