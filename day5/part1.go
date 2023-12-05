package main

import (
	"fmt"
	"math"
	"os"
	"strconv"
	"strings"
)

type Range struct {
    destination int
    sourceStart int
    sourceEnd   int
}

type Ranges []Range

func (rs Ranges) getDestination(seed int) int {
    for _, r := range rs {
        if r.sourceStart <= seed && seed < r.sourceEnd {
            return r.destination + seed - r.sourceStart
        }
    }

    return seed
}

func main () {
    input, err := os.ReadFile("input.txt")
    if err != nil {
        fmt.Println(err)
        os.Exit(1)
    }

    lines := strings.Split(strings.TrimSpace(string(input)), "\n")

    // collecting seeds
    seeds := make([]int, 0)
    for _, seed := range strings.Fields(strings.Split(lines[0], ":")[1]) {
        n, _ := strconv.Atoi(seed)
        seeds = append(seeds, n)
    }
   
    // creating range maps
    seedMaps := []Ranges{}
    for _, l := range lines[1:] {
        if l == "" {
            continue
        }

        if strings.Contains(l, "map") {
            seedMaps = append(seedMaps, Ranges{})
            continue
        }

        nums := make([]int, 3)
        for i, n := range strings.Fields(l) {
            x, _ := strconv.Atoi(n)
            nums[i] = x
        }

        r := Range{
           destination: nums[0],
           sourceStart: nums[1],
           sourceEnd: nums[1] + nums[2],
        }
        seedMaps[len(seedMaps)-1] = append(seedMaps[len(seedMaps)-1], r)
    }

    closestLocation := math.MaxInt
    for _, seed := range seeds {
        for _, ranges := range seedMaps {
            seed = ranges.getDestination(seed)
        }

        if seed < closestLocation {
            closestLocation = seed
        }
    }

    fmt.Println(closestLocation)
}
