#include "Okno_glowne.h"
#include "Przepis.h"
using namespace System;
using namespace System::Windows::Forms;

[STAThread]
int main() {
	Application::EnableVisualStyles();
	Application::SetCompatibleTextRenderingDefault(false);
	Application::Run(gcnew Project3::Okno_glowne());
	return 0;
}