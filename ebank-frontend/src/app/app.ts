import { Component, signal } from '@angular/core';
import { Accounts } from './components/accounts/accounts';
import { Chat } from './components/chat/chat';
import { Spinner } from './components/spinner/spinner';

@Component({
  selector: 'app-root',
  imports: [Accounts, Chat, Spinner],
  templateUrl: './app.html',
  styleUrl: './app.css',
})
export class App {
  tab = signal<'accounts' | 'chat'>('accounts');
}