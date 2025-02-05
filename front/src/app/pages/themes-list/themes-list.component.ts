import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Theme } from 'src/app/interfaces/theme.interface';
import { ThemeService } from 'src/app/services/theme.service';

@Component({
  selector: 'app-themes-list',
  templateUrl: './themes-list.component.html',
  styleUrls: ['./themes-list.component.scss']
})
export class ThemesListComponent implements OnInit {

  protected themes: Theme[] = [];

  constructor(private router: Router, private themeService: ThemeService) { }

  ngOnInit(): void {
      this.themeService.getAll().subscribe((data: any) => {
        this.themes = data.themes;
      });
    }
  
    subscribe(theme: Theme){
      this.themeService.subscribe(theme.id).subscribe(() => {
        window.location.reload();
      });
    }

    unsubscribe(theme: Theme){
      this.themeService.unsubscribe(theme.id).subscribe(() => {
        window.location.reload();
      });
    }

}
