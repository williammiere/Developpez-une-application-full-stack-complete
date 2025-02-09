import { Component, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, Validators } from '@angular/forms';
import { SessionService } from 'src/app/services/session.service';
import { AuthService } from 'src/app/services/auth.service';
import { SessionInformation } from 'src/app/interfaces/sessionInformation.interface';
import { RegisterRequest } from 'src/app/interfaces/registerRequest.interface';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnDestroy {
  public onError = '';
  subscription! : Subscription;

  public form = this.fb.group({
    username: [
      '',
      [
        Validators.required,
        Validators.minLength(3),
        Validators.maxLength(20)
      ]
    ],
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
    private sessionService: SessionService) { }

  back(): void {
    this.router.navigate(['/']);
  }

  ngOnDestroy(): void {
    if(this.subscription){
      this.subscription.unsubscribe();
    }
  }

  submit(): void {
    if (this.form.valid && this.form.value.password) {
      const password = this.form.value.password;
      const pattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[^A-Za-z0-9]).+$/;
      if (!pattern.test(password)) {
        this.onError = 'Le mot de passe doit contenir au moins une majuscule, une minuscule, un chiffre et un caractère spécial.';
        return;
      }
      const registerRequest = this.form.value as RegisterRequest;
      this.subscription = this.authService.register(registerRequest).subscribe({
        next: (response: SessionInformation) => {
          this.sessionService.logIn(response);
          this.router.navigate(['/me']);
        },
        error: (error) => {
          this.onError = error.error;
          console.log(error.error);
        },
      });
    } else {
      this.onError = 'Veuillez remplir tous les champs';
    }
  }

}
