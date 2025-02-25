#include "Tryb.h"
#include "Dodaj.h"

int main() {
	Przepisy przepisy;
	char nr;
	while (true) {
		std::cout << "1. Dodaj przepis\n2. Wyswietl przepis\n3. Wyjscie\n";
		nr = _getch();
		nr -= '0';
		system("cls");
		if (nr == 1) {
			std::string nazwa;
			std::cout << "Podaj nazwe. Zatwierdz klikajac Enter\n";
			std::getline(std::cin, nazwa);
			system("cls");
			std::vector<std::string> skladniki_gl;
			std::vector<std::string> dodatki;
			std::vector<std::string> przyprawy;
			std::vector<std::string> przygotowanie;
			std::cout << "Podaj skladniki glowne oddzielajac je Enterem.\nKoniec dodawania skladnikow glownych -> napisz: koniec\ni zatwierdz Enterem.\n";
			dodaj(skladniki_gl);
			std::cout << "Podaj dodatki oddzielajac je Enterem.\nKoniec dodatkow -> napisz: koniec\ni zatwierdz Enterem.\n";
			dodaj(dodatki);
			std::cout << "Podaj przyprawy oddzielajac je Enterem.\nKoniec przypraw -> napisz: koniec\ni zatwierdz Enterem.\n";
			dodaj(przyprawy);
			std::cout << "Podaj krok przygotowania oddzielajac je Enterem.\nKoniec kroku -> napisz: koniec\ni zatwierdz Enterem.\n";
			dodaj(przygotowanie);
			Przepis a(nazwa, skladniki_gl, dodatki, przyprawy, przygotowanie);
			przepisy.dodajPrzepis(a);
		}
		else if (nr == 2) {
			int g = przepisy.getsize();
			if (g == 0)
				std::cout << "Jeszcze nie ma zadnego przepisu. Dodaj aby moc wyswietlic\n";
			else {
				std::cout << "Wybierz pozycje z menu:\n";
				for (int i = 0; i < g;) {
					Przepis a = przepisy[i];
					std::cout << ++i << "." << a.getNazwa() << "\n";
				}
				nr = _getch();
				nr -= '0'; //-48 bo asci, -1 bo numeracja w wektorze od 0
				nr -= 1;

				WyborTrybu wybor_trybu;
				int petla = 0;
				do {
					std::cout << "W jaki sposob wyswietlic?\n1. Wszystko na raz\n2. Krok po kroku\n";
					int nr_trybu = _getch();
					switch (nr_trybu - '0') {

					case 1:
						wybor_trybu.wybor(std::make_shared<Tryb1>(), przepisy[nr]);
						wybor_trybu.wyswietlanie_przepisu();
						petla = 0;
						break;
					case 2:
						wybor_trybu.wybor(std::make_shared<Tryb2>(), przepisy[nr]);
						wybor_trybu.wyswietlanie_przepisu();
						petla = 0;
						break;
					default:
						std::cout << "Cos nie tak. Jeszcze raz wybor trybu:\n";
						petla = 1;
						break;
					}
				} while (petla == 1);
			}
		}
		else if (nr == 3)
			return 0;

		else {
			std::cout << "Cos poszlo nie tak. Sproboj jeszcze raz.\n";
		}
	}
}