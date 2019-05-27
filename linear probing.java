import java.util.Scanner;

/** Class LinearProbingHashTable **/
class LinearProbingHashTable {
    private int maxSize;
    private String[] keys;
    private String[] vals;

    /** Constructor **/
    public LinearProbingHashTable(int capacity) {
        maxSize = capacity;
        keys = new String[maxSize];
        vals = new String[maxSize];
    }

    /** Fucntion to check if hash table contains a key **/
    public boolean contains(String key) {
        return get(key) != null;
    }

    /** Functiont to get hash code of a given key **/
    private int hash(String key) {
        return key.hashCode() % maxSize;
    }

    /** Function to insert key-value pair **/
    public void insert(String key, String val) {
        int tmp = hash(key);
        int i = tmp;
        do {
            if (keys[i] == null) {
                keys[i] = key;
                vals[i] = val;
                return;
            }
            if (keys[i].equals(key)) {
                vals[i] = val;
                return;
            }
            i = (i + 1) % maxSize;
        } while (i != tmp);
    }

    /** Function to get value for a given key **/
    public String get(String key) {
        int i = hash(key);
        while (keys[i] != null) {
            if (keys[i].equals(key))
                return vals[i];
            i = (i + 1) % maxSize;
        }
        return null;
    }

    /** Function to remove key and its value **/
    public void remove(String key) {
        if (!contains(key))
            return;

        /** find position key and delete **/
        int i = hash(key);
        while (!key.equals(keys[i]))
            i = (i + 1) % maxSize;
        keys[i] = vals[i] = null;

        /** rehash all keys **/
        for (i = (i + 1) % maxSize; keys[i] != null; i = (i + 1) % maxSize) {
            String tmp1 = keys[i], tmp2 = vals[i];
            keys[i] = vals[i] = null;
            insert(tmp1, tmp2);
        }
    }

    /** Function to print HashTable **/
    public void printHashTable() {
        System.out.println("\nHash Table: ");
        for (int i = 0; i < maxSize; i++)
            if (keys[i] != null)
                System.out.println(keys[i] + " " + vals[i]);
        System.out.println();
    }
}

/** Class LinearProbingHashTableTest **/
public class LinearProbingHashTableTest {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Hash Table Test\n\n");
        System.out.println("Enter size");
        /** maxSizeake object of LinearProbingHashTable **/
        LinearProbingHashTable lpht = new LinearProbingHashTable(scan.nextInt());

        char ch;
        /** Perform LinearProbingHashTable operations **/
        do {
            System.out.println("\nHash Table Operations\n");
            System.out.println("1. insert ");
            System.out.println("2. remove");
            System.out.println("3. get");

            int choice = scan.nextInt();
            switch (choice) {
            case 1:
                System.out.println("Enter key and value");
                lpht.insert(scan.next(), scan.next());
                break;
            case 2:
                System.out.println("Enter key");
                lpht.remove(scan.next());
                break;
            case 3:
                System.out.println("Enter key");
                System.out.println("Value = " + lpht.get(scan.next()));
                break;
            default:
                System.out.println("Wrong Entry \n ");
                break;
            }
            /** Display hash table **/
            lpht.printHashTable();

            System.out.println("\nDo you want to continue (Type y or n) \n");
            ch = scan.next().charAt(0);
        } while (ch == 'Y' || ch == 'y');
    }
}