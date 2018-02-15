import java.util.ArrayList;
import java.util.Collections;
/*
Leafkey is the key in the leaf node.
Extends Key class
Checks if split is required in the leafnode after insertion or not
*/
class LeafKey extends BranchKey
{
    String data;

    /**
     * @param dataVal
     * @param keyVal
     */
    public LeafKey(String dataVal,float keyVal)
    {
        super(keyVal, null, null);
        data = dataVal;
    }


    /*
    Method to add the leafKey to correct position in sorted order in the leafNode.
    In case a split is required when the node is full, split is done and keys are managed
     */

    /**
     * @param leafKeys
     * @param newLeafKey
     * @param leafTreeNode
     */
    public static void AddLeafKey(ArrayList<LeafKey> leafKeys, LeafKey newLeafKey, LeafNode leafTreeNode)
    {
        leafKeys.add(newLeafKey);

        //sort to place at the correct location
        int i = leafKeys.size()-1;
        while(i >= 0) {
            if(leafKeys.get(i).key > newLeafKey.key){
                Collections.swap(leafKeys,i+1,i);
            }
            i--;
        }
        //Condition for checking if split is required.
        if(isSplitRequired(leafKeys)) {
            BranchNode splitNode = new BranchNode();// New node created that is not leafKeyNode

            int leafKeySize= leafKeys.size();
            int leftKeySize= (int)(Math.ceil(((float)leafKeySize)/((float)2)))-1;

            ArrayList<LeafKey> leftLeafKeys = new ArrayList<LeafKey>();
            ArrayList<LeafKey> rightLeafKeys = new ArrayList<LeafKey>();

            LeafNode rightLeafNode = new LeafNode(leafTreeNode,leafTreeNode.right);// new right leaf node
            leafTreeNode.right=rightLeafNode;// assigning new rightLeafNode to previous node as it splits into two

            i =0;
            while(i < leafKeySize)
            {
                if(i < leftKeySize)
                    leftLeafKeys.add(leafKeys.get(i));
                else
                    rightLeafKeys.add(leafKeys.get(i));
                i++;
            }

            LeafNode LastNode = (LeafNode) treesearch.traceback.get(0);
            LastNode.leafKeys = leftLeafKeys;
            rightLeafNode.leafKeys = rightLeafKeys;

            BranchKey splitKey = new BranchKey(rightLeafKeys.get(0).key,null,rightLeafNode);

            //adding the splitKey to splitNode
            splitNode.Keys.add(splitKey);
            BranchNode.splitAdjustNode(splitNode, treesearch.traceback);
        }

    }

     /*
    Method to check if a split is required.
     */

    /**
     * @param leafKeys
     * @return
     */
    public static boolean isSplitRequired(ArrayList<LeafKey> leafKeys){
        return leafKeys.size() >= treesearch.degree;
    }

}
