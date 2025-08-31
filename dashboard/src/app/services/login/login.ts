import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  private apiUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  validateCredentials(username: string, password: string): Observable<any> {
    const token = btoa(`${username}:${password}`);
    const headers = new HttpHeaders({
      Authorization: `Basic ${token}`,
    });

    return this.http
      .get(`${this.apiUrl}/auth/validate`, {
        headers,
        responseType: 'text', // 👈 isso aqui resolve o problema
      })
      .pipe(catchError((err) => throwError(() => err)));
  }
}
