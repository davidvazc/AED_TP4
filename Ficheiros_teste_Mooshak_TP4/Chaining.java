import java.io.IOException;
import java.util.StringTokenizer;

class Carro {
    int matricula;
    String smatricula;
    int divida;
    public Carro proximo;

    public Carro(String s, int divida) {
        int aux = 0;
        this.smatricula = s;
        for (int i = 0; i < 6; ++i) {
            char ch = s.charAt(i);
            aux += (int) ch * (6 - i) * 999;
        }
        this.matricula = aux;
        this.divida = divida;

    }

    public int getMatricula() {
        return matricula;
    }

    public String getSMatricula() {
        return smatricula;
    }

    public int getDivida() {
        return divida;
    }

    public void mostraMatricula() {
        System.out.print(smatricula + " ");
    }
}

class ListaOrdenada {
    private Carro primeiro;

    public ListaOrdenada() {
        primeiro = null;
    }

    public void add(Carro carro) {
        String key = carro.getSMatricula();
        Carro anterior = null; // comeca no primeiro
        Carro atual = primeiro;
        // vai ate ao fim da lista ou o atual for maior que a key

        while (atual != null && key.compareTo(atual.getSMatricula()) > 0) {
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

    public Carro encontra(String s) {
        int aux = 0;
        for (int i = 0; i < 6; ++i) {
            char ch = s.charAt(i);
            aux += (int) ch * (6 - i) * 999;
        }
        int key = aux;
        Carro atual = primeiro;
        while (atual != null && atual.getMatricula() <= key) { // or key too small,
            if (atual.getMatricula() == key) { // found, return link
                return atual;
            }
            atual = atual.proximo; // go to proximo item
        }
        return null; // cannot encontra it
    }

    public Carro addDivida(int key, int divida) {
        Carro atual = primeiro;
        while (atual != null && atual.getMatricula() <= key) { // or key too small,
            if (atual.getMatricula() == key) { // found, return link
                atual.divida = atual.divida + divida;
                return atual;
            }
            atual = atual.proximo; // go to proximo item
        }
        return null; // cannot encontra it

    }

    public void displayList() {
        Carro atual = primeiro;
        while (atual != null) {
            System.out.print(atual.smatricula);
            System.out.print(" VALOR EM DIVIDA ");
            System.out.println(atual.divida);
            atual = atual.proximo;
        }
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

    public Carro encontra(String s) {
        int aux = 0;
        for (int i = 0; i < 6; ++i) {
            char ch = s.charAt(i);
            aux += (int) ch * (6 - i) * 999;
        }
        int key = aux;
        int hashVal = hashFunc(key); // hash the key
        Carro theLink = hashArray[hashVal].encontra(s); // get link
        return theLink;
    }

    public void addDivida(String s, int divida) {
        int aux = 0;
        for (int i = 0; i < 6; ++i) {
            char ch = s.charAt(i);
            aux += (int) ch * (6 - i) * 999;
        }
        int key = aux;
        int hashVal = hashFunc(key); // hash the key
        hashArray[hashVal].addDivida(key, divida); // get link
    }

    public static void main(String[] args) throws IOException {
        Carro dataItem;
        int size, initSize, keysPerCell = 100;
        size = 100;
        initSize = 10;
        Chaining hashTable = new Chaining(size);
        String input, comando, matricula;
        int divida;
        StringTokenizer st;

        do {
            input = readLn();
            assert input != null;
            st = new StringTokenizer(input.trim());
            comando = st.nextToken();
            switch (comando) {
            case "PORTICO": {
                matricula = st.nextToken();
                divida = Integer.parseInt(st.nextToken());
                if (hashTable.encontra(matricula) == null) {

                    Carro carro = new Carro(matricula, divida);

                    hashTable.add(carro);

                    if (carro.divida == 0) {
                        hashTable.remove(carro.matricula);
                    }
                    System.out.println("nao existia");
                    break;
                } else {
                    hashTable.addDivida(matricula, divida);
                    System.out.println("existia");
                    break;
                }

            }
            case "PAGAMENTO": {
                matricula = st.nextToken();
                divida = Integer.parseInt(st.nextToken());
                Carro carro = new Carro(matricula, divida * -1);

                hashTable.add(carro);
                if (carro.divida == 0) {
                    hashTable.remove(carro.matricula);
                }

                break;
            }
            case "SALDO": {
                matricula = st.nextToken();
                Carro carro = hashTable.encontra(matricula);

                if (carro == null || carro.divida == 0) {
                    System.out.println(matricula + " SEM REGISTO");
                } else {
                    System.out.println(matricula + " VALOR EM DIVIDA " + carro.divida);
                }

                break;
            }
            case "LISTA":
                hashTable.displayTable();

                break;

            case "TERMINA":
                return;
            }

        } while (true);

    }

    private static String readLn() {
        byte[] lin = new byte[200];
        int lg = 0, car = -1;

        try {
            while (lg < 200) {
                car = System.in.read();
                if ((car < 0) || (car == '\n'))
                    break;
                lin[lg++] += car;
            }
        } catch (IOException e) {
            return (null);
        }

        if ((car < 0) && (lg == 0))
            return null;

        return (new String(lin, 0, lg));

    }
}
