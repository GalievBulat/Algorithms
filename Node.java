public class Node<T> {
    private T element;
    private byte level;
    private Node<T> leftNode;
    private Node<T> rightNode;
    public Node(T el,byte dep,Node<T> l,Node<T> r){
        element = el;
        level = dep;
        leftNode = l;
        rightNode = r;
    }

    protected Node<T> getLeftNode() {
        return leftNode;
    }

    protected byte getLevel() {
        return level;
    }

    protected Node<T> getRightNode() {
        return rightNode;
    }

    protected T getElement() {
        return element;
    }

    protected void setRightNode(Node<T> rightNode) {
        this.rightNode = rightNode;
    }

    protected void setLevel(byte level) {
        this.level = level;
    }

    protected void setLeftNode(Node<T> leftNode) {
        this.leftNode = leftNode;
    }

    protected void setElement(T element) {
        this.element = element;
    }
}
