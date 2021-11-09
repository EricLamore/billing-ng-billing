import * as dayjs from 'dayjs';

export interface ICustomerBilling {
  id?: string;
  name?: string | null;
  lastUpdate?: dayjs.Dayjs | null;
  description?: string | null;
  taxNo?: string | null;
  thirdPartyAccountingCode?: string | null;
  siret?: string | null;
  ownerId?: string | null;
  isParticular?: boolean | null;
  personReferers?: string | null;
  meansPayments?: string | null;
  subscriptions?: string | null;
  usages?: string | null;
  settingsInvoice?: string | null;
  partner?: boolean | null;
  partnerId?: string | null;
  customerId?: string | null;
  customerName?: string | null;
}

export class CustomerBilling implements ICustomerBilling {
  constructor(
    public id?: string,
    public name?: string | null,
    public lastUpdate?: dayjs.Dayjs | null,
    public description?: string | null,
    public taxNo?: string | null,
    public thirdPartyAccountingCode?: string | null,
    public siret?: string | null,
    public ownerId?: string | null,
    public isParticular?: boolean | null,
    public personReferers?: string | null,
    public meansPayments?: string | null,
    public subscriptions?: string | null,
    public usages?: string | null,
    public settingsInvoice?: string | null,
    public partner?: boolean | null,
    public partnerId?: string | null,
    public customerId?: string | null,
    public customerName?: string | null
  ) {
    this.isParticular = this.isParticular ?? false;
    this.partner = this.partner ?? false;
  }
}

export function getCustomerBillingIdentifier(customer: ICustomerBilling): string | undefined {
  return customer.id;
}
