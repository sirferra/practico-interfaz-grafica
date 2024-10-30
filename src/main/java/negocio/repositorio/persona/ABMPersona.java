package negocio.repositorio.persona;

import java.util.ArrayList;

import exceptions.PersonExistsException;
import modelo.persona.Persona;
import negocio.Storage.IStorage;
import negocio.repositorio.StorageMemory;

public class ABMPersona extends StorageMemory {
    private String entity = "Persona";
    private IStorage storage;

    public ABMPersona(IStorage storage) {
        this.storage = storage;
    }

    private Persona transformObjectToPersona(Object object) {
        if (object == null)
            throw new IllegalArgumentException("La persona no puede ser nula");
        if (object instanceof Persona == false)
            throw new IllegalArgumentException("La persona debe ser de tipo Persona");
        Persona persona = (Persona) object;
        return persona;
    }

    public void create(Object object) throws Exception {
        Persona persona = transformObjectToPersona(object);
        if (this.exists(persona.getDni())) {
            throw new PersonExistsException(this.entity + " ya existe");
        }
        this.storage.create(persona);
    }

    public void delete(Object object) throws Exception {
        Persona persona = transformObjectToPersona(object);
        if (!this.exists(persona.getDni())) {
            throw new PersonExistsException(this.entity + " no existe");
        }
        this.storage.delete(object);
    }

    public void update(Object object) throws Exception {
        Persona persona = transformObjectToPersona(object);
        if (!this.exists(persona.getDni())) {
            throw new PersonExistsException(this.entity + " no existe");
        }
        this.storage.update(object);
    }

    public Persona getByDni(int dni) throws Exception {
        return (Persona) storage.getAll(Persona.class)
                .stream()
                .filter(p -> p != null && ((Persona) p).getDni() == dni)
                .findFirst().orElse(null);
    }

    public boolean exists(int dni) throws Exception {
        return storage.getAll(Persona.class)
                .stream()
                .filter(p -> p != null && ((Persona) p).getDni() == dni)
                .count() > 0;
    }

    public boolean exists(Object object) throws Exception {
        Persona persona = transformObjectToPersona(object);
        return storage.exists(persona);
    }

    /**
     * Obtiene todas las personas según tipo. Busca en el array personas
     * 
     * @param type el tipo de persona {@link PersonaType}
     * @return un array de personas
     * @throws Exception
     */
    public Persona[] getByType(PersonaType type) throws Exception {
        return storage.getAll(Persona.class)
                .stream()
                .filter(p -> p != null && p.getClass().getSimpleName().equals(type.toString()))
                .toArray(Persona[]::new);
    }

    public int getIndexOf(Object object) throws Exception {
        Persona persona = transformObjectToPersona(object);
        return storage.getIndexOf(persona);
    }

    public Object findByIndex(int index) throws Exception {
        return storage.get(index, Persona.class);
    }

    /**
     * Obtiene todas las personas.
     * 
     * @return un array de personas
     * @throws Exception
     */
    public ArrayList<?> getAll(Class<?> type) throws Exception {
        return storage.getAll(Persona.class);
    }

    public Object get(int index, Class<?> type) throws Exception {
        return storage.get(index, type);
    }


    // /**
    // * Guarda una persona en el arrPersonas del repositorio de datos
    // * @param persona
    // * @throws PersonExistsException
    // */
    // public void createInArrPersonas(Persona persona) throws PersonExistsException
    // {
    // if(persona == null) throw new IllegalArgumentException("La persona no puede
    // ser nula");
    // if(exists(persona)) throw new PersonExistsException(this.entity + " ya
    // existe");
    // RepositorioDeDatos.arrPersonas.add(persona);
    // }
    public void seed() {
    }

    public void printCsv() {
    }

    public String generateCsv() {
        return "";
    }
}
