package main

import (
	_ "embed"
	"fmt"
	"strconv"
	"strings"
)

//go:embed input.txt
var input string

func waysToBeat(time, record int) int {
    waysToBeat := 0
    for j := 1; j < time; j++ {
        if j * (time - j) > record {
            waysToBeat++
        }
    }
    return waysToBeat
}

func part1(lines []string) {
    times := strings.Fields(lines[0])[1:]
    records := strings.Fields(lines[1])[1:]

    count := 1
    for i := 0; i < len(times); i++ {
        time, _ := strconv.Atoi(times[i])
        record, _ := strconv.Atoi(records[i])

        count *= waysToBeat(time, record)
    }
    fmt.Println(count)
}

func part2(lines []string){
    time, _ := strconv.Atoi(strings.ReplaceAll(strings.Split(lines[0], ":")[1], " ", ""))
    record, _ := strconv.Atoi(strings.ReplaceAll(strings.Split(lines[1], ":")[1], " ", ""))

    fmt.Println(waysToBeat(time, record))
}

func main() {
    lines := strings.Split(input, "\n")

    part1(lines)
    part2(lines)
}
