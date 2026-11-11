export type Perfil = 'CHEFE' | 'ELEITOR' | 'CANDIDATO';

export interface NovoUsuario {
  nome: string;
  login: string;
  senha: string;
  perfil: Perfil;
}

export interface Usuario extends NovoUsuario {
  id: number;
}