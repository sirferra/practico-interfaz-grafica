
package util.coleccion;

import java.util.ArrayList;
import java.util.Collection;

import modelo.persona.Cliente;
import modelo.persona.Persona;
import modelo.persona.Proveedor;
import modelo.persona.Vendedor;

public class ArrayListPersona<E> extends ArrayList<Persona>{

    public ArrayListPersona(int initialCapacity) {
        super(initialCapacity);
    }

    public ArrayListPersona() {
    
    }

    public ArrayListPersona(Collection<? extends Persona> c) {
        super(c);
    }
    
    public int indexOf(int dni) {
        for(int i=0;i<this.size();i++){
            if (this.get(i).getDni()==dni)
                return i;
        }
        return -1;
    }
    
    public int indexOf(Persona p) {
        for(int i=0;i<this.size();i++){
            if(this.get(i) instanceof Cliente){
               Cliente clie=(Cliente)this.get(i);
               Cliente cli2=(Cliente)p;
                if(clie.getDni()==cli2.getDni())
                    return i;
            }else if(this.get(i)instanceof Vendedor){
                Vendedor ven=(Vendedor)this.get(i);
                Vendedor ven2=(Vendedor)p;
                if(ven.getDni()==ven2.getDni())
                    return i;
            }else if(this.get(i) instanceof Proveedor){
                Proveedor prov=(Proveedor)this.get(i);
                Proveedor provP=(Proveedor)p;
                if(prov.getCodigo().equals(provP.getCodigo()))
                    return i;
            }
        }
        return -1;
    }
   
    public boolean contains(Persona p) {
        for(int i=0;i<this.size();i++){
            if(this.get(i) instanceof Cliente){
               Cliente clie=(Cliente)this.get(i);
               Cliente cli2=(Cliente)p;
                if(clie.getDni()==cli2.getDni())
                    return true;
            }else if(this.get(i) instanceof Vendedor){
                Vendedor ven=(Vendedor)this.get(i);
                Vendedor ven2=(Vendedor)p;
                if(ven.getDni()==ven2.getDni())
                    return true;
            }else if(this.get(i) instanceof Proveedor){
                Proveedor prov=(Proveedor)this.get(i);
                Proveedor provP=(Proveedor)p;
                if(prov.getCuit().equals(provP.getCuit()) 
                        && prov.getNombreFantasia().equals(provP.getNombreFantasia()))
                    return true;
            }
        }
        return false;
    }
    
}
