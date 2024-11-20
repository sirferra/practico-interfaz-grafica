package ui.GUI.Constants;

import modelo.persona.Cliente;
import modelo.persona.Proveedor;
import modelo.persona.Vendedor;

public enum TABLES {
    CLIENTS{
        public String getName() {return "Cliente";}
        public Class<?> getAssociatedClass(){return Cliente.class;}
    },
    PROVIDERS{
        public String getName() {return "Proveedor";}
        public Class<?> getAssociatedClass(){return Proveedor.class;}
    },
    SELLER{
        public String getName() {return "Vendedor";}
        public Class<?> getAssociatedClass(){return Vendedor.class;}
    };
    public abstract String getName();
    public abstract Class<?> getAssociatedClass();
}
