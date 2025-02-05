import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Article } from '../interfaces/article.interface';
import { ArticleRequest } from '../interfaces/articleRequest.interface';

@Injectable({
  providedIn: 'root'
})
export class articleService {

  private pathService = environment.baseUrl+'article';

  constructor(private httpClient: HttpClient) { }

  public create(articleRequest: ArticleRequest): Observable<any> {
    return this.httpClient.post<any>(`${this.pathService}/create`, articleRequest);
  }

  public getAll(): Observable<Article[]> {
    return this.httpClient.get<Article[]>(`${this.pathService}s`);
  }

}