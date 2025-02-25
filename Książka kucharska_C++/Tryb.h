#pragma once
#include <vector>
#include <iostream>
#include <string>
#include "Przepisv2.h"
#include <conio.h>

class IBaza {
public:
	virtual void wyswietlanie_przepisu(Przepis przepis) = 0;//czysto wirtualny konstruktor
	virtual ~IBaza() {}
};

class Tryb1 : public IBaza {
	void wyswietlanie_przepisu(Przepis przepis) override {
		std::cout <<"\t"<< przepis.getNazwa() << ":\n\tSkladniki glowne:\n";
		for (std::string i : przepis.getSkladniki_gl()) {
			std::cout <<"-" << i << "\n";
		}
		std::cout << "\n\tDodatki:\n";
		for (std::string i : przepis.getDodatki()) {
			std::cout << "-" << i << "\n";
		}
		std::cout << "\n\tPrzyprawy:\n";
		for (std::string i : przepis.getPrzyprawy()) {
			std::cout << "-" << i << "\n";
		}
		std::cout << "\n\tPrzygotowanie:\n";
		for (std::string i : przepis.getPrzygotowanie()) {
			std::cout << "-" << i << "\n";
		}
		std::cout << "\nNacisnij dowolny klawisz aby przejsc do menu\n";
		_getch();
		system("cls");
	}
};

class Tryb2 : public IBaza {
	void wyswietlanie_przepisu(Przepis przepis) override {
		std::cout << "Nacisnij dowolny klawisz aby isc dalej\n";
		std::cout << "\t" << przepis.getNazwa() << ":\n\tSkladniki glowne:\n";
		for (std::string i : przepis.getSkladniki_gl()) {
			std::cout << "-" << i << "\n";
			_getch();
		}
		std::cout << "\n\tDodatki:\n";
		for (std::string i : przepis.getDodatki()) {
			std::cout << "-" << i << "\n";
			_getch();
		}
		std::cout << "\n\tPrzyprawy:\n";
		for (std::string i : przepis.getPrzyprawy()) {
			std::cout << "-" << i << "\n";
			_getch();
		}
		std::cout << "\n\tPrzygotowanie:\n";
		for (std::string i : przepis.getPrzygotowanie()) {
			std::cout << "-" << i << "\n";
			_getch();
		}
		std::cout << "\nNacisnij dowolny klawisz aby przejsc do menu\n";
		_getch();
		system("cls");
	}
};

class WyborTrybu {
	std::shared_ptr<IBaza>wybor_uzytkownika;
	Przepis przepis;
public:
	WyborTrybu() {};
	void wybor(const std::shared_ptr<IBaza>& wybor_uzytkownika, Przepis przepis) {
		this->wybor_uzytkownika = wybor_uzytkownika;
		this->przepis = przepis;
	}
	void wyswietlanie_przepisu() {
		system("CLS");

		wybor_uzytkownika->wyswietlanie_przepisu(przepis);
	}
};