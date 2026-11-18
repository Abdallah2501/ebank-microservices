export interface Customer {
  id: number;
  name: string;
  email: string;
}

export interface BankAccount {
  accountId: string;
  balance: number;
  createdAt: string;
  currency: string;
  type: 'CURRENT_ACCOUNT' | 'SAVING_ACCOUNT';
  customerId: number;
  customer: Customer | null;
}