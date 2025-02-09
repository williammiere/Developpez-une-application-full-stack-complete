import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { ArticleRequest } from 'src/app/interfaces/articleRequest.interface';
import { ArticleService } from 'src/app/services/article.service';
import { SessionService } from 'src/app/services/session.service';

@Component({
  selector: 'app-article-form',
  templateUrl: './article-form.component.html',
  styleUrls: ['./article-form.component.scss']
})
export class ArticleFormComponent implements OnInit, OnDestroy {

  constructor(private router: Router,
    private fb: FormBuilder, private articleService: ArticleService, private sessionService: SessionService) { }

  public onError = '';
  subscription! : Subscription;

  public form = this.fb.group({
    theme: [
      '',
      [
        Validators.required,
        Validators.minLength(2)
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
  }

  ngOnDestroy(): void {
    if(this.subscription){
      this.subscription.unsubscribe();
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
          this.subscription = this.articleService.create(article).subscribe({
            next: (data) => {
              this.router.navigate(['/articles']);
            },
            error: (error) => {
              this.onError = error.error.message;
            }
          });
        }
      }
    }else{
      this.onError = "Tous les champs doivent contenir au moins 2 caractères"
    }
  }
}
