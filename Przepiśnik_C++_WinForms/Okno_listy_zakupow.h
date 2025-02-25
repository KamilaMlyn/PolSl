#pragma once
#include "Przepis.h"

namespace Project3 {

	using namespace System;
	using namespace System::ComponentModel;
	using namespace System::Collections;
	using namespace System::Windows::Forms;
	using namespace System::Data;
	using namespace System::Drawing;

	public ref class Okno_listy_zakupow : public System::Windows::Forms::Form
	{
		Przepis* przepis;
	public:
		Okno_listy_zakupow(Przepis* przepis)
		{
			this->przepis = przepis;
			InitializeComponent();
		}

	protected:
		~Okno_listy_zakupow()
		{
			if (components)
			{
				delete components;
			}
		}
	private: System::Windows::Forms::Label^ label1;
	private: System::Windows::Forms::TextBox^ textBox1;
	private: System::Windows::Forms::Label^ label2;
	private: System::Windows::Forms::TextBox^ textBox2;
	private: System::Windows::Forms::Button^ button1;

	private: System::ComponentModel::Container^ components;

#pragma region Windows Form Designer generated code
		void InitializeComponent(void)
		{
			this->label1 = (gcnew System::Windows::Forms::Label());
			this->textBox1 = (gcnew System::Windows::Forms::TextBox());
			this->label2 = (gcnew System::Windows::Forms::Label());
			this->textBox2 = (gcnew System::Windows::Forms::TextBox());
			this->button1 = (gcnew System::Windows::Forms::Button());
			this->SuspendLayout();
			// 
			// label1
			// 
			this->label1->AutoSize = true;
			this->label1->Location = System::Drawing::Point(26, 58);
			this->label1->Name = L"label1";
			this->label1->Size = System::Drawing::Size(205, 13);
			this->label1->TabIndex = 0;
			this->label1->Text = L"Jak nazwaæ plik z list¹\? (bez rozszerzenia)";
			// 
			// textBox1
			// 
			this->textBox1->Location = System::Drawing::Point(29, 74);
			this->textBox1->Name = L"textBox1";
			this->textBox1->Size = System::Drawing::Size(352, 20);
			this->textBox1->TabIndex = 1;
			// 
			// label2
			// 
			this->label2->AutoSize = true;
			this->label2->Location = System::Drawing::Point(26, 131);
			this->label2->Name = L"label2";
			this->label2->Size = System::Drawing::Size(258, 13);
			this->label2->TabIndex = 2;
			this->label2->Text = L"W jakim folderze ma siê znaleŸæ\? (œcie¿ka do folderu)";
			// 
			// textBox2
			// 
			this->textBox2->Location = System::Drawing::Point(29, 147);
			this->textBox2->Name = L"textBox2";
			this->textBox2->Size = System::Drawing::Size(352, 20);
			this->textBox2->TabIndex = 3;
			// 
			// button1
			// 
			this->button1->Location = System::Drawing::Point(306, 190);
			this->button1->Name = L"button1";
			this->button1->Size = System::Drawing::Size(75, 23);
			this->button1->TabIndex = 4;
			this->button1->Text = L"Gotowe";
			this->button1->UseVisualStyleBackColor = true;
			this->button1->Click += gcnew System::EventHandler(this, &Okno_listy_zakupow::button1_Click);
			// 
			// Okno_listy_zakupow
			// 
			this->AutoScaleDimensions = System::Drawing::SizeF(6, 13);
			this->AutoScaleMode = System::Windows::Forms::AutoScaleMode::Font;
			this->ClientSize = System::Drawing::Size(403, 240);
			this->Controls->Add(this->button1);
			this->Controls->Add(this->textBox2);
			this->Controls->Add(this->label2);
			this->Controls->Add(this->textBox1);
			this->Controls->Add(this->label1);
			this->Name = L"Okno_listy_zakupow";
			this->Text = L"Okno_listy_zakupow";
			this->ResumeLayout(false);
			this->PerformLayout();
			this->StartPosition = FormStartPosition::CenterParent;
		}
#pragma endregion
		//przycisk gotowe
	private: System::Void button1_Click(System::Object^ sender, System::EventArgs^ e) {
		if (textBox2->Text != "" && textBox1->Text != "") {
			fs::path sciezka = msclr::interop::marshal_as<std::string>(textBox2->Text + "/" + textBox1->Text + ".txt");
			if (przepis->lista_zakupow(sciezka)) {
				MessageBox::Show("Wygenerowano listê zakupów");
				this->Close();
			}
			else {
				MessageBox::Show("Wyst¹pi³ problem z otwarciem folderu");
				return;
			}
		}
		else {
			MessageBox::Show("Musisz uzupe³niæ oba pola");
		}
	}
	};
}