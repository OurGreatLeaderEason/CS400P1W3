import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Tester {

    public static void main(String[] args) throws FileNotFoundException{
        HashtableWithDuplicateKeysAE<String, PostInterface> database=new HashtableWithDuplicateKeysAE<String, PostInterface>(100);
        CHSearchBackendBD back=new CHSearchBackendBD(database, new PostReaderDW());
        CHSearchFrontendFD front=new CHSearchFrontendFD(new Scanner(System.in), back);
        back.loadData("test.txt");
        String input=front.chooseSearchWordsPrompt().toString();
        String[] inputs=back.splitWords(input);

        System.out.println(back.findPostsByBodyWords(input));




        /*
        for(int i=0; i<inputs.length; i++){
            System.out.println(back.findPostsByTitleWords(inputs[i]));
        }
        */


    }

}
