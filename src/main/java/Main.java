import connection.DAO;
import entity.Entidade;
import rmi.RemoteCall;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static DAO dao;
    private static ArrayBlockingQueue<Entidade> insertQueue;
    private static ArrayBlockingQueue<Entidade> updateQueue;
    private static ArrayBlockingQueue<Integer> deleteQueue;

    public static void main(String[] args) {
        insertQueue = new ArrayBlockingQueue<Entidade>(50);
        updateQueue = new ArrayBlockingQueue<Entidade>(50);
        deleteQueue = new ArrayBlockingQueue<Integer>(1);
        //
        final int maximo = 100;

        dao = new DAO();

        final long tempo = System.currentTimeMillis();
        RemoteCall remoteCall = new RemoteCall();
        //
        for (int k = 1; k <= maximo; k++) {

            final int i = remoteCall.getId();
            final Entidade entidade = new Entidade();
            entidade.setId(i);
            entidade.setNome("NOME " + i);
            entidade.setDelete(false);
            entidade.setUpdate(false);

            //INSERINDO
            Runnable inserir = new Runnable() {
                public void run() {
                    try {
                        System.out.println("Inserindo " + i);
                        //
                        dao.salvar(entidade);
                        insertQueue.put(entidade);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            };

            //ATUALIZANDO
            Runnable atualizar = new Runnable() {
                public void run() {
                    try {
                        Entidade entidade = insertQueue.take();
                        System.out.println("Atualizando " + i);
                        entidade.setUpdate(true);
                        dao.atualizar(entidade);
                        updateQueue.put(entidade);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            };

            //DELETANDO
            Runnable deletar = new Runnable() {
                public void run() {
                    try {
                        Entidade entidade = updateQueue.take();
                        //

                        System.out.println("Deletando " + i);
                        entidade.setDelete(true);
                        dao.atualizar(entidade);
                        if (i >= 1000) {
                            long tempo2 = System.currentTimeMillis() - tempo;
                            System.out.println("---------------------------> Tempo: " + tempo2);
                        }
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            };

            //THREAD
            Thread tInserir = new Thread(inserir);
            Thread tAtualizar = new Thread(atualizar);
            Thread tDeletar = new Thread(deletar);

            tInserir.start();
            tAtualizar.start();
            tDeletar.start();

        }
    }
}
