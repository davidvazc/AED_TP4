import java.io.IOException;
import java.util.Arrays;
import java.util.StringTokenizer;

class Carro {
    int key, divida;
    String matricula;

    public Carro(String s, int divida) {
        this.matricula = s;
        this.divida = divida;
        int aux = 0, i;
        char ch;
        for (i = 0; i < 6; ++i) {
            ch = s.charAt(i);
            aux += (int) ch * (6 - i) * 101;
        }
        this.key = aux;
    }

    public int getKey() {
        return key;
    }

    public String getMatricula() {
        return matricula;
    }
}

class LinearProbing {
    private Carro[] ListaCarros;
    private int arraySize;
    private Carro apagado;

    public LinearProbing(int size) {
        arraySize = size;
        ListaCarros = new Carro[arraySize];
        apagado = new Carro("AAAAAA", 0);
    }

    public int hashFunction(int key) {
        return key % arraySize;
    }

    public void add(Carro item) {
        int hashKey = hashFunction(item.getKey());

        while (ListaCarros[hashKey] != null && ListaCarros[hashKey].getMatricula().compareTo("AAAAAA") != 0) {
            if (ListaCarros[hashKey].getMatricula().compareTo(item.getMatricula()) == 0) {
                ListaCarros[hashKey].divida += item.divida;
                return;
            }
            ++hashKey;
            hashKey %= arraySize;
        }
        ListaCarros[hashKey] = item;
    }

    public Carro remove(String s) {
        int aux = 0, i;
        char ch;
        for (i = 0; i < 6; ++i) {
            ch = s.charAt(i);
            aux += (int) ch * (6 - i) * 101;
        }
        int key = aux;
        int hashKey = hashFunction(aux);

        while (ListaCarros[hashKey] != null) {
            if (ListaCarros[hashKey].getMatricula().compareTo(s) == 0 && ListaCarros[hashKey].getKey() == key) {
                Carro temp = ListaCarros[hashKey];
                ListaCarros[hashKey] = apagado;
                return temp;
            }
            ++hashKey;
            hashKey %= arraySize;
        }
        return null;
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
        int hashKey = hashFunction(key);
        while (ListaCarros[hashKey] != null) {
            if (ListaCarros[hashKey].getMatricula().compareTo(s) == 0 && ListaCarros[hashKey].getKey() == key) {
                return ListaCarros[hashKey];
            }
            ++hashKey;
            hashKey %= arraySize;
        }
        return null;
    }
}

public class B {
    public static void main(String[] args) throws IOException {
        int size = 10000;
        LinearProbing hashTable = new LinearProbing(size);
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

                break;
            }
            case "PAGAMENTO": {
                matricula = st.nextToken();
                divida = Integer.parseInt(st.nextToken());
                Carro carro = new Carro(matricula, divida * -1);

                if (hashTable.encontra(matricula).divida - divida == 0) {

                    hashTable.remove(matricula);

                } else {
                    hashTable.add(carro);
                }

                break;
            }
            case "SALDO": {
                matricula = st.nextToken();

                Carro carro = hashTable.encontra(matricula);

                if (carro != null) {
                    if (carro.divida != 0) {
                        System.out.println(matricula + " VALOR EM DIVIDA " + carro.divida);
                    }

                } else {
                    System.out.println(matricula + " SEM REGISTO");
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