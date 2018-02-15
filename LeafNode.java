import java.util.ArrayList;

/*
LeafNodes are taken care of in this class
Handles new key,value pair insert
If tree is empty, inserts to root
Finds the correct leaf node to insert in the tree to maintain the B+ tree
Inserts the new key in ascending order in the suitable leaf node
Checks if key already exists in the tree, if yes, appends the value to current key for further usage when searching the key with multiple values.
 */

class LeafNode extends BranchNode
{
    LeafNode left, right;
    ArrayList<LeafKey> leafKeys;

    /**
     * @param leftLeafNode
     * @param rightLeafNode
     */
    public LeafNode(LeafNode leftLeafNode, LeafNode rightLeafNode) {
        isLeafNode=true;
        leafKeys=new ArrayList<LeafKey>();
        left=leftLeafNode;
        right=rightLeafNode;

    }

    /*
    Method to insert new node
     */

    /**
     * @param key
     * @param val
     */
    public void insertNode(float key, String val) {
        boolean duplicateKeyExists = false;
        int i = 0;

        //Check key size
        while(i < this.leafKeys.size()) {
            if(this.leafKeys.get(i).key == key){
                //Append to existing value if duplicate key exists
                leafKeys.get(i).data = leafKeys.get(i).data+" "+val;
                duplicateKeyExists=true;
            }
            i++;
        }
        if(duplicateKeyExists == false) {
            //Add normally if duplicate value does not exist
            LeafKey newLeafKey= new LeafKey(val,key);
            LeafKey.AddLeafKey(this.leafKeys,newLeafKey,this);
        }
    }

     /*
    Method to find if key already exists
    */

    /**
     * @param key
     * @return
     */
    public boolean keyExists(float key){
        for(int i=0; i < this.leafKeys.size(); i++) {
            if(this.leafKeys.get(i).key == key)// duplicate key
                return true;
        }
        return false;
    }

    /*
    Method to find the correct leafNode for the new key
    A traceback ArrayList keeps track of the visited nodes
     */

    /**
     * @param key
     * @param root
     * @return
     */

    public static LeafNode findLeafNode(float key, BranchNode root) {
        treesearch.traceback = new ArrayList<BranchNode>();
        treesearch.traceback.add(0,root);
        if(root.isLeafNode)
            return (LeafNode)root;

        //DummyNode for traversing the tree and finding suitable place to insert
        BranchNode dummyNode = new BranchNode(root);
        while( dummyNode != null && dummyNode.isLeafNode == false) {
            boolean keyFound=false;
            int i=0;
            while(i < dummyNode.Keys.size()) {
                if(dummyNode.Keys.get(i).key > key) {
                    keyFound = true;
                    dummyNode = dummyNode.Keys.get(i).left;
                    break;
                }
                i++;
            }

            if(keyFound == false)
                dummyNode = dummyNode.Keys.get(dummyNode.Keys.size()-1).right;
            //add the dummy node into traceback arrayList
            treesearch.traceback.add(0,dummyNode);
        }
        return (LeafNode)dummyNode;
    }
}