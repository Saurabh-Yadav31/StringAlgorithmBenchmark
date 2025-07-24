package algorithm;

import java.util.*;

public class SuffixTree {

    // Helper class to allow mutable integer for leaf end index
    class End {
        public int value;

        public End(int val) {
            this.value = val;
        }
    }

    // Node class for suffix tree
    class SuffixTreeNode {
        Map<Character, SuffixTreeNode> children = new HashMap<>();
        int start;      // Start index of the edge label in text
        End end;        // End index (mutable for leaves)
        SuffixTreeNode suffixLink; // Suffix link as per Ukkonen’s algorithm
        int suffixIndex = -1;      // Leaf nodes store suffix start index; internal nodes have -1

        public SuffixTreeNode(int start, End end) {
            this.start = start;
            this.end = end;
            this.suffixLink = root; // Default suffix link points to root initially
        }

        // Returns length of edge label for this node
        public int edgeLength() {
            return end.value - start + 1;
        }
    }

    private SuffixTreeNode root;
    private SuffixTreeNode activeNode;
    private int activeEdge = -1;        // Index of current character of active edge in text
    private int activeLength = 0;       // How deep into active edge we are matching
    private int remainingSuffixCount = 0; // Number of suffixes yet to be added in this phase
    private End leafEnd;                // Global end for all leaves, updated for each extension
    private SuffixTreeNode lastNewNode = null; // Keeps track of last internal node created to set suffix links
    private final String text;

    /**
     * Constructor initializes all variables and builds suffix tree for input text
     */
    public SuffixTree(String text) {
        this.text = text;
        this.leafEnd = new End(-1);
        this.root = new SuffixTreeNode(-1, new End(-1)); // Root edge initialized with dummy values
        this.activeNode = root;

        build();
    }

    /**
     * Main building loop: iterates through each character of the string
     * and calls extendTree to process suffix extensions
     */
    private void build() {
        for (int i = 0; i < text.length(); i++) {
            extendTree(i);
        }
        // After building the tree, assign suffix indices to leaves via DFS
        setSuffixIndexByDFS(root, 0);
    }

    /**
     * Core method to extend the suffix tree at position pos in the text
     * Implements Ukkonen’s algorithm rules:
     * - Adds new leaves or splits edges
     * - Updates active point and suffix links for efficient construction
     *
     * @param pos Current position in the text being processed
     */
    private void extendTree(int pos) {
        leafEnd.value = pos;         // Extend all leaves to include new character
        remainingSuffixCount++;      // We have one more suffix to add in this phase
        lastNewNode = null;          // Reset internal node pointer for suffix links

        while (remainingSuffixCount > 0) {
            if (activeLength == 0) activeEdge = pos;  // Start from current character if no active length

            char currentChar = text.charAt(activeEdge);
            SuffixTreeNode next = activeNode.children.get(currentChar);

            if (next == null) {
                // No edge starting with currentChar from activeNode: create leaf edge (Rule 2)
                SuffixTreeNode leaf = new SuffixTreeNode(pos, leafEnd);
                activeNode.children.put(currentChar, leaf);

                // If there is an internal node waiting for suffix link, link it to activeNode
                if (lastNewNode != null) {
                    lastNewNode.suffixLink = activeNode;
                    lastNewNode = null;
                }
            } else {
                // There is an outgoing edge from activeNode starting with currentChar

                // Check if activeLength goes beyond this edge length — if so, walk down
                int edgeLength = next.edgeLength();
                if (activeLength >= edgeLength) {
                    activeEdge += edgeLength;
                    activeLength -= edgeLength;
                    activeNode = next;
                    continue;   // Walk down and continue extension in new activeNode
                }

                // Check the next character on the edge for match/mismatch
                char nextCharOnEdge = text.charAt(next.start + activeLength);
                if (nextCharOnEdge == text.charAt(pos)) {
                    // Current character matches the character on the edge (Rule 3)
                    // No new node created, just increase activeLength and stop extension here
                    if (lastNewNode != null && activeNode != root) {
                        lastNewNode.suffixLink = activeNode;
                        lastNewNode = null;
                    }
                    activeLength++;
                    break;  // Extension ends here
                }

                // Mismatch found - need to split edge (Rule 2)
                int splitEnd = next.start + activeLength - 1;
                SuffixTreeNode split = new SuffixTreeNode(next.start, new End(splitEnd));
                activeNode.children.put(currentChar, split);

                // Create a new leaf node for the current character
                SuffixTreeNode leaf = new SuffixTreeNode(pos, leafEnd);
                split.children.put(text.charAt(pos), leaf);

                // Adjust existing child edge's start index
                next.start += activeLength;
                split.children.put(text.charAt(next.start), next);

                // Update suffix link for last created internal node, if exists
                if (lastNewNode != null) {
                    lastNewNode.suffixLink = split;
                }

                lastNewNode = split;
            }

            // One suffix extension done
            remainingSuffixCount--;

            // Move active point appropriately
            if (activeNode == root && activeLength > 0) {
                activeLength--;
                activeEdge = pos - remainingSuffixCount + 1;
            } else if (activeNode != root) {
                activeNode = activeNode.suffixLink != null ? activeNode.suffixLink : root;
            }
        }
    }

    /**
     * Searches for a pattern in the suffix tree.
     *
     * @param pattern The substring to search for.
     * @return List of starting positions where the pattern is found, sorted ascending.
     */
    public List<Integer> search(String pattern) {
        List<Integer> result = new ArrayList<>();
        if (pattern == null || pattern.length() == 0) {
            // For empty pattern, return empty list (or define your expected behavior)
            return result;
        }
        SuffixTreeNode currentNode = root;
        int i = 0; // Index in pattern

        // Traverse the suffix tree following the pattern characters
        while (i < pattern.length()) {
            char currentChar = pattern.charAt(i);
            SuffixTreeNode nextNode = currentNode.children.get(currentChar);

            if (nextNode == null) {
                // Pattern character not found at this node, so pattern doesn’t exist
                return result;  // Empty list indicates no match
            }

            // Determine edge length
            int edgeLength = nextNode.edgeLength();

            // Compare characters on edge to pattern substring
            int j = 0;
            while (j < edgeLength && i < pattern.length()) {
                if (text.charAt(nextNode.start + j) != pattern.charAt(i)) {
                    // Mismatch - pattern not found
                    return new ArrayList<>();
                }
                j++;
                i++;
            }

            currentNode = nextNode; // Move to next node as pattern matched on this edge
        }

        // Pattern fully matched, collect all suffix indices under currentNode
        collectLeaves(currentNode, result);

        Collections.sort(result);  // Sort results for consistent ordering
        return result;
    }

    /**
     * Helper method to recursively collect suffix start indices from all leaf nodes in the subtree.
     *
     * @param node Current node to traverse.
     * @param result List to store found starting indices.
     */
    private void collectLeaves(SuffixTreeNode node, List<Integer> result) {
        if (node == null) return;

        if (node.suffixIndex != -1) {
            // Leaf node: Add suffix start index to result list
            result.add(node.suffixIndex);
            return;
        }

        // Internal node: Recursively collect for all children
        for (SuffixTreeNode child : node.children.values()) {
            collectLeaves(child, result);
        }
    }

    /**
     * After building the suffix tree, computes and sets suffix indices for all leaves.
     *
     * @param node The current node in DFS traversal.
     * @param labelHeight Length of the path label from root to this node.
     */
    private void setSuffixIndexByDFS(SuffixTreeNode node, int labelHeight) {
        if (node == null) return;

        if (node.children.isEmpty()) {
            // Leaf node: suffix index is text length minus the label height
            node.suffixIndex = text.length() - labelHeight;
            return;
        }

        for (SuffixTreeNode child : node.children.values()) {
            int edgeLen = child.edgeLength();
            setSuffixIndexByDFS(child, labelHeight + edgeLen);
        }
    }
}
