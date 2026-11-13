import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { IonContent, IonHeader, IonTitle, IonToolbar } from '@ionic/angular';

@Component({
  selector: 'app-home-eleitor',
  templateUrl: './home-eleitor.page.html',
  styleUrls: ['./home-eleitor.page.scss'],
  imports: [IonContent, IonHeader, IonTitle, IonToolbar, CommonModule, FormsModule],
})
export class HomeEleitorPage implements OnInit {
  constructor() {}

  ngOnInit() {}
}
