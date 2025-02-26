import { Component, OnInit } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  menuOpen: boolean = false;
  activeRoute: string = '';

  constructor(private router: Router) {
    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.activeRoute = event.url;
      }
    });
  }

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
