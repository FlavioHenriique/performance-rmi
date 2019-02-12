

import br.edu.ifpb.server.rmi.IdentityManagerImpl;
import br.edu.ifpb.shared.IdentityManager;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Servidor {
    private static final int PORTA_SERVIDOR = 2222;

    public static void main(String[] args) {

        try {
            IdentityManager idGenerator = new IdentityManagerImpl();
            Registry registry = LocateRegistry.createRegistry(PORTA_SERVIDOR);
            registry.bind("Impl", idGenerator);
            System.out.println("Servidor ok");

        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
        }
    }
}