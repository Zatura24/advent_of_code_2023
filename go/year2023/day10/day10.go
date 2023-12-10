package main

import (
	_ "embed"
	"fmt"
	"slices"
	"strings"
)

//go:embed input.txt
var input string

type Direction int
const (
    NORTH Direction = 0x1
    EAST  Direction = 0x2
    SOUTH Direction = 0x4
    WEST  Direction = 0x8
)

func atod(a byte) Direction {
    if a == '|' {
        return NORTH | SOUTH
    }
    if a == '-' {
        return EAST | WEST
    }
    if a == 'L' {
        return NORTH | EAST
    }
    if a == 'J' {
        return NORTH | WEST
    }
    if a == '7' {
        return SOUTH | WEST
    }
    if a == 'F' {
        return SOUTH | EAST
    }
    // hardcoded because I'm lazy :)
    if a == 'S' {
        return EAST | SOUTH
    }
    return 0
}

type Pipe struct {
    x int
    y int
    d Direction
}

type Field []string

func (f Field) getPipe(x, y int) (pipe Pipe) {
    pipe.x = x
    pipe.y = y
    pipe.d = atod(f[y][x])
    return
}

func (f Field) findSPipe() (sPipe Pipe) {
    for y, line := range f {
        for x := range line {
            if f[y][x] == 'S' {
                sPipe.x = x
                sPipe.y = y
                sPipe.d = atod('S')
                return
            }
        }
    }
    return
}

func main() {
    field := Field(strings.Split(input, "\n"))

    sPipe := field.findSPipe()

    queue := []Pipe{sPipe}
    visited := []Pipe{sPipe}

    // part 1
    for len(queue) != 0 {
        pipe := queue[0]
        queue = queue[1:]

        if pipe.d & NORTH != 0 {
            nextPipe := field.getPipe(pipe.x, pipe.y - 1)
            if !slices.Contains(visited, nextPipe) {
                visited = append(visited, nextPipe)
                queue = append(queue, nextPipe)
            }
        }
        if pipe.d & EAST != 0 {
            nextPipe := field.getPipe(pipe.x + 1, pipe.y)
            if !slices.Contains(visited, nextPipe) {
                visited = append(visited, nextPipe)
                queue = append(queue, nextPipe)
            }
        }
        if pipe.d & SOUTH != 0 {
            nextPipe := field.getPipe(pipe.x, pipe.y + 1)
            if !slices.Contains(visited, nextPipe) {
                visited = append(visited, nextPipe)
                queue = append(queue, nextPipe)
            }
        }
        if pipe.d & WEST != 0 {
            nextPipe := field.getPipe(pipe.x - 1, pipe.y)
            if !slices.Contains(visited, nextPipe) {
                visited = append(visited, nextPipe)
                queue = append(queue, nextPipe)
            }
        }
    }
    fmt.Println(len(visited)/2)

    // part 2
    count := 0
    for y := range field {
        north := 0
        for x := range field[y] {
            pipe := field.getPipe(x, y)
            if slices.Contains(visited, pipe) {
                if pipe.d & NORTH != 0 {
                    north++
                }
                continue
            }

            if north % 2 != 0 {
                count++
            }
         }
    }

    fmt.Println(count)
}
