import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ArticleService } from 'src/app/services/article.service';
import { Article } from 'src/app/interfaces/article.interface';
import { SessionInformation } from 'src/app/interfaces/sessionInformation.interface';
import { ThemeService } from 'src/app/services/theme.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-articles-list',
  templateUrl: './articles-list.component.html',
  styleUrls: ['./articles-list.component.scss']
})
export class ArticlesListComponent implements OnInit, OnDestroy {

  protected articles: Article[] = [];
  protected subscriptions: string[] = [];
  protected croissant: boolean = false;
  subscription!: Subscription;

  constructor(private router: Router, private articleService: ArticleService, private themeService: ThemeService) { }

  ngOnDestroy(): void {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }

  ngOnInit(): void {
    this.subscription = this.articleService.getAll().subscribe((data: any) => { // We get all the articles
      this.articles = data.articles;
    });
  }

  create(): void {
    this.router.navigate(['/articleForm']);
  }

  read(article: Article): void {
    this.router.navigate(['/article/' + article.id]);
  }

  tri(): void {
    this.croissant = !this.croissant;
    this.articles.sort((a, b) => {
      return this.croissant ? a.title.localeCompare(b.title) : b.title.localeCompare(a.title); // We sort the articles by title
    });
  }
}
