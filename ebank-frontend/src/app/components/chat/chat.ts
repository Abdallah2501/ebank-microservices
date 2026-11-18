import { Component, inject, signal } from '@angular/core';
import { marked } from 'marked';
import { ChatService } from '../../services/chat.service';

interface Message {
  role: 'user' | 'bot';
  text: string;
}

@Component({
  selector: 'app-chat',
  templateUrl: './chat.html',
  styleUrl: './chat.css',
})
export class Chat {
  private chatService = inject(ChatService);

  messages = signal<Message[]>([
    { role: 'bot', text: 'Bonjour, je suis **EbankBot**. Je peux consulter les clients et les comptes, et en créer.' },
  ]);
  input = signal('');
  sending = signal(false);

  render(text: string): string {
    return marked.parse(text, { async: false }) as string;
  }

  onInput(event: Event): void {
    this.input.set((event.target as HTMLInputElement).value);
  }

  send(): void {
    const question = this.input().trim();
    if (!question || this.sending()) return;

    this.input.set('');
    this.messages.update((m) => [...m, { role: 'user', text: question }, { role: 'bot', text: '' }]);
    this.sending.set(true);

    this.chatService.stream(question).subscribe({
      next: (chunk) => this.appendToLast(chunk),
      error: () => {
        this.appendToLast('\n\n*Erreur : impossible de joindre le chatbot.*');
        this.sending.set(false);
      },
      complete: () => this.sending.set(false),
    });
  }

  private appendToLast(chunk: string): void {
    this.messages.update((m) => {
      const copy = [...m];
      const last = copy[copy.length - 1];
      copy[copy.length - 1] = { ...last, text: last.text + chunk };
      return copy;
    });
  }
}