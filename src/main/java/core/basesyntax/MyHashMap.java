package core.basesyntax;

public class MyHashMap<K, V> implements MyMap<K, V> {
    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;
    private int size;
    private Node[] nodes;

    public MyHashMap() {
        Node[] nodes = new Node[DEFAULT_INITIAL_CAPACITY];
    }

    @Override
    public void put(K key, V value) {
        Node<K, V> newNode = createNewNode(key, value);
        int hashCodeOfNewNode = newNode.hashCode();
        int indexOfKey = hashCodeOfNewNode % nodes.length;
        ensureCapacity(size + 1);
        if (nodes[indexOfKey] != null) {
            Node<K,V> current = nodes[indexOfKey];
            while (nodes[indexOfKey].next != null){
                current=current.next;
            }
            current.next = newNode;
        } else {
            nodes[indexOfKey] = newNode;
        }
        size++;
    }

    @Override
    public V getValue(K key) {
        for (Node node : nodes) {
            Node<K,V> current = node;
            while (current != null) {
                if (current.kay.equals(key)) {
                    return current.value;
                }
                current = current.next;
            }
        }
        return null;
    }

    @Override
    public int getSize() {
        return size;
    }

    private Node createNewNode(K kay, V value) {
        return new Node(kay, value);
    }

    private void ensureCapacity(int minCapacity) {
        if (minCapacity > nodes.length * DEFAULT_LOAD_FACTOR) {
            resize();
        }
        return;
    }

    private void resize() {
        int[] newNodes = new int[nodes.length * 2];
        for (int i = 0; i < nodes.length; i++) {
            Node<K,V> current = nodes[i];
            while (current != null) {
                newNodes.put(current);
                current = current.next;
            }
        }
    }

    private class Node<K, V> {
        private int hashCode;
        private Node next;
        private K kay;
        private V value;

        public Node(K kay, V value) {
            this.kay = kay;
            this.value = value;
            next = null;
        }

        public int hashKode() {
            int hashCode = 17;
            hashKode = hashKode * 31 + kay.hashCode();
            this.hashCode = hashCode;
            return hashCode;
        }
    }
}
