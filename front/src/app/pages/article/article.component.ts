import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { Article } from 'src/app/interfaces/article.interface';
import { CommentRequest } from 'src/app/interfaces/commentRequest.interface';
import { ArticleService } from 'src/app/services/article.service';
import { CommentService } from 'src/app/services/comment.service';
import { Comment } from 'src/app/interfaces/comment.interface';
import { SessionInformation } from 'src/app/interfaces/sessionInformation.interface';
import { SessionService } from 'src/app/services/session.service';

@Component({
  selector: 'app-article',
  templateUrl: './article.component.html',
  styleUrls: ['./article.component.scss']
})
export class ArticleComponent implements OnInit, OnDestroy {

  article!: Article;
  commentRequest!: CommentRequest;
  subscriptions : Subscription[] = [];
  comments: Comment[] = [];
  newComment: string = '';
  userName: string = '';
  onError: string = '';

  constructor(private articleService: ArticleService, private route: ActivatedRoute, private commentService: CommentService, private sessionService: SessionService) { }

  ngOnInit(): void {
    this.userName = this.sessionService?.sessionInformation?.username ? this.sessionService.sessionInformation.username : '';
    this.subscriptions.push(this.articleService.getOne(this.route.snapshot.url[1].toString()).subscribe((data: any) => {
      this.article = data;
      this.subscriptions.push(this.commentService.getComments(this.route.snapshot.url[1].toString()).subscribe((data: any) => {
        this.comments = data;
      }));
    }));
  }

  ngOnDestroy(): void {
    if (this.subscriptions) {
      this.subscriptions.forEach(subscription => {
        subscription.unsubscribe();
      });
    }
  }

  back(): void {
    window.history.back();
  }

  addComment(): void {
    if (this.newComment.trim().length >= 2) {
      this.commentRequest = {
        content: this.newComment,
        article_id: parseInt(this.route.snapshot.url[1].toString())
      };
      this.subscriptions.push(this.commentService.send(this.commentRequest).subscribe());
      this.newComment = '';
    }else{
      this.onError = 'Le commentaire doit contenir au moins 2 caractères';
    }
  }

}
