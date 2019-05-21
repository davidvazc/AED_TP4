import java.io.IOException;

class Carro {
    private int matricula;
    private int divida;
    public Carro proximo;

    public Carro(String matricula, int divida) {
        this.matricula = Integer.parseInt(matricula);
        this.divida = divida;
    }

    public int getMatricula() {
        return matricula;
    }

    public void mostraMatricula() {
        System.out.print(matricula + " ");
    }
}

class ListaOrdenada {
    private Carro primeiro;

    public ListaOrdenada() {
        primeiro = null;
    }

    public void add(Carro carro) {
        int key = carro.getMatricula();
        Carro anterior = null; // comeca no primeiro
        Carro atual = primeiro;
        // vai ate ao fim da lista ou o atual for maior que a key
        while (atual != null && key > atual.getMatricula()) {
            anterior = atual;
            atual = atual.proximo; // vai para o seguinte
        }
        if (anterior == null) // se for o inicio da lista
            primeiro = carro;
        else
            // nao e o inicio da lista
            anterior.proximo = carro;
        carro.proximo = atual;
    }

    public void remove(int matricula) {
        Carro anterior = null;
        Carro atual = primeiro;

        while (atual != null && matricula != atual.getMatricula()) {
            anterior = atual;
            atual = atual.proximo;
        }
        if (anterior == null) // se for o inicio, apaga o primeiro
            primeiro = primeiro.proximo;
        else
            // not at beginning
            anterior.proximo = atual.proximo; // remove atual link
    }

    public Carro encontra(int key) {
        Carro atual = primeiro;
        while (atual != null && atual.getMatricula() <= key) { // or key too small,
            if (atual.getMatricula() == key) // found, return link
                return atual;
            atual = atual.proximo; // go to proximo item
        }
        return null; // cannot encontra it
    }

    public void displayList() {
        System.out.print("List: ");
        Carro atual = primeiro;
        while (atual != null) {
            atual.mostraMatricula();
            atual = atual.proximo;
        }
        System.out.println("");
    }
}

public class Chaining {
    private ListaOrdenada[] hashArray;

    private int arraySize;

    public Chaining(int size) {
        arraySize = size;
        hashArray = new ListaOrdenada[arraySize];
        for (int i = 0; i < arraySize; i++)
            hashArray[i] = new ListaOrdenada();
    }

    public void displayTable() {
        for (int j = 0; j < arraySize; j++) {
            System.out.print(j + ". ");
            hashArray[j].displayList();
        }
    }

    public int hashFunc(int key) {
        return key % arraySize;
    }

    public void add(Carro theLink) {
        int key = theLink.getMatricula();
        int hashVal = hashFunc(key);
        hashArray[hashVal].add(theLink);
    }

    public void remove(int key) {
        int hashVal = hashFunc(key); // hash the key
        hashArray[hashVal].remove(key);
    }

    public Carro encontra(int key) {
        int hashVal = hashFunc(key); // hash the key
        Carro theLink = hashArray[hashVal].encontra(key); // get link
        return theLink;
    }

    public static void main(String[] args) throws IOException {
        Carro dataItem;
        int size, initSize, keysPerCell = 100;
        size = 100;
        initSize = 10;
        Chaining hashTable = new Chaining(size);

        for (int i = 0; i < initSize; i++) {
            dataItem = new Carro("00AA00", 100);
            hashTable.add(dataItem);
        }
        hashTable.displayTable();
        dataItem = new Carro("00AA00", 100);
        hashTable.add(dataItem);

        dataItem = hashTable.encontra(Integer.parseInt("00AA00"));
        if (dataItem != null)
            System.out.println("Found " + Integer.parseInt("00AA00"));
        else
            System.out.println("Nao encontrou " + Integer.parseInt("00AA00"));
    }

}