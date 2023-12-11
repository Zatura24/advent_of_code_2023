package main

import (
	_ "embed"
	"fmt"
	"strings"

	"github.com/Zatura24/advent_of_code_2023/go/utils"
)

//go:embed input.txt
var input string

type Point struct {
    row int
    col int
}

func calculateDistances(lines []string, expansion int) {
    // create slices of distances, 1 2 1 1 1 2 ...
    rows := make([]int, len(lines))
    cols := make([]int, len(lines[0]))
    utils.Fill(rows, expansion)
    utils.Fill(cols, expansion)

    galaxies := make([]Point, 0)
    for r, line := range lines {
        for c, char := range line {
            if char == '#' {
                galaxies = append(galaxies, Point{row: r, col: c})
                rows[r] = 1
                cols[c] = 1
            }
        }
    }

    sum := 0
    for i := 0; i < len(galaxies); i++ {
        for j := i + 1; j < len(galaxies); j++ {
            // sum distances between 2 galaxies
            p1, p2 := galaxies[i], galaxies[j]
            rowLength := utils.Sum(rows[min(p1.row, p2.row):max(p1.row, p2.row)])
            colLength := utils.Sum(cols[min(p1.col, p2.col):max(p1.col, p2.col)])
            sum += rowLength + colLength
        }
    }
    fmt.Println(sum)
}

func main() {
    lines := strings.Split(strings.TrimSpace(input), "\n")

    // part 1
    calculateDistances(lines, 2)

    // part 2
    calculateDistances(lines, 1_000_000)
}
