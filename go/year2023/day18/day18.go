package main

import (
    _ "embed"
    "fmt"
    "strings"
    "strconv"
)

//go:embed input.txt
var input string

type Point struct {
    r int
    c int
}

func innerPoints(points []Point, boundary int) int {
    area := 0
    for i := range points {
        p1 := points[i]
        var p2 Point
        if i + 1 == len(points) {
            p2 = points[0]
        } else {
            p2 = points[i + 1]
        }
        area += (p1.r + p2.r) * (p1.c - p2.c)
    }
    return (area/2 + 1 - boundary / 2 + boundary)
}

func part1(lines []string) {
    boundary := 0
    points := []Point{Point{0, 0}}

    for _, l := range lines {
        split := strings.Split(l, " ")
        d := split[0]
        n, _ := strconv.Atoi(split[1])
        boundary += n

        lp := points[len(points)-1]
        switch d {
        case "U":
            points = append(points, Point{lp.r - n, lp.c})
        case "R":
            points = append(points, Point{lp.r, lp.c + n})
        case "D":
            points = append(points, Point{lp.r + n, lp.c})
        case "L":
            points = append(points, Point{lp.r, lp.c - n})
        }
    }
    fmt.Println(innerPoints(points, boundary))
}

func part2(lines []string) {
    boundary := 0
    points := []Point{Point{0, 0}}

    for _, l := range lines {
        encoding := strings.Split(l, " ")[2][2:8]
        d := string(encoding[len(encoding)-1])
        ni, _ := strconv.ParseInt(string(encoding[:5]), 16, 64)
        n := int(ni)
        boundary += n

        lp := points[len(points)-1]
        switch d {
        case "3":
            points = append(points, Point{lp.r - n, lp.c})
        case "0":
            points = append(points, Point{lp.r, lp.c + n})
        case "1":
            points = append(points, Point{lp.r + n, lp.c})
        case "2":
            points = append(points, Point{lp.r, lp.c - n})
        }
    }

    fmt.Println(innerPoints(points, boundary))
}

func main() {
    lines := strings.Split(strings.TrimSpace(input), "\n")

    part1(lines)

    part2(lines)
}
