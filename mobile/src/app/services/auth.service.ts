import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, map, Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Usuario } from '../models/usuario.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = environment.apiUrl;
  private usuarioAtual = new BehaviorSubject<Usuario | null>(null);

  constructor(private http: HttpClient) {
    const salvo = localStorage.getItem('usuario');
    if (salvo) {
      this.usuarioAtual.next(JSON.parse(salvo));
    }
  }

  login(login: string, senha: string): Observable<Usuario> {
    return this.http.get<Usuario[]>(`${this.apiUrl}/usuarios?login=${login}&senha=${senha}`).pipe(
      map(lista => {
        if (!lista || lista.length === 0) {
          throw new Error('Login ou senha inválidos');
        }
        const user = lista[0];
        localStorage.setItem('usuario', JSON.stringify(user));
        this.usuarioAtual.next(user);
        return user;
      })
    );
  }

  logout() {
    localStorage.removeItem('usuario');
    this.usuarioAtual.next(null);
  }

  get usuarioLogado() {
    return this.usuarioAtual.asObservable();
  }
}