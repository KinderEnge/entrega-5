import { Component } from '@angular/core';
import { CardComponent } from './components/card/card';
import { CommonModule } from '@angular/common';
import { HomeService } from '../../services/home/home';
import { Pessoal } from '../pessoal/models/pessoal.interface';
import { AppModule } from '../../app';
import { Router } from '@angular/router';

@Component({
  selector: 'dash-home',
  imports: [CardComponent, CommonModule],
  templateUrl: './home.html',
  styleUrl: './home.less',
})
export class HomeComponet {
  constructor(private homeService: HomeService, public global: AppModule, private router: Router) {}

  ngOnInit() {
    if(this.global.logou == true){
    this.obterDados();
    }else{
      this.router.navigate([""])
    }
  }

  pessoal: Pessoal[] = [];
  transRecente: number = 0;
  maiorTransacao: number = 0;
  totalTransacoes: number = 0;
  totalTransacoesFeitas: number = 0;

  public obterDados() {
    this.homeService.obterDados().subscribe({
      next: (dados) => {
        this.pessoal = dados;

        this.totalTransacoesFeitas = this.pessoal.length;
        this.maiorTransacao = this.pessoal[1].valor;

        const maisRecente = this.pessoal.reduce((maisNova, atual) =>
          new Date(atual.transactionDate) > new Date(maisNova.transactionDate) ? atual : maisNova
        );

        this.transRecente = maisRecente.valor;

        this.pessoal.forEach((pessoa) => {
          this.totalTransacoes += pessoa.valor;

          if (this.maiorTransacao < pessoa.valor) {
            this.maiorTransacao = pessoa.valor;
          }

          pessoa.transactionDate = formatarData(pessoa.transactionDate); // 21/08/2025
        });
      },
      error: (erro) => {
        console.error('Erro ao buscar dados:', erro);
      },
    });
  }

  public selecionarUsuario(nome: string, numeroCartao: number) {
    this.global.nome = nome;
    this.global.nCartao = numeroCartao.toString();
    this.global.selecionou = true;

    this.router.navigate(["/pessoal"]);
  }

  public formatarParaReal(valor: number): string {
    return valor.toLocaleString('pt-BR', {
      style: 'currency',
      currency: 'BRL',
    });
  }
}

function formatarData(dataISO: string): string {
  const [ano, mes, dia] = dataISO.split('-');
  return `${dia}/${mes}/${ano}`;
}
