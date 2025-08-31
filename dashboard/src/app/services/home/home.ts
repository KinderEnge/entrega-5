import { Injectable } from '@angular/core';
import { Pessoal } from '../../modules/pessoal/models/pessoal.interface';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class HomeService {
  private apiUrl = 'http://localhost:8080/pagamentos';

  constructor(private http: HttpClient) {}

  obterDados(): Observable<Pessoal[]> {
    return this.http.get<Pessoal[]>(this.apiUrl);
  }
}
