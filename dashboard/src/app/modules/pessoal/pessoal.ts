import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { PessoalService } from '../../services/pessoal/pessoal';
import { Pessoal } from './models/pessoal.interface';
import { AppModule } from '../../app';
import { Router } from '@angular/router';

@Component({
  selector: 'app-pessoal',
  standalone: true,
  imports: [
    FormsModule,
    CommonModule
  ],
  templateUrl: './pessoal.html',
  styleUrls: ['./pessoal.less'],
})
export class PessoalComponent {
  constructor(
    private pessoalService: PessoalService,
    public global: AppModule,
    private router: Router
  ) {}

  ngOnInit(){
    if(this.global.logou == true){
      
      if(this.global.selecionou){
        this.obterDados(this.global.nome, this.global.nCartao)
        this.nome = this.global.nome
        this.nCartao = this.global.nCartao
      }
    }else{
      this.router.navigate([""])
    }
  }

  nome: any;
  nCartao: any;
  vlCompra: any;
  nomeUsur: any;
  nCartaoUsur: any;
  cvv: any;
  validade: any;
  tpPag: any;
  nOperacoes: any;

  mensagem: string = ""

  totalTransacoes: number = 0;

  mostraInfos = false;

  pessoal: Pessoal[] = [];

  public limparInfo() {
    this.mostraInfos = false;
    this.nCartao = '';
    this.nome = '';
  }

  public async obterDados(nome:any, numeroCartao:any) {
    this.nomeUsur = "";
    this.nCartaoUsur = "";
    this.cvv = "";
    this.validade = "";
    this.nOperacoes = 0;
    this.totalTransacoes = 0;

    await this.pessoalService.buscarEspec(nome, numeroCartao).subscribe({
      next: (dados) => {
        this.pessoal = dados;
        this.mensagem = ""


        if(this.pessoal.length > 0){

          
          let debito = 0;
          let credito = 0;
          
          this.nomeUsur = this.pessoal[0].nome;
          this.nCartaoUsur = this.pessoal[0].numeroDoCartao;
          this.cvv = this.pessoal[0].codigoDeSeguranca;
          this.validade = this.pessoal[0].validade;
          this.nOperacoes = this.pessoal.length;
          
          this.pessoal.forEach((dado) => {
            this.totalTransacoes += dado.valor;
            
            if (dado.formaDePagamentoId == 1) {
              credito++;
            } else {
              debito++;
            }
          });
          
          this.tpPag = debito < credito ? 'Crédito' : 'Débito';
          
          this.mostraInfos = true;
        }else{
          this.mostraInfos = false;
          this.mensagem = "Nenhum Usuario encontrado"
        }
      },
      error: (erro) => {
        this.mensagem = erro.error.error
      },
    });
  }
}
