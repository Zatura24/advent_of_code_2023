package main

import (
    _ "embed"
	"fmt"
	"math"
	"strconv"
	"strings"
)

//go:embed input.txt
var input string

type SeedRange struct {
    seedStart int
    seedEnd   int
}

type SeedRangeQueue []SeedRange

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

func (rs CategoryMap) getRangeDestination(seedRangeQueue SeedRangeQueue) SeedRangeQueue {
    mappedSeedRanges := make(SeedRangeQueue, 0)

    for _, r := range rs {
        n := len(seedRangeQueue)

        for n > 0 {
            // pop first element
            n -= 1
            sr := seedRangeQueue[0]
            seedRangeQueue = seedRangeQueue[1:]

            // before needs to be checked with the next MapRange
            before := SeedRange{
                seedStart: sr.seedStart,
                seedEnd: min(sr.seedEnd, r.rangeStart),
            }
            if before.seedEnd > before.seedStart {
                seedRangeQueue = append(seedRangeQueue, before)
            }

            // same as in part 1, but for whole range
            inner := SeedRange{
                seedStart: max(sr.seedStart, r.rangeStart),
                seedEnd: min(sr.seedEnd, r.rangeEnd),
            }
            if inner.seedEnd > inner.seedStart {
                inner.seedStart = r.destination + inner.seedStart - r.rangeStart
                inner.seedEnd = r.destination + inner.seedEnd - r.rangeStart
                mappedSeedRanges = append(mappedSeedRanges, inner)
            }

            // after needs to be checked with the next MapRange
            after := SeedRange{
                seedStart: max(sr.seedStart, r.rangeEnd),
                seedEnd: sr.seedEnd,
            }
            if after.seedEnd > after.seedStart {
                seedRangeQueue = append(seedRangeQueue, after)
            }
        }
    }

    // return the combination of all mapped ranges with all non mapped ranges
    return append(mappedSeedRanges, seedRangeQueue...)
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

func seeds(lines []string) []int {
    seeds := make([]int, 0)
    for _, seed := range strings.Fields(strings.Split(lines[0], ":")[1]) {
        n, _ := strconv.Atoi(seed)
        seeds = append(seeds, n)
    }
    return seeds
}

func part1(lines []string) {
    seeds := seeds(lines)
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

func part2(lines []string){
    seeds := seeds(lines)
    categoryMaps := categoryMaps(lines)

    closestLocation := math.MaxInt
    for i := 0; i < len(seeds); i += 2 {
        queue := SeedRangeQueue{
            SeedRange{
                seedStart: seeds[i],
                seedEnd: seeds[i] + seeds[i+1],
            },
        }

        for _, ranges := range categoryMaps {
            queue = ranges.getRangeDestination(queue)
        }

        for _, rs := range queue {
            closestLocation = min(closestLocation, rs.seedStart)
        }
    }

    fmt.Println(closestLocation)
}

func main () {
    lines := strings.Split(strings.TrimSpace(input), "\n")

    part1(lines)
    part2(lines)
}
