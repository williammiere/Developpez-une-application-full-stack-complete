import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit(): void {
  }

  home() {
    this.router.navigate(['/']);
  }

  articles() {
    this.router.navigate(['/articles']);
  }

  themes() {
    this.router.navigate(['/themes']);
  }

  me() {
    this.router.navigate(['/me']);
  }

}
