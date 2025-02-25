#pragma once

template<class T>
void dodaj(std::vector<T>& przepis) {
	static std::string tekst;
	do {
		std::getline(std::cin, tekst);
		przepis.push_back(tekst);
	} while (tekst != "koniec");
	przepis.erase(przepis.end() - 1);
	system("cls");
}