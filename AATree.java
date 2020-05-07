import javafx.util.Pair;

import java.util.NoSuchElementException;

public class AATree<T extends Comparable<T>>{
    private Node<T> root;
    private Node<T> skew(Node<T> n){
        if(n!= null && n.getLeftNode() != null && n.getLeftNode().getLevel() == n.getLevel()){
            return new Node<T>(n.getLeftNode().getElement(),n.getLevel(),n.getLeftNode().getLeftNode(),
                    new Node<T>(n.getElement(),n.getLevel(),n.getLeftNode().getRightNode(),n.getRightNode()));
        } else
            return n;
    }
    private Node<T> split(Node<T> n){
        if (n != null && n.getRightNode() != null && n.getRightNode().getRightNode()!= null
                && n.getLevel() == n.getRightNode().getRightNode().getLevel()){
                    return new Node<T>(n.getRightNode().getElement(),(byte)(n.getLevel()+1),
                            new Node<T>(n.getElement(),n.getLevel(),n.getLeftNode(),n.getRightNode().getLeftNode()),
                            n.getRightNode().getRightNode());
        } else
            return n;
    }

    public void insert(T x){
        root =  insert(x, root);
    }
    private Node<T> insert(T x,Node<T> t){
        if (t == null){
            return new Node<T>(x,(byte) 1,null,null);
        } else if (x.compareTo(t.getElement())<0){
            t.setLeftNode(insert(x,t.getLeftNode()));
        } else if (x.compareTo(t.getElement())>0){
            t.setRightNode(insert(x,t.getRightNode()));
        }
        t = skew(t);
        t = split(t);
        return t;
    }
    public Node<T> predecessor(Node<T> n){
        if (n == null || n.getLeftNode() == null){
            return null;
        }
        n = n.getLeftNode();
        while (n.getRightNode()!=null){
            n = n.getRightNode();
        }
        return n;
    }
    public Node<T> successor(Node<T> n){
        if (n == null || n.getRightNode() == null){
            return null;
        }
        n = n.getRightNode();
        while (n.getLeftNode()!=null){
            n = n.getLeftNode();
        }
        return n;
    }
    private Node<T> decreaseLevel(Node<T> t){
        int min = 0;
        if (t == null) {
            return null;
        } else if (t.getRightNode() == null || t.getLeftNode() == null){
            min = 1;
        } else
            min = Math.min(t.getLeftNode().getLevel(),t.getRightNode().getLevel())+1;
        if (min < t.getLevel()){
            t.setLevel((byte)min);
            if (t.getRightNode()!= null && min<t.getRightNode().getLevel()){
                t.getRightNode().setLevel((byte) min);
            }
        }
        return t;
    }
    public void delete(T x){
        root = delete(x,root);
    }
    private Node<T> delete(T x, Node<T> t){
        if (t == null){
            return null;
        } else if (x.compareTo(t.getElement())<0){
            t.setLeftNode(delete(x,t.getLeftNode()));
        } else if (x.compareTo(t.getElement())>0){
            t.setRightNode(delete(x,t.getRightNode()));
        } else {
            if (t.getRightNode() == null && t.getLeftNode() == null) {
                return null;
            //} else if (t.getRightNode() != null) {
            } else if (t.getLeftNode() == null) {
                Node<T> l = successor(t);
                t.setRightNode(delete(l.getElement(), t.getRightNode()));
                t.setElement(l.getElement());
            } else {
                Node<T> l = predecessor(t);
                t.setLeftNode(delete(l.getElement(), t.getLeftNode()));
                t.setElement(l.getElement());
            }
        }
        t = decreaseLevel(t);
        t = skew(t);
        t.setRightNode(skew(t.getRightNode()));
        if (t.getRightNode()!= null){
            t.getRightNode().setRightNode(skew(t.getRightNode().getRightNode()));
        }
        t = split(t);
        t.setRightNode(split(t.getRightNode()));
        return t;
    }
    public Node<T> search(T x){
        return search(x,root);
    }
    private Node<T> search(T x,Node<T> t){
        if (t == null){
            return null;
        } else if (x.compareTo(t.getElement())<0){
            return search(x,t.getLeftNode());
        } else if (x.compareTo(t.getElement())>0){
            return search(x,t.getRightNode());
        } else
            return t;
    }
    public void clean(){
        root = null;
    }
    public Pair<T,T> findTwoBordering(T x){
        return findTwoBordering(root,x);
    }
    private Pair<T,T> findTwoBordering(Node<T> t,T x) {
        if (root == null || root.getLeftNode() == null && root.getRightNode() == null) {
            throw new NoSuchElementException();
        } else if (t == null) {
            return null;
        } else {
            if (x.compareTo(t.getElement()) > 0) {
                Pair<T,T> pair = findTwoBordering(t.getRightNode(), x);
                if (pair == null) {
                    pair = new Pair<>(t.getElement(), null);
                } else if (pair.getKey() == null && pair.getValue() != null) {
                    pair = new Pair<>(t.getElement(),pair.getValue());
                }
                return pair;
            } else if (x.compareTo(t.getElement()) < 0) {
                Pair<T,T> pair = findTwoBordering(t.getLeftNode(), x);
                if (pair == null) {
                    pair = new Pair<>(null, t.getElement());
                } else if (pair.getKey() != null && pair.getValue() == null) {
                    pair = new Pair<>(pair.getKey(),t.getElement());
                }
                return pair;
            } else {
                if (t.getLeftNode() != null && t.getRightNode() != null) {
                    return new Pair<>(predecessor(t).getElement(), successor(t).getElement());
                } else if (t.getLeftNode() == null && t.getRightNode() == null) {
                    return null;
                } else if (t.getLeftNode() == null){
                    return new Pair<>(null,successor(t).getElement());
                } else
                    return new Pair<>(predecessor(t).getElement(),null);
            }
        }
    }
    public void printSideways(){
        printSideways(root,root.getLevel());
    }
    private void printSideways(Node<T> start,int d){
        if (start != null) {
            if (start.getLeftNode()!= null)
                printSideways(start.getLeftNode(),start.getLeftNode().getLevel());
            for (int i = 1;i<d;i++)
                System.out.print("\t");
            System.out.print(start.getElement());
            System.out.println();
            if (start.getRightNode()!= null)
                printSideways(start.getRightNode(),start.getRightNode().getLevel());
        }
    }
    public Node<T> getRoot() {
        return root;
    }
}