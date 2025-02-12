import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { ArticleRequest } from 'src/app/interfaces/articleRequest.interface';
import { Theme } from 'src/app/interfaces/theme.interface';
import { ArticleService } from 'src/app/services/article.service';
import { SessionService } from 'src/app/services/session.service';
import { ThemeService } from 'src/app/services/theme.service';

@Component({
  selector: 'app-article-form',
  templateUrl: './article-form.component.html',
  styleUrls: ['./article-form.component.scss']
})
export class ArticleFormComponent implements OnInit, OnDestroy {

  protected themes: string[] = [];

  constructor(private router: Router,
    private fb: FormBuilder, private articleService: ArticleService, private sessionService: SessionService, private themeService: ThemeService) { }

  public onError = '';
  subscriptions: Subscription[] = [];

  public form = this.fb.group({
    theme: [
      '',
      [
        Validators.required,
      ]
    ],
    title: [
      '',
      [
        Validators.required,
        Validators.minLength(2)
      ]
    ],
    content: [
      '',
      [
        Validators.required,
        Validators.minLength(2)
      ]
    ]
  });

  ngOnInit(): void {
    this.subscriptions.push(this.themeService.getAll().subscribe((response: any) => {
      response.themes.forEach((theme: Theme) => {
        this.themes.push(theme.name);
      });
    }));
  }

  ngOnDestroy(): void {
    if (this.subscriptions) {
      this.subscriptions.forEach(subscription => {
        subscription.unsubscribe();
      });
    }
  }

  back(): void {
    this.router.navigate(['/articles']);
  }

  submit(): void {
    if (this.form.valid) {
      const article = this.form.value as ArticleRequest;
      const user = this.sessionService.sessionInformation;
      article.author = user?.id ?? -1;
      if (user?.id !== -1 && user != undefined) {
        {
          this.subscriptions.push(this.articleService.create(article).subscribe({
            next: (data) => {
              this.router.navigate(['/articles']);
            },
            error: (error) => {
              this.onError = error.error.message;
            }
          }));
        }
      }
    }else{
      this.onError = "Tous les champs doivent contenir au moins 2 caractères"
    }
  }
}
