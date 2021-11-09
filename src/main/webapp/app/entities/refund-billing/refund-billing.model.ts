export interface IRefundBilling {
  id?: string;
  reference?: string | null;
  amount?: number | null;
}

export class RefundBilling implements IRefundBilling {
  constructor(public id?: string, public reference?: string | null, public amount?: number | null) {}
}

export function getRefundBillingIdentifier(refund: IRefundBilling): string | undefined {
  return refund.id;
}
