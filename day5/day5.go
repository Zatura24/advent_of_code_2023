package main

import (
	"fmt"
	"math"
	"os"
	"strconv"
	"strings"
	"time"
)

type MapRange struct {
    destination int
    rangeStart int
    rangeEnd   int
}

type CategoryMap []MapRange

func (rs CategoryMap) getSeedDestination(seed int) int {
    for _, r := range rs {
        if r.rangeStart <= seed && seed < r.rangeEnd {
            return r.destination + seed - r.rangeStart
        }
    }

    return seed
}

func (rs CategoryMap) getRangeDestination(seedRangeStack SeedRangeStack) SeedRangeStack {
    mappedSeedRangeStack := make(SeedRangeStack, 0)

    for _, r := range rs { 
        n := len(seedRangeStack)

        for n > 0 {
            // pop top element
            n -= 1
            sr := seedRangeStack[0]
            seedRangeStack = seedRangeStack[1:]

            // before needs to be checked with the next MapRange
            before := SeedRange{
                seedStart: sr.seedStart,
                seedEnd: min(sr.seedEnd, r.rangeStart),
            }
            if before.seedEnd > before.seedStart {
                seedRangeStack = append(seedRangeStack, before)
            }

            // same as in part 1, but for whole range
            inner := SeedRange{
                seedStart: max(sr.seedStart, r.rangeStart),
                seedEnd: min(sr.seedEnd, r.rangeEnd),
            }
            if inner.seedEnd > inner.seedStart {
                inner.seedStart = r.destination + inner.seedStart - r.rangeStart
                inner.seedEnd = r.destination + inner.seedEnd - r.rangeStart
                mappedSeedRangeStack = append(mappedSeedRangeStack, inner)
            }

            // after needs to be checked with the next MapRange
            after := SeedRange{
                seedStart: max(sr.seedStart, r.rangeEnd),
                seedEnd: sr.seedEnd,
            }
            if after.seedEnd > after.seedStart {
                seedRangeStack = append(seedRangeStack, after)
            }
        }
    }

    // return the combination of all mapped ranges with all non mapped ranges
    return append(mappedSeedRangeStack, seedRangeStack...)
}

func categoryMaps(lines []string) []CategoryMap {
    categoryMaps := []CategoryMap{}
    i := -1
    for _, l := range lines[1:] {
        if l == "" {
            continue
        }

        if strings.Contains(l, "map") {
            categoryMaps = append(categoryMaps, CategoryMap{})
            i++
            continue
        }

        nums := make([]int, 3)
        for i, n := range strings.Fields(l) {
            x, _ := strconv.Atoi(n)
            nums[i] = x
        }

        r := MapRange{
           destination: nums[0],
           rangeStart: nums[1],
           rangeEnd: nums[1] + nums[2],
        }

        categoryMaps[i] = append(categoryMaps[i], r)
    }

    return categoryMaps
}

func part1(lines []string) {
    seeds := make([]int, 0)
    for _, seed := range strings.Fields(strings.Split(lines[0], ":")[1]) {
        n, _ := strconv.Atoi(seed)
        seeds = append(seeds, n)
    }

    categoryMaps := categoryMaps(lines)
    closestLocation := math.MaxInt
    for _, seed := range seeds {
        for _, ranges := range categoryMaps {
            seed = ranges.getSeedDestination(seed)
        }

        closestLocation = min(closestLocation, seed)
    }

    fmt.Println(closestLocation)
}

type SeedRange struct {
    seedStart int
    seedEnd   int
}

type SeedRangeStack []SeedRange

func part2(lines []string){
    seeds := make([]int, 0)
    for _, seed := range strings.Fields(strings.Split(lines[0], ":")[1]) {
        n, _ := strconv.Atoi(seed)
        seeds = append(seeds, n)
    }

    categoryMaps := categoryMaps(lines)
    closestLocation := math.MaxInt
    for i := 0; i < len(seeds); i += 2 {
        stack := SeedRangeStack{
            SeedRange{
                seedStart: seeds[i],
                seedEnd: seeds[i] + seeds[i+1],
            },
        }

        for _, ranges := range categoryMaps {
            stack = ranges.getRangeDestination(stack)
        }

        for _, rs := range stack {
            closestLocation = min(closestLocation, rs.seedStart)
        }
    }

    fmt.Println(closestLocation)
}

func main () {
    input, err := os.ReadFile("input.txt")
    if err != nil {
        fmt.Println(err)
        os.Exit(1)
    }

    lines := strings.Split(strings.TrimSpace(string(input)), "\n")

    start := time.Now()
    part1(lines)
    fmt.Println(time.Since(start))
    start = time.Now()
    part2(lines)
    fmt.Println(time.Since(start))
}
