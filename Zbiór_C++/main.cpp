#include "zbior.h"
#include "operatory.h"
int main() {
	Zbior zbior;

	zbior.dodaj(1);
	std::cout<<zbior.czy_nalezy(1); //1 - nalezy
	zbior.dodaj(2);
	std::cout << zbior.czy_nalezy(2); //1 - nalezy
	std::cout << zbior.czy_nalezy(5); //0 - nie nalezy
	std::cout<<zbior.getIlosc(); //2 elementy
	std::cout << "\n";

	zbior.usun(1);
	std::cout << zbior.getIlosc(); //1 element
	std::cout << zbior.czy_nalezy(1); //0 - nie nalezy
	std::cout << zbior.czy_nalezy(2); //1 - nalezy
	std::cout << "\n";

	zbior.dodaj(1);
	zbior.dodaj(1);
	zbior.dodaj(1);
	std::cout << zbior.getIlosc(); //2 elementy(1 i 2)
	zbior.oproznij();
	std::cout << zbior.getIlosc() << std::endl; //0 elementow

	zbior.dodaj(5);
	zbior.dodaj(1);
	zbior.dodaj(1);
	zbior.dodaj(1);
	zbior.dodaj(10);
	std::cout << zbior << std::endl; //5 1 10 

	Zbior z;
	z.dodaj(5);
	z.dodaj(4);
	Zbior suma;

	z += zbior;
	std::cout << z << std::endl; //5 1 10 4

	z.dodaj(9);
	z.dodaj(11);
	z += zbior;
	std::cout << z << std::endl; //5 1 10 4 9 11

	z *= zbior; 
	std::cout << z << std::endl; //5 1 10


	z -= zbior;
	std::cout << z << std::endl; //Zbior pusty

	return 0;
}