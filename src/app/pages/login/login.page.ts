import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { 
  IonHeader, IonToolbar, IonTitle, IonContent, IonItem, 
  IonLabel, IonInput, IonButton 
} from '@ionic/angular';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.page.html',
  styleUrls: ['./login.page.scss'],
  standalone: true,
  imports: [
    CommonModule, FormsModule, 
    IonHeader, IonToolbar, IonTitle, IonContent, IonItem, 
    IonLabel, IonInput, IonButton
  ]
})
export class LoginPage {
  login = '';
  senha = '';
  erro = '';
  carregando = false;

  constructor(
    private authService: AuthService, 
    private router: Router
  ) {}

  entrar() {
    this.erro = '';
    this.carregando = true;

    this.authService.login(this.login, this.senha).subscribe({
      next: (usuario: any) => {
        this.carregando = false;
        if (usuario.perfil === 'CHEFE') {
          this.router.navigateByUrl('/home-chefe');
        } else if (usuario.perfil === 'ELEITOR') {
          this.router.navigateByUrl('/home-eleitor');
        } else {
          this.router.navigateByUrl('/home-candidato');
        }
      },
      error: (err: any) => {
        this.carregando = false;
        this.erro = err.message || 'Falha na comunicação com a API';
      }
    });
  }
}