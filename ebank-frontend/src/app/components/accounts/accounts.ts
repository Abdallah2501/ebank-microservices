import { AsyncPipe, DecimalPipe } from '@angular/common';
import { Component, inject } from '@angular/core';
import { Observable, catchError, map, of } from 'rxjs';
import { BankAccount } from '../../models/bank-account.model';
import { BankAccountService } from '../../services/bank-account.service';

interface AccountsState {
  accounts: BankAccount[];
  error: string | null;
}

@Component({
  selector: 'app-accounts',
  imports: [AsyncPipe, DecimalPipe],
  templateUrl: './accounts.html',
  styleUrl: './accounts.css',
})
export class Accounts {
  private service = inject(BankAccountService);

  state$: Observable<AccountsState> = this.service.getAccounts().pipe(
    map((accounts) => ({
      accounts: [...accounts].sort((a, b) => a.customerId - b.customerId),
      error: null,
    })),
    catchError((err) =>
      of({
        accounts: [],
        error: `Impossible de charger les comptes (${err.status ?? 'erreur réseau'})`,
      }),
    ),
  );
}