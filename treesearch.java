import java.io.*;
import java.util.*;

/*
Class with main method. Takes input from a file and outputs to a file.
Reads source file from command line arguments
Outputs single key and range search results to file output.txt
If the degree is <3, does not initialize the tree and quits
 */
class treesearch
{
    public static int degree=0;
    public static ArrayList<BranchNode> traceback;
    public static BranchNode root;

    public static void main(String args[])throws IOException
    {
        traceback=new ArrayList<BranchNode>();

        if (args.length > 0) {

            String filename = args[0];
            File file = new File(filename);

            PrintWriter writer = new PrintWriter("output.txt", "UTF-8");
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            while ((line = br.readLine()) != null) {
                //Read degree
                if (line.length() < 2) {
                    if(Integer.parseInt(line) >= 3){ //Check for valid degree
                        initialize();
                        treesearch.degree = Integer.parseInt(line);
                    }
                    else{
                        writer.println("Degree less than 3 can't be B plus tree");
                        break;
                    }
                }

                String command = line.split("\\(")[0];

                //Insert
                if(command.equals("Insert") || command.equals("insert")){
                    float key = Float.parseFloat(line.split(",")[0].split("\\(")[1]);
                    String value =line.split(",")[1].split("\\)")[0];
                    LeafNode leafTreeNode = LeafNode.findLeafNode(key,root);
                    leafTreeNode.insertNode(key,value);
                }

                //Range search
                else if(line.contains("Search") && line.contains(",")) {
                    Float firstKey = Float.parseFloat(line.split("\\(")[1].split(",")[0]);
                    Float secondKey = Float.parseFloat(line.split("\\(")[1].split(",")[1].split("\\)")[0]);
                    LeafNode leafNode1 =LeafNode.findLeafNode(firstKey,root);
                    String rangeSearchString = Search.rangeSearch(leafNode1, firstKey, secondKey);
                    if(rangeSearchString != null)
                        writer.println(rangeSearchString);
                    else
                        writer.println("null");

                }

                //Single key search
                else if(command.equals("Search") || command.equals("search")){
                    float key = Float.parseFloat(line.split("\\(")[1].split("\\)")[0]);
                    LeafNode leafNode = LeafNode.findLeafNode(key,root);
                    String singleSearchString = Search.singleKeySearch(leafNode, key);
                    if(singleSearchString != null)
                        writer.println(singleSearchString);
                    else
                        writer.println("null");
                }
            }
            br.close();
            writer.close();
        }
    }

    //Initialize the tree when valid degree
    public static void initialize(){
        root= new LeafNode(null,null);
    }

}






