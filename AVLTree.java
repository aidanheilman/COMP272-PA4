/**
 * COMP 272 Assignment 4 — Exercise 1.
 *
 * This is the AVLTree class from Module 4, unchanged except for delete().
 * Implement delete(). Do not change any other method, name or signature.
 * You may add private helper methods.
 */
public class AVLTree extends BinarySearchTree {

    @Override
    public Node add(int value) {
        // First perform ordinary BST insertion.
        Node inserted = super.add(value);

        if (inserted != null) {
            // The new leaf already has height 0.
            rebalanceUpward(inserted.parent);
        }

        return inserted;
    }

    /**
     * Positive: left subtree is taller.
     * Negative: right subtree is taller.
     */
    private int balanceFactor(Node node) {
        return height(node.left) - height(node.right);
    }

    /**
     * Update heights and restore balance from bottom to top.
     */
    private void rebalanceUpward(Node node) {
        while (node != null) {
            updateHeight(node);

            Node subtreeRoot = node;
            int balance = balanceFactor(node);

            if (balance > 1) {
                // Left-right case.
                if (balanceFactor(node.left) < 0) {
                    rotateLeft(node.left);
                }

                // Left-left case, or second left-right rotation.
                subtreeRoot = rotateRight(node);

            } else if (balance < -1) {
                // Right-left case.
                if (balanceFactor(node.right) > 0) {
                    rotateRight(node.right);
                }

                // Right-right case, or second right-left rotation.
                subtreeRoot = rotateLeft(node);
            }

            node = subtreeRoot.parent;
        }
    }

    /**
     * Rotate a subtree left and return its new root.
     */
    private Node rotateLeft(Node oldRoot) {
        Node newRoot = oldRoot.right;
        Node middle = newRoot.left;

        replaceAtParent(oldRoot, newRoot);

        newRoot.left = oldRoot;
        oldRoot.parent = newRoot;

        oldRoot.right = middle;
        if (middle != null) {
            middle.parent = oldRoot;
        }

        // Order matters: update the lower node first.
        updateHeight(oldRoot);
        updateHeight(newRoot);

        return newRoot;
    }

    /**
     * Rotate a subtree right and return its new root.
     */
    private Node rotateRight(Node oldRoot) {
        Node newRoot = oldRoot.left;
        Node middle = newRoot.right;

        replaceAtParent(oldRoot, newRoot);

        newRoot.right = oldRoot;
        oldRoot.parent = newRoot;

        oldRoot.left = middle;
        if (middle != null) {
            middle.parent = oldRoot;
        }

        // Order matters: update the lower node first.
        updateHeight(oldRoot);
        updateHeight(newRoot);

        return newRoot;
    }

    /**
     * Connect the new subtree root to the original parent.
     */
    private void replaceAtParent(Node oldRoot, Node newRoot) {
        Node parent = oldRoot.parent;
        newRoot.parent = parent;

        if (parent == null) {
            root = newRoot;
        } else if (parent.left == oldRoot) {
            parent.left = newRoot;
        } else {
            parent.right = newRoot;
        }
    }

    @Override
    public boolean delete(int value) {
        // TODO (Exercise 1): AVL deletion also requires rebalancing.
        // Remove value as in a BST, then restore the AVL balance condition
        // on every node from the right starting point up to the root.
        /* remove(v, k): // returns new root of v's subtree
            if v == null: return null // key not found
            if k < v.key: v.left = remove(v.left, k)
            elif k > v.key: v.right = remove(v.right, k)
            else:
            if v.left == null: return v.right // 0 or 1 child
            if v.right == null: return v.left
            s = min(v.right) // inorder successor
            v.key = s.key
            v.right = remove(v.right, s.key)
            updateHeight(v) // the walk back up
            return rebalance(v) // same rebalance as insert 
        */
        // Start the same as BST deletion
        Node target = findNode(value);
        if (target == null) {
            return false;
        }

        // Two children: copy the successor's value, then remove the successor.
        if (target.left != null && target.right != null) {
            Node successor = minimum(target.right);
            target.value = successor.value;
            target = successor;
        }

        // Now target has at most one child.
        Node child = (target.left != null) ? target.left : target.right;
        Node parentOfRemoved = target.parent;

        transplant(target, child);
        size--;

        // Rebalance accordingly, instead of just recalculating height
        // Ensures AVL conditions remain intact
        rebalanceUpward(parentOfRemoved);
        return true;
    }

    /*
     * ======================== Exercise 1, Part B — Analysis ========================
     *
     *
     * ===============================================================================
     */

}
