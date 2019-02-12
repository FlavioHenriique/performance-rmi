package rmi;

import br.edu.ifpb.shared.IdentityManager;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RemoteCall {

    private static final int PORTA_SERVIDOR = 2222;

    public RemoteCall() {
    }

    public int getId(){

        int id = 0;
        try {
            IdentityManagerImpl idGenerator = new IdentityManagerImpl();
            Registry registry = LocateRegistry.getRegistry(PORTA_SERVIDOR);
            IdentityManager manager = (IdentityManager) registry.lookup("Impl");
            id = manager.getId();

        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (AccessException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return  id;
    }
}

