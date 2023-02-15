//Exercise 1 and 2

fun sumRangeRec(min: Int, max: Int): Int {
    tailrec fun sumRangeAux(min: Int, sum: Int): Int =
        if(min == max) min+sum
        else sumRangeAux(min+1, min+sum)

    return sumRangeAux(min, 0)
}

//Exercise 3

val isEven: (Int) -> Boolean = {it % 2 == 0}
val isOdd: (Int) -> Boolean = {it % 2 != 0}
fun isDivisor(n: Int): (Int) -> Boolean = { n % it == 0}

//Exercise 4

fun sumRange(min: Int, max: Int, predicate: (Int) -> Boolean): Int {
    tailrec fun sumRangeAux(min: Int, sum: Int): Int =
        if(min == max)
            if (predicate(min)) min+sum else sum
        else if (predicate(min)) sumRangeAux(min+1, min+sum) else sumRangeAux(min+1, sum)


    return sumRangeAux(min, 0)
}