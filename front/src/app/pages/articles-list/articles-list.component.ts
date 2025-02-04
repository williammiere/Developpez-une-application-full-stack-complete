import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { articleService } from 'src/app/services/article.service';
import { Article } from 'src/app/interfaces/article.interface';

@Component({
  selector: 'app-articles-list',
  templateUrl: './articles-list.component.html',
  styleUrls: ['./articles-list.component.scss']
})
export class ArticlesListComponent implements OnInit {

  protected articles: Article[] = [];

  constructor(private router: Router, private articleService: articleService) { }

  ngOnInit(): void {
    this.articleService.getAll().subscribe((data: any) => {
      this.articles = data.articles;
    });
  }

  create(){
    this.router.navigate(['/articleForm']);
  }

  read(article: Article){
    this.router.navigate(['/article/'+article.id]);
  }
}
