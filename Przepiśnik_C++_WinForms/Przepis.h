#pragma once

#include <string>
#include <vector>
#include <filesystem> 
#include <fstream>
#include <iostream>
#include <regex>
#include <msclr\marshal_cppstd.h>//zamiana String na string

namespace fs = std::filesystem;
class Pliki {
	std::fstream stream;
	std::smatch znalezione; //do regexa - zapisujemy tu wynik w regex_match()
	std::string linia; //do getline()
public:
	//return:
	//0 - znaleziono w pliku
	//1 - nie znaleziono w pliku
	//2 - nie udalo sie otworzyc pliku
	
	//wyszukiwanie wyrazenia regolarnego w pliku
	int szukaj(const fs::path& path,const std::regex& reg,const std::string& tekst) { 
		//sprawdzanie czy login istnieje w bazie:
		stream.open(path);
		if (!stream) {
			std::cout << "nie udalo sie otworzyc pliku\n";
			return 2; //nie udalo sie otworzyc pliku
		}
		while (std::getline(stream, linia)) {
			if (std::regex_search(linia, znalezione, reg)) {
				std::cout << "znaleziono\n";
				return 0; //znaleziono
			}
		}
		stream.close();
		return 1; //nie znaleziono
	}
	
	//wpisanie na koniec pliku
	bool wpisz(const fs::path& path,const std::string& tekst) {
		stream.open(path, std::ios::app);
		if (!stream) {
			std::cout << "nie udalo sie otworzyc pliku\n";
			return false; //nie udalo sie otworzyc pliku
		}
		else {
			stream << tekst;
			stream.close();
			std::cout << "pomyslne zakonczenie\n";
			return true;
		}
	}
	bool wpisz(const fs::path& path, const int& tekst) {
		stream.open(path, std::ios::app);
		if (!stream) {
			std::cout << "nie udalo sie otworzyc pliku\n";
			return false; //nie udalo sie otworzyc pliku
		}
		else {
			stream << tekst;
			stream.close();
			std::cout << "pomyslne zakonczenie\n";
			return true;
		}
	}
	
};

class Skladnik  {
	std::string nazwa;
	int kcal; //na 100g
public:
	Skladnik() {};
	Skladnik(std::string nazwa, int kcal) : nazwa(nazwa), kcal(kcal) {};
	~Skladnik() {};
	std::string getNazwa() const{ return nazwa; }
	int getKcal() const { return kcal; }
	void setNazwa(const std::string& nazwa) { this->nazwa = nazwa; };
	void setKcal(const int& kcal) { this->kcal = kcal; }
};

class Skladniki : public Skladnik, public Pliki{
	std::vector<Skladnik*>wszystkie_Skladniki; //wektor zawierajacy wszystkie utworzone skladniki
	fs::path lokal_bazy_s = fs::current_path() / "Pliki Programu/Baza_Skladniki.txt";
	bool zmiana_s = false;
	//int ile_skladnikow = 0;
	
protected:
	Skladniki() {};
	~Skladniki() {};

public:
	bool getZmiana_s()const { return zmiana_s; }
	void setZmiana_s() { zmiana_s = true; }
	void usun_skladniki() {
		for (int i = 0; i < wszystkie_Skladniki.size() - 1; ++i) {
			delete wszystkie_Skladniki[i];
		}
	}
	static Skladniki& getInstSkladniki() { //wzorzec projektowy singleton- wymuszenie jednej instancji
		static Skladniki instancja; // zmienna statyczna - tworzona przy pierwszym wywo³aniu funkcji
		return instancja;
	}
	Skladniki(Skladniki& other) = delete; //wylaczenie konstrintora kopiujacego
	void operator =(const Skladniki&) = delete; //wylaczenie konstrintora kopiujacego
	std::vector<Skladnik*> getWszystkie_Skladniki()const { return getInstSkladniki().wszystkie_Skladniki; };
	int czy_istnieje_skladnik(const std::string& nazwa) {
		for (int i = 0; i < getInstSkladniki().getWszystkie_Skladniki().size(); ++i) {
			if (nazwa == getInstSkladniki().getWszystkie_Skladniki()[i]->getNazwa()) { //jesli skladnik o takiej nazwie jest w bazie
				return i;
			}
		}
		return -1; //jesli nie ma takiego skladnika w bazie;
	}
	void dodaj_skladnik(Skladnik* skladnik) {
		wszystkie_Skladniki.push_back(skladnik); 	
	}
	bool skladnik_do_bazy(Skladnik* skladnik) {
		if (wpisz(lokal_bazy_s, ("@" + skladnik->getNazwa() + "\n$"))) {
			wpisz(lokal_bazy_s, skladnik->getKcal());
			return true;
		}
		else {
			std::cout << "nie udalo sie wpisac do bazy\n";
			return false;
		}
	}
	void nazwa_skladnika_z_bazy(){
		int licznik = 0;
		std::ifstream stream;
		std::string linia; //do niej bedzie wszytywana linia z pliku
		std::string tekst; //do tekstu bedzie nazwa wczytywana z linii nazwa skladnika/jego ilosc
		stream.open(lokal_bazy_s);
		while (std::getline(stream, linia)) {
			tekst.insert(0, linia, 1, linia.size() - 1); //wrzuca do "tekst" to co getline pobral z pliku bez znaku typu elementu przepisu-bez pierwszego znaku
			getInstSkladniki().wszystkie_Skladniki[licznik]->setNazwa(tekst);
			tekst = "";
			std::getline(stream, linia);
			++licznik;
		}
		stream.close();		
	}
	void kalorycznosc_skladnika_z_bazy(){
		std::ifstream stream;
		std::string linia; //do niej bedzie wszytywana linia z pliku
		std::string tekst; //do tekstu bedzie nazwa wczytywana z linii nazwa skladnika/jego ilosc
		int licznik = 0;
		stream.open(lokal_bazy_s);
		while (std::getline(stream, linia)) {
			std::getline(stream, linia);
			tekst.insert(0, linia, 1, linia.size() - 1);
			getInstSkladniki().wszystkie_Skladniki[licznik]->setKcal(stoi(tekst));
			tekst = "";
			++licznik;
		}
		stream.close();
	}
	bool skladniki_z_bazy_ok() {
		std::string linia; //do niej bedzie wszytywana linia z pliku
		std::string tekst; //do tekstu bedzie nazwa wczytywana z linii nazwa skladnika/jego ilosc
		std::ifstream stream;
		
		stream.open(lokal_bazy_s);
		if (!stream) {
			std::cerr << "nie udalo sie otworzyc pliku\n";
			return false; //nie udalo sie otworzyc pliku
		}
		else {
			//sprawdzenie czy forma skladnika w pliku jest ok:
			while (std::getline(stream, linia)) {
				if (linia[0] == '@') {
					std::getline(stream, linia);
					if (linia[0] == '$') {
						tekst.insert(0, linia, 1, linia.size() - 1);
						for (int i = 0; i < tekst.size(); ++i) {
							if (!std::isdigit(tekst[i])) {
								std::cerr << "w miejscu gdzie ma byc kalorycznosc skladnika nie ma liczby\n";
								stream.close();
								return false; //jesli nie ma liczby w miejscu kalorycznosci
							}
						}
						Skladnik* skladnik = new Skladnik;
						getInstSkladniki().wszystkie_Skladniki.push_back(skladnik);
					}
					else {
						std::cerr << "w miejscu gdzie ma byc kalorycznosc skladnika nie ma pierwszego znaku: '$'\n";
						return false;
					}
				}
				else {
					std::cerr << "w miejscu gdzie ma byc nazwa skladnika nie ma pierwszego znaku: '@'\n";
					return false;
				}
			}
			//jesli forma pliku jest ok:
			stream.close();
			return true;
		}
	}
	void usun_skladnik(int index) {
		wszystkie_Skladniki.erase(wszystkie_Skladniki.begin() + index);
		zmiana_s = true;
	}
	bool skladniki_do_bazy() {
		if (zmiana_s == true) {
			std::ofstream stream;
			stream.open(lokal_bazy_s);
			if (!stream) {
				System::Windows::Forms::MessageBox::Show("Nie udany zapis sk³adników do bazy");
				return false;
			}
			else {
				stream.clear();
				for (int i = 0; i < wszystkie_Skladniki.size(); ++i) {
					stream << "@" << wszystkie_Skladniki[i]->getNazwa() << "\n";
					stream << "$" << wszystkie_Skladniki[i]->getKcal() << "\n";
				}
				stream.close();
				System::Windows::Forms::MessageBox::Show("Udany zapis sk³adników do bazy");
				zmiana_s = false;
				return true;
			}
		}
		return true;
	}
};

class Przepis : public Skladniki {
	int nr_przepisu;
	std::string nazwa;
	std::vector<std::pair<Skladnik*, int>>skladniki;
	int ilosc_skladnikow = 0;
	std::vector<std::string>wykonanie;
public:
	Przepis() {};
	~Przepis() {};
	void setNazwa(std::string nazwa) { this->nazwa = nazwa; }
	std::vector<std::pair<Skladnik*, int>> getSkladniki()const { return skladniki; }
	void dodaj_skladnik(Skladnik*skl, int ile) { 
		skladniki.push_back(std::make_pair(skl,ile));	
	}
	std::string getNazwa()const { return nazwa; }
	int getIlosc_skladnikow()const { return ilosc_skladnikow; }
	void dodajIlosc_skladnikow() { ++ilosc_skladnikow; }
	std::vector<std::string> getWykonanie()const { return wykonanie; }
	void dodaj_Wykonanie(std::string w) { wykonanie.push_back(w); }
	bool lista_zakupow(fs::path sciezka) {
		std::ofstream stream;
		stream.open(sciezka);
		if (!stream) {
			return false;
		}
		else {
			//wrzucenie nazwy przepisu
			stream << "\t\t" << nazwa << "\n";
			//wrzucenie skladnikow z iloscia
			stream << "\tSkladniki:\n";
			for (int i = 0; i < skladniki.size(); ++i) {
				stream << "- "<<skladniki[i].second<<"g " << skladniki[i].first->getNazwa() << "\n";
			}
			stream.close();
			return true;
		}
	}; 
	void wyczysc_skladniki() { skladniki.clear(); }
	void wyczysc_wykonanie() { wykonanie.clear(); }
};

class Przepisy :public Przepis {
	std::vector<Przepis*>przepisy;
	fs::path lokal_bazy_p = fs::current_path() / "Pliki Programu/Baza_Przepisy.txt";
	bool zmiana_p = false;
	std::string tekst;
	Przepisy() {};
	~Przepisy() {};
public:
	bool getZmiana_p()const { return zmiana_p; }
	void setZmiana_p() { zmiana_p = true; }
	void usun_przepisy() {
		for (int i = 0; i < przepisy.size(); ++i) {
			delete przepisy[i];
		}
	}
	std::vector<Przepis*> getPrzepisy()const { return przepisy; }
	bool przepisy_z_bazy() {
		std::string linia;
		std::string tekst;
		std::ifstream stream;
		stream.open(lokal_bazy_p);
		if (!stream) {
			std::cout << "nie udalo sie otworzyc pliku\n";
			return false; //nie udalo sie otworzyc pliku
		}
		else {//udalo sie otworzyc plik:
			Przepis* przepis_ost;
			std::getline(stream, linia);
			while (stream.peek() != EOF) {
				if (linia[0] == '@') { //jesli pierwszy znak linii to @
					Przepis* przepis = new Przepis;
					tekst.insert(0, linia, 1, linia.size() - 1);
					przepis->setNazwa(tekst); //ustawianie nazwy przepisu
					tekst = "";
					while (std::getline(stream, linia)) {
						if (linia[0] == '#') { //jesli pierwszy znak linii to #
							tekst.insert(0, linia, 1, linia.size() - 1);
							int index = czy_istnieje_skladnik(tekst);
							if (index == -1) {
								std::cerr << "nie istnieje skladnik o nazwie " << tekst << " w bazie\n";
								delete przepis;
								return false;
							}
							else {
								//s.first= getInstSkladniki().getWszystkie_Skladniki()[index];
								tekst = "";
								std::getline(stream, linia);
								if (linia[0] == '-') { //jesli pierwszy znak linii to -
									tekst.insert(0, linia, 1, linia.size() - 1);
									for (int i = 0; i < tekst.size(); ++i) {
										if (!std::isdigit(tekst[i])) { //isdigit()- sprawdzenie czy jest liczba
											std::cerr << "w miejscu gdzie ma byc ilosc skladnika a nie ma liczby\n";
											stream.close();
											delete przepis;
											return false;
										}
										else {
											przepis->dodaj_skladnik(getInstSkladniki().getWszystkie_Skladniki()[index], std::stoi(tekst));
											tekst = "";
										}
									}
								}
								else {
									std::cerr << "po skladniku nie ma: -ilosc\n";
									delete przepis;
									return false;
								}
							}
						}
						else if (linia[0] == '*') {
							tekst.insert(0, linia, 1, linia.size() - 1);
							przepis->dodaj_Wykonanie(tekst);
							tekst = "";
							przepis_ost = przepis;
						}
						else if (linia[0] == '@') {
							getInstPrzepisy().przepisy.push_back(przepis);
							break;
						}
					}
				}
			}
			getInstPrzepisy().przepisy.push_back(przepis_ost);
			stream.close();
			return true;
		}
	}
	void dodaj_przepis(Przepis* przepis) { 
		przepisy.push_back(przepis); 
		zmiana_p = true;
	};
	bool dodaj_przepis_z_pliku(const fs::path& lokalizacja) {
		Przepis* przepis = new Przepis();
		std::ifstream stream;
		stream.open(lokalizacja);
		if (!stream) {
			std::cout << "nie udalo sie otworzyc pliku\n";
			return false; //nie udalo sie otworzyc pliku
		}
		else {
			std::string linia;
			std::getline(stream, linia);
			if (linia[0] == '@') { //jesli pierwszy znak linii to @
				tekst.insert(0, linia, 1, linia.size() - 1);
				//sprawdzenie czy w bazie nie ma juz przepisu z taka nazwa
				if (czy_istnieje_nazwa(tekst) != -1) {
					return false;
				}
				else {
					przepis->setNazwa(tekst); //ustawianie nazwy przepisu
					tekst = "";
					while (std::getline(stream, linia)) {
						if (linia[0] == '#') { //jesli pierwszy znak linii to #
							tekst.insert(0, linia, 1, linia.size() - 1);
							int index = czy_istnieje_skladnik(tekst);
							if (index == -1) {
								std::cerr << "nie istnieje skladnik o nazwie " << tekst << " w bazie\n";
								return false;
							}
							else {
								tekst = "";
								std::getline(stream, linia);
								if (linia[0] == '-') { //jesli pierwszy znak linii to -
									tekst.insert(0, linia, 1, linia.size() - 1);
									for (int i = 0; i < tekst.size(); ++i) {
										if (!std::isdigit(tekst[i])) { //isdigit()- sprawdzenie czy jest liczba
											std::cerr << "w miejscu gdzie ma byc ilosc skladnika a nie ma liczby\n";
											stream.close();
											return false;
										}
										else {
											//s.second = stoi(tekst);
											przepis->dodaj_skladnik(getInstSkladniki().getWszystkie_Skladniki()[index], std::stoi(tekst));

											tekst = "";
										}
									}
								}
								else {
									std::cerr << "po skladniku nie ma: -ilosc\n";
									return false;
								}
							}
						}
						else if (linia[0] == '*') {
							tekst.insert(0, linia, 1, linia.size() - 1);
							przepis->dodaj_Wykonanie(tekst);
							tekst = "";
						}
					}
				}
			}
			
			getInstPrzepisy().przepisy.push_back(przepis);
			stream.close();
			zmiana_p = true;
			return true;
		}
		delete przepis;
	};
	static Przepisy& getInstPrzepisy() { //wzorzec projektowy singleton- wymuszenie jednej instancji
		static Przepisy instancja; // zmienna statyczna - tworzona przy pierwszym wywo³aniu funkcji
		return instancja;
	}
	Przepisy(Przepisy& other) = delete; //wylaczenie konstrintora kopiujacego
	void operator =(const Przepisy&) = delete; //wylaczenie konstrintora kopiujacego
	void usun_przepis(Przepis* przepis) {
		for (int i = 0; i < przepisy.size(); ++i) {
			if (przepis == przepisy[i]) {
				if (i == 0) {
					przepisy.erase(przepisy.begin());
					delete przepis;
					zmiana_p = true;
					return;
				}
				else {
					przepisy.erase(przepisy.begin() + i);
					delete przepis;
					zmiana_p = true;
					return;
				}
			}
		}
	}
	bool przepisy_do_bazy() {
		if (zmiana_p == true) {
			std::ofstream stream;
			stream.open(lokal_bazy_p);
			if (!stream) {
				System::Windows::Forms::MessageBox::Show("Nie udany zapis przepisów do bazy");
				return false;
			}
			else {
				stream.clear();
				for (int i = 0; i < przepisy.size(); ++i) {
					stream << "@" << przepisy[i]->getNazwa()<<"\n";
					for (int j = 0; j < przepisy[i]->getSkladniki().size(); ++j) {
						stream << "#" << przepisy[i]->getSkladniki()[j].first->getNazwa() << "\n";
						stream << "-" << przepisy[i]->getSkladniki()[j].second << "\n";
					}
					for (int j = 0; j < przepisy[i]->getWykonanie().size(); ++j) {
						stream << "*" << przepisy[i]->getWykonanie()[j] << "\n";
					}
				}
				stream.close();
				System::Windows::Forms::MessageBox::Show("Udany zapis przepisów do bazy");
				zmiana_p = false;
				return true;
			}
		}
		return true;
	}
	int czy_istnieje_nazwa(std::string nazwa) {
		for (int i = 0; i < przepisy.size(); ++i) {
			if (nazwa == przepisy[i]->getNazwa()) {
				return i;
			}
		}
		return -1;
	}
};

class IKalk {
public:
	float suma;
	virtual float jednostka(Przepis* przepis) = 0;
	virtual ~IKalk() {}
};
class Kcal : public IKalk{
	float jednostka(Przepis* przepis)override{
		suma = 0;
		for (int i = 0; i < przepis->getSkladniki().size(); ++i) {
			suma += przepis->getSkladniki()[i].second * przepis->getSkladniki()[i].first->getKcal() / 100;
		}
		return suma;
	}
};
class kJ : public IKalk {
	float jednostka(Przepis* przepis)override{
		suma = 0;
		for (int i = 0; i < przepis->getSkladniki().size(); ++i) {
			suma += przepis->getSkladniki()[i].second * przepis->getSkladniki()[i].first->getKcal() / 100*4,19;
		}
		return suma;
	}
};
class Kalkulator : public Przepis {
	std::shared_ptr<IKalk> wybor_uzytkownika= std::make_shared<Kcal>(); //domyslnie wybrane kalorie
	bool tryb = true;  //true - kcal; false - kJ
	Kalkulator() {};
	~Kalkulator() {};
	
public:
	void wyborJ() { tryb = false; }
	void wyborK() { tryb = true; }
	bool getWybor() { return tryb; }
	static Kalkulator& getInstKalk() { 
		static Kalkulator instancja; 
		return instancja; 
	}
	Kalkulator(Kalkulator& other) = delete; //wylaczenie konstrintora kopiujacego
	void operator =(const Kalkulator&) = delete; //wylaczenie konstrintora kopiujacego
	void wybor(const std::shared_ptr<IKalk>& wybor_uzytkownika) {
		this->wybor_uzytkownika = wybor_uzytkownika;
		
	}
	float oblicz(Przepis* przepis) {
		return wybor_uzytkownika->jednostka(przepis);
	}
};

class Uzytkownik : public Przepis {
	std::vector<Przepis*>ulubione;
	fs::path lokal_ulub = fs::current_path() / "Pliki Programu/Baza_Ulubione";
	bool zmiana_u = false;
	bool log = false;
	std::string login;
	Uzytkownik() {};
	~Uzytkownik() {};
public:
	void setZmiana_u() { zmiana_u = true; }
	bool getZmiana_u() { return zmiana_u; }
	bool getLog() { return log; }
	static Uzytkownik& getInstUzyt() {
		static Uzytkownik instancja;
		return instancja;
	}
	Uzytkownik(Uzytkownik& other) = delete; //wylaczenie konstrintora kopiujacego
	void operator =(const Uzytkownik&) = delete; //wylaczenie konstrintora kopiujacego
	bool czy_jest_ulubiony(Przepis* przepis) {
		for (int i = 0; i < ulubione.size(); ++i) {
			if (przepis == ulubione[i])
				return true;
		}
		return false;
	}
	std::string getLogin()const { return login; }
	std::vector<Przepis*> getUlubione()const { return ulubione; }
	int logowanie(const std::string& login, const std::string& haslo) {
		std::ifstream stream;
		std::string linia;
		stream.open(fs::current_path() / "Pliki Programu/Uzytkownicy.txt"); //ios::app - dodaje na koniec pliku i nie nadpisuje
		if (!stream) {
			std::cout << "nie udalo sie otworzyc pliku\n";
			return 2; //nie udalo sie otworzyc pliku
		}
		while (std::getline(stream, linia)) {
			if (linia == "@" + login + "@") {
				std::getline(stream, linia);
				if (linia == "#" + haslo + "#") {
					stream.close();
					log = true;
					this->login = login;
					std::cout << "zalogowano\n";
					return 0; //znaleziono uzytkownika
				}
			}
		}
		stream.close();
		return 1; //nie znaleziono uzytkownika
	}
	bool ulubione_z_bazy() {
		std::ifstream stream;
		std::string plik = login + ".txt";
		stream.open(lokal_ulub / plik);
		//jesli nie udalo sie otworzyc pliku
		if (!stream) {
			return false;
		}
		else {//jesli udalo sie otworzyc plik
			std::string linia;
			std::string text;
			while (std::getline(stream, linia)) {
				if (linia[0] == '-') {
					text.insert(0, linia, 1, linia.size() - 1);
					int nr = Przepisy::getInstPrzepisy().czy_istnieje_nazwa(text);
					text = "";
					if (nr != -1) {
						ulubione.push_back(Przepisy::getInstPrzepisy().getPrzepisy()[nr]);
					}
				}
				else {//jesli pierwszym znakiem linii nie jest '-'
					return false;
				}
			}
		}
		return true;
	}
	bool ulubione_do_bazy() {
		if (zmiana_u == false) {
			return true;
		}
		else {
			std::ofstream stream;
			std::string plik = login + ".txt";
			stream.open(lokal_ulub/plik);
			if (!stream) {
				return false;
			}
			else {//jesli udalo sie otworzyc plik bazy
				stream.clear();
				for (int i = 0; i < ulubione.size(); ++i) {
					stream << "-" << ulubione[i]->getNazwa()<<"\n";
				}
			}
			stream.close();
		}
		return true;
	}
	void wyloguj() { log = false; ulubione.clear(); }
	void dodaj_ulubione(Przepis* przepis) {
		ulubione.push_back(przepis);
	};
	void usun_z_ulubionych(const std::string& nazwa){
		for (int i = 0; i < ulubione.size(); ++i) {
			if (ulubione[i]->getNazwa() == nazwa) {
				ulubione.erase(ulubione.begin() + (i--));
				return;
			}
		}
	}
	void usun_z_ulubionych(Przepis* przepis) {
		for (int i = 0; i < ulubione.size(); ++i) {
			if (ulubione[i] == przepis) {
				ulubione.erase(ulubione.begin() + (i--));
				return;
			}
		}
	}
	/*
	* return:
	* 0 - istnieje juz taki uzytkownik
	* 1 - nie udalo sie otworzyc pliku z danymi uzytkownikow
	* 2 - haslo nie spelnia wymagan
	* 3 - udane
	*/
	int nowy_uz(const std::string& login, const std::string& haslo) {
		fs::path sciezka = fs::current_path() / "Pliki Programu/Uzytkownicy.txt";
		std::cerr << sciezka << "\n";
		std::regex reg_login("@" + login + "@");
		if (szukaj(sciezka, reg_login, login) == 0) {
			std::cout << "Istnieje juz uzytkownik " << login << "\n";
			return 0;
		}
		else if (szukaj(sciezka, reg_login, login) == 2) {
			std::cout << "nie udalo sie otworzyc pliku\n";
			return 1;
		}
		else {
			/*sprawdzanie poprawnosci hasla, ktore powinno zawierac :
			* -min 8 znakow
			* -min 1 duza litere
			* -min 1 mala litere
			* -min 1 cyfre
			* znaki specjalne ^$*&-_
			*
			*lookahead - minimalne wymagania ktore musi spelniac ciag: (?=wyrazenie)
			*zeby mogly byc w dowolnej kolejnosci dodajemy .*: (?=.*wyrazenie)
			*/
			std::regex reg_haslo("(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[A-Z])(?=.*[\\^\\$\\*\\?\\&\\-\\_])[A-Za-z0-9\\^\\$\\*\\?\\&\\-\\_]{8,}");
			std::smatch znalezione;
			if (std::regex_match(haslo, znalezione, reg_haslo)) { //jesli haslo jest poprawne...
				fs::path sciezka = fs::current_path() / "Pliki Programu/Uzytkownicy.txt";
				std::string login_plik = "@" + login + "@\n#" + haslo + "#\n";
				if (wpisz(sciezka, login_plik)) { //...to wpisz je do pliku uzytkownikow
					//utworzenie pliku z ulubionymi przepisami
					std::ofstream stream;
					std::string plik = login + ".txt";
					stream.open(fs::current_path() / "Pliki Programu/Baza_ulubione"/ plik );
					stream.close();
					return 3; //pomyslne zakonczenie
				}
				else {
					std::cout << "nie udalo sie wpisac do pliku\n";
					return 1;
				}
			}
			else {
				std::cout << "haslo nie jest ok\n";
				return 2; //haslo nie jest ok
			}
		}
	}
};

//wylaczenie konstruktora kopiujacego
/*
#include <msclr\marshal_cppstd.h>
//zamiana String^ na string
System::String^ tekst1_clr = textBox1->Text;// 
std::string tekst1 = msclr::interop::marshal_as<std::string>(tekst1_clr);

//zamiana string na String^
std::string tekst2 = "test";
String^ tekst2_clr = gcnew String(tekst2.c_str());
textBox1->Text = tekst2_clr;
*/