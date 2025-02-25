#include "grupowanie.h"

int main(int argc, char** argv){
	Pliki pliki;
	Param param;
	std::string nazwa_programu = "\"Sortowanie_metoda_k-srednich\"";
	if (!odczyt(param, argc, argv, pliki)) {
		help(nazwa_programu);
		return 1;
	};

	Wektor wektor;
	std::vector <Wektor>Dane;
	if (!odczyt_z_pliku(param, pliki, Dane)) {
		help(nazwa_programu);
		return 1;
	}

	std::vector <Wektor>centroidy;
	std::vector<std::vector<double>>odleglosci;
	grupowanie_wstepne(param, Dane, centroidy, odleglosci);

	int kontrolny = 0;
	do {
	grupowanie(param, Dane, centroidy, kontrolny, odleglosci);
	} while (kontrolny); //kiedy kontrolny jest 1 robi petle

	zapis_do_pliku(param, Dane, centroidy, pliki);

	return 0;
}
