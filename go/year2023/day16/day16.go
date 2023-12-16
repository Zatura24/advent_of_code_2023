package main

import (
	_ "embed"
	"fmt"
	"strings"
	"time"

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

func printGrid(gridO []string, seen utils.Set[Beam]) {
    time.Sleep(10 * time.Millisecond)
    grid := make([]string, len(gridO))
    copy(grid, gridO)

    for _, p := range seen.Members() {
        out := []rune(grid[p.r])
        out[p.c] = '#'
        grid[p.r] = string(out)
    }
    fmt.Println("\033[2J\033[H")
    fmt.Println(strings.Join(grid, "\n"))


    energized := utils.NewSet[Beam]()
    for _ , b := range seen.Members() {
        energized.Add(Beam{r: b.r, c: b.c})
    }
    fmt.Println(len(energized))
}

func main() {
    grid := strings.Split(strings.TrimSpace(input), "\n")

    queue := make([]Beam, 0)
    queue = append(queue, Beam{r: 0, c: -1, dr: 0, dc: 1})

    seen := utils.NewSet[Beam]()
    for len(queue) != 0 {
//        printGrid(grid, seen)

        // pop queue
        cursor := queue[0]
        queue = queue[1:]

        // pop stack
//        cursor := queue[len(queue)-1]
//        queue = queue[:len(queue)-1]

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
    fmt.Println(len(energized))
}
