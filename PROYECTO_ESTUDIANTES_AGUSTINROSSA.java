package tpGrupalv02;

import java.io.*;

import java.util.*;


class Estudiante {
    private final int legajo;
    private final String nombreApellido;
    private final ArrayList<String> materiasAprobadas;

    public Estudiante(int legajo, String nombreApellido) {
        this.legajo = legajo;
        this.nombreApellido = nombreApellido;
        this.materiasAprobadas = new ArrayList<>();
    }

    public int getLegajo() {
        return legajo;
    }

    public String getNombreApellido() {
        return nombreApellido;
    }

    public ArrayList<String> getMateriasAprobadas() {
        return materiasAprobadas;
    }

    public void agregarMateriaAprobada(String materia) {
        materiasAprobadas.add(materia);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(legajo).append(" - ").append(nombreApellido).append(" - ");
        for (int i = 0; i < materiasAprobadas.size(); i++) {
            sb.append(materiasAprobadas.get(i));
            if (i < materiasAprobadas.size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }
}

public class tpGrupalV02 {
    public static void main(String[] args) {
        HashMap<Integer, Estudiante> estudiantes = new HashMap<>();
        cargarDatosDesdeArchivo("aprobaciones.txt", estudiantes);

        try (Scanner scanner = new Scanner(System.in)) {
            OUTER:
            while (true) {
                System.out.println("Seleccione una opción:");
                System.out.println("  ");
                System.out.println("1. Buscar estudiante por legajo");
                System.out.println("2. Agregar estudiante y materias");
                System.out.println("3. Agregar materias a un estudiante existente");
                System.out.println("4. Mostrar la lista de os estudiantes de la lista");
                System.out.println("0. Salir");
                int opcion = scanner.nextInt();
                switch (opcion) {
                    case 0:
                        break OUTER;
                    case 1:
                        System.out.print("Ingrese el número de legajo: ");
                        int legajoBuscado = scanner.nextInt();
                        buscarYMostrarEstudiante(estudiantes, legajoBuscado);
                        break;
                    case 2:
                        agregarEstudiante(estudiantes, scanner);
                        break;
                    case 3:
                        agregarMateriasAEstudiante(estudiantes, scanner);
                        break;
                   case 4:
  System.out.println("La lista de los estudiantes hasta ahora es la siguinte: .");

  for (Estudiante estudiante : estudiantes.values()) {
    System.out.println(estudiante);
    System.out.println("******************");
  }
  break;
          
                    default:
                        System.out.println("Opción no válida. Por favor, seleccione una opción válida.");

                        break OUTER;
                }
            }
     scanner.close();
            System.out.println("  ");
        }catch (Exception e) {
            System.out.println("No te apures, podés seguir los pasos con los datos que corresponden: ");
        guardarDatosEnArchivo("aprobaciones.txt", estudiantes);
        }
    }
    private static void cargarDatosDesdeArchivo(String archivo, HashMap<Integer, Estudiante> estudiantes) {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
          int legajo = Integer.parseInt(partes[0].trim());
                String nombreApellido = partes[1];
                String materiaAprobada = partes[2];

                if (estudiantes.containsKey(legajo)) {
                    estudiantes.get(legajo).agregarMateriaAprobada(materiaAprobada);
                } else {
                    Estudiante estudiante = new Estudiante(legajo, nombreApellido);
                    estudiante.agregarMateriaAprobada(materiaAprobada);
                    estudiantes.put(legajo, estudiante);
                }
            }
        } catch (IOException e) {
            System.out.println("Hola !! ");
        }
    }

    private static void buscarYMostrarEstudiante(HashMap<Integer, Estudiante> estudiantes, int legajoBuscado) {
        if (estudiantes.containsKey(legajoBuscado)) {
            Estudiante estudianteBuscado = estudiantes.get(legajoBuscado);
            System.out.println(estudianteBuscado);
        } else {
            System.out.println("Estudiante con legajo " + legajoBuscado + " no encontrado.");
        }
    }

    private static void agregarEstudiante(HashMap<Integer, Estudiante> estudiantes, Scanner scanner) {
        System.out.print("Ingrese el número de legajo del nuevo estudiante: ");
        int nuevoLegajo = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea
        System.out.print("Ingrese el nombre y apellido del nuevo estudiante: ");
        String nuevoNombre = scanner.nextLine();

        if (estudiantes.containsKey(nuevoLegajo)) {
            System.out.println("El estudiante ya existe en la lista.");
        } else {
            Estudiante nuevoEstudiante = new Estudiante(nuevoLegajo, nuevoNombre);
            estudiantes.put(nuevoLegajo, nuevoEstudiante);

            // Agregar materias aprobadas al nuevo estudiante
            while (true) {
                System.out.print("Ingrese una materia aprobada por el estudiante (o '0' para terminar): ");
                String materia = scanner.nextLine();
                if (materia.equals("0")) {
                    break;
                }
                nuevoEstudiante.agregarMateriaAprobada(materia);
            }
            
            System.out.println("Nuevo estudiante agregado con éxito.");
            
        }
    }

    private static void agregarMateriasAEstudiante(HashMap<Integer, Estudiante> estudiantes, Scanner scanner) {
        System.out.print("Ingrese el número de legajo del estudiante al que desea agregar materias: ");
        int legajoExistente = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea

        if (estudiantes.containsKey(legajoExistente)) {
            Estudiante estudianteExistente = estudiantes.get(legajoExistente);
            System.out.println("Estudiante: " + estudianteExistente.getNombreApellido());
            
            while (true) {
                System.out.print("Ingrese una materia aprobada por el estudiante (o '0' para terminar): ");
                String materia = scanner.nextLine();
                if (materia.equals("0")) {
                    break;
                }
                estudianteExistente.agregarMateriaAprobada(materia);
            }
            System.out.println("Materias agregadas con éxito.");
        } else {
            System.out.println("Estudiante con legajo " + legajoExistente + " no encontrado.");
        }
    }

    private static void guardarDatosEnArchivo(String archivo, HashMap<Integer, Estudiante> estudiantes) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(archivo))) {
            for (Estudiante estudiante : estudiantes.values()) {
                int legajo = estudiante.getLegajo();
                String nombreApellido = estudiante.getNombreApellido();
                ArrayList<String> materiasAprobadas = estudiante.getMateriasAprobadas();

                for (String materia : materiasAprobadas) {
                    writer.println(legajo + "," + nombreApellido + "," + materia);
                }
            }
        } 
        
        catch (IOException e) {
            System.out.println("Algo  no salio bien vuelva a ingresar los datos ");
        }
    }
}