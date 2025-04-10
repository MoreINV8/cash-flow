import { HttpClient, HttpHeaders } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { User } from '../models/user.type';
import { catchError } from 'rxjs';
import { Dashboard } from '../models/dashboard/dashboard.type';
import { DashboardWithMonth } from '../models/dashboard-with-month.type';

@Injectable({
  providedIn: 'root',
})
export class BackendConnectService {
  private baseUrl = 'http://localhost:8000/api';
  private http = inject(HttpClient);

  public postLogin({
    username,
    password,
  }: {
    username: string;
    password: string;
  }) {
    return this.http
      .post<User>(this.baseUrl + '/login', {
        username: username,
        password: password,
      })
      .pipe(
        catchError((err) => {
          console.log(err);
          throw err;
        })
      );
  }

  public getDashboard(user: User) {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${user.jwt_token}`, // Ensure this is not null or undefined
    });
    console.log('Headers:', headers.get('Authorization')); // Log the Authorization header

    return this.http
      .get<DashboardWithMonth[]>(
        this.baseUrl + `/dashboard?username=${user.username}`,
        {
          headers,
        }
      )
      .pipe(
        catchError((err) => {
          console.log(err);
          throw err;
        })
      );
  }

}
