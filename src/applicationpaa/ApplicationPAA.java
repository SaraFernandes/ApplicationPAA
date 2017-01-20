/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationpaa;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Sara Pereira Fernandes
 */
public class ApplicationPAA {

     public static long trocas = 0;
    public static long comp = 0;
    public static long iteracoes = 0;
    public static Runtime runtime = Runtime.getRuntime();
    public static long memmax = 0;
    public static long mem = 0;
    public static long memory1;
    public static long memory2;
    public static long memory3;
    
    
        /** Get CPU time in nanoseconds. */
    public static long getCpuTime() {
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        return bean.isCurrentThreadCpuTimeSupported()
                ? bean.getCurrentThreadCpuTime() : 0L;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //lista para guardar os numeros separadamente 
        ArrayList<Integer> numeros = new ArrayList<Integer>();

      try {
            File file = new File("array10.txt");

            Scanner s = new Scanner(file);
            while (s.hasNext()) {
                String[] v = s.nextLine().split(" ");

                for (int i = 0; i < v.length; i++) {
                    numeros.add(Integer.parseInt(v[i]));
                }
            }
            s.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int[] v = new int[numeros.size()];

        // o loop preenche o vetor com os dados do ArrayList que foi preenchido com os valores do arquivo
        for (int i = 0; i < numeros.size(); i++) {
            v[i] = numeros.get(i).intValue();

        }
        
      

        
        long start, end, time;
        runtime.gc();        
        memory1 = runtime.totalMemory() - runtime.freeMemory();
        
        start = getCpuTime();
        
          int opcao ;
        char operacao;
        Scanner entrada = new Scanner(System.in);
        
        
        
        System.out.print("Escolha a função [1:quicksort 2:insertionsort 3:mergesort 4: selectionsort 5: bubblesort]: ");
        opcao = entrada.nextInt();
       
        
        switch (opcao) {
            case 1:
                quickSort(v, 0, v.length - 1);
                break;
            case 2:
                insertionSort(v);
                break;
            case 3:
                mergeSort(v, 0, v.length - 1);
                break;
            case 4:
                selectionSort(v);
                break;
            case 5:
                bubbleSort(v);
                break;
                
                 default:
                System.out.printf("Você digitou uma opção inválida."); 
        }
        
        
        
        end = getCpuTime();
        time = end - start;
        
        
//        System.out.println("tempo: "+time);
//        System.out.println("mem1: "+memory1);
//        System.out.println("maxi: "+memmax);
//        System.out.println("mem2: "+memory2);
//        System.out.println("mem3: "+memory3);
//        System.out.println("diferença:"+(memmax-memory1));;
//        System.out.println("Saida padao: "+(double)time/(double)(1000000000)+";"+(double)memmax/(double)(1024)+";"+comp+";"+iteracoes+";"+trocas);
        System.out.println("Tempo: "+(double)time/(double)(1000000000)+"\nMemoria: "+(double)memmax/(double)(1024)+"\nComparações: "+comp+"\nIterações: "+iteracoes+"\nTrocas: "+trocas);
    }
    
    
    
    
    
    public static int partition(int v[], int left, int right)
    {
          int i = left, j = right;
          int tmp;
          int pivo = v[(left + right) / 2];

          while (i <= j) {
                comp++;
                while (v[i] < pivo)
                {i++;
                comp++;
                }
                comp++;
                while (v[j] > pivo)
                {j--;
                comp++;
                }
                if (i <= j) {
                    if (i<j){
                    trocas++;
                    tmp = v[i];
                    v[i] = v[j];
                    v[j] = tmp;
                    }
                    i++;
                    j--;
                }
          }

          return i;
    }

    public static void quickSort(int v[], int left, int right) {
          int index = partition(v, left, right);
          iteracoes++;
          mem = runtime.totalMemory() - runtime.freeMemory();
          if (mem > memmax)
              memmax=mem;
          if (left < index - 1)
                quickSort(v, left, index - 1);
          if (index < right)
                quickSort(v, index, right);
    }
    
    
    public static void selectionSort(int[] v) {

        for (int i = 0; i < v.length - 1; i++) {
            mem = runtime.totalMemory() - runtime.freeMemory();
            if (mem > memmax) {
                memmax = mem;
            }
            int index = i;
            for (int j = i + 1; j < v.length; j++) {
                iteracoes++;
                comp++;
                if (v[j] < v[index]) {
                    index = j;
                
                }
                
               
            }

            int menorNum = v[index];
            v[index] = v[i];
            v[i] = menorNum;
            trocas++;
        }

    }


    public static void bubbleSort(int[] v) {

        int aux;
        for (int i = 0; i < v.length; i++) {
            mem = runtime.totalMemory() - runtime.freeMemory();
            if (mem > memmax) {
                memmax = mem;
            }
            for (int j = 0; j < v.length - 1; j++) {
                iteracoes++;
                comp++;
                if (v[j] > v[j + 1]) {
                    aux = v[j];
                    v[j] = v[j + 1];
                    v[j + 1] = aux;
                    trocas++;
                }
            }
        }
    }

    public static void mergeSort(int[] v, int inic, int fim) {
        mem = runtime.totalMemory() - runtime.freeMemory();
        if (mem > memmax) {
            memmax = mem;
        }
        
        if (fim <= inic) {
            return;
        }
        iteracoes++;
        int meio = (inic + fim) / 2;
        mergeSort(v, inic, meio);
        mergeSort(v, meio + 1, fim);
        int[] A = new int[meio - inic + 1];
        int[] B = new int[fim - meio];
        for (int i = 0; i <= meio - inic; i++) {
            A[i] = v[inic + i];
        }
        for (int i = 0; i <= fim - meio - 1; i++) {
            B[i] = v[meio + 1 + i];
        }
        int i = 0;
        int j = 0;
        for (int k = inic; k <= fim; k++) {
            
            if (i < A.length && j < B.length) {
                comp++;
                if (A[i] < B[j]) {
                    trocas++;
                    v[k] = A[i++];
                } else {
                    trocas++;
                    v[k] = B[j++];
                }
                
            } else if (i < A.length) {
                trocas++;
                v[k] = A[i++];
                
            } else if (j < B.length) {
                trocas++;
                v[k] = B[j++];
            }
        }
    }
    
    public static int[] insertionSort(int[] list) {
        for (int i = 0; i < list.length; i++) {
            mem = runtime.totalMemory() - runtime.freeMemory();
            if (mem > memmax) {
                memmax = mem;
            }
            int next = list[i];
            // Entrocara o local de inserÃ§Ã£o enquanto move os maiores elementos pra cima
            int j = i;
            comp++; // Total de ComparaÃ§Ãµes
            while (j > 0 && list[j - 1] > next) {
                list[j] = list[j - 1];
                j--;
                iteracoes++;
                comp++; // Total de ComparaÃ§Ãµes
                trocas++; // Total de MovimentaÃ§Ãµes
            }
            // insert the element
            list[j] = next;

        }
        return list;
    }
    
}
