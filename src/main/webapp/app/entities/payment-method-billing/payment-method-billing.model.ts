export interface IPaymentMethodBilling {
  id?: string;
  typeOfMean?: string | null;
  accountId?: string | null;
  iban?: string | null;
}

export class PaymentMethodBilling implements IPaymentMethodBilling {
  constructor(public id?: string, public typeOfMean?: string | null, public accountId?: string | null, public iban?: string | null) {}
}

export function getPaymentMethodBillingIdentifier(paymentMethod: IPaymentMethodBilling): string | undefined {
  return paymentMethod.id;
}
