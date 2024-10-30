package negocio.Storage;

import negocio.repositorio.StorageMemory;

public class Storage {
    public Storage() {}

    public static IStorage createStorage(StorageType storageType){
        if(storageType == StorageType.FILE){
            return null;
        }else if(storageType == StorageType.MEMORY){
            StorageMemory storageMemory = new StorageMemory();
            storageMemory.seed();
            return storageMemory;
        }else if(storageType == StorageType.DATABASE){
            return null;
        }
        return null;
    }
}
