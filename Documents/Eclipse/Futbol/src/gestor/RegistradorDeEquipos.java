package gestor;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import clases.Equipo;
import clases.Jugador;

public class RegistradorDeEquipos {

	public static void run() throws ParseException, IOException {
		Scanner sn = new Scanner(System.in);
		boolean salir = false;
		boolean salir2 = false;
		
		Equipo equipo = new Equipo();
		
		System.out.println("¿Cual es el nombre del equipo?");
		equipo.setNombre(sn.nextLine());
		
		System.out.println("¿Cual es el estadio del equipo?");
		equipo.setEstadio(sn.nextLine());

		while (!salir) {
			System.out.println("¿Quieres introducir un jugador o archivar el equipo? (J o A)");
			String opcion = sn.nextLine();
			if(opcion.equalsIgnoreCase("J")) {
				Jugador jugador = new Jugador();
				
				System.out.println("Digame el nombre del jugador.\n");
				jugador.setNombreCompleto(sn.nextLine());
				
				System.out.println("Digame el numero del jugador.\n");
				jugador.setNumero(sn.nextLine());
			
				System.out.println("Digame la fecha de nacimiento (dd/MM/yyyy).\n");
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				jugador.setFechaNac(formatter.parse(sn.nextLine()));
				
				System.out.println("Digame el sueldo del jugador.\n");
				jugador.setSueldo(Double.parseDouble(sn.nextLine()));
			
				equipo.getListaJugadores().add(jugador);
				System.out.println("Se ha guardado correctamente. \n");
			}
			else if(opcion.equalsIgnoreCase("A")){
				equipo.toString();
				while (!salir2) {
					System.out.println("¿Quieres salir o guardar estos datos? (S o G). \n");
					String opcion2 = sn.nextLine();
					if(opcion2.equalsIgnoreCase("S")) {
						System.out.println("Adios amigo.");
						salir2=true;
					}
					else if(opcion2.equalsIgnoreCase("G")){
						equipo.guardarEnFichero();
						System.out.println("Se ha guardado correctamente. \n");
						salir2=true;
					}
					else {
						System.out.println("No has elegido una opción correcta. \n");
					}
				}
				salir=true;
			}
			else {
				System.out.println("No has introducido la intrucción requerida.\n");
			}
		}
		sn.close();
	}
}
