import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full',
  },
  {
    path: 'login',
    loadComponent: () => import('./pages/login/login.page').then((m) => m.LoginPage),
  },
  {
    path: 'home-chefe',
    loadComponent: () => import('./pages/home-chefe/home-chefe.page').then((m) => m.HomeChefePage),
  },
  {
    path: 'home-eleitor',
    loadComponent: () => import('./pages/home-eleitor/home-eleitor.page').then((m) => m.HomeEleitorPage),
  },
  {
    path: 'home-candidato',
    loadComponent: () => import('./pages/home-candidato/home-candidato.page').then((m) => m.HomeCandidatoPage),
  },
];