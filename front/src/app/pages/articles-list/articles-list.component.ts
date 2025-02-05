import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { articleService } from 'src/app/services/article.service';
import { Article } from 'src/app/interfaces/article.interface';
import { SessionInformation } from 'src/app/interfaces/sessionInformation.interface';
import { ThemeService } from 'src/app/services/theme.service';

@Component({
  selector: 'app-articles-list',
  templateUrl: './articles-list.component.html',
  styleUrls: ['./articles-list.component.scss']
})
export class ArticlesListComponent implements OnInit {

  protected articles: Article[] = [];
  protected subscription: string[] = [];

  constructor(private router: Router, private articleService: articleService, private themeService: ThemeService) { }

  ngOnInit(): void {
    this.themeService.getSubscribed().subscribe((data: any) => {
      this.subscription = data;
      this.articleService.getAll().subscribe((data: any) => {
        for (let article of data.articles) {
          if (this.subscription.includes(article.theme)) {
            this.articles.push(article);
          }
        }
      });
    });
  }

  create() {
    this.router.navigate(['/articleForm']);
  }

  read(article: Article) {
    this.router.navigate(['/article/' + article.id]);
  }
}
