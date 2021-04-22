package Ejercicios_Apuntes;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import models.Show;

public class Ejercicio3 {

	public static void main(String[] args) {

		final String ruta = "netflix_titles.csv";
		var shows = leerFichero(ruta);
		shows.stream().filter(s -> s.getCountry().equalsIgnoreCase("spain")).forEach(System.out::println);

	}

	public static List<Show> leerFichero(String ruta) {

		List<Show> misShows = new ArrayList<Show>();
		File f = new File(ruta);
		boolean isString = false;
		Scanner sc = null;
		try {
			sc = new Scanner(f);

			// Para quitar la cabecera (primera línea)
			if (sc.hasNextLine())
				sc.nextLine();

			// Loh datoh
			while (sc.hasNextLine()) {
				var cadena = sc.nextLine();
				var cadenitas = cadena.split(",");

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
		} finally {
			sc.close();
		}

		return misShows;
	}
}