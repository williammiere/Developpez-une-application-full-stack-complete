import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Theme } from '../interfaces/theme.interface';

@Injectable({
  providedIn: 'root'
})
export class ThemeService {

  private pathService = environment.baseUrl + 'theme';

  constructor(private httpClient: HttpClient) { }

  public getAll(): Observable<Theme[]> {
    return this.httpClient.get<Theme[]>(`${this.pathService}s`);
  }

  public getSubscribed(): Observable<string[]> {
    return this.httpClient.get<string[]>(`${this.pathService}s/subscribed`);
  }

  public subscribe(themeId: number): Observable<any> {
    return this.httpClient.post<any>(`${this.pathService}/subscribe/` + themeId, {});
  }

  public unsubscribe(themeId: number): Observable<any> {
    return this.httpClient.post<any>(`${this.pathService}/unsubscribe/` + themeId, {});
  }
}