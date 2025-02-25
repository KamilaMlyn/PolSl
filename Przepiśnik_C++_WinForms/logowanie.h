#pragma once
#include "Przepis.h"
namespace Project3 {

	using namespace System;
	using namespace System::ComponentModel;
	using namespace System::Collections;
	using namespace System::Windows::Forms;
	using namespace System::Collections::Generic;
	using namespace System::Data;
	using namespace System::Drawing;

	public ref class logowanie : public System::Windows::Forms::Form
	{
	public:
		logowanie(void)
		{
			InitializeComponent();
		}

	protected:

		~logowanie()
		{
			if (components)
			{
				delete components;
			}
		}
	private: System::Windows::Forms::TextBox^ textBox1; //do loginu
	private: System::Windows::Forms::TextBox^ textBox2; //do hasla
	private: System::Windows::Forms::Button^ button1; //zaloguj
	private: System::Windows::Forms::Label^ label1; //napis "login"
	private: System::Windows::Forms::Label^ label2; //napis "haslo"
	private: System::Windows::Forms::Label^ label3; //napis "LOGOWANIE / REJESTRACJA"
	private: System::Windows::Forms::Button^ button2; //dodaj nowego uzytkownika
	private: System::Windows::Forms::Button^ button3; //pytajnik - zasady hasla

	private: System::ComponentModel::Container ^components;

#pragma region Windows Form Designer generated code
		void InitializeComponent(void)
		{
			this->textBox1 = (gcnew System::Windows::Forms::TextBox());
			this->textBox2 = (gcnew System::Windows::Forms::TextBox());
			this->button1 = (gcnew System::Windows::Forms::Button());
			this->label1 = (gcnew System::Windows::Forms::Label());
			this->label2 = (gcnew System::Windows::Forms::Label());
			this->label3 = (gcnew System::Windows::Forms::Label());
			this->button2 = (gcnew System::Windows::Forms::Button());
			this->button3 = (gcnew System::Windows::Forms::Button());
			this->SuspendLayout();
			// 
			// textBox1
			// 
			this->textBox1->Location = System::Drawing::Point(46, 74);
			this->textBox1->Name = L"textBox1";
			this->textBox1->Size = System::Drawing::Size(206, 20);
			this->textBox1->TabIndex = 0;
			this->textBox1->Text = "admin";
			// 
			// textBox2
			// 
			this->textBox2->Location = System::Drawing::Point(46, 152);
			this->textBox2->Name = L"textBox2";
			this->textBox2->Size = System::Drawing::Size(206, 20);
			this->textBox2->TabIndex = 1;
			this->textBox2->Text = "admin";
			// 
			// button1
			// 
			this->button1->Location = System::Drawing::Point(46, 195);
			this->button1->Name = L"button1";
			this->button1->Size = System::Drawing::Size(75, 23);
			this->button1->TabIndex = 2;
			this->button1->Text = L"zaloguj";
			this->button1->UseVisualStyleBackColor = true;
			this->button1->Click += gcnew System::EventHandler(this, &logowanie::button1_Click);
			// 
			// label1
			// 
			this->label1->AutoSize = true;
			this->label1->Location = System::Drawing::Point(43, 58);
			this->label1->Name = L"label1";
			this->label1->Size = System::Drawing::Size(29, 13);
			this->label1->TabIndex = 3;
			this->label1->Text = L"login";
			// 
			// label2
			// 
			this->label2->AutoSize = true;
			this->label2->Location = System::Drawing::Point(43, 136);
			this->label2->Name = L"label2";
			this->label2->Size = System::Drawing::Size(34, 13);
			this->label2->TabIndex = 4;
			this->label2->Text = L"has³o";
			// 
			// label3
			// 
			this->label3->AutoSize = true;
			this->label3->Location = System::Drawing::Point(73, 27);
			this->label3->Name = L"label3";
			this->label3->Size = System::Drawing::Size(159, 13);
			this->label3->TabIndex = 5;
			this->label3->Text = L"LOGOWANIE / REJESTRACJA";
			// 
			// button2
			// 
			this->button2->Location = System::Drawing::Point(127, 195);
			this->button2->Name = L"button2";
			this->button2->Size = System::Drawing::Size(146, 23);
			this->button2->TabIndex = 6;
			this->button2->Text = L"dodaj nowego uzytkownika";
			this->button2->UseVisualStyleBackColor = true;
			this->button2->Click += gcnew System::EventHandler(this, &logowanie::button2_Click);
			// 
			// button3
			// 
			this->button3->Font = (gcnew System::Drawing::Font(L"Microsoft Sans Serif", 5.25F, System::Drawing::FontStyle::Regular, System::Drawing::GraphicsUnit::Point,
				static_cast<System::Byte>(238)));
			this->button3->Location = System::Drawing::Point(76, 131);
			this->button3->Name = L"button3";
			this->button3->Size = System::Drawing::Size(18, 18);
			this->button3->TabIndex = 7;
			this->button3->Text = L"\?";
			this->button3->UseVisualStyleBackColor = true;
			this->button3->Click += gcnew System::EventHandler(this, &logowanie::button3_Click);
			// 
			// logowanie
			// 
			this->AutoScaleDimensions = System::Drawing::SizeF(6, 13);
			this->AutoScaleMode = System::Windows::Forms::AutoScaleMode::Font;
			this->ClientSize = System::Drawing::Size(300, 277);
			this->Controls->Add(this->button3);
			this->Controls->Add(this->button2);
			this->Controls->Add(this->label3);
			this->Controls->Add(this->label2);
			this->Controls->Add(this->label1);
			this->Controls->Add(this->button1);
			this->Controls->Add(this->textBox2);
			this->Controls->Add(this->textBox1);
			this->Name = L"logowanie";
			this->Text = L"logowanie";
			this->ResumeLayout(false);
			this->PerformLayout();
			this->StartPosition = FormStartPosition::CenterParent;
		}
#pragma endregion
		//pytajnik kolo hasla:
	private: System::Void button3_Click(System::Object^ sender, System::EventArgs^ e) {
		MessageBox::Show("Has³o powinno zawieraæ:\n\t*min 8 znaków\n\t*min 1 du¿¹ literê\n\t*min 1 ma³¹ literê\n\t*min 1 cyfrê\n\t*min 1 znak specjalny ^ $ * &-_");
	}

		//przycisk zaloguj
	private: System::Void button1_Click(System::Object^ sender, System::EventArgs^ e) {
		int wynik = Uzytkownik::getInstUzyt().logowanie(msclr::interop::marshal_as<std::string>(textBox1->Text), msclr::interop::marshal_as<std::string>(textBox2->Text));
		if (wynik == 0) { //jesli wprowadzone dane uzytkownika byly prawidlowe
			Uzytkownik::getInstUzyt().ulubione_z_bazy();
			MessageBox::Show("Logowanie udane");
			this->Close();
		}
		else if(wynik == 1) //jesli wprowadzone dane uzytkownika nie byly prawidlowe
			MessageBox::Show("Logowanie nie udane");
		else  //jesli problem z baza uytkownikow
			MessageBox::Show("Wyst¹pi³ problem z plikiem zawierac¹cym dane u¿ytkowników");
	}

		//przycisk dodaj nowego uzytkownika
	private: System::Void button2_Click(System::Object^ sender, System::EventArgs^ e) {
		int wynik = Uzytkownik::getInstUzyt().nowy_uz(msclr::interop::marshal_as<std::string>(textBox1->Text), msclr::interop::marshal_as<std::string>(textBox2->Text));
		/*
		* 0 - istnieje juz taki uzytkownik
		* 1 - nie udalo sie otworzyc pliku z danymi uzytkownikow
		* 2 - haslo nie spelnia wymagan
		* 3 - udane
		*/
		if (wynik == 0)
			MessageBox::Show("Istnieje ju¿ u¿ytkownik o takim loginie");
		else if (wynik == 1)
			MessageBox::Show("Wyst¹pi³ problem z plikiem zawierac¹cym dane u¿ytkowników");
		else if(wynik == 2)
			MessageBox::Show("Has³o nie spe³nia wymagañ bezpiecznego has³a\nKliknij przycisk \"?\" w celu uzyskania informacji");
		else
			MessageBox::Show("U¿ytkownik dodany. Mo¿esz siê zalogowaæ");
	}
};
}
