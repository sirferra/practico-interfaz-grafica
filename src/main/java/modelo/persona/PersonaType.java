package modelo.persona;

public enum PersonaType {
    CLIENTE {
        @Override
        public Cliente createPersona() {
            return new Cliente();
        }
    },
    VENDEDOR {
        @Override
        public Vendedor createPersona() {
            return new Vendedor();
        }
    },
    PROVEEDOR {
        @Override
        public Proveedor createPersona() {
            return new Proveedor();
        }
    };

    // Método abstracto que cada tipo de PersonaType debe implementar
    public abstract Persona createPersona();
}