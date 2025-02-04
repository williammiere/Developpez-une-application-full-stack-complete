import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { SessionInformation } from 'src/app/interfaces/sessionInformation.interface';
import { environment } from 'src/environments/environment';
import { Article } from '../interfaces/article.interface';
import { ArticleRequest } from '../interfaces/ArticleRequest.interface';

@Injectable({
  providedIn: 'root'
})
export class articleService {

  private pathService = environment.baseUrl+'article';

  constructor(private httpClient: HttpClient) { }

  public create(articleRequest: ArticleRequest): Observable<void> {
    return this.httpClient.post<void>(`${this.pathService}/create`, articleRequest);
  }

  public getAll(): Observable<Article[]> {
    return this.httpClient.get<Article[]>(`${this.pathService}/articles`);
  }

}