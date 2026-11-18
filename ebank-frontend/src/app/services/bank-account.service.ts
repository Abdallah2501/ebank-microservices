import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { BankAccount } from '../models/bank-account.model';

@Injectable({ providedIn: 'root' })
export class BankAccountService {
  private http = inject(HttpClient);
  private baseUrl = 'http://localhost:8888/ebank-service/api/accounts';

  getAccounts(): Observable<BankAccount[]> {
    return this.http.get<BankAccount[]>(this.baseUrl);
  }
}