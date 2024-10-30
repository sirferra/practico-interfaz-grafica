
package negocio.repositorio.persona;

import java.util.stream.Collectors;

import modelo.persona.Persona;
import modelo.persona.Vendedor;
import negocio.Storage.IStorage;
import repositorio.RepositorioDeDatos;

public class ABMVendedor extends ABMPersona{


    public ABMVendedor(IStorage storage) {
        super(storage);
    }

    @SuppressWarnings("unused")
    private String entity = "Vendedor";
     public void seed() {
        for(int i=0;i<=RepositorioDeDatos.CANTIDAD_PERSONA_MAXIMA;i++) {
                String sucursal = RepositorioDeDatos.generateSucursal();
                String name = RepositorioDeDatos.generateName();
                String lastName = RepositorioDeDatos.generateLastName();
                int dni = RepositorioDeDatos.generateDni();
                String phone = RepositorioDeDatos.generatePhone();
                String email = RepositorioDeDatos.generateEmail(name);
                RepositorioDeDatos.arrPersonas.add(new Vendedor(
                    sucursal,
                    name,
                    lastName,
                    dni,
                    phone,
                    email                   
                ));
        }
    }

    public void printCsv(){
        //generate headers reading class properties;
        System.out.println(Vendedor.getHeaders());
        for(Persona persona:RepositorioDeDatos.arrPersonas) {
            if(persona instanceof Vendedor)
                System.out.println(((Vendedor)persona).toString());
        }
    }
    @Override
    public String generateCsv() {
        return Vendedor.getHeaders()
            +"\n"
            +RepositorioDeDatos.arrPersonas
                .stream()
                .filter(p-> p instanceof Vendedor)
                .map(p-> p.toString())
                .collect(Collectors.joining("\n"));
    }
}
