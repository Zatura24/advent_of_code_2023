package utils

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
