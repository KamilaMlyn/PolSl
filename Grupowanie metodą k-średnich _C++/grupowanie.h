#pragma once
#include <vector>
#include <string>

struct Pliki{ std::string Plik_we, Plik_wy; };
struct Param { int k; int d; };

/**Funkcja odczytuje dane z konsoli, sprawdza ich poprawno�� i zapisuje do struktury Param.
@param param struktura przechowuj�ca ilo�� wymiar�w i klastr�w.
@param argc liczba argument�w przekazanych w konsoli.
@param argv tablica argument�w wprowadzonych w konsoli.
@param pliki struktura zawieraj�ca nazwy plik�w wej�ciowych i wyj�ciowych.
@return zwraca true gdy odczyt danych z konsoli uda� si�, lub false gdy nie.
*/
bool odczyt(Param& param,int argc, char** argv, Pliki& pliki);

/**Funkcja wy�wietlaj�ca parametry programu.
@param nazwa_prog nazwa programu.
*/
void help(std::string nazwa_prog);
struct Wektor { std::vector < double > wspolrzedne; int nr_klastra{ 0 }; };

/** Funkcja sprawdza to�samo�� ilo�ci wymiar�w punt�w z pliku wej�ciowego z ich ilo�ci� wprowadzon� w konsoli.
* Wczytuje dane z pliku wej�ciowego i umieszcza je w wektorze Dane.
@param param struktura przechowuj�ca ilo�� wymiar�w i klastr�w.
@param pliki struktura zawieraj�ca nazwy plik�w wej�ciowych i wyj�ciowych.
@param Dane wektor typu "Wektor", przechowuj�cy wektor punkt�w z pliku wej�ciowego i klaster ka�dego z nich.
@return zwraca true gdy odczyt danych z pliku wej�ciowego uda� si�, lub false gdy nie.
*/
bool odczyt_z_pliku(Param param, Pliki pliki, std::vector <Wektor>&Dane);

/** Funkcja losuj�ca liczby w danym zakresie.
@param low minimalna warto�� losowanej liczby.
@param high maksymalna warto�� losowanej liczby.
@return zwraca wylosowan� liczb�.
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