import * as dayjs from 'dayjs';

export interface IFeatureBilling {
  id?: string;
  name?: string | null;
  lastUpdate?: dayjs.Dayjs | null;
  description?: string | null;
  isVisible?: boolean | null;
}

export class FeatureBilling implements IFeatureBilling {
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

export function getFeatureBillingIdentifier(feature: IFeatureBilling): string | undefined {
  return feature.id;
}
