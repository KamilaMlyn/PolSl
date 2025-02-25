#pragma once
#include "Przepis.h"

namespace Project3 {

	using namespace System;
	using namespace System::ComponentModel;
	using namespace System::Collections;
	using namespace System::Windows::Forms;
	using namespace System::Data;
	using namespace System::Drawing;

	public ref class Okno_dodaj_skladnik : public System::Windows::Forms::Form
	{
	public:
		Okno_dodaj_skladnik(void)
		{
			InitializeComponent();
		}

	protected:
		~Okno_dodaj_skladnik()
		{
			if (components)
			{
				delete components;
			}
		}
	private: System::Windows::Forms::TextBox^ textBox1;
	private: System::Windows::Forms::TextBox^ textBox2;
	private: System::Windows::Forms::Button^ button1;
	private: System::Windows::Forms::Button^ button2;
	private: System::Windows::Forms::RadioButton^ radioButton1;
	private: System::Windows::Forms::RadioButton^ radioButton2;

	private: System::ComponentModel::Container^ components;

#pragma region Windows Form Designer generated code
		void InitializeComponent(void)
		{
			this->textBox1 = (gcnew System::Windows::Forms::TextBox());
			this->textBox2 = (gcnew System::Windows::Forms::TextBox());
			this->button1 = (gcnew System::Windows::Forms::Button());
			this->button2 = (gcnew System::Windows::Forms::Button());
			this->radioButton1 = (gcnew System::Windows::Forms::RadioButton());
			this->radioButton2 = (gcnew System::Windows::Forms::RadioButton());
			this->SuspendLayout();
			// 
			// textBox1
			// 
			this->textBox1->Location = System::Drawing::Point(132, 120);
			this->textBox1->Name = L"textBox1";
			this->textBox1->Size = System::Drawing::Size(330, 20);
			this->textBox1->TabIndex = 0;
			this->textBox1->Text = L"Nazwa sk³adnika";
			this->textBox1->Click += gcnew System::EventHandler(this, &Okno_dodaj_skladnik::textBox1_Click);
			// 
			// textBox2
			// 
			this->textBox2->Location = System::Drawing::Point(132, 198);
			this->textBox2->Name = L"textBox2";
			this->textBox2->Size = System::Drawing::Size(130, 20);
			this->textBox2->TabIndex = 1;
			this->textBox2->Text = L"kalorycznoœæ na 100g";
			this->textBox2->Click += gcnew System::EventHandler(this, &Okno_dodaj_skladnik::textBox2_Click);
			// 
			// button1
			// 
			this->button1->Location = System::Drawing::Point(12, 12);
			this->button1->Name = L"button1";
			this->button1->Size = System::Drawing::Size(24, 23);
			this->button1->TabIndex = 2;
			this->button1->Text = L"X";
			this->button1->UseVisualStyleBackColor = true;
			this->button1->Click += gcnew System::EventHandler(this, &Okno_dodaj_skladnik::button1_Click);
			// 
			// button2
			// 
			this->button2->Location = System::Drawing::Point(337, 276);
			this->button2->Name = L"button2";
			this->button2->Size = System::Drawing::Size(125, 23);
			this->button2->TabIndex = 3;
			this->button2->Text = L"Dodaj sk³adnik";
			this->button2->UseVisualStyleBackColor = true;
			this->button2->Click += gcnew System::EventHandler(this, &Okno_dodaj_skladnik::button2_Click);
			// 
			// radioButton1
			// 
			this->radioButton1->AutoSize = true;
			this->radioButton1->Location = System::Drawing::Point(268, 201);
			this->radioButton1->Name = L"radioButton1";
			this->radioButton1->Size = System::Drawing::Size(45, 17);
			this->radioButton1->TabIndex = 4;
			this->radioButton1->TabStop = true;
			this->radioButton1->Text = L"kcal";
			this->radioButton1->UseVisualStyleBackColor = true;
			// 
			// radioButton2
			// 
			this->radioButton2->AutoSize = true;
			this->radioButton2->Location = System::Drawing::Point(319, 201);
			this->radioButton2->Name = L"radioButton2";
			this->radioButton2->Size = System::Drawing::Size(36, 17);
			this->radioButton2->TabIndex = 5;
			this->radioButton2->TabStop = true;
			this->radioButton2->Text = L"kJ";
			this->radioButton2->UseVisualStyleBackColor = true;
			// 
			// Okno_dodaj_skladnik
			// 
			this->AutoScaleDimensions = System::Drawing::SizeF(6, 13);
			this->AutoScaleMode = System::Windows::Forms::AutoScaleMode::Font;
			this->ClientSize = System::Drawing::Size(590, 426);
			this->Controls->Add(this->radioButton2);
			this->Controls->Add(this->radioButton1);
			this->Controls->Add(this->button2);
			this->Controls->Add(this->button1);
			this->Controls->Add(this->textBox2);
			this->Controls->Add(this->textBox1);
			this->FormBorderStyle = System::Windows::Forms::FormBorderStyle::None;
			this->Name = L"Okno_dodaj_skladnik";
			this->Text = L"Okno_dodaj_skladnik";
			this->ResumeLayout(false);
			this->PerformLayout();

		}
#pragma endregion
		//dodaj skladnik
	private: System::Void button2_Click(System::Object^ sender, System::EventArgs^ e) {
		if ((textBox1->Text != "Nazwa sk³adnika" || textBox1->Text != "") && (textBox2->Text != "kalorycznoœæ na 100g" || textBox2->Text != "")) {
			//sprawdzenie czy podano liczbe jako kalorycznosc
			std::string text = msclr::interop::marshal_as<std::string>(textBox2->Text);
			for (int i : text) {
				if (!std::isdigit(i)) { //sprawdzanie czy uzytkownik podal liczbe
					MessageBox::Show("Podana wartoœæ kalorycznoœci nie jest liczb¹.\n");
					return;
				}
			}
			if (std::stoi(text) <= 0) {
				MessageBox::Show("Podana wartoœæ kalorycznoœci nie mo¿e byæ ujemna ani równa 0.\n");
				return;
			}
			if ((!radioButton1->Checked) && (!radioButton2->Checked)) {
				MessageBox::Show("Wybierz jednostkê.\n");
				return;
			}
			Skladnik* skladnik;
			if (radioButton2->Checked) {
				skladnik = new Skladnik(msclr::interop::marshal_as<std::string>(textBox1->Text), (std::stoi(text) / 4, 19));
			}
			else
				skladnik = new Skladnik(msclr::interop::marshal_as<std::string>(textBox1->Text), std::stoi(text));
			Skladniki::getInstSkladniki().dodaj_skladnik(skladnik);
			Skladniki::getInstSkladniki().setZmiana_s();
			MessageBox::Show("Dodano sk³adnik.\nOdœwierz panel ze sk³adnikami, aby zobaczyæ zmianê.");
		}
	}

		   //X
	private: System::Void button1_Click(System::Object^ sender, System::EventArgs^ e) {
		if (textBox1->Text != "" || textBox2->Text != "") {
			if (MessageBox::Show("Czy na pewno chcesz zamkn¹æ?", "Zamkn¹æ?", MessageBoxButtons::YesNo) == System::Windows::Forms::DialogResult::Yes) {
				this->Close();
			}
		}
		else {
			this->Close();
		}
	}

		   //klikniecie w nazwe skladnika
	private: System::Void textBox1_Click(System::Object^ sender, System::EventArgs^ e) {
		textBox1->Text = "";
	}

		   //klikniecie w boxa z kalorycznoscia
	private: System::Void textBox2_Click(System::Object^ sender, System::EventArgs^ e) {
		textBox2->Text = "";
	}
	};
}
