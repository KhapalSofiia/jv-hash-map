package core.basesyntax;

public class MyHashMap<K, V> implements MyMap<K, V> {
    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;
    private static final int RESIZE_MULTIPLIER = 2;
    private static final int HASH_MASK = 0x7fffffff;
    private static final int NULL_KEY_BUCKET = 0;
    private int size;
    private Node<K, V>[] nodes;

    public MyHashMap() {
        this.nodes = (Node<K, V>[]) new Node[DEFAULT_INITIAL_CAPACITY];
    }

    @Override
    public void put(K key, V value) {
        ensureCapacity(size + 1);
        int index = (key == null) ? NULL_KEY_BUCKET : (key.hashCode() & HASH_MASK) % nodes.length;
        if (nodes[index] == null) {
            nodes[index] = new Node<>(key, value);
            size++;
            return;
        }
        Node<K, V> current = nodes[index];
        while (true) {
            if ((key == null && current.key == null) || (key != null && key.equals(current.key))) {
                current.value = value;
                return;
            }
            if (current.next == null) {
                break;
            }
            current = current.next;
        }
        current.next = new Node<>(key, value);
        size++;
    }

    @Override
    public V getValue(K key) {
        int index = (key == null) ? NULL_KEY_BUCKET : (key.hashCode() & HASH_MASK) % nodes.length;
        Node<K, V> current = nodes[index];
        while (current != null) {
            if ((key == null && current.key == null) || (key != null && key.equals(current.key))) {
                return current.value;
            }
            current = current.next;
        }
        return null;
    }

    @Override
    public int getSize() {
        return size;
    }

    private void ensureCapacity(int minCapacity) {
        if (nodes.length * DEFAULT_LOAD_FACTOR < minCapacity) {
            resize();
        }
    }

    private void resize() {
        Node<K, V>[] newNodes = (Node<K, V>[]) new Node[nodes.length * RESIZE_MULTIPLIER];
        for (Node<K, V> node : nodes) {
            while (node != null) {
                Node<K, V> next = node.next;
                int index = (node.key == null
                        ? NULL_KEY_BUCKET : (node.key.hashCode() & HASH_MASK) % newNodes.length);
                node.next = newNodes[index];
                newNodes[index] = node;
                node = next;
            }
        }
        this.nodes = newNodes;
    }

    private class Node<K, V> {
        private Node<K, V> next;
        private K key;
        private V value;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            next = null;
        }
    }
}
