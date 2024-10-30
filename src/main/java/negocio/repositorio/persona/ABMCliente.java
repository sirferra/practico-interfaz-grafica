
package negocio.repositorio.persona;

import java.util.stream.Collectors;

import modelo.persona.Cliente;
import modelo.persona.Persona;
import negocio.Storage.IStorage;
import repositorio.RepositorioDeDatos;

public class ABMCliente extends ABMPersona {
    @SuppressWarnings("unused")
    private String entity = "Cliente";

    public ABMCliente(IStorage storage) {
        super(storage);
    }
    public void seed(){
        for(int i=0;i<=RepositorioDeDatos.CANTIDAD_PERSONA_MAXIMA;i++) {
            String name = RepositorioDeDatos.generateName();
            String lastName = RepositorioDeDatos.generateLastName();
            int dni = RepositorioDeDatos.generateDni();
            String phone = RepositorioDeDatos.generatePhone();
            String cuil = RepositorioDeDatos.generateCuil(false, dni);
            String email = RepositorioDeDatos.generateEmail(name);
            RepositorioDeDatos.arrPersonas.add(
                new Cliente(cuil,name,lastName,dni,phone,email)
            );
        }
    }
    public void printCsv(){
        //generate headers reading class properties;
        System.out.println(Cliente.getHeaders());
        for(Persona persona:RepositorioDeDatos.arrPersonas) {
            if(persona instanceof Cliente)
                System.out.println(((Cliente)persona).toString());
        }
    }
    @Override
    public String generateCsv() { 
        return Cliente.getHeaders()
            +"\n"
            +RepositorioDeDatos.arrPersonas
                .stream()
                .filter(p-> p instanceof Cliente)
                .map(p-> p.toString())
                .collect(Collectors.joining("\n"));
    }
}
