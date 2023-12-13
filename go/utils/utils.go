package utils

import (
	"fmt"
	"strconv"
)

type Number interface {
    int | int8 | int16 | int32 | int64 | float32 | float64
}

func Fill[T any](slice []T, val T) {
    for i := range slice {
        slice[i] = val
    }
}

func Sum[T Number](slice []T) (sum T) {
    for i := range slice {
        sum += slice[i]
    }
    return
}

func Ints(slice []string) ([]int, error) {
    nums := make([]int, len(slice), len(slice))
    for i, s := range slice {
        num, e := strconv.Atoi(s)
        if e != nil {
            fmt.Printf("%v is not an integer\n", s)
            return []int{}, fmt.Errorf("%v is not an integer", s)
        }
        nums[i] = num
    }
    return nums, nil
}
