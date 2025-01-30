import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { LoginComponent } from './pages/auth/login/login.component';
import { RegisterComponent } from './pages/auth/register/register.component';
import { MeComponent } from './pages/auth/me/me.component';
import { ArticlesListComponent } from './pages/articles-list/articles-list.component';
import { ThemesListComponent } from './pages/themes-list/themes-list.component';
import { ArticleComponent } from './pages/article/article.component';
import { AuthGuard } from './guards/auth.guard';
import { ArticleFormComponent } from './pages/article-form/article-form.component';

// consider a guard combined with canLoad / canActivate route option
// to manage unauthenticated user to access private routes
const routes: Routes = [{ path: '', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent},
  { path: 'me', component: MeComponent, canActivate: [AuthGuard]},
  { path: 'articles', component: ArticlesListComponent, canActivate: [AuthGuard]},
  { path: 'themesList', component: ThemesListComponent, canActivate: [AuthGuard]},
  { path: 'article/:id', component: ArticleComponent, canActivate: [AuthGuard] },
  { path: 'articleForm', component: ArticleFormComponent, canActivate: [AuthGuard] },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
