package br.edu.ifpb.server.rmi;

import br.edu.ifpb.shared.IdentityManager;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class IdentityManagerImpl extends UnicastRemoteObject implements IdentityManager {

    private static final int PORTA_SERVIDOR = 2222;
    private static int id_Atual = 0;

    public IdentityManagerImpl() throws RemoteException {
        super();
    }

    @Override
    public synchronized int getId() throws RemoteException {
        return ++ id_Atual;
    }
}