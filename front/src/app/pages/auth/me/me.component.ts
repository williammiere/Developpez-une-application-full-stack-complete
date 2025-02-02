import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { SessionInformation } from 'src/app/interfaces/sessionInformation.interface';
import { UpdateRequest } from 'src/app/interfaces/updateRequest';
import { AuthService } from 'src/app/services/auth.service';
import { SessionService } from 'src/app/services/session.service';

@Component({
  selector: 'app-me',
  templateUrl: './me.component.html',
  styleUrls: ['./me.component.scss']
})
export class MeComponent implements OnInit {

  constructor(private router: Router,
      private fb: FormBuilder,
      private authService: AuthService,
      private sessionService: SessionService) { }

  public onError = '';
  public user = this.sessionService.sessionInformation;
  
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
      ]
    });

  ngOnInit(): void {
    this.form.patchValue(this.user ?? {});
  }

  submit() {
    if(this.form.valid) {
    const updateRequest = this.form.value as UpdateRequest;
    this.authService.update(updateRequest).subscribe({
      next: (response: SessionInformation) => {
        this.sessionService.update(response);
        window.location.reload();
      },
      error: error => this.onError = error.error,
    });
  }
}

logOut() {
  this.sessionService.logOut();
  this.router.navigate(['/']);
}
}
