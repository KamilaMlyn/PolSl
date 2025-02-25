#pragma once
#include <memory>
#include <vector>
#include <string>

class Przepis {
	std::string nazwa;
	std::vector<std::string>skladniki_gl;
	std::vector<std::string>dodatki;
	std::vector<std::string>przyprawy;
	std::vector<std::string>przygotowanie;
public:
	Przepis() {};
	Przepis(const std::string& nazwa,
		const std::vector<std::string>&skladniki_gl,
		const std::vector<std::string>&dodatki,
		const std::vector<std::string>&przyprawy,
		const std::vector<std::string>&przygotowanie) : nazwa(nazwa),
												 skladniki_gl(skladniki_gl),
												 dodatki(dodatki),
												 przyprawy(przyprawy),
												 przygotowanie(przygotowanie) {};
	~Przepis() {}

	std::string getNazwa() const {
		return nazwa;
	}
	
	std::vector<std::string> getSkladniki_gl() const {
		return skladniki_gl;
	}

	std::vector<std::string> getDodatki() const {
		return dodatki;
	}
	
	std::vector<std::string> getPrzyprawy() const {
		return przyprawy;
	}
	
	std::vector<std::string> getPrzygotowanie() const {
		return przygotowanie;
	}

};


class Przepisy {
	std::vector<Przepis>przepisy;
	int size =0;
public:
	Przepisy() {};
	~Przepisy() {};
	std::vector<Przepis> getPrzepisy() const {
		return przepisy;
	}
	void dodajPrzepis(Przepis a) {
		przepisy.push_back(a);
		size++;
	}
	int getsize() const{
		return size;
	}
	Przepis &operator[](int N) {
		return przepisy[N];
	}
	
};