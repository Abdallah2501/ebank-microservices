import { Component } from '@angular/core';
import { Accounts } from './components/accounts/accounts';
import { Spinner } from './components/spinner/spinner';

@Component({
  selector: 'app-root',
  imports: [Accounts, Spinner],
  templateUrl: './app.html',
  styleUrl: './app.css',
})
export class App {}