package principal;

import negocio.Storage.IStorage;
import negocio.Storage.Storage;
import negocio.Storage.StorageType;

import ui.CLI;
import ui.IUi;

public class VentaBici {
    
    public static IUi renderUI;
    public static IStorage storage = Storage.createStorage(StorageType.MEMORY);
    public static void main(String[] args) {

        if(args.length > 0){
            System.out.println("Se han recibido argumentos:");
            System.out.println(args[0]);
        }
        renderUI = new CLI();
    }   
}
