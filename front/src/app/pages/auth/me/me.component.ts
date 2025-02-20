import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { SessionInformation } from 'src/app/interfaces/sessionInformation.interface';
import { Theme } from 'src/app/interfaces/theme.interface';
import { UpdateRequest } from 'src/app/interfaces/updateRequest.interface';
import { AuthService } from 'src/app/services/auth.service';
import { SessionService } from 'src/app/services/session.service';
import { ThemeService } from 'src/app/services/theme.service';

@Component({
  selector: 'app-me',
  templateUrl: './me.component.html',
  styleUrls: ['./me.component.scss']
})
export class MeComponent implements OnInit, OnDestroy {

  constructor(private router: Router,
    private fb: FormBuilder,
    private authService: AuthService,
    private sessionService: SessionService,
    private themeService: ThemeService) { }

  public onError = '';
  public user = this.sessionService.sessionInformation;
  public themes: Theme[] = [];
  subscriptions: Subscription[] = [];
  public passPopup = false;
  public password = null;

  public form = this.fb.group({
    email: [
      '',
      [
        Validators.required,
        Validators.email
      ]
    ],
    username: [
      '',
      [
        Validators.required,
        Validators.minLength(3)
      ]
    ],
    newPassword: [
      '',
      [
        Validators.minLength(8)
      ]
    ],
  });

  ngOnInit(): void {
    this.form.patchValue(this.user ?? {}); // We fill the form with the user information
    this.subscriptions.push(this.themeService.getSubscribed().subscribe((response: any) => { // We get the subscribed themes
      response.forEach((theme: Theme) => {
        this.themes.push(theme);
      });
    }));
  }

  ngOnDestroy(): void {
    if (this.subscriptions) {
      this.subscriptions.forEach(subscription => {
        subscription.unsubscribe();
      });
    }
  }

  submit(): void {
    if (this.form.valid) {
      if (this.form.value.newPassword && this.form.value.newPassword !== '') {
        const password = this.form.value.newPassword;
        const pattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[^A-Za-z0-9]).+$/; // We check if the password contains at least one lowercase, one uppercase, one number and one special character
        if (!pattern.test(password)) {
          this.onError = 'Le mot de passe doit contenir au moins une majuscule, une minuscule, un chiffre et un caractère spécial.';
          return;
        }
      }
      this.passPopup = true;
    }else{
      this.onError = 'Le nom d\'utilisateur doit contenir au moins 3 caractères et l\'email doit être valide.';
    }
  }

  valid(): void {
    this.passPopup = false;
    const updateRequest = this.form.value as UpdateRequest;
    updateRequest.password = this.password;
    updateRequest.newPassword = this.form.value.newPassword ? this.form.value.newPassword : null; // We check if the new password is empty
    this.subscriptions.push(this.authService.update(updateRequest).subscribe({ // We update the user
      next: (response: SessionInformation) => {
        this.sessionService.update(response);
        window.location.reload();
      },
      error: error => this.onError = error.error,
    }));
  }

  cancel(): void {
    this.passPopup = false;
  }

  logOut(): void {
    this.sessionService.logOut();
    this.router.navigate(['/']);
  }

  unsubscribe(theme: Theme): void {
    this.subscriptions.push(this.themeService.unsubscribe(theme.id).subscribe(() => {
      window.location.reload();
    }));
  }
}
