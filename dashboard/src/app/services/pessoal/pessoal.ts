import { Injectable } from '@angular/core';
import { Pessoal } from '../../modules/pessoal/models/pessoal.interface';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class PessoalService {
  private apiUrl = 'http://localhost:8080/pagamentos';

  constructor(private http: HttpClient) {}

  // buscaEspec(filtro: { nome?: string; numeroDoCartao?: string }): Observable<Pessoal[]> {
  buscarEspec(nome: string | null, codigoDoCartao: string | null): Observable<Pessoal[]> {
    const payload: any = {};

    if (nome) payload.nome = nome;
    if (codigoDoCartao) payload.numeroDoCartao = codigoDoCartao;

    return this.http.post<Pessoal[]>(`${this.apiUrl}/forms`, payload);
  }
}
