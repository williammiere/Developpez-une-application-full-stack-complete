import { NgModule } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './pages/home/home.component';
import { LoginComponent } from './pages/auth/login/login.component';
import { RegisterComponent } from './pages/auth/register/register.component';
import { ArticlesListComponent } from './pages/articles-list/articles-list.component';
import { ArticleComponent } from './pages/article/article.component';
import { ThemesListComponent } from './pages/themes-list/themes-list.component';
import { MeComponent } from './pages/auth/me/me.component';
import { FormsModule, FormBuilder } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { JwtInterceptor } from './interceptors/jwt.interceptor';
import { ReactiveFormsModule } from '@angular/forms';
import { HeaderComponent } from './components/header/header.component';
import { ArticleFormComponent } from './pages/article-form/article-form.component';

@NgModule({
  declarations: [AppComponent, HomeComponent, LoginComponent, RegisterComponent, ArticlesListComponent, ArticleComponent, ThemesListComponent, MeComponent, HeaderComponent, ArticleFormComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatIconModule,
    MatFormFieldModule,
    FormsModule,
    MatInputModule,
    HttpClientModule,
    ReactiveFormsModule
  ],
  providers: [{ provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
    FormBuilder
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
