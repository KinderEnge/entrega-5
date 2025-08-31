import { Component, signal } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { Navbar } from "./shared/components/navbar/navbar";
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-root',
  imports: [
    // CommonModule,
    RouterOutlet, 
    Navbar
  ],
  templateUrl: './app.html',
  styleUrl: './app.less'
})
export class AppModule {
  protected readonly title = signal('dashboard-pagamento-mindmatch');

  constructor(
    public router: Router
  ){}


  ngOnInit(){
  }

  nome: string = "";
  nCartao: string = "";

  selecionou: boolean = false
  logou: boolean = false
}
