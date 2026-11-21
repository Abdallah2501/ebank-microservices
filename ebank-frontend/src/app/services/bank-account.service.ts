import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { BankAccount, NewAccount } from '../models/bank-account.model';

@Injectable({ providedIn: 'root' })
export class BankAccountService {
  private http = inject(HttpClient);
  private baseUrl = 'http://localhost:8888/ebank-service/api/accounts';

  private reloadSubject = new BehaviorSubject<void>(undefined);
  readonly reload$ = this.reloadSubject.asObservable();

  getAccounts(): Observable<BankAccount[]> {
    return this.http.get<BankAccount[]>(this.baseUrl);
  }

  createAccount(account: NewAccount): Observable<BankAccount> {
    return this.http.post<BankAccount>(this.baseUrl, account).pipe(
      tap(() => this.reloadSubject.next()),
    );
  }
}