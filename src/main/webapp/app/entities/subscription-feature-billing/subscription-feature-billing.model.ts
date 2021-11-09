import * as dayjs from 'dayjs';

export interface ISubscriptionFeatureBilling {
  id?: string;
  name?: string | null;
  lastUpdate?: dayjs.Dayjs | null;
  description?: string | null;
  isVisible?: boolean | null;
}

export class SubscriptionFeatureBilling implements ISubscriptionFeatureBilling {
  constructor(
    public id?: string,
    public name?: string | null,
    public lastUpdate?: dayjs.Dayjs | null,
    public description?: string | null,
    public isVisible?: boolean | null
  ) {
    this.isVisible = this.isVisible ?? false;
  }
}

export function getSubscriptionFeatureBillingIdentifier(subscriptionFeature: ISubscriptionFeatureBilling): string | undefined {
  return subscriptionFeature.id;
}
