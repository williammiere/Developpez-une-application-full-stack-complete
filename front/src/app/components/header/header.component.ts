import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
  
  menuOpen: boolean = false;

  constructor(private router: Router) { }

  ngOnInit(): void {
  }

  home(): void {
    this.router.navigate(['/']);
  }

  articles(): void {
    this.router.navigate(['/articles']);
  }

  themes(): void {
    this.router.navigate(['/themes']);
  }

  me(): void {
    this.router.navigate(['/me']);
  }

  toggleMenu(): void {
    this.menuOpen = !this.menuOpen;
  }
}
