package negocio.Storage;

import negocio.mysql.Database;

public class Storage {
    public Storage() {}

    public static IStorage createStorage(StorageType storageType){
        if(storageType == StorageType.FILE){
            return null;
        }else if(storageType == StorageType.MEMORY){
            // StorageMemory storageMemory = new StorageMemory();
            // return storageMemory;
            return null;
        }else if(storageType == StorageType.DATABASE){
            return Database.getInstance();
        }
        return null;
    }
}
