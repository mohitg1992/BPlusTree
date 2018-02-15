/*
Searches for single key for input command search(key)
Searches for keys within a range for input command search(key1, key2)
Formats the range search result to comma separated pairs. [(key,value),(key1,value1)...] pairs
*/

public class Search {

     /*
    Method to perform single key search
    Returns null if the key is not present
     */

    /**
     * @param leafNode
     * @param key
     * @return
     */

    public static String singleKeySearch(LeafNode leafNode, float key){
        boolean keyFound=false;
        String singleSearchString ="";
        int i=0;
        while( i < leafNode.leafKeys.size())
        {   //If key found append the duplicate value
            if(leafNode.leafKeys.get(i).key == key)
            {
                singleSearchString = leafNode.leafKeys.get(i).data.replace(" ",",");
                keyFound = true;
                break;
            }
            i++;
        }
        if(keyFound == false)
            return null;
        else
            return singleSearchString;
    }
    /*
    Method to perform Search when 2 keys are provided.
    Search starts from the first leafNode and continues till secondKey >= currentKey
     */

    /**
     * @param leafNode
     * @param firstKey
     * @param secondKey
     * @return
     */
    public static String rangeSearch(LeafNode leafNode, float firstKey, float secondKey){

        boolean firstKeyPresent=false;
        boolean secondKeyPresent=false;

        //Append to a dummy string
        String rangeSearchString="";
        while(secondKeyPresent == false && leafNode != null) {
            for (int i = 0; i < leafNode.leafKeys.size(); i++) {
                if(firstKeyPresent == true || leafNode.leafKeys.get(i).key >= firstKey)
                {
                    if(leafNode.leafKeys.get(i).key > secondKey) {
                        secondKeyPresent = true;
                        break;
                    }
                    rangeSearchString= rangeSearchString + formatRangeSearch(leafNode.leafKeys.get(i))+",";
                    firstKeyPresent = true;
                }
            }
            leafNode = leafNode.right;
        }
        if(firstKeyPresent)
            return rangeSearchString.substring(0,rangeSearchString.length()-1);
        else
            return null;
    }



    /*
    Method to format the range search answer in the comma separated [(key1,value1),(key2,value2)...] format
     */

    /**
     * @param leafKey
     * @return
     */
    public static String formatRangeSearch(LeafKey leafKey)
    {
        String rangeSearch = "";
        String[] dataValues= leafKey.data.split(" ");
        for(int i=0; i < dataValues.length; i++)
        {
            //Need to take only 2 digits precision
            String format = ""+leafKey.key;
            String precisionString;

            if(format.contains(".")){
                int index = format.indexOf(".") + 2;
                if(format.length() - index >= 2)
                    precisionString = format.substring(0,index+1);
                else
                    precisionString = format.substring(0,index);

                rangeSearch = rangeSearch +"("+ precisionString + "," + dataValues[i]+"),";
            }
            else
                rangeSearch = rangeSearch+ "(" + format + "," + dataValues[i]+"),";
        }
        rangeSearch = rangeSearch.substring(0,rangeSearch.length()-1);
        return rangeSearch;
    }
}
