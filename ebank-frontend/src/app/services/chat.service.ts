import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class ChatService {
  private url = 'http://localhost:8888/ebank-bot/chat/stream';
  readonly conversationId = crypto.randomUUID();

  stream(question: string): Observable<string> {
    return new Observable<string>((subscriber) => {
      const controller = new AbortController();
      const params = new URLSearchParams({ question, conversationId: this.conversationId });

      (async () => {
        try {
          const response = await fetch(`${this.url}?${params}`, { signal: controller.signal });
          if (!response.ok || !response.body) {
            throw new Error(`HTTP ${response.status}`);
          }
          const reader = response.body.getReader();
          const decoder = new TextDecoder();
          let buffer = '';

          while (true) {
            const { done, value } = await reader.read();
            if (done) break;
            buffer += decoder.decode(value, { stream: true }).replace(/\r\n/g, '\n');
            const events = buffer.split('\n\n');
            buffer = events.pop() ?? '';
            for (const event of events) {
              for (const line of event.split('\n')) {
                if (line.startsWith('data:')) {
                  const json = line.slice(5).trim();
                  if (json) subscriber.next(JSON.parse(json).t);
                }
              }
            }
          }
          subscriber.complete();
        } catch (e) {
          if (!controller.signal.aborted) subscriber.error(e);
        }
      })();

      return () => controller.abort();
    });
  }
}