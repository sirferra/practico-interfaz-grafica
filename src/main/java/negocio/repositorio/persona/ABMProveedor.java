
package negocio.repositorio.persona;

import java.util.stream.Collectors;

import modelo.persona.Persona;
import modelo.persona.Proveedor;
import negocio.Storage.IStorage;
import repositorio.RepositorioDeDatos;

public class ABMProveedor extends ABMPersona {


    public ABMProveedor(IStorage storage) { 
        super(storage);
    }


    @SuppressWarnings("unused")
    private String entity = "Proveedor";

    public Proveedor[] filterByCode(String code) {
        return RepositorioDeDatos.arrPersonas
                .stream() // transforma a stream 
                .filter(arrPersona -> arrPersona instanceof Proveedor) // filtro por Proveedor
                .map(arrPersona -> (Proveedor) arrPersona) // casteo a Proveedor (para que no falle el getCodigo())
                .filter(proveedor -> proveedor.getCodigo().equals(code))  // filtro por código
                .toArray(Proveedor[]::new); // retorno en un array
    }
    public void seed() {
        for(int i=0;i<=RepositorioDeDatos.CANTIDAD_PERSONA_MAXIMA;i++) {
            String codigo = RepositorioDeDatos.generateCode();
                String fantasyName = RepositorioDeDatos.generateFantasyName();
                String name = RepositorioDeDatos.generateName();
                String lastName = RepositorioDeDatos.generateLastName();
                int dni = RepositorioDeDatos.generateDni();
                String cuit = RepositorioDeDatos.generateCuil(true, dni);
                RepositorioDeDatos.arrPersonas.add(new Proveedor(
                    codigo,
                    fantasyName,
                    cuit,
                    name,
                    lastName,
                    dni
                ));
        }
    }
    public void printCsv(){
        //generate headers reading class properties;
        System.out.println(Proveedor.getHeaders());
        for(Persona persona:RepositorioDeDatos.arrPersonas) {
            if(persona instanceof Proveedor)
                System.out.println(((Proveedor)persona).toString());
        }
    }
    @Override
    public String generateCsv() {
        return Proveedor.getHeaders()
            +"\n"
            +RepositorioDeDatos.arrPersonas
                .stream()
                .filter(p-> p instanceof Proveedor)
                .map(p-> p.toString())
                .collect(Collectors.joining("\n"));
    }
}
