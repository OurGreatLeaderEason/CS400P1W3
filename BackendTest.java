
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class BackendTest {

    /**
     * Tests getStatisticsString() method
     * @return true if implemented correctly, false if otherwise
     */
    public static boolean test1(){
        CHSearchBackendBD back=new CHSearchBackendBD(new HashtableWithDuplicateKeysDB<String,PostInterface>(), new PostReaderDW());
        if(!back.getStatisticsString().equals("Total number of posts: 0")){
            return false;
        }
        return true;
    }

    /**
     * tests findPostsByTitleWords
     * @return true if implemented correctly, false if otherwise
     * @throws FileNotFoundException
     */
    public static boolean test2() throws FileNotFoundException{
        HashtableWithDuplicateKeysAE<String, PostInterface> database=new HashtableWithDuplicateKeysAE<String, PostInterface>(100);
        CHSearchBackendBD back=new CHSearchBackendBD(database, new PostReaderDW());
        back.loadData("test.txt");
        
        if(back.findPostsByTitleWords("[John Cena]").size()!=0){
            return false;
        }
        
        return true;
    }

    /**
     * tests findPostsByBodyWords
     * @return true if implemented correctly, false if otherwise
     */
    public static boolean test3(){
        HashtableWithDuplicateKeysAE<String, PostInterface> database=new HashtableWithDuplicateKeysAE<String, PostInterface>(100);
        CHSearchBackendBD back=new CHSearchBackendBD(database, new PostReaderDW());
        if(back.findPostsByBodyWords("[John Cena]").size()!=0){
            return false;
        }
        return true;
    }

    /**
     * tests findPostsByTitleOrBodyWords
     * @return true if implemented correctly, false if otherwise
     */
    public static boolean test4(){
        HashtableWithDuplicateKeysAE<String, PostInterface> database=new HashtableWithDuplicateKeysAE<String, PostInterface>(100);
        CHSearchBackendBD back=new CHSearchBackendBD(database, new PostReaderDW());
        if(back.findPostsByTitleOrBodyWords("[John Cena]").size()!=0){
            return false;
        }
        return true;
    }

    /**
     * test loadData()
     * @return true if implemented correctly, false if otherwise
     */
    public static boolean test5(){
        try{
            HashtableWithDuplicateKeysAE<String, PostInterface> database=new HashtableWithDuplicateKeysAE<String, PostInterface>(100);
            CHSearchBackendBD back=new CHSearchBackendBD(database, new PostReaderDW());
            back.loadData("fake file");
        }
        catch(FileNotFoundException e){
            return true;
        }
        catch(Exception e){
            return false;
        }
        return false;
    }

    /**
     * Tests the implementation of loadData() and getStatisticString() with DW and AE codes
     * @return
     */
    public static boolean integrationTest1(){
        try{
            HashtableWithDuplicateKeysAE<String, PostInterface> database=new HashtableWithDuplicateKeysAE<String, PostInterface>(100);
            CHSearchBackendBD back=new CHSearchBackendBD(database, new PostReaderDW());
            back.loadData("test.txt");
            if(!back.getStatisticsString().equals("Total number of posts: 2")){
                return false;
            }
            return true;
        }
        catch(Exception e){
            return false;
        }

    }

    /**
     * Tests the implementation of loadData() and findPostsBy methods with DW and AE code
     * @return true if implemented correctly, false if otherwise
     */
    public static boolean integrationTest2(){
        try{
            //test find post by title
            HashtableWithDuplicateKeysAE<String, PostInterface> database=new HashtableWithDuplicateKeysAE<String, PostInterface>(100);
            CHSearchBackendBD back=new CHSearchBackendBD(database, new PostReaderDW());
            back.loadData("test.txt");
            if(!back.findPostsByTitleWords("pikachu").get(0).equals("Pikachu I choose you")){
                return false;
            }

            //test find post by words
            if(!back.findPostsByBodyWords("pikachu").get(0).equals("Pikachu I choose you")){
                return false;
            }

            List<String> title=back.findPostsByTitleOrBodyWords("Soups");
            if(!title.contains("Pikachu I choose you") || !title.contains("Soups and chilis?")){
                return false;
            }
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

    /**
     * tests mainMenuPrompt()
     * @return true if implemented correctly, false if otherwise
     */
    public static boolean partnerTest1(){

        try {
            HashtableWithDuplicateKeysAE<String, PostInterface> database=new HashtableWithDuplicateKeysAE<String, PostInterface>(100);
            CHSearchBackendBD back=new CHSearchBackendBD(database, new PostReaderDW());
            back.loadData("test.txt");
            CHSearchFrontendFD front=new CHSearchFrontendFD(new Scanner(System.in), back);
            char a=front.mainMenuPrompt();
            if(a=='L' || a=='Q' || a=='T' || a=='B' || a=='P' || a=='S'){
                return true;
            }
            return false;
        } catch (FileNotFoundException e) {
            return false;
        }
  
    }

    /**
     * Tests the chooseSearchWordsPrompt() method
     * @return true if implemented correctly, false if otherwise
     */
    public static boolean partnerTest2(){
        try {
            HashtableWithDuplicateKeysAE<String, PostInterface> database=new HashtableWithDuplicateKeysAE<String, PostInterface>(100);
            CHSearchBackendBD back=new CHSearchBackendBD(database, new PostReaderDW());
            back.loadData("test.txt");
            CHSearchFrontendFD front=new CHSearchFrontendFD(new Scanner(System.in), back);
            List<String> words=front.chooseSearchWordsPrompt();
            if(!words.contains("John") || !words.contains("Cena")){
                return false;
            }
            return true;
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            return false;
        }
       
    }

    public static void main(String[] args) throws FileNotFoundException{
        System.out.println("test1: "+test1());
        System.out.println("test2: "+test2());
        System.out.println("test3: "+test3());
        System.out.println("test4: "+test4());
        System.out.println("test5: "+test5());
        System.out.println("Backend Integration Test1: "+integrationTest1());
        System.out.println("Backend Integration Test2: "+integrationTest2());
        System.out.println("Backend parter Frontend test1: "+partnerTest1());
        System.out.println("Backend parter Frontend test2: "+partnerTest2());
    }
}

