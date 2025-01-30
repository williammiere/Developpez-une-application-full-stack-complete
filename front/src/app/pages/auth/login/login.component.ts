import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, Validators } from '@angular/forms';
import { SessionService } from 'src/app/services/session.service';
import { AuthService } from 'src/app/services/auth.service';
import { SessionInformation } from 'src/app/interfaces/sessionInformation.interface';
import { LoginRequest } from 'src/app/interfaces/loginRequest.interface';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent{
  public onError = '';

  public form = this.fb.group({
    email: [
      '',
      [
        Validators.required,
        Validators.email
      ]
    ],
    password: [
      '',
      [
        Validators.required,
        Validators.minLength(8)
      ]
    ]
  });

  constructor(private router: Router,
    private fb: FormBuilder,
    private authService: AuthService,
    private sessionService: SessionService)
 {}

  back() {
    this.router.navigate(['/']);
  }

  submit() {
    const loginRequest = this.form.value as LoginRequest;
    this.authService.login(loginRequest).subscribe({
      next: (response: SessionInformation) => {
        localStorage.setItem('token', response.token);
        this.sessionService.logIn(response);
        this.router.navigate(['/me']);
      },
      error: error => this.onError = error.error,
    });
  }

}
