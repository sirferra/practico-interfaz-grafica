package ui;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import exceptions.PersonExistsException;
import modelo.persona.Cliente;
import modelo.persona.Persona;
import modelo.persona.Proveedor;
import modelo.persona.Vendedor;
import negocio.Storage.IStorage;
import modelo.persona.PersonaType;
import principal.VentaBici;
import repositorio.RepositorioDeDatos;
import util.Tools;

public class CLI implements IUi{
    private int selectedItem = 0;
    private boolean exit = false;
    Scanner scanner = new Scanner(System.in);
    public CLI(){
        while (!exit){
            this.render();
        }
        scanner.close();
    }
    public void render(){
        this.renderMenu();
    }

    public void renderMenu(){
        System.out.println("=========== Menu =============");
        System.out.println("0. Imprimir TODO");
        System.out.println("1. Clientes");
        System.out.println("2. Proveedores");
        System.out.println("3. Vendedores");
        System.out.println("4. Salir");
        System.out.println("==============================");
        this.selectedItem = scanner.nextInt();
        switch (this.selectedItem){ 
            case 1:
                this.renderSubMenu(PersonaType.CLIENTE, VentaBici.storage);
                break;
            case 2:
                this.renderSubMenu(PersonaType.PROVEEDOR, VentaBici.storage);
                break;
            case 3:
                this.renderSubMenu(PersonaType.VENDEDOR, VentaBici.storage);
                break;
            case 4:
                System.out.println("Has seleccionado Salir");
                exit = true;
                break;
            case 0:
                RepositorioDeDatos.arrPersonas.forEach(p-> System.out.println(p.getClass().getSimpleName() + ":" + p.toString()));
                break;
            default:
                System.out.println("Opcion no valida");
                break;
        }
        System.out.flush();
        System.out.println();
    }

    public void renderSubMenu(PersonaType entity,IStorage storage){
        System.out.println("=========== "+entity+" =============");
        System.out.println("1. Crear");
        System.out.println("2. Listar");
        System.out.println("3. Actualizar");
        System.out.println("4. Eliminar");
        System.out.println("5. Volver");
        System.out.println("==============================");
        this.selectedItem = scanner.nextInt();
        try {
            switch (this.selectedItem){ 
                case 1:
                    if(entity == PersonaType.CLIENTE){
                        Cliente c = (Cliente)this.getDataOfNewPersona(PersonaType.CLIENTE);
                        storage.create(c.table, Tools.mapFromObject(c));
                    }else if(entity == PersonaType.PROVEEDOR){
                        Proveedor p = (Proveedor)this.getDataOfNewPersona(PersonaType.PROVEEDOR);
                        storage.create(p.table, Tools.mapFromObject(p));
                    }else if(entity == PersonaType.VENDEDOR){
                        Vendedor v = (Vendedor)this.getDataOfNewPersona(PersonaType.VENDEDOR);
                        storage.create(v.table, Tools.mapFromObject(v));
                    }
                    System.out.println();
                    this.renderSubMenu(entity, storage);
                    break;
                case 2:
                    if(entity == PersonaType.CLIENTE){
                        this.convertCSVToTable(storage.generateCsv(Cliente.class));
                    }else if(entity == PersonaType.PROVEEDOR){
                        this.convertCSVToTable(storage.generateCsv(Proveedor.class));
                    }else if(entity == PersonaType.VENDEDOR){
                        this.convertCSVToTable(storage.generateCsv(Vendedor.class));
                    }
                    //this.convertCSVToTable(storage.generateCsv());
                    System.out.println();
                    this.renderSubMenu(entity, storage);
                    break;
                case 3:
                    if(entity == PersonaType.CLIENTE){
                        Map<String, String> c = this.getDataToUpdatePersona(PersonaType.CLIENTE, storage);
                        storage.update("clientes", c, Integer.parseInt(c.get("dni")));
                    }else if(entity == PersonaType.PROVEEDOR){
                        Map<String, String> p = this.getDataToUpdatePersona(PersonaType.PROVEEDOR, storage);
                        storage.update("proveedores", p, Integer.parseInt(p.get("dni")));
                    }else if(entity == PersonaType.VENDEDOR){
                        Map<String, String> v = this.getDataToUpdatePersona(PersonaType.VENDEDOR, storage);
                        storage.update("vendedores", v, Integer.parseInt(v.get("dni")));
                    }
                    System.out.println();
                    this.renderSubMenu(entity, storage);
                    break;
                case 4:
                    Persona p = this.getPersonaToDelete(entity,storage);
                    storage.delete(p.table, p);
                    System.out.println("Se ha eliminado " + p.getClass().getSimpleName() + ":" + p.toString());
                    System.out.println();
                    this.renderSubMenu(entity, storage);
                    break;
                case 5:
                    System.out.println("Has seleccionado Volver");
                    System.out.println();
                    break;
                default:
                    System.out.flush();
                    System.out.println("Opcion no valida");
                    this.renderSubMenu(entity, storage);
                    break;
            }        
        } catch (Exception e) {
            System.out.println(e.getMessage());
            this.renderSubMenu(entity, storage);
        }
    }

    public String checkEmpty(String str,String org){
        return str.isEmpty() ? org : str;
    }



    public Persona getDataOfNewPersona(PersonaType typeOfPerson){
        System.out.println("Ingresa el dni");
        int dni = scanner.nextInt();
        System.out.println("Ingresa el nombre");
        String nombre = scanner.next();
        System.out.println("Ingresa el apellido");
        String apellido = scanner.next();
        System.out.println("Ingresa el email");
        String email = scanner.next();
        System.out.println("Ingresa el telefono");
        String telefono = scanner.next();
        switch (typeOfPerson) {
            case CLIENTE:
                System.out.println("Ingresa el CUIL");
                String cuil = scanner.next();
                return new Cliente(cuil, nombre, apellido, dni, telefono, email);
            case VENDEDOR:
                System.out.println("Ingresa la Sucursal que pertenece");
                String sucursal = scanner.next();
                return new Vendedor(sucursal, nombre, apellido, dni, telefono, email);
            case PROVEEDOR:
                System.out.println("Ingresa el codigo del proveedor");
                String cod = scanner.next();
                System.out.println("Ingresa el nombre de Fantasía");
                String nombreFantasia = scanner.next();
                System.out.println("Ingresa el CUIT");
                String cuit = scanner.next();
                return new Proveedor(cod, nombreFantasia, cuit,nombre, apellido, dni);
            default:
                throw new IllegalArgumentException("Tipo de persona no válido");
        }       
    }

    public Map<String, String> getDataToUpdatePersona(PersonaType typeOfPerson, IStorage storage) throws Exception {
        System.out.println("Ingresa el dni");
        int dni = scanner.nextInt();
        System.out.println(typeOfPerson);
        String table = typeOfPerson.createPersona().table;
        System.out.println(table);;
        if (!storage.exists(table, dni)) throw new PersonExistsException("La persona no existe");
        int id = storage.getId(table, "dni", String.valueOf(dni));
        System.out.println(id);
        Persona p = (Persona) storage.get(table, id, typeOfPerson.createPersona().getClass());
        System.out.println("Ingresa el nombre [" + p.getNombre() + "]");
        String nombre = scanner.next();
        System.out.println("Ingresa el apellido [" + p.getApellido() + "]");
        String apellido = scanner.next();
        System.out.println("Ingresa el email [" + p.getEmail() + "]");
        String email = scanner.next();
        System.out.println("Ingresa el telefono [" + p.getTelefono() + "]");
        String telefono = scanner.next();

        Map<String, String> updatedData = new HashMap<>();

        updatedData.put("nombre", checkEmpty(nombre, p.getNombre()));
        updatedData.put("apellido", checkEmpty(apellido, p.getApellido()));
        updatedData.put("dni", String.valueOf(dni));
        updatedData.put("telefono", checkEmpty(telefono, p.getTelefono()));
        updatedData.put("email", checkEmpty(email, p.getEmail()));

        switch (typeOfPerson) {
            case CLIENTE:
                Cliente client = (Cliente) p;
                System.out.println("Ingresa el CUIL [" + client.getCuil() + "]");
                String cuil = scanner.next();
                updatedData.put("cuil", checkEmpty(cuil, client.getCuil()));
                break;
            case VENDEDOR:
                Vendedor vendedor = (Vendedor) p;
                System.out.println("Ingresa la Sucursal [" + vendedor.getSucursal() + "]");
                String sucursal = scanner.next();
                updatedData.put("sucursal", checkEmpty(sucursal, vendedor.getSucursal()));
                break;
            case PROVEEDOR:
                Proveedor proveedor = (Proveedor) p;
                System.out.println("Ingresa el codigo del proveedor [" + proveedor.getCodigo() + "]");
                String cod = scanner.next();
                System.out.println("Ingresa el nombre de Fantasía [" + proveedor.getNombreFantasia() + "]");
                String nombreFantasia = scanner.next();
                System.out.println("Ingresa el CUIT [" + proveedor.getCuit() + "]");
                String cuit = scanner.next();
                updatedData.put("codigo", checkEmpty(cod, proveedor.getCodigo()));
                updatedData.put("nombreFantasia", checkEmpty(nombreFantasia, proveedor.getNombreFantasia()));
                updatedData.put("cuit", checkEmpty(cuit, proveedor.getCuit()));
                break;
            default:
                throw new IllegalArgumentException("Tipo de persona no válido");
        }

        return updatedData;
    }
    
    public Persona getPersonaToDelete(PersonaType typeOfPerson, IStorage storage) throws Exception, PersonExistsException {
        System.out.println("Ingresa el dni del/la "+typeOfPerson.toString().toLowerCase()+ " a eliminar");
        int dni = scanner.nextInt();
        Persona tempPersona = typeOfPerson.createPersona();
        String table = tempPersona.table;
        if (!storage.exists(table, dni)) throw new PersonExistsException("La persona no existe");
        System.out.println("¿Seguro que desea eliminar la persona? (s/n)");
        String confirm = scanner.next();
        while (!confirm.equals("s") && !confirm.equals("n")){ 
            System.out.println("Opcion no valida "+ confirm);
            System.out.println("¿Seguro que desea eliminar la persona? (s/n)");
            confirm = scanner.next();
        }
        if(confirm.equals("n")) throw new Exception("No se borrará la persona");
        return (Persona) storage.get(table, dni, Persona.class);
    }
    
    /**
     * Toma un csv en formato String y lo convierte en una tabla
     * en consola
     * @param csv
     */
    public void convertCSVToTable(String csv){
        String[] lines = csv.split("\n");
        String[] headers = lines[0].split(";"); // Obtengo los Headers
        
        int[] maxLengths = new int[headers.length];   // Longitud máxima de cada columna de la tabla   

        for (int i = 0; i < headers.length; i++) {
            final int index = i; // Necesario para usar el indice i, dentro del foreach (porque es otro scope)
            maxLengths[i] = 0;
            // Obtengo el tamaño de cada columna
            Arrays
                .stream(lines)
                .map(l -> l.split(";"))
                .forEach(l -> {
                    if(l[index].length() > maxLengths[index]){
                        maxLengths[index] = l[index].length();
                    }
                });
        }
        int totalLengths = Arrays.stream(maxLengths).sum() + headers.length * 3;
        
        System.out.println("-".repeat(totalLengths));
        for (int i=0;i<lines.length;i++){
            String[] values = lines[i].split(";");
            for(int j=0;j<headers.length;j++){
                String stringfied = values[j].toString();
                if(stringfied.length() < maxLengths[j]){
                    int spacesToAdd = maxLengths[j] - stringfied.length();
                    System.out.print((j == 0? "| ": " ") + stringfied + " ".repeat(spacesToAdd) + " |");
                }else{
                    System.out.print((j == 0? "| ": " ") + stringfied + " |");
                }
            }
            System.out.println(i==0 || i==lines.length-1? "\n" + "-".repeat(totalLengths):"");
        }
    }
}
