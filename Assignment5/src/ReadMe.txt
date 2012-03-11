//Daniel Monroy

How does the Big O() of this sort compare to the Big O() of the destructive merge sort in the class notes? How much garbage is produced by this sort function?

Both of these methods are O(nlogn).
The garbage produced in this sort function is much less than that of the one in the class notes. It produces no garbage since the iterative loop allows for the list to be accessed directly and looped through only once. So there aren't any extra garbage of lists.