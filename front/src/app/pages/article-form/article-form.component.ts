import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ArticleRequest } from 'src/app/interfaces/ArticleRequest.interface';
import { articleService } from 'src/app/services/article.service';
import { SessionService } from 'src/app/services/session.service';

@Component({
  selector: 'app-article-form',
  templateUrl: './article-form.component.html',
  styleUrls: ['./article-form.component.scss']
})
export class ArticleFormComponent implements OnInit {

  constructor(private router: Router,
    private fb: FormBuilder, private articleService: articleService, private sessionService: SessionService) { }

  public onError = '';

  public form = this.fb.group({
    theme: [
      '',
      [
        Validators.required,
        Validators.minLength(1)
      ]
    ],
    title: [
      '',
      [
        Validators.required,
        Validators.minLength(1)
      ]
    ],
    content: [
      '',
      [
        Validators.required,
        Validators.minLength(1)
      ]
    ]
  });

  ngOnInit(): void {
  }

  back() {
    this.router.navigate(['/articles']);
  }

  submit() {
    if (this.form.valid) {
      const article = this.form.value as ArticleRequest;
      const user = this.sessionService.sessionInformation;
      article.author = user?.id ?? -1;
      if (user?.id !== -1 && user != undefined) {
        {
          this.articleService.create(article).subscribe({
            next: (data) => {
              this.router.navigate(['/articles']);
            },
            error: (error) => {
              this.onError = error.error.message;
            }
          });
        }
      }
    }
  }
}
