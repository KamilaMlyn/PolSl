#pragma once
#include <string>
#include <msclr\marshal_cppstd.h>
#include"Przepis.h"

namespace Project3 {

	using namespace System;
	using namespace System::ComponentModel;
	using namespace System::Collections;
	using namespace System::Windows::Forms;
	using namespace System::Data;
	using namespace System::Drawing;

	public ref class Okno_dodaj_przepis : public System::Windows::Forms::Form
	{
	public:
		Okno_dodaj_przepis(void)
		{
			InitializeComponent();
		}

	protected:
		~Okno_dodaj_przepis()
		{
			if (components)
			{
				delete components;
			}
		}
	private: System::Windows::Forms::Button^ button1; //zamkniecie okienka
	private: System::Windows::Forms::TextBox^ textBox1; //nazwa przepisu
	private: System::Windows::Forms::ListView^ listView1; //lista skladnikow
	private: System::Windows::Forms::ColumnHeader^ columnHeader1; //kolumna skladnik
	private: System::Windows::Forms::ColumnHeader^ columnHeader2; //kolumna ilosc
	private: System::Windows::Forms::TextBox^ textBox2;
	private: System::Windows::Forms::TextBox^ textBox3;
	private: System::Windows::Forms::Button^ button2; //dodaj
	private: System::Windows::Forms::ListView^ listView2; //lista krokow przygotowania
	private: System::Windows::Forms::ColumnHeader^ columnHeader3; //kolumna kroki przygotowania
	private: System::Windows::Forms::TextBox^ textBox4;
	private: System::Windows::Forms::Button^ button3; //dodaj
	private: System::Windows::Forms::Button^ button4; //usun zaznaczone
	private: System::Windows::Forms::Button^ button5; //uzun zaznaczone
	private: System::Windows::Forms::Label^ label1; // tekst "lub wpisz œcie¿kê do pliku z przepisem (.txt):"
	private: System::Windows::Forms::TextBox^ textBox5;
	private: System::Windows::Forms::Button^ button6; //dodaj przepis
	private: System::Windows::Forms::Button^ button7;

	private: System::ComponentModel::Container^ components;

#pragma region Windows Form Designer generated code
		   void InitializeComponent(void)
		   {
			   this->button1 = (gcnew System::Windows::Forms::Button());
			   this->textBox1 = (gcnew System::Windows::Forms::TextBox());
			   this->listView1 = (gcnew System::Windows::Forms::ListView());
			   this->columnHeader1 = (gcnew System::Windows::Forms::ColumnHeader());
			   this->columnHeader2 = (gcnew System::Windows::Forms::ColumnHeader());
			   this->textBox2 = (gcnew System::Windows::Forms::TextBox());
			   this->textBox3 = (gcnew System::Windows::Forms::TextBox());
			   this->button2 = (gcnew System::Windows::Forms::Button());
			   this->listView2 = (gcnew System::Windows::Forms::ListView());
			   this->columnHeader3 = (gcnew System::Windows::Forms::ColumnHeader());
			   this->textBox4 = (gcnew System::Windows::Forms::TextBox());
			   this->button3 = (gcnew System::Windows::Forms::Button());
			   this->button4 = (gcnew System::Windows::Forms::Button());
			   this->button5 = (gcnew System::Windows::Forms::Button());
			   this->label1 = (gcnew System::Windows::Forms::Label());
			   this->textBox5 = (gcnew System::Windows::Forms::TextBox());
			   this->button6 = (gcnew System::Windows::Forms::Button());
			   this->button7 = (gcnew System::Windows::Forms::Button());
			   this->SuspendLayout();
			   // 
			   // button1
			   // 
			   this->button1->Location = System::Drawing::Point(12, 12);
			   this->button1->Name = L"button1";
			   this->button1->Size = System::Drawing::Size(22, 23);
			   this->button1->TabIndex = 0;
			   this->button1->Text = L"X";
			   this->button1->UseVisualStyleBackColor = true;
			   this->button1->Click += gcnew System::EventHandler(this, &Okno_dodaj_przepis::button1_Click);
			   // 
			   // textBox1
			   // 
			   this->textBox1->Location = System::Drawing::Point(255, 18);
			   this->textBox1->Name = L"textBox1";
			   this->textBox1->Size = System::Drawing::Size(100, 20);
			   this->textBox1->TabIndex = 1;
			   this->textBox1->Text = L"Nazwa";
			   // 
			   // listView1
			   // 
			   this->listView1->Columns->AddRange(gcnew cli::array< System::Windows::Forms::ColumnHeader^  >(2) { this->columnHeader1, this->columnHeader2 });
			   this->listView1->HideSelection = false;
			   this->listView1->Location = System::Drawing::Point(12, 59);
			   this->listView1->Name = L"listView1";
			   this->listView1->Size = System::Drawing::Size(566, 97);
			   this->listView1->TabIndex = 2;
			   this->listView1->UseCompatibleStateImageBehavior = false;
			   this->listView1->View = System::Windows::Forms::View::Details;
			   // 
			   // columnHeader1
			   // 
			   this->columnHeader1->Text = L"Sk³adnik";
			   this->columnHeader1->Width = 447;
			   // 
			   // columnHeader2
			   // 
			   this->columnHeader2->Text = L"iloœæ";
			   // 
			   // textBox2
			   // 
			   this->textBox2->Location = System::Drawing::Point(12, 162);
			   this->textBox2->Name = L"textBox2";
			   this->textBox2->Size = System::Drawing::Size(258, 20);
			   this->textBox2->TabIndex = 3;
			   // 
			   // textBox3
			   // 
			   this->textBox3->Location = System::Drawing::Point(276, 162);
			   this->textBox3->Name = L"textBox3";
			   this->textBox3->Size = System::Drawing::Size(100, 20);
			   this->textBox3->TabIndex = 4;
			   // 
			   // button2
			   // 
			   this->button2->Location = System::Drawing::Point(382, 160);
			   this->button2->Name = L"button2";
			   this->button2->Size = System::Drawing::Size(75, 23);
			   this->button2->TabIndex = 5;
			   this->button2->Text = L"dodaj";
			   this->button2->UseVisualStyleBackColor = true;
			   this->button2->Click += gcnew System::EventHandler(this, &Okno_dodaj_przepis::button2_Click);
			   // 
			   // listView2
			   // 
			   this->listView2->Columns->AddRange(gcnew cli::array< System::Windows::Forms::ColumnHeader^  >(1) { this->columnHeader3 });
			   this->listView2->HideSelection = false;
			   this->listView2->Location = System::Drawing::Point(12, 201);
			   this->listView2->Name = L"listView2";
			   this->listView2->Size = System::Drawing::Size(566, 97);
			   this->listView2->TabIndex = 6;
			   this->listView2->UseCompatibleStateImageBehavior = false;
			   this->listView2->View = System::Windows::Forms::View::Details;
			   // 
			   // columnHeader3
			   // 
			   this->columnHeader3->Text = L"Kroki przygotowania";
			   this->columnHeader3->Width = 560;
			   // 
			   // textBox4
			   // 
			   this->textBox4->Location = System::Drawing::Point(12, 304);
			   this->textBox4->Name = L"textBox4";
			   this->textBox4->Size = System::Drawing::Size(364, 20);
			   this->textBox4->TabIndex = 7;
			   // 
			   // button3
			   // 
			   this->button3->Location = System::Drawing::Point(382, 301);
			   this->button3->Name = L"button3";
			   this->button3->Size = System::Drawing::Size(75, 23);
			   this->button3->TabIndex = 8;
			   this->button3->Text = L"dodaj";
			   this->button3->UseVisualStyleBackColor = true;
			   this->button3->Click += gcnew System::EventHandler(this, &Okno_dodaj_przepis::button3_Click);
			   // 
			   // button4
			   // 
			   this->button4->Location = System::Drawing::Point(479, 160);
			   this->button4->Name = L"button4";
			   this->button4->Size = System::Drawing::Size(99, 23);
			   this->button4->TabIndex = 9;
			   this->button4->Text = L"usuñ zaznaczone";
			   this->button4->UseVisualStyleBackColor = true;
			   this->button4->Click += gcnew System::EventHandler(this, &Okno_dodaj_przepis::button4_Click);
			   // 
			   // button5
			   // 
			   this->button5->Location = System::Drawing::Point(479, 304);
			   this->button5->Name = L"button5";
			   this->button5->Size = System::Drawing::Size(99, 23);
			   this->button5->TabIndex = 10;
			   this->button5->Text = L"usuñ zaznaczone";
			   this->button5->UseVisualStyleBackColor = true;
			   this->button5->Click += gcnew System::EventHandler(this, &Okno_dodaj_przepis::button5_Click);
			   // 
			   // label1
			   // 
			   this->label1->AutoSize = true;
			   this->label1->Location = System::Drawing::Point(12, 348);
			   this->label1->Name = L"label1";
			   this->label1->Size = System::Drawing::Size(213, 13);
			   this->label1->TabIndex = 11;
			   this->label1->Text = L"lub wpisz œcie¿kê do pliku z przepisem (.txt):";
			   // 
			   // textBox5
			   // 
			   this->textBox5->Location = System::Drawing::Point(12, 364);
			   this->textBox5->Name = L"textBox5";
			   this->textBox5->Size = System::Drawing::Size(537, 20);
			   this->textBox5->TabIndex = 12;
			   // 
			   // button6
			   // 
			   this->button6->Location = System::Drawing::Point(438, 389);
			   this->button6->Name = L"button6";
			   this->button6->Size = System::Drawing::Size(140, 34);
			   this->button6->TabIndex = 13;
			   this->button6->Text = L"Dodaj przepis";
			   this->button6->UseVisualStyleBackColor = true;
			   this->button6->Click += gcnew System::EventHandler(this, &Okno_dodaj_przepis::button6_Click);
			   // 
			   // button7
			   // 
			   this->button7->Location = System::Drawing::Point(556, 364);
			   this->button7->Name = L"button7";
			   this->button7->Size = System::Drawing::Size(22, 23);
			   this->button7->TabIndex = 14;
			   this->button7->Text = L"\?";
			   this->button7->UseVisualStyleBackColor = true;
			   this->button7->Click += gcnew System::EventHandler(this, &Okno_dodaj_przepis::button7_Click);
			   // 
			   // Okno_dodaj_przepis
			   // 
			   this->AutoScaleDimensions = System::Drawing::SizeF(6, 13);
			   this->AutoScaleMode = System::Windows::Forms::AutoScaleMode::Font;
			   this->ClientSize = System::Drawing::Size(590, 426);
			   this->Controls->Add(this->button7);
			   this->Controls->Add(this->button6);
			   this->Controls->Add(this->textBox5);
			   this->Controls->Add(this->label1);
			   this->Controls->Add(this->button5);
			   this->Controls->Add(this->button4);
			   this->Controls->Add(this->button3);
			   this->Controls->Add(this->textBox4);
			   this->Controls->Add(this->listView2);
			   this->Controls->Add(this->button2);
			   this->Controls->Add(this->textBox3);
			   this->Controls->Add(this->textBox2);
			   this->Controls->Add(this->listView1);
			   this->Controls->Add(this->textBox1);
			   this->Controls->Add(this->button1);
			   this->Name = L"Okno_dodaj_przepis";
			   this->Text = L"Okno_dodaj_przepis";
			   this->ResumeLayout(false);
			   this->PerformLayout();

		   }
#pragma endregion
		   //przycisk zamkniecia
	private: System::Void button1_Click(System::Object^ sender, System::EventArgs^ e) {
		if (listView1->Items->Count > 0 || listView2->Items->Count > 0 || textBox5->Text != "") {
			if (MessageBox::Show("Czy na pewno chcesz anulowaæ dodawane przepisu?", "Anulowaæ?", MessageBoxButtons::YesNo) == System::Windows::Forms::DialogResult::Yes)
				this->Close();
		}
		else
			this->Close();
	}

		   //dodaj skladnik
	private: System::Void button2_Click(System::Object^ sender, System::EventArgs^ e) {
		if (textBox2->Text != "" && textBox3->Text != "") { //jesli wprowadzono cos do textboxow
			std::string text = msclr::interop::marshal_as<std::string>(textBox3->Text);
			for (int i : text) {
				if (!std::isdigit(i)) { //sprawdzanie czy uzytkownik podal liczbe
					textBox2->Text = "";
					textBox3->Text = "";
					MessageBox::Show("Podana wartoœæ iloœci produktu nie jest liczb¹.");
					return;
				}
			}
			if (std::stoi(text) <= 0) { //jesli liczba jest mniejsza/rowna 0
				MessageBox::Show("Ilosc produktu musi byc wieksza od zera.");
				return;
			}
			ListViewItem^ element = gcnew ListViewItem(); //utworzenie nowego elementu listy
			element->Text = textBox2->Text;
			element->SubItems->Add(textBox3->Text);
			listView1->Items->Add(element);
			textBox2->Text = "";
			textBox3->Text = "";
		}
	}

		   //usun zaznaczone skladnik
	private: System::Void button4_Click(System::Object^ sender, System::EventArgs^ e) {
		if (listView1->SelectedItems->Count > 0) {
			listView1->Items->Remove(listView1->SelectedItems[0]);
		}
	}

		   //dodaj prok przygotowania
	private: System::Void button3_Click(System::Object^ sender, System::EventArgs^ e) {
		if (textBox4->Text != "") {
			ListViewItem^ element = gcnew ListViewItem();
			element->Text = textBox4->Text;
			listView2->Items->Add(element);
			textBox4->Text = "";
		}
	}

		   //usun zaznaczony krok przygotowania
	private: System::Void button5_Click(System::Object^ sender, System::EventArgs^ e) {
		if (listView2->SelectedItems->Count > 0) {
			listView2->Items->Remove(listView2->SelectedItems[0]);
		}
	}

		   //dodaj przepis
	private: System::Void button6_Click(System::Object^ sender, System::EventArgs^ e) {
		//jesli uzupelniono i dodawanie z aplikacji i sciezke do pliku
		if ((textBox1->Text != "Nazwa" || listView1->Items->Count > 0 || listView2->Items->Count > 0) && textBox5->Text != "") {
			MessageBox::Show("Nie mo¿na na raz dodaæ przepisu na dwa sposoby.", "Pola obu sposobów s¹ uzupe³nione");
			return;
		}
		//jesli uzupelniono pola dodawania przez aplikacje
		else if (textBox1->Text != "" && listView1->Items->Count > 0 && listView2->Items->Count > 0) {
			Przepis* przepis = new Przepis;
			//dodanie skladnikow
			for (int i = 0; i < listView1->Items->Count; ++i) { //sprawdzenie czy taki skladnik istnieje w bazie
				int index = Skladniki::getInstSkladniki().czy_istnieje_skladnik(msclr::interop::marshal_as<std::string>(listView1->Items[i]->Text));
				if (index == -1) {
					MessageBox::Show("Nie ma w bazie skladnika: " + listView1->Items[i]->Text + ".\nDodaj go do bazy aby mo¿na by³o go dodaæ do przepisu.");
					return;
				}
				else { //jesli istnieje - dodanie go do przepisu
					przepis->dodaj_skladnik(Skladniki::getInstSkladniki().getWszystkie_Skladniki()[index], std::stoi(msclr::interop::marshal_as<std::string>(listView1->Items[i]->SubItems[1]->Text)));
				}
			}
			//sprawdzanie czy w bazie nie ma juz przepisu o takiej nazwie
			if (Przepisy::getInstPrzepisy().czy_istnieje_nazwa(msclr::interop::marshal_as<std::string>(textBox1->Text)) != -1) {
				MessageBox::Show("Istnieje ju¿ w bazie przepis z tak¹ nazw¹");
				return;
			}
			else {//jesli nie ma
				//dodanie nazwy przepisu
				przepis->setNazwa(msclr::interop::marshal_as<std::string>(textBox1->Text));
				//dodanie krokow przygotowania
				for (int i = 0; i < listView2->Items->Count; ++i) {
					przepis->dodaj_Wykonanie(msclr::interop::marshal_as<std::string>(listView2->Items[i]->Text));
				}
				//dodanie przepisu do wektora z przepisami
				Przepisy::getInstPrzepisy().dodaj_przepis(przepis);
				Przepisy::getInstPrzepisy().setZmiana_p();
				MessageBox::Show("Udane dodanie przepisu");
			}
		}
		//jesli uzupelniono dodawanie z pliku
		else if (textBox5->Text != "") {
			fs::path sciezka = msclr::interop::marshal_as<std::string>(textBox5->Text);
			//jesli plik na ktory wskazuje sciezka nie ma formatu .txt
			if (sciezka.extension() != ".txt") {
				MessageBox::Show("Œcie¿ka musi wskazywaæ na plik z rozszerzeniem .txt.\nTa jest " + gcnew String(sciezka.extension().c_str()));
				return;
			}
			if (Przepisy::getInstPrzepisy().dodaj_przepis_z_pliku(sciezka) == false) {
				MessageBox::Show("Wyst¹pi³ problem z podanym plikiem lub z jego zawartoœci¹\nMo¿liwe powowdy niepowodzeinia ze wzglêdu na zawartoœæ:\n- istnieje ju¿ przepis o takiej nazwie\n- b³êdnie zapisany przepis (sprawdŸ przycisk \"?\")");
				return;
			}
			else {
				Przepisy::getInstPrzepisy().setZmiana_p();
				MessageBox::Show("Udane dodanie przepisu. \nOdœwie¿ panel przepisów aby zobaczyæ zmianê.");
			}
		}
		//jesli czegos nie usupelniono
		else {
			MessageBox::Show("Czegoœ tu brakuje");
		}
	}
		   //?
	private: System::Void button7_Click(System::Object^ sender, System::EventArgs^ e) {
		MessageBox::Show("Poprawny przepis nie zawiara polskich znaków oraz ma formê:\n@nazwa przepisu\n#nazwa skladnika 1\n-iloœæ skladnika 1 w gramach\n...\n#nazwa skladnika n\n-iloœæ skladnika n w gramach\n*krok przygotowania 1\n...\n*krok przygotowania n\n\nnp.:\n@kanapka z serem\n#kromka chleba\n-4\n#ser zolty\n-1\n*zloz kanapke","poprawny przepis");
	}
};
}
