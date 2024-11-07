package ui;

import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;

import modelo.persona.Cliente;
import modelo.persona.Persona;
import modelo.persona.PersonaType;
import modelo.persona.Proveedor;
import modelo.persona.Vendedor;
import negocio.mysql.SimpleORM;

public class CLI implements IUi {
    private int selectedItem = 0;
    private boolean exit = false;
    private final Scanner scanner = new Scanner(System.in);
    private final SimpleORM storage;

    public CLI(SimpleORM storage) {
        this.storage = storage;
        while (!exit) {
            render();
        }
        scanner.close();
    }

    public void render() {
        renderMenu();
    }

    public void renderMenu() {
        System.out.println("=========== Menu =============");
        System.out.println("1. Clientes");
        System.out.println("2. Proveedores");
        System.out.println("3. Vendedores");
        System.out.println("4. Salir");
        System.out.println("==============================");
        this.selectedItem = scanner.nextInt();
        
        switch (this.selectedItem) {
            case 1 -> renderSubMenu(PersonaType.CLIENTE);
            case 2 -> renderSubMenu(PersonaType.PROVEEDOR);
            case 3 -> renderSubMenu(PersonaType.VENDEDOR);
            case 4 -> {
                System.out.println("Has seleccionado Salir");
                exit = true;
            }
            default -> System.out.println("Opcion no valida");
        }
        System.out.flush();
        System.out.println();
    }

    public void renderSubMenu(PersonaType entityType) {
        System.out.println("=========== " + entityType + " =============");
        System.out.println("1. Crear");
        System.out.println("2. Listar");
        System.out.println("3. Actualizar");
        System.out.println("4. Eliminar");
        System.out.println("5. Volver");
        System.out.println("==============================");
        this.selectedItem = scanner.nextInt();
        
        try {
            switch (this.selectedItem) {
                case 1 -> createEntity(entityType);
                case 2 -> listEntities(entityType);
                case 3 -> updateEntity(entityType);
                case 4 -> deleteEntity(entityType);
                case 5 -> System.out.println("Has seleccionado Volver");
                default -> {
                    System.out.println("Opción no válida");
                    renderSubMenu(entityType);
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void createEntity(PersonaType entityType) throws Exception {
        Persona entity = getDataOfNewPersona(entityType);
        storage.create(entity);
        System.out.println(entityType + " creado exitosamente.");
    }

    private void listEntities(PersonaType entityType) throws Exception {
        String csvData = storage.generateCsv(entityType.createPersona().getClass());
        convertCSVToTable(csvData);
    }

    private void updateEntity(PersonaType entityType) throws Exception {
        Map<String, String> updatedData = getDataToUpdatePersona(entityType);
        Persona entity = entityType.createPersona();
        Object idValue = storage.getIdFieldValue(entity);
        storage.update(updatedData, Integer.parseInt(idValue.toString()));
        System.out.println(entityType + " actualizado correctamente.");
    }

    private void deleteEntity(PersonaType entityType) throws Exception {
        Persona personaToDelete = getPersonaToDelete(entityType);
        int idValue = (int) storage.getIdFieldValue(personaToDelete);
        storage.delete(entityType.getClass(), idValue);
        System.out.println(entityType + " eliminado correctamente.");
    }

    public Persona getDataOfNewPersona(PersonaType typeOfPerson) {
        // Solicitar y capturar los datos básicos
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
        
        // Crear la instancia según el tipo
        return switch (typeOfPerson) {
            case CLIENTE -> new Cliente(scanner.next(), nombre, apellido, dni, telefono, email);
            case VENDEDOR -> new Vendedor(scanner.next(), nombre, apellido, dni, telefono, email);
            case PROVEEDOR -> new Proveedor(scanner.next(), scanner.next(), scanner.next(), nombre, apellido, dni);
            default -> throw new IllegalArgumentException("Tipo de persona no válido");
        };
    }

    public Map<String, String> getDataToUpdatePersona(PersonaType typeOfPerson) throws Exception {
        System.out.println("Ingresa el dni");
        int dni = scanner.nextInt();
        Persona persona = (Persona) storage.findByField(typeOfPerson.createPersona().getClass(),"dni", dni);
        
        System.out.println("Ingresa el nombre [" + persona.getNombre() + "]");
        String nombre = scanner.next();
        System.out.println("Ingresa el apellido [" + persona.getApellido() + "]");
        String apellido = scanner.next();
        System.out.println("Ingresa el email [" + persona.getEmail() + "]");
        String email = scanner.next();
        System.out.println("Ingresa el telefono [" + persona.getTelefono() + "]");
        String telefono = scanner.next();
        
        Map<String, String> updatedData = Map.of(
            "nombre", checkEmpty(nombre, persona.getNombre()),
            "apellido", checkEmpty(apellido, persona.getApellido()),
            "dni", String.valueOf(dni),
            "telefono", checkEmpty(telefono, persona.getTelefono()),
            "email", checkEmpty(email, persona.getEmail())
        );

        if (typeOfPerson == PersonaType.CLIENTE) {
            System.out.println("Ingresa el CUIL [" + ((Cliente) persona).getCuil() + "]");
            updatedData.put("cuil", checkEmpty(scanner.next(), ((Cliente) persona).getCuil()));
        }
        
        return updatedData;
    }

    public Persona getPersonaToDelete(PersonaType typeOfPerson) throws Exception {
        System.out.println("Ingresa el dni del/la " + typeOfPerson + " a eliminar");
        int dni = scanner.nextInt();
        return (Persona) storage.findByField(typeOfPerson.createPersona().getClass(), "dni", dni);
    }

    public String checkEmpty(String input, String defaultValue) {
        return (input == null || input.trim().isEmpty()) ? defaultValue : input;
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
