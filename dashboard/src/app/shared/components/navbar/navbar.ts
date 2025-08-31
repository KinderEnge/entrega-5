import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { RouterModule } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'dash-navbar',
  imports: [RouterLink, RouterLinkActive,RouterModule],
  templateUrl: './navbar.html',
  styleUrl: './navbar.less'
})
export class Navbar {
}
