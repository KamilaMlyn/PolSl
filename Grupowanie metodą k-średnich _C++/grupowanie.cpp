#include "grupowanie.h"
#include <random> //do losowania centroidow
#include <math.h> //do pierwiastka przy liczeniu odleglosci
#include <fstream>
#include <iostream>
#include <sstream> //do sprawdzania ilosci wymiarow punktow z pliku we

void help(std::string nazwa_prog) {
	std::cerr << "Parametry programu "<< nazwa_prog << ":\n";
	std::cerr<<"-i <Plik_wejsciowy> -o <Plik_wyjsciowy> -k <liczka_klastrow> -d <liczba_wymiarow>\n";
}

bool odczyt(Param& param, int argc, char** argv, Pliki& pliki) {
	std::string napis;
	for (int i = 0; i < argc - 1; ++i)
	{
		std::string c = argv[i];
		if (c == "-i") {
			napis = argv[++i];
			if (napis == "-o" || napis == "-k" || napis == "-d") {
				std::cerr << "Nazwa pliku wejsciowego nie moze byc pusta.\n\n";
				return false;
			}
			pliki.Plik_we = argv[i];
		}
		if (c == "-o") {
			napis = argv[++i];
			if (napis == "-i" || napis == "-k" || napis == "-d") {
				std::cerr << "Nazwa pliku wyjsciowego nie moze byc pusta.\n\n";
				return false;
			}
			pliki.Plik_wy = argv[i];
		}
		if (c == "-k") {
			if (*argv[++i] >= 58 || *argv[i] <= 48) { //bo ascii
				std::cerr << "Liczba klastrow k musi byc liczba z przedzialu <1;9>\n\n";
				return false;
			}
			param.k = *argv[i] -48;
		}
		if (c == "-d") { 
			
			if (*argv[++i] >= 58 || *argv[i] <= 48) { //bo ascii
				std::cerr << "Liczba wymiarow d musi byc liczba z przedzialu <1;9>\n\n";
				return false;
			}
			param.d = *argv[i] - 48;
		}
	}
	return pliki.Plik_we != "";
}

bool odczyt_z_pliku(Param param, Pliki pliki, std::vector <Wektor>&Dane) {
	std::ifstream plik(pliki.Plik_we);											
	if (!plik) {
		std::cerr << "Nie mozna otworzyc pliku wejsciowego: " << pliki.Plik_we << "\n\n";
		return false;
	}
	//sprawdza poprawnosc pliku we
	std::string napis;
	int liczba_we;
	int licznik = 0;
	while (getline(plik, napis)) {
		std::istringstream linia;
		linia.str(napis);
		while (linia >> liczba_we) {
			licznik++;
		}
		if (licznik == param.d)
			licznik = 0;
		else {
			std::cerr << "Blad zwiazany z plikiem wejsciowym. Mozliwe przyczyny:\n";
			std::cerr << "- Liczba wymiarow wprowadzonych w konsoli rozni sie od liczby wymiarow punktow z pliku wejsciowego,\n";
			std::cerr << "- liczba wymiarow jest wieksza niz 9,\n";
			std::cerr << "- plik zawiera pusta linijke,\n";
			std::cerr << "- plik zawiera inne znaki niz liczby.\n\n";
			return false;
		}
	}
	
	plik.close();
	//wrzucanie danych z pliku do wektora Dane
	plik.open(pliki.Plik_we);
	std::string liczba;
	int licznik_d = 0;
	Wektor wektor_tymcz;
	while (plik >> liczba) {
		if (licznik_d < param.d){
			double wspolrzedna = std::stod(liczba); //zamiana string na double
			wektor_tymcz.wspolrzedne.push_back(wspolrzedna); 
			licznik_d++;	
		}
		else {
			Dane.push_back(wektor_tymcz);
			wektor_tymcz.wspolrzedne.erase(wektor_tymcz.wspolrzedne.begin() , wektor_tymcz.wspolrzedne.end());
			double wspolrzedna = std::stod(liczba); //zamiana string na double
			wektor_tymcz.wspolrzedne.push_back(wspolrzedna); 
			licznik_d = 1;
		}
	}
	Dane.push_back(wektor_tymcz);
	
	return true;
} 

int los(int low, int high) {
	static std::default_random_engine re{ std::random_device{}() };
	using Dist = std::uniform_int_distribution<int>;
	static Dist uid{};
	return uid(re, Dist::param_type{ low, high });
}

bool grupowanie_wstepne(Param param, std::vector <Wektor>& Dane, std::vector<Wektor>& centroidy, std::vector<std::vector<double>>& odleglosci) {
	//losuje centroidy poczatkowe
	//1. losuje numer adresu centroidy sposrod wektora Dane, 
	//2. wrzuca wektor punktu na poczatek w Dane, 
	//3. losuje nastepny numer bez wczesniej wylosowanych centroidow
	int numer; 
	int a = 0;
	while (centroidy.size() < param.k) {
		numer = los(a, Dane.size() - 1);
		centroidy.push_back(Dane[numer]);
		if (numer != a) {
			Dane.push_back(Dane[a]);
			Dane[a] = centroidy[a];
			Dane.erase(Dane.begin() + numer);
		}
		++a;
	}
			
	//liczy odleg³oœci punktow od centroidow
	double suma = 0;
	std::vector<double>wektor_tymcz;
	for (int i = 0; i < Dane.size(); ++i) {
		for (int j = 0; j < centroidy.size(); ++j) {
			for (int k = 0; k < param.d; ++k) {
				suma += (centroidy[j].wspolrzedne[k] - Dane[i].wspolrzedne[k]) * (centroidy[j].wspolrzedne[k] - Dane[i].wspolrzedne[k]);
			}
			wektor_tymcz.push_back(sqrt(suma));//pkt 0 centr 0
			suma = 0;
		}
		odleglosci.push_back(wektor_tymcz);
		wektor_tymcz.erase(wektor_tymcz.begin(), wektor_tymcz.end());
	}
		
	//przydzielanie klastra:
	double odl_tymcz;
	for (int i = 0; i < Dane.size(); ++i) {
		odl_tymcz = odleglosci[i][0];
		for (int j = 0; j < odleglosci[0].size(); ++j) {
			if (odleglosci[i][j] <= odl_tymcz) {
				odl_tymcz = odleglosci[i][j];
				Dane[i].nr_klastra = j;
			}
		}
	} 
	return true;
}

bool grupowanie(Param param, std::vector <Wektor>& Dane, std::vector<Wektor>& centroidy, int& kontrolny, std::vector<std::vector<double>>odleglosci) {
	kontrolny = 0;

	//zmienia wspolrzedne centroidow
	int licznik = 0;
	double suma = 0;
	for (int k = 0; k < param.k; ++k) {  //nr klastra/nr centroidu/miejsce centroidu w wektorze centroidy
		for (int l = 0; l < param.d; ++l) {
			for (int i = 0; i < Dane.size(); ++i) {
				if (Dane[i].nr_klastra == k) {
					suma = suma + Dane[i].wspolrzedne[l];
					licznik++;
				}
			}
			suma = suma / licznik;
			centroidy[k].wspolrzedne[l]=suma;
			licznik = 0;
			suma = 0;
		}
	}

	//liczy odleglosci punktow od centroidow
	std::vector<double>wektor_tymcz;
	for (int i = 0; i < Dane.size(); ++i) {
		for (int j = 0; j < centroidy.size(); ++j) {
			for (int k = 0; k < param.d; ++k) {
				suma += (centroidy[j].wspolrzedne[k] - Dane[i].wspolrzedne[k]) * (centroidy[j].wspolrzedne[k] - Dane[i].wspolrzedne[k]);
			}
			wektor_tymcz.push_back(sqrt(suma));//pkt 0 centr 0
			suma = 0;
		}
		odleglosci[i]=wektor_tymcz;
		wektor_tymcz.erase(wektor_tymcz.begin(), wektor_tymcz.end());
	}

	//przydziela klastry:
	double odl_tymcz;
	for (int i = 0; i < Dane.size(); ++i) {
		odl_tymcz = odleglosci[i][Dane[i].nr_klastra];
		for (int j = 0; j < odleglosci[0].size(); ++j) {
			if (odleglosci[i][j] < odl_tymcz) {
				odl_tymcz = odleglosci[i][j];
				Dane[i].nr_klastra = j;
				kontrolny = 1;
			}
		}
	}

	return kontrolny;
}

bool zapis_do_pliku(Param param, std::vector <Wektor> Dane, std::vector<Wektor> centroidy, Pliki pliki) {
	std::ofstream plik(pliki.Plik_wy);
	int P_F = 1;
	for (int k = 0; k < param.k; ++k) {
		plik << "Klaster " << k << "\n";
		for (int i = 0; i < param.d; ++i)
			plik << centroidy[k].wspolrzedne[i] << " ";		
		plik << "\n";
		for (int i = 0; i < Dane.size(); ++i) {
			for (int j = 0; j < param.d; ++j) {
				if (Dane[i].nr_klastra == k) {
					plik << Dane[i].wspolrzedne[j] << " ";
					P_F = 0;
				}
			}
			if (P_F == 0) {
				plik << "\n";
				P_F = 1;
			}
		}plik << "\n";
	}
	std::cout << "Program zakonczony. Wynik zapisany do pliku.\n";
	return true;
}