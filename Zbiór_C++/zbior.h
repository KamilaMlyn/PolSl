#pragma once
#include <iostream>
#include <list>
#include <ostream>

class Zbior{
	std::list<int> lista;

	std::list<int>::iterator gdzie(int a) {
		for (std::list<int>::iterator iter = lista.begin(); iter != lista.end(); iter++) {
			if (*iter == a) {
				return iter; //iterator do miejsca szukanej liczby
			}
		}
	};

public:
	Zbior() {};
	~Zbior() {};

	size_t getIlosc() const { return lista.size(); }

	std::list<int> getLista() const { return lista; }

	bool czy_nalezy(int a) const {
		for (auto iter = lista.begin(); iter != lista.end(); iter++) {
			if (*iter == a) {
				return true; //liczba a jest w zbiorze
			}
		}
		return false;
	};

	bool dodaj(int a) {
		if (lista.size() == 0)
			lista.push_back(a);//zbi�r pusty, wi�c dodajemy
		else {
			if (czy_nalezy(a) == true) {
				return false;//element znajduje si� w zbiorze, czyli nie dodajemy
			}
			else
				lista.push_back(a);
		}
		return true;
	};

	bool usun(int a) {
		if (czy_nalezy(a) == false) {
			return false;//element nie nale�y do zbioru wi�c nie ma co usuwa�
		}
		else {
			lista.erase(gdzie(a));
			return true;
		}
	};
	void oproznij() {
		lista.clear();
	};

	std::list<int> operator+(const Zbior& B) {
		std::list<int> temp;
		if (lista.size() != 0 && B.getIlosc() != 0) {//je�eli listy s� niepuste
			for (auto x : B.getLista()) {
				temp.push_back(x);
			}
			for (auto y : lista) {
				if (B.czy_nalezy(y) == false) {
					temp.push_back(y);//je�eli element nale�y tylko do listy this jest dodawany
				}
			}
		}
		else if (lista.size() == 0 && B.getIlosc() != 0) {
			return B.getLista();
		}
		else if (lista.size() != 0 && B.getIlosc() == 0) {
			return lista;
		}
		else std::cout << "Zbior jest pusty" << std::endl;//suma ze zbior�w pustych to zbi�r pusty
		return temp;
	};

	std::list<int> operator-(const Zbior& B) {
		std::list<int> temp;
		if (lista.size() != 0 && B.getLista().size() != 0) {
			for (auto x : lista) {
				if (B.czy_nalezy(x) == false) {
					temp.push_back(x);
				}
			}
		}
		else if (lista.size() == 0) {
			std::cout << "Zbior pusty" << std::endl;
		}
		else if (B.getLista().size() == 0) {
			return lista;
		}
		else std::cout << "Zbior pusty" << std::endl;
		return temp;
	};

	std::list<int> operator*(const Zbior& B) {
		std::list<int>temp;
		int k = 0;
		if (lista.size() != 0 && B.getLista().size() != 0) {
			for (int x : lista) {
				if (B.czy_nalezy(x) == true) {
					temp.push_back(x);
					++k;
				}
			}
			if (k == 0) std::cout << "Zbior pusty" << std::endl;
		}
		else std::cout << "Zbior pusty" << std::endl;
		return temp;
	};
	void operator+=(const Zbior& B);
	void operator-=(const Zbior& B);
	void operator*=(const Zbior& B);
};
/*
6 B
Klasa implementuj�ca zbi�r (zbi�r jest kontenerem, w kt�rym elementy nie mog� si� powtarza�)
z nast�puj�cymi metodami:
done- Dodaj/usu� element do/ze zbioru
done- Funkcj� opr�niaj�c� zbi�r
done- Funkcj� o warto�ciach logicznych informuj�c�, czy dany element nale�y do zbioru
done- Wypisz zawarto�� zbioru (odpowiedni operator biblioteki iostream)
done- Informacja, ile element�w zbi�r zawiera.

done- Przeci��one operatory +, -, * implementuj�ce odpowiednio operacje sumy, r�nicy oraz
iloczynu (cz�ci wsp�lnej) mnogo�ciowych. Fakultatywnie mo�na przewidzie� tak�e alternatyw�
wykluczaj�c�.
done- Przeci��one operatory +=, -=, *=, zakodowane w postaci inline za pomoc� wcze�niej
zaimplentowanych operator�w +, -, *.
Inne:
done- Jako struktur� danych przechowuj�cych zawarto�� kontenera u�y� list� 2-kierunkow�.
*/