#pragma once
//#include "Wyglad_interface.h"
#include "Przepis.h" //plik z klasami funkcjonalnosci
#include "logowanie.h" //okno logowania
#include "Okno_dodaj_przepis.h" //okno dodawania przepisu
#include "Okno_dodaj_skladnik.h" //okno dodawania skladnika
#include "Okno_przepisu.h" //okno przepisu
#include "Okno_konta.h" //okno konta po zalogowaniu
#include "Okno_Skladnika.h" //okno skladnika
#include <thread> //do watkow

namespace Project3 {
	using namespace System;
	using namespace System::ComponentModel;
	using namespace System::Collections;
	using namespace System::Collections::Generic;
	using namespace System::Windows::Forms;
	using namespace System::Data;
	using namespace System::Drawing;

	public ref class Okno_glowne : public System::Windows::Forms::Form
	{
	public:
		Okno_glowne(void)
		{
			if (Skladniki::getInstSkladniki().skladniki_z_bazy_ok()) { //sprawdzenie czy pliki w bazie  skladnikow sa odpowiednio zapisane
				std::thread thr1(&Skladniki::nazwa_skladnika_z_bazy, &Skladniki::getInstSkladniki()); //watek ustawiajacy nazwe skladnikow z bazy
				std::thread thr2(&Skladniki::kalorycznosc_skladnika_z_bazy, &Skladniki::getInstSkladniki()); //watek ustawiajacy kalorycznosc skladnikow z bazy
				thr1.join();
				thr2.join();
				if (Przepisy::getInstPrzepisy().przepisy_z_bazy() == true) { //jesli poprawnie sciagnieto przepisy z bazy
					InitializeComponent();
					//wyswietlanie przepisow i skladnikow z bazy
					for (int i = 0; i < Przepisy::getInstPrzepisy().getPrzepisy().size(); ++i) {
						Dodaj_przycisk(Przepisy::getInstPrzepisy().getPrzepisy()[i]->getNazwa(), panel_przepisy);
						++ilosc_p;
					}
					for (int i = 0; i < Skladniki::getInstSkladniki().getWszystkie_Skladniki().size(); ++i) {
						Dodaj_przycisk(Skladniki::getInstSkladniki().getWszystkie_Skladniki()[i]->getNazwa(), panel_skladniki);
						++ilosc_s;
					}
				}
				else { //jesli nie udalo sie poprawnie sciagnac przepisow z bazy
					MessageBox::Show("Wyst¹pi³ problem zwi¹zany z pobieraniem przepisów z bazy");
				}
			}
			else { //jesli skladniki w bazie nie sa odppowiednio zapisane
				MessageBox::Show("Wyst¹pi³ problem zwi¹zany z pobieraniem skladników z bazy");
			}
			
		}
	protected:
		~Okno_glowne()
		{
			if (components)
			{
				delete components;
			}
			Skladniki::getInstSkladniki().usun_skladniki(); //zwolnienie pamieci skladnikow
			Przepisy::getInstPrzepisy().usun_przepisy(); //zwolnienie pamieci przepisow
		}
		
	private: System::Windows::Forms::Panel^ panel1; //panel menu bocznego
	private: System::Windows::Forms::Panel^ panel2; //panel prawy
	private: System::Windows::Forms::Button^ button2; //przycisk dodaj_przepis
	private: System::Windows::Forms::Button^ button1; //przycisk logowania
	private: System::Windows::Forms::Label^ label2; //napis na prawym panelu
	private: System::Windows::Forms::Button^ button3; //Menu - Skladniki
	private: System::Windows::Forms::Panel^ panel_przepisy;//panel do menu do przepisow
	private: System::Windows::Forms::Panel^ panel_skladniki; //panel do menu do skladnikow
	private: System::Windows::Forms::Button^ button7; //dodaj skladnik
	private: System::Windows::Forms::Button^ button4;//menu: przepisy
	private: System::Windows::Forms::Button^ button6; //odswiez menu skladnikow
	private: System::Windows::Forms::Button^ button5; //odswiez menu przepisow

	private: System::ComponentModel::Container^ components;// = gcnew System::ComponentModel::Container();

#pragma region Windows Form Designer generated code
		void InitializeComponent(void)
		{
			this->panel1 = (gcnew System::Windows::Forms::Panel());
			this->button6 = (gcnew System::Windows::Forms::Button());
			this->button5 = (gcnew System::Windows::Forms::Button());
			this->panel_skladniki = (gcnew System::Windows::Forms::Panel());
			this->button4 = (gcnew System::Windows::Forms::Button());
			this->panel_przepisy = (gcnew System::Windows::Forms::Panel());
			this->button3 = (gcnew System::Windows::Forms::Button());
			this->panel2 = (gcnew System::Windows::Forms::Panel());
			this->label2 = (gcnew System::Windows::Forms::Label());
			this->button1 = (gcnew System::Windows::Forms::Button());
			this->button2 = (gcnew System::Windows::Forms::Button());
			this->button7 = (gcnew System::Windows::Forms::Button());
			this->panel1->SuspendLayout();
			this->panel2->SuspendLayout();
			this->SuspendLayout();
			// 
			// panel1
			// 
			this->panel1->Anchor = static_cast<System::Windows::Forms::AnchorStyles>(((System::Windows::Forms::AnchorStyles::Top | System::Windows::Forms::AnchorStyles::Bottom)
				| System::Windows::Forms::AnchorStyles::Left));
			this->panel1->BackColor = System::Drawing::SystemColors::AppWorkspace;
			this->panel1->Controls->Add(this->button6);
			this->panel1->Controls->Add(this->button5);
			this->panel1->Controls->Add(this->panel_skladniki);
			this->panel1->Controls->Add(this->button4);
			this->panel1->Controls->Add(this->panel_przepisy);
			this->panel1->Controls->Add(this->button3);
			this->panel1->Location = System::Drawing::Point(12, 12);
			this->panel1->Name = L"panel1";
			this->panel1->Size = System::Drawing::Size(225, 466);
			this->panel1->TabIndex = 0;
			// 
			// button6
			// 
			this->button6->Location = System::Drawing::Point(200, 235);
			this->button6->Name = L"button6";
			this->button6->Size = System::Drawing::Size(25, 23);
			this->button6->TabIndex = 4;
			this->button6->Text = L"o";
			this->button6->UseVisualStyleBackColor = true;
			this->button6->Click += gcnew System::EventHandler(this, &Okno_glowne::button6_Click);
			// 
			// button5
			// 
			this->button5->Location = System::Drawing::Point(200, 0);
			this->button5->Name = L"button5";
			this->button5->Size = System::Drawing::Size(25, 23);
			this->button5->TabIndex = 1;
			this->button5->Text = L"o";
			this->button5->UseVisualStyleBackColor = true;
			this->button5->Click += gcnew System::EventHandler(this, &Okno_glowne::button5_Click);
			// 
			// panel_skladniki
			// 
			this->panel_skladniki->AutoScroll = true;
			this->panel_skladniki->Dock = System::Windows::Forms::DockStyle::Top;
			this->panel_skladniki->Location = System::Drawing::Point(0, 258);
			this->panel_skladniki->Name = L"panel_skladniki";
			this->panel_skladniki->Size = System::Drawing::Size(225, 208);
			this->panel_skladniki->TabIndex = 3;
			// 
			// button4
			// 
			this->button4->Dock = System::Windows::Forms::DockStyle::Top;
			this->button4->Location = System::Drawing::Point(0, 235);
			this->button4->Name = L"button4";
			this->button4->Size = System::Drawing::Size(225, 23);
			this->button4->TabIndex = 2;
			this->button4->Text = L"Sk³adniki";
			this->button4->UseVisualStyleBackColor = true;
			// 
			// panel_przepisy
			// 
			this->panel_przepisy->AutoScroll = true;
			this->panel_przepisy->Dock = System::Windows::Forms::DockStyle::Top;
			this->panel_przepisy->Location = System::Drawing::Point(0, 23);
			this->panel_przepisy->Name = L"panel_przepisy";
			this->panel_przepisy->Size = System::Drawing::Size(225, 212);
			this->panel_przepisy->TabIndex = 1;
			// 
			// button3
			// 
			this->button3->Dock = System::Windows::Forms::DockStyle::Top;
			this->button3->Location = System::Drawing::Point(0, 0);
			this->button3->Name = L"button3";
			this->button3->Size = System::Drawing::Size(225, 23);
			this->button3->TabIndex = 0;
			this->button3->Text = L"Przepisy";
			this->button3->UseVisualStyleBackColor = true;
			// 
			// panel2
			// 
			this->panel2->Controls->Add(this->label2);
			this->panel2->Location = System::Drawing::Point(243, 12);
			this->panel2->Name = L"panel2";
			this->panel2->Size = System::Drawing::Size(610, 465);
			this->panel2->TabIndex = 1;
			// 
			// label2
			// 
			this->label2->AutoSize = true;
			this->label2->Font = (gcnew System::Drawing::Font(L"Myriad Pro Cond", 15.75F, System::Drawing::FontStyle::Regular, System::Drawing::GraphicsUnit::Point,
				static_cast<System::Byte>(238)));
			this->label2->Location = System::Drawing::Point(208, 210);
			this->label2->Name = L"label2";
			this->label2->Size = System::Drawing::Size(220, 25);
			this->label2->TabIndex = 0;
			this->label2->Text = L"Witaj! Wybierz co chcesz zrobiæ :)\r\n";
			// 
			// button1
			// 
			this->button1->Anchor = static_cast<System::Windows::Forms::AnchorStyles>((System::Windows::Forms::AnchorStyles::Bottom | System::Windows::Forms::AnchorStyles::Left));
			this->button1->Location = System::Drawing::Point(178, 487);
			this->button1->Name = L"button1";
			this->button1->Size = System::Drawing::Size(59, 38);
			this->button1->TabIndex = 0;
			this->button1->Tag = L"";
			this->button1->Text = L"Konto";
			this->button1->UseVisualStyleBackColor = true;
			this->button1->Click += gcnew System::EventHandler(this, &Okno_glowne::button1_Click);
			// 
			// button2
			// 
			this->button2->Anchor = static_cast<System::Windows::Forms::AnchorStyles>((System::Windows::Forms::AnchorStyles::Bottom | System::Windows::Forms::AnchorStyles::Left));
			this->button2->Location = System::Drawing::Point(12, 487);
			this->button2->Name = L"button2";
			this->button2->Size = System::Drawing::Size(72, 38);
			this->button2->TabIndex = 2;
			this->button2->Text = L"Dodaj przepis";
			this->button2->UseVisualStyleBackColor = true;
			this->button2->Click += gcnew System::EventHandler(this, &Okno_glowne::button2_Click);
			// 
			// button7
			// 
			this->button7->Anchor = static_cast<System::Windows::Forms::AnchorStyles>((System::Windows::Forms::AnchorStyles::Bottom | System::Windows::Forms::AnchorStyles::Left));
			this->button7->Location = System::Drawing::Point(97, 487);
			this->button7->Name = L"button7";
			this->button7->Size = System::Drawing::Size(71, 38);
			this->button7->TabIndex = 3;
			this->button7->Text = L"Dodaj sk³adnik";
			this->button7->UseVisualStyleBackColor = true;
			this->button7->Click += gcnew System::EventHandler(this, &Okno_glowne::button7_Click);
			// 
			// Okno_glowne
			// 
			this->AutoScaleDimensions = System::Drawing::SizeF(6, 13);
			this->AutoScaleMode = System::Windows::Forms::AutoScaleMode::Font;
			this->AutoSize = true;
			this->ClientSize = System::Drawing::Size(877, 530);
			this->Controls->Add(this->button7);
			this->Controls->Add(this->button2);
			this->Controls->Add(this->button1);
			this->Controls->Add(this->panel2);
			this->Controls->Add(this->panel1);
			this->MaximumSize = System::Drawing::Size(893, 569);
			this->MinimumSize = System::Drawing::Size(893, 569);
			this->Name = L"Okno_glowne";
			this->Text = L"Przepiœnik";
			this->FormClosing += gcnew System::Windows::Forms::FormClosingEventHandler(this, &Okno_glowne::Okno_glowne_FormClosing);
			this->panel1->ResumeLayout(false);
			this->panel2->ResumeLayout(false);
			this->panel2->PerformLayout();
			this->ResumeLayout(false);

		}
#pragma endregion
		//otwieranie okienek w panelu bocznym prawym
	private:
		Form^ activeForm = nullptr;
		void openChildForm(Form^ childForm) {
			if (activeForm != nullptr)
				activeForm->Close();
			activeForm = childForm;
			childForm->TopLevel = false;
			childForm->FormBorderStyle = System::Windows::Forms::FormBorderStyle::None;
			childForm->Dock = DockStyle::Fill;
			panel2->Controls->Add(childForm);
			panel2->Tag = childForm;
			childForm->BringToFront();
			childForm->Show();
		}
		
		//przyciski w panelu lewym
		List <Button^>p_Skladniki;
		List <Button^>p_Przepisy;
		int ilosc_s = 0;
		int ilosc_p = 0;
		void Dodaj_przycisk(const std::string& nazwa, Panel^ panel) {
			Button^ b = gcnew Button(); //utworzenie przycisku
			b->Visible = true; //widocznosc
			b->Dock = DockStyle::Top; //ma byc u gory
			b->UseVisualStyleBackColor = true; //kolor tla przycisku
			if (panel == panel_skladniki) {
				b->Click += gcnew System::EventHandler(this, &Okno_glowne::przycisk_skladnik); //akcja przy przycisnieciu
				b->Text = gcnew String(nazwa.c_str());
				p_Skladniki.Add(b);
			}
			if (panel == panel_przepisy) {
				b->Click += gcnew System::EventHandler(this, &Okno_glowne::przycisk_przepis);
				b->Text = gcnew String(nazwa.c_str());
				p_Przepisy.Add(b);
			}
			panel->Controls->Add(b); //dodanie do panelu
		}
		
		//konkretny przycisk skladnika z lewego panelu
	private: System::Void przycisk_skladnik(System::Object^ sender, System::EventArgs^ e) {
		std::string tekst;
		tekst.insert(0, msclr::interop::marshal_as<std::string>(sender->ToString()), 35); 
		for (int i = 0; i < Skladniki::getInstSkladniki().getWszystkie_Skladniki().size(); ++i) {
			if (tekst == Skladniki::getInstSkladniki().getWszystkie_Skladniki()[i]->getNazwa()) { //jesli tekst przycisku jest taki sam jak nazwa skladnika
				openChildForm(gcnew Okno_Skladnika(Skladniki::getInstSkladniki().getWszystkie_Skladniki()[i],i));
				return;
			}
		}
	}
		   //konkretny przycisk przepisu z lewego panelu
	private: System::Void przycisk_przepis(System::Object^ sender, System::EventArgs^ e) {
		std::string tekst;
		tekst.insert(0, msclr::interop::marshal_as<std::string>(sender->ToString()), 35);
		for (int i = 0; i < Przepisy::getInstPrzepisy().getPrzepisy().size(); ++i) {
			if (tekst == Przepisy::getInstPrzepisy().getPrzepisy()[i]->getNazwa()) {//jesli tekst przycisku jest taki sam jak nazwa przepisu
				openChildForm(gcnew Okno_przepisu(Przepisy::getInstPrzepisy().getPrzepisy()[i]));
			}
		}
	}
		   //przycisk logowania
	private: System::Void button1_Click(System::Object^ sender, System::EventArgs^ e) {
		if (Uzytkownik::getInstUzyt().getLog() == false) {//jesli nie jest zalogowany wyswietl okno logowania
			logowanie^ okno_log = gcnew logowanie();
			okno_log->ShowDialog();
		}
		else //jesli jest zalogowany to otworz okno konta
		{
			openChildForm(gcnew Okno_konta());
		}
		
	}
		   //przycisk dodaj przepis
	private: System::Void button2_Click(System::Object^ sender, System::EventArgs^ e) {
		openChildForm(gcnew Okno_dodaj_przepis);
	}
	
		  //odswierz przepisy
	private: System::Void button5_Click(System::Object^ sender, System::EventArgs^ e) {
		if (ilosc_p != 0) { //jesli sa juz jakies przyciski to ustaw pamiec jako zwolniona
			for (int i = 0; i < ilosc_p; ++i) {
				delete p_Przepisy[i];
				p_Przepisy[i]->Visible = false;
			}
			ilosc_p = 0;
			p_Przepisy.Clear();
		}
		for (int i = 0; i < Przepisy::getInstPrzepisy().getPrzepisy().size(); ++i) { //wyswietl przyciski przepisow
			Dodaj_przycisk(Przepisy::getInstPrzepisy().getPrzepisy()[i]->getNazwa(), panel_przepisy);
			++ilosc_p;
		}
	}
	   //odswierz skladniki
	private: System::Void button6_Click(System::Object^ sender, System::EventArgs^ e) {
		if (ilosc_s != 0) { //jesli sa juz jakies przyciski to ustaw pamiec jako zwolniona
			for (int i = 0; i < ilosc_s; ++i) {
				delete p_Skladniki[i];
				p_Skladniki[i]->Visible = false;
				
			}
			ilosc_s = 0;
			p_Skladniki.Clear();
		}
		for (int i = 0; i < Skladniki::getInstSkladniki().getWszystkie_Skladniki().size(); ++i) { //wyswietl przyciski skladnikow
			Dodaj_przycisk(Skladniki::getInstSkladniki().getWszystkie_Skladniki()[i]->getNazwa(), panel_skladniki);
			++ilosc_s;
		}
	}
		   //przycisk dodaj skladnik
	private: System::Void button7_Click(System::Object^ sender, System::EventArgs^ e) {
		openChildForm(gcnew Okno_dodaj_skladnik);
	}
		  //zamkniecie okna glownego
	private: System::Void Okno_glowne_FormClosing(System::Object^ sender, System::Windows::Forms::FormClosingEventArgs^ e) {
		if (Skladniki::getInstSkladniki().getZmiana_s() == true || Przepisy::getInstPrzepisy().getZmiana_p() == true || Uzytkownik::getInstUzyt().getZmiana_u()==true) { //jesli dokonano jakiejs zmiany w skladnikach, przepisach lub ulubionych uzytkownika
			auto odp = MessageBox::Show("Czy przed zamknieciem chcesz zapisaæ wpowadzone zmiany do bazy?", "Zapis?", MessageBoxButtons::YesNoCancel);
			//jesli chce zapisac
			if (odp == System::Windows::Forms::DialogResult::Yes) {
				std::thread th1(&Przepisy::przepisy_do_bazy,&Przepisy::getInstPrzepisy());
				std::thread th2(&Skladniki::skladniki_do_bazy,&Skladniki::getInstSkladniki());
				std::thread th3(&Uzytkownik::ulubione_do_bazy, &Uzytkownik::getInstUzyt());
				th1.join();
				th2.join();
				th3.join();
			}
			//jesli nie
			else if (odp == System::Windows::Forms::DialogResult::No) {
				Application::ExitThread();
			}
			else {
				e->Cancel = true;
			}
		}
		else {//nie zostaly wprowadzone zadne zmiany, ktore moznaby wpisac do bazy
			if (MessageBox::Show("Czy na pewno chcesz zamkn¹æ okno?", "Zamkn¹æ?", MessageBoxButtons::YesNo) == System::Windows::Forms::DialogResult::Yes)
				Application::ExitThread();
			else
				e->Cancel = true;
		}
	}
};
}
