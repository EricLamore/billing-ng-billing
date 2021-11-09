import * as dayjs from 'dayjs';

export interface ISubscriptionTmpBilling {
  id?: string;
  name?: string | null;
  lastUpdate?: dayjs.Dayjs | null;
  description?: string | null;
  commercialName?: string | null;
  ratePlans?: string | null;
  subscriptionFeatures?: string | null;
  version?: string | null;
  usages?: string | null;
  freeMonths?: number | null;
  invoiceItemDateds?: string | null;
}

export class SubscriptionTmpBilling implements ISubscriptionTmpBilling {
  constructor(
    public id?: string,
    public name?: string | null,
    public lastUpdate?: dayjs.Dayjs | null,
    public description?: string | null,
    public commercialName?: string | null,
    public ratePlans?: string | null,
    public subscriptionFeatures?: string | null,
    public version?: string | null,
    public usages?: string | null,
    public freeMonths?: number | null,
    public invoiceItemDateds?: string | null
  ) {}
}

export function getSubscriptionTmpBillingIdentifier(subscriptionTmp: ISubscriptionTmpBilling): string | undefined {
  return subscriptionTmp.id;
}
