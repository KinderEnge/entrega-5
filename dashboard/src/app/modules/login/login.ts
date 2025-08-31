import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AppModule } from '../../app';
import { Router } from '@angular/router';
import { LoginService } from '../../services/login/login';
import { CommonModule } from '@angular/common';


@Component({
  selector: 'app-login',
  imports: [FormsModule, CommonModule],
  templateUrl: './login.html',
  styleUrl: './login.less',
})
export class LoginComponent {
  constructor(private loginService: LoginService, public global: AppModule, private router: Router) {}
  
  username = '';
  password = '';
  message = { type: '', text: '' };

  invalido: boolean = false
  
  ngOnInit() {}
  

  login() {
    if (!this.username || !this.password) {
      this.message = { type: 'error', text: 'Informe usuário e senha' };
      return;
    }

    this.loginService.validateCredentials(this.username, this.password).subscribe({
      next: () => {
        this.global.logou = true
        this.router.navigate(["/home"])
      },
      error: () => {
        this.invalido = true
      },
    });
  }
}
