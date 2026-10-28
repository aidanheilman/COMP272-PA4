import java.util.TreeMap;
import java.util.TreeSet;

/**
 * COMP 272 Assignment 4 — Exercise 3: nearest free locker.
 *
 * The library has n lockers numbered 1..n, all free at the start.
 * A student asks for a preferred number and receives the FREE locker closest
 * to it. If two free lockers are equally close, the SMALLER number wins.
 *
 * Use java.util.TreeSet and/or java.util.TreeMap (your choice).
 * Do not change the method signatures. You may add private fields and helpers.
 */
public class LockerAssigner {

    // Initialize a TreeSet to be used as locker system, 
    // and private numLockers to keep track between methods
    private TreeSet<Integer> freeLockers = new TreeSet<>();
    private int numLockers;

    /** Create lockers 1..n, all free. Throw IllegalArgumentException if n < 0. */
    public LockerAssigner(int n) {
        // Add n nodes if legal
        if (n < 0) {
            throw new IllegalArgumentException("Negative arguments not accepted");
        }
        // Assign n to numLockers for validation in release()
        numLockers = n;
        for (int i = 1; i <= n; i++) {
            freeLockers.add(i);
        }
    }

    /**
     * Assign the free locker closest to preferred (ties: smaller number),
     * mark it as taken, and return its number. preferred may be any int,
     * even outside 1..n. Return -1 if no locker is free.
     */
    public int assign(int preferred) {
        // Handle base case -> no locker free, return -1
        if (freeLockers.isEmpty()) {
            return -1;
        }
        // Declare var to be returned later
        // Use Integer to be able to compare to null
        int taken;
        Integer floorLocker = freeLockers.floor(preferred);
        Integer ceilingLocker = freeLockers.ceiling(preferred);
        
        // Secondary case -> floor or ceiling returned null
        if (floorLocker == null) {
            taken = ceilingLocker;
        }
        else if (ceilingLocker == null) {
            taken = floorLocker;
        }
        // Store difference between available and preferred for comparison
        // Use (long) to avoid possible overflow
        else {
            long lowDiff = (long) preferred - floorLocker;
            long highDiff = (long) ceilingLocker - preferred;

            // Check if lockers are equidistant and if not, which is higher, in one step
            if (lowDiff <= highDiff) {
                taken = floorLocker;
            } 
            else {
                taken = ceilingLocker;
            }
        }
        // Delete result from tree, return result
        freeLockers.remove(taken);
        return taken;
    }

    /**
     * Make locker free again and return true. Return false (and change
     * nothing) if it is already free or is not a number in 1..n.
     */
    public boolean release(int locker) {
        // Validate that locker is in range of available lockers and is currently taken
        if (locker < 1 || locker > numLockers || freeLockers.contains(locker)) {
            return false;
        }
        // Valid locker, "release" (add it back in)
        freeLockers.add(locker);
        return true;
    }

    /*
     * ======================== Exercise 3, Part B — Analysis =======================
     * Let n be the number of lockers.
     *
     * Q1. Which structure did you use (TreeSet, TreeMap, or both), what does it
     *     store, and why does it fit this problem?
     *
     * Q2. If you had stored the free lockers in an unsorted ArrayList instead,
     *     what would be the worst-case running time of assign and of release?
     *
     * Q3. What is the worst-case running time of assign and release with your
     *     solution? Justify it using the fact that TreeSet and TreeMap are
     *     red-black trees.
     * ============================================================================
     */
}
