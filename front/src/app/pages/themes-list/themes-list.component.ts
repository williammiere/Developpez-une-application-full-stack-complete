import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Theme } from 'src/app/interfaces/theme.interface';
import { ThemeService } from 'src/app/services/theme.service';

@Component({
  selector: 'app-themes-list',
  templateUrl: './themes-list.component.html',
  styleUrls: ['./themes-list.component.scss']
})
export class ThemesListComponent implements OnInit, OnDestroy {

  protected themes: Theme[] = [];
  subscriptions: Subscription[] = [];

  constructor(private router: Router, private themeService: ThemeService) { }

  ngOnDestroy(): void {
    for(var subscription of this.subscriptions){
      subscription.unsubscribe();
    }
  }

  ngOnInit(): void {
      this.subscriptions.push(this.themeService.getAll().subscribe((data: any) => {
        this.themes = data.themes;
      }));
    }
  
    subscribe(theme: Theme): void{
      this.subscriptions.push(this.themeService.subscribe(theme.id).subscribe(() => {
        window.location.reload();
      }));
    }

    unsubscribe(theme: Theme): void{
      this.subscriptions.push(this.themeService.unsubscribe(theme.id).subscribe(() => {
        window.location.reload();
      }));
    }

}
