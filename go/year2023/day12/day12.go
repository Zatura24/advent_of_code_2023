package main

import (
    _ "embed"
    "fmt"
    "strings"

    "github.com/Zatura24/advent_of_code_2023/go/utils"
)

//go:embed input.txt
var input string

type Key struct {
    i int
    gi int
}


func newCache() map[Key]int {
    return map[Key]int{}
}

var cache = newCache()

func bruteForceArrangements2(record string, groups []int, i, gi int) int {
    cacheKey := Key{i: i, gi: gi}
    recordLength, amountOfGroups := len(record), len(groups)

    if x, ok := cache[cacheKey]; ok {
        return x
    }

    if recordLength == i {
        if amountOfGroups == gi {
            return 1
        }
        return 0
    }

    if amountOfGroups == gi {
        // check if there are still damaged springs left in the record
        if i < recordLength && strings.Contains(record[i:], "#") {
            return 0
        }
        return 1
    }

    arrs := 0
    spring := record[i]
    group := groups[gi]
    if spring == '.' || spring == '?' {
        arrs += bruteForceArrangements2(record, groups, i+1, gi)
    }

    if spring == '#' || spring == '?' {
        if group <= recordLength-i &&  // check if group can fit in rest of chars
        !strings.Contains(record[i:i+group], ".") && // check if in the record if the length of the group contains operational springs
        (group == recordLength-i || record[i+group] != '#') { // check if group fills the remaining length of the record or that the last spring is not damaged
            arrs += bruteForceArrangements2(record, groups, min(i+group+1, recordLength), gi+1)
        }
    }

    cache[cacheKey] = arrs
    return arrs
}

func main() {
    lines := strings.Split(strings.TrimSpace(input), "\n")

    // part 1
    sum := 0
    for _, l := range lines {
        s := strings.Split(l, " ")
        chars := s[0]
        groups, _ := utils.Ints(strings.Split(s[1], ","))
        cache = newCache()
        n := bruteForceArrangements2(chars, groups, 0, 0)
        sum += n
    }
    fmt.Println(sum)

    // part 2
    sum = 0
    for _, l := range lines {
        s := strings.Split(l, " ")
        chars := strings.Join([]string{s[0], s[0], s[0], s[0], s[0]}, "?")
        groups, _ := utils.Ints(strings.Split(strings.Join([]string{s[1], s[1], s[1], s[1], s[1]}, ","), ","))
        cache = newCache()
        n := bruteForceArrangements2(chars, groups, 0, 0)
        sum += n
    }
    fmt.Println(sum)
}

