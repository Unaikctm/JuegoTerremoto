package clases;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
import objeto.*;

public class Main {
	
	private static ArrayList<Terremoto> listaDeTerremotos  = new ArrayList<Terremoto>();
	
	private static Persona personaje;
	
	private static Persona personajeAnterior;
	
	private static String[] listaNiveles= {"Bosque","Montaña","Playa","Puerto","Escuela","Hospital","Cárcel","Faro","Almacén","Estadio de fútbol","Barco","Desierto","Observatorio","Avion","Coche"};
	
	private final static int SALIR=0;
	private final static int VISUALIZAR_TERREMOTOS = 1;
	private final static int EDITAR_TERREMOTOS = 2;
	private final static int ELIMINAR_TERREMOTOS = 3;
	private final static int JUEGO_INTERACTIVO = 4;
	
	private final static int FECHA = 1;
	private final static int HORA = 2;
	private final static int LATITUD = 3;
	private final static int LONGITUD = 4;
	private final static int PROFUNDIDAD = 5;
	private final static int MAGNITUD = 6;
	private final static int TIPOMAG = 7;
	private final static int LOCALIZACION = 8;
	
	public static void main(String[] args) {
		cargarFichero();
		Boolean salir=false;
		Scanner scan = new Scanner(System.in);
		while (!salir) {
			int menu = menuPrincipal(scan);
			switch(menu) {

			case SALIR:
				salir=salirMenu(scan);
				break;

			case VISUALIZAR_TERREMOTOS:
				visualizarTerremotos();
				break;

			case EDITAR_TERREMOTOS:
				editarTerremotos(listaDeTerremotos, scan);
				break;

			case ELIMINAR_TERREMOTOS:
				eliminarTerremotos(listaDeTerremotos, scan);
				break;
			
			case JUEGO_INTERACTIVO:
				personaje=creadorPersonaje(scan,personajeAnterior);
				personajeAnterior=personaje;
				slowPrint("Este es tu personaje: "+personaje.toString());
				slowPrint("¡Comienza el juego!\n");
				
				juego(listaDeTerremotos, scan, listaNiveles, personaje);
				break;
			default:
				System.out.println("No has introducido un valor valido");
			}
			
		}
	}

	private static void juego(ArrayList<Terremoto> lista, Scanner scanner, String[] listaNiveles, Persona personaje) {
		System.out.println("Hola! En este juego tendrás que tomar decisiones sobre como actuar con un terremoto haciendo temblar todo a tu lado.");
		slowPrint("El resultado de tus decisiones hará que aumente o disminuya tu vida, que empezará con un valor de 10.");
		slowPrint("Tienes Alzheimer y no recuerdas como llegas a los sitios");
		slowPrint("Utiliza las teclas W(Avanzar), A(Ir hacia la izquierda), S(Retroceder), D(Ir hacia la derecha) para tomar decisiones.");
		slowPrint("Disfruta el juego!");
		Boolean finTerremotos=false;
		int lugaresSinVer=15;
		int randomLugar;
		Boolean ganador = null;
		
		//INICIO DEL JUEGO
		while(!finTerremotos) {
			lugaresSinVer--;
			System.out.println("\nTienes "+personaje.getVidas()+" vidas\n");
			randomLugar =(int) Math.round(Math.random()*14);
			int randomTerremoto =(int) Math.round(Math.random()*(listaDeTerremotos.size()-1));
			String situacion="";
			
			//Elige una situacion aleatoriamente
			while (situacion=="") {
				if (listaNiveles[randomLugar]==null){
					randomLugar =(int) Math.round(Math.random()*14);
				}else {
					situacion=listaNiveles[randomLugar];
				}
			}
			
			Terremoto terremotoActual=listaDeTerremotos.get(randomTerremoto);
			
			switch (situacion) {//{"Bosque","Montaña","Playa","Puerto","Escuela","Hospital","Cárcel","Faro","Almacén","Estadio de fútbol","Barco","Desierto","Observatorio","Avion","Coche"};
				case "Bosque": 
					bosqueSituacion(scanner, personaje);
					break;
				case "Montaña":
					montañaSituacion(scanner, personaje, terremotoActual);
					break;
				case "Playa":
					playaSituacion(scanner, personaje, terremotoActual);
					break;
				case "Puerto":
					puertoSituacion(scanner, personaje, terremotoActual);
					break;
				case "Escuela":
					escuelaSituacion(scanner, personaje);
					break;
				case "Hospital":
					hospitalSituacion(scanner, personaje, terremotoActual);
					break;
				case "Cárcel":
					carcelSituacion(scanner, personaje);
					break;
				case "Faro":
					faroSituacion(scanner, personaje, terremotoActual);
					break;
				case "Almacén":
					almacenSituacion(scanner, personaje);
					break;
				case "Estadio de fútbol":
					estadioSituacion(scanner, personaje);
					break;
				case "Barco":
					barcoSituacion(scanner, personaje, terremotoActual);
					break;
				case "Desierto":
					desiertoSituacion(scanner, personaje, terremotoActual);
					break;
				case "Observatorio":
					observatorioSituacion(scanner, personaje, terremotoActual);
					break;
				case "Avion":
					avionSituacion(personaje);
					break;
				case "Coche":
					cocheSituacion(scanner, personaje);
					break;
				default:
					throw new IllegalArgumentException("Unexpected value: " + randomLugar);
				}
			
			listaNiveles[randomLugar]=null;
			if (personaje.getVidas()<=0) {
				ganador=false;
				finTerremotos=true;
			}else if(lugaresSinVer==3) {
				ganador=true;
				finTerremotos=true;
			}
		}
		if (ganador==true) {
			personaje.setVidas(10);
			slowPrint("\nTodo deja de temblar y empiezas a llorar de alegría, has pasado un muy mal rato pero...");
			slowPrint("\nHAS SOBREVIVIDO A LOS TERREMOTOS, ENHORABUENA!!!!\n");
		}
		else {
			personaje.setVidas(10);
			slowPrint("\nHAS MUERTO, FIN DEL JUEGO\n");
		}
	}

	private static void carcelSituacion(Scanner scanner, Persona personaje) {
		slowPrint("Llegas a la cárcel y el silencio que encuentras te abruma.");
		slowPrint("Recuerdas que toda cárcel tiene una enfermeria dentro y parece que está vacia");
		slowPrint("¿Decides entrar a la cárcel (w) o irte? (s)");
		boolean salir=false;
		while(!salir) {
			String teclado=scanner.nextLine();
			if (teclado.equalsIgnoreCase("S")) {
				slowPrint("Comienzas a irte y escuchas gritos en el interior");
				slowPrint("No quieres saber que habia dentro");
				salir=true;
			}
			else if(teclado.equalsIgnoreCase("W")) {
				slowPrint("Entras y sientes que algo va mal.");
				slowPrint("Resulta que hay presos convictos escondidos en la cárcel");
				slowPrint("Corren hacia ti con la intención de robarte");
				if(personaje.getAtletico()==true) {
					slowPrint("Gracias a tus capacidades atléticas consigues escapar solo recibiendo un golpe en el abdomen");
					slowPrint("Pierdes 2 puntos de vida");
					personaje.setVidas(personaje.getVidas()-2);
					salir=true;
				}
				else {
					slowPrint("Recibes varios golpes por todo el cuerpo, pero ven que no tienes nada");
					slowPrint("Te dejan huir por pena, sienten verdadera lástima por ti");
					slowPrint("No eras lo suficientemente atlético para escapar");
					slowPrint("Pierdes 7 puntos de vida");
					personaje.setVidas(personaje.getVidas()-7);
					salir=true;
				}
			}
			else {
				slowPrint("¿Decides entrar a la cárcel (w) o irte? (s)");
			}
		}
	}

	private static void faroSituacion(Scanner scanner, Persona personaje, Terremoto terremotoActual) {
		slowPrint("Llegas al faro y comienzas a subir las escaleras agarrandote a lo que puedas.");
		slowPrint("Cuanto más subes hay una luz que se intensifica");
		slowPrint("¿Quieres seguir subiendo (w) o bajar? (s)");
		boolean salir=false;
		while(!salir) {
			String teclado=scanner.nextLine();
			if (teclado.equalsIgnoreCase("S")) {
				if (terremotoActual.getPeligroso()==true) {
					slowPrint("Comienzas a bajar el faro debido al miedo que te entra al pensar en la caída.");
					slowPrint("La magnitud del terremoto es "+terremotoActual.getMagnitud());
					slowPrint("Aun así no tienes el suficiente equilibrio y te caes por las escaleras");
					slowPrint("Pierdes 4 puntos de vida.");
					personaje.setVidas(personaje.getVidas()-4);
					salir=true;
				}
				else{
					slowPrint("Comienzas a bajar el faro debido al miedo que te entra al pensar en la caída.");
					slowPrint("La magnitud del terremoto es "+terremotoActual.getMagnitud());
					slowPrint("Aguantas el seismo con algo de complicación pero bajas sin problemas");
					salir=true;
				}
			}
			else if(teclado.equalsIgnoreCase("W")) {
				if (terremotoActual.getPeligroso()==true) {
					slowPrint("Comienzas a subir el faro debido al miedo que te entra al pensar en la caída.");
					slowPrint("La magnitud del terremoto es "+terremotoActual.getMagnitud());
					slowPrint("Aun así no tienes el suficiente equilibrio y te caes por las escaleras");
					slowPrint("Pierdes 6 puntos de vida.");
					personaje.setVidas(personaje.getVidas()-6);
					salir=true;
				}
				else{
					slowPrint("Comienzas a subir el faro debido al miedo que te entra al pensar en la caída.");
					slowPrint("La magnitud del terremoto es "+terremotoActual.getMagnitud());
					slowPrint("Aguantas el seismo con algo de complicación pero subes sin problemas");
					slowPrint("Arriba encuentras algo increible...");
					slowPrint("La lámpara del faro, no se que te esperabas que fuera");
					slowPrint("Bajas y del cabreo que te llevas le pegas una patada a una pared y te haces daño.");
					slowPrint("Pierdes 1 punto de vida");
					personaje.setVidas(personaje.getVidas()-1);
					salir=true;
				}
				
			}else {
				slowPrint("¿Quieres seguir subiendo (w) o bajar? (s)");
			}
		}
	}

	private static void observatorioSituacion(Scanner scanner, Persona personaje, Terremoto terremotoActual) {
		slowPrint("Decidiste subir al observatorio, el terremoto no debería de afectar aquí y piensas que estás seguro.");
		slowPrint("¿Entras al observatorio (w) o decides rodearlo?");
		boolean salir=false;
		while(!salir) {
			String teclado=scanner.nextLine();
			if (teclado.equalsIgnoreCase("W")){
				if (terremotoActual.getProfundidad()<5.0) {
					slowPrint("La profundidad del terremoto es "+terremotoActual.getProfundidad());
					slowPrint("Es suficientemente superficial para notarlo");
					slowPrint("Encuentras un telescopio y ves las estrellas antes de irte");
					salir=true;
				}else{
					slowPrint("La profundidad del terremoto es "+terremotoActual.getProfundidad());
					slowPrint("Es suficientemente profundo como para notar un ligero temblor sin consecuencias.");
					slowPrint("Encuentras un telescopio y ves las estrellas antes de irte");
					salir=true;
				}
			}
			else if (teclado.equalsIgnoreCase("A")||teclado.equalsIgnoreCase("S")||teclado.equalsIgnoreCase("D")) {
				if (terremotoActual.getProfundidad()<5.0) {
					slowPrint("La profundidad del terremoto es "+terremotoActual.getProfundidad());
					slowPrint("Es suficientemente superficial para hacer romper cristales exteriores");
					slowPrint("Te caen en la cabeza y sufres bastante daño.");
					slowPrint("Pierdes 5 puntos de vida");
					personaje.setVidas(personaje.getVidas()-5);
					salir=true;
				}else {
					slowPrint("La profundidad del terremoto es "+terremotoActual.getProfundidad());
					slowPrint("Pasas rodeando el observatorio sin ningún problema.");
					salir=true;
				}
			}
			else {
				slowPrint("¿Entras al observatorio (w) o decides rodearlo?");
			}		
		}
	}

	private static void almacenSituacion(Scanner scanner, Persona personaje) {
		slowPrint("Llegas al almacén y te toca buscar los planos si tienes la necesidad de curarte o mejoras físicas.");
		slowPrint("Todo está temblando y escuchas ruidos fuertes en el interior");
		slowPrint("¿Quieres entrar al almacén a buscar el plano (W) o buscar algo fuera (S)?");
		
		//Generador de pasillos importantes
		int num1=(int) Math.round(Math.random()*6);
		int num2;
		do{
			num2=(int) Math.round(Math.random()*6);
		}while (num2==num1);
		String pasillo1= ""+num1;
		String pasillo2= ""+num2;
		
		boolean salir=false;
		while(!salir) {
			String teclado=scanner.nextLine();
			//ENTRA DENTRO
			if (teclado.equalsIgnoreCase("W")) {
				int contadorPasillos =0;
				slowPrint("Entras dentro y encuentras el plano del almacén, descubres que hay 6 pasillos distintos");
				slowPrint("Te interesan el pasillo de deportes para mejorar tus capacidades atléticas o el de medicina para curarte");
				slowPrint("Resulta que el número de los pasillos está borrado y no sabes que hacer");
				while(contadorPasillos <3) {
					slowPrint("¿Qué Nº de pasillo eliges?");
					String teclado2=scanner.nextLine();
					//ELIGES PASILLO BUENO
					if (teclado2.equalsIgnoreCase(pasillo1)||teclado2.equalsIgnoreCase(pasillo2)) {
						slowPrint("Empiezas a avanzar por el pasillo y comienzan a caer objetos de las baldas");
						slowPrint("¿Decides correr y buscar el objeto que buscas (W), salir y buscar en otro pasillo (S) o meterte en un armario y esperar a que se calme un poco todo? (D)");
						boolean salir2=false;
						while (!salir2) {
							teclado=scanner.nextLine();
							//CORRES ESQUIVANDO
							if(teclado.equalsIgnoreCase("W")) {
								if (personaje.getAtletico()==true) {
									slowPrint("Consigues avanzar esquivando los objetos y encuentras lo que necesitas");
									if (teclado2.equalsIgnoreCase(pasillo1)) {
										slowPrint("Encuentras coges el botiquin de primeros auxilios");
										slowPrint("Te curas 8 puntos de vida y huyes del instituto como puedes");
										personaje.setVidas(personaje.getVidas()+8);
										contadorPasillos++;
										salir2=true;
									}else{
										slowPrint("Dentro del pasillo encuentras equipo deportivo");
										slowPrint("Te lo equipas y te vuelves atlético");
										personaje.setAtletico(true);
										contadorPasillos++;
										salir2=true;
									}
								}else {
									slowPrint("Te tropiezas mientras avanzas y te cae un objeto encima");
									slowPrint("Pierdes 5 puntos de vida");
									if (personaje.getVidas()-5<=0) {
										personaje.setVidas(personaje.getVidas()-5);
										slowPrint("Tienes "+personaje.getVidas()+" vidas");
										salir2=true;
									}
									else {
										personaje.setVidas(personaje.getVidas()-5);
										slowPrint("Aun así llegas al lugar de interés");
										if (teclado2.equalsIgnoreCase(pasillo1)) {
											slowPrint("Encuentras coges el botiquin de primeros auxilios");
											slowPrint("Te curas 8 puntos de vida y huyes del instituto como puedes");
											personaje.setVidas(personaje.getVidas()+8);
											contadorPasillos++;
											salir2=true;
										}else{
											slowPrint("Dentro del pasillo encuentras equipo deportivo");
											slowPrint("Te lo equipas y te vuelves atlético");
											personaje.setAtletico(true);
											contadorPasillos++;
											salir2=true;
										}
									}
									
								}
							//TE CAGAS
							}else if(teclado.equalsIgnoreCase("S")){
								contadorPasillos++;
								salir2=true;
							//TE ESCONDES SI ERES BAJO
							}else if(teclado.equalsIgnoreCase("D")){
								if (personaje.getAltura()>1.75) {
									slowPrint("No entras en la balda por tu altura y te cae un objeto encima");
									slowPrint("Pierdes 5 puntos de vida");
									personaje.setVidas(personaje.getVidas()-5);
									contadorPasillos++;
									salir2=true;
								}else {
									slowPrint("Esperas a que todo pase y buscas lo que necesitas.");
									if (teclado2.equalsIgnoreCase(pasillo1)) {
										slowPrint("Encuentras coges el botiquin de primeros auxilios");
										slowPrint("Te curas 8 puntos de vida y huyes del instituto como puedes");
										personaje.setVidas(personaje.getVidas()+8);
										contadorPasillos++;
										salir2=true;
									}else{
										slowPrint("Dentro del pasillo encuentras equipo deportivo");
										slowPrint("Te lo equipas y te vuelves atlético");
										personaje.setAtletico(true);
										contadorPasillos++;
										salir2=true;
									}
								}
							}
							else {
								slowPrint("¿Decides correr y buscar el objeto que buscas (W), salir y buscar en otro pasillo (S) o meterte en un armario y esperar a que se calme un poco todo? (D)");
							}
						}
						contadorPasillos++;
					//ELIGES PASILLO MALO
					}else if(!(teclado2.equalsIgnoreCase(pasillo1))||!(teclado2.equalsIgnoreCase(pasillo1))){
						slowPrint("El pasillo se encuentra tranquilo, pero tiene nada de tu interes.");
						slowPrint("Decides cambiar de pasillo");
						contadorPasillos++;
					}
				}
				slowPrint("El almacén empieza a derrumbarse y sales corriendo antes de que te quedes atrapado dentro.");
				salir=true;
			}
						
			//IGNORAS ALMACEN
			else if(teclado.equalsIgnoreCase("S")){
				slowPrint("Buscas por la parte trasera algo que te pueda ser útil.");
				slowPrint("Resulta que encuentras un pack de tiritas en una caja.");
				slowPrint("Te pones una tirita y te recuperas 1 punto de vida");
				personaje.setVidas(personaje.getVidas()+1);
				salir=true;
				
			}else {
				slowPrint("¿Quieres entrar al almacén a buscar el plano (W) o buscar algo fuera (S)?");
			}
			salir=true;
		}
	}

	private static void estadioSituacion(Scanner scanner, Persona personaje) {
		slowPrint("Es sábado y vas al Camp Nou a ver un partido de fútbol");
		slowPrint("Te encuentras viendo el clásico Barcelona VS Real Madrid de esta temporada cuando el terremoto se intensifica.");
		slowPrint("Sales por la puerta principal (w) o intentas buscar una posible salida de emergencia (s)?");
		boolean salir=false;
		while(!salir) {
			String teclado=scanner.nextLine();
			if (teclado.equalsIgnoreCase("w")) {
				slowPrint("Intentas esquivar a la gente pero te es imposible.");
				slowPrint("Escuchas gritos como \"Viva la Xavineta\" o \"Vinicius Balon de oro\" mientras te toca esquivar a las personas");
				slowPrint("PREPARATE, ESCRIBE LO QUE SALGA EN PANTALLA");
				if (personaje.getAtletico()==true) {
					slowPrint("EMPUJA EMPUJA");
					String escrito = null;
					try {
						escrito=scannerTiempo();
					} catch (IOException e) {
						e.printStackTrace();
					}
					if (escrito.equalsIgnoreCase("EMPUJA EMPUJA")) {
						salir = ganarFutbol();
					}else {
						salir = perderFutbol(personaje);
					}
				}else {
					slowPrint("EMPUJA EMPUJA");
					String escrito = null;
					try {
						escrito=scannerTiempo();
					} catch (IOException e) {
						e.printStackTrace();
					}
					if (escrito.equalsIgnoreCase("EMPUJA EMPUJA")) {
						slowPrint("CORRE RAPIDO");
						try {
							escrito=scannerTiempo();
						} catch (IOException e) {
							e.printStackTrace();
						}
						if (escrito.equalsIgnoreCase("CORRE RAPIDO")) {
							slowPrint("DESLIZATE Y ESQUIVA");
							try {
								escrito=scannerTiempo();
							} catch (IOException e) {
								e.printStackTrace();
							}
							if (escrito.equalsIgnoreCase("DESLIZATE Y ESQUIVA")) {
								salir = ganarFutbol();
							}else {
								salir = perderFutbol(personaje);
							}
							
						}else {
							salir = perderFutbol(personaje);
						}
					
					}else {
						salir = perderFutbol(personaje);
					}
				}		
			}
			else if(teclado.equalsIgnoreCase("S")) {
				slowPrint("Encuentras facilmente la salida de emergencia.");
				slowPrint("Escuchas gritos como \"Vinicius mono\" o \"FC Palancas\" mientras bajas rápidamente.");
				slowPrint("Sales del estadio sin más complicación");
				salir=true;
			}else {
				slowPrint("Sales por la puerta principal (w) o intentas buscar una salida de emergencia (s)?");
			}
		}
	}

	private static boolean ganarFutbol() {
		boolean salir;
		slowPrint("Muy bien, lograste salir del estadio.");
		slowPrint("Recibes insultos de dudosa moralidad pero no recibes ningún daño.");
		salir=true;
		return salir;
	}

	private static boolean perderFutbol(Persona personaje) {
		boolean salir;
		slowPrint("¡Has fallado!");
		slowPrint("La gente te empuja por detrás y acabas cayendote al suelo.");
		slowPrint("Empiezas a ser pisado y aplastado sin ningún tipo de escrúpulos.");
		slowPrint("Te consigues levantar pero te has hecho un esguince en el tobillo.");
		slowPrint("Pierdes 5 puntos de vida y tu capacidad atlética.");
		personaje.setVidas(personaje.getVidas()-5);
		personaje.setAtletico(false);
		salir=true;
		return salir;
	}

	private static void barcoSituacion(Scanner scanner, Persona personaje, Terremoto terremotoActual) {
		slowPrint("Subes a un barco creyendo que los problemas de tierra eran los únicos peligrosos, pero no es así.");
		slowPrint("A lo lejos ves una ola de tamaño considerable formandose a lo lejos");
		slowPrint("¿Decides ir a la proa o a la popa del barco?");
		boolean salir=false;
		while(!salir) {
			String teclado=scanner.nextLine();
			if (teclado.equalsIgnoreCase("S")) {
				slowPrint("Llegas a la popa y la ola cada vez se acerca más.");
				slowPrint("La magnitud del terremoto es "+terremotoActual.getMagnitud());
				if (terremotoActual.getPeligroso()==true) {
					slowPrint("Una vez la tienes cerca ves la magnitud de la ola y comprendes el peligro de la situación...");
					slowPrint("¡¡¡¡ES UN TSUNAMI!!!!");
					if (personaje.getPeso()>75.0) {
						slowPrint("Aun así, eres lo suficientemente pesado como para agarrarte al barco y no ser llevado por el agua");
						slowPrint("Llegas a tierra y vives para contarlo.");
						salir=true;
					}
					else {
						slowPrint("Eres muy ligero y el tsunami te lleva consigo una vez llega al barco.");
						slowPrint("Mueres ahogado.");
						personaje.setVidas(0);
						salir=true;
					}
				}else {
					slowPrint("La ola no era para tanto y la vista te ha fallado.");
					slowPrint("Vuelves a tierra sin mayor complicación.");
					salir=true;
				}
				
			}else if(teclado.equalsIgnoreCase("W")){
				slowPrint("Llegas a la proa y la ola cada vez se acerca más.");
				slowPrint("La magnitud del terremoto es "+terremotoActual.getMagnitud());
				if (terremotoActual.getPeligroso()==true) {
					slowPrint("Una vez la tienes cerca ves la magnitud de la ola y comprendes el peligro de la situación...");
					slowPrint("¡¡¡¡ES UN TSUNAMI!!!!");
					if (personaje.getAtletico()==true && personaje.getPeso()>75.0) {
						slowPrint("Aun así, eres lo suficientemente atlético para llegar a la popa y no caerte en el intento.");
						slowPrint("Aunque el tsunami impacte, aguantas como puedes y este no te lleva debido a tu peso.");
						slowPrint("Llegas a tierra y vives para contarlo.");
						salir=true;
					}
					else if (personaje.getAtletico()==false){
						slowPrint("En el intento de llegar a la popa para resistir mejor el tsunami te resbalas y caes.");
						slowPrint("Tardas demasiado en levantarte y el tsunami te lleva consigo una vez llega al barco.");
						slowPrint("Mueres ahogado.");
						personaje.setVidas(0);
						salir=true;}
				}else {
					slowPrint("La ola no era para tanto y la vista te ha fallado.");
					slowPrint("Vuelves a tierra sin mayor complicación.");
					salir=true;
				}
			}else {
				slowPrint("¿Decides ir a la proa o a la popa del barco?");
			}
		}
	}

	private static void desiertoSituacion(Scanner scanner, Persona personaje, Terremoto terremotoActual) {
		slowPrint("Te aproximas a un desierto en el que el calor es tu 2do mayor peligro, recuerda que están ocurriendo terremotos.");
		slowPrint("El terremoto se comienza a intensificar y tienes que decidir rápido que hacer");
		slowPrint("Delante tuyo hay una duna que no sabemos que puede tener detrás, pero hacia los lados parece un camino más llano");
		slowPrint("¿Hacia que dirección avanzas?");
		boolean salir=false;
		while(!salir) {
			String teclado=scanner.nextLine();
			if (teclado.equalsIgnoreCase("A")||teclado.equalsIgnoreCase("D")||teclado.equalsIgnoreCase("S")) {
				slowPrint("Avanzas por el desierto y el terremoto comienza a abrir el suelo bajo tus pies.");
				slowPrint("La magnitud del terremoto es "+terremotoActual.getMagnitud());
				if (terremotoActual.getPeligroso()==true && personaje.getAtletico()==false) {
					slowPrint("La arena te comienza a tragar y no tienes fuerza suficiente para salir de ahí");
					slowPrint("Nunca consigues salir de ahí y mueres ahogado");
					personaje.setVidas(0);
					salir=true;
				}else {
					slowPrint("Después de horas de camino ves el final del desierto.");
					slowPrint("Pese a los temblores constantes, consigues salir de ese infierno.");
					salir=true;
				}
				
			}else if(teclado.equalsIgnoreCase("W")){
				if (personaje.getAtletico()==true) {
					slowPrint("Gracias a tus capacidades fisicas llegas a la cima de la duna.");
					slowPrint("No puedes creer lo que ves, una zona con vegetación y un lago en el centro");
					slowPrint("Decides ir al oasis, pero una vez entras, nunca sales de allí, el cerebro te jugó una mala pasada.");
					slowPrint("Mueres de inanición.");
					personaje.setVidas(0);
					salir=true;
				}else {
					slowPrint("Te caes intentando subir la duna y te haces algo de daño, pero no hay ningún peligro mayor.");
					slowPrint("Decides tomar el camino de los lados y consigues salir del desierto después de horas de caminata.");
					slowPrint("Recibes 2 punto de daño");
					personaje.setVidas(personaje.getVidas()-2);
					salir=true;
				}
			}else {
				slowPrint("¿Hacia que dirección avanzas?");
			}
		}
	}

	private static void hospitalSituacion(Scanner scanner, Persona personaje, Terremoto terremotoActual) {
		slowPrint("Llegas al hospital y hay muchas personas heridas esperando la cola para ser atendidos.");
		slowPrint("Sientes que no puedes perder tanto tiempo y te planteas colarte en una habitación para ser atendido antes.");
		slowPrint("¿Decides colarte (w) o esperar pacientemente (s)?");
		boolean salir=false;
		while(!salir) {
			String teclado=scanner.nextLine();
			if (teclado.equalsIgnoreCase("S")) {
				slowPrint("Esperas una hora y llegado tu turno, te buscan en la base de datos y te indican una sala en la que serás atendido.");
				slowPrint("Te dan la opción de elegir la camilla, ¿Prefieres la del fondo (w) o la más cercana (d)?");
				boolean salir2=false;
				while (!salir2) {
					teclado=scanner.nextLine();
					if (terremotoActual.getPeligroso()==true && teclado.equalsIgnoreCase("w")) {
						slowPrint("La magnitud del terremoto es "+terremotoActual.getMagnitud());
						slowPrint("Te tumbas en la camilla y las ventanas se rompen y te cortas");
						slowPrint("Las enfermeras llegan rápido y no llega a más, pero has sufrido daños leves.");
						slowPrint("Recibes 2 puntos de daño.");
						personaje.setVidas(personaje.getVidas()-2);
						salir2=true;
					}
					else if(teclado.equalsIgnoreCase("d") || (terremotoActual.getPeligroso()==false && teclado.equalsIgnoreCase("w"))) {
						slowPrint("La magnitud del terremoto es "+terremotoActual.getMagnitud());
						slowPrint("Llegan las enfermeras y aunque todo tiemble, pueden sanarte sin problemas.");
						slowPrint("Te tumbas en la camilla y te curan 8 puntos de vida.");
						slowPrint("También te otorgan unas pastillas para mejorar el rendimiento físico, eres atlético a partir de ahora.");
						slowPrint("Agradeces a las enfermeras y sales del hospital.");
						personaje.setVidas(personaje.getVidas()+8);
						personaje.setAtletico(true);
						salir2=true;
					}
					else {
						slowPrint("Te dan la opción de elegir la camilla, ¿Prefieres la del fondo (w) o la más cercana (d)?");
					}
				}
				salir=true;			
			}
			else if(teclado.equalsIgnoreCase("W")){
				if (personaje.getAtletico()==true) {
					slowPrint("Gracias a tus capacidades fisicas te consigues colar rapidamente.");
					slowPrint("Encuentras unas vendas que utilizas para curarte, recuperas 3 puntos de vida.");
					slowPrint("Por el pasillo escuchas a las enfermeras llegar y huyes del hospital tan rápido como puedes.");
					personaje.setVidas(personaje.getVidas()+3);
					salir=true;
				}else {
					slowPrint("Justo antes de entrar en la habitación, te tropiezas debido a tu ineptitud atlética.");
					slowPrint("No recibes daños, pero eres lo suficientemente ruidoso para llamar la atención del guardia");
					slowPrint("Este te amenaza con un arma y decides salir del hospital para salir airoso");
					salir=true;
				}
			}else {
				slowPrint("¿Decides colarte (w) o esperar pacientemente (s)?");
			}
		}
	}

	private static void cocheSituacion(Scanner scanner, Persona personaje) {
		slowPrint("Mientras avanzas encuentras tu coche aparcado al borde de la calle, en el lado derecho.");
		slowPrint("Decides entrar dentro cuando el terremoto se intensifica");
		slowPrint("Los edificios a tu lado están temblando y no parecen seguros");
		slowPrint("¿Quieres arrancar el coche y empezar a moverte (w) o decides esconderte hasta que pase (s)?");	
		boolean salir=false;
		while(!salir) {
			String teclado=scanner.nextLine();
			if (teclado.equalsIgnoreCase("W")) {
				slowPrint("Arrancas el coche y conduces como puedas.");
				slowPrint("Justo donde estabas aparcado acaba de caer un escombro de un edificio");
				slowPrint("Giras la esquina y un árbol corta la carretera");
				slowPrint("No tienes mucho tiempo para reaccionar y tienes el árbol muy cerca");
				slowPrint("Decides acelerar para intentar partirlo (w), pegar un volantazo e intentar evitarlo (a)y(d) o usar el freno de mano e intentar frenar (s)");
				boolean salir2=false;
				while(!salir2) {
					teclado=scanner.nextLine();
					if (teclado.equalsIgnoreCase("W")) {
						slowPrint("Tomas la decisión de acelerar y vas de frente al árbol");
						slowPrint("El árbol es más duro de lo que creías y no logras partirlo");
						slowPrint("El coche queda destrozado... y tú también.");
						personaje.setVidas(0);
						salir2=true;
					}
					else if (teclado.equalsIgnoreCase("A")||teclado.equalsIgnoreCase("D")) {
						slowPrint("Das un volantazo con el que pones el coche en paralelo con el árbol");
						slowPrint("De la fuerza del giro y la del terremoto das unas cuantas vueltas de campana");
						slowPrint("El coche queda destrozado... y tú también.");
						personaje.setVidas(0);
						salir2=true;
					}
					else if (teclado.equalsIgnoreCase("S")) {
						slowPrint("El coche comienza a frenar y se acerca lentamente al árbol");
						slowPrint("Chocas con el árbol lo suficientemente rápido como para romper los cristales delanteros y activar el Airbag");
						slowPrint("Te cortas por los cristales pero sales vivo de la carretera");
						slowPrint("Pierdes 3 puntos de vida");
						personaje.setVidas(personaje.getVidas()-3);
						salir2=true;
					}else {
						slowPrint("Decides acelerar para intentar partirlo (w), pegar un volantazo e intentar evitarlo (a)y(d) o usar el freno de mano e intentar frenar (s)");
					}							
				}
				salir=true;
				
			}else if(teclado.equalsIgnoreCase("S")){
				slowPrint("La puerta del piloto se bloquea nada más entrar.");
				slowPrint("Escuchas un ruido y al asomarte por la ventana ves como un escombro cae de un edificio dirección a tu coche.");
				slowPrint("Elige donde colocarte para no recibir el impacto.");
				System.out.println("⠀⠀⠀⠀⡀⠄⢒⣒⣐⣒⣒⠀⠄⡀⠀⠀⠀");
				System.out.println("⠀⠀⣐⠁⡰⠋⠉⠉⠉⠉⠉⠉⢢⠀⡠⠀⠀");
				System.out.println("⠀⠀⡘⠋⠀⠀⠀⠀⠀⠀ ⠀⠀⠐⠣⡇⠀ ");
				System.out.println("⠀⢰⠀⠐⠀⠀ ⠀⠀⠀⠀⠀ ⠀⢁⠐⡇⠀");
				System.out.println("⠀⢸⠰⢡⣴⣶⣶⣶⡿⡒⣶⢲⣦⡴⡀⠇⠀");
				System.out.println("⠀⠰⢸⠘⣿⣿⣿⡿⡿⣜⣿⡿⣽⠇⣧⠦⡀");
				System.out.println("⠘⠚⢾⡆⣿⣿⠿⠵⡿⠯⠽⣿⣿⢀⠇⡓⠂");
				System.out.println("⠀⠀⢸⣱⢃1⠀⠀⠀ ⠀2⢘⢻⡆⠃⠀");
				System.out.println("⠀⠀⢸⣽⠈⠀⠀⠀⠀⠀ ⠀⢸⢣⡇⡄⠀");
				System.out.println("⠀⠀⡨⣽ 3⠀ 4⠀ 5⠘⠼⡅⠁⠀");
				System.out.println("⠀⠀⠀⡗⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⢸⠃⠀");
				System.out.println("⠀⠀⡇⡇⢀⠀⠀⠀⠀⠀  ⠀⢀⢸⠀⡇⠀");
				System.out.println("⠀⠀⣇⠁⣄⣀⠀⠀⠀⠀  ⣀⣡⠀⢠⠀⠀");
				System.out.println("⠀⠀⢉⢧⠻⣿⣿⡿⣿⣿⣿⣻⠿⣱⠿⠀⠀");
				System.out.println("⠀⠀⠈⠐⠁⠤⠤⠬⠥⠥⠤⠤⠈⠒⠁⠀⠀");
				boolean salir2=false;
				while(!salir2) {
					teclado=scanner.nextLine();
					if (teclado.equalsIgnoreCase("3")||teclado.equalsIgnoreCase("4")) {
						slowPrint("Los escombros aplastan el coche en las posiciones 1 2 y 5.");
						slowPrint("Sales del coche por la trasera izquierda y no recibes daño.");
						salir2=true;
					}else if(teclado.equalsIgnoreCase("1")||teclado.equalsIgnoreCase("2")||teclado.equalsIgnoreCase("5")){
						slowPrint("Los asientos delanteros y el asiento trasero derecho quedan aplastados por los escombros.");
						slowPrint("Mueres aplastado en tu coche");
						personaje.setVidas(0);
						salir2=true;
					}else {
						slowPrint("Elige donde colocarte para no recibir el impacto.");
						System.out.println("⠀⠀⠀⠀⡀⠄⢒⣒⣐⣒⣒⠀⠄⡀⠀⠀⠀");
						System.out.println("⠀⠀⣐⠁⡰⠋⠉⠉⠉⠉⠉⠉⢢⠀⡠⠀⠀");
						System.out.println("⠀⠀⡘⠋⠀⠀⠀⠀⠀⠀ ⠀⠀⠐⠣⡇⠀ ");
						System.out.println("⠀⢰⠀⠐⠀⠀ ⠀⠀⠀⠀⠀ ⠀⢁⠐⡇⠀");
						System.out.println("⠀⢸⠰⢡⣴⣶⣶⣶⡿⡒⣶⢲⣦⡴⡀⠇⠀");
						System.out.println("⠀⠰⢸⠘⣿⣿⣿⡿⡿⣜⣿⡿⣽⠇⣧⠦⡀");
						System.out.println("⠘⠚⢾⡆⣿⣿⠿⠵⡿⠯⠽⣿⣿⢀⠇⡓⠂");
						System.out.println("⠀⠀⢸⣱⢃1⠀⠀⠀ ⠀2⢘⢻⡆⠃⠀");
						System.out.println("⠀⠀⢸⣽⠈⠀⠀⠀⠀⠀ ⠀⢸⢣⡇⡄⠀");
						System.out.println("⠀⠀⡨⣽ 3⠀ 4⠀ 5⠘⠼⡅⠁⠀");
						System.out.println("⠀⠀⠀⡗⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⢸⠃⠀");
						System.out.println("⠀⠀⡇⡇⢀⠀⠀⠀⠀⠀  ⠀⢀⢸⠀⡇⠀");
						System.out.println("⠀⠀⣇⠁⣄⣀⠀⠀⠀⠀  ⣀⣡⠀⢠⠀⠀");
						System.out.println("⠀⠀⢉⢧⠻⣿⣿⡿⣿⣿⣿⣻⠿⣱⠿⠀⠀");
						System.out.println("⠀⠀⠈⠐⠁⠤⠤⠬⠥⠥⠤⠤⠈⠒⠁⠀⠀");
					}
				}
				salir=true;
			}else {
				slowPrint("¿Quieres arrancar el coche y empezar a moverte (w) o decides esconderte hasta que pase (s)?");
			}
		}
	}

	private static void avionSituacion(Persona personaje) {
		slowPrint("Llegas al aeropuerto, tienes mucha prisa por coger el avion");
		slowPrint("Delante tuyo ves minimo 300 personas más intentando entrar en el avión, pero necesitas actuar rápido");
		slowPrint("PREPARATE, ESCRIBE LO QUE SALGA EN PANTALLA");
		if (personaje.getAtletico()==true) {
			slowPrint("ZIG ZAG");
			String escrito = null;
			try {
				escrito=scannerTiempo();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (escrito.equalsIgnoreCase("ZIG ZAG")) {
				lograrAvion();
			}else {
				perderAvion(personaje);
			}
		}else {
			slowPrint("ZIG ZAG");
			String escrito = null;
			try {
				escrito=scannerTiempo();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (escrito.equalsIgnoreCase("ZIG ZAG")) {
				slowPrint("SALTAAAA");
				try {
					escrito=scannerTiempo();
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (escrito.equalsIgnoreCase("SALTAAAA")) {
					slowPrint("CORRE Y DESLIZATE");
					try {
						escrito=scannerTiempo();
					} catch (IOException e) {
						e.printStackTrace();
					}
					if (escrito.equalsIgnoreCase("CORRE Y DESLIZATE")) {
						lograrAvion();
					}else {
						perderAvion(personaje);
					}
					
				}else {
					perderAvion(personaje);
				}
			
			}else {
				perderAvion(personaje);
			}
		
		}
	}

	private static void perderAvion(Persona personaje) {
		slowPrint("¡Has fallado!");
		slowPrint("La gente te empuja por detrás y acabas cayendote al suelo.");
		slowPrint("Empiezas a ser pisado y aplastado sin ningún tipo de escrúpulos.");
		slowPrint("Te consigues levantar pero te has hecho un esguince en el tobillo.");
		slowPrint("Pierdes 5 puntos de vida y tu capacidad atlética.");
		personaje.setVidas(personaje.getVidas()-5);
		personaje.setAtletico(false);
	}

	private static void lograrAvion() {
		slowPrint("Muy bien, lograste entrar en el avión");
		slowPrint("Una vez dentro, ves como la gente se amontona y les da igual aplastarse con tal de entrar en el avión");
		slowPrint("Pillas un buen asiento, pides unos nachos, una pepsi y te echas una siesta");
		slowPrint("Recuperas 4 puntos de vida gracias al descanso");
		slowPrint("Después de 2 hora de viaje llegas a tu destino");
		personaje.setVidas(personaje.getVidas()+4);
	}

	private static void escuelaSituacion(Scanner scanner, Persona personaje) {
		slowPrint("Llegas al único instituto de la ciudad y dudas si buscar la enfermería.");
		slowPrint("Ves como todos los cristales están temblando y como escuchas ruidos fuertes en el interior");
		slowPrint("¿Quieres entrar en el insituto (W) o atravesarlo por el patio (S)?");
		boolean salir=false;
		while(!salir) {
			String teclado=scanner.nextLine();
			
			//ENTRA DENTRO
			if (teclado.equalsIgnoreCase("W")) {
				slowPrint("Entras dentro y encuentras el plano del instituto, descubres que la enfermería está en el 2do piso en el lado izquierdo");
				slowPrint("Hay unas escaleras en cada lado, pero en las de la izquierda hay una barricada");
				slowPrint("¿Intentas sobrepasar la barricada y subir las escaleras de la izquierda o tomar el camino largo de la derecha?");
				boolean salir2=false;
				while(!salir2) {
					teclado=scanner.nextLine();
					
					//AVANZA BARRICADA
					if (teclado.equalsIgnoreCase("A")) {
						if (personaje.getAtletico()==true) {
							slowPrint("Empiezas a avanzar entre los escombros y consigues llegar al 2do piso");
							slowPrint("Llegas a la enfermeria y coges el botiquin de primeros auxilios");
							slowPrint("Te curas 8 puntos de vida");
							slowPrint("Bajas las escaleras de nuevo a la máxima velocidad posible y huyes sin problemas");
							personaje.setVidas(personaje.getVidas()+8);
						}else {
							slowPrint("No consigues sobrepasar los escombros y cuando vas a tomar el camino largo ves que ya se ha bloqueado");
							slowPrint("Decides salir del instituto sin mayor complicación");
						}
						salir2=true;
					}
					
					//CAMINO LARGO
					else if (teclado.equalsIgnoreCase("D")) {
						slowPrint("Llegas al 2do piso y mientras avanzas por el pasillo se escuchan ruidos fuertes en todas las direcciones");
						slowPrint("¿Decides darte prisa (W) o dar la vuelta e irte? (S)");
						boolean salir3=false;
						while(!salir3) {
							teclado=scanner.nextLine();
							
							//DECIDE JUGAR
							if (teclado.equalsIgnoreCase("W")) {
								slowPrint("De repente, el techo detrás tuyo se rompe, ya no hay vuelta atrás");
								slowPrint("Desde una sala, un pupitre sale disparado hacia el pasillo y lo estás viendo llegar");
								slowPrint("¿Decides correr hacia el y saltarlo (W), meterte en una sala cercana (A) y esperar a que pase o avanzar pegado a las ventanas? (D)");
								boolean salir4=false;
								while(!salir4) {
									teclado=scanner.nextLine();
									
									//SALTAR PUPITRE
									if (teclado.equalsIgnoreCase("W")) {
										if (personaje.getAtletico()==true) {
											slowPrint("Consigues saltarlo sin problemas y entras en la enfermería");
											slowPrint("Dentro de la enfermeria coges el botiquin de primeros auxilios");
											slowPrint("Te curas 8 puntos de vida y huyes del instituto como puedes");
											personaje.setVidas(personaje.getVidas()+8);
											salir4=true;
										}else {
											slowPrint("Por tu inaptitud atletica el pupitre te arrolla y te das un golpe en la cabeza");
											if (personaje.getVidas()<=8) {
												slowPrint("Es lo suficientemente fuerte como para acabar con tu vida.");
												salir4=true;
											}else {
												slowPrint("Sobrevives al golpe y huyes viviendo para contarlo.");
												slowPrint("Pierdes 8 puntos de vida del incidente");
												salir4=true;
											}
											personaje.setVidas(personaje.getVidas()-8);
										}
									
									//MUERTE CAIDA
									}else if(teclado.equalsIgnoreCase("D")) {
										slowPrint("Aunque te peges a las ventanas, el pupitre te sigue golpeando");
										slowPrint("El golpe no era tan fuerte, pero pierdes suficiente el equilibrio y caes por la ventana");
										slowPrint("Caes y pereces en el acto.");
										personaje.setVidas(0);
										salir4=true;
									}
									
									//GANASTE EL JUEGO
									else if(teclado.equalsIgnoreCase("A")){
										slowPrint("Aseguras tu integridad, con el riesgo de no poder llegar a la enfermería");
										slowPrint("Una vez pasa el pupitre, corres a la enfermeria y llegas a ella");
										slowPrint("Dentro de la enfermeria coges el botiquin de primeros auxilios");
										slowPrint("Te curas 8 puntos de vida y huyes del instituto como puedes");
										personaje.setVidas(personaje.getVidas()+8);
										salir4=true;
									
									}else {
										slowPrint("¿Decides correr hacia el y saltarlo (W), meterte en una sala cercana (A) y esperar a que pase o avanzar pegado a las ventanas? (D)");
									}
								}
								salir3=true;
							
							//HUYE ANTES DEL DESASTRE	
							}else if(teclado.equalsIgnoreCase("S")) {
								slowPrint("Vuelves por donde has venido, mientras escuchas como el techo del 2do piso se cae");
								slowPrint("Tomas la ruta principal y sales del instituto sin mayor complicación");
								salir3=true;
							}
							else {
								slowPrint("¿Decides darte prisa (W) o dar la vuelta e irte? (S)");
							}
							
						}
						salir2=true;
					}
					else {
						slowPrint("¿Intentas sobrepasar la barricada y subir las escaleras de la izquierda o tomar el camino largo de la derecha?");
					}
					
				}
				salir2=true;
				
			//IGNORAS ENFERMERIA
			}else if(teclado.equalsIgnoreCase("S")){
				slowPrint("Avanzas por el patio pero el terremoto se intensifica.");
				slowPrint("¿Decides pegarte al edificio principal (A) o atravesar el campo de baloncesto? (D)");
				boolean salir2=false;
				while(!salir2) {
					teclado=scanner.nextLine();
					
					//CAEN CRISTALES
					if (teclado.equalsIgnoreCase("A")) {
						slowPrint("Las paredes del instituto son muy solidas y no tienes miedo de que te ocurra nada.");
						slowPrint("Lo que no tenias en cuenta, es que los cristales no son tan solidos.");
						slowPrint("Te caen unos cuantos cristales de las ventanas de los pisos superiores.");
						slowPrint("Pierdes 5 puntos de vida pero consigues atravesar el instituto.");
						personaje.setVidas(personaje.getVidas()-5);
						salir2=true;
					}
					
					//AVANZAS CANCHA
					else if (teclado.equalsIgnoreCase("D")) {
						slowPrint("Como no tienes nada donde agarrarte, comienzas a avanzar tirado en el suelo.");
						slowPrint("Una canasta cercana tuya empieza a temblar demasiado.");
						slowPrint("Es suficientemente pesada como para no ser tirada por el terremoto.");
						slowPrint("Llegas a la puerta trasera del instituto y huyes sin problemas.");
						salir2=true;
					}
					else {
						slowPrint("¿Decides pegarte al edificio principal (A) o atravesar el campo de baloncesto? (D)");
					}
				}
				salir=true;
			}else {
				slowPrint("¿Quieres entrar en el insituto (w) o atravesarlo por el patio (s)?");
			}
			salir=true;
		}
	}

	private static void puertoSituacion(Scanner scanner, Persona personaje, Terremoto terremotoActual) {
		slowPrint("Llegaste al puerto, el suelo está húmedo y hay charcos por donde pases.");
		slowPrint("Comienza el terremoto y temes de que ocurra un maremoto");
		slowPrint("A mano izquierda tienes un camino que se aleja del puerto pero completamente mojado");
		slowPrint("A mano derecha tienes un camino que se acerca al mar, aunque parece mucho más seco y seguro");
		slowPrint("¿Hacia que dirección avanzas?");
		boolean salir=false;
		while(!salir) {
			String teclado=scanner.nextLine();
			if (teclado.equalsIgnoreCase("A")) {
				slowPrint("Tomas la ruta de la izquierda y empiezas a agilizar el ritmo.");
				slowPrint("El terremoto se intensifica y empiezas a tener miedo, por lo que aceleras el paso");
				slowPrint("La magnitud del terremoto es "+terremotoActual.getMagnitud());
				if (personaje.getAtletico()==true) {
					slowPrint("Te caes por el suelo mojado y un poste eléctrico comienza a caer justo en el charco de detrás tuyo");
					slowPrint("Gracias a tus capacidades atléticas eres capaz de levantarte rápido y tienes que decidir hacia donde lanzarte.");
					slowPrint("¿Qué decisión tomas?");
					boolean salir2=false;
					while(!salir2) {
						teclado=scanner.nextLine();
						if (teclado.equalsIgnoreCase("S")) {
							slowPrint("Decides saltar al único lugar donde la electrificación del poste afecta");
							slowPrint("El shock eléctrico es tan fuerte que pereces en el acto");
							personaje.setVidas(0);
							salir2=true;
						}else if (teclado.equalsIgnoreCase("A")||teclado.equalsIgnoreCase("D")||teclado.equalsIgnoreCase("W")){
							slowPrint("Saltas a una zona sin demasiada humedad pero te haces daño al caer");
							slowPrint("Pierdes 2 puntos de vida");
							personaje.setVidas(personaje.getVidas()-2);
							salir2=true;
						}else {
							slowPrint("¿Qué decisión tomas?");
						}
					}
					salir=true;
				}else {
					slowPrint("Te caes por el suelo mojado y un poste eléctrico comienza a caer justo en el charco de detrás tuyo");
					slowPrint("No eres lo suficientemente atletico para levantarte rápido, por lo que pereces electrificado");
					personaje.setVidas(0);
					salir=true;
				}
			}else if(teclado.equalsIgnoreCase("D")){
				if (terremotoActual.getPeligroso()==true) {
					slowPrint("El terremoto es tan fuerte que no solo te tira, si no que crea un maremoto a pocos metros tuyos.");
					slowPrint("Caes al agua irremediablemente y por las corrientes fuertes no consigues salir del mar.");
					personaje.setVidas(0);
					salir=true;
				}else {
					slowPrint("Andas con cuidado y la suerte te sonrie, el terremoto no es tan fuerte para que caigas al mar.");
					slowPrint("Abandonas el puerto sin más complicaciones en tu camino");
					slowPrint("No recibes ningún daño");
					salir=true;
				}
			}else {
				slowPrint("¿Hacia que dirección avanzas?");
			}
		}
	}

	private static void playaSituacion(Scanner scanner, Persona personaje, Terremoto terremotoActual) {
		slowPrint("Llegaste a una hermosa playa, la arena a tus pies está temblando y necesitas agarrarte a algo.");
		slowPrint("Delante tuyo ves una sombrilla y tumbona en la arena y unas cuantas palmeras suficientemente solidas hacia las demás direcciones");
		slowPrint("¿Hacia que dirección avanzas?");
		boolean salir=false;
		while(!salir) {
			String teclado=scanner.nextLine();
			if (teclado.equalsIgnoreCase("A")||teclado.equalsIgnoreCase("D")||teclado.equalsIgnoreCase("S")) {
				slowPrint("Llegas a las palmeras y te aferras a una de ellas con todas tus fuerzas.");
				slowPrint("La magnitud del terremoto es "+terremotoActual.getMagnitud());
				if (terremotoActual.getPeligroso()==true) {
					slowPrint("Es suficientemente fuerte como para conseguir soltar un coco y que te caiga en la cabeza");
					slowPrint("Recibes 3 puntos de daño.");
					personaje.setVidas(personaje.getVidas()-3);
					salir=true;
				}else {
					slowPrint("La palmera resiste la fuerza débil del terremoto y sales airoso sin problemas");
					salir=true;
				}
				
			}else if(teclado.equalsIgnoreCase("W")){
				if (personaje.getAtletico()==true) {
					slowPrint("Gracias a tus capacidades fisicas llegas hasta la zona de descanso sin mayor complicación.");
					slowPrint("Encuentras un caipiriña y te lo bebes mientras todo tiembla, te sienta muy bien");
					slowPrint("No recibes ningún daño y recuperas 1 punto de vida.");
					personaje.setVidas(personaje.getVidas()+1);
					salir=true;
				}else {
					slowPrint("Te caes intentando llegar a la zona de descanso y te haces algo de daño, pero no hay ningún peligro mayor.");
					slowPrint("Eso si, toda la zona chill-out ha quedado completamente destruida, como tu rodilla");
					slowPrint("Recibes 2 punto de daño");
					personaje.setVidas(personaje.getVidas()-2);
					salir=true;
				}
			}else {
				slowPrint("¿Hacia que dirección avanzas?");
			}
		}
	}

	private static void montañaSituacion(Scanner scanner, Persona personaje, Terremoto terremotoActual) {
		slowPrint("Decidiste escalar la montaña, el terremoto no debería de afectar aquí y piensas que estás seguro.");
		slowPrint("A lo lejos ves un posible bunker donde resguardarte de los terremotos.");
		slowPrint("¿Avanzas (w) o decides ubicarte detrás de un montículo para después desviarte? (s)");
		boolean salir=false;
		while(!salir) {
			String teclado=scanner.nextLine();
			if (teclado.equalsIgnoreCase("W")){
				if (terremotoActual.getProfundidad()<5.0) {
					slowPrint("La profundidad del terremoto es "+terremotoActual.getProfundidad());
					slowPrint("Es suficientemente superficial para generar...");
					slowPrint("¡¡¡UNA AVALANCHA!!!");
					slowPrint("¿Sigues avanzando (W) o decides resguardarte detrás de una roca cercana? (S)");
					boolean salir2=false;
					while(!salir2) {
						teclado=scanner.nextLine();
						if (teclado.equalsIgnoreCase("W")){
							if (personaje.getAtletico()==true) {
								slowPrint("Debido a tu velocidad consigues sobrepasar la avalancha y llegar al bunker.");
								slowPrint("Dentro encuentras un botiquin que te curará 3 puntos de vida.");
								personaje.setVidas(personaje.getVidas()+3);
								salir2=true;
							}
							else if (personaje.getAtletico()==false) {
								slowPrint("Debido a tu inaptitud atletica la avalancha te pilla de lleno.");
								slowPrint("Pierdes 9 puntos de daño debido a multiples contusiones a lo largo del cuerpo.");
								personaje.setVidas(personaje.getVidas()-9);
								salir2=true;
							}
						}else if (teclado.equalsIgnoreCase("S")){
							slowPrint("Te agazapas detrás de la roca tan rápido como puedes");
							slowPrint("Acabas con heridas en las extremidades debido a las piedras que traía consigo la avalancha");
							slowPrint("Pierdes 5 puntos de vida");
							personaje.setVidas(personaje.getVidas()-5);
							salir2=true;
						}else {
							System.out.println("¿Sigues avanzando (W) o decides resguardarte detrás de una roca cercana? (S)");
						}
					}
					salir=true;
				}else{
					slowPrint("La profundidad del terremoto es "+terremotoActual.getProfundidad());
					slowPrint("Es suficientemente profundo como para notar un ligero temblor sin consecuencias.");
					slowPrint("Llegas sin ningún problema al bunker y encuentras unas vendas que te curarán 3 puntos de vida.");
					personaje.setVidas(personaje.getVidas()+3);
					salir=true;
				}
			}
			else if (teclado.equalsIgnoreCase("S")) {
				slowPrint("Detrás del monticulo hay un oso aterrorizado por los terremotos y tu presencia le asusta aun más");
				slowPrint("El oso lanza un zarpazo hacia tu brazo y comienza a huir despavorido");
				slowPrint("El terremoto acaba y decides dejar que todo pase y tomar una ruta distinta");
				slowPrint("Pierdes 3 puntos de vida");
				personaje.setVidas(personaje.getVidas()-3);
				salir=true;
			}
			else {
				slowPrint("¿Avanzas (w) o decides ubicarte detrás de un montículo para después desviarte? (s)");
			}		
		}
	}

	private static void bosqueSituacion(Scanner scanner, Persona personaje) {
		slowPrint("Te has adentrado en un frondoso bosque, los árboles están temblando y uno situado a la derecha empieza a caer después partirse el tronco.");
		slowPrint("¿Hacia que dirección avanzas?");
		boolean salir=false;
		while(!salir) {
			String teclado=scanner.nextLine();
			if (teclado.equalsIgnoreCase("A")||teclado.equalsIgnoreCase("D")) {
				slowPrint("Intentas esquivarlo moviendote en la misma dirección en la que caía y te cae encima de la pierna.");
				slowPrint("Recibes 6 puntos de daño y pierdes tu capacidad atlética.");
				personaje.setAtletico(false);
				personaje.setVidas(personaje.getVidas()-6);
				salir=true;
				
			}else if(teclado.equalsIgnoreCase("W")){
				if (personaje.getAtletico()==true) {
					slowPrint("Gracias a tus capacidades fisicas consigues pasar por debajo del árbol una vez esta cayendo.");
					slowPrint("No recibes ningún daño.");
					salir=true;
				}else {
					slowPrint("No eres lo suficientemente veloz y mientras pasas por debajo del árbol te rasguñas el brazo.");
					slowPrint("Recibes 1 punto de daño");
					personaje.setVidas(personaje.getVidas()-1);
					salir=true;
				}
				
			}else if(teclado.equalsIgnoreCase("S")) {
				slowPrint("Retrocedes y esquivas el árbol perfectamente.");
				slowPrint("No recibes ningún daño.");
				salir=true;
			}else {
				slowPrint("¿Hacia que dirección avanzas?");
			}
		}
	}
	
	private static void eliminarTerremotos(ArrayList<Terremoto> lista, Scanner scanner) {
		System.out.println("Introduce el número del terremoto a eliminar\n");
		int indice = Integer.parseInt(scanner.nextLine());
		String lecturaScanner;
		if (indice<=lista.size()){
			System.out.println(lista.get(indice-1).toString()+"\n¿Quieres eliminar el terremoto? (Y/N)\n");
			lecturaScanner=scanner.nextLine();
			if (lecturaScanner.equalsIgnoreCase("y")) {
				lista.remove(indice-1);
				System.out.println("Se ha eliminado correctamente.");
			}
		}
		else {
			System.out.println("Ese indice es incorrecto, pulse otro por favor");
		}
		
		System.out.println("Quieres guardar los datos? (S/N)");
		lecturaScanner=scanner.nextLine();
		if (lecturaScanner.equalsIgnoreCase("s")) {
			guardarDatos(listaDeTerremotos);
		}
		else {
			System.out.println("No se ha guardado ningún dato");
		}
	}
	
	private static void editarTerremotos(ArrayList<Terremoto> lista, Scanner scanner) {
		Boolean salir=false;
		System.out.println("Introduce el número del terremoto a editar\n");
		int indice = Integer.parseInt(scanner.nextLine());
		if (indice<=lista.size()){
			System.out.println(lista.get(indice-1).toString()+"\n¿Quieres modificar los valores? (S/N)\n");
			String lecturaScanner=scanner.nextLine();
			if (lecturaScanner.equalsIgnoreCase("s")) {
				while (!salir) {
					System.out.println("¿Qué valor quieres editar?\n"
							+ "0.Salir\n"
							+ "1.Fecha\n"
							+ "2.Hora\n"
							+ "3.Latitud\n"
							+ "4.Longitud\n"
							+ "5.Profundidad\n"
							+ "6.Magnitud\n"
							+ "7.Tipo de Magnitud\n"
							+ "8.Localización\n");
					int opcion = Integer.parseInt(scanner.nextLine());
					switch (opcion) {
						case SALIR:
							salir=true;
							break;
						case FECHA: 
							System.out.println("Has elegido modificar la fecha, tiene este valor: "+lista.get(indice).getFecha()+", dame un valor para modificarla");
							try {
								lecturaScanner=scanner.nextLine();
								lista.get(indice).setFecha(lecturaScanner);
								System.out.println("Se ha modificado correctamente\n");
							} catch (Exception e) {
								System.out.println("No has dado un dato correcto\n");
							}
							break;
								
						case HORA: 
							System.out.println("Has elegido modificar la hora, tiene este valor: "+lista.get(indice).getHora()+", dame un valor para modificarla");
							try {
								lecturaScanner=scanner.nextLine();
								lista.get(indice).setHora(lecturaScanner);
								System.out.println("Se ha modificado correctamente\n");
							} catch (Exception e) {
									System.out.println("No has dado un dato correcto\n");
							}
							break;
							
						case LATITUD: 
							System.out.println("Has elegido modificar la latitud, tiene este valor: "+lista.get(indice).getLatitude()+", dame un valor para modificarla");
							try {
								lecturaScanner=scanner.nextLine();
								lista.get(indice).setLatitude(Double.parseDouble(lecturaScanner));
								System.out.println("Se ha modificado correctamente\n");
							} catch (Exception e) {
								System.out.println("No has dado un dato correcto\n");
							}
							break;
							
						case LONGITUD: 
							System.out.println("Has elegido modificar la longitud, tiene este valor: "+lista.get(indice).getLongitud()+", dame un valor para modificarla");
							try {
								lecturaScanner=scanner.nextLine();
								lista.get(indice).setLongitud(Double.parseDouble(lecturaScanner));
								System.out.println("Se ha modificado correctamente\n");
							} catch (Exception e) {
								System.out.println("No has dado un dato correcto\n");
							}
							break;
						case PROFUNDIDAD: 
							System.out.println("Has elegido modificar la profundidad, tiene este valor: "+lista.get(indice).getProfundidad()+", dame un valor para modificarla");
							try {
								lecturaScanner=scanner.nextLine();
								lista.get(indice).setProfundidad(Double.parseDouble(lecturaScanner));
								System.out.println("Se ha modificado correctamente\n");
							} catch (Exception e) {
								System.out.println("No has dado un dato correcto\n");
							}
							break;
						
						case MAGNITUD: 
							System.out.println("Has elegido modificar la magnitud, tiene este valor: "+lista.get(indice).getMagnitud()+", dame un valor para modificarla");
							try {
								lecturaScanner=scanner.nextLine();
								lista.get(indice).setMagnitud(Double.parseDouble(lecturaScanner));
								System.out.println("Se ha modificado correctamente\n");
							} catch (Exception e) {
								System.out.println("No has dado un dato correcto\n");
							}
							break;
							
						case TIPOMAG: 
							System.out.println("Has elegido modificar el tipo de magnitud, tiene este valor: "+lista.get(indice).getTipoMag()+", dame un valor entre (mbLg, mb, Mw) para modificarlo");
							try {
								lecturaScanner=scanner.nextLine();
								if (lecturaScanner.equals("mbLg") || lecturaScanner.equals("mb") || lecturaScanner.equals("Mw")) {
									lista.get(indice).setTipoMag(lecturaScanner);
								}else {
									throw new Exception();
								}
								System.out.println("Se ha modificado correctamente\n");
							} catch (Exception e) {
									System.out.println("No has dado un dato correcto\n");
							}
							break;
							
						case LOCALIZACION: 
							System.out.println("Has elegido modificar la localización, tiene este valor: "+lista.get(indice).getLocalización()+", dame un valor para modificarla");
							try {
								lecturaScanner=scanner.nextLine();
								lista.get(indice).setLocalización(lecturaScanner);
								System.out.println("Se ha modificado correctamente\n");
							} catch (Exception e) {
								System.out.println("No has dado un dato correcto\n");
							}
							break;
						
						default:
							System.out.println("Número no disponible\n");
					}
				}		
			}
		}
		else {
			System.out.println("Ese indice es incorrecto, pulse otro por favor");
		}
		System.out.println("Quieres guardar los datos? (S/N)");
		String s=scanner.nextLine();
		if (s.equalsIgnoreCase("s")) {
			guardarDatos(listaDeTerremotos);
		}
		else {
			System.out.println("No se ha guardado ningún dato");
		}
	}

	private static String scannerTiempo() throws IOException {
        int x = 5; // wait 2 seconds at most

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        long startTime = System.currentTimeMillis();
        while ((System.currentTimeMillis() - startTime) < x * 1000
                && !in.ready()) {
        }

        if (in.ready()) {
            
            return in.readLine();
        } else {
            System.out.println("No has introducido a tiempo");
            return "DEATH";
        }
    }
	
	private static Persona creadorPersonaje(Scanner scanner, Persona pAnt) {
		Boolean salir=false;
		while (!salir) {
			System.out.println("1. Crea tu personaje\r\n"
					+ "2. Utiliza el predeterminado (Fácil) \r\n"
					+ "3. Utiliza el predeterminado (Difícil) \r\n"
					+ "4. Utiliza el último personaje usado \r\n");
			int indice = Integer.parseInt(scanner.nextLine());
			switch (indice) {

			case 1:
				try {
					System.out.println("Escribe el nombre de tu personaje");
					String n = scanner.nextLine();
					System.out.println("¿Cúal es su altura?");
					String s1=scanner.nextLine();
					s1=s1.replace(',','.');
					double a = Double.parseDouble(s1);
					System.out.println("¿Y su peso?");
					String s2=scanner.nextLine();
					s2=s2.replace(',','.');
					double p = Double.parseDouble(s2);
					System.out.println("¿Consideras atlético a tu personaje? (S/n)\n"
							+ "Esta decisión repercute en la dificultad de la partida.");
					Boolean atl;
					String s = scanner.nextLine();
					if (s.equalsIgnoreCase("S")||s.equals("")) {
						atl=true;
						if (p>120) {
							System.out.println("Eres demasiado pesado como para ser atlético");
							atl=false;
						}
					}else if (s.equalsIgnoreCase("N")) {
						atl=false;
					}
					else {
						throw new Exception();
					}
					return new Persona(n, a, p, atl);
				} catch (Exception e) {
					System.out.println("No me has dado un valor correcto\n");
				}
				break;

			case 2:
				return new Persona("Jagoba", 1.70, 80.0, true);
				
			case 3:
				return new Persona("Byron", 1.81, 73.2, false);
			
			case 4:
				if (pAnt==null) {
					System.out.println("No hay personaje utilizado anteriormente\n");
				}else {
					System.out.println("El personaje que quieres usar es: "+pAnt);
					System.out.println("¿Quieres utilizarlo? (S/n)");
					String s = scanner.nextLine();
					if(s.equalsIgnoreCase("S")||s.equals("")) {
						return pAnt;
					}
				}
			default:
				System.out.println("Elige de nuevo un número\n");	
			}
			
		}
		return null;
	}
	
	private static void visualizarTerremotos() {
		int contador=0;
		for (Terremoto t : listaDeTerremotos) {
			System.out.println(contador+1+". "+t.toString()+"\n");
			contador++;
		}
	}

	
	private static int menuPrincipal(Scanner sn) {
		int i;
		System.out.println("--Terremotos en España--\n"
				+ SALIR + ". Salir\r\n"
				+ VISUALIZAR_TERREMOTOS + ". Visualizar los terremotos.\r\n"
				+ EDITAR_TERREMOTOS + ". Editar los valores de un terremoto.\r\n"
				+ ELIMINAR_TERREMOTOS + ". Eliminar el terremoto seleccionado.\r\n"
				+ JUEGO_INTERACTIVO + ". Juego de superviviencia.\r\n");
		i=Integer.parseInt(sn.nextLine());
		return i;
	}
	
	private static Boolean salirMenu(Scanner scanner) {
		String lecturaScanner;
		Boolean salir=true;
		System.out.println("¿Estás seguro de que quieres salir? (S/N)");
		lecturaScanner=scanner.nextLine();
		if (lecturaScanner.equalsIgnoreCase("s")) {
			System.out.println("Quieres guardar los datos? (S/N)");
			String s=scanner.nextLine();
			if (s.equalsIgnoreCase("s")) {
				guardarDatos(listaDeTerremotos);
			}
			else {
				System.out.println("No se ha guardado ningún dato\n");
			}
			System.out.println("Adios, vuelve pronto");
			return salir;
		}else {
			return !salir;
		}
	}

	private static void guardarDatos(ArrayList<Terremoto> listaTerremotos) {
		try {
			FileWriter lista = new FileWriter("datos/Terremotos.txt");
			for (int i=0;i<listaTerremotos.size();i++) {
				Terremoto t = listaTerremotos.get(i);
				lista.write(t.getFecha()+";"+t.getHora()+";"+t.getLatitude()+";"+t.getLocalización()+";"+t.getLongitud()+";"+t.getMagnitud()+";"+t.getProfundidad()+t.getTipoMag()+"\n");
			}
			lista.close();
			System.out.println("Se han guardado los cambios correctamente\n");
		}
		catch (IOException e) {
			System.out.println("Ha ocurrido un error.");
		    e.printStackTrace();
		}
	}
	
	private static void cargarFichero() {
		try {

			File lista = new File("datos/Terremotos.txt");
			Scanner reader= new Scanner(lista);
			while (reader.hasNextLine()) {
				Terremoto t = new Terremoto();
				String data = reader.nextLine();
				String[] parts = data.split(";");
				t.setFecha(parts[0]);
				t.setHora(parts[1]);
				t.setLatitude(Double.parseDouble(parts[2]));
				t.setLongitud(Double.parseDouble(parts[3]));
				t.setProfundidad(Double.parseDouble(parts[4]));
				t.setMagnitud(Double.parseDouble(parts[5]));
				t.setTipoMag(parts[6]);
				t.setLocalización(parts[7]);
				listaDeTerremotos.add(t);
			}
			reader.close();
		} 
		catch (FileNotFoundException e) {
			System.out.println("Ha ocurrido un error.");
		    e.printStackTrace();
		}
	}
	private static void slowPrint(String s) {
		try {
			  Thread.sleep(3500);
			} catch (InterruptedException e) {
			  Thread.currentThread().interrupt();
			}
		System.out.println(s);
	}

}
