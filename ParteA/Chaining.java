import java.io.IOException;
import java.util.StringTokenizer;

class Carro {
    int matriculaInt;
    String matriculaString;
    int divida;
    public Carro proximo;

    public Carro(String s, int divida) {
        this.matriculaString = s;
        this.divida = divida;
        int aux = 0;
        int i;
        char ch;
        for (i = 0; i < 6; ++i) {
            ch = s.charAt(i);
            aux += (int) ch * (6 - i) * 101;
        }
        this.matriculaInt = aux;
    }

    public int getMatricula() {
        return matriculaInt;
    }

    public String getSMatricula() {
        return matriculaString;
    }
}

class ListaOrdenada {
    private Carro primeiro;

    public ListaOrdenada() {
        primeiro = null;
    }

    public void add(Carro carro) {
        String key = carro.getSMatricula();
        Carro anterior = null;
        Carro atual = primeiro;
        boolean existe = false;

        while (atual != null) {
            if (atual.getSMatricula().compareTo(key) == 0) {
                atual.divida += carro.divida;
                existe = true;
            }
            atual = atual.proximo;
        }
        atual = primeiro;
        if (!existe) {
            while (atual != null && key.compareTo(atual.getSMatricula()) > 0) {
                anterior = atual;
                atual = atual.proximo;
            }
            if (anterior == null)
                primeiro = carro;
            else
                anterior.proximo = carro;
            carro.proximo = atual;
        }
    }

    public void remove(int matriculaInt, String s) {
        Carro anterior = null;
        Carro atual = primeiro;

        while (atual != null && atual.getMatricula() <= matriculaInt) {
            if (atual.getMatricula() == matriculaInt && atual.getSMatricula().compareTo(s) == 0) {
                if (anterior == null)
                    primeiro = primeiro.proximo;
                else
                    anterior.proximo = atual.proximo;

                break;
            }
            anterior = atual;
            atual = atual.proximo;

        }

    }

    public Carro encontra(int key, String s) {
        Carro atual = primeiro;
        while (atual != null && atual.getMatricula() <= key) {
            if (atual.getMatricula() == key && atual.getSMatricula().compareTo(s) == 0)
                return atual;
            atual = atual.proximo;
        }
        return null;
    }
}

public class Chaining {
    private ListaOrdenada[] hashArray;

    private int arraySize;

    public Chaining(int size) {
        arraySize = size;
        hashArray = new ListaOrdenada[arraySize];
        int i;
        for (i = 0; i < arraySize; i++)
            hashArray[i] = new ListaOrdenada();
    }

    public int hashFunc(int key) {
        return key % arraySize;
    }

    public void add(Carro theLink) {
        int key = theLink.getMatricula();
        int hashVal = hashFunc(key);
        hashArray[hashVal].add(theLink);
    }

    public void remove(String s) {
        int aux = 0;
        int i;
        char ch;
        for (i = 0; i < 6; ++i) {
            ch = s.charAt(i);
            aux += (int) ch * (6 - i) * 101;
        }
        int key = aux;
        int hashVal = hashFunc(key);
        hashArray[hashVal].remove(key, s);
    }

    public Carro encontra(String s) {
        int aux = 0;
        int i;
        char ch;
        for (i = 0; i < 6; ++i) {
            ch = s.charAt(i);
            aux += (int) ch * (6 - i) * 101;
        }
        int key = aux;
        int hashVal = hashFunc(key);
        Carro theLink = hashArray[hashVal].encontra(key, s);
        return theLink;
    }

    public static void main(String[] args) throws IOException {
        int size = 1000;
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
                Carro carro = new Carro(matricula, divida);

                hashTable.add(carro);

                if (carro.divida == 0) {
                    hashTable.remove(matricula);
                }

                break;
            }
            case "PAGAMENTO": {
                matricula = st.nextToken();
                divida = Integer.parseInt(st.nextToken());
                Carro carro = new Carro(matricula, divida * -1);

                hashTable.add(carro);
                if (hashTable.encontra(matricula).divida == 0) {
                    hashTable.remove(matricula);
                }

                break;
            }
            case "SALDO": {
                matricula = st.nextToken();
                Carro carro = hashTable.encontra(matricula);

                if (carro == null) {
                    System.out.println(matricula + " SEM REGISTO");
                } else {
                    System.out.println(matricula + " VALOR EM DIVIDA " + carro.divida);
                }

                break;
            }
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
