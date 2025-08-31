import { Routes } from '@angular/router';
import {HomeComponet} from './modules/home/home'
import { PessoalComponent } from './modules/pessoal/pessoal';
import { TransacoesComponent } from './modules/transacoes/transacoes';
import { LoginComponent } from './modules/login/login';

export const routes: Routes = [
    {path: '', component: LoginComponent},
    {path: 'home', component: HomeComponet},
    {path: 'pessoal', component: PessoalComponent},
    {path: 'transacoes', component: TransacoesComponent}
];
