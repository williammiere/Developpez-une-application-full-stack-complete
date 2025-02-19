import { HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { SessionService } from '../services/session.service';

@Injectable({ providedIn: 'root' })
export class JwtInterceptor implements HttpInterceptor {
  constructor(private sessionService: SessionService) {}

  public intercept(request: HttpRequest<any>, next: HttpHandler) { // We intercept the request
    const token = JSON.parse(localStorage.getItem('session') || "{}")?.token; // We get the token from the local storage
    if (token) {
      request = request.clone({ // We clone the request and add the token to the headers
        setHeaders: {
          Authorization: `Bearer ${token}`,
        },
      });
    }
    return next.handle(request);
  }
}
