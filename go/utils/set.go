package utils

// From: https://bitfieldconsulting.com/golang/generic-set

type Set[E comparable] map[E]struct{}

func NewSet[E comparable]() Set[E] {
    return make(Set[E])
}

func (s Set[E]) Add(v E) {
    s[v] = struct{}{}
}

func (s Set[E]) Contains(v E) bool {
    _, ok := s[v]
    return ok
}

func (s Set[E]) Members() []E {
    result := make([]E, 0, len(s))
    for v := range s {
        result = append(result, v)
    }
    return result
}
