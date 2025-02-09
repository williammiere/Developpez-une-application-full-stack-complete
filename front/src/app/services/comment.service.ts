import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { CommentRequest } from '../interfaces/commentRequest.interface';

@Injectable({
    providedIn: 'root'
  })

export class CommentService{
    private pathService = environment.baseUrl + 'comment';

    constructor(private httpClient: HttpClient) { }

    public send(commentRequest: CommentRequest): Observable<any> {
        return this.httpClient.post<any>(`${this.pathService}/send`, commentRequest);
    }

    public getComments(id: string): Observable<Comment[]> {
        return this.httpClient.get<Comment[]>(`${this.pathService}s/${id}`);
    }
}