#pragma once
#include "Przepis.h"

namespace Project3 {
	using namespace System;
	using namespace System::ComponentModel;
	using namespace System::Collections;
	using namespace System::Windows::Forms;
	using namespace System::Data;
	using namespace System::Drawing;

	public ref class Okno_Skladnika : public System::Windows::Forms::Form
	{
	public:
		int miejsce_s;
		Okno_Skladnika(Skladnik* skladnik, int miejsce)
		{
			miejsce_s = miejsce;
			InitializeComponent();
			label1->Text = gcnew String(skladnik->getNazwa().c_str());
			std::string jednostka;
			if (Kalkulator::getInstKalk().getWybor() == true) {
				label2->Text = gcnew String("" + skladnik->getKcal());
				label4->Text = "kcal";
			}
			else {
				label2->Text = gcnew String("" + (skladnik->getKcal() * 4, 19));
				label4->Text = "kJ";
			}
		}
	protected:
		~Okno_Skladnika()
		{
			if (components)
			{
				delete components;
			}
		}

	private: System::Windows::Forms::Label^ label1; //nazwa
	private: System::Windows::Forms::Label^ label2; //kalorycznosc
	private: System::Windows::Forms::Button^ button1;
	private: System::Windows::Forms::Label^ label3;
	private: System::Windows::Forms::TextBox^ textBox1; //nazwa skladnika
	private: System::Windows::Forms::TextBox^ textBox2; //kalorycznosc
	private: System::Windows::Forms::Button^ button2;
	private: System::Windows::Forms::Button^ button3;
	private: System::Windows::Forms::Button^ button4;
	private: System::Windows::Forms::Label^ label4;
	private: System::Windows::Forms::RadioButton^ radioButton1;
	private: System::Windows::Forms::RadioButton^ radioButton2;
	private: System::Windows::Forms::Label^ label5;

	private: System::ComponentModel::Container^ components;

#pragma region Windows Form Designer generated code
		   void InitializeComponent(void)
		   {
			   this->label1 = (gcnew System::Windows::Forms::Label());
			   this->label2 = (gcnew System::Windows::Forms::Label());
			   this->button1 = (gcnew System::Windows::Forms::Button());
			   this->label3 = (gcnew System::Windows::Forms::Label());
			   this->textBox1 = (gcnew System::Windows::Forms::TextBox());
			   this->textBox2 = (gcnew System::Windows::Forms::TextBox());
			   this->button2 = (gcnew System::Windows::Forms::Button());
			   this->button3 = (gcnew System::Windows::Forms::Button());
			   this->button4 = (gcnew System::Windows::Forms::Button());
			   this->label4 = (gcnew System::Windows::Forms::Label());
			   this->radioButton1 = (gcnew System::Windows::Forms::RadioButton());
			   this->radioButton2 = (gcnew System::Windows::Forms::RadioButton());
			   this->label5 = (gcnew System::Windows::Forms::Label());
			   this->SuspendLayout();
			   // 
			   // label1
			   // 
			   this->label1->AutoSize = true;
			   this->label1->Location = System::Drawing::Point(282, 55);
			   this->label1->Name = L"label1";
			   this->label1->Size = System::Drawing::Size(35, 13);
			   this->label1->TabIndex = 0;
			   this->label1->Text = L"label1";
			   // 
			   // label2
			   // 
			   this->label2->AutoSize = true;
			   this->label2->Location = System::Drawing::Point(115, 125);
			   this->label2->Name = L"label2";
			   this->label2->Size = System::Drawing::Size(35, 13);
			   this->label2->TabIndex = 1;
			   this->label2->Text = L"label2";
			   // 
			   // button1
			   // 
			   this->button1->Location = System::Drawing::Point(13, 13);
			   this->button1->Name = L"button1";
			   this->button1->Size = System::Drawing::Size(33, 23);
			   this->button1->TabIndex = 2;
			   this->button1->Text = L"X";
			   this->button1->UseVisualStyleBackColor = true;
			   this->button1->Click += gcnew System::EventHandler(this, &Okno_Skladnika::button1_Click);
			   // 
			   // label3
			   // 
			   this->label3->AutoSize = true;
			   this->label3->Location = System::Drawing::Point(426, 329);
			   this->label3->Name = L"label3";
			   this->label3->Size = System::Drawing::Size(35, 13);
			   this->label3->TabIndex = 3;
			   this->label3->Text = L"edytuj";
			   this->label3->Click += gcnew System::EventHandler(this, &Okno_Skladnika::label3_Click);
			   // 
			   // textBox1
			   // 
			   this->textBox1->Location = System::Drawing::Point(257, 55);
			   this->textBox1->Name = L"textBox1";
			   this->textBox1->Size = System::Drawing::Size(100, 20);
			   this->textBox1->TabIndex = 4;
			   this->textBox1->Visible = false;
			   // 
			   // textBox2
			   // 
			   this->textBox2->Location = System::Drawing::Point(105, 120);
			   this->textBox2->Name = L"textBox2";
			   this->textBox2->Size = System::Drawing::Size(100, 20);
			   this->textBox2->TabIndex = 5;
			   this->textBox2->Visible = false;
			   // 
			   // button2
			   // 
			   this->button2->Location = System::Drawing::Point(368, 237);
			   this->button2->Name = L"button2";
			   this->button2->Size = System::Drawing::Size(133, 23);
			   this->button2->TabIndex = 6;
			   this->button2->Text = L"Akceptuj zmiany";
			   this->button2->UseVisualStyleBackColor = true;
			   this->button2->Visible = false;
			   this->button2->Click += gcnew System::EventHandler(this, &Okno_Skladnika::button2_Click);
			   // 
			   // button3
			   // 
			   this->button3->Location = System::Drawing::Point(181, 237);
			   this->button3->Name = L"button3";
			   this->button3->Size = System::Drawing::Size(121, 23);
			   this->button3->TabIndex = 7;
			   this->button3->Text = L"Odrzuæ zmiany";
			   this->button3->UseVisualStyleBackColor = true;
			   this->button3->Visible = false;
			   this->button3->Click += gcnew System::EventHandler(this, &Okno_Skladnika::button3_Click);
			   // 
			   // button4
			   // 
			   this->button4->Location = System::Drawing::Point(478, 430);
			   this->button4->Name = L"button4";
			   this->button4->Size = System::Drawing::Size(120, 23);
			   this->button4->TabIndex = 8;
			   this->button4->Text = L"Usuñ ten sk³adnik";
			   this->button4->UseVisualStyleBackColor = true;
			   this->button4->Click += gcnew System::EventHandler(this, &Okno_Skladnika::button4_Click);
			   // 
			   // label4
			   // 
			   this->label4->AutoSize = true;
			   this->label4->Location = System::Drawing::Point(157, 125);
			   this->label4->Name = L"label4";
			   this->label4->Size = System::Drawing::Size(35, 13);
			   this->label4->TabIndex = 9;
			   this->label4->Text = L"label4";
			   // 
			   // radioButton1
			   // 
			   this->radioButton1->AutoSize = true;
			   this->radioButton1->Location = System::Drawing::Point(211, 121);
			   this->radioButton1->Name = L"radioButton1";
			   this->radioButton1->Size = System::Drawing::Size(45, 17);
			   this->radioButton1->TabIndex = 10;
			   this->radioButton1->TabStop = true;
			   this->radioButton1->Text = L"kcal";
			   this->radioButton1->UseVisualStyleBackColor = true;
			   this->radioButton1->Visible = false;
			   // 
			   // radioButton2
			   // 
			   this->radioButton2->AutoSize = true;
			   this->radioButton2->Location = System::Drawing::Point(262, 121);
			   this->radioButton2->Name = L"radioButton2";
			   this->radioButton2->Size = System::Drawing::Size(36, 17);
			   this->radioButton2->TabIndex = 11;
			   this->radioButton2->TabStop = true;
			   this->radioButton2->Text = L"kJ";
			   this->radioButton2->UseVisualStyleBackColor = true;
			   this->radioButton2->Visible = false;
			   // 
			   // label5
			   // 
			   this->label5->AutoSize = true;
			   this->label5->Location = System::Drawing::Point(102, 95);
			   this->label5->Name = L"label5";
			   this->label5->Size = System::Drawing::Size(73, 13);
			   this->label5->TabIndex = 12;
			   this->label5->Text = L"Kalorycznoœæ:";
			   // 
			   // Okno_Skladnika
			   // 
			   this->AutoScaleDimensions = System::Drawing::SizeF(6, 13);
			   this->AutoScaleMode = System::Windows::Forms::AutoScaleMode::Font;
			   this->ClientSize = System::Drawing::Size(610, 465);
			   this->Controls->Add(this->label5);
			   this->Controls->Add(this->radioButton2);
			   this->Controls->Add(this->radioButton1);
			   this->Controls->Add(this->label4);
			   this->Controls->Add(this->button4);
			   this->Controls->Add(this->button3);
			   this->Controls->Add(this->button2);
			   this->Controls->Add(this->textBox2);
			   this->Controls->Add(this->textBox1);
			   this->Controls->Add(this->label3);
			   this->Controls->Add(this->button1);
			   this->Controls->Add(this->label2);
			   this->Controls->Add(this->label1);
			   this->FormBorderStyle = System::Windows::Forms::FormBorderStyle::None;
			   this->Name = L"Okno_Skladnika";
			   this->Text = L"Okno_Skladnika";
			   this->ResumeLayout(false);
			   this->PerformLayout();

		   }
#pragma endregion
		   //klikniecie na "edytuj"
	private: System::Void label3_Click(System::Object^ sender, System::EventArgs^ e) {
		label1->Visible = false; //nie widac nazwy
		label2->Visible = false;//nie widac kalorycznosci
		label3->Visible = false;//nie widac "edytuj"
		label4->Visible = false;//nie widac jednostki
		radioButton1->Visible = true;
		radioButton2->Visible = true;

		textBox1->Text = label1->Text; //uzupelnienie textboga stara nazwa
		textBox2->Text = label2->Text;//uzupelnienie textboga stara kalorycznoscia
		textBox1->Visible = true; //widoczny textbox nazwy
		textBox2->Visible = true; //widoczny textbox kalorycznosci
		button2->Visible = true; //widoczny przycisk akceptacji zmiany
		button3->Visible = true; //widoczny przycisk odrzucenia zmiany
	}

		   //X 
	private: System::Void button1_Click(System::Object^ sender, System::EventArgs^ e) {
		this->Close();
	}

		   //przycisk "Akceptuj zmiany"
	private: System::Void button2_Click(System::Object^ sender, System::EventArgs^ e) {
		//jesli zmieniono nazwe
		if (label1->Text != textBox1->Text) {
			Skladniki::getInstSkladniki().getWszystkie_Skladniki()[miejsce_s]->setNazwa(msclr::interop::marshal_as<std::string>(textBox1->Text));
			Skladniki::getInstSkladniki().setZmiana_s();
			label1->Text = textBox1->Text;
		}
		//jesli zmieniono kalorycznosc
		if (label2->Text != textBox2->Text) {
			std::string text = msclr::interop::marshal_as<std::string>(textBox2->Text);
			for (int i : text) {
				if (!std::isdigit(i)) {
					MessageBox::Show("Liczba kalorii musi byæ liczb¹");
					return;
				}
			}
			if (std::stoi(text) <= 0) {
				MessageBox::Show("Kalorycznosc musi byc wielsza od 0");
				return;
			}
			if ((!radioButton1->Checked) && (!radioButton2->Checked)) {
				MessageBox::Show("wybierz jednostkê");
				return;
			}
			if (radioButton1->Checked) {
				Skladniki::getInstSkladniki().getWszystkie_Skladniki()[miejsce_s]->setKcal(std::stoi(text));
				label4->Text = "kcal";
			}
			else {
				Skladniki::getInstSkladniki().getWszystkie_Skladniki()[miejsce_s]->setKcal((std::stoi(text) / 4, 19));
				label4->Text = "kJ";
			}

			label2->Text = textBox2->Text;
			Skladniki::getInstSkladniki().setZmiana_s();
		}
		radioButton1->Visible = false;
		radioButton2->Visible = false;
		label4->Visible = true;
		textBox1->Visible = false; //widocznosc textboxa nazwy
		textBox2->Visible = false; //wodocznosc textboxa kalorycznosci
		label1->Visible = true; //widac nazwe
		label2->Visible = true; //widac kalorycznosc
		label3->Visible = true; //widac "edytuj"
		button2->Visible = false; //nie widoczny przycisk akceptacji zmiany
		button3->Visible = false; // nie widoczny przycisk odrzucenia zmiany
		MessageBox::Show("\tZaktualizowano sk³adnik\nOdœwie¿ panel sk³adników aby zobaczyæ zmiany");
	}

		   //przycisk "Odrzuæ zmiany"
	private: System::Void button3_Click(System::Object^ sender, System::EventArgs^ e) {
		radioButton1->Visible = false;
		radioButton2->Visible = false;
		label4->Visible = true;
		label1->Visible = true;
		label2->Visible = true;
		label3->Visible = true;
		this->textBox1->Visible = false;
		this->textBox2->Visible = false;
		button2->Visible = false;
		button3->Visible = false;
	}

		   //usun ten skladnik
	private: System::Void button4_Click(System::Object^ sender, System::EventArgs^ e) {
		if (MessageBox::Show("Czy na pewno chcesz usun¹æ ten sk³adnik?", "Usun¹æ sk³adnik?", MessageBoxButtons::YesNo) == System::Windows::Forms::DialogResult::Yes) {
			Skladniki::getInstSkladniki().usun_skladnik(miejsce_s);
			MessageBox::Show("Usuniêto sk³adnik.\nAby zobaczyæ zmiany odœwierz panel sk³adników");
			Skladniki::getInstSkladniki().setZmiana_s();
		}
	}
	};
}
