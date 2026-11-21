import { Component, inject, signal } from '@angular/core';
import { FormField, form, min } from '@angular/forms/signals';
import { BankAccountService } from '../../services/bank-account.service';

const INITIAL = { customerId: 1, balance: 0, currency: 'MAD', type: 'CURRENT_ACCOUNT' };

@Component({
  selector: 'app-new-account',
  imports: [FormField],
  templateUrl: './new-account.html',
  styleUrl: './new-account.css',
})
export class NewAccount {
  private service = inject(BankAccountService);

  model = signal({ ...INITIAL });

  accountForm = form(this.model, (f) => {
    min(f.customerId, 1, { message: 'Identifiant client invalide' });
    min(f.balance, 0, { message: 'Le solde ne peut pas être négatif' });
  });

  saving = signal(false);
  message = signal<{ ok: boolean; text: string } | null>(null);

  submit(event: Event): void {
    event.preventDefault();
    if (!this.accountForm().valid() || this.saving()) return;

    this.saving.set(true);
    this.message.set(null);

    this.service.createAccount(this.model()).subscribe({
      next: (account) => {
        this.message.set({ ok: true, text: `Compte créé : ${account.accountId}` });
        this.model.set({ ...INITIAL });
        this.saving.set(false);
      },
      error: (err) => {
        this.message.set({ ok: false, text: `Échec de la création (${err.status ?? 'erreur réseau'})` });
        this.saving.set(false);
      },
    });
  }
}