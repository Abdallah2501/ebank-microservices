import { Component, signal } from '@angular/core';
import { Accounts } from './components/accounts/accounts';
import { Chat } from './components/chat/chat';
import { NewAccount } from './components/new-account/new-account';
import { Spinner } from './components/spinner/spinner';

@Component({
  selector: 'app-root',
  imports: [Accounts, Chat, NewAccount, Spinner],
  templateUrl: './app.html',
  styleUrl: './app.css',
})
export class App {
  tab = signal<'accounts' | 'new' | 'chat'>('accounts');
}