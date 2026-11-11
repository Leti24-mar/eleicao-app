import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { IonContent, IonHeader, IonTitle, IonToolbar } from '@ionic/angular';

@Component({
  selector: 'app-home-candidato',
  templateUrl: './home-candidato.page.html',
  styleUrls: ['./home-candidato.page.scss'],
  imports: [IonContent, IonHeader, IonTitle, IonToolbar, CommonModule, FormsModule],
})
export class HomeCandidatoPage implements OnInit {
  constructor() {}

  ngOnInit() {}
}
