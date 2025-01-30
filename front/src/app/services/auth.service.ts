import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LoginRequest } from '../interfaces/loginRequest.interface';
import { RegisterRequest } from '../interfaces/registerRequest.interface';
import { SessionInformation } from 'src/app/interfaces/sessionInformation.interface';
import { environment } from 'src/environments/environment';
import { UpdateRequest } from '../interfaces/updateRequest';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private pathService = environment.baseUrl+'auth';

  constructor(private httpClient: HttpClient) { }

  public register(registerRequest: RegisterRequest): Observable<SessionInformation> {
    return this.httpClient.post<SessionInformation>(`${this.pathService}/register`, registerRequest);
  }

  public login(loginRequest: LoginRequest): Observable<SessionInformation> {
    return this.httpClient.post<SessionInformation>(`${this.pathService}/login`, loginRequest);
  }

  public update(updateRequest: UpdateRequest): Observable<void> {
    return this.httpClient.put<void>(`${this.pathService}/update`, updateRequest);
  }
}