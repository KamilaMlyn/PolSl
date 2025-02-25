#pragma once
#include "zbior.h"

inline std::ostream& operator<<(std::ostream& stream, Zbior z) {
	stream << "Elementy zbioru:\n";
	if (z.getIlosc() == 0) {
		stream << "Zbior pusty\n";
	}
	else {
		for (auto x : z.getLista()) {
			stream << x << " ";
		}
		stream << std::endl;
	}
	return stream;
}
inline void Zbior::operator+=(const Zbior& B) {
	lista = operator+(B);
};
inline void Zbior::operator-=(const Zbior& B) {
	lista = operator-(B);
};
inline void Zbior::operator*=(const Zbior& B) {
	lista = operator*(B);
};