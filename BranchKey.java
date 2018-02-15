/*
TreeKey is the generic key class for all nodes.
Branch Nodes have key and value and left and right pointer to their children
Leaf Nodes dont have pointer to their parent.
*/
class BranchKey
{
    double key;
    BranchNode left,right;

    /**
     * @param keyValue
     * @param leftNode
     * @param rightNode
     */
    public BranchKey(double keyValue, BranchNode leftNode, BranchNode rightNode) {
        key=keyValue;
        left=leftNode;
        right=rightNode;
    }

    /**
     * @param key
     */
    public BranchKey(double key){
        this.key = key;
    }
}