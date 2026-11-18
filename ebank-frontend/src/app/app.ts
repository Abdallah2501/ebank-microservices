import { Component } from '@angular/core';
import { Accounts } from './components/accounts/accounts';

@Component({
  selector: 'app-root',
  imports: [Accounts],
  templateUrl: './app.html',
  styleUrl: './app.css',
})
export class App {}