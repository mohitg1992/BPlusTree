import java.util.ArrayList;

/*
Branch nodes are taken care of in this class
Checks whether after insert and leaf node splitting, is branch node splitting required
Splits the overfull branch nodes when needed
Manages the left and right pointers of the branch node keys
*/

class BranchNode {

    boolean isLeafNode = false;
    ArrayList<BranchKey> Keys;

    /**
     * @param branchNode
     */
    public BranchNode(BranchNode branchNode) {
        this.isLeafNode = branchNode.isLeafNode;
        this.Keys = branchNode.Keys;

    }

    //Constructor
    public BranchNode() {
        isLeafNode = false;
        Keys = new ArrayList<BranchKey>();
    }

    /*
    Method to split and adjust the nodes by bottom up approach.
     */

    /**
     *
     * @param newBranchNode
     * @param traceback
     */

    public static void splitAdjustNode(BranchNode newBranchNode, ArrayList<BranchNode> traceback) {
        BranchNode detachedChildNode = traceback.get(0);
        traceback.remove(0);
        int parentKeySize;

        //Traceback will check how many nodes traversed down
        if (traceback.size() == 0){
            //Parent does not exist
            newBranchNode.Keys.get(0).left = detachedChildNode;
            treesearch.root = newBranchNode;
        }

        else {
            BranchNode updatedParent = adjustParentNode(traceback.get(0), newBranchNode);
            if (isSplitRequired(updatedParent.Keys))
            {
                BranchNode middleNode = new BranchNode();
                parentKeySize = updatedParent.Keys.size();
                int leftListSize = (int) (Math.ceil((float) (parentKeySize) / (float) 2) - 1);
                int rightListSize = (int) (Math.ceil((float) (parentKeySize) / (float) 2));
                //Create new arraylists for left and right node after split
                ArrayList<BranchKey> leftNodeList = new ArrayList<>(updatedParent.Keys.subList(0, leftListSize));
                ArrayList<BranchKey> rightNodeList = new ArrayList<>(updatedParent.Keys.subList(rightListSize, parentKeySize));

                BranchKey newParentKey = updatedParent.Keys.get((int) (Math.ceil((float) parentKeySize / (float) 2) - 1));
                middleNode.Keys.add(newParentKey);
                BranchNode newRightNode = new BranchNode();

                fixMiddleKeyPointers(newRightNode, updatedParent, leftNodeList, rightNodeList, newParentKey);
                splitAdjustNode(middleNode, traceback);
            }

        }
    }

    /*
    Method to adjust the pointers in parent node after a split.
     */

    /**
     * @param branchNode
     * @param singleKeyNode
     * @return
     */
    public static BranchNode adjustParentNode(BranchNode branchNode, BranchNode singleKeyNode)
    {
        BranchKey singleKey = singleKeyNode.Keys.get(0);
        int i=0;
        while(i < branchNode.Keys.size()){
            //Find the correct position and pointers for singleKeyNode
            if (branchNode.Keys.get(i).key > singleKey.key) {
                singleKey.left = branchNode.Keys.get(i).left;
                branchNode.Keys.get(i).left = singleKey.right;
                branchNode.Keys.add(i, singleKey);
                return branchNode;
            }
            i++;
        }
        branchNode.Keys.add(singleKey);
        singleKey.left = branchNode.Keys.get(branchNode.Keys.size() - 2).right;
        return branchNode;
    }

    /*
    Method to check if a split is required.
     */

    /**
     * @param treeNodeKeys
     * @return
     */
    public static boolean isSplitRequired(ArrayList<BranchKey> treeNodeKeys){
        return treeNodeKeys.size() >= treesearch.degree;
    }

    /*
    Method to adjust pointers of middleKey after the split
     */

    /**
     *
     * @param newRightNode
     * @param updatedParent
     * @param leftList
     * @param rightList
     * @param newParentKey
     */
    public static void fixMiddleKeyPointers(BranchNode newRightNode, BranchNode updatedParent, ArrayList<BranchKey> leftList, ArrayList<BranchKey> rightList, BranchKey newParentKey){
        newRightNode.Keys = rightList;
        updatedParent.Keys = leftList;
        newParentKey.left = null;
        newParentKey.right = newRightNode;
    }

}