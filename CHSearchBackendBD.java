import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;


public class CHSearchBackendBD implements CHSearchBackendInterface{

    private int numPosts;
    private HashtableWithDuplicateKeysInterface database;
    private PostReaderInterface postReader;
    
    //make sure the hashtable is at least 10x bigger than the # of elements or this thing breaks :(
    public CHSearchBackendBD(HashtableWithDuplicateKeysInterface<String,PostInterface> hashtable, PostReaderInterface postReader){
        this.database=new HashtableWithDuplicateKeysAE<String, PostInterface>(100);
        this.postReader=postReader;
        numPosts=0;
    }

    /**
     * Loads data into the database field. This method splices titles and bodies into an array of Strings,
     * then store each word into the database. They keys are strings in the form of "title/body: word", and the values are the post objects
     * @param filename the name of the file :)
     */
    @Override
    public void loadData(String filename) throws FileNotFoundException {
        File file=new File(filename);
        if(!file.exists()){
            throw new FileNotFoundException("File cannot be found!");
        }
        List<PostInterface> posts=this.postReader.readPostsFromFile(filename);
        this.numPosts+=posts.size();
        for(int i=0; i<posts.size(); i++){
            String[] title=posts.get(i).getTitle().replaceAll("[^a-zA-Z0-9\\s]", "").split(" ");
            String[] body=posts.get(i).getBody().replaceAll("[^a-zA-Z0-9\\s]", "").split(" ");
            
            for(int j=0; j<title.length; j++){
                this.database.putOne(("title: "+title[j]).toLowerCase(), posts.get(i));
            }
            
            for(int k=0; k<body.length; k++){
                this.database.putOne("body: "+body[k].toLowerCase(), posts.get(i));
            }
        }
    }

    /**
     * Returns a list containing the title of the posts that contains the given word in its title
     * @param words all returned posts will contain this word in the title
     * @return a list containing the title of the posts that contains the given word in its title
     */
    @Override
    public List<String> findPostsByTitleWords(String words) {
        //turn our fake string array into a real array of Strings
        String[] terms=this.splitWords(words);
        List<String> posts=new ArrayList<String>();
        //loops through the array of Strings
        for(int i=0; i<terms.length; i++){
            List<String> liszt=this.searchPostByTitleWord(terms[i].toLowerCase());
            this.merge(posts, liszt);
        }
        return posts;
    }

    /**
     * Returns a list containing the title of the posts that contains the given word in its body
     * @param words all returned posts will contain this word in the body
     * @return a list containing the title of the posts that contains the given word in its body
     */
    @Override
    public List<String> findPostsByBodyWords(String words) {
        //turn our fake string array into a real array of Strings
        String[] terms=this.splitWords(words);
        List<String> posts=new ArrayList<String>();
        //loops through the array of Strings
        for(int i=0; i<terms.length; i++){
            List<String> liszt=this.searchPostByBodyWord(terms[i].toLowerCase());
            this.merge(posts, liszt);
        }
        return posts;
    }

    /**
     * Returns a list containing the title of the posts that contains the given word either in the title or body. If a post has the world in both the title and body, it is only counted once
     * @param all returned posts will either contain this word in the title or in the body
     * @return a list containing the title of the posts that contains the given word either in the title or body. If a post has the world in both the title and body, it is only counted once
     */
    @Override
    public List<String> findPostsByTitleOrBodyWords(String words) {
           
            List<String> titles=this.findPostsByTitleWords(words);
            List<String> bodies=this.findPostsByBodyWords(words);

            if(bodies==null && titles==null){
                return new ArrayList<String>();
            }
            else if(titles==null){
                return bodies;
            }
            else if(bodies==null){
                return titles;
            }
            else{
                HashSet<String> set = new HashSet<String>(titles);
                set.addAll(bodies);
                ArrayList<String> combinedList = new ArrayList<String>(set);
                return combinedList;
            }
         
        
       
    }

    /**
     * @return the interesting facts about the database
     */
    @Override
    public String getStatisticsString() {
        String st="";
        st+="Total number of posts: "+this.numPosts;
        return st;
    }

    /**
     * Private helper method that looks for only one word
     * @param words the word that we're looking ford
     * @return list of posts that contains the word in its body
     */
    private List<String> searchPostByBodyWord(String words){
        try{
            List<PostInterface> posts=(List<PostInterface>) this.database.get("body: "+words.toLowerCase());
            ArrayList<String> titles=new ArrayList<String>();
            for(int i=0; i<posts.size(); i++){
                titles.add(posts.get(i).getTitle());
            }
            return titles;
        }
        catch(NoSuchElementException e){
            return new ArrayList<String>();
        }      
    }

    /**
     * basically the inverse of the toString() method for Lists
     * @param input The Stringified List
     * @return A list of Strings
     */
    public String[] splitWords(String input) {
        // Remove the brackets at the beginning and end of the input string
        input = input.substring(1, input.length() - 1);
        // Split the remaining string at every comma
        String[] words = input.split(", ");
        // Trim any whitespace from each word
        for (int i = 0; i < words.length; i++) {
            words[i] = words[i].trim();
        }
        return words;
    }

    /**
     * Search Posts that contain the word in its title
     * @param words The word that we are looking for
     * @return posts the contains this word in its title
     */
    private List<String> searchPostByTitleWord(String words){
        try{
            List<PostInterface> posts=(List<PostInterface>) this.database.get("title: "+words.toLowerCase());
            ArrayList<String> titles=new ArrayList<String>();
            for(int i=0; i<posts.size(); i++){
                titles.add(posts.get(i).getTitle());
            }
            return titles;
        }
        catch(NoSuchElementException e){
            return new ArrayList<String>();
        }
    }
    
    /**
     * Merges 2 lists into 1, no duplicates
     * @param list1 list we want to merge into
     * @param list2 list we want to merge from
     */
    public void merge(List<String> list1, List<String> list2) {
        if (list1 == null) {
            list1 = new ArrayList<String>();
        }
    
        if (list2 == null) {
            list2 = new ArrayList<String>();
        }
    
        HashSet<String> set = new HashSet<String>(list1);
        set.addAll(list2);
        list1.clear();
        list1.addAll(set);
    }
}
