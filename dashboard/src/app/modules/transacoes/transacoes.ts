import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AppModule } from '../../app';

@Component({
  selector: 'app-transacoes',
  imports: [],
  templateUrl: './transacoes.html',
  styleUrl: './transacoes.less'
})
export class TransacoesComponent {
  constructor(private router: Router, public global: AppModule){}

  ngOnInit(){
    if(this.global.logou == false){
      this.deslogar()
    }
  }

  voltaHome(){
    this.router.navigate(["/home"])
  }

  deslogar(){
    this.global.logou = false
    this.router.navigate([""])
  }
}
