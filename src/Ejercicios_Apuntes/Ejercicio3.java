package Ejercicios_Apuntes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

import models.Show;

public class Ejercicio3 {

	static List<Show> shows;

	static Map<String, Show> favs = new HashMap<>();

	public static void main(String[] args) throws FileNotFoundException {

		int opcionMenu = -1;

		Scanner scanner = new Scanner(System.in);

		final String ruta = "my_netflix_titles.csv";

		shows = leerFichero(ruta);
		//
		// shows.stream().filter(s ->
		// s.getCountry().equalsIgnoreCase("spain")).forEach(System.out::println);

		do {
			opcionMenu = mostrarMenu(opcionMenu, scanner);

		} while (opcionMenu != 0);

//		addFav("s75");
//
//		addFav("s4392");
//
//		System.out.println();
//
//		favs.values().forEach(System.out::println);
//
//		// volcarFav("volc.txt");
//
//		addFav("s4292");
//
//		volcarFav("volc.txt");

//		List<Show> leerFichero = leerFichero("volc.txt");
//
//		leerFichero.forEach(elemento -> favs.put(elemento.getShow_id(), elemento));
//
//		System.out.println();
//		
//		favs.values().forEach(System.out::println);

	}

	private static int mostrarMenu(int opcionMenu, Scanner scanner) {
		try {
			System.out.println("Elige una opcion");
			System.out.println("----------------");
			System.out.println("0. Salir");
			System.out.println("1. Buscar");
			System.out.println("2. Añadir favorito");
			System.out.println("3. Volcar favoritos");
			System.out.println("4. Cargar favoritos");

			opcionMenu = Integer.parseInt(scanner.nextLine());

			switch (opcionMenu) {
			case 0:

				System.out.println("Adios...");

				break;

			case 1:

				System.out.println("Introduce titulo ");
				String titulo = scanner.nextLine();
				System.out.println("Introduce pais ");
				String pais = scanner.nextLine();
				System.out.println("Introduce director ");
				String director = scanner.nextLine();
				System.out.println("Introduce añor ");
				String year = scanner.nextLine();

				buscar(titulo, pais, director, year);

				break;
			case 2:

				System.out.println("Indica el ID del show a añadir a favoritos");
				addFav(scanner.nextLine());

				break;
			case 3:

				System.out.println("Indica el fichero donde volcar los favoritos");

				volcarFav(scanner.nextLine());

				break;
			case 4:

				System.out.println("Indica el fichero desde donde cargar los favoritos");
				cargarFavs(scanner.nextLine());

				break;

			default:

				System.out.println("Opcion invalida");
				break;
			}
		} catch (Exception e) {
			System.out.println("Opcion invalida");
		}
		return opcionMenu;
	}

	/**
	 * @param fichero
	 */
	private static void cargarFavs(String fichero) {

		List<Show> leerFichero = leerFichero(fichero);
		leerFichero.forEach(elemento -> favs.put(elemento.getShow_id(), elemento));

	}

	private static void volcarFav(String string) throws FileNotFoundException {

		File csvOutputFile = new File(string);

		try (PrintWriter pw = new PrintWriter(csvOutputFile)) {

			favs.values().stream().map(Show::toCsvFormat).forEach(pw::println);

		}

	}

	private static void addFav(String id) {
		shows.stream().filter(s -> s.getShow_id().equalsIgnoreCase(id))
				.forEach(elemento -> favs.put(elemento.getShow_id(), elemento)

				);
	}

	// Permitirá buscar shows por nombre, país, director, año y
	// el usuario elegirá sus favoritos añadiéndolos a una lista del usuario.
	private static void buscar(String title, String country, String director, String year) {

		Stream<Show> filter = shows.stream();

		if (title != null && !title.isEmpty()) {
			filter = filter.filter(s -> s.getTitle().equalsIgnoreCase(title));

		}

		if (country != null && !country.isEmpty()) {
			filter = filter.filter(s -> s.getCountry().equalsIgnoreCase(country));
		}

		if (director != null && !director.isEmpty()) {
			filter = filter.filter(s -> s.getDirector().equalsIgnoreCase(director));
		}

		if (year != null && !year.isEmpty()) {
			filter = filter.filter(s -> s.getRelease_year().equalsIgnoreCase(year));
		}

		filter.forEach(System.out::println);

	}

	public static List<Show> leerFichero(String ruta) {

		List<Show> misShows = new ArrayList<Show>();
		File f = new File(ruta);
		boolean isString = false;
		Scanner sc = null;
		String[] cadenitas = null;
		try {
			sc = new Scanner(f);

			// Para quitar la cabecera (primera línea)
			if (sc.hasNextLine())
				sc.nextLine();

			while (sc.hasNextLine()) {
				var cadena = sc.nextLine();

				cadenitas = cadena.split(";");

				String stringConComillas = "";
				for (String s : cadenitas) {
					// Para escapar cadenas completas de caracteres
					if (s.startsWith("\"")) {
						isString = true;
					}
					if (isString) {
						stringConComillas += s + ",";
					}
					if (s.endsWith("\"")) {
						isString = false;
						s = stringConComillas;
						stringConComillas = "";
					}

					s = s.replaceAll("\"", "");
					while (s.endsWith(",")) {
						s = s.substring(0, s.length() - 1);
					}
					s = s.trim();

					// Imprimir el término entre las comas.
					if (!isString) {
						// System.out.println(s);
					}
				}
				// if (cadenitas.length == 12) {
				misShows.add(
						new Show(cadenitas[0], cadenitas[1], cadenitas[2], cadenitas[3], cadenitas[4], cadenitas[5],
								cadenitas[6], cadenitas[7], cadenitas[8], cadenitas[9], cadenitas[10], cadenitas[11]));

				// }
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ArrayIndexOutOfBoundsException e) {
			System.err.println("fila erronea " + cadenitas);
			e.printStackTrace();
		} finally {
			sc.close();
		}

		return misShows;
	}
}