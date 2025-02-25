#pragma once
#include <vector>
#include <string>

struct Pliki{ std::string Plik_we, Plik_wy; };
struct Param { int k; int d; };

/**Funkcja odczytuje dane z konsoli, sprawdza ich poprawnoœæ i zapisuje do struktury Param.
@param param struktura przechowuj¹ca iloœæ wymiarów i klastrów.
@param argc liczba argumentów przekazanych w konsoli.
@param argv tablica argumentów wprowadzonych w konsoli.
@param pliki struktura zawieraj¹ca nazwy plików wejœciowych i wyjœciowych.
@return zwraca true gdy odczyt danych z konsoli uda³ siê, lub false gdy nie.
*/
bool odczyt(Param& param,int argc, char** argv, Pliki& pliki);

/**Funkcja wyœwietlaj¹ca parametry programu.
@param nazwa_prog nazwa programu.
*/
void help(std::string nazwa_prog);
struct Wektor { std::vector < double > wspolrzedne; int nr_klastra{ 0 }; };

/** Funkcja sprawdza to¿samoœæ iloœci wymiarów puntów z pliku wejœciowego z ich iloœci¹ wprowadzon¹ w konsoli.
* Wczytuje dane z pliku wejœciowego i umieszcza je w wektorze Dane.
@param param struktura przechowuj¹ca iloœæ wymiarów i klastrów.
@param pliki struktura zawieraj¹ca nazwy plików wejœciowych i wyjœciowych.
@param Dane wektor typu "Wektor", przechowuj¹cy wektor punktów z pliku wejœciowego i klaster ka¿dego z nich.
@return zwraca true gdy odczyt danych z pliku wejœciowego uda³ siê, lub false gdy nie.
*/
bool odczyt_z_pliku(Param param, Pliki pliki, std::vector <Wektor>&Dane);

/** Funkcja losuj¹ca liczby w danym zakresie.
@param low minimalna wartoœæ losowanej liczby.
@param high maksymalna wartoœæ losowanej liczby.
@return zwraca wylosowan¹ liczbê.
*/
int los(int low, int high);

/** Funkcja losuje poczatkowe centroidy sposrod punktow z pliku wejsciowego.
* Oblicza odleglosci punktow do kazdego z wylosowanych centroidow.
* Przydziela kazdemu z punktow klaster z centroidem do ktorego odleglosc punktu jest najmniejsza.
@param param struktura przechowujaca ilosc wymiarow i klastrow.
@param Dane wektor typu "Wektor", przechowujacy wektor punktow z pliku wejsciowego i klaster kazdego z nich.
@param centroidy wektor typu "Wektor", przechowujacy wspolrzedne kazdego z centroidow.
 Adres komorki przechowujacy dany centroid jest jednoczesnie numerem klastra do ktorego nalezy.
@param odleglosci wektor dwuwymiarowy typu "double", przechowujacy odleglosci kazdego z punktow od kazdego z centroidow,
 gdzie adres komorki wektora oznacza adres danej komorki w wektorze "Dane" a kolumny oznaczaja adres centroidu z wektora "centroidy".
 */
bool grupowanie_wstepne(Param param, std::vector <Wektor>&Dane, std::vector<Wektor>&centroidy, std::vector<std::vector<double>>&odleglosci);

/**Funkcja oblicza nowe wspolrzedne centroidow jako srednia wspolrzednych punktow w danym klastrze.
* Na podstawie nowych centroidow liczy odleglosci od nich do kazdego z punktow i przydziela nowe klastry.
@param param struktura przechowujaca ilosc wymiarow i klastrow.
@param Dane wektor typu "Wektor", przechowujacy wektor punktow z pliku wejsciowego i klaster kazdego z nich.
@param centroidy wektor typu "Wektor", przechowujacy wspolrzedne kazdego z centroidow.
@param kontrolny parametr zmieniajacy swoja wartosc z 0 na 1 w przypadku gdy ktorykolwiek z punktow zmienil swoj klaster.
@param odleglosci wektor dwuwymiarowy typu "double", przechowujacy odleglosci kazdego z punktow od kazdego z centroidow,
 gdzie adres komorki wektora oznacza adres danej komorki w wektorze "Dane" a kolumny oznaczaja adres centroidu z wektora "centroidy".
*/
bool grupowanie(Param param, std::vector <Wektor>&Dane, std::vector<Wektor>& centroidy, int& kontrolny, std::vector<std::vector<double>>odleglosci);

/**Funkcja zapisuje wyliczone poprzednimi funkcjami dane do pliku wyjsciowego w formacie:
* Klaster "nr_klastra"
* "wspolrzedne_centroidu"
* "wspolrzedne_punktow_nalezadych_do_danego_klastra"
@param param struktura przechowujaca ilosc wymiarow i klastrow.
@param Dane wektor typu "Wektor", przechowujacy wektor punktow z pliku wejsciowego i klaster kazdego z nich.
@param centroidy wektor typu "Wektor", przechowujacy wspolrzedne kazdego z centroidow.
@param pliki struktura zawierajaca nazwy klikow wejsciowych i wyjsciowych.
*/
bool zapis_do_pliku(Param param, std::vector <Wektor> Dane, std::vector<Wektor> centroidy, Pliki pliki);