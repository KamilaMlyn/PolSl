#pragma once
#include "Okno_listy_zakupow.h"
#include "Przepis.h"
namespace Project3 {

	using namespace System;
	using namespace System::ComponentModel;
	using namespace System::Collections;
	using namespace System::Windows::Forms;
	using namespace System::Data;
	using namespace System::Drawing;

	public ref class Okno_konta : public System::Windows::Forms::Form
	{
	public:
		Okno_konta()
		{
			InitializeComponent();
			label1->Text = gcnew String(Uzytkownik::getInstUzyt().getLogin().c_str()); //ustawienie loginu
			if (Kalkulator::getInstKalk().getWybor() == true) //ustawienie jednostki
				this->columnHeader2->Text = L"kcal";
			else
				this->columnHeader2->Text = L"kJ";
			for (int i = 0; i < Uzytkownik::getInstUzyt().getUlubione().size(); ++i) { //dodanie ulubionych do listy
				ListViewItem^ element = gcnew ListViewItem(gcnew System::String(Uzytkownik::getInstUzyt().getUlubione()[i]->getNazwa().c_str()));
				element->SubItems->Add(gcnew System::String("" + Kalkulator::getInstKalk().oblicz(Uzytkownik::getInstUzyt().getUlubione()[i])));
				listView1->Items->Add(element);
			}
		}

	protected:
		~Okno_konta()
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
	private: System::Windows::Forms::Button^ button1;
	private: System::Windows::Forms::Button^ button2;
	private: System::Windows::Forms::Button^ button3;

	private: System::Windows::Forms::Button^ button4;


	private: System::ComponentModel::Container^ components;

#pragma region Windows Form Designer generated code
		   void InitializeComponent(void)
		   {

			   this->label1 = (gcnew System::Windows::Forms::Label());
			   this->listView1 = (gcnew System::Windows::Forms::ListView());
			   this->columnHeader1 = (gcnew System::Windows::Forms::ColumnHeader());
			   this->columnHeader2 = (gcnew System::Windows::Forms::ColumnHeader());
			   this->button1 = (gcnew System::Windows::Forms::Button());
			   this->button2 = (gcnew System::Windows::Forms::Button());
			   this->button3 = (gcnew System::Windows::Forms::Button());
			   this->button4 = (gcnew System::Windows::Forms::Button());
			   this->SuspendLayout();
			   // 
			   // label1
			   // 
			   this->label1->AutoSize = true;
			   this->label1->Location = System::Drawing::Point(72, 79);
			   this->label1->Name = L"label1";
			   this->label1->Size = System::Drawing::Size(35, 13);
			   this->label1->TabIndex = 0;
			   this->label1->Text = L"label1";
			   // 
			   // listView1
			   // 
			   this->listView1->Columns->AddRange(gcnew cli::array< System::Windows::Forms::ColumnHeader^  >(2) { this->columnHeader1, this->columnHeader2 });
			   this->listView1->HideSelection = false;
			   this->listView1->Location = System::Drawing::Point(75, 131);
			   this->listView1->Name = L"listView1";
			   this->listView1->Size = System::Drawing::Size(442, 182);
			   this->listView1->TabIndex = 1;
			   this->listView1->UseCompatibleStateImageBehavior = false;
			   this->listView1->View = System::Windows::Forms::View::Details;
			   this->listView1->DoubleClick += gcnew System::EventHandler(this, &Okno_konta::listView1_DoubleClick);
			   // 
			   // columnHeader1
			   // 
			   this->columnHeader1->Text = L"Ulubione Przepisy";
			   this->columnHeader1->Width = 283;
			   // 
			   // columnHeader2
			   // 
			   this->columnHeader2->Width = 154;
			   // 
			   // button1
			   // 
			   this->button1->Location = System::Drawing::Point(404, 334);
			   this->button1->Name = L"button1";
			   this->button1->Size = System::Drawing::Size(113, 23);
			   this->button1->TabIndex = 2;
			   this->button1->Text = L"usuñ z ulubionych";
			   this->button1->UseVisualStyleBackColor = true;
			   this->button1->Click += gcnew System::EventHandler(this, &Okno_konta::button1_Click);
			   // 
			   // button2
			   // 
			   this->button2->Location = System::Drawing::Point(507, 391);
			   this->button2->Name = L"button2";
			   this->button2->Size = System::Drawing::Size(75, 23);
			   this->button2->TabIndex = 3;
			   this->button2->Text = L"wyloguj";
			   this->button2->UseVisualStyleBackColor = true;
			   this->button2->Click += gcnew System::EventHandler(this, &Okno_konta::button2_Click);
			   // 
			   // button3
			   // 
			   this->button3->Location = System::Drawing::Point(13, 13);
			   this->button3->Name = L"button3";
			   this->button3->Size = System::Drawing::Size(26, 23);
			   this->button3->TabIndex = 4;
			   this->button3->Text = L"X";
			   this->button3->UseVisualStyleBackColor = true;
			   this->button3->Click += gcnew System::EventHandler(this, &Okno_konta::button3_Click);
			   // 
			   // button4
			   // 
			   this->button4->Location = System::Drawing::Point(75, 381);
			   this->button4->Name = L"button4";
			   this->button4->Size = System::Drawing::Size(144, 23);
			   this->button4->TabIndex = 5;
			   this->button4->Text = L"wybierz jednostkê energii";
			   this->button4->UseVisualStyleBackColor = true;
			   this->button4->Click += gcnew System::EventHandler(this, &Okno_konta::button4_Click);
			   // 
			   // Okno_konta
			   // 
			   this->AutoScaleDimensions = System::Drawing::SizeF(6, 13);
			   this->AutoScaleMode = System::Windows::Forms::AutoScaleMode::Font;
			   this->ClientSize = System::Drawing::Size(594, 426);
			   this->Controls->Add(this->button4);
			   this->Controls->Add(this->button3);
			   this->Controls->Add(this->button2);
			   this->Controls->Add(this->button1);
			   this->Controls->Add(this->listView1);
			   this->Controls->Add(this->label1);
			   this->FormBorderStyle = System::Windows::Forms::FormBorderStyle::None;
			   this->Name = L"Okno_konta";
			   this->Text = L"Okno_konta";
			   this->ResumeLayout(false);
			   this->PerformLayout();

		   }
#pragma endregion

		   //przycisk wyloguj
	private: System::Void button2_Click(System::Object^ sender, System::EventArgs^ e) {
		Uzytkownik::getInstUzyt().wyloguj();
		this->Close();
	}

		   //przycisk X
	private: System::Void button3_Click(System::Object^ sender, System::EventArgs^ e) {
		this->Close();
	}

		   //usuñ z ulubionych
	private: System::Void button1_Click(System::Object^ sender, System::EventArgs^ e) {
		if (listView1->SelectedItems->Count > 0) {
			label1->Text = listView1->SelectedItems[0]->Text;
			Uzytkownik::getInstUzyt().usun_z_ulubionych(msclr::interop::marshal_as<std::string>(listView1->SelectedItems[0]->Text));
			Uzytkownik::getInstUzyt().setZmiana_u();
			listView1->Items->Remove(listView1->SelectedItems[0]);
			MessageBox::Show("Usuniêto z ulubionych");
		}
	}

		   //wybierz jednostke energii
	private: System::Void button4_Click(System::Object^ sender, System::EventArgs^ e) {
		if (MessageBox::Show("Czy chcesz aby jednostk¹ by³y kcal?\nTak-kcal.\nNie, wolê kJ.", "Jednostka", MessageBoxButtons::YesNo) == System::Windows::Forms::DialogResult::Yes) {
			Kalkulator::getInstKalk().wybor(std::make_shared<Kcal>());
			Kalkulator::getInstKalk().wyborK();
			columnHeader2->Text = "Kcal";

		}
		else {
			Kalkulator::getInstKalk().wybor(std::make_shared<kJ>());
			Kalkulator::getInstKalk().wyborJ();
			columnHeader2->Text = "kJ";

		}
		for (int i = 0; i < Uzytkownik::getInstUzyt().getUlubione().size(); ++i) {
			listView1->Items[i]->SubItems[1]->Text = gcnew String("" + Kalkulator::getInstKalk().oblicz(Uzytkownik::getInstUzyt().getUlubione()[i]));
		}
	}

		   //podwojne klikniecie na przepis z ulubionych
	private: System::Void listView1_DoubleClick(System::Object^ sender, System::EventArgs^ e) {
		int index = Przepisy::getInstPrzepisy().czy_istnieje_nazwa(msclr::interop::marshal_as<std::string>(listView1->SelectedItems[0]->Text));
		Okno_listy_zakupow^ okno_l_z = gcnew Okno_listy_zakupow(Przepisy::getInstPrzepisy().getPrzepisy()[index]);
		okno_l_z->ShowDialog();
	}
	};
}