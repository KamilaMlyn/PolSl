#pragma once
#include "Przepis.h"
#include "Okno_listy_zakupow.h"
#include <msclr\marshal_cppstd.h>//zamiana String na string
namespace Project3 {

	using namespace System;
	using namespace System::ComponentModel;
	using namespace System::Collections;
	using namespace System::Windows::Forms;
	using namespace System::Data;
	using namespace System::Drawing;

	public ref class Okno_przepisu : public System::Windows::Forms::Form
	{
		Przepis* przepis;
	public:
		Okno_przepisu(Przepis* przepis)
		{
			this->przepis = przepis;
			InitializeComponent();

			if (Uzytkownik::getInstUzyt().czy_jest_ulubiony(przepis) == true) { //jesli dany przepis znajduje sie w liscie ulubionych zalogowanego uzytkownika
				button2->Visible = false; //przycisk "dodaj do ulubionych"
				button12->Visible = true; //przycisk "usun z ulubionych"
			}
			else {
				button2->Visible = true;
				button12->Visible = false;
			}
			String^ jednostka;
			label1->Text = gcnew String(przepis->getNazwa().c_str()); //wyswietlanie nazwy przepisu
			if (Kalkulator::getInstKalk().getWybor() == true) { //wyswietlanie kalorycznosci przepisu z odpowiednia jednostka
				jednostka = " kcal";
			}
			else
				jednostka = " kJ";
			label2->Text = Kalkulator::getInstKalk().oblicz(przepis) + jednostka;
			for (int i = 0; i < przepis->getSkladniki().size(); ++i) { //wyswietlanie skladnikow
				ListViewItem^ element = gcnew ListViewItem(gcnew String(przepis->getSkladniki()[i].first->getNazwa().c_str()));
				element->SubItems->Add(gcnew String("" + przepis->getSkladniki()[i].second)); //nie zadzialalo std::to_string()
				listView1->Items->Add(element);
			}

			if (MessageBox::Show("Czy chcesz zobaczyæ od razu ca³y przepis?\nTak, chcê wyœwietliæ od razu wszystkie kroki przygotowania.\nNie, wolê pokazywanie ich krok po kroku.", "Sposób wyœwietlania", MessageBoxButtons::YesNo) == System::Windows::Forms::DialogResult::Yes) {
				for (int i = 0; i < przepis->getWykonanie().size(); ++i) { //wszystkie na raz
					ListViewItem^ element = gcnew ListViewItem(gcnew System::String(przepis->getWykonanie()[i].c_str()));
					listView2->Items->Add(element);
				}
				ListViewItem^ element = gcnew ListViewItem();
				element->Text = "Smacznego!";
				listView2->Items->Add(element);
				button1->Visible = true; //"edytuj przepis"
			}
			else { //co krok
				ListViewItem^ element = gcnew ListViewItem(gcnew System::String(przepis->getWykonanie()[0].c_str()));
				listView2->Items->Add(element);
				button1->Visible = false;
				button2->Visible = false;
				button3->Visible = false;
				button13->Visible = false;
				button12->Visible = false;
				button5->Visible = true; //przycisk "nastepny"
			}
		}


	protected:
		~Okno_przepisu()
		{
			if (components)
			{
				delete components;
			}
		}
	private: System::Windows::Forms::Label^ label1;
	private: System::Windows::Forms::ListView^ listView1;
	private: System::Windows::Forms::ColumnHeader^ columnHeader1;
	private: System::Windows::Forms::ColumnHeader^ columnHeader2;
	private: System::Windows::Forms::ListView^ listView2;
	private: System::Windows::Forms::ColumnHeader^ columnHeader3;
	private: System::Windows::Forms::Button^ button1;
	private: System::Windows::Forms::Button^ button2;
	private: System::Windows::Forms::Button^ button3;
	private: System::Windows::Forms::Button^ button4;
	private: System::Windows::Forms::Button^ button5; //Nastepny
	private: System::Windows::Forms::Button^ button6;
	private: System::Windows::Forms::Button^ button7;
	private: System::Windows::Forms::Button^ button8;
	private: System::Windows::Forms::TextBox^ textBox1;
	private: System::Windows::Forms::Button^ button9;
	private: System::Windows::Forms::Button^ button10;
	private: System::Windows::Forms::Button^ button11;
	private: System::Windows::Forms::Label^ label2;
	private: System::Windows::Forms::TextBox^ textBox2;
	private: System::Windows::Forms::TextBox^ textBox3;
	private: System::Windows::Forms::Button^ button12;
	private: System::Windows::Forms::Button^ button13;


	private: System::ComponentModel::Container^ components;

#pragma region Windows Form Designer generated code
		/// <summary>
		/// Metoda wymagana do obs³ugi projektanta — nie nale¿y modyfikowaæ
		/// jej zawartoœci w edytorze kodu.
		/// </summary>
		void InitializeComponent(void)
		{
			this->label1 = (gcnew System::Windows::Forms::Label());
			this->listView1 = (gcnew System::Windows::Forms::ListView());
			this->columnHeader1 = (gcnew System::Windows::Forms::ColumnHeader());
			this->columnHeader2 = (gcnew System::Windows::Forms::ColumnHeader());
			this->listView2 = (gcnew System::Windows::Forms::ListView());
			this->columnHeader3 = (gcnew System::Windows::Forms::ColumnHeader());
			this->button1 = (gcnew System::Windows::Forms::Button());
			this->button2 = (gcnew System::Windows::Forms::Button());
			this->button3 = (gcnew System::Windows::Forms::Button());
			this->button4 = (gcnew System::Windows::Forms::Button());
			this->button5 = (gcnew System::Windows::Forms::Button());
			this->button6 = (gcnew System::Windows::Forms::Button());
			this->button7 = (gcnew System::Windows::Forms::Button());
			this->button8 = (gcnew System::Windows::Forms::Button());
			this->textBox1 = (gcnew System::Windows::Forms::TextBox());
			this->button9 = (gcnew System::Windows::Forms::Button());
			this->button10 = (gcnew System::Windows::Forms::Button());
			this->button11 = (gcnew System::Windows::Forms::Button());
			this->button12 = (gcnew System::Windows::Forms::Button());
			this->button13 = (gcnew System::Windows::Forms::Button());
			this->label2 = (gcnew System::Windows::Forms::Label());
			this->textBox2 = (gcnew System::Windows::Forms::TextBox());
			this->textBox3 = (gcnew System::Windows::Forms::TextBox());
			this->SuspendLayout();
			// 
			// label1
			// 
			this->label1->AutoSize = true;
			this->label1->ForeColor = System::Drawing::SystemColors::ControlText;
			this->label1->Location = System::Drawing::Point(278, 41);
			this->label1->Name = L"label1";
			this->label1->Size = System::Drawing::Size(40, 13);
			this->label1->TabIndex = 0;
			this->label1->Text = L"Nazwa";
			// 
			// listView1
			// 
			this->listView1->Columns->AddRange(gcnew cli::array< System::Windows::Forms::ColumnHeader^  >(2) { this->columnHeader1, this->columnHeader2 });
			this->listView1->HideSelection = false;
			this->listView1->Location = System::Drawing::Point(35, 57);
			this->listView1->Name = L"listView1";
			this->listView1->Size = System::Drawing::Size(521, 127);
			this->listView1->TabIndex = 1;
			this->listView1->UseCompatibleStateImageBehavior = false;
			this->listView1->View = System::Windows::Forms::View::Details;
			// 
			// columnHeader1
			// 
			this->columnHeader1->Text = L"Produkty";
			this->columnHeader1->Width = 391;
			// 
			// columnHeader2
			// 
			this->columnHeader2->Text = L"Iloœæ (g)";
			this->columnHeader2->Width = 93;
			// 
			// listView2
			// 
			this->listView2->Columns->AddRange(gcnew cli::array< System::Windows::Forms::ColumnHeader^  >(1) { this->columnHeader3 });
			this->listView2->HideSelection = false;
			this->listView2->Location = System::Drawing::Point(35, 214);
			this->listView2->Name = L"listView2";
			this->listView2->Size = System::Drawing::Size(521, 167);
			this->listView2->TabIndex = 2;
			this->listView2->UseCompatibleStateImageBehavior = false;
			this->listView2->View = System::Windows::Forms::View::Details;
			// 
			// columnHeader3
			// 
			this->columnHeader3->Text = L"Przygotowanie:";
			this->columnHeader3->Width = 516;
			// 
			// button1
			// 
			this->button1->Location = System::Drawing::Point(325, 394);
			this->button1->Name = L"button1";
			this->button1->Size = System::Drawing::Size(85, 60);
			this->button1->TabIndex = 3;
			this->button1->Text = L"edytuj przepis";
			this->button1->UseVisualStyleBackColor = true;
			this->button1->Click += gcnew System::EventHandler(this, &Okno_przepisu::button1_Click);
			// 
			// button2
			// 
			this->button2->Location = System::Drawing::Point(80, 394);
			this->button2->Name = L"button2";
			this->button2->Size = System::Drawing::Size(85, 61);
			this->button2->TabIndex = 4;
			this->button2->Text = L"dodaj do ulubionych";
			this->button2->UseVisualStyleBackColor = true;
			this->button2->Click += gcnew System::EventHandler(this, &Okno_przepisu::button2_Click);
			// 
			// button3
			// 
			this->button3->Location = System::Drawing::Point(200, 394);
			this->button3->Name = L"button3";
			this->button3->Size = System::Drawing::Size(85, 61);
			this->button3->TabIndex = 5;
			this->button3->Text = L"generuj listê zakupów";
			this->button3->UseVisualStyleBackColor = true;
			this->button3->Click += gcnew System::EventHandler(this, &Okno_przepisu::button3_Click);
			// 
			// button4
			// 
			this->button4->Location = System::Drawing::Point(12, 12);
			this->button4->Name = L"button4";
			this->button4->Size = System::Drawing::Size(28, 23);
			this->button4->TabIndex = 6;
			this->button4->Text = L"X";
			this->button4->UseVisualStyleBackColor = true;
			this->button4->Click += gcnew System::EventHandler(this, &Okno_przepisu::button4_Click);
			// 
			// button5
			// 
			this->button5->Location = System::Drawing::Point(481, 394);
			this->button5->Name = L"button5";
			this->button5->Size = System::Drawing::Size(75, 23);
			this->button5->TabIndex = 7;
			this->button5->Text = L"Nastêpny";
			this->button5->UseVisualStyleBackColor = true;
			this->button5->Visible = false;
			this->button5->Click += gcnew System::EventHandler(this, &Okno_przepisu::button5_Click);
			// 
			// button6
			// 
			this->button6->Location = System::Drawing::Point(461, 388);
			this->button6->Name = L"button6";
			this->button6->Size = System::Drawing::Size(107, 23);
			this->button6->TabIndex = 8;
			this->button6->Text = L"usuñ zaznaczone";
			this->button6->UseVisualStyleBackColor = true;
			this->button6->Visible = false;
			this->button6->Click += gcnew System::EventHandler(this, &Okno_przepisu::button6_Click);
			// 
			// button7
			// 
			this->button7->Location = System::Drawing::Point(461, 409);
			this->button7->Name = L"button7";
			this->button7->Size = System::Drawing::Size(107, 23);
			this->button7->TabIndex = 9;
			this->button7->Text = L"akceptuj zmiany";
			this->button7->UseVisualStyleBackColor = true;
			this->button7->Visible = false;
			this->button7->Click += gcnew System::EventHandler(this, &Okno_przepisu::button7_Click);
			// 
			// button8
			// 
			this->button8->Location = System::Drawing::Point(461, 429);
			this->button8->Name = L"button8";
			this->button8->Size = System::Drawing::Size(107, 23);
			this->button8->TabIndex = 10;
			this->button8->Text = L"anuluj zmiany";
			this->button8->UseVisualStyleBackColor = true;
			this->button8->Visible = false;
			this->button8->Click += gcnew System::EventHandler(this, &Okno_przepisu::button8_Click);
			// 
			// textBox1
			// 
			this->textBox1->Location = System::Drawing::Point(47, 390);
			this->textBox1->Name = L"textBox1";
			this->textBox1->Size = System::Drawing::Size(363, 20);
			this->textBox1->TabIndex = 11;
			this->textBox1->Visible = false;
			// 
			// button9
			// 
			this->button9->Location = System::Drawing::Point(80, 421);
			this->button9->Name = L"button9";
			this->button9->Size = System::Drawing::Size(94, 23);
			this->button9->TabIndex = 12;
			this->button9->Text = L"zmieñ nazwê";
			this->button9->UseVisualStyleBackColor = true;
			this->button9->Visible = false;
			this->button9->Click += gcnew System::EventHandler(this, &Okno_przepisu::button9_Click);
			// 
			// button10
			// 
			this->button10->Location = System::Drawing::Point(493, 188);
			this->button10->Name = L"button10";
			this->button10->Size = System::Drawing::Size(63, 23);
			this->button10->TabIndex = 13;
			this->button10->Text = L"dodaj sk³adnik";
			this->button10->UseVisualStyleBackColor = true;
			this->button10->Visible = false;
			this->button10->Click += gcnew System::EventHandler(this, &Okno_przepisu::button10_Click);
			// 
			// button11
			// 
			this->button11->Location = System::Drawing::Point(210, 421);
			this->button11->Name = L"button11";
			this->button11->Size = System::Drawing::Size(145, 23);
			this->button11->TabIndex = 14;
			this->button11->Text = L"dodaj krok przygotowania";
			this->button11->UseVisualStyleBackColor = true;
			this->button11->Visible = false;
			this->button11->Click += gcnew System::EventHandler(this, &Okno_przepisu::button11_Click);
			// 
			// button12
			// 
			this->button12->Location = System::Drawing::Point(80, 394);
			this->button12->Name = L"button12";
			this->button12->Size = System::Drawing::Size(85, 61);
			this->button12->TabIndex = 17;
			this->button12->Text = L"usuñ z ulubionych";
			this->button12->UseVisualStyleBackColor = true;
			this->button12->Click += gcnew System::EventHandler(this, &Okno_przepisu::button12_Click);
			// 
			// button13
			// 
			this->button13->Location = System::Drawing::Point(440, 394);
			this->button13->Name = L"button13";
			this->button13->Size = System::Drawing::Size(85, 58);
			this->button13->TabIndex = 17;
			this->button13->Text = L"usuñ przepis";
			this->button13->UseVisualStyleBackColor = true;
			this->button13->Click += gcnew System::EventHandler(this, &Okno_przepisu::button13_Click);
			// 
			// label2
			// 
			this->label2->AutoSize = true;
			this->label2->Location = System::Drawing::Point(521, 41);
			this->label2->Name = L"label2";
			this->label2->Size = System::Drawing::Size(35, 13);
			this->label2->TabIndex = 15;
			this->label2->Text = L"label2";
			// 
			// textBox2
			// 
			this->textBox2->Location = System::Drawing::Point(47, 190);
			this->textBox2->Name = L"textBox2";
			this->textBox2->Size = System::Drawing::Size(363, 20);
			this->textBox2->TabIndex = 16;
			this->textBox2->Visible = false;
			// 
			// textBox3
			// 
			this->textBox3->Location = System::Drawing::Point(416, 190);
			this->textBox3->Name = L"textBox3";
			this->textBox3->Size = System::Drawing::Size(61, 20);
			this->textBox3->TabIndex = 17;
			this->textBox3->Visible = false;
			// 
			// Okno_przepisu
			// 
			this->AutoScaleDimensions = System::Drawing::SizeF(6, 13);
			this->AutoScaleMode = System::Windows::Forms::AutoScaleMode::Font;
			this->ClientSize = System::Drawing::Size(610, 465);
			this->Controls->Add(this->textBox3);
			this->Controls->Add(this->textBox2);
			this->Controls->Add(this->label2);
			this->Controls->Add(this->button11);
			this->Controls->Add(this->button10);
			this->Controls->Add(this->button9);
			this->Controls->Add(this->textBox1);
			this->Controls->Add(this->button8);
			this->Controls->Add(this->button7);
			this->Controls->Add(this->button6);
			this->Controls->Add(this->button5);
			this->Controls->Add(this->button4);
			this->Controls->Add(this->button3);
			this->Controls->Add(this->button2);
			this->Controls->Add(this->button1);
			this->Controls->Add(this->button12);
			this->Controls->Add(this->listView2);
			this->Controls->Add(this->listView1);
			this->Controls->Add(this->label1);
			this->Controls->Add(this->button13);
			this->FormBorderStyle = System::Windows::Forms::FormBorderStyle::None;
			this->Name = L"Okno_przepisu";
			this->Text = L"Okno_przepisu";
			this->ResumeLayout(false);
			this->PerformLayout();

		}
#pragma endregion

		//przycisk zamknieicia
	private: System::Void button4_Click(System::Object^ sender, System::EventArgs^ e) {
		this->Close();
	}

		   //generuj liste zakupow
	private: System::Void button3_Click(System::Object^ sender, System::EventArgs^ e) {
		Okno_listy_zakupow^ okno_l_z = gcnew Okno_listy_zakupow(przepis);
		okno_l_z->ShowDialog();
	}

		   //przycisk dodaj do ulubionych
	private: System::Void button2_Click(System::Object^ sender, System::EventArgs^ e) {
		if (Uzytkownik::getInstUzyt().getLog() == false) {
			MessageBox::Show("Aby dodaæ przepis do uluionych musisz siê zalogowaæ");
			return;
		}
		else {
			Uzytkownik::getInstUzyt().dodaj_ulubione(przepis);
			button2->Visible = false;
			button12->Visible = true;
			Uzytkownik::getInstUzyt().setZmiana_u();
			MessageBox::Show("Dodano do ulubionych");
		}
	}

		   //przycisk usuñ z ulubionych
	private: System::Void button12_Click(System::Object^ sender, System::EventArgs^ e) {
		Uzytkownik::getInstUzyt().usun_z_ulubionych(przepis);
		button2->Visible = true;
		button12->Visible = false;
		Uzytkownik::getInstUzyt().setZmiana_u();
		MessageBox::Show("Usuniêto z ulubionych");
	}

		   //przycisk Nastêpny
		   int licznik = 1;
	private: System::Void button5_Click(System::Object^ sender, System::EventArgs^ e) {
		if (licznik < przepis->getWykonanie().size()) {
			ListViewItem^ element = gcnew ListViewItem(gcnew System::String(przepis->getWykonanie()[licznik].c_str()));
			listView2->Items->Add(element);
			++licznik;
		}
		else {
			ListViewItem^ element = gcnew ListViewItem();
			element->Text = "Smacznego!";
			listView2->Items->Add(element);
			button5->Visible = false;
			button1->Visible = true;
			button3->Visible = true;
			button13->Visible = true;
			if (Uzytkownik::getInstUzyt().czy_jest_ulubiony(przepis) == true)
				button12->Visible = true;
			else
				button2->Visible = true;
		}
	}

		   //przycisk edytuj przepis
	private: System::Void button1_Click(System::Object^ sender, System::EventArgs^ e) {
		button1->Visible = false;
		button2->Visible = false;
		button3->Visible = false;
		button13->Visible = false;
		button12->Visible = false;
		button6->Visible = true;
		button7->Visible = true;
		button8->Visible = true;
		button9->Visible = true;
		button10->Visible = true;
		button11->Visible = true;
		textBox1->Visible = true;
		textBox2->Visible = true;
		textBox3->Visible = true;
	}

		   bool skasowane_s = false;
		   bool skasowane_k = false;
		   //usun zaznaczone
	private: System::Void button6_Click(System::Object^ sender, System::EventArgs^ e) {
		if (listView1->SelectedItems->Count > 0) {
			listView1->Items->Remove(listView1->SelectedItems[0]);
			skasowane_s = true;
		}
		if (listView2->SelectedItems->Count > 0) {
			listView2->Items->Remove(listView2->SelectedItems[0]);
			skasowane_k = true;
		}
	}

		   int nowe_s = 0;
		   int nowe_k = 0;
		   bool nowa_n = false;
		   //anuluj zmiany
	private: System::Void button8_Click(System::Object^ sender, System::EventArgs^ e) {
		nowe_s = 0;
		nowe_k = 0;
		nowa_n = false;
		MessageBox::Show("Anulowano zmiany. Wybierz ponownie ten przepis aby zobaczyæ aktualny stan");
		button1->Visible = true;
		button13->Visible = true;
		button2->Visible = true;
		button3->Visible = true;
		button6->Visible = false;
		button7->Visible = false;
		button8->Visible = false;
		button9->Visible = false;
		button10->Visible = false;
		button11->Visible = false;
		textBox1->Visible = false;
		textBox2->Visible = false;
		textBox3->Visible = false;
	}

		   //zmiana nazwy
	private: System::Void button9_Click(System::Object^ sender, System::EventArgs^ e) {
		if (textBox1->Text != "") {
			nowa_n = true;
			label1->Text = textBox1->Text;
		}
	}

		   //dodaj skladnik
		   int ilosc = 0;
	private: System::Void button10_Click(System::Object^ sender, System::EventArgs^ e) {
		if (textBox2->Text != "" && textBox3->Text != "") {
			std::string text = msclr::interop::marshal_as<std::string>(textBox3->Text);
			for (int i : text) {
				if (!std::isdigit(i)) { //sprawdzanie czy uzytkownik podal liczbe
					textBox2->Text = "";
					textBox3->Text = "";
					MessageBox::Show("Podana wartoœæ iloœci produktu nie jest liczb¹.\n");
					return;
				}
			}
			ListViewItem^ element = gcnew ListViewItem();
			element->Text = textBox2->Text;
			element->SubItems->Add(textBox3->Text);
			listView1->Items->Add(element);
			++nowe_s;
			textBox2->Text = "";
			textBox3->Text = "";
		}
	}

		   //dodaj krok przygotowania
	private: System::Void button11_Click(System::Object^ sender, System::EventArgs^ e) {
		if (textBox1->Text != "") {
			ListViewItem^ element = gcnew ListViewItem();
			element->Text = textBox1->Text;
			listView2->Items->Add(element);
			++nowe_k;
		}
	}

		   //akceptuj zmiany
	private: System::Void button7_Click(System::Object^ sender, System::EventArgs^ e) {
		if (nowa_n == true) { //aktualizacja nazwy
			przepis->setNazwa(msclr::interop::marshal_as<std::string>(label1->Text));
			Przepisy::getInstPrzepisy().setZmiana_p();
		}
		//aktualizacja wykonania
		if (skasowane_k == true) { //jesli bylo cos skasowane
			przepis->wyczysc_wykonanie();
			for (int i = 0; i < listView2->Items->Count - 1; ++i) {
				przepis->dodaj_Wykonanie(msclr::interop::marshal_as<std::string>(listView2->Items[i]->Text));
			}
			Przepisy::getInstPrzepisy().setZmiana_p();
		}
		else if (nowe_k > 0) { //jesli nie bylo nic skasowane ale bylo cos dodane
			for (int i = listView2->Items->Count - nowe_k; i < listView2->Items->Count; ++i) {
				przepis->dodaj_Wykonanie(msclr::interop::marshal_as<std::string>(listView2->Items[i]->Text));
			}
			Przepisy::getInstPrzepisy().setZmiana_p();
		}
		//aktualizacja skladnikow
		String^ jednostka;
		if (Kalkulator::getInstKalk().getWybor() == true)
			jednostka = " kcal";
		else
			jednostka = " kJ";
		if (skasowane_s == true) {//jesli skasowano jakis skladnik
			przepis->wyczysc_skladniki();
			for (int i = 0; i < listView1->Items->Count; ++i) {
				int index = Skladniki::getInstSkladniki().czy_istnieje_skladnik(msclr::interop::marshal_as<std::string>(listView1->Items[i]->Text));
				if (index == -1) {
					MessageBox::Show("Nie ma w bazie skladnika: " + listView1->Items[i]->Text + ".\nDodaj go do bazy aby mo¿na by³o go dodaæ do przepisu.");
					return;
				}
				else {
					Przepisy::getInstPrzepisy().setZmiana_p();
					przepis->dodaj_skladnik(Skladniki::getInstSkladniki().getWszystkie_Skladniki()[index], std::stoi(msclr::interop::marshal_as<std::string>(listView1->Items[i]->SubItems[1]->Text)));
				}
			}
			skasowane_s = false;
			label2->Text = Kalkulator::getInstKalk().oblicz(przepis) + jednostka;
		}
		else if (nowe_s > 0) {
			for (int i = listView1->Items->Count - nowe_s; i < listView1->Items->Count; ++i) {
				int index = Skladniki::getInstSkladniki().czy_istnieje_skladnik(msclr::interop::marshal_as<std::string>(listView1->Items[i]->Text));
				if (index == -1) {
					MessageBox::Show("Nie ma w bazie skladnika: " + listView1->Items[i] + ".\nDodaj go do bazy aby mo¿na by³o go dodaæ do przepisu.");
				}
				else {
					Przepisy::getInstPrzepisy().setZmiana_p();
					przepis->dodaj_skladnik(Skladniki::getInstSkladniki().getWszystkie_Skladniki()[index], std::stoi(msclr::interop::marshal_as<std::string>(listView1->Items[i]->SubItems[1]->Text)));
				}
			}
			nowe_s = 0;
			label2->Text = Kalkulator::getInstKalk().oblicz(przepis) + jednostka;
		}
		MessageBox::Show("Dokonano zmiany.");
		button1->Visible = true;
		button13->Visible = true;
		button2->Visible = true;
		button3->Visible = true;
		button6->Visible = false;
		button7->Visible = false;
		button8->Visible = false;
		button9->Visible = false;
		button10->Visible = false;
		button11->Visible = false;
		textBox1->Visible = false;
		textBox2->Visible = false;
		textBox3->Visible = false;
	}

		   //usun przepis
	private: System::Void button13_Click(System::Object^ sender, System::EventArgs^ e) {
		if (MessageBox::Show("Czy na pewno chcesz usun¹æ ten przepis?", "Usun¹æ przepis?", MessageBoxButtons::YesNo) == System::Windows::Forms::DialogResult::Yes) {
			Przepisy::getInstPrzepisy().usun_przepis(przepis);
			MessageBox::Show("Usuniêto przepis.\nAby zobaczyæ zmiany odœwierz panel przepisów");
			Przepisy::getInstPrzepisy().setZmiana_p();
		}
	}
	};
}