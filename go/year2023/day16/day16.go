package main

import (
	_ "embed"
	"fmt"
	"strings"
	"sync"

	"github.com/Zatura24/advent_of_code_2023/go/utils"
)

//go:embed input.txt
var input string

type Beam struct {
    r int
    c int
    dr int
    dc int
}

func countEnergizedTiles(grid []string, startBeam Beam, c chan int, wg *sync.WaitGroup) {
    defer wg.Done()

    queue := make([]Beam, 0)
    queue = append(queue, startBeam)

    seen := utils.NewSet[Beam]()
    for len(queue) != 0 {
        // pop queue
        cursor := queue[0]
        queue = queue[1:]

        // move beam
        r, c := cursor.r + cursor.dr, cursor.c + cursor.dc

        // check beam oob
        if r < 0 || r >= len(grid) || c < 0 || c >= len(grid[0]) {
            continue
        }

        char := grid[r][c]

        // riding along the same path
        if char == '.' || (char == '-' && cursor.dc != 0) || (char == '|' && cursor.dr != 0) {
            p := Beam{r: r, c: c, dr: cursor.dr, dc: cursor.dc}
            if !seen.Contains(p) {
                seen.Add(p)
                queue = append(queue, p)
            }
            continue
        }

        // splitting on rows
        if char == '|' {
            p1 := Beam{r: r, c: c, dr: -1, dc: 0}
            if !seen.Contains(p1) {
                seen.Add(p1)
                queue = append(queue, p1)
            }
            p2 := Beam{r: r, c: c, dr: 1, dc: 0}
            if !seen.Contains(p2) {
                seen.Add(p2)
                queue = append(queue, p2)
            }
            continue
        }

        // splitting on cols
        if char == '-' {
            p1 := Beam{r: r, c: c, dr: 0, dc: 1}
            if !seen.Contains(p1) {
                seen.Add(p1)
                queue = append(queue, p1)
            }
            p2 := Beam{r: r, c: c, dr: 0, dc: -1}
            if !seen.Contains(p2) {
                seen.Add(p2)
                queue = append(queue, p2)
            }
            continue
        }

        //  1  0 ->  0 -1
        // -1  0 ->  0  1
        //  0  1 -> -1  0
        //  0 -1 ->  1  0
        if char == '/' {
            p := Beam{r: r, c: c, dr: -cursor.dc, dc: -cursor.dr}
            if !seen.Contains(p) {
                seen.Add(p)
                queue = append(queue, p)
            }
            continue
        }

        //  1  0 ->  0  1
        // -1  0 ->  0 -1
        //  0  1 ->  1  0
        //  0 -1 -> -1  0
        if char == '\\' {
            p := Beam{r: r, c: c, dr: cursor.dc, dc: cursor.dr}
            if !seen.Contains(p) {
                seen.Add(p)
                queue = append(queue, p)
            }
            queue = append(queue, p)
            continue
        }
    }

    energized := utils.NewSet[Beam]()
    for _ , b := range seen.Members() {
        energized.Add(Beam{r: b.r, c: b.c})
    }

    c <- len(energized)
}

func main() {
    grid := strings.Split(strings.TrimSpace(input), "\n")

    amountOfDirections := len(grid)*2 + len(grid[0])*2
    var wg sync.WaitGroup
    c := make(chan int, amountOfDirections)

    wg.Add(amountOfDirections)
    for i := 0; i < len(grid); i++ {
        go countEnergizedTiles(grid, Beam{r: i, c: -1, dr: 0, dc: 1}, c, &wg)
        go countEnergizedTiles(grid, Beam{r: i, c: len(grid[0]), dr: 0, dc: -1}, c, &wg)
    }
    for i := 0; i < len(grid[0]); i++ {
        go countEnergizedTiles(grid, Beam{r: -1, c: i, dr: 1, dc: 0}, c, &wg)
        go countEnergizedTiles(grid, Beam{r: len(grid), c: i, dr: -1, dc: 0}, c, &wg)
    }

    go func() {
        defer close(c)
        wg.Wait()
    }()

    maxEnergized := 0
    for n := range c {
        maxEnergized = max(maxEnergized, n)
    }

    fmt.Println(maxEnergized)
}
