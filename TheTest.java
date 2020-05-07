import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

public class TheTest {
    static int number = 0;
    private static void createAnInputSet(AATree<Integer> t, Path p, Path p2) throws IOException {
        Files.write(p,("\nset = " + number + "\n").getBytes(), StandardOpenOption.APPEND);
        long times[] = new long[6];
        int n = (int) (Math.random()*9900)+100;
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = (int)(Math.random()*100000)+10;
        }
        for (int i = 0; i < n/2-1; i++) {
            t.insert(array[i]);
        }
        long startTime = System.nanoTime();
        t.insert(array[n/2-1]);
        long endTime = System.nanoTime();
        times[0]= (endTime-startTime);

        startTime = System.nanoTime();
        t.delete(t.getRoot().getElement());
        endTime = System.nanoTime();
        times[1]=endTime-startTime;
        
        startTime = System.nanoTime();
        t.search(t.successor(t.getRoot()).getElement());
        endTime = System.nanoTime();
        times[2]=endTime-startTime;

        for (int i = n/2; i < n-1; i++) {
            t.insert(array[i]);
        }
        startTime = System.nanoTime();
        t.insert(array[n-1]);
        endTime = System.nanoTime();
        times[3]= (endTime-startTime);

        startTime = System.nanoTime();
        t.delete(t.getRoot().getElement());
        endTime = System.nanoTime();
        times[4]=endTime-startTime;

        startTime = System.nanoTime();
        t.search(t.successor(t.getRoot()).getElement());
        endTime = System.nanoTime();
        times[5]=endTime-startTime;

        for (int a: array){
            Files.write(p,(a+" ,").getBytes(), StandardOpenOption.APPEND);
        }
        Files.write(p,("\nn = " + n).getBytes(), StandardOpenOption.APPEND);
        Files.write(p2,("\n" + n).getBytes(), StandardOpenOption.APPEND);
        Files.write(p,("\n" + Arrays.toString(times).replace("[","").replace("]","")).getBytes(), StandardOpenOption.APPEND);
        Files.write(p2,(", " + Arrays.toString(times).replace("[","").replace("]","")).getBytes(), StandardOpenOption.APPEND);
        Files.write(p,("\nh = " + t.getRoot().getLevel()).getBytes(), StandardOpenOption.APPEND);
    }
    public static void main(String[] args) {
        AATree<Integer> t = new AATree<>();
        try {
            for (int i = 0; i < 54 ; i++) {
                number++;
                Files.write(Paths.get("C:\\Users\\GalievBulat\\Documents\\Algs\\dataDescription.txt"),("\ninsertion in a half sized tree; deletion in a half sized tree; searching in a half sized tree;" +
                                " insertion in full tree; deletion in a full tree; searching in a full tree: \n").getBytes(), StandardOpenOption.APPEND);
                createAnInputSet(t, Paths.get("C:\\Users\\GalievBulat\\Documents\\Algs\\dataDescription.txt"),Paths.get("C:\\Users\\GalievBulat\\Documents\\Algs\\data.txt"));
                t.clean();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
