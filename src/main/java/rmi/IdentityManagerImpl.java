package rmi;

import connection.DAO;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class IdentityManagerImpl extends UnicastRemoteObject implements IdentityManager {

    private static final int PORTA_SERVIDOR = 2222;
    private static int id_Atual = 0;

    protected IdentityManagerImpl() throws RemoteException {
        super();
        DAO dao = new DAO();
        id_Atual = dao.maiorId();
    }

    @Override
    public synchronized int getId() throws RemoteException {
        return ++ id_Atual;
    }
}
