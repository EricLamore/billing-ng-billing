export interface ISettingsInvoiceUnitBilling {
  id?: string;
  seller?: string | null;
  personBuyerId?: string | null;
  personRefererCopys?: string | null;
  subscriptionId?: string | null;
  useBillingAddress?: boolean | null;
  paymentMethod?: string | null;
}

export class SettingsInvoiceUnitBilling implements ISettingsInvoiceUnitBilling {
  constructor(
    public id?: string,
    public seller?: string | null,
    public personBuyerId?: string | null,
    public personRefererCopys?: string | null,
    public subscriptionId?: string | null,
    public useBillingAddress?: boolean | null,
    public paymentMethod?: string | null
  ) {
    this.useBillingAddress = this.useBillingAddress ?? false;
  }
}

export function getSettingsInvoiceUnitBillingIdentifier(settingsInvoiceUnit: ISettingsInvoiceUnitBilling): string | undefined {
  return settingsInvoiceUnit.id;
}
