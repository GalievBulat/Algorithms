public class TesTest {
    public static void main(String[] args) throws Exception {
        AATree<Integer> t = new AATree<>();
        t.insert(1);
        t.insert(2);
        t.insert(3);
        t.insert(4);
        t.insert(5);
        t.insert(6);
        t.insert(7);
        t.insert(8);
        t.insert(9);
        t.insert(10);
        t.insert(11);
        t.insert(12);
        t.insert(13);
        t.delete(3);
        t.delete(10);
        t.printSideways();
        System.out.println(t.findTwoBordering(9));
        //System.out.println(t.search(7).getRightNode().getElement());
    }

}
